package com.example.paperpersonalizing.service;

import com.example.paperpersonalizing.config.StorageConfig;
import com.example.paperpersonalizing.entity.PaperBo;
import com.example.paperpersonalizing.entity.PaperPo;
import com.example.paperpersonalizing.entity.ResultEntity;
import com.example.paperpersonalizing.entity.UserPo;
import com.example.paperpersonalizing.mapper.AlertPaperMapper;
import com.example.paperpersonalizing.mapper.UserInfoMapper;
import com.example.paperpersonalizing.mapper.UserPaperMapper;
import com.example.paperpersonalizing.mapper.UsersCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertPaperService {

    @Autowired
    private AlertPaperMapper alertPaperMapper;
    @Autowired
    @Lazy
    private AlertPaperService alertPaperService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UsersCategoryMapper usersCategoryMapper;
    @Autowired
    private PaperTreeEntryService paperTreeEntryService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ParseService parseService;

    public void alertPaperToUser(List<PaperBo> paperList, int userId){
        for (PaperBo paperBo : paperList) {
            alertPaperMapper.insertAlertingPaperByUserIdPaperId(paperBo.getPaperId(), userId, paperBo.getCategoryId());
        }
    }

    public ResultEntity<?> dealAlerting(int userId, int paperId, boolean receipt){
        if(!receipt){
            paperTreeEntryService.deleteNotAlertPaper(userId, paperId);
        }else {
            paperTreeEntryService.addAlertPaper(userId, paperId);

        }
        ResultEntity<?> result=new ResultEntity<>();
        result.success();
        return result;
    }

    public ResultEntity<?> receiveAlertPaper(MultipartFile zipFile) throws IOException {
        ResultEntity<?> result=new ResultEntity<>();
        List<File> files;
        List<PaperBo> paperBoList = new ArrayList<>();

        files = fileStorageService.readZip(zipFile, StorageConfig.USERS_DIRECTORY);

        for(File file : files){
            paperBoList.add(parseService.parsePaper(file));
        }

        selectAlertPaper(paperBoList);

        result.success();
        return result;
    }

    /**
     * 接收了一批论文，将其解析并个性化推送给每一个user
     */
    public ResultEntity<?> selectAlertPaper(List<PaperBo> paperBoList){

        ResultEntity<?> result=new ResultEntity<>();
        List<UserPo> userPoList=userInfoMapper.getAllUsers();

        for (UserPo userPo : userPoList) {
            List<PaperBo> alertPaper=new ArrayList<>();
            //将paperPoList中需要推给user的论文放入list
            for (PaperBo paperBo : paperBoList) {
                if(usersCategoryMapper.getUserCategoryIdByUserId(userPo.getUserId()).contains(paperBo.getCategoryId())){
                    alertPaper.add(paperBo);
                }
            }
            //将选择出来的论文推送给user
            alertPaperService.alertPaperToUser(alertPaper,userPo.getUserId());
        }

        return result;
    }
}
