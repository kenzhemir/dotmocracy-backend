package utils;

import models.IdeasEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

import static utils.ServerConstants.ENTITY_MANAGER_FACTORY;

/**
 * Created by Assylkhanov Aslan on 30.03.2018.03.2018=
 */

public class IdeasHibernateUtil {

    public static List<IdeasEntity> getBoardIdeas(long boardId) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        //Resulting variable
        IdeasEntity entity = null;
        try {
            // Get the User object by login
            Query query = manager.createQuery("SELECT c FROM IdeasEntity c WHERE c.boardsId = :id");
            query.setParameter("id", boardId);
            List<IdeasEntity> ideasList = query.getResultList();
            //System.out.println("My log. ideas size: " + ideasList.size());
            return ideasList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            manager.close();
        }
    }

    public static boolean createIdea(long boardId, String name, String description) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        IdeasEntity ideasEntity = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            ideasEntity = new IdeasEntity();
            ideasEntity.setName(name);
            ideasEntity.setBoardsId(boardId);
            ideasEntity.setDescription(description);
            manager.persist(ideasEntity);
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
             ideasEntity = null;
            // Print the Exception
            ex.printStackTrace();
        } finally {
            //close manager
            manager.close();
        }
        return ideasEntity != null;
    }

}
