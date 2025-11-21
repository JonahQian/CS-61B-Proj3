package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.util.Random;

import static utils.RandomUtils.uniform;

public class World {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 15;
    private static final double OCCUPIED_RATE = 0.2;
    private TETile[][] board;
    private int occupied;
    private Avatar avatar;
    private boolean language = true;
    private Random seed;
    private String txtCurrStatus;

    public World() {
        this.board = new TETile[WIDTH][HEIGHT];
        //TERenderer ter = new TERenderer();
        //ter.initialize(WIDTH, HEIGHT);

        //TETile[][] board = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                board[x][y] = Tileset.NOTHING;
            }
        }
        //ter.renderFrame(board);
        this.occupied = 0;
        this.txtCurrStatus = "";
    }

    public void closeLight(int x, int y, TETile currTile) {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (inBounds(x + i, y + j) && (board[x + i][y + j].equals(Tileset.FLOOR0)
                        || board[x + i][y + j].equals(Tileset.FLOOR1) || board[x + i][y + j].equals(Tileset.FLOOR2)
                        || board[x + i][y + j].equals(Tileset.FLOOR3)) || board[x + i][y + j].equals(Tileset.AVATAR)) {
                    if (board[x + i][y + j] == Tileset.AVATAR) {
                        avatar.changeUnderAvator(Tileset.FLOOR);
                    } else {
                        board[x + i][y + j] = Tileset.FLOOR;
                    }
                }
            }
        }
        board[x][y] = currTile;
    }

    public void generateLight(int x, int y, TETile currTile) {
        for (int i = 0; i <= 3; i++) {
            for (int j = 0; j <= 3; j++) {
                if (inBounds(x + i, y + j) && !board[x + i][y + j].equals(Tileset.NOTHING)
                        && !board[x + i][y + j].equals(Tileset.WALL)) {
                    if (board[x + i][y + j] == Tileset.AVATAR) {
                        avatar.changeUnderAvator(Tileset.FLOOR3);
                    } else {
                        board[x + i][y + j] = Tileset.FLOOR3;
                    }
                }
            }
        }
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (inBounds(x + i, y + j) && !board[x + i][y + j].equals(Tileset.NOTHING)
                        && !board[x + i][y + j].equals(Tileset.WALL)) {
                    if (board[x + i][y + j] == Tileset.AVATAR) {
                        avatar.changeUnderAvator(Tileset.FLOOR2);
                    } else {
                        board[x + i][y + j] = Tileset.FLOOR2;
                    }
                }
            }
        }
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (inBounds(x + i, y + j) && !board[x + i][y + j].equals(Tileset.NOTHING)
                        && !board[x + i][y + j].equals(Tileset.WALL)) {
                    if (board[x + i][y + j] == Tileset.AVATAR) {
                        avatar.changeUnderAvator(Tileset.FLOOR1);
                    } else {
                        board[x + i][y + j] = Tileset.FLOOR1;
                    }
                }
            }
        }
        board[x][y] = currTile;
    }

    public void generateRooms(long seedID) {
        this.seed = new Random(seedID);
        int currWidth = 0;
        int currHeight = 0;
        int currX = 0;
        int currY = 0;

        while (notEnough()) {
            int width = uniform(seed, MIN_LENGTH, MAX_LENGTH);
            int height = uniform(seed, MIN_LENGTH, MAX_LENGTH);
            int x = uniform(seed, 0, WIDTH);
            int y = uniform(seed, 0, HEIGHT);

            if (roomInBounds(width, height, x, y) && !occupied(width, height, x, y)) {
                for (int i = x; i < (x + width); i++) {
                    for (int j = y; j < (y + height); j++) {
                        board[i][j] = Tileset.FLOOR;
                        occupied += 1;
                    }
                }
                generateLight(x, y, Tileset.FLOOR0);
                if (currWidth != 0) {
                    generateHallway(currWidth, currHeight, currX, currY, width, height, x, y);
                }
                currWidth = width;
                currHeight = height;
                currX = x;
                currY = y;
            }
        }
        generateWalls(board);
    }

    public void putAvatar() {
        outerLoop:
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (board[i][j] == Tileset.FLOOR) {
                    avatar = new Avatar(i, j, board);
                    break outerLoop;
                }
            }
        }
        //Main.movement(avatar, this);
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void generateWalls(TETile[][] boards) {
        for (int i = 0; i < boards.length; i++) {
            for (int j = 0; j < boards[0].length; j++) {
                if (boards[i][j] == Tileset.FLOOR || boards[i][j] == Tileset.FLOOR0
                        || boards[i][j] == Tileset.FLOOR1 || boards[i][j] == Tileset.FLOOR2
                        || boards[i][j] == Tileset.FLOOR3 || boards[i][j] == Tileset.FLOOR0CLOSED) {
                    if (boards[i - 1][j] == Tileset.NOTHING) {
                        boards[i - 1][j] = Tileset.WALL;
                    }
                    if (boards[i][j - 1] == Tileset.NOTHING) {
                        boards[i][j - 1] = Tileset.WALL;
                    }
                    if (boards[i + 1][j] == Tileset.NOTHING) {
                        boards[i + 1][j] = Tileset.WALL;
                    }
                    if (boards[i][j + 1] == Tileset.NOTHING) {
                        boards[i][j + 1] = Tileset.WALL;
                    }
                    if (boards[i - 1][j - 1] == Tileset.NOTHING) {
                        boards[i - 1][j - 1] = Tileset.WALL;
                    }
                    if (boards[i + 1][j - 1] == Tileset.NOTHING) {
                        boards[i + 1][j - 1] = Tileset.WALL;
                    }
                    if (boards[i + 1][j + 1] == Tileset.NOTHING) {
                        boards[i + 1][j + 1] = Tileset.WALL;
                    }
                    if (boards[i - 1][j + 1] == Tileset.NOTHING) {
                        boards[i - 1][j + 1] = Tileset.WALL;
                    }
                }
            }
        }
    }

    private void generateHallway(int currWidth, int currHeight, int currX, int currY,
                                 int width, int height, int x, int y) {
        int xOverlap = overlap(currX, currWidth, x, width);
        int yOverlap = overlap(currY, currHeight, y, height);

        if (xOverlap != 0) {
            for (int tempY = Math.min(y, currY); tempY < Math.max(y, currY); tempY++) {
                if (board[xOverlap][tempY] == Tileset.NOTHING) {
                    board[xOverlap][tempY] = Tileset.FLOOR;
                    occupied += 1;
                }
            }

        } else if (yOverlap != 0) {
            for (int tempX = Math.min(x, currX); tempX < Math.max(x, currX); tempX++) {
                if (board[tempX][yOverlap] == Tileset.NOTHING) {
                    board[tempX][yOverlap] = Tileset.FLOOR;
                    occupied += 1;
                }
            }

        } else {
            boolean largeXLargeYSameSet;
            int largeX = Math.max(x, currX);
            int targetX;
            if (largeX == x) {
                targetX = uniform(seed, currX, currX + currWidth);
            } else {
                targetX = uniform(seed, x, x + width);
            }
            int largeY = Math.max(y, currY);
            int targetY;
            if (largeY == y) {
                targetY = uniform(seed, currY, currY + currHeight);
                largeXLargeYSameSet = largeX == x;
            } else {
                targetY = uniform(seed, y, y + height);
                largeXLargeYSameSet = largeX == currX;
            }


            if (largeXLargeYSameSet) {
                for (int i = targetX; i <= largeX; i++) {
                    if (board[i][targetY] == Tileset.NOTHING) {
                        board[i][targetY] = Tileset.FLOOR;
                        occupied += 1;
                    }
                }
                for (int j = largeY; j >= targetY; j--) {
                    if (board[largeX][j] == Tileset.NOTHING) {
                        board[largeX][j] = Tileset.FLOOR;
                        occupied += 1;
                    }
                }
            } else {
                for (int i = targetX; i <= largeX; i++) {
                    if (board[i][targetY] == Tileset.NOTHING) {
                        board[i][targetY] = Tileset.FLOOR;
                        occupied += 1;
                    }
                }
                for (int j = largeY; j >= targetY; j--) {
                    if (board[targetX][j] == Tileset.NOTHING) {
                        board[targetX][j] = Tileset.FLOOR;
                        occupied += 1;
                    }
                }
            }


        }
    }

    private int overlap(int currCoordinate, int currLength, int coordinate, int length) {
        if (currCoordinate < coordinate) {
            if (currCoordinate + currLength <= coordinate) {
                return 0;
            } else {
                return uniform(seed, coordinate, Math.min(currCoordinate + currLength, coordinate + length));
            }
        } else if (currCoordinate > coordinate) {
            if (coordinate + length <= currCoordinate) {
                return 0;
            } else {
                return uniform(seed, currCoordinate, Math.min(coordinate + length, currCoordinate + currLength));
            }
        } else {
            return uniform(seed, coordinate, coordinate + Math.min(currLength, length));
        }
    }

    public void changeLanguage() {
        language = !language;
    }

    public void renderTileName() {
        if (language) {
            renderTileNameEnglish();
        } else {
            renderTileNameChinese();

        }
    }
    public void renderTileNameEnglish() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String text;
        TETile currTile;
        StdDraw.setPenColor(255, 255, 255);

        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            currTile = board[x][y];
        } else {
            currTile = Tileset.NOTHING;
        }

        if (currTile == Tileset.FLOOR0) {
            text = "Light Source (On)";
        } else if (currTile == Tileset.WALL) {
            text = "Wall";
        } else if (currTile == Tileset.FLOOR1 || currTile == Tileset.FLOOR2 || currTile == Tileset.FLOOR3) {
            text = "Floor Lighted Up";
        } else if (currTile == Tileset.FLOOR) {
            text = "Floor Un-Lighted";
        } else if (currTile == Tileset.AVATAR) {
            text = "Avatar";
        } else if (currTile == Tileset.FLOOR0CLOSED) {
            text = "Light Source (Off)";
        } else {
            text = "Nothing";
        }

        StdDraw.textLeft(1, 1, "Tile: " + text);
    }

    public void renderTileNameChinese() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String text;
        TETile currTile;
        StdDraw.setPenColor(255, 255, 255);

        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            currTile = board[x][y];
        } else {
            currTile = Tileset.NOTHING;
        }

        if (currTile == Tileset.FLOOR0) {
            text = "灯（开着）";
        } else if (currTile == Tileset.WALL) {
            text = "墙";
        } else if (currTile == Tileset.FLOOR1 || currTile == Tileset.FLOOR2 || currTile == Tileset.FLOOR3) {
            text = "被照亮的地板";
        } else if (currTile == Tileset.FLOOR) {
            text = "未被照亮的地板";
        } else if (currTile == Tileset.AVATAR) {
            text = "钱汉";
        } else if (currTile == Tileset.FLOOR0CLOSED) {
            text = "灯（关着）";
        } else {
            text = "虚无";
        }

        StdDraw.textLeft(1, 1, "鼠标所在: " + text);
    }

    private boolean occupied(int width, int height, int x, int y) {
        for (int i = x - 2; i < (x + width + 2); i++) {
            for (int j = y - 2; j < (y + height + 2); j++) {
                if (!board[i][j].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inBounds(int x, int y) {
        if (x > 1 && x < WIDTH - 1) {
            return y > 1 && y < HEIGHT - 1;
        }
        return false;
    }

    private boolean roomInBounds(int width, int height, int x, int y) {
        return inBounds(x, y)
                && inBounds(x + width, y) && inBounds(x, y + height) && inBounds(x + width, y + height);
    }

    private boolean notEnough() {
        double size = (double) WIDTH * HEIGHT;
        return (double) occupied < (size * OCCUPIED_RATE);
    }

    public TETile[][] getBoard() {
        return board;
    }

    public TETile[][] fillWithNothing() {
        TETile[][] emptyBoard = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                emptyBoard[i][j] = Tileset.NOTHING;
            }
        }
        return emptyBoard;
    }

    public void setMenu() {
        if (language) {
            setMenuEnglish();
        } else {
            setMenuChinese();

        }
    }

    public void setMenuEnglish() {
        StdDraw.setPenColor(253, 181, 21);
        Font font = new Font("Consolas", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(40, 30, "CS 61B Project 3");

        Font newFont = new Font("Consolas", Font.BOLD, 20);
        StdDraw.setFont(newFont);
        StdDraw.text(40, 15, "New Game (N)");
        StdDraw.text(40, 13, "Load Game (L)");
        StdDraw.text(40, 11, "Quit Game (Q)");
        StdDraw.text(40, 9, "Change Language(C)");

        StdDraw.setFont();
    }

    public void setMenuChinese() {
        StdDraw.setPenColor(253, 181, 21);
        Font font = new Font("Consolas", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(40, 30, "CS 61B 项目 3");

        Font newFont = new Font("Consolas", Font.BOLD, 20);
        StdDraw.setFont(newFont);
        StdDraw.text(40, 15, "新游戏 (N)");
        StdDraw.text(40, 13, "加载游戏 (L)");
        StdDraw.text(40, 11, "退出游戏 (Q)");
        StdDraw.text(40, 9, "更换语言 (C)");


        StdDraw.setFont();
    }

    public void setNewWorldPrompt() {
        if (language) {
            setNewWorldPromptEnglish();
        } else {
            setNewWorldPromptChinese();

        }
    }

    public void setNewWorldPromptEnglish() {
        StdDraw.setPenColor(253, 181, 21);
        Font font = new Font("Consolas", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(40, 30, "Enter A Random Seed (type 's' when finished)");

        Font newFont = new Font("Consolas", Font.BOLD, 20);
        StdDraw.setFont(newFont);
        StdDraw.text(40, 15, "Seed Entered So Far: ");

        String currSeed = getTxt().substring(1);
        StdDraw.text(40, 13, currSeed);

        StdDraw.setFont();
    }

    public void setNewWorldPromptChinese() {
        StdDraw.setPenColor(253, 181, 21);
        Font font = new Font("Consolas", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(40, 30, "输入种子 (输完按s)");

        Font newFont = new Font("Consolas", Font.BOLD, 20);
        StdDraw.setFont(newFont);
        StdDraw.text(40, 15, "种子：");

        String currSeed = getTxt().substring(1);
        StdDraw.text(40, 13, currSeed);

        StdDraw.setFont();
    }

    public void setTxt(String str) {
        this.txtCurrStatus = str;
    }

    public void addToTxt(char c) {
        this.txtCurrStatus += c;
    }

    public String getTxt() {
        return this.txtCurrStatus;
    }

    public void clearTxt() {
        this.txtCurrStatus = "";
    }
}
