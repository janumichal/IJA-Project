import Chess_board.Board;
import Chess_pieces.Pawn;
import enums.color_piece;

public class Main {
    public static void main(String[] args) {
        Board chess_b = new Board();
        chess_b.fillBoard();
        chess_b.showPiecesText();
        System.out.println();
        chess_b.movePiece("b1c3");
        chess_b.showPiecesText();
    }
}
