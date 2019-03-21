package Chess_pieces;

import enums.color_piece;

public class Piece {
    private int x, y; // Position on board
    private color_piece color = null; // Color of piece
    private int value_of_piece; // value of certain piece

    public Piece(int x, int y, int value_of_piece, color_piece color){
        this.x = x;
        this.y = y;
        this.value_of_piece = value_of_piece;
        this.color = color;
    }


}
