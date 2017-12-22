package com.emp.controller;

import com.emp.model.DAO.EmployeeDAO;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Employee;
import com.emp.model.domain.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FXMLAnchorPaneRecordEmployeeController implements Initializable {

    @FXML
    Label labelNameEmployee;
    @FXML
    Label labelAddressEmployee;
    @FXML
    Label labelPhoneEmployee;
    @FXML
    Label labelPostCodeEmployee;
    @FXML
    Label labelTFNEmployee;
    @FXML
    Label labelPositionEmployee;
    @FXML
    Label labelStartDateEmployee;
    @FXML
    TableView<Employee> tableViewEmployee;
    @FXML
    TableColumn<Employee, String> tableColumnEmployeeName;
    @FXML
    TableColumn<Employee, Integer> tableColumnEmployeePhone;
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
    DatabaseHandler databaseHandler = new DatabaseHandler();
    private Connection connection = databaseHandler.ConectDB();
    EmployeeDAO employeeDAO = new EmployeeDAO();

    List<Employee> listEmployee;
    private ObservableList<Employee> observableListEmployee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeDAO.setConnection(connection);
        loadTableViewEmployee();
        tableViewEmployee.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTableViewEmployee(newValue));
    }

    public void loadTableViewEmployee(){

        tableColumnEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmployeePhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        listEmployee = employeeDAO.SelectAllEmployees();
        observableListEmployee = FXCollections.observableArrayList(listEmployee);
        tableViewEmployee.setItems(observableListEmployee);
    }

    public void selectItemTableViewEmployee(Employee employee){
        if(employee != null){

            labelNameEmployee.setText(employee.getName().trim());
            labelAddressEmployee.setText(employee.getAddres().trim());
            labelPhoneEmployee.setText(String.valueOf(employee.getPhone()));
            labelTFNEmployee.setText(String.valueOf(employee.getTfn()));
            labelPositionEmployee.setText(String.valueOf(employee.getPosition().getPostion().trim()));
            labelPostCodeEmployee.setText(String.valueOf(employee.getPostCode()));


            labelStartDateEmployee.setText(employee.getStartDate().getDayOfMonth()
                    +"/"+employee.getStartDate().getMonthValue()
                    +"/"+employee.getStartDate().getYear());

        }else{
            labelNameEmployee.setText("");
            labelAddressEmployee.setText("");
            labelPhoneEmployee.setText("");
            labelPostCodeEmployee.setText("");
            labelPositionEmployee.setText("");
            labelStartDateEmployee.setText("");
            labelTFNEmployee.setText("");

        }
    }
    @FXML
    public void AddButtonClick() throws IOException {
        Employee employee = new Employee();
        boolean buttonClicked = showFXMLAnchorPaneRecordEmployeeDialog(employee);
        if(buttonClicked){
            showEmployeeData(employee);
            employeeDAO.InsertEmployee(employee);
            loadTableViewEmployee();
        }
    }
    @FXML
    public void AlterButtonClick() throws IOException{
        Employee employee = tableViewEmployee.getSelectionModel().getSelectedItem();
        if(employee!=null){
            boolean buttonClicked = showFXMLAnchorPaneRecordEmployeeDialog(employee);
            if (buttonClicked) {
                showEmployeeData(employee);
                employeeDAO.AlterEmployee(employee);
                loadTableViewEmployee();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setHeaderText("Select the employee to alter...");
            alert.show();
        }
    }

    @FXML
    public void DeleteButtonClick(){
        Employee employee = tableViewEmployee.getSelectionModel().getSelectedItem();
        if(employeeDAO.DeleteEmployee(employee)){
            loadTableViewEmployee();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Employee deleted with success");
            alert.show();
        }else{

        }
    }
    public boolean showFXMLAnchorPaneRecordEmployeeDialog(Employee employee) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneRecordEmployeeDialogController.class.getResource("/view/FXMLAnchorPaneRecordEmployeeDialog.fxml"));
        AnchorPane page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Record Employee");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAnchorPaneRecordEmployeeDialogController controller = loader.getController();

        controller.setStage(dialogStage);
        controller.setEmployee(employee);
        dialogStage.showAndWait();
        return controller.isButtonConfirmClicked();
    }

    public void showEmployeeData(Employee employee){
         labelNameEmployee.setText(employee.getName());
         labelAddressEmployee.setText(employee.getAddres());
         labelPhoneEmployee.setText(String.valueOf(employee.getPhone()));
         labelPostCodeEmployee.setText(String.valueOf(employee.getPostCode()));
         labelTFNEmployee.setText(String.valueOf(employee.getTfn()));
         labelPositionEmployee.setText(String.valueOf(employee.getPosition().getPostion()));
         DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
         labelStartDateEmployee.setText(String.valueOf(employee.getStartDate().format(pattern)));
    }


}
