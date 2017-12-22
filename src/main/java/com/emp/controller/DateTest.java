package com.emp.controller;

import javafx.application.Application;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

public class DateTest extends Application implements DateAux {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LocalDate localDate = LocalDate.of(2017,5,6);
        LocalDate calendarDay = findMonday(localDate);


        int o;
        do{
        Scanner scan = new Scanner(System.in);
             o = scan.nextInt();
             calendarDay = SetTheWeek(calendarDay,o);

        }while (o>0);
    }

    public LocalDate findMonday(LocalDate localDate){
        int dif = localDate.getDayOfWeek().getValue() - 1;
        LocalDate monday = localDate.minusDays(dif);
        return monday;
    }

    public LocalDate SetTheWeek(LocalDate date, int dir){
        LocalDate newDate;
        if(dir == 1) {
            newDate = date.minusWeeks(1);
        }else{
            newDate = date.plusWeeks(1);
        }
        for(int i=0; i<7; i++){
            System.out.println(newDate.plusDays(i).getDayOfWeek());
        }
        return newDate;
    }
    @Override
    public void getLastMonday() {

    }
}
