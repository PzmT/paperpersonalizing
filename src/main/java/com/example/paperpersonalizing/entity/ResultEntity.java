package com.example.paperpersonalizing.entity;

public class ResultEntity<T> {
    private int code;
    private T detail;

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public void success(){
        this.code=200;
    }
    public void failure(){
        this.code=400;
    }
}
