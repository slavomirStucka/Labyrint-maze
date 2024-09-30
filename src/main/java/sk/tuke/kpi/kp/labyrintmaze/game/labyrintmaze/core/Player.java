package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

import java.io.Serializable;

public class Player implements Serializable {
    private Field field;
    public int posX;
    public int posY;
    public int moves;
    public int koef;


    public Player(Field field){
        this.posX=1;
        this.posY=1;
        this.field=field;
        this.moves=0;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getMoves() {
        return moves;
    }

    public void move(Direction direction){
        if(field.getState()==GameState.PLAYING) {
            Tile possibleWall = field.getTile(getPosX() + direction.getDx(), getPosY() + direction.getDy());
            if (possibleWall instanceof Path || possibleWall instanceof Finish) {
                delOldPos();
                this.setPosition(getPosY() + direction.getDy(), getPosX() + direction.getDx());
                updateTiles(direction);
            } else {
                this.setPosition(getPosY(), getPosX());
            }
            if(field.getDifficulty()=='E'){this.koef=3;}
            else if(field.getDifficulty()=='M'){this.koef=2;}
            else {this.koef=1;}
            moves=moves+koef;
        }
    }
    public void teleportation(int posX,int posY){
        delOldPos();
        this.setPosition(posY,posX);
        updateTiles();
    }
    private void setPosition(int newPosY,int newPosX){
        this.posY=newPosY;
        this.posX=newPosX;
    }
    private void updateTiles(){
        int X=field.player.getPosX();
        int Y=field.player.getPosY();
        var dlazdica=field.getTile(X,Y);
        if(dlazdica instanceof Path){
            ((Path) dlazdica).setHasPlayer(true);
        }
        //delOldPos();
    }
    private void updateTiles(Direction direction){
        int X=field.player.getPosX();
        int Y=field.player.getPosY();
        var dlazdica=field.getTile(X,Y);
        if(dlazdica instanceof Path){
            ((Path) dlazdica).setHasPlayer(true);
        }
        //delOldPos();
    }
    private void delOldPos(){
        var dlazdica=field.getTile(field.player.getPosX(),field.player.getPosY());
        if(dlazdica instanceof Path) {
            ((Path) dlazdica).setHasPlayer(false);
        }
    }

}
