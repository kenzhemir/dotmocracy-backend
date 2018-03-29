package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Assylkhanov Aslan on 30.03.2018.03.2018=
 */

public class ServerConstants {

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("UserPersistenceUnit");

}
