package sk.tuke.kpi.kp.labyrintmaze.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.labyrintmaze.entity.Score;
import sk.tuke.kpi.kp.labyrintmaze.service.ScoreService;
import sk.tuke.kpi.kp.labyrintmaze.service.ScoreServiceJDBC;

import java.sql.Timestamp;
import java.util.List;

public class ScoreServiceTest {
    private ScoreService scoreService;

    @BeforeEach
    public void setUp() {
        scoreService = new ScoreServiceJDBC();
        scoreService.reset();
    }

    @Test
    public void testAddScore() {
        Score score = new Score("player1", "game1", 100, new Timestamp(System.currentTimeMillis()));
        scoreService.addScore(score);
        List<Score> topScores = scoreService.getTopScores("game1");
        Assertions.assertEquals(1, topScores.size());
        Assertions.assertEquals(score.getGame(), topScores.get(0).getGame());
        Assertions.assertEquals(score.getPlayer(), topScores.get(0).getPlayer());
        Assertions.assertEquals(score.getPoints(), topScores.get(0).getPoints());
    }

    @Test
    public void testGetTopScores() {
        Score score1 = new Score("player1", "game1", 100, new Timestamp(System.currentTimeMillis()));
        Score score2 = new Score("player1", "game1", 200, new Timestamp(System.currentTimeMillis()));
        Score score3 = new Score("player1", "game1", 300, new Timestamp(System.currentTimeMillis()));

        scoreService.addScore(score1);
        scoreService.addScore(score2);
        scoreService.addScore(score3);

        List<Score> topScores = scoreService.getTopScores("game1");
        Assertions.assertEquals(3, topScores.size());
        Assertions.assertEquals(score1.getGame(), topScores.get(0).getGame());
        Assertions.assertEquals(score1.getPlayer(), topScores.get(0).getPlayer());
        Assertions.assertEquals(score1.getPoints(), topScores.get(0).getPoints());
        Assertions.assertEquals(score2.getGame(), topScores.get(1).getGame());
        Assertions.assertEquals(score2.getPlayer(), topScores.get(1).getPlayer());
        Assertions.assertEquals(score2.getPoints(), topScores.get(1).getPoints());
        Assertions.assertEquals(score3.getGame(), topScores.get(2).getGame());
        Assertions.assertEquals(score3.getPlayer(), topScores.get(2).getPlayer());
        Assertions.assertEquals(score3.getPoints(), topScores.get(2).getPoints());
    }

    @Test
    public void testReset() {
        Score score = new Score("game1", "player1", 100, new Timestamp(System.currentTimeMillis()));
        scoreService.addScore(score);

        scoreService.reset();
        List<Score> topScores = scoreService.getTopScores("game1");
        Assertions.assertEquals(0, topScores.size());
    }
}