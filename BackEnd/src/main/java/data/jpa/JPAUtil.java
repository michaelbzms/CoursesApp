package data.jpa;

import conf.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = (Configuration.USE_JPA) ? Persistence.createEntityManagerFactory("PERSISTENCE") : null;

    public static EntityManager getNewEntityManager() {
        if (emf == null) {
            System.err.println("\n(!) Oh noooo: emf is null. Container why?");
            return null;
        }
        return emf.createEntityManager();
    }

    public void shutdown() {
        if (emf != null) emf.close();
    }

}
