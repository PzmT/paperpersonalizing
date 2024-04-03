package com.example.paperpersonalizing.controller;



import com.example.paperpersonalizing.entity.ResultEntity;
import com.example.paperpersonalizing.service.PaperTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/papertree")
public class PaperTreeController {
    @Autowired
    private PaperTreeService paperTreeService;
    @GetMapping("/getPaperTree")
    public ResultEntity<?> getPaperTree(@RequestParam("userId") Integer userId) {
        return paperTreeService.getPaperTree(userId);
    }
    @PostMapping("/initialize")
    public ResultEntity<?> initialize(@RequestParam("zip") MultipartFile zip, @RequestParam("userId") Integer userId){
        return paperTreeService.initialize(userId, zip);
    }
}
