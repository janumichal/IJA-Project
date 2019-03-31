package Chess_common;

import Chess_pieces.Piece;

public class Move {
    private Field from;
    private Field to;
    private boolean is_take;
    private boolean is_check;
    private boolean is_mat;

    private boolean is_king;
    private boolean is_queen;
    private boolean is_bishop;
    private boolean is_knight;
    private boolean is_pawn;
    private boolean is_rook;

    private int column;
    private int row;

    private char exchange;

    public Move() {
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


    public void setMove(Field from, Field to){
        setFrom(from);
        setTo(to);
    }

    public char getExchange() {
        return exchange;
    }

    public void setExchange(char exchange) {
        this.exchange = exchange;
    }

    public void setFrom(Field from) {
        this.from = from;
    }

    public void setTo(Field to) {
        this.to = to;
    }

    public void setTake() {
        this.is_take = true;
    }

    public void setCheck() {
        this.is_check = true;
    }

    public void setMat() {
        this.is_mat = true;
    }

    public void setKing() {
        this.is_king = true;
    }

    public void setQueen() {
        this.is_queen = true;
    }

    public void setBishop() {
        this.is_bishop = true;
    }

    public void setKnight() {
        this.is_knight = true;
    }

    public void setPawn() {
        this.is_pawn = true;
    }

    public void setRook() {
        this.is_rook = true;
    }

    public Field getFrom() {
        return from;
    }

    public Field getTo() {
        return to;
    }

    public boolean isTake() {
        return is_take;
    }

    public boolean isCheck() {
        return is_check;
    }

    public boolean isMat() {
        return is_mat;
    }

    public boolean isKing() {
        return is_king;
    }

    public boolean isQueen() {
        return is_queen;
    }

    public boolean isBishop() {
        return is_bishop;
    }

    public boolean isKnight() {
        return is_knight;
    }

    public boolean isPawn() {
        return is_pawn;
    }

    public boolean isRook() {
        return is_rook;
    }

    public void setColumn(char column) {
        this.column = ((int)column - 97);
    }

    public void setRow(char row) {
        this.row = 8 - ((int)row - 48);
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
