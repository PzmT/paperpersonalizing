package com.example.paperpersonalizing.controller;

import com.example.paperpersonalizing.entity.ResultEntity;
import com.example.paperpersonalizing.service.AlertPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class AlertController {
    @Autowired
    private AlertPaperService alertPaperService;

    @PostMapping
    public ResultEntity<?> dealAlerting(int userId){
        return alertPaperService.dealAlerting(userId);
    }

}
