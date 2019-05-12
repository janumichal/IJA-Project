package Chess_pieces;

import enums.color_piece;
/**
 * Class for Piece
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class Piece {
    protected int x, y; // Position on board
    protected color_piece color = null; // Color of piece
    protected int value_of_piece; // value of certain piece

    /**
     * Class for Original piece
     * @param x Vertical location of piece.
     * @param y Horizontal location of piece.
     * @param value_of_piece Value of specific piece.
     * @param color Color of piece.
     */
    public Piece(int x, int y, int value_of_piece, color_piece color){
        this.x = x;
        this.y = y;
        this.value_of_piece = value_of_piece;
        this.color = color;
    }

    /**
     * Getter for piece value.
     * @return value of piece
     */
    public int getValue(){
        return this.value_of_piece;
    }

    /**
     * Getter for piece color.
     * @return color of piece
     */
    public color_piece getColor(){
        return color;
    }

    /**
     * Setter for column.
     * @param x number of column
     */
    public void setCol(int x) {
        this.x = x;
    }

    /**
     * Setter for row.
     * @param y number of row
     */
    public void setRow(int y) {
        this.y = y;
    }

    /**
     * Getter for column.
     * @return column number
     */
    public int getCol() { return this.x; }

    /**
     * Getter for row.
     * @return row number
     */
    public int getRow() {
        return this.y;
    }
}
