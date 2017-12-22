package com.emp.controller;

import com.emp.model.DAO.EmployeeDAO;
import com.emp.model.DAO.PaymentDAO;
import com.emp.model.Exports.PDFExportTaxSheet;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Employee;
import com.emp.model.domain.Payment;
import com.emp.model.domain.Tax;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.table.TableColumn;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAnchorPaneViewTaxController implements Initializable{
    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final Connection connection = databaseHandler.ConectDB();
    PaymentDAO paymentDAO = new PaymentDAO();
    EmployeeDAO employeeDAO = new EmployeeDAO();
    LocalDate todayDate = LocalDate.now();
    LocalDate theMonday;

    @FXML
    DatePicker datePickerStartInterval;
    @FXML
    ChoiceBox<String> choiceBoxPeriod = new ChoiceBox<>();
    @FXML
    TableView<Employee> tableViewEmployee;
    @FXML
    javafx.scene.control.TableColumn<Employee,String> tableColumnEmployee ;
    @FXML
    Label labelNameEmployee, labelStartDate, labelGrossInterval, labelGrossAlways;
    @FXML
    Label labelNetInterval,labelNetAlways,labelSuperInterval,labelSuperAlways,labelTaxIncomeInterval,labelTaxIncomeAlways;
    Employee employee = new Employee();
    List<Employee> listEmployee = new ArrayList<>();
    Tax taxInterval, taxAlways;
    private ObservableList<Employee> observableListEmployee;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    LocalDate endDateInterval;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeDAO.setConnection(connection);
        paymentDAO.setConnection(connection);
        df2.setRoundingMode(RoundingMode.UP);
        choiceBoxPeriod();
        setDateInterval();
        loadTableViewEmployee();
        tableViewEmployee.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTablViewEmployee(newValue));
    }
    private void selectItemTablViewEmployee(Employee newValue) {
        employee = newValue;
        labelNameEmployee.setText(newValue.getName());
        Double gross = calculateGross(paymentDAO.SelectEmployeePayments(employee));
        taxAlways = new Tax(employee,employee.getStartDate(),todayDate,gross);
        labelStartDate.setText(employee.getStartDate().getDayOfMonth()
                +"/"+employee.getStartDate().getMonthValue()
                +"/"+employee.getStartDate().getYear());

    }
    public void loadTableViewEmployee(){
        tableColumnEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        listEmployee = employeeDAO.SelectAllEmployees();
        observableListEmployee = FXCollections.observableArrayList(listEmployee);
        tableViewEmployee.setItems(observableListEmployee);
    }
    public LocalDate findMonday(LocalDate localDate){
        if(localDate.getDayOfWeek().getValue() != 1) {
            int dif = localDate.getDayOfWeek().getValue() - 1;
            LocalDate monday = localDate.minusDays(dif);
            return monday;
        }else{
            return localDate;
        }
    }
    public void setDateInterval(){
        LocalDate lastMonday = findMonday(todayDate);
        datePickerStartInterval.setValue(lastMonday);
    }
    private LocalDate setEndDate(){
        String interval = choiceBoxPeriod.getValue();
        if(interval == "WEEKLY"){
            endDateInterval = datePickerStartInterval.getValue().plusDays(6);
        }
        return endDateInterval;

}
    public void handleButtonRefreshClick() throws IOException {
        selectInterval();
        setTableValues();

    }

    public Double calculateGross(List<Payment> payments){
        Double gross=0.0;
        for(Payment p : payments){
            gross += (p.getHours()*p.getPayRate());
        }
        return gross;
    }
    public void setTableValues(){
        labelGrossInterval.setText(String.valueOf(df2.format(taxInterval.getGross())));
        labelGrossAlways.setText(String.valueOf(df2.format(taxAlways.getGross())));
        labelNetInterval.setText(String.valueOf(df2.format(taxInterval.getNet())));
        labelNetAlways.setText(String.valueOf(df2.format(taxAlways.getNet())));
        labelSuperInterval.setText(String.valueOf(df2.format(taxInterval.getSuperannuation())));
        labelSuperAlways.setText(String.valueOf(df2.format(taxAlways.getSuperannuation())));
        labelTaxIncomeInterval.setText(String.valueOf(df2.format(taxInterval.getTaxIncome())));
        labelTaxIncomeAlways.setText(String.valueOf(df2.format(taxAlways.getTaxIncome())));
    }

    public void selectInterval(){
        endDateInterval = setEndDate();
        List<Payment> pay = paymentDAO.SelectPaymenteFromTo(datePickerStartInterval.getValue(), endDateInterval,employee);
        Double gross = calculateGross(pay);
        taxInterval = new Tax(employee,datePickerStartInterval.getValue(),endDateInterval,gross);
    }
    private void choiceBoxPeriod(){
        ObservableList<String> choices = FXCollections.observableArrayList();
        choices.add("WEEKLY");
        choices.add("MONTHLY");
        choices.add("YEARLY");
        choiceBoxPeriod.setValue(choices.get(0));
        choiceBoxPeriod.setItems(choices);
    }
    public void exportToPDF() throws IOException {
        PDFExportTaxSheet pdfExportTaxSheet = new PDFExportTaxSheet();
        pdfExportTaxSheet.buildPDFSingle(taxInterval);
    }
}
