package sk.tuke.kpi.kp.labyrintmaze.service;
import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;

import java.util.List;
public interface CommentService {
    Object addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;
}
