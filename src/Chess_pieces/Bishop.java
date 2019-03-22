package Chess_pieces;

import enums.color_piece;

public class Bishop extends Piece {
    public Bishop(int x, int y, color_piece color){
        super(x, y, 3, color);
    }

    public boolean isMoveValid(int x, int y, String type){
        boolean is_valid = false;
        int pos_x = x;
        int pos_y = 0;
        int array_size = 8;
        for (int i = 0; i < array_size; i++){

        }
        return is_valid;
    }
}
