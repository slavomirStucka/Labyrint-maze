package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

public class Wall extends Tile{
    public Wall(int row,int column) {
        super(row, column);
    }
    public Wall(){}

    @Override
    public Tile createClone(int row,int  column) {
        return new Wall(row, column);
    }
    public Tile createClone(int row,int  column,boolean hasPlayer) {
        return null;
    }
}

