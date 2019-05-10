package Chess_pieces;

import enums.color_piece;

public class Queen extends Piece {
    /**
     * Class for Queen piece.
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param color Color of piece.
     */
    public Queen(int x, int y, color_piece color){
        super(x, y, 9, color);
    }

    /**
     * Checks if move is valid for Queen.
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
            }else if (((x == this.x+i)&&(y == this.y-i))){
                return true;
            }
        }
        return ((x == this.x && y != this.y) || (x != this.x && y == this.y));
    }
}
