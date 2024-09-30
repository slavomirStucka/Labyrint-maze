package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements  CommentService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "9zsY?Egs";
    public static final String DELETE_STATEMENT = "DELETE FROM comment";
    public static final String INSERT_STATEMENT = "INSERT INTO comment(player, game, comment, commentedOn)VALUES (?,?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT player, game, comment, commentedOn  FROM comment WHERE game = ? ORDER BY commentedOn DESC LIMIT 10";
    @Override
    public Object addComment(Comment comment) throws CommentException {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(INSERT_STATEMENT);
        ){
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException(e);
        }
        return null;
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(SELECT_STATEMENT);
        ){
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                var comments = new ArrayList<Comment>();
                while (rs.next())
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                return comments;
            }
        }catch (SQLException e) {
            throw new CommentException(e);
        }
    }

    @Override
    public void reset() throws CommentException {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new CommentException(e);
        }
    }
}
