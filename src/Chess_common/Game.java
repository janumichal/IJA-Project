package Chess_common;

import Chess_pieces.*;
import enums.color_piece;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    History history;
    HistoryItem item;
    private List<Move> loaded_moves; // SHOWS LOADED MOVES
    private boolean is_pawn;
    private int index; // INDEX IN MOVES
    private boolean auto_mode;

    public Game() {
        this.index = 0;
        this.loaded_moves = new ArrayList<>();
        this.board = new Board();
        this.history = new History();
        HistoryItem item;
        board.fillBoard();
        this.is_pawn = false;
        this.auto_mode = true;
    }

    public boolean isAuto_mode() {
        return auto_mode;
    }

    public void setAuto_mode() {
        this.auto_mode = false;
    }

    public void prew(){
        undo();
        this.index--;
    }

    public void next(){
        applyMove();
        this.index++;
    }

    public void applyMove(){
        Move move = this.loaded_moves.get(this.index);
        if (move.getTo() != null && move.getFrom() != null){
            fullFormat(move);
        }else if (move.getTo() != null && move.getFrom() == null){
            simpleFormat(move);
        }
    }

    public void fullFormat(Move one_move){
        if ((one_move.isTake() && one_move.getTo().getPiece() != null) || (!one_move.isTake() && one_move.getTo().getPiece() == null)){
            if (one_move.isPawn() && one_move.getFrom().getPiece() instanceof Pawn) {
                if (one_move.getExchange() == '\0'){
                    move(one_move.getFrom(), one_move.getTo());
                }else{
                    if ((one_move.getFrom().getPiece().getColor() == color_piece.WHITE && one_move.getTo().getRow() == 0)||(one_move.getTo().getPiece().getColor() == color_piece.BLACK && one_move.getTo().getRow() == 7)){
                        if (one_move.getExchange() == 'D'){

                            Queen piece = new Queen(one_move.getTo().getRow(),one_move.getTo().getCol(), one_move.getFrom().getPiece().getColor());

                            HistoryItem new_item = new HistoryItem(one_move.getFrom(), one_move.getTo(), one_move.getTo().getPiece());
                            new_item.setExchange(piece);
                            this.history.add(new_item);

                            one_move.getFrom().removePiece();
                            one_move.getTo().putPiece(piece);


                        }else if (one_move.getExchange() == 'V'){
                            Rook piece = new Rook(one_move.getRow(),one_move.getColumn(), one_move.getFrom().getPiece().getColor());
                            one_move.getFrom().removePiece();
                            one_move.getTo().putPiece(piece);
                        }else if (one_move.getExchange() == 'J'){
                            Knight piece = new Knight(one_move.getRow(),one_move.getColumn(), one_move.getFrom().getPiece().getColor());
                            one_move.getFrom().removePiece();
                            one_move.getTo().putPiece(piece);
                        }else if (one_move.getExchange() == 'S'){
                            Bishop piece = new Bishop(one_move.getRow(),one_move.getColumn(), one_move.getFrom().getPiece().getColor());
                            one_move.getFrom().removePiece();
                            one_move.getTo().putPiece(piece);
                        }else {
                            System.out.println("WRONGLY FORMATED MOVE WRONG PIECE");
                        }
                    }else {
                        System.out.println("WRONGLY FORMATED MOVE WRONG PLACE");
                    }
                }
            }else if (one_move.isKnight() && one_move.getFrom().getPiece() instanceof Knight) {
                move(one_move.getFrom(), one_move.getTo());
            }else if (one_move.isKing() && one_move.getFrom().getPiece() instanceof King){
                move(one_move.getFrom(), one_move.getTo());
            }else if (one_move.isQueen() && one_move.getFrom().getPiece() instanceof Queen){
                move(one_move.getFrom(), one_move.getTo());
            }else if (one_move.isBishop() && one_move.getFrom().getPiece() instanceof Bishop){
                move(one_move.getFrom(), one_move.getTo());
            }else if (one_move.isRook() && one_move.getFrom().getPiece() instanceof Rook){
                move(one_move.getFrom(), one_move.getTo());
            }else {
                System.out.println("WRONGLY FORMATED MOVE");
            }
        }else {
            System.out.println("WRONGLY FORMATED MOVE");
        }
    }

    public void simpleFormat(Move one_move){
//        move(one_move.getFrom(),one_move.getTo()); TODO
    }

    public void move(Field from, Field to){
        if (!isAuto_mode()){
            delFromIndex();
            this.loaded_moves.add(createMove(from, to));

        }
        item = this.board.movePiece(from, to);
        if (item != null){
            this.history.add(item);
        }
    }

    public void delFromIndex(){
        this.loaded_moves.subList(index, this.loaded_moves.size()).clear();
    }

    public Move createMove(Field from, Field to){
        Move new_move = new Move();
        char from_x = (char)(97 + from.getCol());
        char from_y =  (char)((8 - from.getRow())+'0');
        new_move.setFrom(new Field(from_x, from_y));

        char to_x = (char)(97 + to.getCol());
        char to_y =  (char)((8 - to.getRow())+'0');
        new_move.setTo(new Field(to_x, to_y));

        if (to.getPiece() != null){
            new_move.setTake();
        }

        if ((from.getPiece() instanceof Pawn && from.getPiece().getColor() == color_piece.WHITE && to.getRow() == 0)||(from.getPiece() instanceof Pawn && from.getPiece().getColor() == color_piece.BLACK && to.getRow() == 7)){
            // TODO WHAT TO CHANGE TO POPUP
            new_move.setExchange('K');
        }
        if (from.getPiece() instanceof King){
            new_move.setKing();
        }else if (from.getPiece() instanceof Queen){
            new_move.setQueen();
        }else if (from.getPiece() instanceof Bishop){
            new_move.setBishop();
        }else if (from.getPiece() instanceof Knight){
            new_move.setKnight();
        }else if (from.getPiece() instanceof Rook){
            new_move.setRook();
        }

        //TODO CHACK AND MAT

        return new_move;
    }

    public int points(color_piece color){
        if (color == color_piece.BLACK){
            return this.board.getBlack_points();
        }else{
            return this.board.getWhite_points();
        }
    }

    public void undo(){
        HistoryItem item = history.undo();
        if (item != null){
            Field from = item.getFrom();
            Field to = item.getTo();
            Piece target = item.getTarget_to();
            Piece exchange = item.getExchange();

            board.moveHistory(from, to, target, exchange);
        }
    }

    public void redo(){
        HistoryItem item = history.redo();
        if (item != null){
            Field from = item.getFrom();
            Field to = item.getTo();
            Piece exchange = item.getExchange();

            board.moveHistory(to, from, null, exchange);
        }
    }

    public void loadAllMoves(String all_moves){
        if (!all_moves.equals("")){
            String[] rotation_format = all_moves.split("\n");
            for (int i = 0; i < rotation_format.length ; i++){
                String[] splited = rotation_format[i].split("\\.");
                if (i+1 != Integer.valueOf(splited[0].trim())){
                    System.out.println("Order is INVALID"+ splited[0]);
                    // TODO WINDOW POPUP
                    break;
                }else {
                    String[] arr_cord = splited[1].trim().split(" ");
                    int move_length = arr_cord.length;
                    if (move_length != 2){
                        System.out.println("INVALID NUMBER OF MOVES");
                        break;
                    }else {
                        String white = arr_cord[0];
                        String black = arr_cord[1];

                        if (validFormat(white) && validFormat(black)) {
                            this.loaded_moves.add(formatMove(white));
                            this.loaded_moves.add(formatMove(black));
                        }else{
                            System.out.println("FORMAT NOT VALID");
                            break;
                        }
                    }
                }
            }
        }else {
            this.auto_mode = false;
        }
    }

    public Move formatMove(String coordinates){
        Move move = new Move();
            int counter = 0;
            char sign = coordinates.charAt(counter++);
            switch (sign){
                case 'K':
                    move.setKing();
                    sign = coordinates.charAt(counter++);
                    return isBodyMove(coordinates, sign, counter, move);
                case 'D':
                    move.setQueen();
                    sign = coordinates.charAt(counter++);
                    return isBodyMove(coordinates, sign, counter, move);
                case 'V':
                    move.setRook();
                    sign = coordinates.charAt(counter++);
                    return isBodyMove(coordinates, sign, counter, move);
                case 'S':
                    move.setBishop();
                    sign = coordinates.charAt(counter++);
                    return isBodyMove(coordinates, sign, counter, move);
                case 'J':
                    move.setKnight();
                    sign = coordinates.charAt(counter++);
                    return isBodyMove(coordinates, sign, counter, move);
                default:
                    move.setPawn();
                    return isBodyMove(coordinates, sign, counter, move);
            }
    }

    public Move isBodyMove(String coordinates, char sign, int counter, Move move){
        if (isValidSign(sign)){ // is a,b,c,d,e,f,g,h (FIRST CHAR)
            move.setColumn(sign);
            sign = coordinates.charAt(counter++);
            if (isValidNumber(sign)){ // is 1,2,3,4,5,6,7,8
                move.setRow(sign);
                if (coordinates.length() <= counter){
                    move.setTo(this.board.getField(move.getColumn(), move.getRow()));
                    return move;
                }
                move.setFrom(this.board.getField(move.getColumn(), move.getRow()));
                sign = coordinates.charAt(counter++);
                if (sign == 'x'){
                    move.setTake();
                    sign = coordinates.charAt(counter++);
                    return isValidFromTakeToEndMove(coordinates, sign, counter, move);
                }else if (isValidSign(sign)){
                    return isValidFromTakeToEndMove(coordinates, sign, counter, move);
                }
            }else if (sign == 'x'){
                move.setTake();
                sign = coordinates.charAt(counter++);
                return isValidFromTakeToEndMove(coordinates, sign, counter, move);
            }else if (isValidSign(sign)){
                return isValidFromTakeToEndMove(coordinates, sign, counter, move);
            }
        }else if (isValidNumber(sign)){ // is 1,2,3,4,5,6,7,8 (FIRST CHAR) simplified version DONE
            move.setRow(sign);
            sign = coordinates.charAt(counter++);
            if(isValidSign(sign)){ // is a,b,c,d,e,f,g,h (SECOND CHAR)
                return isValidFromTakeToEndMove(coordinates, sign, counter, move);
            }else if (sign == 'x'){ // is x (SECOND CHAR)
                move.setTake();
                sign = coordinates.charAt(counter++);
                return isValidFromTakeToEndMove(coordinates, sign, counter, move);
            }
        }else if (sign == 'x'){
            move.setTake();
            sign = coordinates.charAt(counter++);
            return isValidFromTakeToEndMove(coordinates, sign, counter, move);
        }
        return move;
    }

    public Move isValidFromTakeToEndMove(String coordinates, char sign, int counter, Move move){
        move.setColumn(sign);
        sign = coordinates.charAt(counter++);
        move.setRow(sign);
        move.setTo(this.board.getField(move.getColumn(),move.getRow()));
        if (coordinates.length() > counter){
            sign = coordinates.charAt(counter++);
            switch (sign){
                case 'D':
                case 'V':
                case 'S':
                case 'J':
                    move.setExchange(sign);
                    if (coordinates.length() > counter){
                        sign = coordinates.charAt(counter++);
                        if (sign == '#' || sign == '+'){
                            move.setMat();
                        }else if (sign == '+'){
                            move.setCheck();
                        }
                        return move;
                    }else {
                        return move;
                    }
                case '#':
                    move.setMat();
                    return move;
                case '+':
                    move.setCheck();
                    return move;
            }
        }else {
            return move;
        }
        return move;
    }

    // VALIDATION OF MOVE FORMAT

    public boolean validFormat(String coordinates){
        if (coordinates.length() < 2){
            System.out.println("INVALID LENGTH OF COORDINATES");
        }else {
            int counter = 0;
            char sign = coordinates.charAt(counter++);
            switch (sign){
                case 'K':
                case 'D':
                case 'V':
                case 'S':
                case 'J':
                    sign = coordinates.charAt(counter++);
                    return isBody(coordinates, sign, counter);
                default:
                    this.is_pawn = true;
                    return isBody(coordinates, sign, counter);
            }
        }
        return false;
    }

    public boolean isValidSign(char Character){
        if (((int)Character) > 96 && ((int)Character) < 105){ // is a,b,c,d,e,f,g,h
            return true;
        }
        return false;
    }

    public boolean isValidNumber(char Character){
        if (((int)Character) > 48 && ((int)Character) < 57){ // is 1,2,3,4,5,6,7,8
            return true;
        }
        return false;
    }

    public boolean isValidFromTakeToEnd(String coordinates, char sign, int counter){
        if (!isValidSign(sign)){ // is a,b,c,d,e,f,g,h (SECOND CHAR) simplified version
            System.out.println(sign + " IS NOT OK");
            return false;
        }else {
            if (coordinates.length() < counter){
                return false;
            }
            sign = coordinates.charAt(counter++);
            if (!isValidNumber(sign)){
                System.out.println(sign + " IS NOT OK");
                return false;
            }else {
                if (coordinates.length() > counter){
                    sign = coordinates.charAt(counter++);
                    switch (sign){
                        case 'D':
                        case 'V':
                        case 'S':
                        case 'J':
                            if (is_pawn == false){
                                System.out.println("ONLY PAWN CAN EXCHANGE");
                                return false;
                            }
                            if (coordinates.length() > counter){
                                sign = coordinates.charAt(counter++);
                                if (sign == '#' || sign == '+'){
                                    if (coordinates.length() > counter){
                                        sign = coordinates.charAt(counter++);
                                        System.out.println(sign + " IS NOT OK");
                                        return false;
                                    }
                                    return true;
                                }
                                System.out.println(sign + " IS NOT OK");
                                return false;
                            }else {
                                return true;
                            }
                        case '#':
                        case '+':
                            if (coordinates.length() > counter){
                                sign = coordinates.charAt(counter++);
                                System.out.println(sign + " IS NOT OK");
                                return false;
                            }
                            return true;
                        default:
                            System.out.println(sign + " IS NOT OK");
                            return false;
                    }
                }else {
                    return true;
                }
            }
        }
    }

    public boolean isBody(String coordinates, char sign, int counter){
        if (isValidSign(sign)){ // is a,b,c,d,e,f,g,h (FIRST CHAR)
            if (coordinates.length() <= counter){
                return false;
            }
            sign = coordinates.charAt(counter++);
            if (isValidNumber(sign)){ // is 1,2,3,4,5,6,7,8
                if (coordinates.length() <= counter){
                    return true;
                }
                sign = coordinates.charAt(counter++);
                if (sign == 'x'){
                    if (coordinates.length() <= counter){
                        return false;
                    }
                    sign = coordinates.charAt(counter++);
                    return isValidFromTakeToEnd(coordinates, sign, counter);
                }else if (isValidSign(sign)){
                    return isValidFromTakeToEnd(coordinates, sign, counter);
                }else {
                    return false;
                }
            }else if (sign == 'x'){
                if (coordinates.length() <= counter){
                    return false;
                }
                sign = coordinates.charAt(counter++);
                return isValidFromTakeToEnd(coordinates, sign, counter);
            }else if (isValidSign(sign)){
                return isValidFromTakeToEnd(coordinates, sign, counter);
            }else {
                return false;
            }
        }else if (isValidNumber(sign)){ // is 1,2,3,4,5,6,7,8 (FIRST CHAR) simplified version DONE
            sign = coordinates.charAt(counter++);
            if(isValidSign(sign)){ // is a,b,c,d,e,f,g,h (SECOND CHAR)
                return isValidFromTakeToEnd(coordinates, sign, counter);
            }else if (sign == 'x'){ // is x (SECOND CHAR)
                if (coordinates.length() <= counter){
                    return false;
                }
                sign = coordinates.charAt(counter++);
                return isValidFromTakeToEnd(coordinates, sign, counter);
            }else {
                return false;
            }
        }else if (sign == 'x'){
            if (coordinates.length() <= counter){
                return false;
            }
            sign = coordinates.charAt(counter++);
            return isValidFromTakeToEnd(coordinates, sign, counter);
        }else {
            return false;
        }
    }

    public int convertCharToIndex(char col){
        return ((int)col - 97);
    }
}
