package Chess_common;

import Chess_pieces.Piece;

import java.io.PipedReader;

public class Game {
    Board board;
    History history;
    HistoryItem item;

    public Game() {
        this.board = new Board();
        this.history = new History();
        HistoryItem item;
        board.fillBoard();
        move("a2a4");
        move("a4a5");
        move("a5a6");
        move("a6b7");
        undo();
        undo();
        move("a5a6");
        redo();

        board.showPiecesText();
    }

    public void move(String position){
        item = this.board.movePiece(position);
        if (item != null){
            this.history.add(item);
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
}
