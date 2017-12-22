package com.emp.model.calendar;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Week {
    private short year;
    private LocalDate firstDay;
    private short weekNumber;
    public Week(){}

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public LocalDate getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(LocalDate firstDay) {
        if(firstDay.getDayOfWeek() == null)
        this.firstDay = firstDay;
    }

    private void setWeekNumber(){
        int dof = firstDay.getDayOfYear();

    }

}
