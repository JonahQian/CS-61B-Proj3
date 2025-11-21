package core;
import utils.FileUtils;

public class Input {

    //private static String txt = "";
    private static final String SAVE_FILE = "save.txt";

    /**public static boolean typedNorL(World world, TERenderer ter) {
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
                    txt += "n";
                    return true;
                } else if (key == 'l') {
                    txt += "l";
                    return false;
                }
            }
        }
    }

    public static boolean sNotEntered() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            if (key == 's' || key == 'S') {
                txt += 's';
                return false;
            }
            txt += key;
            return true;
        }
        return true;
    }*/


    public static long getSeed(String input) {
        input = input.toLowerCase();
        int nIndex = 0;
        int sIndex = input.indexOf("s");
        return Long.parseLong(input.substring(nIndex + 1, sIndex));
    }

    /**public static String getTxt() {
        return txt;
    }

    public static void movement(Avatar a, World world) {
        TETile[][] board = world.getBoard();
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            key = Character.toLowerCase(key);
            if (key == 'w') {
                a.moveUp(board);
                txt += "w";
            } else if (key == 'a') {
                a.moveLeft(board);
                txt += "a";
            } else if (key == 's') {
                a.moveDown(board);
                txt += "s";
            } else if (key == 'd') {
                a.moveRight(board);
                txt += "d";
            } else if (key == 'e') {
                a.lightSwitch(world);
                txt += "e";
            } else if (key == ':') {
                txt += ":";
            } else if (key == 'q') {
                txt += 'q';
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

    public static void resetTxt() {
        txt = "";
    }*/

    public static boolean whetherSaveAndQuit(World world) {
        String currTxt = world.getTxt();
        return !currTxt.endsWith(":q");
    }

    /**public static void saveOnFile() {
     txt = txt.replace("o", "");
     FileUtils.writeFile(SAVE_FILE, txt);
     //System.exit(0);
     }*/

    public static void saveOnFile(String saveTxt) {
        saveTxt = saveTxt.replace(":q", "u");
        saveTxt = saveTxt.replace(":", "");
        saveTxt = saveTxt.replace("q", "");
        saveTxt = saveTxt.replace("o", "");
        saveTxt = saveTxt.replace("u", ":q");
        FileUtils.writeFile(SAVE_FILE, saveTxt);
        System.exit(0);
    }

    public static int load(World world) {
        //txt = FileUtils.readFile(SAVE_FILE);
        String currTxt = FileUtils.readFile(SAVE_FILE);
        world.setTxt(currTxt);
        int sLocation = 0;
        String seed = "";
        //for (int i = 0; i < txt.length(); i++) {
        for (int i = 0; i < currTxt.length(); i++) {
            if (currTxt.charAt(i) == 's') {
                seed = currTxt.substring(1, i);
                sLocation = i;
                break;
            }
        }
        return sLocation;
    }

    public static int load(World world, String customInput) {
        //txt = customInput;
        String currTxt = customInput;
        world.setTxt(currTxt);
        int sLocation = 0;
        String seed = "";
        for (int i = 0; i < currTxt.length(); i++) {
            if (currTxt.charAt(i) == 's') {
                seed = currTxt.substring(1, i);
                sLocation = i;
                break;
            }
        }
        return sLocation;
    }

    public static void addCharacterToTXT(World world) {
        world.addToTxt('o');
        //txt += "o";
    }

    public static void addToTxt(String addedPart, World world) {
        for (int i = 0; i < addedPart.length(); i++) {
            world.addToTxt(addedPart.charAt(i));
        }
        //txt += addedPart;
    }
}
