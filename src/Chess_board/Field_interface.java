package Chess_board;

import Chess_pieces.Piece;

public interface Field_interface {
    Piece getPiece();
    void setPiece(Piece piece);

    boolean isEmpty();

    boolean putPiece(Piece piece);
    boolean removePiece(Piece piece);

    void setColumn(int index);
    void setRow(int index);

    void addNextField(Field_interface.Direction direction, Field field);

    enum Direction{
        LEFT_UP,
        UP,
        RIGHT_UP,
        RIGHT,
        RIGHT_DOWN,
        DOWN,
        LEFT_DOWN,
        LEFT;
    }
}
