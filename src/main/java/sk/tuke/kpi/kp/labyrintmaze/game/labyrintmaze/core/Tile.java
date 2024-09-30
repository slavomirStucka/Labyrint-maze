package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

import java.io.Serializable;

public abstract class Tile implements Serializable {
    private int row;
    private int column;
    public Tile(int row,int column){
        this.column=column;
        this.row=row;
    }
    public Tile(){

    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public abstract Tile createClone(int row,int column,boolean hasPlayer);
    public abstract Tile createClone(int row,int  column);


}