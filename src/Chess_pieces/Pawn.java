package Chess_pieces;

import enums.color_piece;

public class Pawn extends Piece {
    private boolean is_on_starting_position = true;

    /**
     * Class for Pawn piece.
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param color Color of piece.
     */
    public Pawn(int x, int y, color_piece color){
        super(x, y, 1, color);
    }

    /**
     * Checks if move is valid for Pawn.
     * @param x Vertical location that piece has to move on.
     * @param y Horizontal location that piece has to move on.
     * @param target Pointer on piece on position (nullptr if empty)
     * @return Is Valid
     */
    public boolean isMoveValid(int x, int y, Piece target){
        if (this.color == color_piece.BLACK){
            if (target == null){
                if(is_on_starting_position){
                    setNotOnStart();
                    return (y <= (this.y + 2) && y > this.y && this.x == x);
                }
                return (y <= (this.y + 1) && y > this.y && x == this.x);
            }else{
                return (y <= (this.y + 1) && (x == this.x+1 || x == this.x-1));
            }
        }else{
            if (target == null){
                if(is_on_starting_position){
                    setNotOnStart();
                    return (this.y <= (y + 2)  && this.y > y && this.x == x); // CHECKED
                }
                return (this.y == (y + 1) && this.y > y  && x == this.x); // CHECKED
            }else{
                return (this.y == (y + 1) && (x+1 == this.x || x-1 == this.x)); // CHECKED
            }
        }
    }

    /**
     * Checks if piece is on starting position.
     * @return Is on starting position.
     */
    public boolean getIsOnStart(){
        return this.is_on_starting_position;
    }

    /**
     * Sets is_on_starting_position as false.
     */
    private void setNotOnStart() {
        this.is_on_starting_position = false;
    }
}
