//package com.example.paperpersonalizing.controller;
//
//import com.example.paperpersonalizing.entity.ResultEntity;
//import com.example.paperpersonalizing.entity.papertreeComposite.Paper;
//import com.example.paperpersonalizing.entity.papertreeComposite.PaperTree;
//import com.example.paperpersonalizing.service.PaperService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.File;
//import java.util.List;
//
//@RestController
//@RequestMapping("/paper")
//public class PaperController {
//    @Autowired
//    private PaperService paperService;
//    private Paper paper;
//
//    @GetMapping("/readDirectory")
//    public ResultEntity<?> readDirectory(String directoryPath) {
//        ResultEntity<PaperTree> result=new ResultEntity<>();
//        return result;
//    }
//}
