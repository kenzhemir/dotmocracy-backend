package utils;

import models.UsersEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

import static utils.ServerConstants.ENTITY_MANAGER_FACTORY;

public class UserHibernateUtil {

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
            users = manager.createQuery("SELECT c FROM UsersEntity c",
                    UsersEntity.class).getResultList();
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
    public static UsersEntity checkUser(String login) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        // Boolean variable
        UsersEntity user = null;
        try {
            // Get the User object by login
            System.out.println("Select starts " + login);
            Query query = manager.createQuery("SELECT c FROM UsersEntity c WHERE c.username = :username");
            query.setParameter("username", login);
            System.out.println("My log. Check: " + query.getResultList().size());
            List<UsersEntity> resultList = query.getResultList();
            System.out.println("My log: size: " + resultList.size());
            if (resultList.size() != 0) {
                user = resultList.get(0);
            }
        } catch (Exception ex) {
            // Print the Exception
            ex.printStackTrace();
            return null;
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return user;
    }

    /**
     * Create a new User.
     */
    public static UsersEntity createUser(String login, String password) {
        // Create a User
        UsersEntity user = null;
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Create a new User object
            user = new UsersEntity();
            user.setUsername(login);
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
            UsersEntity user = manager.find(UsersEntity.class, id);
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
    public static void updateUser(long id, String login, String password) {
        // Create an EntityManager and EntityTransaction
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();
            // Get the User object
            UsersEntity user = manager.find(UsersEntity.class, id);
            if (!login.isEmpty()) {
                user.setUsername(login);
            }
            if (!password.isEmpty()) {
                user.setPassword(password);
            }
            // Change the values
            user.setId(id);
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