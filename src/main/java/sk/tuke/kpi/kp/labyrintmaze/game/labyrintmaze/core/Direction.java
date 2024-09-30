package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

public enum Direction {

    SOUTH (0, 1),
    EAST (1, 0),
    NORTH (0, -1),
    WEST (-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dy,int dx){
        this.dx=dx;
        this.dy=dy;
    }
    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

}
