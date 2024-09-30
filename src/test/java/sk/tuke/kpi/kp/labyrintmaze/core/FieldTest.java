package sk.tuke.kpi.kp.labyrintmaze.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.labyrintmaze.game.labyrintmaze.core.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FieldTest {
    private Field field;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    void generateFieldBadParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new Field(10, 9,'a'));
    }

    @Test
    void gameState() {
        var field = new Field(31, 42, 'H');
        assertSame(field.getState(), GameState.PLAYING);
    }

    @Test
    void startPosition() {
        var field = new Field(15, 15, 'E');
        Path player = (Path)field.getTile(1,1);
        int row=player.getRow();
        int column= player.getColumn();
        boolean hasPlayer=player.isHasPlayer();
        assertSame(1, row);
        assertSame(1, column);
        assertSame(true, hasPlayer);
    }
    @Test
    void move() {
        var field = new Field(15, 15, 'E');
        Path player = (Path)field.getTile(1,1);
        Player player1=new Player(field);
        player1.move(Direction.SOUTH);
        Path player2 = (Path)field.getTile(3,1);
        assertSame(false,player2.isHasPlayer());
    }
    @BeforeEach
    public void setUp() {
        field = new Field(5, 5, 'M');
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testGetColumnCount() {
        Assertions.assertEquals(5, field.getColumnCount());
    }

    @Test
    public void testGetRowCount() {
        Assertions.assertEquals(5, field.getRowCount());
    }

    @Test
    public void testGetTile() {
        Assertions.assertTrue(field.getTile(0, 0) instanceof Wall);
        Assertions.assertTrue(field.getTile(1, 1) instanceof Path);
    }

    @Test
    public void testGetTiles() {
        Assertions.assertNotNull(field.getTiles());
        Assertions.assertEquals(5, field.getTiles().length);
        Assertions.assertEquals(5, field.getTiles()[0].length);
    }

    @Test
    public void testSetState() {
        field.setState(GameState.SOLVED);
        Assertions.assertEquals(GameState.SOLVED, field.getState());
    }

}
