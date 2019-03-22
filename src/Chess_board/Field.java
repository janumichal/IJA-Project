package Chess_board;

import Chess_pieces.Piece;

import java.util.EnumMap;

public class Field implements Field_interface{
    int row;
    char column;

    private Piece piece_on_board = null;
    private EnumMap<Field_interface.Direction, Field> fields_around;

    public Field(int x, int y){
        fields_around = new EnumMap<>(Field_interface.Direction.class);
        setRow(x);
        setColumn(y);
    }

    public void addNextField(Field_interface.Direction direction, Field field){
        this.fields_around.put(direction, field);
    }


    public void setColumn(int index){
        int ascii_start = 65;
        this.column = (char)(ascii_start + index);
    }

    public void setRow(int index){
        this.row = index + 1;
    }


    // get piece placed in this field
    public Piece getPiece() {
        return this.piece_on_board;
    }

    // set piece on this field
    public void setPiece(Piece piece){
        this.piece_on_board = piece;
    }

    // check if field is empty
    public boolean isEmpty(){
        return this.piece_on_board == null;
    }

    // put piece on field if empty and return true if piece was successfuly placed
    public boolean putPiece(Piece piece){
        if(isEmpty()){
            setPiece(piece);
            return true;
        }else {
            return false;
        }
    }

    public boolean removePiece(Piece piece) {
        if(isEmpty()){
            return true;
        }else{
            if (this.piece_on_board == piece){
                this.piece_on_board = null;
                return true;
            }
            return false;
        }
    }


}
