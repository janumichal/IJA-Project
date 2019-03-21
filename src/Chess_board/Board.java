package Chess_board;

public class Board {
    private static final int BOARD_SIZE = 8;
    private Field[][] board_array = new Field[BOARD_SIZE][BOARD_SIZE];

    // constructor creates array and fills it with pieces
    public Board(){
        for (int y = 0; y < BOARD_SIZE; y++){
            for (int x = 0; x < BOARD_SIZE; x++){
                Field field = new Field(x, y);
                board_array[x][y]=field;
            }
        }


    }

    // fills array around in seperate field across whole board
    public void fillArrayAround(){

    }
}
