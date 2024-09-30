package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

public class Path extends Tile{
    private boolean hasPlayer;
    public Path(int row,int column,boolean hasPlayer) {
        super(row, column);
        this.hasPlayer=hasPlayer;
    }
    public Path(){}

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    @Override
    public Tile createClone(int row, int column, boolean hasPlayer) {
        return new Path(row, column, hasPlayer);
    }

    @Override
    public Tile createClone(int row, int column) {
        return null;
    }
}