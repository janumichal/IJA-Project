package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import Chess_common.*;



public class Controller {
    @FXML protected void stopGame(ActionEvent event){
        // TODO
    }

    @FXML protected void undo(ActionEvent event){
        Tab.game.undo();
    }

    @FXML protected void redo(ActionEvent event){
        Tab.game.redo();
    }

    @FXML protected void restart(ActionEvent event){
        Tab.game = new Game();
        //Tab.game.newGame();
    }

    @FXML protected void load(ActionEvent event){
    }

    @FXML protected void gameType(ActionEvent event){
        // TODO
    }
}
