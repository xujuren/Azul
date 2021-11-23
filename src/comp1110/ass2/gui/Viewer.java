package comp1110.ass2.gui;

import comp1110.ass2.*;
import comp1110.ass2.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.FileInputStream;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * author : Honggic,Oh
 * Azul game is playble with computer or user now
 */
public class Viewer extends Application {

    static Color[] col = {Color.BLUE, Color.GREEN, Color.ORANGE, Color.PURPLE, Color.RED, Color.BROWN};
    private static HashMap<String, ImageIcon> iconCache = new HashMap<String, ImageIcon>();
    private SpringLayout layout;
    enum MapIndex {
        centre(0),
        bag(1),
        discard(2),

        factory1(3),
        factory2(4),
        factory3(5),
        factory4(6),
        factory5(7),
        mosaicA(8),
        mosaicB(9),
        storageA(10),
        storageB(11),
        floorA(12),
        floorB(13);

        MapIndex(int value) {
            this.value = value;
        }

        private final int value;

        public int val() {
            return value;
        }
    }

    static double[][] defaultMapInfo = {
            //start_x,start_y,end_x,end_y, tile size, x column, row, each
            {900, 10, 1100, 100, 20, 4, 10} //centreX,Y
            , {900, 130, 1100, 340, 20, 10, 10} //bagX,Y
            , {900, 390, 1100, 600, 20, 10, 10} //discardX,Y
            , {200, 10, 280, 100, 40, 2, 2} //factory1X,Y  ,size
            , {320, 10, 400, 100, 40, 2, 2} ////factory2X,Y  ,size
            , {440, 10, 520, 100, 40, 2, 2} //factory3X,Y  ,size
            , {560, 10, 640, 100, 40, 2, 2} //factory4X,Y  ,size
            , {680, 10, 760, 100, 40, 2, 2} //factory5X,Y  ,size
            , {560, 130, 760, 340, 40, 5, 5} //userA - mosaicX,Y
            , {560, 390, 760, 600, 40, 5, 5} //userB - mosaicX,Y
            , {240, 130, 440, 340, 40, 5, 0} //userA - storageX,Y
            , {240, 390, 440, 600, 40, 5, 0} //userB - storageX,Y
            , {240, 340, 520, 380, 40, 1, 7} //userA -floorX,Y
            , {240, 600, 520, 640, 40, 1, 7} //userB -floorX,Y
    };
    static double[][]buttonInfo ={

    };
    static RadioButton rb1 = new RadioButton("Player");
    static RadioButton rb2 = new RadioButton("Player");
    static RadioButton rb3 = new RadioButton("Computer");
    static Label playerA = new Label();
    static Label playerB = new Label();
    ToggleGroup group = new ToggleGroup();
    private static final int VIEWER_WIDTH = 1280;
    private static final int VIEWER_HEIGHT = 768;
    static Label statelabel1 = new Label();
    static Label statelabel2 = new Label();
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group controls1 = new Group();
    private final Group moves = new Group();
    static TextField playerTextField = new TextField();
    static TextField boardTextField = new TextField();
    static TextField turnTextField = new TextField();
    static TextArea playerAfield = new TextArea();
    static TextArea playerBfield = new TextArea();
    static TextField gameState = new TextField();
    static Label playerAlabel = new Label();
    static Label playerBlabel = new Label();
    static Game board = new Game(2);

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     *              TASK 4
     */
    void displayState(String[] state) {
        // FIXME Task 4: implement the simple state viewer
        StringBuilder storagesA = new StringBuilder();
        StringBuilder storagesB = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            storagesA.append(Game.players[0].storage[i].toString());
            storagesB.append(Game.players[1].storage[i].toString());
        }

        System.out.println(board.getGameState()[0]);
        playerAfield.setText("score :" + Game.players[0].score.getScore() + "\n" + "Mosaic : " + Game.players[0].mosaic.toString().substring(1) + "\n" + "Storage : " + storagesA + "\n" + "Floor :" + Game.players[0].floor.toString().substring(1));
        playerBfield.setText("score :" + Game.players[1].score.getScore() + "\n" + "Mosaic : " + Game.players[1].mosaic.toString().substring(1) + "\n" + "Storage : " + storagesB + "\n" + "Floor :" + Game.players[1].floor.toString().substring(1));
        if (board.isAllFactoriesEmpty() && board.centre.isEmpty()) {
            turnTextField.setText((char)(board.curPlayer+'A')+"'s Scoring");
        } else {
            turnTextField.setText(((char) (board.curPlayer + 'A')) + "'s Turn");
        }
        turnTextField.setAlignment(Pos.CENTER);
          gameState.setText(board.getCurrentState());
        Collections.sort(board.discard);
        Collections.sort(board.centre);
        Collections.sort(board.bag);
    }

    /**
     * draw a tile
     */
    void drawingtile() {

        moves.getChildren().clear();
        root.getChildren().remove(moves);
        ArrayList<comp1110.ass2.Tile>[] drawObj = new ArrayList[14];
        drawObj[0] = board.centre;
        drawObj[1] = board.bag;
        drawObj[2] = board.discard;
        Factory[] factories = Game.factories;
        for (int i = 0; i < factories.length; i++) {
            drawObj[i + 3] = factories[i];
        }

        for (int i = 0; i < 8; i++) {
            drawMapObj(i, drawObj[i]);
        } //change here to drawObj[8~13], draw centre,bag,discard, and factories
        for (int i = 8; i < 14; i++) {
            double x, y, side;
            side = defaultMapInfo[i][4];
            if (i == MapIndex.mosaicA.val() || i == MapIndex.mosaicB.val()) {//drawing mosaic A and B
                Mosaic mosaic = Game.players[i % 2].mosaic;
                for (int l = 0; l < 5; l++) {
                    for (int k = 0; k < 5; k++) {
                        if (mosaic.mosaic[l][k] != null) {
                            char ch = (char) (mosaic.mosaic[l][k].val() + 'a');
                            x = defaultMapInfo[i][0] + k * side;
                            y = defaultMapInfo[i][1] + l * side;
                            ViewerTile tile = new ViewerTile(x, y, side - 2, ch, col[ch - 'a'], false);
                            moves.getChildren().add(tile);
                        }
                    }
                }
            } else if (i == MapIndex.storageA.val() || i == MapIndex.storageB.val()) {//drawing storage A and B
                Storage[] storage = Game.players[i % 2].storage;
                for (int l = 0; l < storage.length; l++) {
                    if (storage[l] != null && storage[l].currColor() != null) {
                        char ch = (char) (storage[l].currColor().val() + 'a');
                        int num = storage[l].getTileCount();
                        for (int k = 0; k < num; k++) {
                            x = defaultMapInfo[i][0] + k * side;
                            y = defaultMapInfo[i][1] + l * side;
                            ViewerTile tile = new ViewerTile(x, y, side - 2, ch, col[ch - 'a'], true);

                            moves.getChildren().add(tile);
                        }
                    }
                }
            } else if (i == MapIndex.floorA.val() || i == MapIndex.floorB.val()) {//drawing floor A and B
                Floor floor = Game.players[i % 2].floor;
                for (int k = 0; k < floor.size(); k++) {
                    char ch = (char) (floor.get(k).val() + 'a');
                    x = defaultMapInfo[i][0] + k * side;
                    y = defaultMapInfo[i][1];
                    ViewerTile tile = new ViewerTile(x, y, side - 2, ch, col[ch - 'a'], true);
                    moves.getChildren().add(tile);
                }
            }
        }
        root.getChildren().add(moves);
    }

    /**
     * next for next game and next turn.
     */
    public void next() {

        System.out.println("cur"+board.curPlayer + "/tf:"+ !Game.players[0].hasCompletedStorage());
        board.nextRound();//refill if all player ready OR calculate bounus if end.
        if(rb3.isSelected()) {

            while (board.curPlayer == 1) {

                String mov = board.generateAction();
                System.out.println("test2mov" + mov);
                if (board.isMoveValid(mov))
                    board.applyMove(mov);
                displayState(new String[]{playerTextField.getText(),
                        boardTextField.getText()});
                board.nextRound();
            }
        }
        drawingtile();
        displayState(new String[]{playerTextField.getText(),
                boardTextField.getText()});

        if(board.isGameEnd)
        {
            System.out.println(Game.players[0].getBonusPoints());
                System.out.println(Game.players[1].getBonusPoints());
                System.out.println(Game.players[0].mosaic.completedCol());
                System.out.println(Game.players[0].mosaic.completedRow());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Winner");
                if (Game.players[0].score.getScore() > Game.players[1].score.getScore()) {
                    alert.setHeaderText("PlayerA win , Score :" + Game.players[0].score.getScore());
                    alert.setContentText("PlayerB score :" + Game.players[1].score.getScore());
                } else if (Game.players[0].score.getScore() < Game.players[1].score.getScore()) {
                    alert.setHeaderText("PlayerB win , Score :" + Game.players[1].score.getScore());
                    alert.setContentText("PlayerA score :" + Game.players[0].score.getScore());
                } else {
                    if (Game.players[0].mosaic.countAllTiles() > Game.players[1].mosaic.countAllTiles()) {
                        alert.setHeaderText("PlayerA win , Score :" + Game.players[0].score.getScore());
                        alert.setContentText("PlayerB score :" + Game.players[1].score.getScore());
                    } else if (Game.players[0].mosaic.countAllTiles() < Game.players[1].mosaic.countAllTiles()) {
                        alert.setHeaderText("PlayerB win , Score :" + Game.players[1].score.getScore());
                        alert.setContentText("PlayerA score :" + Game.players[0].score.getScore());
                    } else {
                        alert.setHeaderText("Draw");
                        alert.setContentText("PlayerA Score :" + Game.players[0].score.getScore() + "PlayerB Score :" + Game.players[1].score.getScore());
                    }
                }

                Optional<ButtonType> result = alert.showAndWait();
                moves.getChildren().clear();
                board = new Game(2);

        }

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    class ViewerTile extends StackPane {
        Text t;
        Polygon p;
        int locMap;

        private double orgx;
        private double orgy;
        private double mousex;
        private double mousey;
        boolean movable = false;

        /**
         *
         * @param locMap the tile's previous location
         * @param newlocMap the tile's new location
         * @return return x and y location
         */
        double[] modifyReleaseLocate (int locMap, int newlocMap){
            double[] temp = new double[2];
            double sideT = defaultMapInfo[locMap][4];
            temp[0] = (this.getLayoutX() - defaultMapInfo[newlocMap][0]) % sideT;
            temp[1] = (this.getLayoutY() - defaultMapInfo[newlocMap][1]) % sideT;

            int row = 0;
            if (temp[0] > (sideT) / 3) {
                temp[0] = this.getLayoutX() + (sideT - temp[0]);
            } else {
                temp[0] = this.getLayoutX() - temp[0];
            }
            if (temp[1] > (sideT) / 3) {
                temp[1] = this.getLayoutY() + (sideT - temp[1]);
            } else {
                temp[1] = this.getLayoutY() - temp[1];
            }

            return temp;
        }

        /**
         * @param x x's layout of starting point
         * @param y y's layout of starting point
         * @param side the tile's side length(tile's side)
         * @param ch tile's alphabet(a,b,c,d,e,f) for movable tile
         * @param col tile's color for background tile
         * @param movable Decide tile is movable
         */
        ViewerTile(double x, double y, double side, char ch, Color col, boolean movable) {
            locMap = checkLoc(x, y);
            p = new Polygon(0.0, 0.0, side, 0, side, side, 0, side);
            p.setLayoutX(x);
            p.setLayoutY(y);
            p.setFill(col);
            t = new Text("" + ch);
            this.setLayoutX(x);
            this.setLayoutY(y);
            this.getChildren().addAll(p, t);
            this.movable = movable;
            this.setOnMousePressed(event -> {
                displayState(new String[]{playerTextField.getText(),boardTextField.getText()});
                orgx = getLayoutX();
                orgy = getLayoutY();

                if (this.movable) {
                    mousex = event.getSceneX();
                    mousey = event.getSceneY();
                    toFront();
                }
            });
            this.setOnMouseDragged(event -> {
                if (this.movable) {

                    setLayoutX(getLayoutX() + event.getSceneX() - mousex);
                    setLayoutY(getLayoutY() + event.getSceneY() - mousey);
                    mousex = event.getSceneX();
                    mousey = event.getSceneY();
                }
            });
            this.setOnMouseReleased(event -> {

                if (this.movable) {
                    int newlocMap = checkLoc(event.getSceneX(), event.getSceneY());
                    if (newlocMap == -1) {
                        setLayoutX(orgx);
                        setLayoutY(orgy);
                        return;
                    }
                    double sideT = defaultMapInfo[newlocMap][4];
                    double[] temp = modifyReleaseLocate (locMap, newlocMap);
                    double tempX_M = temp[0];
                    double tempY_M = temp[1];

                    int row=(int) ((tempY_M - defaultMapInfo[newlocMap][1]) / sideT);
                    int column = (int) ((tempX_M - defaultMapInfo[newlocMap][0]) / sideT);

                    char player,from, to;
                    Tile tile1 = Tile.fromInt((t.getText().charAt(0) - 'a'));



                    //storage to mosaic or floor
                    if ((locMap == MapIndex.storageA.val() && (newlocMap == MapIndex.mosaicA.val() || newlocMap == MapIndex.floorA.val()))
                            || (locMap == MapIndex.storageB.val() && (newlocMap == MapIndex.mosaicB.val() || newlocMap == MapIndex.floorB.val())) ) {

                        if (board.isAllFactoriesEmpty() && board.centre.isEmpty()) {
                            int orgrow = (int) ((orgy - defaultMapInfo[locMap][1]) / sideT);

                            System.out.println("row:"+row + "/col:"+column +"/orgrow"+orgrow);
                            String moveMosaic ;
                            if(newlocMap == MapIndex.floorA.val() || newlocMap == MapIndex.floorB.val())
                                moveMosaic =(char) (newlocMap % 2 + 'A') + Integer.toString(orgrow)+ 'F';
                            else{
                                moveMosaic = (char) (newlocMap % 2 + 'A') + Integer.toString(row) + column;
                            }
                            System.out.println(moveMosaic);
                            System.out.println("/valid:"+board.isMoveValid(moveMosaic));

                            if (board.isMoveValid(moveMosaic)) {
                                board.applyMove(moveMosaic);

                            }
                            displayState(new String[]{playerTextField.getText(),boardTextField.getText()});

                        }

                    }
                    //factory, centre to storage
                    else if (locMap == MapIndex.centre.val() || (locMap >= MapIndex.factory1.val() && locMap <= MapIndex.factory5.val())) {

                        if (newlocMap <= MapIndex.floorB.val() && newlocMap >= MapIndex.storageA.val()) {
                            //else if moved to storage or floor
                            player = (char) (newlocMap % 2 + 'A');
                            char tilech = t.getText().charAt(0);
                            from = 'C';
                            to = 'F';
                            if (locMap >= 3 && locMap <= 8)
                                from = Integer.toString(locMap - 3).charAt(0);
                            if (newlocMap == MapIndex.storageA.val() || newlocMap == MapIndex.storageB.val()) { // (Y- startY > (X-startX)
                                double halfY = tempX_M - 1 * defaultMapInfo[newlocMap][0] + defaultMapInfo[newlocMap][1]; // defaultMapInfo[newlocMap][3] - sideT;

                                to = Integer.toString(row).charAt(0);

                                if (row > 4 || ((tempY_M < halfY))) {
                                    setLayoutX(orgx);
                                    setLayoutY(orgy);
                                    return;
                                }
                            }
                            String move = "" + player + from + tilech + to;
                            System.out.println("move: " + move);
                            if (board.isMoveValid(move)) {
                                board.applyMove(move);
                                //if action, draw all again
                                drawingtile();

                            }displayState(new String[]{playerTextField.getText(),boardTextField.getText()});
                            System.out.println("tile '" + t.getText() + "' moved from obj " + locMap + " to " + newlocMap + "/x:" + event.getSceneX() + "/y:" + event.getSceneY());
                        }
                    }
                    else {
                        System.out.println("else");
                    }
                }
                next();
                drawingtile();
            });
        }

        /**
         * @param x layout of x
         * @param y layout of y
         * @return moving is valid or not
         */
        int checkLoc(double x, double y) {
            for (int i = 0; i < defaultMapInfo.length; i++) {

                if (x >= defaultMapInfo[i][0] && x <= defaultMapInfo[i][2]) {
                    if (y >= defaultMapInfo[i][1] && y <= defaultMapInfo[i][3]) {
                        return i;
                    }
                }
            }
            return -1;
        }
    }

    private void makeControls(){
        Label boardLabel = new Label("Board State:");
        boardLabel.setFont(Font.font(15));;
        gameState = new TextField();gameState.setPrefWidth(400);gameState.setPrefHeight(10);gameState.setLayoutX(700);gameState.setLayoutY(VIEWER_HEIGHT - 50);
        playerA.setText("PlayerA");
        playerA.setFont(Font.font(15));
        playerB.setText("PlayerB");
        playerB.setFont(Font.font(15));
        Button button1 = new Button("start");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                board = new Game(2);
                drawingtile();
                displayState(new String[]{playerTextField.getText(),boardTextField.getText()});
            }
        });

        HBox hb = new HBox();
        hb.getChildren().addAll( boardLabel,gameState,button1 );
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().addAll(hb);


        VBox vb1 = new VBox();
        VBox vb2 = new VBox();

        vb1.setLayoutX(650);
        vb2.setLayoutX(720);
        vb1.setLayoutY(620);
        vb2.setLayoutY(620);
        vb1.getChildren().addAll(playerA);
        vb2.getChildren().addAll(playerB);
        controls1.getChildren().addAll(vb1,vb2);
    }
    @Override

    public void start(Stage primaryStage) throws Exception {
        StackPane pane = new StackPane();

        pane.setPrefSize(1280,768);
        Image image;

        Path currentRelativePath = Paths.get("");
        String rootPath = currentRelativePath.toAbsolutePath().toString();
        image = new Image(new FileInputStream( rootPath + "/assets/backgroundbeach.jpg"));
        pane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));


        primaryStage.setTitle("Azul Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(pane);
        root.getChildren().addAll(controls,controls1);
        makeControls();
        primaryStage.setScene(scene);
        for (int i = 0; i < defaultMapInfo.length; i++) {
            drawMapObj(i, false);
        }
        rb1.setLayoutX(650);
        rb1.setLayoutY(650);
        rb2.setLayoutX(720);
        rb2.setLayoutY(650);
        rb3.setLayoutX(720);
        rb3.setLayoutY(670);
        rb1.setSelected(true);
        rb2.setSelected(true);
        rb2.setToggleGroup(group);
        rb3.setToggleGroup(group);
        controls.getChildren().addAll(statelabel1, statelabel2,rb1,rb2,rb3);
        playerAlabel.setText("PlayerA State");
        playerAlabel.setFont(Font.font(15));
        playerAlabel.setLayoutX(10);playerAlabel.setLayoutY(170);
        playerBlabel.setText("PlayerB State");playerBlabel.setLayoutX(10);playerBlabel.setLayoutY(430);
        playerBlabel.setFont(Font.font(15));
        playerAfield = new TextArea();playerAfield.setPrefWidth(200);playerAfield.setPrefHeight(150);playerAfield.setLayoutX(10);playerAfield.setLayoutY(190);
        turnTextField.setPrefHeight(50);turnTextField.setPrefWidth(80);turnTextField.setLayoutX(130);turnTextField.setLayoutY(130);
        playerBfield = new TextArea();playerBfield.setPrefWidth(200);playerBfield.setPrefHeight(150);playerBfield.setLayoutX(10);playerBfield.setLayoutY(450);
        root.getChildren().addAll(playerAlabel, playerBlabel, playerAfield, playerBfield, turnTextField);
        primaryStage.show();

    }

    /**
     * @param mapInd   classify the background's section.(Centre,Factories,Mosaic,Storage ...)
     * @param isActive decide tile is movable or not
     */
    public void drawMapObj(int mapInd, boolean isActive) {
        double max_i = defaultMapInfo[mapInd][5];
        double max_j = defaultMapInfo[mapInd][6];

        int i = 0;
        int j = 0;

        if (isActive) {
            j = (int) (defaultMapInfo[mapInd][7] % max_j);
            i = (int) (defaultMapInfo[mapInd][7] / max_j);

            defaultMapInfo[mapInd][7]++;
        }

        for (; i < max_i; i++) {

            if (mapInd == MapIndex.storageA.val() || mapInd == MapIndex.storageB.val())     //
                max_j = i + 1;

            for (; j < max_j; j++) {

                double side = defaultMapInfo[mapInd][4];

                ViewerTile tile;
                if (mapInd == MapIndex.mosaicA.val() || mapInd == MapIndex.mosaicB.val()) {
                    tile = new ViewerTile(j * side + defaultMapInfo[mapInd][0], i * side + defaultMapInfo[mapInd][1], side - 2, ' ', Color.WHITESMOKE, false);
                    tile.p.setStroke(Color.INDIGO);
                    tile.p.setStrokeWidth(0.1);
                } else {
                    tile = new ViewerTile(j * side + defaultMapInfo[mapInd][0], i * side + defaultMapInfo[mapInd][1], side - 2,' ',Color.WHITESMOKE,false);
                    tile.p.setStroke(Color.INDIGO);
                    tile.p.setStrokeWidth(0.2);
                }
                root.getChildren().add(tile);

            }
            j = 0;
        }
    }




    /**
     * @param mapInd     classify the background's section.(Centre,Factories,Mosaic,Storage ...)
     * @param tileArrays tile's color from array
     */
    public void drawMapObj(int mapInd, ArrayList<Tile> tileArrays) {
        double max_i = defaultMapInfo[mapInd][5];
        double max_j = defaultMapInfo[mapInd][6];
        double side = defaultMapInfo[mapInd][4];
        int count = 0;
        for (int i = 0; i < max_i; i++) {

            if (mapInd == MapIndex.storageA.val() || mapInd == MapIndex.storageB.val())     //
                max_j = i + 1;
            for (int j = 0; j < max_j; j++) {
                ViewerTile tile;
                if (count >= tileArrays.size()) {
                    return;
                }
                int colorIndex = tileArrays.get(count).val();
                count++;

                if (mapInd == MapIndex.mosaicA.val() || mapInd == MapIndex.mosaicB.val()) {
                    tile = new ViewerTile(j * side + defaultMapInfo[mapInd][0], i * side + defaultMapInfo[mapInd][1], side - 2, ' ', Color.WHITESMOKE, false);
                    tile.p.setStroke(col[(int) ((max_j * i + j - i) % max_i)]);
                } else {
                    tile = new ViewerTile(j * side + defaultMapInfo[mapInd][0], i * side + defaultMapInfo[mapInd][1], side - 2, (char) (colorIndex + 'a'), col[colorIndex], true);
                }
                moves.getChildren().add(tile);

            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


}
