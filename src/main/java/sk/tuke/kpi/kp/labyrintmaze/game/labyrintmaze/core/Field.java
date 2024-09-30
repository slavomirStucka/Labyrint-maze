package sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core;

import sk.tuke.kpi.kp.labyrintmaze.service.ScoreException;

import java.io.*;
import java.util.Random;

public class Field implements Serializable{
    private final int rowCount;
    private final int columnCount;
    public Tile[][] tiles;
    public Player player;
    int posXofFinish;
    int posYofFinish;
    char difficulty;
    private static final String FILE ="field.bin";

    private GameState state;
    public int level;

    public Field(int rowCount, int colmunCount,char difficulty) {
        this.rowCount = rowCount;
        this.columnCount = colmunCount;
        if (difficulty!='h'&&difficulty!='H'&&difficulty!='m'&&difficulty!='M'&&difficulty!='e'&&difficulty!='E')
            throw new IllegalArgumentException("Invalid dificulty");
        player = new Player(this);
        tiles = new Tile[rowCount][columnCount];
        state=GameState.PLAYING;
        int random= new Random().nextInt(3);
        this.difficulty=difficulty;
        level=random;
        readMap("src/main/resources/maps/maze"+difficulty+random+".txt");
    }

    public int getLevel(){
        return this.level;
    }
    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public int getPosXofFinish() {
        return posXofFinish;
    }

    public int getPosYofFinish() {
        return posYofFinish;
    }

    public Tile[][] getTiles(){
        return this.tiles;
    }
    public void setState(GameState state){
        this.state=state;
    }
    public GameState getState() {
        isSolved(this);
        return state;
    }
    public char getDifficulty(){
        return this.difficulty;
    }

    public Player getPlayer() {
        return player;
    }

    private void readMap(String filename) {
        char[] charArray = new char[rowCount * columnCount];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            String fileContents = sb.toString();
            charArray = fileContents.toCharArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        convertToField(charArray);
    }

    private void convertToField(char[] charArray){
        Factory factory= new Factory();
        int a = 0;
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                if (charArray[a] == 'P') {
                    tiles[row][col]= factory.createProduct(Path.class,row,col,false);
                } else if (charArray[a] == 'F') {
                    tiles[row][col]= factory.createProduct(Finish.class,row,col,false);
                    posXofFinish=col;
                    posYofFinish=row;
                } else {
                    tiles[row][col]= factory.createProduct(Wall.class,row,col);
                }
                a++;
            }
        }
        tiles[1][1] = new Path(1, 1, true);
        tiles[1][1]= factory.createProduct(Path.class,1,1,true);
    }
    private boolean isSolved(Field field){
        Tile finish = field.getTile(posYofFinish,posXofFinish);
        if(field.player.getPosX()==posYofFinish&&field.player.getPosY()==posXofFinish&&finish instanceof Finish){
                setState(GameState.SOLVED);
                return true;
            }
        return false;
    }
    public static Field load(){
            try (var is = new ObjectInputStream(new FileInputStream(FILE))) {
                return (Field) is.readObject();
            }catch(IOException e ){
                throw new ScoreException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

    }
    public void save() {
        try (var os = new ObjectOutputStream(new FileOutputStream(FILE))) {
            os.writeObject(this);
        }catch(IOException e){
            throw new ScoreException(e);
        }
    }



}


