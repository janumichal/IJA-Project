package Chess_pieces;

import enums.color_piece;

public class King extends Piece {
    /**
     * Checks if move is valid for King.
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param color Color of piece.
     */
    public King(int x, int y, color_piece color){
        super(x, y, 0, color);
    }

    /**
     * Checks if move is valid for King.
     * @param x Vertical location that piece has to move on.
     * @param y Horizontal location that piece has to move on.
     * @return Is valid.
     */
    public boolean isMoveValid(int x, int y){
        return (!(x == this.x && y == this.y)) && ((( this.x == x+1 ) || ( this.x == x-1 ) || (this.x == x && this.y != y)) && (( this.y == y-1 ) || ( this.y == y+1 ) || (this.y == y && this.x != x)) );
    }
}
