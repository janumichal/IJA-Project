package gui;

import Chess_pieces.*;
import enums.color_piece;
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
            Tab tab = new Tab();
            drawBoard(c.chessBoardView, tab);

        }
        catch (Exception e){

        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void drawBoard(Pane pane, Tab tab){
        for (Node node : pane.getChildren()) {
            int img_size = 50;
            Integer y = GridPane.getColumnIndex(node);
            Integer x = GridPane.getRowIndex(node);
            int row = y == null ? 0 : y;
            int col = x == null ? 0 : x;
            if (node instanceof Pane) {
                node.setId("pane" + row + "_" + col);
            }

            if ((row + col) % 2 == 0) {
                node.setStyle("-fx-background-color: rgb(255,255,255);");
            } else {
                node.setStyle("-fx-background-color: rgb(128,128,128);");
            }



            ImageView img;

            Piece piece = tab.game.board.getField(row, col).getPiece();
            if(piece instanceof Rook){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Rook_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Rook_b.png"));
                }
            }else if(piece instanceof Knight){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Knight_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Knight_b.png"));
                }
            }else if(piece instanceof Bishop){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Bishop_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Bishop_b.png"));
                }
            }else if(piece instanceof King){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/King_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/King_b.png"));
                }
            }else if(piece instanceof Queen){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Queen_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Queen_b.png"));
                }
            }else if(piece instanceof Pawn){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Pawn_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Pawn_B.png"));
                }
            }else{
                img = new ImageView();
            }

            if (node instanceof Pane){
                img.setFitHeight(img_size);
                img.setFitWidth(img_size);
                ((Pane)node).getChildren().add(img);
            }
        }
    }
}