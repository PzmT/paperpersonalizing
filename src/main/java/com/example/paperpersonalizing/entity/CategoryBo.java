package com.example.paperpersonalizing.entity;

public class CategoryBo {
    private Integer id;
    private String name;

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
