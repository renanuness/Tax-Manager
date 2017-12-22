package com.emp.controller;

import com.emp.LoginManager;
import com.emp.model.database.DatabaseCreator;
import com.emp.model.database.DatabaseHandler;
import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;

/** Controls the main application screen */
public class MainViewController extends Application {
  @FXML private Button logoutButton;
  @FXML private Label  sessionLabel;
  @FXML private AnchorPane anchorPane;
  public void initialize() {
  }
  public void initSessionID(final LoginManager loginManager, String sessionID) {
    sessionLabel.setText(sessionID);

    logoutButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        loginManager.logout();
      }
    });
  }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(("/view/FXMLVBoxMain.fxml")));
        Scene scene = new Scene(root,500,400);
        primaryStage.setScene(scene);
        DatabaseHandler dbHandler = new DatabaseHandler();
        Connection conn = dbHandler.ConectDB();
        DatabaseCreator databaseCreator = new DatabaseCreator();
        databaseCreator.CreateDB();
        dbHandler.DisconnectDB(conn);
        primaryStage.show();

    }

    public void loadAnchorPaneEmployee() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLAnchorPaneRecordEmployee.fxml"));
        Scene scene = new Scene(root,500,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void loadAnchorPanePosition() throws IOException{
      Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLAnchorPaneRecordPosition.fxml"));
      Scene scene = new Scene(root,500,400);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
  }

  public void loadAnchorPanePayment() throws IOException {
      Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLAnchorPaneRecordPaymentDialog.fxml"));
      Scene scene = new Scene(root,500,400);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
  }

  public void loadAnchorPaneTax()throws IOException{
      Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLAnchorPaneViewTax.fxml"));
      Scene scene = new Scene(root,500,400);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.show();
  }

    public void loadAnchorPaneGeneralTax()throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLAnchorPaneGerenalTax.fxml"));
        Scene scene = new Scene(root,500,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}