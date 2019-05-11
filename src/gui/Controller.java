package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;

import Chess_common.*;



public class Controller {
    @FXML private GridPane chessBoard;
    private Board board = new Board();


    Tab tab = new Tab();

    @FXML TextArea text; // TODO TextField?

    @FXML protected void onMouseClick(){

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
        String all_moves = "1. Jc3 a5\n" +
                "2. Jd5 J8f6\n" +
                "3. Jxf6+ c7c6\n" +
                "4. Dxa5 f7f5\n";
        text.appendText(all_moves);
    }

    @FXML protected void save(ActionEvent event){

    }


    @FXML protected void gameType(ActionEvent event){
        // TODO
    }
}
