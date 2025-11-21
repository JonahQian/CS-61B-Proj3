package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;


public class Main {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    /**public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        World world = new World();
        TETile[][] emptyBoard = world.fillWithNothing();
        ter.renderFrame(emptyBoard);
        world.setMenu();
        StdDraw.show();

        long seed;
        TETile[][] board;

        if (Input.typedNorL(world, ter)) {
            while (Input.sNotEntered()) {
                ter.drawTiles(emptyBoard);
                world.setNewWorldPrompt();
                StdDraw.show();
            }
            seed = Input.getSeed(Input.getTxt());
            world.generateRooms(seed);
            board = world.getBoard();

            ter.renderFrame(board);
            world.putAvatar();
        } else {
            int sLocation = Input.load();
            String savedInput = Input.getTxt();
            String stringSeed = savedInput.substring(0, sLocation + 1);

            seed = Input.getSeed(stringSeed);
            world.generateRooms(seed);
            board = world.getBoard();

            ter.renderFrame(board);
            world.putAvatar();

            String removedQ = savedInput.replace(":q", "");
            for (int i = sLocation + 1; i < removedQ.length(); i++) {
                Input.movement(world.getAvatar(), world, removedQ.charAt(i));
            }
            Input.addCharacterToTXT();
            ter.drawTiles(board);
            world.renderTileName();
            StdDraw.show();
        }


        while (Input.whetherSaveAndQuit()) {
            Input.movement(world.getAvatar(), world);
            ter.drawTiles(board);
            world.renderTileName();
            StdDraw.show();
        }
        Input.saveOnFile();
    }*/


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        World world = new World();
        TETile[][] emptyBoard = world.fillWithNothing();
        ter.renderFrame(emptyBoard);
        world.setMenu();
        StdDraw.show();

        long seed;
        TETile[][] board;
        //String targetSavingText = "";

        if (typedNorL(world, ter)) {
            //targetSavingText += "n";
            while (sNotEntered(world)) {
                ter.drawTiles(emptyBoard);
                world.setNewWorldPrompt();
                StdDraw.show();
            }
            //seed = Input.getSeed(Input.getTxt());
            seed = Input.getSeed(world.getTxt());
            world.generateRooms(seed);
            board = world.getBoard();

            ter.renderFrame(board);
            world.putAvatar();
        } else {
            //targetSavingText += "l";
            int sLocation = Input.load(world);
            String savedInput = world.getTxt();
            String stringSeed = savedInput.substring(0, sLocation + 1);

            seed = Input.getSeed(stringSeed);
            world.generateRooms(seed);
            board = world.getBoard();

            ter.renderFrame(board);
            world.putAvatar();

            String removedQ = savedInput.replace(":q", "");
            for (int i = sLocation + 1; i < removedQ.length(); i++) {
                movement(world.getAvatar(), world, removedQ.charAt(i));
            }
            Input.addCharacterToTXT(world);
            ter.drawTiles(board);
            world.renderTileName();
            StdDraw.show();
        }


        while (Input.whetherSaveAndQuit(world)) {
            movement(world.getAvatar(), world);
            ter.drawTiles(board);
            world.renderTileName();
            StdDraw.show();
        }
        Input.saveOnFile(world.getTxt());
    }

    public static boolean typedNorL(World world, TERenderer ter) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (key == 'c') {
                    world.changeLanguage();
                    ter.drawTiles(world.fillWithNothing());
                    world.setMenu();
                    StdDraw.show();
                }
                if (key == 'q') {
                    System.exit(0);
                }
                if (key == 'n') {
                    world.addToTxt('n');
                    //txt += "n";
                    return true;
                } else if (key == 'l') {
                    world.addToTxt('l');
                    //txt += "l";
                    return false;
                }
            }
        }
    }

    public static boolean sNotEntered(World world) {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            if (key == 's' || key == 'S') {
                world.addToTxt('s');
                //txt += 's';
                return false;
            }
            world.addToTxt(key);
            //txt += key;
            return true;
        }
        return true;
    }

    public static void movement(Avatar a, World world) {
        TETile[][] board = world.getBoard();
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            key = Character.toLowerCase(key);
            if (key == 'w') {
                a.moveUp(board);
                world.addToTxt('w');
                //txt += "w";
            } else if (key == 'a') {
                a.moveLeft(board);
                world.addToTxt('a');
                //txt += "a";
            } else if (key == 's') {
                a.moveDown(board);
                world.addToTxt('s');
                //txt += "s";
            } else if (key == 'd') {
                a.moveRight(board);
                world.addToTxt('d');
                //txt += "d";
            } else if (key == 'e') {
                a.lightSwitch(world);
                world.addToTxt('e');
                //txt += "e";
            } else if (key == ':') {
                world.addToTxt(':');
                //txt += ":";
            } else if (key == 'q') {
                world.addToTxt('q');
                //txt += 'q';
            }
        }
    }


    public static void movement(Avatar a, World world, char currChar) {
        TETile[][] board = world.getBoard();
        char key = currChar;
        key = Character.toLowerCase(key);
        if (key == 'w') {
            a.moveUp(board);
            //txt += "w";
        } else if (key == 'a') {
            a.moveLeft(board);
            //txt += "a";
        } else if (key == 's') {
            a.moveDown(board);
            //txt += "s";
        } else if (key == 'd') {
            a.moveRight(board);
            //txt += "d";
        } else if (key == 'e') {
            a.lightSwitch(world);
            //txt += "e";
        }
    }
}
