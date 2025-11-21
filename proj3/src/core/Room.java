package core;

import java.util.Random;

import static utils.RandomUtils.uniform;

public class Room {
    static final int MINLENGTH = 2;
    static final int MAXLENGTH = 10;

    public void generateRooms(long seedID) {
        Random seed = new Random(seedID);
        int width = uniform(seed, MINLENGTH, MAXLENGTH);
        int height = uniform(seed, MINLENGTH, MAXLENGTH);


    }
}
