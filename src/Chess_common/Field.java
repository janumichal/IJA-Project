/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import Chess_pieces.Piece;

import java.util.EnumMap;

public class Field implements Field_interface{
    private int row;
    private int col;

    private Piece piece_on_board = null;
    private EnumMap<Field_interface.Direction, Field> fields_around;

    /**
     * Creates instance of Field.
     * @param x Vertical position of Field.
     * @param y Horizontal position of Field.
     */
    public Field(int x, int y){
        fields_around = new EnumMap<>(Field_interface.Direction.class);
        setCol(x);
        setRow(y);
    }

    /**
     * Gets Field in specific direction on board.
     * @param direction Diretion to go in.
     * @return Field in direction.
     */
    public Field nextField(Field_interface.Direction direction){
        return this.fields_around.get(direction);
    }

    /**
     * Adds Field to enum map in direction.
     * @param direction direction to set.
     * @param field Field to set in direction.
     */
    public void addNextField(Field_interface.Direction direction, Field field){
        this.fields_around.put(direction, field);
    }

    /**
     * Setter for column.
     * @param col Column that is being set.
     */
    public void setCol(int col){
        this.col = col;
    }

    /**
     * Setter for row.
     * @param col Row that is being set.
     */
    public void setRow(int row){
        this.row = row;
    }

    /**
     * Getter for row.
     * @return row.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Getter for column.
     * @return column.
     */
    public int getCol() {
        return this.col;
    }

    // get piece placed in this field
    /**
     * Getter for piece in field.
     * @return Pointer on piece.
     */
    public Piece getPiece() {
        return this.piece_on_board;
    }

    // set piece on this field
    /**
     * Setter for piece in field.
     * @param piece Piece that is being set.
     */
    public void setPiece(Piece piece){
        this.piece_on_board = piece;
    }

    // check if field is empty
    /**
     * Checks if field is empty
     * @return is Empty.
     */
    public boolean isEmpty(){
        return this.piece_on_board == null;
    }

    // put piece on field if empty and return true if piece was successfuly placed
    /**
     * Puts piece in field.
     * @param piece Piece that is being put.
     */
    public void putPiece(Piece piece){
        removePiece();
        if (piece != null){
            piece.setCol(this.getCol());
            piece.setRow(this.getRow());
        }
        setPiece(piece);
    }

    /**
     * @brief Removes Piece from field.
     * @return Pointer on piece that has been removed from field.
     */
    public Piece removePiece() {
        Piece piece = this.piece_on_board;
        this.piece_on_board = null;
        return piece;
    }

}
