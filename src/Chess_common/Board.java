package Chess_common;

import Chess_pieces.*;
import enums.color_piece;

public class Board {
    private static final int BOARD_SIZE = 8;
    private Field[][] board_array;
    private HistoryItem historyItem;

    // constructor creates array and fills it with pieces
    public Board(){
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

    public Field getField(int x, int y){
        return this.board_array[x][y];
    }

    // ################################################# CORDINATES MOVE ###############################################
    // moves piece from position1 to position2
    public HistoryItem movePiece(String from_to){
        HistoryItem historyItem;
        int[] cordiantes_array = new int[4];
        cordiantes_array = seperateFromTo(from_to);

        if (cordiantes_array == null){
            System.out.println("coordinates err");
            System.exit(0);
        }
        if (checkCoordinates(cordiantes_array)){
            System.out.println("coordinates not valid number,letter");
            System.exit(0);
        }

        Field from = getField(cordiantes_array[0], cordiantes_array[1]);
        Field to = getField(cordiantes_array[2], cordiantes_array[3]);

        Piece player = from.getPiece();
        Piece target = to.getPiece();

        if (player instanceof Pawn){ // MOVE PAWN
            if(((Pawn) player).isMoveValid(cordiantes_array[2], cordiantes_array[3], target)){
                movePawn(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
            }
        }else if(player instanceof Rook){ // MOVE ROOK
            if (((Rook) player).isMoveValid(cordiantes_array[2], cordiantes_array[3])){
                moveRook(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
            }
        }else if (player instanceof Knight){ // MOVE KNIGHT
            if (((Knight) player).isMoveValid(cordiantes_array[2], cordiantes_array[3])){
                moveKnight(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
            }
        }else if(player instanceof Bishop){ // MOVE BISHOP
            if (((Bishop) player).isMoveValid(cordiantes_array[2], cordiantes_array[3])){
                moveBishop(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
            }
        }else if(player instanceof King){
            if (((King) player).isMoveValid(cordiantes_array[2], cordiantes_array[3])){
                moveKing(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
            }
        }else if(player instanceof Queen){
            if (((Queen) player).isMoveValid(cordiantes_array[2], cordiantes_array[3])){
                moveQueen(from, to);
            }else {
                System.out.println("IS NOT VALID MOVE");
            }
        }else{
            System.out.println("NO movable piece avalible");
        }
        return this.historyItem;
    }

    // ############################################ PIECE MOVES ########################################################
    // BISHOP
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
    private void moveKing(Field from, Field to){
        move(from, to);
    }

    // ROOK
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
    private void moveKnight(Field from, Field to){
        move(from, to);
    }

    private void move(Field from, Field to){
        if (!to.isEmpty()){
            if(from.getPiece().getColor() == to.getPiece().getColor()){
                System.out.println("CANT ATTACK SAME COLOR");
            }else{
                Piece piece_to;
                Piece piece_from = from.removePiece();
                piece_to = to.removePiece();
                System.out.println("TARGET ["+piece_to.getValue()+"]");
                to.putPiece(piece_from);
                this.historyItem = new HistoryItem(from, to, piece_to);
            }
        }else{
            Piece piece_from = from.removePiece();
            to.putPiece(piece_from);
            this.historyItem = new HistoryItem(from, to, null);
        }
    }

    public void moveHistory(Field from, Field to, Piece target){
        Piece piece = to.removePiece();
        from.putPiece(piece);
        to.putPiece(target);
    }

    //check if coordinates are on board
    private boolean checkCoordinates(int[] array){
        boolean is_not_valid = false;
        for (int i = 0; i < 4; i++){
            if (array[i] > 7 || array[i] < 0){
                is_not_valid = true;
            }
        }
        return is_not_valid;
    }

    //seperates coordinates FROM and coordinate TO
    private int[] seperateFromTo(String from_to){
        String from, to;
        if(from_to.length() == 4 || from_to.length() == 5){ // normal positions
            if (from_to.length() == 5){
//                char piece_symbol = from_to.charAt(0); // if needed
                from = from_to.substring(1,3);
                to = from_to.substring(3);
            }else {
                from = from_to.substring(0,2);
                to = from_to.substring(2);
            }
            return normalPosition(from, to);
        }else if (from_to.length() == 2 || from_to.length() == 3){ // simplified positions
            if (from_to.length() == 3){
//                char piece_symbol = from_to.charAt(0); // if needed
                to = from_to.substring(1);
                return simplePosition(to);
            }else {
                to = from_to;
                return simplePosition(to);
            }
        }
        return null;
    }

    // converts coordinates to int array with on board indexes
    // TODO simple position
    private int[] simplePosition(String move_to){
        return null;
    }

    // converts coordinates to int array with on board indexes
    private int[] normalPosition(String from, String to){
        int[] array = new int[4];
        array[0] = convertCharToIndex(from.charAt(0));
        array[1] = BOARD_SIZE - (from.charAt(1) - 49) -1;
        array[2] = convertCharToIndex(to.charAt(0));
        array[3] = BOARD_SIZE - (to.charAt(1) - 49) -1;
        return array;
    }

    private int convertCharToIndex(char character){
        return (((int) Character.toUpperCase(character)))-65;
    }


    //############################################# CREATING BOARD #####################################################
    // show piece placement in text mode
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
    public void fillBoard(){
        placePieces(color_piece.BLACK);
        placePieces(color_piece.WHITE);
    }

    // placement of pieces
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
                    this.board_array[x][y].putPiece(new King(x, y, color));
                    break;
                case (BOARD_SIZE-4):
                    // place QUEEN
                    this.board_array[x][y].putPiece(new Queen(x, y, color));
                    break;
            }
        }
    }

    // fills array around in seperate field across whole board
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
                            y1 = y-1;
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
    private boolean isInArray(int x, int y){
        if((x >= 0)&&(x <= BOARD_SIZE-1)){
            return((y >= 0)&&(y <= BOARD_SIZE-1));
        }
        return false;
    }
}
