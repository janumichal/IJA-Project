package gui;

import Chess_pieces.*;
import enums.color_piece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.Set;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import javafx.stage.Stage;

import Chess_common.*;


public class Controller {
    @FXML public GridPane chessBoardView;
    @FXML private Pane main;
    private Piece currentPiece;
    private Boolean click = false;
    private Field from;
    private Field to;
    private Pane board[][] = new Pane[8][8];

    
    private Tab tab = new Tab();

    @FXML ListView<String> listView;

    @FXML protected void onMouseClick(MouseEvent event){

        for (Node node : chessBoardView.getChildren()) {
            if (node instanceof Pane){
                Pane pane = (Pane)node;
                board[Character.getNumericValue(pane.getId().charAt(4))][Character.getNumericValue(pane.getId().charAt(6))] = pane;
            }
        }

        double width = chessBoardView.getWidth();
        double height = chessBoardView.getHeight();
        if (!click) {
            int col = (int) (event.getX() / width * 8);
            int row = (int) (event.getY() / height * 8);
            click = true;
            System.out.println("From " + row + " : " + col);
            from = tab.game.board.getField(col,row);
            currentPiece = from.getPiece();
            System.out.println(currentPiece instanceof Pawn ? "ano" : "ne");
            if (currentPiece == null) return;



        }
        else if (click){
            int col2 = (int) (event.getX() / width * 8);
            int row2 = (int) (event.getY() / height * 8);
            System.out.println("To " + row2 + " : " + col2);
            click = false;

            to = tab.game.board.getField(col2,row2);
            tab.move(from, to);
            Pane oldPane = board[currentPiece.getRow()][currentPiece.getCol()];
            Image image = null;

            for (Node view : oldPane.getChildren()) {
                if (view instanceof ImageView) {
                    image = ((ImageView) view).getImage();
                    ((ImageView) view).setImage(null);
                }

            }

            drawBoard(chessBoardView,tab);
            loadListFromMove();
        }


    }



    @FXML protected void stopGame(ActionEvent event){
        // TODO
    }

    @FXML protected void undo(ActionEvent event){
        tab.undo();
    }

    @FXML protected void redo(ActionEvent event){
        tab.redo();
    }

    @FXML protected void next(ActionEvent event){
        tab.next();
    }

    @FXML protected void prew(ActionEvent event) { tab.prew(); }

    @FXML protected void restart(ActionEvent event){
        tab.newGame();
    }

    @FXML protected void load(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(main.getScene().getWindow());
        ArrayList<String> lines = new ArrayList<String>();

        try {
            StringBuilder str_b = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                str_b.append(line);
                str_b.append("\n");
            }
            br.close();
            tab.game.loadAllMoves(str_b.toString());
            loadListFromMove();
        }
        catch(Exception e){

        }

    }

    public void loadListFromMove(){
        listView.getItems().clear();
        String[] radky = tab.game.printAllMoves().split("\n");
        for (String one_line: radky){
            listView.getItems().add(one_line);
        }
    }

    @FXML protected void save(ActionEvent event){

    }


    @FXML protected void gameType(ActionEvent event){
        // TODO
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
                    img.setImage(null);
                }

                if (node instanceof Pane){
                    img.setFitHeight(img_size);
                    img.setFitWidth(img_size);
                    ((Pane)node).getChildren().add(img);
                }
            }
        }
}
