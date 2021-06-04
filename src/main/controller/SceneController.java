package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Main;
import main.model.BookingSession;

import java.io.IOException;
import java.util.Optional;

public class SceneController {

    public void openScene(Button btn, String resource) throws IOException {
        try {
            Stage window = (Stage) btn.getScene().getWindow();
            Parent root = FXMLLoader.load(Main.class.getResource(resource));
            Scene scene =  new Scene(root);
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void getQuestion(ComboBox<String> secretQuestions) {
        secretQuestions.getItems().add("What was your childhood nickname?");
        secretQuestions.getItems().add("In what city did you meet your spouse/significant other?");
        secretQuestions.getItems().add("What street did you live on in third grade?");
        secretQuestions.getItems().add("What was the last name of your first grade teacher?");
        secretQuestions.getItems().add("How old were you when you started your first job?");
    }

    public void showError(String headerText, String contentText){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }

    public void showInfo(String headerText, String contentText, Button btn, String resource) throws IOException {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        Optional<ButtonType> result = successAlert.showAndWait();
        if(!((Optional<?>) result).isPresent() || result.get() == ButtonType.OK){
            openScene(btn, resource);
        }
    }
}
