import enums.*;
import objects.*;

public class Main {
    public static void main(String[] args) {
        chess_piece queen = new chess_piece(chess_type.QUEEN,color_piece.BLACK);
        System.out.println("je to : "+ queen.getType()+"  a je barvy : "+queen.getColor()+" a je hodnoty : "+queen.getValue_of_piece());
    }
}
