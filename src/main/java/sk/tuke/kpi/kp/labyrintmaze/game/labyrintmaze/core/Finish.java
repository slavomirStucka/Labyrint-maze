package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

public class Finish extends Tile{
    private boolean hasPlayer;
    public Finish(int row,int column,boolean hasPlayer) {
        super(row, column);
        this.hasPlayer=hasPlayer;
    }
    public Finish(){}

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    @Override
    public Tile createClone(int row,int column,boolean hasPlayer) {
        return new Finish(row, column, hasPlayer);
    }

    @Override
    public Tile createClone(int row, int column) {
        return null;
    }
}
