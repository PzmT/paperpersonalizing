package com.example.paperpersonalizing.service;

import com.example.paperpersonalizing.entity.CategoryBo;
import com.example.paperpersonalizing.entity.PaperBo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
public class ParseService {
    public PaperBo parsePaper(File paper){
        PaperBo paperBo=new PaperBo();
        //TODO: parse paper's id, paperName, DOI
        return paperBo;
    }
    public CategoryBo parseCategory(File directory){
        CategoryBo categoryBo=new CategoryBo();
        String directoryName=directory.getName();
        //TODO: parse directory to a category and change its name
        return categoryBo;
    }
}
