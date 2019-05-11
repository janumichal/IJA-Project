package Chess_pieces;

import enums.color_piece;
/**
 * Class for Rook
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class Rook extends Piece {
    /**
     * Class for Rook piece.
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param color Color of piece.
     */
    public Rook( int x, int y, color_piece color){
        super(x, y, 5, color);
    }

    /**
     * Checks if move is valid for Rook.
     * @param x Vertical location that piece has to move on.
     * @param y Horizontal location that piece has to move on.
     * @return Is valid.
     */
    public boolean isMoveValid(int x, int y){
        return ((x == this.x && y != this.y) || (x != this.x && y == this.y));
    }
}
