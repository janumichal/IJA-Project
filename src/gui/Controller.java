package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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

import Chess_pieces.Piece;
import Chess_common.*;


public class Controller {
    @FXML public GridPane chessBoardView;
    @FXML private Pane main;
    private Field[][] board_array;
    Piece currentPiece;
    Boolean click = false;
    private Board chessBoard = new Board();
    private Pane board[][] = new Pane[8][8];
    Field from = new Field(0,0);
    Field to = new Field(0,0);


    Tab tab = new Tab();
    @FXML ListView<String> listView;

    @FXML protected void onMouseClick(MouseEvent event){
        for(Node node : chessBoardView.getChildren()){
            if (node instanceof Pane){
                Pane pane = (Pane) node;
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

            currentPiece = tab.game.board.getField(row, col).getPiece();
            if (currentPiece == null) return;

            from.setRow(row);
            from.setCol(col);
            System.out.println(from.getPiece());
        }
        else if (click){
            int col2 = (int) (event.getX() / width * 8);
            int row2 = (int) (event.getY() / height * 8);
            System.out.println("To " + row2 + " : " + col2);
            click = false;

            to.setRow(row2);
            to.setCol(col2);
        }


       tab.move(from, to);


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
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                listView.getItems().add(line);
            }

            br.close();
        }
        catch(Exception e){

        }

    }

    @FXML protected void save(ActionEvent event){

    }


    @FXML protected void gameType(ActionEvent event){
        // TODO
    }
}
