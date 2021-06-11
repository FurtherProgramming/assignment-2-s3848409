package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

import java.io.IOException;
import java.util.Optional;

public class SceneController {

    //open new scene function, reusable code
    public void openScene(Button btn, String resource) throws IOException {
        try {
            Stage window = (Stage) btn.getScene().getWindow();
            Parent root = FXMLLoader.load(Main.class.getResource(resource));
            Scene scene =  new Scene(root);
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        }catch (IOException e){
            showError("Error", "Cannot open requested scene at the moment.");
        }
    }

    //generate secret questions
    @FXML
    public void getQuestion(ComboBox<String> secretQuestions) {
        secretQuestions.getItems().add("What was your childhood nickname?");
        secretQuestions.getItems().add("In what city did you meet your spouse/significant other?");
        secretQuestions.getItems().add("What street did you live on in third grade?");
        secretQuestions.getItems().add("What was the last name of your first grade teacher?");
        secretQuestions.getItems().add("How old were you when you started your first job?");
    }

    //create and show error alert
    public void showError(String headerText, String contentText){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    //create and show info alert with button and scene opener
    public void showInfo(String headerText, String contentText, Button btn, String resource) throws IOException {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        Optional<ButtonType> result = successAlert.showAndWait();
        if(!((Optional<?>) result).isPresent() || result.get() == ButtonType.OK){
            openScene(btn, resource);
        }
    }

    //create and show info alert only
    public void showInfoOnly(String headerText, String contentText) throws IOException {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        successAlert.showAndWait();
    }

    //create and show confirmation alert
    public boolean showConfirmation(String headerText, String contentText) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText(headerText);
        confirmAlert.setContentText(contentText);
        final Optional<ButtonType> result = confirmAlert.showAndWait();
        return ((Optional<?>) result).isPresent() && result.get() == ButtonType.OK;
    }

    //create and show text dialog and return string of text
    public String showDialog(String headerText, String contentText){
        TextInputDialog textDialog = new TextInputDialog();
        textDialog.setHeaderText(headerText);
        textDialog.setContentText(contentText);
        Optional<String> result = textDialog.showAndWait();
        if(!((Optional<?>) result).isPresent()){
            textDialog.close();
            return null;
        }else{
            return textDialog.getEditor().getText();
        }
    }

}
