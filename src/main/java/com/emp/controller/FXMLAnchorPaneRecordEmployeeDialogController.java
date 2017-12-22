package com.emp.controller;

import com.emp.model.DAO.EmployeeDAO;
import com.emp.model.DAO.PositionDAO;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Employee;
import com.emp.model.domain.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

public class FXMLAnchorPaneRecordEmployeeDialogController implements Initializable{
    private final DatabaseHandler databaseHandler = new DatabaseHandler();
    private final Connection connection = databaseHandler.ConectDB();
    private  final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final PositionDAO positionDAO = new PositionDAO();
    private List<Position> positions;

    private boolean buttonConfirmClicked = false;

    private Stage stage;
    @FXML
    private TextField textFieldNameEmployee;
    @FXML
    private TextField textFieldAddressEmployee;
    @FXML
    private TextField textFieldPostCodeEmployee;
    @FXML
    private TextField textFieldPhoneEmployee;
    @FXML
    private TextField textFieldTFNEmployee;
    @FXML
    private ChoiceBox<String> choiceBoxPositionEmployee = new ChoiceBox<>();
    @FXML
    private DatePicker datePickerStartDateEmployee;

    private Position selectedPosition;
    private Employee employee;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeDAO.setConnection(connection);
        positionDAO.setConnection(connection);
        choiceBoxPositionSetOptions();
        datePickerSetTodayDate();
        choiceBoxPositionEmployee.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> selectedPosition = positionToInt(newValue));


    }



    @FXML
    public void handleButtonSaveClick(){
        System.out.println("SAVE");

        if(1==1){

            employee.setName(textFieldNameEmployee.getText());
            employee.setAddres(textFieldAddressEmployee.getText());
            employee.setPhone(Integer.parseInt(textFieldPhoneEmployee.getText()));
            employee.setPostCode(Integer.parseInt(textFieldPostCodeEmployee.getText()));
            employee.setTfn(Integer.parseInt(textFieldTFNEmployee.getText()));
            employee.setStartDate(datePickerStartDateEmployee.getValue());
            employee.setPosition(selectedPosition);
            System.out.println(employee.getId()+":"+employee.getName()+":"+employee.getAddres());
            buttonConfirmClicked = true;
            stage.close();
        }
    }
    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }
    public void setEmployee(Employee employee) {
       this.employee = employee;
        textFieldNameEmployee.setText(employee.getName());
        textFieldAddressEmployee.setText(employee.getAddres());
        textFieldPostCodeEmployee.setText(String.valueOf(employee.getPostCode()));
        textFieldPhoneEmployee.setText(String.valueOf(employee.getPhone()));
        textFieldTFNEmployee.setText(String.valueOf(employee.getTfn()));
        if (employee.getPosition() == null) {
            choiceBoxPositionEmployee.setValue("");
        } else {
            choiceBoxPositionEmployee.setValue(employee.getPosition().getPostion());
        }

        datePickerStartDateEmployee.setValue(employee.getStartDate());

    }

    private void choiceBoxPositionSetOptions(){
        positions = positionDAO.SelectAllPositions();
        ObservableList<String> choices = FXCollections.observableArrayList();
        for (Position position : positions) {
            choices.add(position.getPostion());
            System.out.println("ADD" + position.getPostion());
        }
        choiceBoxPositionEmployee.setItems(choices);
    }

    private Position positionToInt(Number str){
        selectedPosition = positions.get((Integer) str);
        System.out.println("Esoclheu:"+selectedPosition.getPostion());
        return selectedPosition;
    }
    private boolean validateEmployeeData(){
        String errorMessage = "";
        if(textFieldNameEmployee.getText() == null){
            errorMessage += "Name can't be blank";
        }
        if(textFieldAddressEmployee.getText() == null){
            errorMessage += "Address can't be blank";
        }
        if(textFieldPhoneEmployee.getText() == null ){
            errorMessage += "Phone can't be blank";
        }
        if(textFieldPostCodeEmployee.getText() != null && textFieldPostCodeEmployee.getText().length()<4){
            errorMessage += "Post Code not valid";
        }
        if(textFieldTFNEmployee.getText() != null){
            validateTFN(textFieldTFNEmployee.getText());
        }else{
            errorMessage += "TFN can't be blank";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }

    }

    private void validateTFN(String tfn){
        int[] weights = new int[]{1, 4, 3, 7, 5, 8, 6, 9, 10};
//        String tfnStr = Integer.toString(tfn);
        if(tfn.length() == 9){
            int sum = 0;
            for(int i = 0; i < 9; i++){
                int digit = tfn.charAt(i);
                sum = sum+(digit*weights[i]);
            }
            if(sum%11 == 0){
                System.out.println("TFN Válido");
            }else{
                System.out.println("TFN inválido");

            }
        }

    }

    private void datePickerSetTodayDate(){
        //Setting the current date
        LocalDate localDate = LocalDate.now();
        datePickerStartDateEmployee.setValue(localDate);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public boolean isButtonConfirmClicked() {

        return buttonConfirmClicked;
    }

    public Stage getDialogStage() {
        return stage;
    }
}
