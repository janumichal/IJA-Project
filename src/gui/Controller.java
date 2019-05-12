package gui;

import Chess_pieces.*;
import enums.color_piece;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.Timer;
import java.util.TimerTask;

import Chess_common.*;
import javafx.util.Duration;


public class Controller {
    @FXML public GridPane chessBoardView;
    @FXML private Pane main;
    private Piece currentPiece;
    private Boolean click = false;
    private Field from;
    private Field to;
    private int speed = 1;
    private int moveCount;
    private Pane board[][] = new Pane[8][8];

    private Tab tab = new Tab();

    @FXML ListView<String> listView;
    @FXML private Slider slider;
    @FXML private Label body;

    /**
     * initialize listener for slider
     */
    public void initialize() {

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            speed = newValue.intValue();
        });
        body.setText("Body: " + tab.game.board.printPoints());
    }

    /**
     * initialize listener for mouse click
     * @param event mouse click listener
     */
    @FXML protected void onMouseClick(MouseEvent event){

        for (Node node : chessBoardView.getChildren()) {
            if (node instanceof Pane){
                Pane pane = (Pane)node;
                board[Character.getNumericValue(pane.getId().charAt(4))][Character.getNumericValue(pane.getId().charAt(6))] = pane;
            }
        }

        double width = chessBoardView.getWidth();
        double height = chessBoardView.getHeight();
        if (!click) {
            int col = (int) (event.getX() / width * 8);
            int row = (int) (event.getY() / height * 8);
            click = true;
            System.out.println("From " + row + " : " + col);
            from = tab.game.board.getField(col,row);
            currentPiece = from.getPiece();
            System.out.println(currentPiece instanceof Pawn ? "ano" : "ne");
            if (currentPiece == null) return;



        }
        else if (click){
            int col2 = (int) (event.getX() / width * 8);
            int row2 = (int) (event.getY() / height * 8);
            System.out.println("To " + row2 + " : " + col2);
            click = false;

            to = tab.game.board.getField(col2,row2);
            tab.move(from, to);
            moveCount++;
            Pane oldPane = board[currentPiece.getRow()][currentPiece.getCol()];
            Image image = null;

            for (Node view : oldPane.getChildren()) {
                if (view instanceof ImageView) {
                    image = ((ImageView) view).getImage();
                    ((ImageView) view).setImage(null);
                }

            }

            drawBoard(chessBoardView,tab);
            loadListFromMove();
        }


    }

    /**
     * initialize listener for mouse click
     * @param event mouse click listener
     */
    @FXML public void handleMouseClick(MouseEvent event) {
        listView.getSelectionModel().getSelectedItem();

        int count = (listView.getSelectionModel().getSelectedIndex()) * 2;
        for (int i = 0; i < count-1; i++){
            tab.undo();

        }
        listView.getItems().clear();
        loadListFromMove();
        drawBoard(chessBoardView, tab);

    }
int count = 0;
    Timer timer;
    /**
     * start the game and timer
     * @param event event listener
     */
    @FXML protected void start(ActionEvent event){
        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        count++;
                        System.out.println(count);
                        tab.next();
                        drawBoard(chessBoardView, tab);
                    }
                });
            }
        }, 0, speed*1000);
    }



    /**
     * stop the game and timer
     * @param event event listener
     */
    @FXML protected void stop(ActionEvent event){
        timer.cancel();
        timer.purge();
    }

    /**
     * undo move
     * @param event event listener
     */
    @FXML protected void undo(ActionEvent event){
        tab.undo();
        loadListFromMove();
        drawBoard(chessBoardView, tab);
    }

    /**
     * redo move
     * @param event event listener
     */
    @FXML protected void redo(ActionEvent event){
        tab.redo();
        loadListFromMove();
        drawBoard(chessBoardView, tab);
    }

    /**
     * next move
     * @param event event listener
     */
    @FXML protected void next(ActionEvent event){
        tab.next();
        loadListFromMove();
        drawBoard(chessBoardView, tab);
    }

    /**
     * previous move
     * @param event event listener
     */
    @FXML protected void prew(ActionEvent event) {
        tab.prew();
        loadListFromMove();
        drawBoard(chessBoardView, tab);
    }

    /**
     * restart the game
     * @param event event listener
     */
    @FXML protected void restart(ActionEvent event){
        tab.newGame();
        listView.getItems().clear();
        loadListFromMove();
        drawBoard(chessBoardView, tab);
    }

    /**
     * load moves from file
     * @param event event listener
     */
    @FXML protected void load(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(main.getScene().getWindow());
        ArrayList<String> lines = new ArrayList<String>();

        try {
            StringBuilder str_b = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                str_b.append(line);
                str_b.append("\n");
            }
            br.close();
            tab.game.loadAllMoves(str_b.toString());
            loadListFromMove();
        }
        catch(Exception e){

        }

    }

    /**
     * load list of moves
     */
    public void loadListFromMove(){
        listView.getItems().clear();
        String[] radky = tab.game.printAllMoves().split("\n");
        for (String one_line: radky){
            listView.getItems().add(one_line);
        }
    }

    /**
     * save moves to file
     * @param event event listener
     */
    @FXML protected void save(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(main.getScene().getWindow());

        if(file != null){

            SaveFile(tab.game.printAllMoves(), file);

        }
    }

    /**
     * save file
     * @param content content of the file
     * @param file name of the file
     */
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (Exception e) {

        }

    }

    /**
     * set game type
     * @param event event listener
     */
    @FXML protected void gameType(ActionEvent event){
        tab.start_auto();
    }

    /**
     * place figures to board
     * @param pane actual field
     * @param tab actual tab
     */
    public void drawBoard(Pane pane, Tab tab){
        body.setText("Body: " + tab.game.board.printPoints());
        for (Node node : pane.getChildren()) {
            try {
                ((Pane)node).getChildren().clear();
            }
            catch (Exception e){
            }

            int img_size = 50;
            Integer y = GridPane.getColumnIndex(node);
            Integer x = GridPane.getRowIndex(node);
            int row = y == null ? 0 : y;
            int col = x == null ? 0 : x;
            if (node instanceof Pane) {
                node.setId("pane" + row + "_" + col);
            }
            if ((row + col) % 2 == 0) {
                node.setStyle("-fx-background-color: rgb(255,255,255);");
            } else {
                node.setStyle("-fx-background-color: rgb(128,128,128);");
            }

            ImageView img;

            Piece piece = tab.game.board.getField(row, col).getPiece();
            if(piece instanceof Rook){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Rook_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Rook_b.png"));
                }
            }else if(piece instanceof Knight){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Knight_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Knight_b.png"));
                }
            }else if(piece instanceof Bishop){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Bishop_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Bishop_b.png"));
                }
            }else if(piece instanceof King){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/King_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/King_b.png"));
                }
            }else if(piece instanceof Queen){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Queen_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Queen_b.png"));
                }
            }else if(piece instanceof Pawn){
                if (piece.getColor() == color_piece.WHITE){
                    img = new ImageView(new Image("chess_figures_img/Pawn_w.png"));
                }else{
                    img = new ImageView(new Image("chess_figures_img/Pawn_B.png"));
                }
            }else{
                img = new ImageView();
                img.setImage(null);
            }
            if (node instanceof Pane){
                img.setFitHeight(img_size);
                img.setFitWidth(img_size);
                ((Pane)node).getChildren().add(img);
            }
        }
    }
}
