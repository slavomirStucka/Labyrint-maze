package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public void setRating(Rating rating) throws RatingException {
        //entityManager.persist(rating);
        try {
            int points=getRating(rating.getGame(), rating.getPlayer());
            if(points!=0){
                updateRating(rating);
            }else{
                entityManager.persist(rating);
            }
        } catch (NoResultException ex) {
            entityManager.persist(rating);
        }catch (Exception e) {
            throw new RatingException("Problem setRating",e);
        }
    }
    public void updateRating(Rating rating) throws  RatingException{
        entityManager.createQuery("update Rating r set r.rating= :rating where r.game= :game and r.player= :player")
                .setParameter("rating",rating.getRating())
                .setParameter("game",rating.getGame())
                .setParameter("player",rating.getPlayer())
                .executeUpdate();
    }
    @Override
    public int getRating(String game, String player) throws RatingException {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT r.rating FROM Rating r WHERE r.game = :game and r.player= :player", Integer.class);
        query.setParameter("game", game).setParameter("player",player);
        int result= query.getSingleResult();
        return (result != 0) ? result : 0;
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
          TypedQuery<Double> query = entityManager.createQuery(
                        "select avg(r.rating) from Rating r where r.game = :game", Double.class)
                .setParameter("game", game);
        Double result = query.getSingleResult();
        return (result != null) ? result.intValue() : 0;
    }



    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
