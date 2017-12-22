package com.emp.model.domain;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.math.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class Tax {
    private Employee employee;
    private LocalDate dateOne;
    private LocalDate dateTwo;
    private Double anualGross;
    private Double gross;
    private Double net;
    private Double superannuation;
    private Double taxIncome;

    public Tax(){}

    public Tax(Employee employee, LocalDate dateOne, LocalDate dateTwo, Double gross) {

        this.employee = employee;
        this.dateOne = dateOne;
        this.dateTwo = dateTwo;
        this.gross = gross;
        calculateTaxIncome();
        calculateNet();
        calculateSuper();

    }

    public void calculateTaxIncome(){
        Double anual = this.gross*52;
        if(anual<18200){
            this.taxIncome = 0.0;
        }else if(anual>18200 && anual<=37000){
            this.taxIncome = 0.19*(anual-18200);
        }else if(anual>37000 && anual<=87000){
            this.taxIncome = ((anual-37000)*0.325)+3572;
        }else if(anual>87000 && anual <= 180000){
            this.taxIncome = ((anual-87000)*0.37)+19822;
        }else if(anual > 180000){
            this.taxIncome = ((anual-180000)*0.45)+54232;
        }
        this.taxIncome = this.taxIncome/52;
        System.out.println(this.taxIncome);
    }
    public void calculateSuper(){
        this.superannuation = this.gross * 0.095;

    }
    public void calculateNet(){
        this.net = this.gross-this.taxIncome;

    }

    public Double getAnualGross() {
        return anualGross;
    }

    public void setAnualGross(Double anualGross) {
        this.anualGross = anualGross;

    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setDateOne(LocalDate dateOne) {
        this.dateOne = dateOne;
    }

    public void setDateTwo(LocalDate dateTwo) {
        this.dateTwo = dateTwo;
    }

    public void setGross(Double gross) {
        this.gross = gross;

    }

    public void setNet(Double net) {
        this.net = net;
    }

    public void setSuperannuation(Double superannuation) {
        this.superannuation = superannuation;
    }

    public void setTaxIncome(Double taxIncome) {
        this.taxIncome = taxIncome;
    }
    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getDateOne() {
        return dateOne;
    }

    public LocalDate getDateTwo() {
        return dateTwo;
    }

    public Double getGross() {
        return this.gross;
    }

    public Double getNet() {
        return this.net;
    }

    public Double getSuperannuation() {
        return this.superannuation;
    }

    public Double getTaxIncome() {
        return this.taxIncome;
    }
}
