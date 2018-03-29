package utils;

import models.BoardsEntity;
import models.VotesEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static utils.ServerConstants.ENTITY_MANAGER_FACTORY;

public class VotesHibernateUtil {

    /**
     * Read all boards of the user.
     */
    public static VotesEntity saveVote(long option_id, long user_id, int val) {
        VotesEntity vote;
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            vote = new VotesEntity();
            vote.setOptionsId(option_id);
            vote.setUsersId(user_id);
            vote.setValue(val);
            manager.persist(vote);
            manager.flush();
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            vote = null;
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return vote;
    }


    /**
     * Read all boards of the user.
     */
    public static VotesEntity editVote(long vote_id, long user_id, int val) {
        VotesEntity vote;
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            vote = manager.createQuery("SELECT v FROM VotesEntity v WHERE v.id=:id", VotesEntity.class)
                    .setParameter("id", vote_id)
                    .getSingleResult();
            if (user_id != vote.getUsersId()) throw new Exception("You cannot edit others' votes");
            vote.setValue(val);
            manager.merge(vote);
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            vote = null;
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return vote;
    }

}