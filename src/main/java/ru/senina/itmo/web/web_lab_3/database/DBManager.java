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

import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;

@Log
@Named()
@ApplicationScoped
public class DBManager {
//    private static final EntityManagerFactory entityManagerFactory = setEntityManagerFactoryWithProperties();
@PersistenceUnit(name = "MyJPAModel")
private  EntityManagerFactory entityManagerFactory;

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
            Owner owner = getOwnerBySessionId(manager, userSessionId);
            log.log(Level.WARNING, owner.toString());
            attempt.setOwner(owner);
            log.log(Level.WARNING, attempt.toString());
            owner.getAttemptList().add(attempt);
            log.log(Level.WARNING, owner.toString());
            attempt.getCoordinates().setAttempt(attempt);
            log.log(Level.WARNING, attempt.toString());

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.persist(attempt);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.log(Level.WARNING, "There were some exceptions during adding Attempt. " + ex);
            log.log(Level.WARNING, "There were some exceptions during adding Attempt. " + ex.getMessage());
            log.log(Level.WARNING, owner.toString());
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
        log.log(Level.WARNING, "start to search for owner by session id");
        Query query = manager.createQuery("SELECT owner FROM Owner owner WHERE owner.sessionId=:sessionId", Owner.class);
        query.setParameter("sessionId", sessionId);
        log.log(Level.WARNING, "just did request");
        Owner theOwner;
        try {
            return (Owner) query.getSingleResult();
        }catch (Exception e){
            theOwner = Owner.initOwner();
            theOwner.setSessionId(sessionId);
            log.log(Level.WARNING, "Searching for owner with given id failed with error: " + e.getMessage());
            log.log(Level.WARNING, "There were some exceptions during adding Attempt. " + e);
            log.log(Level.WARNING, "the owner:" + theOwner);
            return  theOwner;
        }
    }
}
