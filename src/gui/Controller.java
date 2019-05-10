package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import Chess_common.*;



public class Controller {
    Tab tab = new Tab();

    @FXML protected void stopGame(ActionEvent event){
        // TODO
    }

    @FXML protected void undo(ActionEvent event){
        tab.undo();
    }

    @FXML protected void redo(ActionEvent event){
        Tab.game.redo();
    }

    @FXML protected void restart(ActionEvent event){
        tab.newGame();
        tab.start_auto();
    }

    @FXML protected void load(ActionEvent event){
//        tab.loadAllMoves(value);
    }

    @FXML protected void gameType(ActionEvent event){
        // TODO
    }
}
