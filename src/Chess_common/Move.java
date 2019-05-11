/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import enums.color_piece;

/**
 * Class for Move
 */
public class Move {
    private Field from;
    private Field to;
    private boolean is_take;
    private boolean is_check;
    private boolean is_mat;
    private color_piece color;

    private boolean is_king;
    private boolean is_queen;
    private boolean is_bishop;

    private boolean is_knight;
    private boolean is_pawn;
    private boolean is_rook;

    private int column;
    private int row;

    private char exchange;

    /**
     * Creates instance for move.
     */
    public Move() {
        this.column = -1;
        this.row = -1;
        this.is_take = false;
        this.is_check = false;
        this.is_mat = false;

        this.is_knight = false;
        this.is_king = false;
        this.is_queen = false;
        this.is_bishop = false;
        this.is_pawn = false;
        this.is_rook = false;
    }

    /**
     * Setter of color in move.
     * @param color Color to set.
     */
    public void setColor(color_piece color) {
        this.color = color;
    }

    /**
     * Getter for color in move.
     * @return Color in move.
     */
    public color_piece getColor() {
        return this.color;
    }

    /**
     * Prints move in valid format.
     * @return Move in valid format.
     */
    public String pritnMove(){
        StringBuffer output = new StringBuffer();
        if (isKing()){
            output.append('K');
        }else if (isQueen()){
            output.append('D');
        }else if (isKnight()){
            output.append('J');
        }else if (isBishop()){
            output.append('S');
        }else if (isRook()){
            output.append('V');
        }

        if (this.from != null){
            char x = (char)(this.from.getCol() + 97);
            char y = (char)(8 - this.from.getRow() + 48);
            output.append(x);
            output.append(y);

        }else {
            if (this.column != -1){
                char znak = (char)(this.column + 97); // COLUMN WRONG
                output.append(znak);
            }else if (this.row != -1){
                char znak = (char)(8 - this.row + 48); // COLUMN WRONG
                output.append(znak);
            }
        }

        if (isTake()){
            output.append('x');
        }

        char x = (char)(this.to.getCol() + 97);
        char y = (char)(8 - this.to.getRow() + 48);
        output.append(x);
        output.append(y);
        if (this.getExchange() != '\0'){
            output.append(this.getExchange());
        }
        if (isMat()){
            output.append('#');
        }else if (isCheck()){
            output.append('+');
        }
        return output.toString();
    }

    /**
     * Getter for exchange.
     * @return Character representing piece exchange for.
     */
    public char getExchange() {
        return this.exchange;
    }

    /**
     * Setter for exchange.
     * @param exchange Sets exchange character.
     */
    public void setExchange(char exchange) {
        this.exchange = exchange;
    }

    /**
     * Setter for field from in move.
     * @param from Field piece is moving from.
     */
    public void setFrom(Field from) {
        this.from = from;
    }

    /**
     * Setter for field to in move
     * @param to Field piece is moving to.
     */
    public void setTo(Field to) {
        this.to = to;
    }

    /**
     * Setter for take in move.
     */
    public void setTake() {
        this.is_take = true;
    }

    /**
     * Setter for check in move.
     */
    public void setCheck() {
        this.is_check = true;
    }

    /**
     * Setter for mat in move.
     */
    public void setMat() {
        this.is_mat = true;
    }

    /**
     * Setter for King in move.
     */
    public void setKing() {
        this.is_king = true;
    }

    /**
     * Setter for Queen in move.
     */
    public void setQueen() {
        this.is_queen = true;
    }

    /**
     * Setter for Bishop in move.
     */
    public void setBishop() {
        this.is_bishop = true;
    }

    /**
     * Setter for Knight in move.
     */
    public void setKnight() {
        this.is_knight = true;
    }

    /**
     * Setter for Pawn in move.
     */
    public void setPawn() {
        this.is_pawn = true;
    }

    /**
     * Setter for Rook in move.
     */
    public void setRook() {
        this.is_rook = true;
    }

    /**
     * getter for from in move
     * @return Field piece move from.
     */
    public Field getFrom() {
        return this.from;
    }

    /**
     * getter for to in move
     * @return Field piece move to.
     */
    public Field getTo() {
        return this.to;
    }

    /**
     * @return Move is take.
     */
    public boolean isTake() {
        return this.is_take;
    }

    /**
     * @return Move is check.
     */
    public boolean isCheck() {
        return this.is_check;
    }

    /**
     * @return Move is mat.
     */
    public boolean isMat() {
        return this.is_mat;
    }

    /**
     * @return Move is King.
     */
    public boolean isKing() {
        return this.is_king;
    }

    /**
     * @return Move is Queen.
     */
    public boolean isQueen() {
        return this.is_queen;
    }

    /**
     * @return Move is Bishop.
     */
    public boolean isBishop() {
        return this.is_bishop;
    }

    /**
     * @return Move is Knight.
     */
    public boolean isKnight() {
        return this.is_knight;
    }

    /**
     * @return Move is Pawn.
     */
    public boolean isPawn() {
        return this.is_pawn;
    }

    /**
     * @return Move is Rook.
     */
    public boolean isRook() {
        return this.is_rook;
    }

    /**
     * Setter for column in move in character form.
     * @param column Column on board.
     */
    public void setColumn(char column) {
        this.column = ((int)column - 97);
    }

    /**
     * Setter for column in move in numeric form.
     * @param column Column on board.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Setter for row in move in character form.
     * @param row Row on board.
     */
    public void setRow(char row) {
        this.row = 8 - ((int)row - 48);
    }

    /**
     * Setter for row in move in numeric form.
     * @param row Row on board.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter for column in move.
     * @return column
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Getter for row in move.
     * @return row
     */
    public int getRow() {
        return this.row;
    }
}
