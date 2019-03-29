package Chess_common;

import enums.color_piece;

public class Tab {
    Game game;
    boolean replay_mode;
    String loaded_game;

    public Tab(String loaded_game) {
        this.game = new Game();
        this.replay_mode = loaded_game != null && !loaded_game.equals("");
    }
}
