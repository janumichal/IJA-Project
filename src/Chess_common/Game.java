package Chess_common;

import Chess_pieces.Piece;
import enums.color_piece;

import java.io.PipedReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    Board board;
    History history;
    HistoryItem item;
    List<String> loaded_moves;
    boolean is_check;
    boolean is_mat;

    public Game() {
        this.loaded_moves = new ArrayList<>();
        this.board = new Board();
        this.history = new History();
        HistoryItem item;
        board.fillBoard();
        this.is_check = false;
        this.is_mat = false;



        board.showPiecesText();
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
                    String white_move = arr_cord[0];
                    String black_move = arr_cord[1];

                    if (validFormat(white_move) && validFormat(black_move)) {
//                        this.loaded_moves.add(white_move); //TODO OBJ FORMATED MOVE TO LIST
//                        this.loaded_moves.add(black_move);
                    }else {
                        System.out.println("FORMAT NOT VALID");
                        break;
                    }
                }
            }
        }

    }

    public boolean validFormat(String coordinates){
        System.out.println(coordinates); // TODO DEL LATER
        if (coordinates.length() < 2 || coordinates.length() > 6){
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
                    break;
                case 'x': // is x (FIRST CHAR) simplified version
                    return isValidFromTakeToEnd(coordinates, sign, counter);
                default: // TODO HEEEEEEEEEEEEEEERRRRRRRWEEEEEEE
                    if (((int)sign) > 96 && ((int)sign) < 105){ // is a,b,c,d,e,f,g,h (FIRST CHAR)
                        System.out.println("FIRST IS OK");
                        sign = coordinates.charAt(counter++);
                        if (((int)sign) > 48 && ((int)sign) < 57){
                            System.out.println("SECOND IS OK");
                            return true;
                        }
                    }else if (((int)sign) > 48 && ((int)sign) < 57){ // is 1,2,3,4,5,6,7,8 (FIRST CHAR) simplified version
                        return true;
                    }
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
        if (coordinates.length() < 3){
            return false;
        }
        System.out.println("FIRST IS OK");
        sign = coordinates.charAt(counter++);
        if (!isValidSign(sign)){ // is a,b,c,d,e,f,g,h (SECOND CHAR) simplified version
            System.out.println("SECOND IS NOT OK");
            return false;
        }else {
            System.out.println("SECOND IS OK");
            sign = coordinates.charAt(counter++);
            if (!isValidNumber(sign)){
                System.out.println("THIRD IS NOT OK");
                return false;
            }else {
                System.out.println("THIRD IS OK");
                if (coordinates.length() > counter){
                    sign = coordinates.charAt(counter++);
                    switch (sign){
                        case 'D':
                        case 'V':
                        case 'S':
                        case 'J':
                            if (coordinates.length() > counter){
                                sign = coordinates.charAt(counter++);
                                if (sign == '#' || sign == '+'){
                                    System.out.println("FIFTH IS OK");
                                    return true;
                                }
                                System.out.println("FIFTH IS NOT OK");
                                return false;
                            }else {
                                System.out.println("FOURTH IS OK");
                                return true;
                            }
                        case '#':
                        case '+':
                            System.out.println("FOURTH IS OK");
                            return true;
                        default:
                            System.out.println("FOURTH IS NOT OK");
                            return false;
                    }
                }else {
                    return true;
                }
            }
        }
    }

}
