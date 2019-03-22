package Chess_pieces;

import enums.color_piece;

public class Pawn extends Piece {
    private boolean is_on_starting_position = true;

    public Pawn(int x, int y, color_piece color){
        super(x, y, 1, color);
    }

    public boolean isMoveValid(int x, int y, String type){
        if (type.equals("move")){
            if(is_on_starting_position){
                return (y <= (this.y + 2) && y != this.y && this.x == x);
            }
            return (y <= (this.y + 1) && y != this.y && x == this.x);
        }else if (type.equals("take")){
            return (y <= (this.y + 1) && (x == this.x+1 || x == this.x-1));
        }
        return false;
    }

    public void setNotOnStart() {
        this.is_on_starting_position = false;
    }
}
