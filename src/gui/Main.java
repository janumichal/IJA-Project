package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import Chess_common.*;

import java.awt.*;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader.setDefaultClassLoader(Thread.currentThread().getContextClassLoader());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));



        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
