package com.example.paperpersonalizing.service;

import com.example.paperpersonalizing.entity.PaperBo;
import com.example.paperpersonalizing.entity.PaperPo;
import com.example.paperpersonalizing.entity.ResultEntity;
import com.example.paperpersonalizing.mapper.AlertPaperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertPaperService {
    @Autowired
    private AlertPaperMapper alertPaperMapper;

    public void alertPaperToUser(List<PaperPo> paperList, int userId){
        for (PaperPo paperPo : paperList) {
            alertPaperMapper.insertAlertingPaper(paperPo.getPaperId(),userId);
        }
    }

    public ResultEntity<?> dealAlerting(int userId){
        ResultEntity result=new ResultEntity();
        List<PaperPo> alertingPapers=alertPaperMapper.getAlertingPaperByUserId(userId);
        for (PaperPo alertingPaper : alertingPapers) {

        }
        return result;
    }
    public ResultEntity<?> selectAlertPaper(List<PaperPo> paperPoList){
        ResultEntity result=new ResultEntity();
        //TODO 接收了一批论文，将其解析并个性化推送给user

        return result;
    }
}
