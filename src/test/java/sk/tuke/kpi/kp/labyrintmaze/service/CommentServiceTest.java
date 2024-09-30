package sk.tuke.kpi.kp.labyrintmaze.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.service.CommentService;
import sk.tuke.kpi.kp.labyrintmaze.service.CommentServiceJDBC;

import java.sql.Timestamp;
import java.util.List;

public class CommentServiceTest {
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        commentService = new CommentServiceJDBC();
        commentService.reset();
    }

    @Test
    public void testAddComment() {
        Comment comment = new Comment("player1", "game1", "Velmi dobra hra", new Timestamp(System.currentTimeMillis()));
        commentService.addComment(comment);
        List<Comment> recComments = commentService.getComments("game1");
        Assertions.assertEquals(1, recComments.size());
        Assertions.assertEquals(comment.getGame(), recComments.get(0).getGame());
        Assertions.assertEquals(comment.getPlayer(), recComments.get(0).getPlayer());
        Assertions.assertEquals(comment.getComment(), recComments.get(0).getComment());
    }

    @Test
    public void testGetComment() {
        Comment comment1 = new Comment("player1", "game1", "Dobra hra", new Timestamp(System.currentTimeMillis()));
        Comment comment2 = new Comment("player1", "game1", "Good game", new Timestamp(System.currentTimeMillis()));
        Comment comment3 = new Comment("player1", "game1", "velmi tazke", new Timestamp(System.currentTimeMillis()));

        commentService.addComment(comment1);
        commentService.addComment(comment2);
        commentService.addComment(comment3);

        List<Comment> topScores = commentService.getComments("game1");
        Assertions.assertEquals(3, topScores.size());
        Assertions.assertEquals(comment1.getGame(), topScores.get(0).getGame());
        Assertions.assertEquals(comment1.getPlayer(), topScores.get(0).getPlayer());
        Assertions.assertEquals(comment1.getComment(), topScores.get(0).getComment());
        Assertions.assertEquals(comment2.getGame(), topScores.get(1).getGame());
        Assertions.assertEquals(comment2.getPlayer(), topScores.get(1).getPlayer());
        Assertions.assertEquals(comment2.getComment(), topScores.get(1).getComment());
        Assertions.assertEquals(comment3.getGame(), topScores.get(2).getGame());
        Assertions.assertEquals(comment3.getPlayer(), topScores.get(2).getPlayer());
        Assertions.assertEquals(comment3.getComment(), topScores.get(2).getComment());
    }

    @Test
    public void testReset() {
        Comment comment = new Comment("game1", "player1", "Good game", new Timestamp(System.currentTimeMillis()));
        commentService.addComment(comment);

        commentService.reset();
        List<Comment> topScores = commentService.getComments("game1");
        Assertions.assertEquals(0, topScores.size());
    }
}
