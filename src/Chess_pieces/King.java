package Chess_pieces;

import enums.color_piece;

public class King extends Piece {
    public King(int x, int y, color_piece color){
        super(x, y, 0, color);
    }

//    public boolean isMoveValid(int x, int y, String type){
//        return (x != this.x && y != this.y) && ((( (this.x-1) <= x ) || ( x <= (this.x+1) )) && (( (this.y-1) <= y ) || ( y <= (this.y+1) )) );
//    }
}
