package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Engine extends Main implements Serializable {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    private int numberOfCalls = 0;
    private int multiplierPn;
    private int lastX;
    private int lastY;
    private UI testing;
    private UI startWstring;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void interactWithKeyboard() throws javax.sound.sampled.LineUnavailableException,
            java.io.IOException, javax.sound.sampled.UnsupportedAudioFileException {

        File audioFile = new File("/Users/nabkizil/Desktop/sp19-s1684/proj3/music/theme.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);

        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        audioClip.start();

        testing = new byow.Core.Engine.UI();
    }


    public TETile[][] interactWithInputString(String input) {


        long seed = 0;
        int counter = 0;
        startWstring = new byow.Core.Engine.UI(69);
        input = input.toUpperCase();
        boolean repeater = false;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'N') {
                repeater = true;
            } else if (input.charAt(i) == 'L') {
                startWstring = startWstring.loadGame();
                counter = 1;
            } else if (input.charAt(i) == ':') {
                startWstring.quitGame();

            } else if (input.charAt(i) == 'Q') {
                continue;
            } else if (input.charAt(i) != 'S' && counter == 0) {
                seed = seed * 10 + Character.getNumericValue(input.charAt(i));
            } else if (input.charAt(i) == 'S' && counter == 0) {
                RandWorldCreator newGame =
                        new RandWorldCreator(seed);
                TETile[][] board = new TETile[WIDTH][HEIGHT];
                newGame.wCreator(board);
                startWstring.board = board;
                startWstring.randomUI = new Random(seed);
                startWstring.placeItems();
                counter = 1;
            } else if (input.charAt(i) == 'W') {
                startWstring.moveUp();

            } else if (input.charAt(i) == 'A') {
                startWstring.moveLeft();

            } else if (input.charAt(i) == 'S') {
                startWstring.moveDown();

            } else if (input.charAt(i) == 'D') {
                startWstring.moveRight();
            }
        }

        return startWstring.board;
    }

    private class UI implements Serializable {
        private TETile[][] board;
        private TERenderer ter;
        private long seed;
        private  StringBuilder seedString = new StringBuilder();
        private boolean menuSelect = true;
        private boolean seedSetter = true;
        private Random randomUI;
        private int playerXposition;
        private int playerYposition;
        private String[] itemsList = {"Heart", "Sword", "Fruit", "Fries", "U A STAR"};
        int hiddenEnemyX;
        int hiddenEnemyY;
        int enemyX;
        int enemyY;
        int[][] multiEnemy = new int [100][100];
        int[] enemiesAlive = new int[100];
        int enemyCount = 0;
        int moves = 0;
        boolean hasHeart = false;
        boolean hasSword = false;
        boolean enemyAlive = true;
        int pws;

        private UI() {
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT + 2);
            mainMenu();

        }

        private UI(int x) {
            this.pws = 69;
        }
        private void mainMenu() {
            StdDraw.clear(Color.black);
            Font font = new Font("Arial", Font.BOLD, 36);
            StdDraw.setFont(font);
            StdDraw.setPenColor(Color.red);
            StdDraw.text(WIDTH / 2, HEIGHT - 15,
                    "CS61B: the Game");

            Font menuFont = new Font("Arial", Font.BOLD, 24);
            StdDraw.setFont(menuFont);
            StdDraw.setPenColor(Color.orange);
            StdDraw.text(WIDTH / 2, HEIGHT - 20, "New Game (N)");
            StdDraw.text(WIDTH / 2, HEIGHT - 22, "Load Game (L)");
            StdDraw.text(WIDTH / 2, HEIGHT - 24, "Quit (Q)");
            StdDraw.show();

            Font gameFont = new Font("Arial", Font.BOLD, 16);
            StdDraw.setFont(gameFont);
            while (menuSelect) {
                if (StdDraw.isKeyPressed(78)) {
                    menuSelect = false;
                    newGame();
                } else if (StdDraw.isKeyPressed(76)) {
                    menuSelect = false;
                    loadGame();
                } else if (StdDraw.isKeyPressed(81)) {
                    menuSelect = false;
                    quitGame();
                }
            }
        }

        private TETile[][] makeBoard(String input) {
            input = input.toUpperCase();
            if (input.charAt(0) == 'N') {
                for (int i = 1; i < input.length(); i++) {
                    if (input.charAt(i) != 's') {
                        seed = seed * 10 + Character.getNumericValue(input.charAt(i));
                    }
                }
            }

            if (input.charAt(0) == 'Q') {
                return null;
            }
            RandWorldCreator newGame =
                    new RandWorldCreator(this.seed);
            board = new TETile[WIDTH][HEIGHT];
            newGame.wCreator(this.board);
            return board;
        }

        private void newGame() {
            StdDraw.clear(Color.black);
            StdDraw.text(WIDTH / 2, HEIGHT / 2,
                    "Please enter random seed and then press the 'S' key.");
            StdDraw.show();
            while (seedSetter) {
                if (StdDraw.hasNextKeyTyped()) {
                    char temp = StdDraw.nextKeyTyped();

                    if (temp == 's') {
                        seedSetter = false;
                        seedString.append(temp);
                        this.board = makeBoard(seedString.toString());
                        StringBuilder buildIntSeed = new StringBuilder();
                        for (int i = 1; i < seedString.toString().length() - 1; i++) {
                            buildIntSeed.append(seedString.toString().charAt(i));
                        }
                        this.seed = Integer.parseInt(buildIntSeed.toString());
                        this.randomUI = new Random(this.seed);
                    }
                    seedString.append(temp);
                }

            }
            ter.renderFrame(this.board);
            placeItems();


        }

        private void placeItems() {
            boolean setPieces = true;
            int piecePlacer = 0;
            while (setPieces) {
                int startX = this.randomUI.nextInt(WIDTH);
                int startY = this.randomUI.nextInt(HEIGHT);
                if (this.board[startX][startY].equals(Tileset.MYFLOOR)) {
                    if (piecePlacer == 0) {
                        this.board[startX][startY] = Tileset.HEART;
                        piecePlacer += 1;
                    } else if (piecePlacer == 1) {
                        this.board[startX][startY] = Tileset.SWORD;
                        piecePlacer += 1;
                    } else if (piecePlacer == 2) {
                        this.board[startX][startY] = Tileset.APPLE;
                        piecePlacer += 1;
                    } else if (piecePlacer == 3) {
                        enemyX = startX;
                        enemyY = startY;
                        this.board[startX][startY] = Tileset.ENEMY;
                        piecePlacer += 1;
                    } else if (piecePlacer == 4) {
                        this.board[startX][startY] = Tileset.FRIES;
                        piecePlacer += 1;
                    } else if (piecePlacer == 5) {
                        this.board[startX][startY] = Tileset.STAR;
                        piecePlacer += 1;
                    } else if (piecePlacer == 6) {
                        hiddenEnemyX = startX;
                        hiddenEnemyY = startY;
                        piecePlacer += 1;
                    } else if (piecePlacer == 7) {
                        setPieces = false;
                        this.playerXposition = startX;
                        this.playerYposition = startY;
                    }
                }
            }
            if (!(pws == 69)) {
                playGame();
            }
        }

        public void additional() {
            ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT + 2);
        }

        public void playGame() {
            Tileset.PLAYER.draw(playerXposition, playerYposition);
            if (pws == 69) {
                additional();
            }
            ter.renderFrame(this.board);
            Tileset.PLAYER.draw(playerXposition, playerYposition);
            StdDraw.show();
            boolean playingGame = true;
            int mouseX = (int) StdDraw.mouseX();
            int mouseY = (int) StdDraw.mouseY();
            while (playingGame) {
                if ((int) StdDraw.mouseX() != mouseX || (int) StdDraw.mouseY() != mouseY) {
                    ter.renderFrame(this.board);
                    Tileset.PLAYER.draw(playerXposition, playerYposition);
                    try {
                        StdDraw.textLeft(0, HEIGHT + 1,
                                this.board[(int) StdDraw.mouseX()][(int)
                                        StdDraw.mouseY()].description());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }

                    StdDraw.textLeft(20, HEIGHT + 1,
                            "Items You Haven't Collected: " + Arrays.toString(itemsList));
                    StdDraw.show();
                    mouseX = (int) StdDraw.mouseX();
                    mouseY = (int) StdDraw.mouseY();
                }
                if (StdDraw.hasNextKeyTyped()) {
                    char temp = StdDraw.nextKeyTyped();
                    if (temp == 'w') {
                        this.moveUp();
                        newPosition();
                    }
                    if (temp == 'a') {
                        this.moveLeft();
                        newPosition();
                    }
                    if (temp == 's') {
                        this.moveDown();
                        newPosition();
                    }
                    if (temp == 'd') {
                        this.moveRight();
                        newPosition();
                    }

                    if (temp == 'q') {
                        quitGame();
                    } else {
                        continue;
                    }
                }
            }
        }

        public void spawnEnemy() {
            int check = 10;
            if (moves % 10 == 0) {
                boolean placePieces = true;
                while (placePieces && check != 0) {
                    int startX = this.randomUI.nextInt(WIDTH);
                    int startY = this.randomUI.nextInt(HEIGHT);
                    if (board[startX][startY].equals(Tileset.MYFLOOR) && enemyCount < 100) {
                        board[startX][startY] = Tileset.ENEMY;
                        int[] x = {startX, startY, 1};
                        multiEnemy[enemyCount] = x;
                        placePieces = false;
                        enemyCount += 1;
                    }
                    check -= 1;
                }
            }
        }

        public void avatarStatus(int i) {
            if (hasSword) {
                multiEnemy[i][2] = 0;
                board[multiEnemy[i][0]][multiEnemy[i][1]] = Tileset.FLOOR;
                hasSword = false;
            } else if (hasHeart) {
                hasHeart = false;
            } else {
                StdDraw.clear(new Color(0, 0, 0));
                StdDraw.picture(WIDTH / 2, HEIGHT / 2, "../byow/Core/mission_failed_meme.jpg");
                StdDraw.show();
                StdDraw.pause(10000);
                System.exit(0);
            }
        }

        public void moveEnemies() {
            for (int i = 0; i < enemyCount; i++) {
                if (multiEnemy[i][2] == 1) {
                    int enemyx = multiEnemy[i][0];
                    int enemyy = multiEnemy[i][1];
                    if (enemyx == playerXposition && enemyy == playerYposition) {
                        avatarStatus(i);
                    }
                    boolean work = false;
                    int check = 3;
                    while (!work && check != 0) {
                        switch (this.randomUI.nextInt(3)) {
                            case 0:
                                if (this.board[enemyx][enemyy + 1].description().equals("floor")) {
                                    this.board[enemyx][enemyy + 1] = Tileset.ENEMY;
                                    this.board[enemyx][enemyy] = Tileset.MYFLOOR;
                                    multiEnemy[i][1] += 1;
                                    work = true;
                                } else if (this.board[enemyx]
                                        [enemyy + 1].description().equals("player")) {
                                    avatarStatus(i);

                                    work = true;
                                }
                                break;
                            case 1:
                                if (this.board[enemyx + 1][enemyy].description().equals("floor")) {
                                    this.board[enemyx + 1][enemyy] = Tileset.ENEMY;
                                    this.board[enemyx][enemyy] = Tileset.MYFLOOR;
                                    multiEnemy[i][0] += 1;
                                    work = true;
                                } else if (this.board[enemyx + 1]
                                        [enemyy].description().equals("player")) {
                                    avatarStatus(i);
                                    work = true;
                                }
                                break;
                            case 2:
                                if (this.board[enemyx][enemyy - 1].description().equals("floor")) {
                                    this.board[enemyx][enemyy - 1] = Tileset.ENEMY;
                                    this.board[enemyx][enemyy] = Tileset.MYFLOOR;
                                    multiEnemy[i][1] -= 1;
                                    work = true;
                                } else if (this.board[enemyx]
                                        [enemyy - 1].description().equals("player")) {
                                    avatarStatus(i);
                                    work = true;
                                }
                                break;
                            case 3:
                                if (this.board[enemyx - 1][enemyy].description().equals("floor")) {
                                    this.board[enemyx - 1][enemyy] = Tileset.ENEMY;
                                    this.board[enemyx][enemyy] = Tileset.MYFLOOR;
                                    multiEnemy[i]
                                            [0] -= 1;
                                    work = true;
                                } else if (this.board[enemyx - 1]
                                        [enemyy].description().equals("player")) {
                                    avatarStatus(i);
                                    work = true;
                                }
                                break;
                            default:
                                this.board[enemyX - 1][enemyY] = Tileset.ENEMY;
                                this.board[enemyX][enemyY] = Tileset.MYFLOOR;
                                multiEnemy[i][0] -= 1;
                                break;
                        }
                        check -= 1;
                    }
                    check = 3;
                }
            }
        }

        public void moveUp() {
            if (!this.board[this.playerXposition]
                    [this.playerYposition + 1].description().equals("wall")) {
                if (!this.board[this.playerXposition]
                        [this.playerYposition + 1].description().equals("floor")) {
                    if (!(pws == 69)) {
                        itemInteraction(this.board[this.playerXposition]
                                [this.playerYposition + 1].description());
                    }
                    this.board[this.playerXposition]
                            [this.playerYposition + 1] = Tileset.MYFLOOR;
                }
                this.playerYposition += 1;
            }
        }

        public void moveLeft() {
            if (!this.board[this.playerXposition - 1]
                    [this.playerYposition].description().equals("wall")) {
                if (!this.board[this.playerXposition - 1]
                        [this.playerYposition].description().equals("floor")) {
                    if (!(pws == 69)) {
                        itemInteraction(this.board[this.playerXposition - 1]
                                [this.playerYposition].description());
                    }
                    this.board[this.playerXposition - 1]
                            [this.playerYposition] = Tileset.MYFLOOR;
                }
                this.playerXposition -= 1;
            }

        }
        public void moveDown() {
            if (!this.board[this.playerXposition]
                    [this.playerYposition - 1].description().equals("wall")) {
                if (!this.board[this.playerXposition]
                        [this.playerYposition - 1].description().equals("floor")) {
                    if (!(pws == 69)) {
                        itemInteraction(this.board[this.playerXposition]
                                [this.playerYposition - 1].description());
                    }
                    this.board[this.playerXposition]
                            [this.playerYposition - 1] = Tileset.MYFLOOR;
                }
                this.playerYposition -= 1;
            }
        }

        public void moveRight() {
            if (!this.board[this.playerXposition + 1]
                    [this.playerYposition].description().equals("wall")) {
                if (!this.board[this.playerXposition + 1]
                        [this.playerYposition].description().equals("floor")) {
                    if (!(pws == 69)) {
                        itemInteraction(this.board[this.playerXposition + 1]
                                [this.playerYposition].description());
                    }
                    this.board[this.playerXposition + 1][this.playerYposition] = Tileset.MYFLOOR;
                }
                this.playerXposition += 1;
            }

        }



        private void newPosition() {
            moveEnemy();
            this.ter.renderFrame(this.board);
            Tileset.PLAYER.draw(this.playerXposition, this.playerYposition);

            StdDraw.textLeft(0, HEIGHT + 1,
                    this.board[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description());
            StdDraw.textLeft(20, HEIGHT + 1,
                    "Items You Haven't Collected: " + Arrays.toString(itemsList));
            StdDraw.show();
        }

        public void moveEnemy() {
            if (enemyAlive) {
                switch (this.randomUI.nextInt(3)) {
                    case 0:
                        if (this.board[enemyX][enemyY + 1].description().equals("floor")) {
                            this.board[enemyX][enemyY + 1] = Tileset.ENEMY;
                            this.board[enemyX][enemyY] = Tileset.MYFLOOR;
                            enemyY += 1;
                        }
                        break;
                    case 1:
                        if (this.board[enemyX + 1]
                                [enemyY].description().equals("floor")) {
                            this.board[enemyX + 1]
                                    [enemyY] = Tileset.ENEMY;
                            this.board[enemyX]
                                    [enemyY] = Tileset.MYFLOOR;
                            enemyX += 1;
                        }
                        break;
                    case 2:
                        if (this.board[enemyX]
                                [enemyY - 1].description().equals("floor")) {
                            this.board[enemyX]
                                    [enemyY - 1] = Tileset.ENEMY;
                            this.board[enemyX]
                                    [enemyY] = Tileset.MYFLOOR;
                            enemyY -= 1;
                        }
                        break;
                    case 3:
                        if (this.board[enemyX - 1][enemyY].description().equals("floor")) {
                            this.board[enemyX - 1][enemyY] = Tileset.ENEMY;
                            this.board[enemyX][enemyY] = Tileset.MYFLOOR;
                            enemyX -= 1;
                        }
                        break;
                    default:
                        this.board[enemyX - 1][enemyY] = Tileset.ENEMY;
                        this.board[enemyX][enemyY] = Tileset.MYFLOOR;
                        enemyX -= 1;
                        break;
                }
            }
        }

        private void itemInteraction(String item) {
            if (item.equals("Heart")) {
                hasHeart = true;
            }
            if (item.equals("Sword")) {
                hasSword = true;
            }
            if (item.equals("Enemy")) {
                if (hasSword) {
                    enemyAlive = false;
                } else {
                    StdDraw.clear(new Color(0, 0, 0));
                    StdDraw.picture(WIDTH / 2, HEIGHT / 2, "../proj3/pics/OVER.jpg");
                    StdDraw.show();
                    StdDraw.pause(10500);
                    System.exit(0);
                }
                return;

            }
            if (itemsList.length == 1) {
                victory();
            }
            String[] updatedItems = new String[itemsList.length - 1];
            int updatedItemsIndex = 0;
            for (int i = 0; i < itemsList.length; i++) {
                if (item.equals(itemsList[i])) {
                    itemsList[i] = null;
                    continue;
                }
                updatedItems[updatedItemsIndex] = itemsList[i];
                updatedItemsIndex += 1;
            }
            itemsList = updatedItems;

        }
        private void victory() {
            StdDraw.clear(Color.black);
            StdDraw.picture(WIDTH / 2, (HEIGHT / 2) - 3, "../proj3/pics/WIN.png");
            StdDraw.show();
            StdDraw.pause(6500);
            StdDraw.clear(Color.black);
            StdDraw.setPenColor(Color.orange);
            StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
            StdDraw.text(WIDTH / 2, (HEIGHT / 2) - 4, "Quit (Q)");
            StdDraw.show();
            boolean success = true;
            while (success) {
                if (StdDraw.isKeyPressed(78)) {
                    success = false;
                    UI x = new UI();
                    x.mainMenu();
                } else if (StdDraw.isKeyPressed(81)) {
                    success = false;
                    quitGame();
                }
            }

        }
        private UI loadGame() {
            File f = new File("game.txt");
            if (f.exists()) {
                try {
                    FileInputStream fis = new FileInputStream("game.txt");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    byow.Core.Engine.UI result = (byow.Core.Engine.UI) ois.readObject();
                    ois.close();
                    if (pws != 69) {
                        result.playGame();
                    }
                    return result;


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return new UI();
        }

        private void quitGame() {
            try {
                FileOutputStream fos = new FileOutputStream("game.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (pws != 69) {
                System.exit(0);
            }
        }


    }

    private class RandWorldCreator {
        private Random RANDOM;
        private long seed;
        private int currX;
        private int currY;

        private RandWorldCreator(long seed) {
            this.seed = seed;
            this.RANDOM = new Random(this.seed);
            this.currX = RANDOM.nextInt(WIDTH - 2);
            this.currY = RANDOM.nextInt(HEIGHT - 2);
        }

        public void wCreator(TETile[][] board) {

            int multiplierPicker = RANDOM.nextInt(1);
            int ranCalls = RANDOM.nextInt(30) + 10;
            if (numberOfCalls == 0) {
                fillBoard(board);
            }
            if (numberOfCalls > ranCalls) {
                wrapWalls(board);
                boarderPatrol(board);
                return;
            } else {
                for (int i = 0; i < ranCalls; i++) {
                    switch (multiplierPicker) {
                        case 0:
                            multiplierPn = 1;
                            break;
                        case 1:
                            multiplierPn = -1;
                            break;
                        default:
                            multiplierPn = 1;
                    }
                    int tileNum = RANDOM.nextInt(2);
                    switch (tileNum) {
                        case 0:
                            room(board);
                            connectTheDots(board);
                            numberOfCalls += 1;
                            break;
                        case 1:
                            hallway(board);
                            connectTheDots(board);
                            numberOfCalls += 1;
                            break;
                        default:
                            hallway(board);
                            connectTheDots(board);
                            numberOfCalls += 1;
                            break;
                    }
                }
            }
            wrapWalls(board);
            boarderPatrol(board);
            return;
        }

        private void room(TETile[][] board) {
            int roomW = RANDOM.nextInt(6) + 2;
            int roomH = RANDOM.nextInt(6) + 2;
            for (int x = 0; x < roomW; x++) {
                if (checkCurrX(x * multiplierPn)) {
                    int ran = RANDOM.nextInt();
                    RandWorldCreator oneMore =
                            new RandWorldCreator(ran);
                    oneMore.wCreator(board);
                    return;
                }

                for (int y = 0; y < roomH; y++) {
                    if (checkCurrY(y * multiplierPn)) {
                        int ran = RANDOM.nextInt();
                        RandWorldCreator oneMore =
                                new RandWorldCreator(ran);
                        oneMore.wCreator(board);
                        return;
                    }
                    board[currX + (x * multiplierPn)][currY + (y * multiplierPn)] = Tileset.MYFLOOR;
                    lastX = currX + (x * multiplierPn);
                    lastY = currY + (y * multiplierPn);
                }
            }
            currX += (roomW - 1) * multiplierPn;
            currY += (roomH - 1) * multiplierPn;


        }

        private void hallway(TETile[][] board) {
            int pick = RANDOM.nextInt(2);
            int rando = RANDOM.nextInt(1);
            switch (pick) {
                case 0: {
                    verticalHall(board);
                    switch (rando) {
                        case 0: {
                            horizontalHall(board);
                            break;
                        }
                        default:
                            break;
                    }
                    break;
                }
                case 1: {
                    horizontalHall(board);
                    switch (rando) {
                        case 0: {
                            verticalHall(board);
                            break;
                        }
                        default:
                            break;
                    }
                    break;
                }
                default:
                    horizontalHall(board);
            }
        }

        private void verticalHall(TETile[][] board) {
            int height = RANDOM.nextInt(7) + 1;
            int counterY = 0;
            for (int i = 0; i <= height; i++) {
                if (checkCurrY(i * multiplierPn)) {
                    RandWorldCreator oneMore =
                            new RandWorldCreator(RANDOM.nextInt());
                    oneMore.wCreator(board);
                    return;
                }
                if (i == 0) {
                    counterY += 0;
                } else {
                    counterY += multiplierPn;
                }

                board[currX][currY + (i * multiplierPn)] = Tileset.MYFLOOR;

                lastX = currX;
                lastY = currY + (i * multiplierPn);
            }
            currY += counterY;

        }

        private void horizontalHall(TETile[][] board) {
            int width = RANDOM.nextInt(8) + 1;
            int counterX = 0;
            for (int i = 0; i < width; i++) {
                if (checkCurrX(i * multiplierPn)) {
                    int nextInt = RANDOM.nextInt();
                    RandWorldCreator anotherOne =
                            new RandWorldCreator(nextInt);
                    anotherOne.wCreator(board);
                    return;
                }
                if (i == 0) {
                    counterX += 0;
                } else {
                    counterX += multiplierPn;
                }
                board[currX + (i * multiplierPn)][currY] = Tileset.MYFLOOR;
                lastX = currX + (i * multiplierPn);
                lastY = currY;
            }
            currX += counterX;

        }

        private void fillBoard(TETile[][] board) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    board[x][y] = Tileset.TREE;
                }
            }
        }

        private boolean checkCurrX(int i) {
            return !(1 <= currX + i && currX + i < WIDTH - 2);
        }

        private boolean checkCurrY(int i) {
            return !(1 <= currY + i && currY + i < HEIGHT - 2);
        }

        private void connectTheDots(TETile[][] board) {
            int maxX;
            int maxY;
            int maxXy;
            int maxYx;
            int minX;
            int minXy;
            int minY;
            int minYx;
            if (lastX > currX) {
                maxX = lastX;
                maxXy = lastY;
                minX = currX;
                minXy = currY;
            } else {
                maxX = currX;
                maxXy = currY;
                minX = lastX;
                minXy = lastY;
            }

            if (lastY > currY) {
                maxY = lastY;
                maxYx = lastX;
                minY = currY;
                minYx = currX;
            } else {
                maxY = currY;
                maxYx = currX;
                minY = lastY;
                minYx = lastX;
            }

            for (int i = 0; i < maxX - minX; i++) {
                board[minX + i][minXy] = Tileset.MYFLOOR;
            }
            for (int j = 0; j < maxY - minY; j++) {
                if (maxY == maxXy) {
                    board[maxX][minY + j] = Tileset.MYFLOOR;
                } else {
                    board[maxX][maxY - j] = Tileset.MYFLOOR;
                }
            }
        }

        private void wrapWalls(TETile[][] board) {
            for (int x = 0; x < WIDTH - 1; x++) {
                for (int y = 0; y < HEIGHT - 1; y++) {
                    if (board[x][y].equals(Tileset.TREE)
                            ||
                            ((x == 0 || y == 0) && board[x][y] == Tileset.MYFLOOR)) {
                        if (x == 0) {
                            try {
                                if (board[1][y].equals(Tileset.MYFLOOR)
                                        || board[1][y + 1].equals(Tileset.MYFLOOR)
                                        || board[1][y - 1].equals(Tileset.MYFLOOR)) {
                                    board[0][y] = Tileset.MYWALL;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                        if (y == 0) {
                            try {
                                if (board[x][1].equals(Tileset.MYFLOOR)
                                        || board[x + 1][1].equals(Tileset.MYFLOOR)
                                        || board[x - 1][1].equals(Tileset.MYFLOOR)) {
                                    board[x][0] = Tileset.MYWALL;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                        if (x > 0 && y > 0) {
                            for (int xI = -1; xI < 2; xI++) {
                                for (int yI = -1; yI < 2; yI++) {
                                    if (board[(x + xI)][(y + yI)].equals(Tileset.MYFLOOR)) {
                                        board[x][y] = Tileset.MYWALL;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        private void boarderPatrol(TETile[][] board) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                        if (board[x][y] == Tileset.MYFLOOR) {
                            board[x][y] = Tileset.TREE;
                        }
                    }
                }
            }
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    if (x == 0 || x == WIDTH - 1 || y == 0 || y == HEIGHT - 1) {
                        if (board[x][y] == Tileset.MYFLOOR) {
                            board[x][y] = Tileset.TREE;
                        }
                    } else if (board[x][y] == Tileset.MYWALL) {
                        if (board[x + 1][y] != Tileset.MYFLOOR) {
                            if (board[x - 1][y] != Tileset.MYFLOOR) {
                                if (board[x][y + 1] != Tileset.MYFLOOR) {
                                    if (board[x][y - 1] != Tileset.MYFLOOR) {
                                        if (board[x + 1][y + 1] != Tileset.MYFLOOR) {
                                            if (board[x - 1][y - 1] != Tileset.MYFLOOR) {
                                                if (board[x - 1][y + 1] != Tileset.MYFLOOR) {
                                                    if (board[x + 1][y - 1] != Tileset.MYFLOOR) {
                                                        board[x][y] = Tileset.TREE;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
