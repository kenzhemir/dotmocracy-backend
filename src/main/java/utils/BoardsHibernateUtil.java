package utils;

import models.BoardsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import static utils.ServerConstants.ENTITY_MANAGER_FACTORY;

public class BoardsHibernateUtil {

    /**
     * Read all boards of the user.
     */
    public static List readUserBoards(long user_id) {
        // List of Boards
        List boards = null;
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Get a list of Boards
            boards = manager.createQuery("SELECT b FROM BoardsEntity b WHERE b.owner=:id",
                    BoardsEntity.class).setParameter("id", user_id).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return boards;
    }


    /**
     * Read all boards of the user.
     */
    public static BoardsEntity addBoard(long user_id, String category, String topic, Long team_id) {
        BoardsEntity newBoard;
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            newBoard = new BoardsEntity();
            newBoard.setCategory(category);
            newBoard.setOwner(user_id);
            newBoard.setTopic(topic);
            manager.persist(newBoard);
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            newBoard = null;
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return newBoard;
    }

}