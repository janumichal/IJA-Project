/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import enums.color_piece;
import javafx.scene.control.Alert;

/**
 * Class for Tab
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class Tab {
    public Game game;

    /**
     * Creates instance of Tab.
     */
    public Tab() {
        this.game = new Game();
        newGame();
    }

    /**
     * Next move in Auto mode.
     */
    public void next(){
        if(this.game.isAuto_mode()){
            this.game.next();
        }
    }

    /**
     * Preview move in Auto mode.
     */
    public void prew(){
        if(this.game.isAuto_mode()){
            this.game.prew();
        }
    }

    /**
     * Undo move in Manual mode.
     */
    public void undo(){
        if(!this.game.isAuto_mode()){
            this.game.undo();
        }
    }

    /**
     * Redo move in Manual mode
     */
    public void redo(){
        if(!this.game.isAuto_mode()){
            this.game.redo();
        }
    }

    /**
     * moves piece from field from to field to.
     * @param from Moves from.
     * @param to Moves to.
     */
    public void move(Field from, Field to){
        if (from.getPiece() == null){
            System.out.println("NO PIECE TO MOVE!!!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("NO PIECE TO MOVE!!!");
            alert.showAndWait();
        }else{
            this.game.setAuto_modeOFF();
            this.game.move(from, to);
        }
    }

    /**
     * starts Automatic mode.
     */
    public void start_auto(){ // prehrava kroky postupně z loaded_moves
        this.game.setAuto_modeON();
        this.game.setIndex(0);
    }

    /**
     * Loads all moves from string
     * @param moves_input string moves are being load from.
     */
    public void loadAllMoves(String moves_input){
        this.game.loadAllMoves(moves_input);
    }

    /**
     * Creates new game.
     */
    public void newGame(){
        this.game.setIndex(0);
        this.game.board.white_on_move = true;
        this.game.board.cleanBoard();
        this.game.board.fillBoard();
    }

}

