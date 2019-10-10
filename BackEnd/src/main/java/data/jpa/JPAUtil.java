package data.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

public class JPAUtil {

    @PersistenceContext   // The container is in charge of emf
    private static EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        if (emf == null) {
            System.err.println("\n(!) Oh noooo: emf is null. Container why?");
            return null;
        }
        return emf.createEntityManager();
    }

}
