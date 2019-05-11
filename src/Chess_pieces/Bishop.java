package Chess_pieces;

import enums.color_piece;
/**
 * Class for Bishop
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class Bishop extends Piece {

    /**
     * Class for Bishop piece.
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param color Color of piece.
     */
    public Bishop(int x, int y, color_piece color){
        super(x, y, 3, color);
    }

    /**
     * Checks if move is valid for Bishop.
     * @param x Vertical location that piece has to move on.
     * @param y Horizontal location that piece has to move on.
     * @return Is valid.
     */
    public boolean isMoveValid(int x, int y){
        int array_size = 8;
        for (int i = 1; i <= array_size; i++){
            if ((x == this.x-i)&&(y == this.y-i)){
                return true;
            }else if((x == this.x-i)&&(y == this.y+i)){
                return true;
            }else if ((x == this.x+i)&&(y == this.y+i)){
                return true;
            }else if ((x == this.x+i)&&(y == this.y-i)){
                return true;
            }
        }
        return false;
    }
}
