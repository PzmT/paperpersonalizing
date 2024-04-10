package com.example.paperpersonalizing.entity;

public class PaperBo {
    private int paperId;
    private String paperName;
    private int categoryId;
    private String doi;
    private String savingPath;
    private String keyword;
    private String pptPath;
    private String videoPath;
    private String imgPath;
    public PaperBo(){}
    public PaperBo (PaperPo paperPo){
        this.paperId=paperPo.getPaperId();
        this.paperName=paperPo.getPaperName();
        this.doi=paperPo.getDoi();
        this.savingPath=paperPo.getSavingPath();
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPptPath() {
        return pptPath;
    }

    public void setPptPath(String pptPath) {
        this.pptPath = pptPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

