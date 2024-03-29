/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */

package Chess_common;

import Chess_pieces.*;
import enums.color_piece;
import javafx.scene.control.Alert;

/**
 * Class for Board
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class Board {
    private static final int BOARD_SIZE = 8;
    public boolean valid = true;
    private Field[][] board_array;
    private HistoryItem historyItem;
    public boolean white_on_move;
    private int white_points;
    private int black_points;
    public boolean game_end = false;
    public boolean validation = true;

    /**
     * Check if is white on move.
     * @return if white on move.
     */
    public boolean is_white_on_move() {
        return this.white_on_move;
    }

    /**
     * Prints out the points
     * @return points
     */
    public String printPoints(){
        return "White: " + getWhite_points() + " Black: " + getBlack_points();
    }

    // constructor creates array and fills it with pieces
    /**
     * Creates instance of board.
     */
    public Board(){
        this.white_on_move = true;
        this.white_points = 0;
        this.black_points = 0;

        board_array = new Field[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++){
            for (int y = 0; y < BOARD_SIZE; y++){
                Field field = new Field(x, y);
                board_array[x][y] = field;
            }
        }
        fillArrayAround();
    }

    //################################################# FIELD WORK #####################################################
    /**
     * get field from 2D array on position [x][y]
     * @param x Vertical position
     * @param y Horizontal position
     * @return field on position
     */
    public Field getField(int x, int y){
        return this.board_array[x][y];
    }

    // ################################################# CORDINATES MOVE ###############################################
    // moves piece from position1 to position2
    /**
     * moves piece from to to and saves move to history
     * @param from field from
     * @param to field to
     * @return history item
     */
    public HistoryItem movePiece(Field from, Field to){

        Piece player = from.getPiece();
        Piece target = to.getPiece();
        if (player != null){
            if (is_white_on_move()){
                if (player.getColor() == color_piece.BLACK){
                    System.out.println("WHITE IS ON MOVE");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("WHITE IS ON MOVE");
                    alert.showAndWait();
                    return null;
                }
            }else {
                if (player.getColor()== color_piece.WHITE){
                    System.out.println("BLACK IS ON MOVE");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("BLACK IS ON MOVE");
                    alert.showAndWait();
                    return null;
                }
            }
        }


        if (player instanceof Pawn){ // MOVE PAWN
            if(((Pawn) player).isMoveValid(to.getCol(), to.getRow(), target)){
                movePawn(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("IS NOT VALID MOVE");
                alert.showAndWait();
                return null;
            }
        }else if(player instanceof Rook){ // MOVE ROOK
            if (((Rook) player).isMoveValid(to.getCol(), to.getRow())){
                moveRook(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("IS NOT VALID MOVE");
                alert.showAndWait();
                return null;
            }
        }else if (player instanceof Knight){ // MOVE KNIGHT
            if (((Knight) player).isMoveValid(to.getCol(), to.getRow())){
                moveKnight(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("IS NOT VALID MOVE");
                alert.showAndWait();
                return null;
            }
        }else if(player instanceof Bishop){ // MOVE BISHOP
            if (((Bishop) player).isMoveValid(to.getCol(), to.getRow())){
                moveBishop(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("IS NOT VALID MOVE");
                alert.showAndWait();
                return null;
            }
        }else if(player instanceof King){
            if (((King) player).isMoveValid(to.getCol(), to.getRow())){
                moveKing(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("IS NOT VALID MOVE");
                alert.showAndWait();
                return null;
            }
        }else if(player instanceof Queen){
            if (((Queen) player).isMoveValid(to.getCol(), to.getRow())){
                moveQueen(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("IS NOT VALID MOVE");
                alert.showAndWait();
                return null;
            }
        }else{
            System.out.println("NO movable piece avalible");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("NO movable piece avalible");
            alert.showAndWait();
            return null;
        }
        return this.historyItem;
    }

    // ############################################ PIECE MOVES ########################################################
    // BISHOP
    /**
     * moves Bishop from field from to field to
     * @param from field from
     * @param to field to
     */
    private void moveBishop(Field from, Field to){
        if (from.getCol() < to.getCol() && from.getRow() < to.getRow()){ // GO RIGHT DOWN
            moveInDir(Field_interface.Direction.RIGHT_DOWN, from, to);
        }else if (from.getCol() < to.getCol() && from.getRow() > to.getRow()){ // GO RIGHT UP
            moveInDir(Field_interface.Direction.RIGHT_UP, from, to);
        }else if (from.getCol() > to.getCol() && from.getRow() < to.getRow()){ // GO LEFT DOWN
            moveInDir(Field_interface.Direction.LEFT_DOWN, from, to);
        }else if (from.getCol() > to.getCol() && from.getRow() > to.getRow()){ // GO LEFT UP
            moveInDir(Field_interface.Direction.LEFT_UP, from, to);
        }
    }

    //Queen
    /**
     * moves Queen from field from to field to
     * @param from field from
     * @param to field to
     */
    private void moveQueen(Field from, Field to){
        if (from.getCol() < to.getCol() && from.getRow() < to.getRow()){ // GO RIGHT DOWN
            moveInDir(Field_interface.Direction.RIGHT_DOWN, from, to);
        }else if (from.getCol() < to.getCol() && from.getRow() > to.getRow()){ // GO RIGHT UP
            moveInDir(Field_interface.Direction.RIGHT_UP, from, to);
        }else if (from.getCol() > to.getCol() && from.getRow() < to.getRow()){ // GO LEFT DOWN
            moveInDir(Field_interface.Direction.LEFT_DOWN, from, to);
        }else if (from.getCol() > to.getCol() && from.getRow() > to.getRow()){ // GO LEFT UP
            moveInDir(Field_interface.Direction.LEFT_UP, from, to);
        }else if (from.getCol() < to.getCol() && from.getRow() == to.getRow()){ // GO RIGHT
            moveInDir(Field_interface.Direction.RIGHT, from, to);
        }else if (from.getCol() > to.getCol() && from.getRow() == to.getRow()){ // GO LEFT
            moveInDir(Field_interface.Direction.LEFT, from, to);
        }else if (from.getRow() < to.getRow() && from.getCol() == to.getCol()){ // GO DOWN
            moveInDir(Field_interface.Direction.DOWN, from, to);
        }else if (from.getRow() > to.getRow() && from.getCol() == to.getCol()){ // GO UP
            moveInDir(Field_interface.Direction.UP, from, to);
        }
    }

    //King
    /**
     * moves King from field from to field to
     * @param from field from
     * @param to field to
     */
    private void moveKing(Field from, Field to){
        move(from, to);
    }

    // ROOK
    /**
     * moves Rook from field from to field to
     * @param from field from
     * @param to field to
     */
    private void moveRook(Field from, Field to){
        if (from.getCol() < to.getCol()){ // GO RIGHT
            moveInDir(Field_interface.Direction.RIGHT, from, to);
        }else if (from.getCol() > to.getCol()){ // GO LEFT
            moveInDir(Field_interface.Direction.LEFT, from, to);
        }else if (from.getRow() < to.getRow()){ // GO DOWN
            moveInDir(Field_interface.Direction.DOWN, from, to);
        }else if (from.getRow() > to.getRow()){ // GO UP
            moveInDir(Field_interface.Direction.UP, from, to);
        }
    }

    /**
     * moves piece in direction
     * @param direction direction to go to
     * @param from field from
     * @param to field to
     */
    private void moveInDir(Field_interface.Direction direction, Field from, Field to){
            Field pointer = from.nextField(direction);
            boolean free_path = true;

            while (to != pointer){
                if(pointer.getPiece() != null){
                    free_path = false;
                    break;
                }else{
                    pointer = pointer.nextField(direction);
                }
            }

            if (free_path){
                move(from, to);
            }else {
                System.out.println("NOT FREE PATH");
            }

    }

    // PAWN
    /**
     * moves Pawn from field from to field to
     * @param from field from
     * @param to field to
     */
    private void movePawn(Field from, Field to){
        if (from.getPiece().getColor() == color_piece.WHITE){
            if (to.getPiece() == null){
                moveInDir(Field_interface.Direction.UP, from, to);
            }else {
                move(from, to);
            }
        }else{
            if (to.getPiece() == null){
                Field pointer = from.nextField(Field_interface.Direction.DOWN);
                boolean free_path = true;

                while (to != pointer){
                    if(pointer.getPiece() != null){
                        free_path = false;
                        break;
                    }else{
                        pointer = pointer.nextField(Field_interface.Direction.DOWN);
                    }
                }

                if (free_path){
                    move(from, to);
                }else {
                    System.out.println("NOT FREE PATH");
                }
            }else {
                move(from, to);
            }
        }
    }

    // Knight
    /**
     * moves Knight from field from to field to
     * @param from field from
     * @param to field to
     */
    private void moveKnight(Field from, Field to){
        move(from, to);
    }

    /**
     * move from field from to field to
     * @param from field from
     * @param to field to
     */
    private void move(Field from, Field to){
        if (!to.isEmpty()){
            if(from.getPiece().getColor() == to.getPiece().getColor()){
                System.out.println("CANT ATTACK SAME COLOR");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("CANT ATTACK SAME COLOR");
                alert.showAndWait();
                this.validation = false;
            }else{
                Piece piece_to;
                Piece piece_from = from.removePiece();
                piece_to = to.removePiece();
                if (piece_to.getColor() == color_piece.WHITE){
                    this.black_points += piece_to.getValue();
                }else{
                    this.white_points += piece_to.getValue();
                }
                to.putPiece(piece_from);
                this.historyItem = new HistoryItem(from, to, piece_to);
                this.white_on_move = !this.white_on_move;
                if(piece_to instanceof King){
                    System.out.println("GAME ENDS KING TAKEN");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("GAME ENDS KING TAKEN");
                    alert.showAndWait();
                    this.game_end = true;
                }
            }
        }else{
            Piece piece_from = from.removePiece();
            to.putPiece(piece_from);
            this.historyItem = new HistoryItem(from, to, null);
            this.white_on_move = !this.white_on_move;
        }
    }

    /**
     * applayes history item and specified move
     * @param from field from
     * @param to field to
     * @param target piece that has been taken (null if doesnt exist)
     * @param exchange for what was exchenged (empty if not)
     */
    public void moveHistory(Field from, Field to, Piece target, Piece exchange){
        Piece piece = to.removePiece();
        if (exchange == null){
            from.putPiece(piece);
        }else{
            if (piece instanceof Pawn){
                from.putPiece(exchange);
            }else {
                from.putPiece(new Pawn(from.getCol(), from.getRow(), piece.getColor()));
            }
        }
        to.putPiece(target);
        this.white_on_move = !this.white_on_move;
    }

    //############################################# CREATING BOARD #####################################################
    // show piece placement in text mode
    /**
     * Displays piece placement in console
     */
    public void showPiecesText(){
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (int y = 0; y < BOARD_SIZE; y++){
            System.out.print("|");
            for (int x = 0; x < BOARD_SIZE; x++){
                Piece piece = this.board_array[x][y].getPiece();
                if(piece instanceof Rook){
                    System.out.print("  Rook (V)  ");
                }else if(piece instanceof Knight){
                    System.out.print(" Knight (J) ");
                }else if(piece instanceof Bishop){
                    System.out.print(" Bishop (S) ");
                }else if(piece instanceof King){
                    System.out.print("  King (K)  ");
                }else if(piece instanceof Queen){
                    System.out.print(" Queen (D)  ");
                }else if(piece instanceof Pawn){
                    System.out.print("  pawn (p)  ");
                }else{
                    System.out.print("            ");
                }
                if (x == BOARD_SIZE-1){
                    System.out.println("|");
                }else {
                    System.out.print("|");
                }
            }
            System.out.println("---------------------------------------------------------------------------------------------------------");
        }
    }

    // fill board with B/W pieces
    /**
     * fills board on starting point
     */
    public void fillBoard(){
        placePieces(color_piece.BLACK);
        placePieces(color_piece.WHITE);
    }

    /**
     * cleans board of everything
     */
    public void cleanBoard(){
        for (int x = 0; x < BOARD_SIZE; x++){
            for (int y = 0; y < BOARD_SIZE; y++){
                this.board_array[x][y].putPiece(null);
            }
        }
    }

    // placement of pieces
    /**
     * places pieces of specified color
     * @param color specified color
     */
    private void placePieces(color_piece color){
        boolean is_black = color == color_piece.BLACK;
        int y = is_black ? 0 : BOARD_SIZE-1;
        for (int x = 0; x < BOARD_SIZE; x++){
            // place PAWN
            this.board_array[x][is_black ? y+1 : y-1].putPiece(new Pawn(x, is_black ? y+1 : y-1, color));
            switch (x){
                case 0:
                case (BOARD_SIZE-1):
                    // place ROOKS
                    this.board_array[x][y].putPiece(new Rook(x, y, color));
                    break;
                case 1:
                case (BOARD_SIZE-2):
                    // place KNIGHTS
                    this.board_array[x][y].putPiece(new Knight(x, y, color));
                    break;
                case 2:
                case (BOARD_SIZE-3):
                    //place BISHOPS
                    this.board_array[x][y].putPiece(new Bishop(x, y, color));
                    break;
                case 3:
                    // place KING
                    this.board_array[x][y].putPiece(new Queen(x, y, color));
                    break;
                case (BOARD_SIZE-4):
                    // place QUEEN
                    this.board_array[x][y].putPiece(new King(x, y, color));
                    break;
            }
        }
    }

    // fills array around in seperate field across whole board
    /**
     * fills enum map in field
     */
    private void fillArrayAround(){
        for (int x = 0; x < BOARD_SIZE; x++){
            for (int y = 0; y < BOARD_SIZE; y++){
                Field actual_field = this.board_array[x][y];

                for (int k = 0;k < 8;k++){
                    int x1;
                    int y1;
                    switch (k){
                        case 0:
                            x1 = x-1;
                            y1 = y-1;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.LEFT_UP, this.board_array[x1][y1]);
                            }
                            break;
                        case 1:
                            x1 = x;
                            y1 = y-1;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.UP, this.board_array[x1][y1]);
                            }
                            break;
                        case 2:
                            x1 = x+1;
                            y1 = y-1;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.RIGHT_UP, this.board_array[x1][y1]);
                            }
                            break;
                        case 3:
                            x1 = x+1;
                            y1 = y;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.RIGHT, this.board_array[x1][y1]);
                            }
                            break;
                        case 4:
                            x1 = x+1;
                            y1 = y+1;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.RIGHT_DOWN, this.board_array[x1][y1]);
                            }
                            break;
                        case 5:
                            x1 = x;
                            y1 = y+1;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.DOWN, this.board_array[x1][y1]);
                            }
                            break;
                        case 6:
                            x1 = x-1;
                            y1 = y+1;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.LEFT_DOWN, this.board_array[x1][y1]);
                            }
                            break;
                        case 7:
                            x1 = x-1;
                            y1 = y;
                            if (isInArray(x1,y1)){
                                actual_field.addNextField(Field_interface.Direction.LEFT, this.board_array[x1][y1]);
                            }
                            break;
                    }
                }
            }
        }
    }

    // is in array
    /**
     * checks if position is still in array
     * @param x position x
     * @param y position y
     * @return if is inside array
     */
    private boolean isInArray(int x, int y){
        if((x >= 0)&&(x <= BOARD_SIZE-1)){
            return((y >= 0)&&(y <= BOARD_SIZE-1));
        }
        return false;
    }

    /**
     * get points of white player
     * @return points
     */
    public int getWhite_points() {
        return white_points;
    }

    /**
     * get points of black player
     * @return points
     */
    public int getBlack_points() {
        return black_points;
    }
}
