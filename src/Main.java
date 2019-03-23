import Chess_board.Board;

public class Main {
    public static void main(String[] args) {
        Board chess_b = new Board();
        chess_b.fillBoard();
        chess_b.showPiecesText();
        chess_b.movePiece("a2a4");
    }
}
