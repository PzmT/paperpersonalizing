package com.example.paperpersonalizing.controller;

import com.example.paperpersonalizing.entity.PaperBo;
import com.example.paperpersonalizing.entity.ResultEntity;
import com.example.paperpersonalizing.service.AlertPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/alert")
public class AlertController {

    @Autowired
    @Lazy
    private AlertPaperService alertPaperService;

    @PostMapping("/dealalerting")
    public ResultEntity<?> dealAlerting(int userId, int paperId,boolean receipt){
        return alertPaperService.dealAlerting(userId,paperId,receipt);
    }

    @PostMapping("/receivealertPaper")
    public ResultEntity<?> receiveAlertPaper(MultipartFile zipFile) throws IOException {
        return alertPaperService.receiveAlertPaper(zipFile);
    }


}
