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

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import javafx.stage.Stage;


import Chess_common.*;


public class Controller {
    @FXML private GridPane chessBoardView;
    @FXML private Pane main;
    private Board chessBoard = new Board();
    private Pane board[][] = new Pane[8][8];

    Tab tab = new Tab();
    @FXML ListView<String> listView;
    @FXML TextArea text; // TODO TextField?

    @FXML protected void onMouseClick(MouseEvent event){
        for(Node node : chessBoardView.getChildren()){
            if (node instanceof Pane){
                Pane pane = (Pane) node;
                board[Character.getNumericValue(pane.getId().charAt(4))][Character.getNumericValue(pane.getId().charAt(6))] = pane;
            }
        }

        double width = chessBoardView.getWidth();
        double height = chessBoardView.getHeight();

        int col = (int)(event.getX()/width*8);
        int row = (int)(event.getY()/height*8);
        //System.out.println(row + " : " + col);


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
        tab.game = new Game();
    }

    @FXML protected void load(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(main.getScene().getWindow());
        System.out.println(file);
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

        System.out.println(lines);


        String all_moves = "1. Jc3 a5\n" +
                "2. Jd5 J8f6\n" +
                "3. Jxf6+ c7c6\n" +
                "4. Dxa5 f7f5\n";

    }

    @FXML protected void save(ActionEvent event){

    }


    @FXML protected void gameType(ActionEvent event){
        // TODO
    }
}
