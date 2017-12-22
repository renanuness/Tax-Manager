package com.emp.controller;

import com.emp.model.DAO.EmployeeDAO;
import com.emp.model.DAO.PaymentDAO;
import com.emp.model.Exports.PDFExportTaxSheet;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Employee;
import com.emp.model.domain.Payment;
import com.emp.model.domain.Position;
import com.emp.model.domain.Tax;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class FXMLAnchorPaneGerenalTaxController implements Initializable{
    @FXML
    private ChoiceBox<String> choiceBoxOptions = new ChoiceBox<>();
    @FXML
    DatePicker datePickerStartPeriod;
    @FXML
    Label labelTotalGross, labelTotalSuper,labelTotalTax;
    DatabaseHandler databaseHandler = new DatabaseHandler();
    private Connection connection = databaseHandler.ConectDB();
    PaymentDAO paymentDAO = new PaymentDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    //
    List<Tax> taxes = new ArrayList<>();
    List<Employee> employees = new ArrayList<>();

    //
    Tax taxAlways = new Tax();
    Map<String,LocalDate> periodAndStart = new HashMap<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBoxPositionSetOptions();
        setDatePicker();
        paymentDAO.setConnection(connection);
        employeeDAO.setConnection(connection);
    }

    private void setDatePicker() {
        datePickerStartPeriod.setValue(LocalDate.now());
    }

    @FXML
    private void handleRefreshButtonClick(){
        calculateTax();
    }
    @FXML
    private void handleButtonExportPDFClick() throws IOException {
        PDFExportTaxSheet pdfExportTaxSheet = new PDFExportTaxSheet();
        pdfExportTaxSheet.buildPDFAll("D:\\Git\\Exports\\PDF",employees,taxes);
    }
    private void choiceBoxPositionSetOptions(){
        ObservableList<String> choices = FXCollections.observableArrayList();
        choices.add("WEEKLY");
        choices.add("MONTHLY");
        choices.add("YEARLY");
        choiceBoxOptions.setValue(choices.get(0));
        choiceBoxOptions.setItems(choices);
    }

    private List<LocalDate> setFinalDate(){
        LocalDate startDate = datePickerStartPeriod.getValue();
        LocalDate finalDate = null;
        if(choiceBoxOptions.getValue() == "WEEKLY"){
            finalDate = datePickerStartPeriod.getValue().plusDays(6);
        }else if(choiceBoxOptions.getValue() == "MONTHLY"){
            startDate = LocalDate.of(startDate.getYear(),startDate.getMonthValue(),1);
            finalDate = startDate.plusMonths(1);
            System.out.println("MONTH");
        }else if(choiceBoxOptions.getValue() == "YEARLY"){
            startDate = LocalDate.of(startDate.getYear(),startDate.getMonthValue(),1);
            finalDate = startDate.plusYears(1);
            System.out.println("YEAR");

        }
        List<LocalDate> dates = new ArrayList<>();
        dates.add(startDate);
        dates.add(finalDate);
        System.out.println("Start:"+startDate+" FInal:"+finalDate);
        periodAndStart.put(choiceBoxOptions.getValue(),startDate);
        return dates;
    }
    private List<Employee> selectEmployee(){
        List<Employee> employees = employeeDAO.SelectAllEmployees();
        return employees;
    }
    private List<Tax> calculateTax(){
        List<LocalDate> dates = setFinalDate();
        List<Payment> payments = new ArrayList<>();
        employees = selectEmployee();
        System.out.println(dates.get(0)+":"+dates.get(1));
        for(int i=0; i<employees.size(); i++){
            Tax tax = new Tax();
            payments = paymentDAO.SelectPaymenteFromTo(dates.get(0),dates.get(1),employees.get(i));
            tax.setDateTwo(dates.get(1));
            tax.setDateOne(dates.get(0));
            tax.setEmployee(employees.get(i));
            Double totalGross = 0.0;
            for(int j=0; j< payments.size(); j++) {
                totalGross += payments.get(j).getHours()*payments.get(j).getPayRate();
            }
            tax.setGross(totalGross);

            taxes.add(tax);
        }
        printTaxes(taxes);
        return taxes;
    }

    private void printTaxes(List<Tax> taxes){
        Double totalGross = 0.0;
        Double totalSuper = 0.0;
        Double totalTax = 0.0;
        for(int i=0; i<taxes.size(); i++){
            taxes.get(i).calculateTaxIncome();
            taxes.get(i).calculateNet();
            taxes.get(i).calculateSuper();

            totalGross += taxes.get(i).getGross();
            totalSuper += taxes.get(i).getSuperannuation();
            totalTax += taxes.get(i).getTaxIncome();
        }
        labelTotalGross.setText(String.valueOf(totalGross));
        labelTotalSuper.setText(String.valueOf(totalSuper));
        labelTotalTax.setText(String.valueOf(totalTax));
    }
}
