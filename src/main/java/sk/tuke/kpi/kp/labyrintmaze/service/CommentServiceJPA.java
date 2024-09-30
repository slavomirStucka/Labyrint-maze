package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Transactional
public class CommentServiceJPA implements CommentService{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Object addComment(Comment comment) {
        entityManager.persist(comment);
        return null;
    }

    @Override
    public List<Comment> getComments(String game) {
        return (List<Comment>) entityManager.createQuery("select c from Comment c where c.game= :game order by c.commentedOn desc")
                .setParameter("game",game)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM comment").executeUpdate();
    }
}
