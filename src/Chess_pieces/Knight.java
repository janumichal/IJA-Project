package Chess_pieces;

import enums.color_piece;

public class Knight extends Piece {
    /**
     * Class for Knight piece.
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param color Color of piece.
     */
    public Knight(int x, int y, color_piece color){
        super(x, y, 3, color);
    }

    /**
     * Checks if move is valid for Knight.
     * @param x Vertical location that piece has to move on.
     * @param y Horizontal location that piece has to move on.
     * @return Is valid.
     */
    public boolean isMoveValid(int x, int y){
        if(x > this.x){
            if(y > this.y){ // LEFT DOWN
                return ((x == (this.x+1) && y == (this.y+2)) || (x == (this.x+2) && y == (this.y+1)));
            }else if (y < this.y){ //LEFT UP
                return ((x == (this.x+1) && y == (this.y-2)) || (x == (this.x+2) && y == (this.y-1)));
            }
            return false;
        }else if (x < this.x){
            if(y > this.y){ // RIGHT DOWN
                return ((x == (this.x-1) && y == (this.y+2)) || (x == (this.x-2) && y == (this.y+1)));
            }else if (y < this.y){ //RIGHT UP
                return ((x == (this.x-1) && y == (this.y-2)) || (x == (this.x-2) && y == (this.y-1)));
            }
            return false;
        }
        return false;
    }
}
