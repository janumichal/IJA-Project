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
    Pane root;
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //loader.setDefaultClassLoader(Thread.currentThread().getContextClassLoader());
        loader.setLocation(Main.class.getResource("sample.fxml"));
        root = loader.load();
        Controller myController = loader.getController();
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();

        init(myController);
        
    }

    public void init(Controller c){
        try {

            for (Node node : c.chessBoardView.getChildren()) {
                int img_size = 50;
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
                    ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Pawn_B.png")));
                    if (node instanceof Pane) {
                        img.setFitHeight(img_size);
                        img.setFitWidth(img_size);
                        ((Pane) node).getChildren().add(img);
                    }
                }
                else if(row == 6) {
                    ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Pawn_W.png")));
                    if (node instanceof Pane){
                        img.setFitHeight(img_size);
                        img.setFitWidth(img_size);
                        ((Pane)node).getChildren().add(img);
                    }
                }
                else if (row == 0) {
                    if (col == 0 || col== 7) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Rook_B.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    } else if (col == 1 || col == 6) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Knight_B.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    } else if (col == 2 || col == 5) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Bishop_B.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    }
                    else if (col == 4) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/King_B.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    }
                    else if (col == 3) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Queen_B.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    }

                } else if (row == 7) {
                    if (col == 0 || col== 7) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Rook_W.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    } else if (col == 1 || col == 6) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Knight_W.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    } else if (col == 2 || col == 5) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Bishop_W.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    }
                    else if (col == 4) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/King_W.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    }
                    else if (col == 3) {
                        ImageView img = new ImageView(new Image(Main.class.getResourceAsStream("../chess_figures_img/Queen_W.png")));
                        if (node instanceof Pane){
                            img.setFitHeight(img_size);
                            img.setFitWidth(img_size);
                            ((Pane)node).getChildren().add(img);
                        }
                    }
                }
                else {
                    ImageView img = new ImageView();
                    if (node instanceof Pane){
                        img.setFitHeight(img_size);
                        img.setFitWidth(img_size);
                        ((Pane)node).getChildren().add(img);
                    }
                }
            }
        }
        catch (Exception e){

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
