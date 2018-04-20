package utils;

import models.LogsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.List;

import static utils.ServerConstants.ENTITY_MANAGER_FACTORY;

/**
 * Created by Assylkhanov Aslan on 20.04.2018.04.2018=
 */

public class LogsHibernateUtil {

    public static boolean storeLog(String who, String when, String what, String from) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        LogsEntity log = new LogsEntity();
        log.setWho(who);
        log.setWhattime(when);
        log.setWhat(what);
        log.setAgent(from);
        try {
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

    public static List<LogsEntity> getLogs() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<LogsEntity> logs;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            logs = manager.createQuery("SELECT c FROM LogsEntity c",
                    LogsEntity.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            logs = null;
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return logs;
    }



}
