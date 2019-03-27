import Chess_board.Board;
import Chess_pieces.Pawn;
import enums.color_piece;

public class Main {
    public static void main(String[] args) {
        Board chess_b = new Board();
        chess_b.fillBoard();
        chess_b.showPiecesText();
        chess_b.movePiece("a2a4");
        chess_b.showPiecesText();
        chess_b.movePiece("a4b5");
        chess_b.showPiecesText();
    }
}
