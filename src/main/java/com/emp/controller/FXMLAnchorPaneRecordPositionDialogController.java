package com.emp.controller;

import com.emp.model.DAO.PositionDAO;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Position;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
public class FXMLAnchorPaneRecordPositionDialogController implements Initializable{
    DatabaseHandler databaseHandler = new DatabaseHandler();
    Connection connection = databaseHandler.ConectDB();
    PositionDAO positionDAO = new PositionDAO();

    private Stage stage;
    @FXML
    private TextField textFieldPosition;
    private boolean buttonConfirmClicked;
    private Position position;

    public boolean isButtonConfirmClicked() {
        return buttonConfirmClicked;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        positionDAO.setConnection(connection);

    }

    public void handleButtonClickSave(){
        System.out.println("SAVE");

        if(validatePositionData()) {
            position.setPostion(textFieldPosition.getText());
            buttonConfirmClicked = true;
            stage.close();
        }
    }

    public void setPosition(Position position) {
        this.position = position;
        textFieldPosition.setText(position.getPostion());
    }

    private boolean validatePositionData() {
        String errorMessage = "";
        if(textFieldPosition.getText() == null ){
            errorMessage += "Position can't be empty";
        }
        if (errorMessage.length() == 0) {
            System.out.println("sem erro");
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


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
