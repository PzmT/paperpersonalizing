package com.example.paperpersonalizing.service;

import com.example.paperpersonalizing.API.BaseResponse;
import com.example.paperpersonalizing.entity.ResultEntity;
import com.example.paperpersonalizing.mapper.UsersCategoryAndCategoryMapper;
import com.example.paperpersonalizing.util.ReadTool;
import com.gearwenxin.client.ernie.ErnieBotClient;
import com.gearwenxin.entity.response.ChatResponse;
import jakarta.annotation.Resource;
import org.apache.commons.text.similarity.LevenshteinDistance;
import com.example.paperpersonalizing.entity.CategoryBo;
import com.example.paperpersonalizing.entity.PaperBo;
import com.example.paperpersonalizing.entity.PaperPo;
import com.example.paperpersonalizing.mapper.PaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParseService {

    @Autowired
    PaperMapper paperMapper;

    @Autowired
    UsersCategoryAndCategoryMapper usersCategoryAndCategoryMapper;

    @Autowired
    ParseService parseService;

    @Autowired
    ReadTool readTool;

    private List<String> existingCategoryNames = usersCategoryAndCategoryMapper.getAllCategoryName();


    @Resource
    private ErnieBotClient ernieBotClient;

    // 单次对话
    public BaseResponse<String> chatSingle(String msg) {
        ChatResponse response = ernieBotClient.chatSingle(msg);
        return BaseResponse.success(response.getResult());
    }



    @Transactional
    public PaperBo parsePaper(File paper) throws IOException {
        PaperBo paperBo = new PaperBo();

        // Read the paper file (assuming it's in a simple text format)
        String fileContent = readTool.readFileToString(paper);
//        BaseResponse baseResponse=new BaseResponse<>();
        String msg = BaseResponse.getValueAsString
                (parseService.chatSingle("请告诉我这篇论文的题目是什么，只返回给我题目的名字，不含其他信息："+ fileContent));

        // Check if paper exists in database, if not insert it
        PaperPo existingPaper = paperMapper.getPaperIdByDoi(paperBo.getDoi());
        if (existingPaper != null) {
            paperBo.setPaperId(existingPaper.getPaperId());
            paperBo.setPaperName(existingPaper.getPaperName());
        } else {
            PaperPo newPaper = new PaperPo();
            newPaper.setPaperName(paperBo.getPaperName());
            newPaper.setDoi(paperBo.getDoi());
            paperMapper.setPaperByDoi(newPaper.getDoi());
            paperBo.setPaperId(newPaper.getPaperId());
        }

        return paperBo;
    }

    // 实现 parseCategory 方法
    public CategoryBo parseCategory(File directory) {
        CategoryBo categoryBo = new CategoryBo();
        String directoryName = directory.getName();
        categoryBo.setOriginalName(directoryName);

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        String closestMatch = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (String categoryName : existingCategoryNames) {
            int distance = levenshteinDistance.apply(directoryName, categoryName);
            if (distance < lowestDistance) {
                lowestDistance = distance;
                closestMatch = categoryName;
            }
        }

        categoryBo.setMatchedName(closestMatch);
        categoryBo.setSimilarityScore(lowestDistance);

        return categoryBo;
    }

    public ResultEntity<List<PaperBo>> parseAlertPaper(String path) {
        ResultEntity<List<PaperBo>> result = new ResultEntity<>();
        List<PaperBo> papers = new ArrayList<>();

        File rootDirectory = new File(path);
        if (!rootDirectory.exists() || !rootDirectory.isDirectory()) {
            result.failure();
            result.setDetail(null);
            return result;
        }

        File[] categoryDirectories = rootDirectory.listFiles(File::isDirectory);
        if (categoryDirectories != null) {
            for (File categoryDir : categoryDirectories) {
                int categoryId = usersCategoryAndCategoryMapper.getCategoryIdByFormalCategoryName(categoryDir.getName());
                File[] paperFiles = categoryDir.listFiles(File::isFile);
                if (paperFiles != null) {
                    for (File paperFile : paperFiles) {
                        PaperBo paperBo = new PaperBo();
                        paperBo.setCategoryId(categoryId);
                        paperBo.setPaperName(paperFile.getName());
                        papers.add(paperBo);
                    }
                }
            }
        }

        result.success();
        result.setDetail(papers);
        return result;
    }




}
