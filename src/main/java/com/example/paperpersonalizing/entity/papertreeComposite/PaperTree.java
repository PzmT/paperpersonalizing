package com.example.paperpersonalizing.entity.papertreeComposite;

import com.example.paperpersonalizing.entity.CategoryBo;
import com.example.paperpersonalizing.entity.CategoryPo;

import java.util.ArrayList;
import java.util.List;

public class PaperTree extends PaperTreeEntry{

    private CategoryBo categoryBo;
    private final List<PaperTreeEntry> paperTreeEntryList;

    public void addPaperTreeEntry(PaperTreeEntry paperTreeEntry) {
        paperTreeEntryList.add(paperTreeEntry);
    }

    public List<PaperTreeEntry> getPaperTreeEntryList() {
        return paperTreeEntryList;
    }

    public PaperTree(CategoryBo categoryBo) {
        this.categoryBo=categoryBo;
        paperTreeEntryList=new ArrayList<>();
    }

    public CategoryBo getCategoryBo() {
        return categoryBo;
    }

    public void setCategoryBo(CategoryBo categoryBo) {
        this.categoryBo = categoryBo;
    }
}
