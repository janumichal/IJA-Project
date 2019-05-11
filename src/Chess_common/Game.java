/*!
 * @authors Michal Janů (xjanum03), Miroslav Švarc (xsvarc06)
 */
package Chess_common;

import Chess_pieces.*;
import enums.color_piece;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for Game
 * @author Michal Janů (xjanum03)
 * @author Miroslav Švarc (xsvarc06)
 */
public class Game {
    public Board board;
    private History history;
    private HistoryItem item;
    private List<Move> loaded_moves; // SHOWS LOADED MOVES
    private boolean is_pawn;
    private int index; // INDEX IN MOVES
    private boolean auto_mode;
    public boolean game_end = false;

    /**
     * Creates instance of game.
     */
    public Game() {
        this.index = 0;
        this.loaded_moves = new ArrayList<>();
        this.board = new Board();
        this.history = new History();
        board.fillBoard();
        this.is_pawn = false;
    }

    /**
     * Check if is Automatic mode.
     * @return is Automatic mode.
     */
    public boolean isAuto_mode() {
        return this.auto_mode;
    }

    /**
     * Sets Automatic mode off.
     */
    public void setAuto_modeOFF() {
        this.auto_mode = false;
    }

    /**
     * Sets automatic mode on.
     */
    public void setAuto_modeON(){
        this.auto_mode = true;
    }

    /**
     * Prewious move.
     */
    public void prew(){
        undo();
        this.index--;
    }

    /**
     * Next move.
     */
    public void next(){
        if (this.loaded_moves.size() > 0 && this.index < this.loaded_moves.size()){
            applyMove();
        }
    }

    /**
     * Applayes move from loaded_moves.
     */
    public void applyMove(){
        Move move = this.loaded_moves.get(this.index);
        if (move.getTo() != null && move.getFrom() != null){
            fullFormat(move);
        }else if (move.getTo() != null && move.getFrom() == null){
            simpleFormat(move);
        }
    }

    /**
     * Setter for index
     * @param i index
     */
    public void setIndex(int i){
        this.index = i;
    }

    /**
     * Finds King of specific color on board.
     * @param color color of king
     * @return pointer on king
     */
    public Field findKing(color_piece color){
        for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                Field f = this.board.getField(x,y);
                if (f != null && f.getPiece() != null && f.getPiece() instanceof King && f.getPiece().getColor() == color){
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * Check if is CheckMat.
     * @param one_move move in instance format
     * @return if is checkmat.
     */
    boolean checkMat(Move one_move){
        if (one_move.isMat()){
            Field king_field = findKing(one_move.getColor() == color_piece.WHITE ? color_piece.BLACK : color_piece.WHITE);
            if(!isMat(king_field, king_field.getPiece().getColor())){
                System.out.println("WRONG FORMAT IS NOT MAT!!!"); // TODO POPOUT
                undo();
                return false;
            }
            System.out.println("IS MAT !!!"); // TODO POPOUT end game
        } else if(one_move.isCheck()){
            Field king_field = findKing(one_move.getColor() == color_piece.WHITE ? color_piece.BLACK : color_piece.WHITE);
            if(!isCheck(king_field, king_field.getPiece().getColor())){
                System.out.println("WRONG FORMAT IS NOT CHECK!!!"); // TODO POPOUT
                undo();
                return false;
            }
        } else {
            Field king_field = findKing(one_move.getColor() == color_piece.WHITE ? color_piece.BLACK : color_piece.WHITE);
            if(isMat(king_field, king_field.getPiece().getColor())){
                System.out.println("WRONG FORMAT IS MAT!!!"); // TODO POPOUT
                undo();
                return false;
            }
            if(isCheck(king_field, king_field.getPiece().getColor())){
                System.out.println("WRONG FORMAT IS CHECK!!!"); // TODO POPOUT
                undo();
                return false;
            }
        }

        if(one_move.isMat()){
            this.loaded_moves.get(this.index).setMat();
        }else if(one_move.isCheck()){
            this.loaded_moves.get(this.index).setCheck();
        }

        return true;
    }

    /**
     * Loads Move in full format
     * @param one_move Loaded move.
     */
    public void fullFormat(Move one_move){
        Field from = this.board.getField(one_move.getFrom().getCol(), one_move.getFrom().getRow());
        Field to = this.board.getField(one_move.getTo().getCol(), one_move.getTo().getRow());
        if ((one_move.isTake() && to.getPiece() != null) || (!one_move.isTake() && to.getPiece() == null)){
            if (one_move.isPawn() && from.getPiece() instanceof Pawn) {
                if (one_move.getExchange() == '\0'){
                    move(from, to);

                    if (!checkMat(one_move)){
                        return;
                    }

                    this.index++;
                }else{
                    if ((from.getPiece() != null && from.getPiece().getColor() == color_piece.WHITE && to.getRow() == 0)||(from.getPiece() != null && from.getPiece().getColor() == color_piece.BLACK && to.getRow() == 7)){
                        if (this.board.is_white_on_move()){
                            if (from.getPiece().getColor() == color_piece.WHITE){
                                exchange(one_move);
                            }else {
                                // TODO POPUP
                                System.out.println("MOVE WITH WRONG COLOR");
                            }
                        }else {
                            if (from.getPiece().getColor() == color_piece.BLACK){
                                exchange(one_move);
                            }else {
                                // TODO POPUP
                                System.out.println("MOVE WITH WRONG COLOR");
                            }
                        }
                    }else {
                        System.out.println("WRONGLY FORMATED MOVE WRONG PLACE");
                    }
                }
            }else if (one_move.isKnight() && from.getPiece() instanceof Knight) {
                move(from, to);

                if (!checkMat(one_move)){
                    return;
                }


                this.index++;
            }else if (one_move.isKing() && one_move.getFrom().getPiece() instanceof King){
                move(from, to);

                if (!checkMat(one_move)){
                    return;
                }

                this.index++;
            }else if (one_move.isQueen() && from.getPiece() instanceof Queen){
                move(from, to);

                if (!checkMat(one_move)){
                    return;
                }

                this.index++;
            }else if (one_move.isBishop() && from.getPiece() instanceof Bishop){
                move(from, to);

                if (!checkMat(one_move)){
                    return;
                }

                this.index++;
            }else if (one_move.isRook() && from.getPiece() instanceof Rook){
                move(from, to);

                if (!checkMat(one_move)){
                    return;
                }

                this.index++;
            }else {
                System.out.println("WRONGLY FORMATED MOVE");
            }
        }else {
            System.out.println("WRONGLY FORMATED MOVE");
        }
    }

    /**
     * Applayes exchange from move.
     * @param one_move move with the exchange.
     */
    public void exchange(Move one_move){
        if (one_move.getExchange() == 'D'){
            Queen piece = new Queen(one_move.getTo().getRow(),one_move.getTo().getCol(), one_move.getFrom().getPiece().getColor());

            saveHistory(one_move, piece);

            one_move.getFrom().removePiece();
            one_move.getTo().putPiece(piece);
            this.index++;

        }else if (one_move.getExchange() == 'V'){
            Rook piece = new Rook(one_move.getRow(),one_move.getColumn(), one_move.getFrom().getPiece().getColor());

            saveHistory(one_move, piece);

            one_move.getFrom().removePiece();
            one_move.getTo().putPiece(piece);
            this.index++;
        }else if (one_move.getExchange() == 'J'){
            Knight piece = new Knight(one_move.getRow(),one_move.getColumn(), one_move.getFrom().getPiece().getColor());

            saveHistory(one_move, piece);

            one_move.getFrom().removePiece();
            one_move.getTo().putPiece(piece);
            this.index++;
        }else if (one_move.getExchange() == 'S'){
            Bishop piece = new Bishop(one_move.getRow(),one_move.getColumn(), one_move.getFrom().getPiece().getColor());

            saveHistory(one_move, piece);

            one_move.getFrom().removePiece();
            one_move.getTo().putPiece(piece);
            this.index++;
        }else {
            System.out.println("WRONGLY FORMATED MOVE WRONG PIECE");
        }
    }

    /**
     * saves HistoryItem to History
     * @param one_move move that is being saved.
     * @param piece Exchange piece.
     */
    public void saveHistory(Move one_move, Piece piece){
        HistoryItem new_item = new HistoryItem(one_move.getFrom(), one_move.getTo(), one_move.getTo().getPiece());
        new_item.setExchange(piece);
        this.history.add(new_item);
    }

    /**
     * Loads simplified format of move.
     * @param one_move move that is being loading from.
     */
    public void simpleFormat(Move one_move){
        getFromCoords(one_move);
    }

    /**
     * Gets field from and saves it to move
     * @param one_move move is being saved in.
     */
    public void getFromCoords(Move one_move){
        if (one_move.isPawn()){
            if (one_move.isTake()){
                if (one_move.getColor() == color_piece.WHITE){
                    pawnCheckTake(Field_interface.Direction.LEFT_DOWN, Field_interface.Direction.RIGHT_DOWN, one_move);
                }else if (one_move.getColor() == color_piece.BLACK){
                    pawnCheckTake(Field_interface.Direction.LEFT_UP, Field_interface.Direction.RIGHT_UP, one_move);
                }
            }else {
                if (one_move.getColor() == color_piece.WHITE){
                    pawnCheck(Field_interface.Direction.DOWN, one_move);
                }else if (one_move.getColor() == color_piece.BLACK){
                    pawnCheck(Field_interface.Direction.UP, one_move);
                }
            }
        }else if (one_move.isKing()){
            kingCheck(one_move);
        }else if (one_move.isQueen()){
            queenCheck(one_move);
        }else if (one_move.isKnight()){
            knightCheck(one_move);
        }else if (one_move.isBishop()){
            bishopCheck(one_move);
        }else if (one_move.isRook()){
            rookCheck(one_move);
        }
    }

    /**
     * Check valid moves for Queen.
     * @param move move with coordinates.
     */
    public void queenCheck(Move move){
        Field to = move.getTo();

        if (move.isTake() && to.getPiece() != null || !move.isTake() && to.getPiece() == null){
            if (move.getColumn() == -1 && move.getRow() == -1){
                if (bishopAll(move,to , "Queen")){
                    return;
                }
                if (rookCheckAll(move, to, "Queen")){
                    return;
                }
            }else if (move.getColumn() != -1){
                if(bishopCheckCol(move, "Queen")){
                    return;
                }
                if (rookCheckCol(move, "Queen")){
                    return;
                }
            }else if (move.getRow() != -1){
                if(bishopCheckRow(move, "Queen")){
                    return;
                }
                if (rookCheckRow(move, "Queen")){
                    return;
                }
            }
        }
        System.out.println("WRONG MOVE !!!"); // TODO POPUP
    }

    /**
     * Check valid moves for Bishop.
     * @param move move with coordinates.
     * @return if move is valid for this piece
     */
    public boolean bishopCheck(Move move){
        Field to = move.getTo();

        if (move.isTake() && to.getPiece() != null || !move.isTake() && to.getPiece() == null){
            if (move.getColumn() == -1 && move.getRow() == -1){
                if (bishopAll(move,to , "Bishop")){
                    return true;
                }
            }else if (move.getColumn() != -1){
                if(bishopCheckCol(move, "Bishop")){
                    return true;
                }
            }else if (move.getRow() != -1){
                if(bishopCheckRow(move, "Bishop")){
                    return true;
                }
            }
        }
        System.out.println("WRONG MOVE !!!"); // TODO POPUP
        return false;
    }

    /**
     * Checks all direction for move validation.
     * @param move move with coords.
     * @param to Field to move to.
     * @param piece Piece that is being moved.
     * @return if move is valid
     */
    public boolean bishopAll(Move move, Field to, String piece){
        Field left_up = to.nextField(Field_interface.Direction.LEFT_UP);
        Field right_up = to.nextField(Field_interface.Direction.RIGHT_UP);
        Field left_down = to.nextField(Field_interface.Direction.LEFT_DOWN);
        Field right_down = to.nextField(Field_interface.Direction.RIGHT_DOWN);

        if (twoSides(left_up, Field_interface.Direction.LEFT_UP, right_up, Field_interface.Direction.RIGHT_UP, move, piece)){
            return true;
        }

        if (twoSides(left_down, Field_interface.Direction.LEFT_DOWN, right_down, Field_interface.Direction.RIGHT_DOWN, move, piece)){
            return true;
        }
        return false;
    }

    /**
     * move validation in simplified format in with set column.
     * @param move move with coordinates.
     * @param piece piece that is being moved.
     * @return if is valid move.
     */
    public boolean bishopCheckCol(Move move, String piece){
        Field to = move.getTo();

        if (move.getColumn() < to.getCol()){ // left
            Field left_up = to.nextField(Field_interface.Direction.LEFT_UP);
            Field left_down = to.nextField(Field_interface.Direction.LEFT_DOWN);

            return twoSides(left_up, Field_interface.Direction.LEFT_UP, left_down, Field_interface.Direction.LEFT_DOWN, move, piece);

        }else if (move.getColumn() > to.getCol()){ // right
            Field right_up = to.nextField(Field_interface.Direction.RIGHT_UP);
            Field right_down = to.nextField(Field_interface.Direction.RIGHT_DOWN);

            return twoSides(right_up, Field_interface.Direction.RIGHT_UP, right_down, Field_interface.Direction.RIGHT_DOWN, move, piece);
        }
        return false;
    }

    /**
     * move validation in simplified format in with set row.
     * @param move move with coordinates.
     * @param piece piece that is being moved.
     * @return if is valid move.
     */
    public boolean bishopCheckRow(Move move, String piece){
        Field to = move.getTo();

        if (move.getRow() < to.getRow()){ // up
            Field left_up = to.nextField(Field_interface.Direction.LEFT_UP);
            Field right_up = to.nextField(Field_interface.Direction.RIGHT_UP);

            return twoSides(left_up, Field_interface.Direction.LEFT_UP, right_up, Field_interface.Direction.RIGHT_UP, move, piece);

        }else if (move.getRow() > to.getRow()){ // down
            Field left_down = to.nextField(Field_interface.Direction.LEFT_DOWN);
            Field right_down = to.nextField(Field_interface.Direction.RIGHT_DOWN);

            return twoSides(left_down, Field_interface.Direction.LEFT_DOWN, right_down, Field_interface.Direction.RIGHT_DOWN, move, piece);
        }
        return false;
    }

    /**
     * Check valid moves for Rook.
     * @param move move with coordinates.
     * @return if move is valid.
     */
    public boolean rookCheck(Move move){
        Field to  = move.getTo();
            if (rookCheckAll(move,to , "Rook")){
                return true;
            }
        if (move.isTake() && to.getPiece() != null || !move.isTake() && to.getPiece() == null){
            if (move.getColumn() == -1 && move.getRow() == -1){


            }else if (move.getColumn() != -1){
                if(rookCheckCol(move, "Rook")){
                    return true;
                }

            }else if (move.getRow() != -1){
                if(rookCheckRow(move, "Rook")){
                    return true;
                }

            }
        }
        System.out.println("WRONG MOVE !!!"); // TODO POPUP
        return false;
    }

    /**
     * Checks all direction for move validation.
     * @param move move with coords.
     * @param to Field to move to.
     * @param piece Piece that is being moved.
     * @return id move is valid
     */
    public boolean rookCheckAll(Move move,Field to, String piece){
        Field up = to.nextField(Field_interface.Direction.UP);
        Field down = to.nextField(Field_interface.Direction.DOWN);
        Field left = to.nextField(Field_interface.Direction.LEFT);
        Field right = to.nextField(Field_interface.Direction.RIGHT);

        if (twoSides(up, Field_interface.Direction.UP, down, Field_interface.Direction.DOWN, move, piece)){
            return true;
        }

        if (twoSides(left, Field_interface.Direction.LEFT, right, Field_interface.Direction.RIGHT, move, piece)){
            return true;
        }
        return false;
    }

    /**
     * move validation in simplified format in with set row.
     * @param move move with coordinates.
     * @param piece piece that is being moved.
     * @return if is valid move.
     */
    public boolean rookCheckRow(Move move ,String piece){
        Field to = move.getTo();
        if (move.getRow() == to.getRow()){
            Field left = to.nextField(Field_interface.Direction.LEFT);
            Field right = to.nextField(Field_interface.Direction.RIGHT);

            return twoSides(left, Field_interface.Direction.LEFT, right, Field_interface.Direction.RIGHT, move, piece);

        }else if (move.getRow() < to.getRow()){
            Field up = to.nextField(Field_interface.Direction.UP);

            return oneSide(up, Field_interface.Direction.UP, move, piece);

        }else if (move.getRow() > to.getRow()){
            Field down = to.nextField(Field_interface.Direction.DOWN);

            return oneSide(down, Field_interface.Direction.DOWN, move, piece);
        }
        return false;
    }

    /**
     * Check if something is not in direction
     * @param f1 Field starts from.
     * @param dir1 Direction goes to.
     * @param move Move with all coordinates.
     * @param piece Piece that is being moved
     * @return If something is in way.
     */
    public boolean oneSide(Field f1, Field_interface.Direction dir1, Move move, String piece){
        while (f1 != null){
            switch (piece){
                case "Queen":
                    if (f1.getPiece() != null && f1.getPiece() instanceof Queen){
                        pieceCheckMove(f1,move);
                        return true;
                    }
                    break;
                case "Rook":
                    if (f1.getPiece() != null && f1.getPiece() instanceof Rook){
                        pieceCheckMove(f1,move);
                        return true;
                    }
                    break;
                case "Bishop":
                    if (f1.getPiece() != null && f1.getPiece() instanceof Bishop){
                        pieceCheckMove(f1,move);
                        return true;
                    }
                    break;
            }
            f1 = f1.nextField(dir1);
        }
        return false;
    }

    /**
     * Check if something is not in directions
     * @param f1 Field starts from.
     * @param dir1 Direction goes to.
     * @param f2 Field starts from.
     * @param dir2 Direction goes to.
     * @param move Move with all coordinates.
     * @param piece Piece that is being moved
     * @return If something is in way.
     */
    public boolean twoSides(Field f1, Field_interface.Direction dir1, Field f2, Field_interface.Direction dir2, Move move, String piece){
        while (f1 != null || f2 != null){
            if (f1 != null){
                switch (piece){
                    case "Queen":
                        if (f1.getPiece() != null && f1.getPiece() instanceof Queen){
                            pieceCheckMove(f1,move);
                            return true;
                        }
                        break;
                    case "Rook":
                        if (f1.getPiece() != null && f1.getPiece() instanceof Rook){
                            pieceCheckMove(f1,move);
                            return true;
                        }
                        break;
                    case "Bishop":
                        if (f1.getPiece() != null && f1.getPiece() instanceof Bishop){
                            pieceCheckMove(f1,move);
                            return true;
                        }
                        break;
                }
                f1 = f1.nextField(dir1);
            }
            if (f2 != null){
                switch (piece){
                    case "Queen":
                        if (f2.getPiece() != null && f2.getPiece() instanceof Queen){
                            pieceCheckMove(f2,move);
                            return true;
                        }
                        break;
                    case "Rook":
                        if (f2.getPiece() != null && f2.getPiece() instanceof Rook){
                            pieceCheckMove(f2,move);
                            return true;
                        }
                        break;
                    case "Bishop":
                        if (f2.getPiece() != null && f2.getPiece() instanceof Bishop){
                            pieceCheckMove(f2,move);
                            return true;
                        }
                        break;
                }
                f2 = f2.nextField(dir2);
            }
        }
        return false;
    }

    /**
     * move validation in simplified format in with set column.
     * @param move move with coordinates.
     * @param piece piece that is being moved.
     * @return if is valid move.
     */
    public boolean rookCheckCol(Move move, String piece){
        Field to = move.getTo();
        if (move.getColumn() == to.getCol()){
            Field up = to.nextField(Field_interface.Direction.UP);
            Field down = to.nextField(Field_interface.Direction.DOWN);

            return twoSides(up, Field_interface.Direction.UP, down, Field_interface.Direction.DOWN, move, piece);
        }else if (move.getColumn() < to.getCol()){
            Field left = to.nextField(Field_interface.Direction.LEFT);

            return oneSide(left, Field_interface.Direction.LEFT, move, piece);
        }else if (move.getColumn() > to.getCol()){
            Field right = to.nextField(Field_interface.Direction.RIGHT);

            return oneSide(right, Field_interface.Direction.RIGHT, move, piece);
        }
        return false;
    }

    /**
     * Check valid moves for Knight.
     * @param one_move move with coordinates.
     */
    public void knightCheck(Move one_move){
        Field to = one_move.getTo();
        if (one_move.isTake() && to.getPiece() != null || !one_move.isTake() && to.getPiece() == null){
            if (one_move.getColumn() == -1 && one_move.getRow() == -1){
                knightCheckAllmoves(one_move, to);
            }else if (one_move.getColumn() != -1){
                knightCheckColumn(one_move, to);
            }else if (one_move.getRow() != -1){
                knightCheckRow(one_move, to);
            }
        }else {
            System.out.println("WRONG MOVE !!!"); // TODO POPUP
        }
    }

    /**
     * move aplication in simplified format in with set row.
     * @param move move with coordinates.
     * @param to Position where move to.
     */
    public void knightCheckRow(Move move, Field to){
        if (move.getRow() != to.getRow()){
            if (move.getRow() < to.getRow()){ //from (UP) to (DOWN)
                knightCheckRowN(to, move, Field_interface.Direction.UP);
            }else if (move.getRow() > to.getRow()){ // from (DOWN) to (UP)
                knightCheckRowN(to, move, Field_interface.Direction.DOWN);
            }
        }else {
            System.out.println("WRONG MOVE !!!"); // TODO POPUP
        }
    }

    /**
     * move aplication in simplified format in with set row.
     * @param move move with coordinates.
     * @param to Position where move to.
     * @param dir Direction to go to.
     */
    public void knightCheckRowN(Field to, Move move, Field_interface.Direction dir){
        Field from;
        int adder = dir == Field_interface.Direction.UP ? -1 : +1;
        int adder2 = dir == Field_interface.Direction.UP ? -2 : +2;

        if ((to.getRow() + adder) == move.getRow()){
            from = to.nextField(dir);
            if (from.nextField(Field_interface.Direction.LEFT) != null) {
                from = from.nextField(Field_interface.Direction.LEFT);
                if ((from = from.nextField(Field_interface.Direction.LEFT)) != null && from.getPiece() != null && from.getPiece() instanceof Knight) {
                    pieceCheckMove(from, move);
                    return;
                }
            }
            from = to.nextField(dir);
            if (from.nextField(Field_interface.Direction.RIGHT) != null ){
                from = from.nextField(Field_interface.Direction.RIGHT);
                if ((from = from.nextField(Field_interface.Direction.RIGHT)) != null && from.getPiece() != null && from.getPiece() instanceof Knight){
                    pieceCheckMove(from, move);
                    return;
                }
            }
        }else if ((to.getRow() + adder2) == move.getRow()){
            from = to.nextField(dir).nextField(dir);
            if (from.nextField(Field_interface.Direction.LEFT) != null) {
                from = from.nextField(Field_interface.Direction.LEFT);
                if (from.getPiece() != null && from.getPiece() instanceof Knight) {
                    pieceCheckMove(from, move);
                    return;
                }
            }
            from = to.nextField(dir).nextField(dir);
            if (from.nextField(Field_interface.Direction.RIGHT) != null){
                from = from.nextField(Field_interface.Direction.RIGHT);
                if (from.getPiece() != null && from.getPiece() instanceof Knight){
                    pieceCheckMove(from, move);
                    return;
                }
            }
        }
        System.out.println("WRONG MOVE !!!"); // TODO POPUP
    }

    /**
     * move aplication in simplified format in with set column.
     * @param move move with coordinates.
     * @param to Position where move to.
     */
    public void knightCheckColumn(Move move, Field to){
        if (move.getColumn() != to.getCol()){
            if (move.getColumn() < to.getCol()){ //from (levo) to (pravo)
                knightCheckColumnN(to, move, Field_interface.Direction.LEFT);
            }else if (move.getColumn() > to.getCol()){ // from (pravo) to (levo)
                knightCheckColumnN(to, move, Field_interface.Direction.RIGHT);
            }
        }else {
            System.out.println("WRONG MOVE !!!"); // TODO POPUP
        }
    }

    /**
     * move aplication in simplified format in with set column.
     * @param move move with coordinates.
     * @param to Position where move to.
     * @param dir Direction to go to.
     */
    public void knightCheckColumnN(Field to, Move move, Field_interface.Direction dir){
        Field from;
        int adder = dir == Field_interface.Direction.LEFT ? -1 : +1;
        int adder2 = dir == Field_interface.Direction.LEFT ? -2 : +2;

        if ((to.getCol() + adder) == move.getColumn()){
            from = to.nextField(dir);
            if (from.nextField(Field_interface.Direction.UP) != null){
                from = from.nextField(Field_interface.Direction.UP);
                if ((from = from.nextField(Field_interface.Direction.UP)) != null && from.getPiece() != null && from.getPiece() instanceof Knight){
                    pieceCheckMove(from, move);
                    return;
                }
            }
            from = to.nextField(dir);
            if (from.nextField(Field_interface.Direction.DOWN) != null){
                from = from.nextField(Field_interface.Direction.DOWN);
                if ((from = from.nextField(Field_interface.Direction.DOWN)) != null && from.getPiece() != null && from.getPiece() instanceof Knight){
                    pieceCheckMove(from, move);
                    return;
                }
            }
        }else if ((to.getCol() + adder2) == move.getColumn()){
            from = to.nextField(dir).nextField(dir);
            if (from.nextField(Field_interface.Direction.UP) != null){
                from = from.nextField(Field_interface.Direction.UP);
                if (from.getPiece() != null && from.getPiece() instanceof Knight){
                    pieceCheckMove(from, move);
                    return;
                }
            }
            from = to.nextField(dir).nextField(dir);
            if (from.nextField(Field_interface.Direction.DOWN) != null){
                from = from.nextField(Field_interface.Direction.DOWN);
                if (from.getPiece() != null && from.getPiece() instanceof Knight){
                    pieceCheckMove(from, move);
                    return;
                }
            }
        }
        System.out.println("WRONG MOVE !!!"); // TODO POPUP
    }

    /**
     * applyes move that is in simplified format
     * @param one_move move containing all coordinates
     * @param to where to move to
     */
    public void knightCheckAllmoves(Move one_move, Field to){
        Field from;
        Field tmp;

        if((tmp = to.nextField(Field_interface.Direction.UP))!= null){
            if((from = tmp.nextField(Field_interface.Direction.LEFT_UP))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
            }else if ((from = tmp.nextField(Field_interface.Direction.RIGHT_UP))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }
        }

        if((tmp = to.nextField(Field_interface.Direction.LEFT))!= null){
            if((from = tmp.nextField(Field_interface.Direction.LEFT_UP))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }else if ((from = tmp.nextField(Field_interface.Direction.LEFT_DOWN))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }
        }

        if((tmp = to.nextField(Field_interface.Direction.DOWN))!= null){
            if((from = tmp.nextField(Field_interface.Direction.RIGHT_DOWN))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }else if ((from = tmp.nextField(Field_interface.Direction.LEFT_DOWN))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }
        }

        if((tmp = to.nextField(Field_interface.Direction.RIGHT))!= null){
            if((from = tmp.nextField(Field_interface.Direction.RIGHT_DOWN))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }else if ((from = tmp.nextField(Field_interface.Direction.RIGHT_UP))!= null && from.getPiece() != null && from.getPiece() instanceof Knight){
                pieceCheckMove(from,one_move);
                return;
            }
        }

        System.out.println("WRONG MOVE !!!");
    }

    /**
     * applyes move.
     * @param from field to move from.
     * @param one_move move with coordinates.
     */
    public void pieceCheckMove(Field from, Move one_move){
        one_move.setFrom(from);
        move(one_move.getFrom(), one_move.getTo());

        if (!checkMat(one_move)){
            return;
        }

        this.index++;
    }

    /**
     * Check valid moves for King.
     * @param one_move move with coordinates.
     */
    public void kingCheck(Move one_move){
        Field to = one_move.getTo();
        for (Field_interface.Direction dir : Field_interface.Direction.values()) {
            Field tmp = to.nextField(dir);
            if (tmp != null && tmp.getPiece() != null && tmp.getPiece() instanceof King && tmp.getPiece().getColor() == one_move.getColor()){
                one_move.setFrom(tmp);
                move(one_move.getFrom(), one_move.getTo());

                if (!checkMat(one_move)){
                    return;
                }

                this.index++;
                break;
            }
        }
        if (one_move.getFrom() == null){
            System.out.println("WRONG MOVE !!!"); // TODO POPUP
        }
    }

    /**
     * Check valid moves for Pawn.
     * @param dir whitch direction pice moves in
     * @param one_move move with coordinates.
     */
    public void pawnCheck(Field_interface.Direction dir, Move one_move){
        Field to = one_move.getTo();
        Field tmp = to.nextField(dir);

        if (tmp != null && tmp.getPiece() != null && tmp.getPiece() instanceof Pawn){
            pieceCheckMove(tmp, one_move);
        }else if ((tmp = tmp.nextField(dir)) != null && tmp.getPiece() != null && tmp.getPiece() instanceof Pawn && ((Pawn) tmp.getPiece()).getIsOnStart()){
            pieceCheckMove(tmp, one_move);
        }else {
            System.out.println("WRONG MOVE !!!");
        }
    }

    /**
     * application of move where pawn takes piece.
     * @param dir1 direction to move to
     * @param dir2 direction to move to
     * @param one_move move with all coordinates
     */
    public void pawnCheckTake(Field_interface.Direction dir1, Field_interface.Direction dir2, Move one_move){ // LEFT RIGHT
        Field to = one_move.getTo();
        Field tmp;
        if (one_move.getColumn() == -1){
            if ((tmp = to.nextField(dir1)) != null && tmp.getPiece() != null && tmp.getPiece() instanceof Pawn){
                pieceCheckMove(tmp, one_move);
            }else if ((tmp = to.nextField(dir2)) != null && tmp.getPiece() != null && tmp.getPiece() instanceof Pawn){
                pieceCheckMove(tmp, one_move);
            }else {
                System.out.println("WRONG MOVE !!!"); // TODO POPUP
            }
        }else {
            if (one_move.getColumn() < one_move.getTo().getCol()){
                if ((tmp = to.nextField(dir1)) != null && tmp.getPiece() != null && tmp.getPiece() instanceof Pawn){
                    pieceCheckMove(tmp, one_move);
                }else{
                    System.out.println("WRONG MOVE !!!"); // TODO POPUP
                }
            }else if (one_move.getColumn() > one_move.getTo().getCol()){
                if ((tmp = to.nextField(dir2)) != null && tmp.getPiece() != null && tmp.getPiece() instanceof Pawn){
                    pieceCheckMove(tmp, one_move);
                }else{
                    System.out.println("WRONG MOVE !!!"); // TODO POPUP
                }
            }
        }
    }

    /**
     * applyes move
     * @param from move from
     * @param to move to
     */
    public void move(Field from, Field to){
        if (!isAuto_mode()){
            delFromIndex();

            Move move = createMove(from, to);
            if(move != null){
                if (this.loaded_moves.size() % 2 == 0){
                    move.setColor(color_piece.WHITE);
                }else {
                    move.setColor(color_piece.BLACK);
                }

                this.loaded_moves.add(move);
                this.index++;
            }
        }else{
            item = this.board.movePiece(from, to);
            this.game_end = this.board.game_end;
            if (item != null){
                this.history.add(item);
            }
        }
    }

    /**
     * deletes moves from index to end
     */
    public void delFromIndex(){
        this.loaded_moves.subList(index, this.loaded_moves.size()).clear();
    }

    /**
     * Creates move
     * @param from from where to move
     * @param to where to move to.
     * @return move that has been saved.
     */
    public Move createMove(Field from, Field to){
        Move new_move = new Move();
        int from_x = from.getCol();
        int from_y =  from.getRow();
        new_move.setFrom(new Field(from_x, from_y));

        int to_x = to.getCol();
        int to_y = to.getRow();
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
        }else if (from.getPiece() instanceof Pawn){
            new_move.setPawn();
        }

        item = this.board.movePiece(from, to);
        this.game_end = this.board.game_end;
        if (item != null){
            this.history.add(item);
            if (!game_end){
                Field king_field = findKing(to.getPiece().getColor() == color_piece.WHITE ? color_piece.BLACK : color_piece.WHITE);
                if (isCheck(king_field, king_field.getPiece().getColor())){
                    new_move.setCheck();
                }else if(isMat(king_field, king_field.getPiece().getColor())){ //TODO NON_WORKING do later
                    new_move.setMat();
                }
            }

            return new_move;
        }

        //TODO CHACK AND MAT
        return null;
    }

    /**
     * getter of points of specific color.
     * @param color Color
     * @return points
     */
    public int points(color_piece color){
        if (color == color_piece.BLACK){
            return this.board.getBlack_points();
        }else{
            return this.board.getWhite_points();
        }
    }

    /**
     * undo move.
     */
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

    /**
     * redo move.
     */
    public void redo(){
        HistoryItem item = history.redo();
        if (item != null){
            Field from = item.getFrom();
            Field to = item.getTo();
            Piece exchange = item.getExchange();

            board.moveHistory(to, from, null, exchange);
        }
    }

    /**
     * loads all moves from string.
     * @param all_moves moves in string form.
     */
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
                    if (move_length > 2 || move_length < 1){
                        System.out.println("INVALID NUMBER OF MOVES");
                        break;
                    }else {
                        String white = arr_cord[0];

                        if (validFormat(white)) {
                            Move w_move = formatMove(white);
                            w_move.setColor(color_piece.WHITE);
                            this.loaded_moves.add(w_move);
                            if (move_length > 1 ){
                                String black = arr_cord[1];

                                if (validFormat(black)){
                                    Move b_move = formatMove(black);
                                    b_move.setColor(this.loaded_moves.size() % 2 == 0 ? color_piece.WHITE : color_piece.BLACK);
                                    this.loaded_moves.add(b_move);
                                }else {
                                    System.out.println("FORMAT NOT VALID");
                                    break;
                                }
                            }
                        }else{
                            System.out.println("err");
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

    /**
     * Prints all saved moves to console.
     */
    public void printAllMoves(){
        int counter = 1;
        int arr_size = this.loaded_moves.size();
        for (int idx = 0; idx < arr_size ; idx++){

            boolean even = idx % 2 == 0;
            String move = this.loaded_moves.get(idx).pritnMove();
            if (even){
                System.out.print(counter + ". " + move + " ("+ idx + ") ");
                if (idx+1 == arr_size){
                    System.out.println();
                }
            }else {
                System.out.print(move + " ("+ idx + ") \n");
                counter++;
            }
        }
    }

    /**
     * check format of one move
     * @param coordinates move in string form
     * @return move in Move instance
     */
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

    /**
     *
     * @param coordinates Move in string form
     * @param sign sign from move
     * @param counter counter of length
     * @param move move in instance format
     * @return move in Move instance
     */
    public Move isBodyMove(String coordinates, char sign, int counter, Move move){
        if (isValidSign(sign)){ // is a,b,c,d,e,f,g,h (FIRST CHAR)
            move.setColumn(sign);
            sign = coordinates.charAt(counter++);
            if (isValidNumber(sign)){ // is 1,2,3,4,5,6,7,8
                move.setRow(sign);

                if (coordinates.length() <= counter){
                    move.setTo(this.board.getField(move.getColumn(), move.getRow()));
                    move.setColumn(-1);
                    move.setRow(-1);

                    return move;
                }

                sign = coordinates.charAt(counter++);
                if (sign == 'x'){
                    move.setFrom(this.board.getField(move.getColumn(), move.getRow()));
                    move.setColumn(-1);
                    move.setRow(-1);

                    move.setTake();
                    sign = coordinates.charAt(counter++);
                    return isValidFromTakeToEndMove(coordinates, sign, counter, move);
                }else if (isValidSign(sign)){
                    move.setFrom(this.board.getField(move.getColumn(), move.getRow()));
                    return isValidFromTakeToEndMove(coordinates, sign, counter, move);
                }else {
                    move.setTo(this.board.getField(move.getColumn(), move.getRow()));
                    move.setColumn(-1);
                    move.setRow(-1);
                    return endSwitchFormatMove(coordinates, counter, sign, move);
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

    /**
     *
     * @param coordinates Move in string form
     * @param sign sign from move
     * @param counter counter of length
     * @param move move in instance format
     * @return move in Move instance
     */
    public Move isValidFromTakeToEndMove(String coordinates, char sign, int counter, Move move){
        int col = setColumnI(sign);
        sign = coordinates.charAt(counter++);
        int row = setRowI(sign);
        move.setTo(this.board.getField(col,row));
        if (coordinates.length() > counter){
            sign = coordinates.charAt(counter++);
            return endSwitchFormatMove(coordinates, counter, sign, move);
        }else {
            return move;
        }
    }

    /**
     *
     * @param coordinates Move in string form
     * @param counter counter of length
     * @param sign sign from move
     * @param move move in instance format
     * @return move in Move instance
     */
    public Move endSwitchFormatMove(String coordinates, int counter, char sign, Move move){
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
        return move;
    }


    // VALIDATION OF MOVE FORMAT


    /**
     * check valid format.
     * @param coordinates move in string format
     * @return if is valid.
     */
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

    /**
     * if is valid character
     * @param Character the character
     * @return if is valid
     */
    public boolean isValidSign(char Character){
        // is a,b,c,d,e,f,g,h
        return ((int) Character) > 96 && ((int) Character) < 105;
    }

    /**
     * Check if is valid number.
     * @param Character Character that is being transfered to number.
     * @return if is valid.
     */
    public boolean isValidNumber(char Character){
        // is 1,2,3,4,5,6,7,8
        return ((int) Character) > 48 && ((int) Character) < 57;
    }

    /**
     * Check if move is in valid format.
     * @param coordinates move in string format
     * @param sign one character of string move
     * @param counter counter of move length
     * @return if is valid.
     */
    public boolean isValidFromTakeToEnd(String coordinates, char sign, int counter){
        if (!isValidSign(sign)){ // is a,b,c,d,e,f,g,h
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
                    return endSwitchFormat(coordinates, sign, counter);
                }else {
                    return true;
                }
            }
        }
    }

    /**
     * finds if moves are being valid for Bishop or Queen.
     * @param f Field moves from
     * @param color color of piece
     * @param piece pointer on piece
     * @return if move is valid
     */
    public boolean controlEX(Field f, color_piece color, char piece){
        if(f.getPiece().getColor() != color){
            if(piece == 'b'){
                if(f.getPiece() instanceof Bishop || f.getPiece() instanceof Queen) {
                    return true;
                }
            }else if (piece == 'r'){
                if(f.getPiece() instanceof Rook || f.getPiece() instanceof Queen) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check if is check
     * @param from king is on field from
     * @param color_king color of king.
     * @return if is check.
     */
    public boolean isCheck(Field from, color_piece color_king){ // colors
        // Pawn
        if (color_king == color_piece.BLACK){ // BLACK KING
            Field left_down = from.nextField(Field_interface.Direction.LEFT_DOWN);
            Field right_down = from.nextField(Field_interface.Direction.RIGHT_DOWN);
            if (left_down != null && left_down.getPiece() != null && left_down.getPiece() instanceof Pawn && left_down.getPiece().getColor() == color_piece.WHITE){
                return true;
            }else if (right_down != null && right_down.getPiece() != null && right_down.getPiece() instanceof Pawn && right_down.getPiece().getColor() == color_piece.WHITE){
                return true;
            }
        }else{ // WHITE KING
            Field left_up = from.nextField(Field_interface.Direction.LEFT_UP);
            Field right_up = from.nextField(Field_interface.Direction.RIGHT_UP);
            if (left_up != null && left_up.getPiece() != null && left_up.getPiece() instanceof Pawn && left_up.getPiece().getColor() == color_piece.BLACK){
                return true;
            }else if (right_up != null && right_up.getPiece() != null && right_up.getPiece() instanceof Pawn && right_up.getPiece().getColor() == color_piece.BLACK){
                return true;
            }
        }
        // KNIGHT
        int x,y;
        x = from.getCol();
        y = from.getRow();

        Field field[] = new Field[8];

        field[0] = ((x-1) >= 0 && (y-2) >= 0) ? this.board.getField(x-1,y-2) : null;
        field[1] = ((x-1) >= 0 && (y+2) <= 7) ? this.board.getField(x-1,y+2) : null;

        field[2] = ((x-2) >= 0 && (y-1) >= 0) ? this.board.getField(x-2,y-1) : null;
        field[3] = ((x-2) >= 0 && (y+1) <= 7) ? this.board.getField(x-2,y+1) : null;

        field[4] = ((x+1) <= 7 && (y-2) >= 0) ? this.board.getField(x+1,y-2) : null;
        field[5] = ((x+1) <= 7 && (y+2) <= 7) ? this.board.getField(x+1,y+2) : null;

        field[6] = ((x+2) <= 7 && (y-1) >= 0) ? this.board.getField(x+2,y-1) : null;
        field[7] = ((x+2) <= 7 && (y+1) <= 7) ? this.board.getField(x+2,y+1) : null;

        for (int i = 0; i < 8 ; i++){
            Field fld = field[i];
            if (fld != null && fld.getPiece() != null && fld.getPiece() instanceof Knight && fld.getPiece().getColor() != color_king){
                return true;
            }
        }

        // BISHOP
        Field f1 = from.nextField(Field_interface.Direction.LEFT_UP);
        Field f2 = from.nextField(Field_interface.Direction.LEFT_DOWN);
        Field f3 = from.nextField(Field_interface.Direction.RIGHT_UP);
        Field f4 = from.nextField(Field_interface.Direction.RIGHT_DOWN);

        while (f1 != null || f2 != null || f3 != null || f4 != null){
            if (f1 != null) {
                if (f1.getPiece() != null){
                    if(controlEX(f1, color_king, 'b')){
                        return true;
                    }
                    f1 = null;
                }else{
                    f1 = f1.nextField(Field_interface.Direction.LEFT_UP);
                }
            }
            if (f2 != null) {
                if (f2.getPiece() != null){
                    if(controlEX(f2, color_king, 'b')){
                        return true;
                    }
                    f2 = null;
                }else{
                    f2 = f2.nextField(Field_interface.Direction.LEFT_DOWN);
                }
            }
            if (f3 != null) {
                if (f3.getPiece() != null){
                    if(controlEX(f3, color_king, 'b')){
                        return true;
                    }
                    f3 = null;
                }else{
                    f3 = f3.nextField(Field_interface.Direction.RIGHT_UP);
                }
            }
            if (f4 != null) {
                if (f4.getPiece() != null){
                    if(controlEX(f4, color_king, 'b')){
                        return true;
                    }
                    f4 = null;
                }else{
                    f4 = f4.nextField(Field_interface.Direction.RIGHT_DOWN);
                }
            }
        }
        // ROOK
        f1 = from.nextField(Field_interface.Direction.LEFT);
        f2 = from.nextField(Field_interface.Direction.UP);
        f3 = from.nextField(Field_interface.Direction.RIGHT);
        f4 = from.nextField(Field_interface.Direction.DOWN);

        while (f1 != null || f2 != null || f3 != null || f4 != null){
            if (f1 != null) {
                if(f1.getPiece() != null){
                    if(controlEX(f1, color_king, 'r')){
                        return true;
                    }
                    f1 = null;
                }else{
                    f1 = f1.nextField(Field_interface.Direction.LEFT);
                }
            }
            if (f2 != null) {
                if(f2.getPiece() != null){
                    if(controlEX(f2, color_king, 'r')){
                        return true;
                    }
                    f2 = null;
                }else{
                    f2 = f2.nextField(Field_interface.Direction.UP);
                }
            }
            if (f3 != null) {
                if(f3.getPiece() != null){
                    if(controlEX(f3, color_king, 'r')){
                        return true;
                    }
                    f3 = null;
                }else{
                    f3 = f3.nextField(Field_interface.Direction.RIGHT);
                }
            }
            if (f4 != null) {
                if(f4.getPiece() != null){
                    if(controlEX(f4, color_king, 'r')){
                        return true;
                    }
                    f4 = null;
                }else{
                    f4 = f4.nextField(Field_interface.Direction.DOWN);
                }
            }
        }

        return false;
    }

    /**
     * check if is mat
     * @param from king is on field from
     * @param color_king color of king.
     * @return if is mat.
     */
    public boolean isMat(Field from, color_piece color_king){
        boolean mat = true;
        if(!isCheck(from, color_king)){
            return false;
        }
        for (Field_interface.Direction dir : Field_interface.Direction.values()){
            Field new_f = from.nextField(dir);
            if(new_f != null){
                if(new_f.getPiece() == null || new_f.getPiece() != null && new_f.getPiece().getColor() != color_king){
                    mat = isCheck(new_f, color_king);
                }
                if (!mat){
                    return mat;
                }
            }
        }
        return mat;
    }

    /**
     *  Check if move is in valid format.
     * @param coordinates move in string format
     * @param sign one character of string move
     * @param counter counter of move length
     * @return if is valid.
     */
    public boolean endSwitchFormat(String coordinates, char sign, int counter){
        switch (sign){
            case 'D':
            case 'V':
            case 'S':
            case 'J':
                if (!this.is_pawn){
                    System.out.println("ONLY PAWN CAN EXCHANGE");
                    return false;
                }
                if (coordinates.length() > counter){
                    sign = coordinates.charAt(counter++);
                    if (sign == '#' || sign == '+'){
                        if (coordinates.length() > counter){
                            sign = coordinates.charAt(counter);
                            System.out.println(sign + " IS NOT OK");
                            return false;
                        }else {
                            return true;
                        }
                    }
                    System.out.println(sign + " IS NOT OK");
                    return false;
                }else {
                    return true;
                }
            case '#':
            case '+':
                if (coordinates.length() > counter){
                    sign = coordinates.charAt(counter);
                    System.out.println(sign + " IS NOT OK");
                    return false;
                }
                return true;
            default:
                System.out.println(sign + " IS NOT OK");
                return false;
        }
    }

    /**
     * Check if move is in valid format.
     * @param coordinates move in string format
     * @param sign one character of string move
     * @param counter counter of move length
     * @return if is valid.
     */
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
                    return endSwitchFormat(coordinates, sign, counter);

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

    /**
     * Setter for column in character format
     * @param sign column in character.
     * @return column number
     */
    public int setColumnI(char sign){
        return ((int)sign - 97);
    }

    /**
     * Setter for row in character format
     * @param sign row in character.
     * @return row number
     */
    public int setRowI(char sign){
        return 8 - ((int)sign - 48);
    }
}

