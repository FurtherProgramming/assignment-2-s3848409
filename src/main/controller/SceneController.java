package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

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
}
