package com.example.paperpersonalizing.service;

import com.example.paperpersonalizing.config.StorageConfig;
import com.example.paperpersonalizing.entity.*;
import com.example.paperpersonalizing.entity.papertreeComposite.Paper;
import com.example.paperpersonalizing.entity.papertreeComposite.PaperAlerting;
import com.example.paperpersonalizing.entity.papertreeComposite.PaperTree;
import com.example.paperpersonalizing.entity.papertreeComposite.PaperTreeEntry;
import com.example.paperpersonalizing.mapper.AlertPaperMapper;
import com.example.paperpersonalizing.mapper.UsersCategoryAndCategoryMapper;
import com.example.paperpersonalizing.mapper.UserInfoMapper;
import com.example.paperpersonalizing.mapper.UserPaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class PaperTreeService {

    @Autowired
    private UserPaperMapper userPaperMapper;
    @Autowired
    private AlertPaperMapper alertPaperMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UsersCategoryAndCategoryMapper usersCategoryAndCategoryMapper;
    @Autowired
    private ParseService parseService;
    @Autowired
    private FileStorageService fileStorageService;
    public ResultEntity<?> getPaperTree(Integer userId){
        ResultEntity<?> result=new ResultEntity<>();

        List<PaperPo> userPaperPoList=userPaperMapper.getUserPaperByUserId(userId);
        List<PaperPo> alertPaperPoList=alertPaperMapper.getAlertedPaperByUserId(userId);
        List<PaperPo> alertingPaperPoList=alertPaperMapper.getAlertingPaperByUserId(userId);
        List<PaperPo> paperPoList= new ArrayList<>(userPaperPoList);
        Set<Integer> categoryIdSet=new HashSet<>();
        paperPoList.addAll(alertPaperPoList);
        paperPoList.addAll(alertingPaperPoList);

        //将处于alerting的paper的id放在一个set里
        HashSet<Integer> alertingSet=new HashSet<>();
        for (PaperPo paperPo : alertingPaperPoList) {
            alertingSet.add(paperPo.getPaperId());
        }

        for (PaperPo paperPo : paperPoList) {
            categoryIdSet.add(paperPo.getCategoryId());
        }
        List<CategoryPo> categoryPoList= usersCategoryAndCategoryMapper.getCategoryByCategoryId(categoryIdSet);
        //paperMap通过paper所属的个人分类进行匹配
        HashMap<Integer,List<PaperPo>> paperMap= new HashMap<>();

        for (PaperPo paperPo : paperPoList) {
            if(paperMap.get(paperPo.getCategoryId())==null){
                List<PaperPo> list=new ArrayList<>();
                paperMap.put(paperPo.getCategoryId(), list);
            }
            List<PaperPo> list=paperMap.get(paperPo.getCategoryId());
            list.add(paperPo);
        }
        // map->通过id找到所有子目录
        HashMap<Integer,List<CategoryPo>> map=new HashMap<>();
        // 填满map
        for (CategoryPo categoryPo : categoryPoList) {
            if(map.get(categoryPo.getParentCategoryId())==null){
                List<CategoryPo> list=new ArrayList<>();
                map.put(categoryPo.getParentCategoryId(),list);
            }
            List<CategoryPo> list = map.get(categoryPo.getParentCategoryId());
            list.add(categoryPo);
        }
        String userName=userInfoMapper.getUserNameByUserId(userId);
        // 开始构造paper树的目录结构
        PaperTree paperTree=buildPaperTreeCategory(userName, map, paperMap,alertingSet);
        return result;
    }
    private PaperTree buildPaperTreeCategory (String userName, HashMap<Integer, List<CategoryPo>> map,
                                              HashMap<Integer, List<PaperPo>> paperMap,HashSet<Integer> alertingSet){
        // BFS
        Queue<PaperTree> queue=new PriorityQueue<>();
        CategoryBo root=new CategoryBo(userName,null);
        PaperTree rootPaperTree=new PaperTree(root);
        queue.offer(rootPaperTree);
        while (!queue.isEmpty()){
            PaperTree paperTree=queue.poll();
            List<CategoryPo> list=map.get(paperTree.getCategoryBo().getId());
            List<PaperPo> paperList=paperMap.get(paperTree.getCategoryBo().getId());
            for (PaperPo paperPo : paperList) {
                //如果是alert过去但是用户还没确认
                if(alertingSet.contains(paperPo.getPaperId())){
                    PaperAlerting paperAlerting=new PaperAlerting(new PaperBo(paperPo));
                    paperTree.addPaperTreeEntry(paperAlerting);
                }else{//一般情况
                    Paper paper=new Paper(new PaperBo(paperPo));
                    paperTree.addPaperTreeEntry(paper);
                }

            }
            for (CategoryPo categoryPo : list) {
                PaperTree childPaperTree=new PaperTree(new CategoryBo(categoryPo));
                queue.offer(childPaperTree);
                paperTree.addPaperTreeEntry(childPaperTree);
            }
        }
        return rootPaperTree;
    }

    public ResultEntity<?> initialize(int userId, MultipartFile zip){
        ResultEntity<?> result=new ResultEntity<>();
        // String rootDirectoryPath = System.getProperty("java.io.tmpdir")+ UUID.randomUUID();
        String userDirectoryPath = StorageConfig.USERS_DIRECTORY+File.pathSeparator+userId+UUID.randomUUID();
        try{
            List<File> files=fileStorageService.readZip(zip,userDirectoryPath);
             fileStorageService.readUserDirectory(files);
        }catch (IOException e){
            e.printStackTrace();
        }
        result.setCode(200);
        return result;
    }
}
