package objects;

import enums.*;

public class chess_piece{
    private chess_type type = null;
    private color_piece color = null;
    private int value_of_piece;

//    CONSTRUCTOR
    public chess_piece(chess_type type, color_piece color){
        this.color = color;
        this.type = type;
            switch (type){
                case KING:
                    this.value_of_piece = 0;
                    break;
                case QUEEN:
                    this.value_of_piece = 9;
                    break;
                case BISHOP:
                    this.value_of_piece = 5;
                    break;
                case KNIGHT:
                    this.value_of_piece = 3;
                    break;
                case ROOK:
                    this.value_of_piece = 3;
                    break;
                case PAWN:
                    this.value_of_piece = 1;
                    break;
        }
    }

//    GETTERS
    public chess_type getType() {
        return type;
    }
    public color_piece getColor() {
        return color;
    }

    public int getValue_of_piece() {
        return value_of_piece;
    }

    //    SETTERS
    public void setColor(color_piece color) {
        this.color = color;
    }
    public void setType(chess_type type) {
        this.type = type;
    }

    public void setValue_of_piece(int value_of_piece) {
        this.value_of_piece = value_of_piece;
    }

    public char get_sign_from_chess_type(chess_type type){
        switch (type){
            case KING:
                return 'K';
            case QUEEN:
                return 'Q';
            case BISHOP:
                return 'B';
            case KNIGHT:
                return 'K';
            case ROOK:
                return 'R';
            case PAWN:
                return 'p';
        }
        return '%';
    }

}
