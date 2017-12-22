package com.emp.controller;

import com.emp.model.DAO.PositionDAO;
import com.emp.model.database.DatabaseHandler;
import com.emp.model.domain.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAnchorPaneRecordPositionController implements Initializable {
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private Connection connection = databaseHandler.ConectDB();
    private PositionDAO positionDAO = new PositionDAO();

    //
    @FXML
    private TableView<Position> tableViewPositions;
    @FXML
    private TableColumn<Position, String> tableColumnPosition;
    @FXML
    private Label labelPosition;

    private List<Position> listPositions;
    private ObservableList<Position> observableListPositions;
    //

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        positionDAO.setConnection(connection);
        loadTableViewPositions();

        tableViewPositions.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTableViewPosition(newValue));
    }

    public void loadTableViewPositions(){
        tableColumnPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        listPositions = positionDAO.SelectAllPositions();
        if(listPositions.size()>0){
        observableListPositions = FXCollections.observableArrayList(listPositions);
        tableViewPositions.setItems(observableListPositions);
        }
    }

    public void selectItemTableViewPosition(Position position){
        if(position != null){
            labelPosition.setText(position.getPostion());
        }else{
            labelPosition.setText("");

        }
    }

    @FXML
    public void handleButtonADD() throws IOException {
        Position position = new Position();
        boolean buttonSaveConfirmClick = showFXMLAnchorPaneRecordPositionDialog(position);
        if(buttonSaveConfirmClick){
            System.out.println(position.getPostion());
            positionDAO.InsertPosition(position);
            loadTableViewPositions();
        }
    }

    @FXML
    public void handleButtonAlter() throws IOException{
        Position position = tableViewPositions.getSelectionModel().getSelectedItem();
        if(position != null) {
            boolean buttonSaveConfirmCLick = showFXMLAnchorPaneRecordPositionDialog(position);
            if (buttonSaveConfirmCLick) {
                positionDAO.AlterPosition(position);
                loadTableViewPositions();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Select one position");
            alert.show();
        }
    }

    @FXML
    public void handleButtonDelete(){
        Position position = tableViewPositions.getSelectionModel().getSelectedItem();
        if(positionDAO.DeletePosition(position)){
            loadTableViewPositions();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCESS");
            alert.setHeaderText("Position deleted with success");
            alert.show();
        }else{

        }
    }
    private boolean showFXMLAnchorPaneRecordPositionDialog(Position position) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneRecordEmployeeDialogController.class.getResource("/view/FXMLAnchorPaneRecordPositionDialog.fxml"));
        AnchorPane page = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Record Position");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAnchorPaneRecordPositionDialogController controller = loader.getController();
        controller.setStage(dialogStage);
        controller.setPosition(position);
        dialogStage.showAndWait();
        return controller.isButtonConfirmClicked();
    }
}
