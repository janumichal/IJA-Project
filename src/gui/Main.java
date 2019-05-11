package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import Chess_common.*;




public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //loader.setDefaultClassLoader(Thread.currentThread().getContextClassLoader());
        loader.setLocation(Main.class.getResource("sample.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();

        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(Main.class.getResource("chessBoard.fxml"));
        GridPane chessBoard = loader1.load();
        chessBoard.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        for (Node node : chessBoard.getChildren()) {
            Integer x = GridPane.getRowIndex(node);
            Integer y = GridPane.getColumnIndex(node);
            int row = x == null ? 0 : x;
            int col = y == null ? 0 : y;
            if (node instanceof Pane) {
                node.setId("pane" + row + "_" + col);
            }
            row = 8 - row - 1;
            if ((row + col) % 2 == 1) {
                node.setStyle("-fx-background-color: rgb(255,255,255);");
            } else {
                node.setStyle("-fx-background-color: rgb(128,128,128);");
            }
            if (row == 1) {
                ImageView img = new ImageView(new Image(getClass().getResourceAsStream("../chess_figures_img/Pawn_B.png")));
                if (node instanceof Pane) {
                    ((Pane) node).getChildren().add(img);
                }
            }
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
