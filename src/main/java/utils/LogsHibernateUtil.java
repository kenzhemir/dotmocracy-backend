package utils;

import models.LogsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static utils.ServerConstants.ENTITY_MANAGER_FACTORY;

/**
 * Created by Assylkhanov Aslan on 20.04.2018.04.2018=
 */

public class LogsHibernateUtil {

    public static boolean storeLog(String who, String when, String what, String from) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        //Setting up the entity
        LogsEntity log = new LogsEntity();
        log.setWho(who);
        log.setWhen(when);
        log.setWhat(what);
        log.setFrom(from);
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            manager.persist(log);
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
            return false;
        } finally {
            //close manager
            manager.close();
        }
        return true;
    }

}
