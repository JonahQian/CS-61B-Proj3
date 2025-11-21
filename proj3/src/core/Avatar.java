package core;

import tileengine.TETile;
import tileengine.Tileset;

public class Avatar {
    private int x;
    private int y;
    private TETile underAvatar;
    private TETile avatar = Tileset.AVATAR;

    public Avatar(int i, int j, TETile[][] board) {
        this.x = i;
        this.y = j;
        underAvatar = board[this.x][this.y];
        board[this.x][this.y] = avatar;
    }

    public void changeUnderAvator(TETile tetile) {
        underAvatar = tetile;
    }

    public void moveUp(TETile[][] board) {
        if (canMove(x, y + 1, board)) {
            board[this.x][this.y] = underAvatar;
            this.y = this.y + 1;
            underAvatar = board[this.x][this.y];
            board[this.x][this.y] = avatar;
        }
    }

    public void moveLeft(TETile[][] board) {
        if (canMove(x - 1, y, board)) {
            board[this.x][this.y] = underAvatar;
            this.x = this.x - 1;
            underAvatar = board[this.x][this.y];
            board[this.x][this.y] = avatar;
        }
    }

    public void moveRight(TETile[][] board) {
        if (canMove(x + 1, y, board)) {
            board[this.x][this.y] = underAvatar;
            this.x = this.x + 1;
            underAvatar = board[this.x][this.y];
            board[this.x][this.y] = avatar;
        }
    }

    public void moveDown(TETile[][] board) {
        if (canMove(x, y - 1, board)) {
            board[this.x][this.y] = underAvatar;
            this.y = this.y - 1;
            underAvatar = board[this.x][this.y];
            board[this.x][this.y] = avatar;
        }
    }

    public void lightSwitch(World world) {
        if (underAvatar == Tileset.FLOOR0) {
            world.closeLight(x, y, Tileset.AVATAR);
            underAvatar = Tileset.FLOOR0CLOSED;
        } else if (underAvatar == Tileset.FLOOR0CLOSED) {
            world.generateLight(x, y, Tileset.AVATAR);
            underAvatar = Tileset.FLOOR0;
        }
    }

    public boolean canMove(int xi, int yi, TETile[][] board) {
        int width = board[0].length;
        int height = board.length;
        return board[xi][yi] != Tileset.WALL;
    }
}
