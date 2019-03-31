package Chess_common;

import Chess_pieces.Piece;
import enums.color_piece;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    History history;
    HistoryItem item;
    List<Move> loaded_moves;
    boolean is_pawn;

    public Game() {
        this.loaded_moves = new ArrayList<>();
        this.board = new Board();
        this.history = new History();
        HistoryItem item;
        board.fillBoard();
        this.is_pawn = false;

        next();

        board.showPiecesText();
    }

    public void next(){

    }

    public void move(String position){
        item = this.board.movePiece(position);
        if (item != null){
            this.history.add(item);
        }
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

            board.moveHistory(from, to, target);
        }
    }

    public void redo(){
        HistoryItem item = history.redo();
        if (item != null){
            Field from = item.getFrom();
            Field to = item.getTo();

            board.moveHistory(to, from, null);
        }
    }

    public void loadAllMoves(String all_moves){

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
            System.out.println(sign + " IS OK");
            if (coordinates.length() < counter){
                return false;
            }
            sign = coordinates.charAt(counter++);
            if (!isValidNumber(sign)){
                System.out.println(sign + " IS NOT OK");
                return false;
            }else {
                System.out.println(sign + " IS OK");
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
                                    System.out.println(sign + " IS OK");
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
                                System.out.println(sign + " IS OK");
                                return true;
                            }
                        case '#':
                        case '+':
                            System.out.println(sign + " IS OK");
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
            System.out.println(sign + " IS OK");
            if (coordinates.length() <= counter){
                return false;
            }
            sign = coordinates.charAt(counter++);
            if (isValidNumber(sign)){ // is 1,2,3,4,5,6,7,8
                System.out.println(sign + " IS OK");
                if (coordinates.length() <= counter){
                    return true;
                }
                sign = coordinates.charAt(counter++);
                if (sign == 'x'){
                    System.out.println(sign + " IS OK");
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
                System.out.println(sign + " IS OK");
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
            System.out.println(sign + " IS OK");
            sign = coordinates.charAt(counter++);
            if(isValidSign(sign)){ // is a,b,c,d,e,f,g,h (SECOND CHAR)
                return isValidFromTakeToEnd(coordinates, sign, counter);
            }else if (sign == 'x'){ // is x (SECOND CHAR)
                System.out.println(sign + " IS OK");
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
            System.out.println(sign + " IS OK");
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
