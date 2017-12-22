package com.emp.model.domain;

import java.io.Serializable;

public class Position implements Serializable{
    private int id;
    private String postion;

    public Position(){}

    public Position(int id, String position){
        this.id = id;
        this.postion = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }
}
