package data.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");

    public static EntityManager getNewEntityManager() {
        if (emf == null) {
            System.err.println("\n(!) Oh noooo: emf is null. Container why?");
            return null;
        }
        return emf.createEntityManager();
    }

}
