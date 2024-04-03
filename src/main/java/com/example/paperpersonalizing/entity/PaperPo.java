package com.example.paperpersonalizing.entity;



public class PaperPo {

    private int paperId;
    private String paperName;
    private String doi;
    private String savingPath;
    private int categoryId;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getSavingPath() {
        return savingPath;
    }

    public void setSavingPath(String savingPath) {
        this.savingPath = savingPath;
    }




}
