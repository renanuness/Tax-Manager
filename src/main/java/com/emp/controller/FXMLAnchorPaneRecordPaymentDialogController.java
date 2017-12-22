package com.emp.controller;

import com.emp.model.DAO.EmployeeDAO;
import com.emp.model.DAO.PaymentDAO;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Employee;
import com.emp.model.domain.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

public class FXMLAnchorPaneRecordPaymentDialogController implements Initializable{
    @FXML
    Label labelEmployeeName;
    @FXML
    Label labelMonthYear;
    @FXML
    TableView<Employee> tableViewEmployee;
    @FXML
    TableColumn<Employee, String> tableColumnEmployeeName;
    @FXML
    Label labelWeekDay1, labelWeekDay2,labelWeekDay3,labelWeekDay4,labelWeekDay5,labelWeekDay6,labelWeekDay7;
    @FXML
    Label labelMonthDay1,labelMonthDay2,labelMonthDay3,labelMonthDay4,labelMonthDay5,labelMonthDay6,labelMonthDay7;
    @FXML
    TextField textFieldHourRate,textFieldMonday,textFieldTuesday,textFieldWednesday,textFieldThursday,textFieldFriday,textFieldSaturday,textFieldSunday;

    List<Payment> allPayments = new ArrayList<>();
    List<Employee> listEmployee = new ArrayList<>();
    private ObservableList<Employee> observableListEmployee;
    List<LocalDate> currentDays = new ArrayList<>();;
    List<TextField> listTextFieldHours = new ArrayList<>();
    List<Label> listLabelWeekDays = new ArrayList<>();
    List<Label> listLabelMonthDays = new ArrayList<>();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    Employee employee;

    PaymentDAO paymentDAO = new PaymentDAO();
    DatabaseHandler databaseHandler = new DatabaseHandler();
    private Connection connection = databaseHandler.ConectDB();
    LocalDate todayDate = LocalDate.now();
    LocalDate theMonday;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    employeeDAO.setConnection(connection);
    paymentDAO.setConnection(connection);
       theMonday = findMonday(todayDate);
       loadTableViewEmployee();
        setListMonthDays();
        setListWeekDays();
        setListTextFieldHours();
        setTheDays();
        tableViewEmployee.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTablViewEmployee(newValue));
    }

    private void selectItemTablViewEmployee(Employee newValue) {
        employee = newValue;
        labelEmployeeName.setText(newValue.getName());
        refreshExistingHours();
    }

    private void setTheDays(){
        for(int i=0; i<7; i++){
            listLabelWeekDays.get(i).setText(String.valueOf(theMonday.plusDays(i).getDayOfWeek()).substring(0,3));
            listLabelMonthDays.get(i).setText(String.valueOf(theMonday.plusDays(i).getDayOfMonth()));
            labelMonthYear.setText(theMonday.getMonth()+"/"+theMonday.getYear());
            currentDays.add(theMonday.plusDays(i));
        }
    }

    public LocalDate findMonday(LocalDate localDate) {
        if (localDate.getDayOfWeek().getValue() != 1) {
            int dif = localDate.getDayOfWeek().getValue() - 1;
            LocalDate monday = localDate.minusDays(dif);
            return monday;

        } else {
            return localDate;
        }
    }
    @FXML
    public void handleButtonSaveClick(){
        if(employee != null) {
        paymentDAO.InsertPayment(setInsertData());
        List<Payment> l = setInsertData();
        for(int i=0; i<l.size(); i++){
            System.out.println(l.get(i).getDatePayment()+" | "+l.get(i).getHours());
        }
        }else{
            System.out.println("Selecione um funcionário");
        }
    }
    @FXML
    public void nextWeek(){
        LocalDate newDate = theMonday.plusWeeks(1);
        theMonday = newDate;
        for(int i=0; i<7; i++){
            listLabelWeekDays.get(i).setText(String.valueOf(newDate.plusDays(i).getDayOfWeek()).substring(0,3));
            listLabelMonthDays.get(i).setText(String.valueOf(newDate.plusDays(i).getDayOfMonth()));
            currentDays.set(i,newDate.plusDays(i));
            refreshExistingHours();
        }
        labelMonthYear.setText(newDate.getMonth()+"/"+newDate.getYear());
        refreshExistingHours();
    }
    @FXML
    public void previousWeek(){
        LocalDate newDate = theMonday.minusWeeks(1);
        theMonday = newDate;
        for(int i=0; i<7; i++){
            listLabelWeekDays.get(i).setText(String.valueOf(newDate.plusDays(i).getDayOfWeek()).substring(0,3));
            listLabelMonthDays.get(i).setText(String.valueOf(newDate.plusDays(i).getDayOfMonth()));
            currentDays.set(i,newDate.plusDays(i));
        }
        labelMonthYear.setText(newDate.getMonth()+"/"+newDate.getYear());
        refreshExistingHours();
    }

    private void setListTextFieldHours(){
        listTextFieldHours.add(textFieldMonday);
        listTextFieldHours.add(textFieldTuesday);
        listTextFieldHours.add(textFieldWednesday);
        listTextFieldHours.add(textFieldThursday);
        listTextFieldHours.add(textFieldFriday);
        listTextFieldHours.add(textFieldSaturday);
        listTextFieldHours.add(textFieldSunday);
    }
    private void setListWeekDays(){
        listLabelWeekDays.add(labelWeekDay1);
        listLabelWeekDays.add(labelWeekDay2);
        listLabelWeekDays.add(labelWeekDay3);
        listLabelWeekDays.add(labelWeekDay4);
        listLabelWeekDays.add(labelWeekDay5);
        listLabelWeekDays.add(labelWeekDay6);
        listLabelWeekDays.add(labelWeekDay7);

    }

    private void setListMonthDays(){
        listLabelMonthDays.add(labelMonthDay1);
        listLabelMonthDays.add(labelMonthDay2);
        listLabelMonthDays.add(labelMonthDay3);
        listLabelMonthDays.add(labelMonthDay4);
        listLabelMonthDays.add(labelMonthDay5);
        listLabelMonthDays.add(labelMonthDay6);
        listLabelMonthDays.add(labelMonthDay7);
    }
    public void loadTableViewEmployee(){
        tableColumnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        listEmployee = employeeDAO.SelectAllEmployees();
        observableListEmployee = FXCollections.observableArrayList(listEmployee);
        tableViewEmployee.setItems(observableListEmployee);
    }

    public List<Payment> setInsertData(){
        List<Payment> listInsterPayment = new ArrayList<>();
        for(int i=0; i<listTextFieldHours.size(); i++){
            Payment payment = new Payment();
            if(listTextFieldHours.get(i).getText().length()>0 && listTextFieldHours.get(i).getText().length() != 0){
                payment.setEmployee(employee);
                payment.setDatePayment(currentDays.get(i));
                payment.setHours(Integer.parseInt(listTextFieldHours.get(i).getText()));
                payment.setPayRate(Float.parseFloat(textFieldHourRate.getText()));
                listInsterPayment.add(payment);
            }else{

                payment.setEmployee(employee);
                payment.setDatePayment(currentDays.get(i));
                payment.setHours(0);
                payment.setPayRate(0);
                listInsterPayment.add(payment);


            }
        }

        return listInsterPayment;
    }

    private void refreshExistingHours(){
        if(employee != null) {
            LocalDate theDay = theMonday;
            int d = 0;
            allPayments = paymentDAO.SelectPaymenteFromTo(theMonday, theMonday.plusDays(6), employee);
            if(allPayments.size()>0) {
                textFieldHourRate.setText(String.valueOf(allPayments.get(0).getPayRate()));
                for (int i = 0; i < 7; i++) {
                    if (theDay.equals(allPayments.get(d).getDatePayment())) {
                        listTextFieldHours.get(i).setText(String.valueOf(allPayments.get(d).getHours()));
                        d++;
                    } else {
                        listTextFieldHours.get(i).setText("");
                    }
                    theDay = theDay.plusDays(1);
                }
            }else{
                for(int i =0; i<listTextFieldHours.size(); i++){
                    listTextFieldHours.get(i).setText("");
                }
            }
        }
    }


}
