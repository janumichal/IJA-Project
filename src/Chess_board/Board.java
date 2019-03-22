package Chess_board;

import Chess_pieces.*;
import enums.color_piece;

public class Board {
    private static final int BOARD_SIZE = 8;
    private Field[][] board_array = new Field[BOARD_SIZE][BOARD_SIZE];

    // constructor creates array and fills it with pieces
    public Board(){
        for (int y = 0; y < BOARD_SIZE; y++){
            for (int x = 0; x < BOARD_SIZE; x++){
                Field field = new Field(x, y);
                board_array[x][y] = field;
            }
        }
        fillArrayAround();
    }

    // show piece placement in text mode
    public void showPiecesText(){
        System.out.println("-------------------------------------------------------------------------");
        for (int i = 0; i < BOARD_SIZE; i++){
            System.out.print("|");
            for (int j = 0; j < BOARD_SIZE; j++){
                Piece piece = this.board_array[i][j].getPiece();
                if(piece instanceof Rook){
                    System.out.print("  Rook  ");
                }else if(piece instanceof Knight){
                    System.out.print(" Knight ");
                }else if(piece instanceof Bishop){
                    System.out.print(" Bishop ");
                }else if(piece instanceof King){
                    System.out.print("  King  ");
                }else if(piece instanceof Queen){
                    System.out.print(" Queen  ");
                }else if(piece instanceof Pawn){
                    System.out.print("  pawn  ");
                }else{
                    System.out.print("        ");
                }
                if (j == BOARD_SIZE-1){
                    System.out.println("|");
                }else {
                    System.out.print("|");
                }
            }
            System.out.println("-------------------------------------------------------------------------");
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
        int x = is_black ? 0 : BOARD_SIZE-1;
        for (int y = 0; y < BOARD_SIZE; y++){
            // place PAWN
            this.board_array[is_black ? x+1 : x-1][y].setPiece(new Pawn(x, y, color));
            switch (y){
                case 0:
                case (BOARD_SIZE-1):
                    // place ROOKS
                    this.board_array[x][y].setPiece(new Rook(x, y, color));
                    break;
                case 1:
                case (BOARD_SIZE-2):
                    // place KNIGHTS
                    this.board_array[x][y].setPiece(new Knight(x, y, color));
                    break;
                case 2:
                case (BOARD_SIZE-3):
                    //place BISHOPS
                    this.board_array[x][y].setPiece(new Bishop(x, y, color));
                    break;
                case 3:
                    // place KING
                    this.board_array[x][y].setPiece(new King(x, y, color));
                    break;
                case (BOARD_SIZE-4):
                    // place QUEEN
                    this.board_array[x][y].setPiece(new Queen(x, y, color));
                    break;
            }
        }
    }

    // fills array around in seperate field across whole board
    private void fillArrayAround(){
        for (int y = 0; y < BOARD_SIZE; y++){
            for (int x = 0; x < BOARD_SIZE; x++){
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
