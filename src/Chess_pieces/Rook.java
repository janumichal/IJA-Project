package Chess_pieces;

import enums.color_piece;

public class Rook extends Piece {
    public Rook( int x, int y, color_piece color){
        super(x, y, 5, color);
    }

//    public boolean isMoveValid(int x, int y, String type){
//        return ((x == this.x && y != this.y) || (x != this.x && y == this.y));
//    }
}
