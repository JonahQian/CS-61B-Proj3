package core;
import tileengine.TETile;
import tileengine.Tileset;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {
        World world = new World();

        long seed;
        TETile[][] board;
        if (input.startsWith("n")) {
            int sLocation = Input.load(world, input);

            String savedInput = world.getTxt();
            String stringSeed = savedInput.substring(0, sLocation + 1);

            seed = Input.getSeed(stringSeed);
            world.generateRooms(seed);
            board = world.getBoard();

            world.putAvatar();

            String removedQ = savedInput.replace(":q", "");
            for (int i = sLocation + 1; i < removedQ.length(); i++) {
                Main.movement(world.getAvatar(), world, removedQ.charAt(i));
            }

            if (input.endsWith(":q")) {
                Input.saveOnFile(world.getTxt());
            }
            return board;
        } else {
            int sLocation = Input.load(world);
            Input.addToTxt(input.substring(1), world);
            String savedInput = world.getTxt();
            String stringSeed = savedInput.substring(0, sLocation + 1);

            seed = Input.getSeed(stringSeed);
            world.generateRooms(seed);
            board = world.getBoard();

            world.putAvatar();

            String removedQ = savedInput.replace(":q", "");
            for (int i = sLocation + 1; i < removedQ.length(); i++) {
                Main.movement(world.getAvatar(), world, removedQ.charAt(i));
            }

            if (input.endsWith(":q")) {
                Input.saveOnFile(world.getTxt());
            }

            return board;
        }

    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
