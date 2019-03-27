import Chess_common.Board;

public class Main {
    public static void main(String[] args) {
        Board chess_b = new Board();
        chess_b.fillBoard();
        chess_b.movePiece("d2d4");
        chess_b.showPiecesText();
    }
}
