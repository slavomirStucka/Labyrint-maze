package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

import java.util.HashMap;
import java.util.Map;

public class Factory {
    private Map<Class, Tile> tiles;

    public Factory() {
        this.tiles = new HashMap<>();
        this.tiles.put(Wall.class, new Wall());
        this.tiles.put(Finish.class, new Finish());
        this.tiles.put(Path.class, new Path());
    }

    public <P extends Tile> P createProduct(Class<P> c, int row, int column) {
        return (P) this.tiles.get(c).createClone(row, column);
    }
    public <P extends Tile> P createProduct(Class<P> c, int row, int column,boolean hasPlayer) {
        return (P) this.tiles.get(c).createClone(row, column, hasPlayer);
    }
}