package utils;

import models.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class HibernateUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("UserPersistenceUnit");

    /**
     * Read all the Users.
     */
    public static List readUsers() {
        // List of Users
        List users = null;
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Get a list of Users
            users = manager.createQuery("SELECT c FROM UserEntity c",
                    UserEntity.class).getResultList();
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
        return users;
    }

    /**
     * Check if User exists
     */
    public static UserEntity checkUser(String login) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Boolean variable
        UserEntity user = null;
        try {
            // Get the User object by login
            Query query = manager.createQuery("SELECT c FROM UserEntity c WHERE login = :login");
            query.setParameter("login", login);
            List<UserEntity> resultList = query.getResultList();
            if (resultList.size() != 0) {
                user = resultList.get(0);
            }
        } catch (Exception ex) {
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return user;
    }

    /**
     * Create a new User.
     */
    public static UserEntity createUser(String login, String password) {
        // Create a User
        UserEntity user = null;
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Create a new User object
            user = new UserEntity();
            user.setLogin(login);
            user.setPassword(password);
            // Save the user object
            manager.persist(user);
            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            user = null;
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return user;
    }

    /**
     * Delete the existing User.
     *
     * @param id
     */
    public static void deleteUser(int id) {
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Get the User object
            UserEntity user = manager.find(UserEntity.class, id);
            // Delete the user
            manager.remove(user);
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
    }

    /**
     * Update the existing User.
     */
    public static void updateUser(int id, String login) {
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Get the User object
            UserEntity user = manager.find(UserEntity.class, id);
            // Change the values
            user.setId(id);
            user.setLogin(login);
            // Update the user
            manager.persist(user);
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
    }
}