package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller  {
    @FXML
    private Button btnPushups;
    @FXML
    private Button btnJump;
    @FXML
    private Button btnSquats;
    @FXML
    void initialize(){
        btnJump.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Jump.fxml"));
            getFXMLWindows(fxmlLoader,"Jump");
        });
        btnPushups.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PushUp.fxml"));
            getFXMLWindows(fxmlLoader,"PushUp");
        });
        btnSquats.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Squats.fxml"));
            getFXMLWindows(fxmlLoader,"Squats");
        });

    }

    private void getFXMLWindows(FXMLLoader fxmlLoader, String nameWin) {
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(nameWin);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
