package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class RatingServiceJDBC implements RatingService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "9zsY?Egs";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String INSERT_STATEMENT = "INSERT INTO rating(player, game, rating, ratedOn)VALUES (?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT player,game, rating, ratedOn  FROM rating WHERE game = ? AND player = ?";
    private static final String SELECT_AVERAGE_STATEMENT = "SELECT AVG(rating) FROM RATING WHERE game = ?";
    private static final String UPDATE_STATEMENT = "UPDATE RATING SET rating = ? WHERE game = ? AND player = ?";


    @Override
    public void setRating(Rating rating){
        try (var connection = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD);
             var statement = connection.prepareStatement(SELECT_STATEMENT)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    updateRating(rating);
                } else {
                    insertRating(rating);
                }
            }
        } catch (SQLException e) {
            throw new RatingException(e);
        }
    }

    private void insertRating( Rating rating) throws SQLException {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(INSERT_STATEMENT)) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        }
    }

    private void updateRating(Rating rating) throws SQLException {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(UPDATE_STATEMENT)) {
            statement.setInt(1, rating.getRating());
            statement.setString(2, rating.getGame());
            statement.setString(3, rating.getPlayer());
            statement.executeUpdate();
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(SELECT_AVERAGE_STATEMENT);
        ){
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }catch (SQLException e) {
            throw new RatingException(e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(SELECT_STATEMENT);
        ){
            statement.setString(1, game);
            statement.setString(2, player);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    int rating = rs.getInt(3);
                    return rating;
                }
                return 0;
            }
        }catch (SQLException e) {
            throw new RatingException(e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new RatingException(e);
        }
    }
}
