package Chess_common;

import enums.color_piece;

public class Tab {
    Game game;

    public Tab() {
        this.game = new Game();
        String all_moves = "1. e4 b7b6\n" +
                "2. a4a5 Jb8a6\n" +
                "3. a5xb6 c7c6\n" +
                "4. b6b7 c6c5\n" +
                "5. b7b8D c5c4";

        this.game.loadAllMoves(all_moves);

        next();
        next();

        System.out.println();
        this.game.printAllMoves();


        this.game.board.showPiecesText();
    }

    public void next(){
        if(this.game.isAuto_mode()){
            this.game.next();
        }
    }

    public void prew(){
        if(this.game.isAuto_mode()){
            this.game.prew();
        }
    }

    public void undo(){
        this.game.undo();
    }

    public void redo(){
        this.game.redo();
    }

    public void move(Field from, Field to){
        this.game.setAuto_mode();
        this.game.move(from, to);
    }
}
