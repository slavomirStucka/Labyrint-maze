package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "9zsY?Egs";
    public static final String DELETE_STATEMENT = "DELETE FROM score";
    public static final String INSERT_STATEMENT = "INSERT INTO score(player, game, points, playedOn)VALUES (?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT player, game, points, playedOn FROM score WHERE game = ? ORDER BY points ASC LIMIT 10";

    @Override
    public void addScore(Score score) {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(INSERT_STATEMENT);
        ){
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getPlayedon().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }


    @Override
    public List<Score> getTopScores(String game) {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(SELECT_STATEMENT);
        ){
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                var scores = new ArrayList<Score>();
                while (rs.next())
                    scores.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                return scores;
            }
        }catch (SQLException e) {
            throw new ScoreException(e);
        }
    }

    @Override
    public void reset() {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new ScoreException(e);
        }
    }
}
