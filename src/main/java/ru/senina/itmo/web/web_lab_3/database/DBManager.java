package ru.senina.itmo.web.web_lab_3.database;

import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.entities.Owner;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;

@Log
@Named()
@ApplicationScoped
public class DBManager {
    private static final int tokenLength = 50;
//    private static final EntityManagerFactory entityManagerFactory = setEntityManagerFactoryWithProperties();
@PersistenceUnit(name = "MyJPAModel") private  EntityManagerFactory entityManagerFactory;

    { //free the resource if shutdown
        Thread shutdownHook = new Thread(this::finish);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @Deprecated
    public static EntityManagerFactory setEntityManagerFactoryWithProperties() {
        String path = Optional.ofNullable(System.getenv("DB_properties")).orElseThrow(() -> new IllegalArgumentException("Invalid  DB_properties variable"));
        try (final InputStream jpaFileInput = Files.newInputStream(Paths.get(path))) {
            final Properties properties = new Properties();
            properties.load(jpaFileInput);
            return Persistence.createEntityManagerFactory("MyJPAModel", properties);
            //we can also inject EntityManagerFactory by annotation @PersistenceUnit(name = "MyJPAModel")
            //but i need to set parameters to MyJPAModel configuration (set password, login and db_name)
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void finish() {
        assert entityManagerFactory != null;
        entityManagerFactory.close();
    }

    public void addElement(Attempt attempt, String userSessionId) {
        assert entityManagerFactory != null;
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Owner owner = getOwnerBySessionId(manager, userSessionId);
            log.log(Level.WARNING, owner.toString());
            attempt.setOwner(owner);
            log.log(Level.WARNING, attempt.toString());
            owner.getAttemptList().add(attempt);
            log.log(Level.WARNING, owner.toString());
            attempt.getCoordinates().setAttempt(attempt);
            log.log(Level.WARNING, attempt.toString());
            manager.persist(attempt);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.log(Level.WARNING, "There were some exceptions during adding Attempt. " + ex.getMessage());
        } finally {
            manager.close();
        }
    }

    public List<Attempt> readAll() {
        List<Attempt> elements = null;

        assert entityManagerFactory != null;
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            elements = manager.createQuery("SELECT attempt FROM Attempt attempt", Attempt.class).getResultList();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.log(Level.WARNING, "There were some exceptions during readingAllAttempts. " + ex.getMessage());
        } finally {
            manager.close();
        }
        return elements;
    }


    private Owner getOwnerBySessionId(EntityManager manager, String sessionId) {
        Query query = manager.createQuery("SELECT owner FROM Owner owner WHERE owner.sessionId=:sessionId", Owner.class);
        query.setParameter("sessionId", sessionId);
        Owner theOwner;
        try {
            theOwner = (Owner) query.getSingleResult();
        }catch (NoResultException e){
            theOwner = new Owner();
            theOwner.setSessionId(sessionId);
        }
        return  theOwner;
    }
}
