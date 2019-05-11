/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import enums.color_piece;

/**
 * Class for Tab
 */
public class Tab {
    public static Game game;

    /**
     * Creates instance of Tab.
     */
    public Tab() {
        this.game = new Game();
        newGame();
    }
//        String all_moves = "1. Jc3 a5\n" +
//                "2. Jd5 J8f6\n" +
//                "3. Jxf6+ c7c6\n" +
//                "4. Dxa5 f7f5\n";
//
//        loadAllMoves(all_moves);
//        newGame();
//        start_auto();
//
//        next();
//        next();
//
//        next();
//        next();
//
//        Field f1 = this.game.board.getField(3,3);
//        Field f2 = this.game.board.getField(5,2);
//        move(f1, f2);
//
//        f1 = this.game.board.getField(1,1);
//        f2 = this.game.board.getField(1,3);
//        move(f1, f2);
//
//        newGame();
//        start_auto();
//
//        next();
//        next();
//
//        next();
//        next();
//
//        next();
//        next();
//
////        f1 = this.game.board.getField(5,2);
////        f2 = this.game.board.getField(4,0);
////        move(f1, f2);
//
////        next();
////
////        next();
////        next();
////
////        next();
////        next();
////
////        next();
//
//
////        Field f1 = this.game.board.getField(2,1);
////        Field f2 = this.game.board.getField(2,3);
////        move(f1,f2);
//
//
//        this.game.board.printPoints();
//
//        System.out.println();
//        this.game.printAllMoves();
//
//
//        this.game.board.showPiecesText();
//    }

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
            System.out.println("NO PIECE TO MOVE!!!"); //TODO POPUP
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
        this.game.board.cleanBoard();
        this.game.board.fillBoard();
    }

}


