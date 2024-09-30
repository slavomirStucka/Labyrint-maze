package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Comment;
import sk.tuke.kpi.kp.labyrintmaze.entity.Hrac;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class HracServiceJPA implements HracService{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public String getHeslo(String name) throws HracException {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT h.heslo FROM Hrac h WHERE h.name= :name", String.class);
        query.setParameter("name",name);
        try {
            String result = query.getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void addHrac(Hrac hrac) {
        entityManager.persist(hrac);
    }

    @Override
    public Hrac getHrac(String name) {
        try {
            TypedQuery<Hrac> query = entityManager.createQuery(
                    "SELECT h FROM Hrac h WHERE h.name = :name", Hrac.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
