package com.emp.model.domain;

import java.time.LocalDate;
import java.util.Date;

public class Employee {

    private int id;
    private String name;
    private String addres;
    private int phone;
    private int postCode;
    private int tfn;
    private LocalDate startDate;
    private Position position;

    public Employee(){}

    public Employee(int id, String name, String addres, int postCode, int tfn, int phone, LocalDate startDate){
        this.id = id;
        this.name = name;
        this.addres = addres;
        this.postCode = postCode;
        this.startDate = startDate;
        this.tfn = tfn;
        this.phone = phone;
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

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public int getTfn() {
        return tfn;
    }

    public void setTfn(int tfn) {
        this.tfn = tfn;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
