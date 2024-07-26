package com.example.paperpersonalizing.entity;

public class CategoryBo {
    private Integer id;
    private String name;
    private String originalName;
    private String matchedName;
    private int similarityScore;

    // Getters and Setters
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getMatchedName() {
        return matchedName;
    }

    public void setMatchedName(String matchedName) {
        this.matchedName = matchedName;
    }

    public int getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(int similarityScore) {
        this.similarityScore = similarityScore;
    }

    @Override
    public String toString() {
        return "CategoryBo{" +
                "originalName='" + originalName + '\'' +
                ", matchedName='" + matchedName + '\'' +
                ", similarityScore=" + similarityScore +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public CategoryBo(){}
    public CategoryBo (String name,Integer id){
        this.name=name;
        this.id=id;
    }
    public CategoryBo(CategoryPo categoryPo){
        this.name=categoryPo.getCategory();
        this.id=categoryPo.getId();
    }
}
