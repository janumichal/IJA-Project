package Chess_common;

import enums.color_piece;

public class Tab {
    Game game;
    boolean replay_mode;
    String loaded_game;

    public Tab() {
        this.game = new Game();
//        move("a2a4");
//        move("b7b6");
//        move("a4a5");
//        move("b6a5");
//        move("a5a6");
//        move("a6b7");
        String all_moves = "1. a1xa1V+ b7b6\n";
        this.game.loadAllMoves(all_moves);


    }
}
