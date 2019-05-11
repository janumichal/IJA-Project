package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;

import Chess_common.*;



public class Controller {
<<<<<<< HEAD
    @FXML private GridPane chessBoard;
    private Board board = new Board();


    Tab tab = new Tab();

    @FXML TextArea text; // TODO TextField?

    @FXML protected void onMouseClick(){

    }


=======
    Tab tab = new Tab();

>>>>>>> 920681553b98769f5d2658a6a06ed863e771135d
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
<<<<<<< HEAD
        tab.game = new Game();
        //Tab.game.newGame();
    }

    @FXML protected void load(ActionEvent event){
        String all_moves = "1. Jc3 a5\n" +
                "2. Jd5 J8f6\n" +
                "3. Jxf6+ c7c6\n" +
                "4. Dxa5 f7f5\n";
        text.appendText(all_moves);
    }

    @FXML protected void save(ActionEvent event){
        tab.
=======
        tab.newGame();
        tab.start_auto();
    }

    @FXML protected void load(ActionEvent event){
//        tab.loadAllMoves(value);
>>>>>>> 920681553b98769f5d2658a6a06ed863e771135d
    }

    @FXML protected void gameType(ActionEvent event){
        // TODO
    }
}
