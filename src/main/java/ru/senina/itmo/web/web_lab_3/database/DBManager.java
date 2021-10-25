package ru.senina.itmo.web.web_lab_3.database;

import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;

@Log
public class DBManager {
    private static final int tokenLength = 50;
    private static final EntityManagerFactory entityManagerFactory = setEntityManagerFactory();

    public static EntityManagerFactory setEntityManagerFactory() {
        String path = Optional.ofNullable(System.getenv("DB_properties")).orElseThrow(() -> new IllegalArgumentException("Invalid  DB_properties variable"));
        try (final InputStream jpaFileInput = Files.newInputStream(Paths.get(path))) {
            final Properties properties = new Properties();
            properties.load(jpaFileInput);
            return Persistence.createEntityManagerFactory("MyJPAModel", properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void finish() {
        assert entityManagerFactory != null;
        entityManagerFactory.close();
    }

    public static void addElement(Attempt attempt, String userSessionId) {
        assert entityManagerFactory != null;
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

//            Owner owner = getOwnerByToken(manager, token);
//            labWork.setOwner(owner);
//            owner.addLabWork(labWork);
//            labWork.getCoordinates().setLabWork(labWork);
//
//            Discipline dbDiscipline = createDiscipline(labWork.getDiscipline());
//            dbDiscipline.addLabWork(labWork);
//            labWork.setDiscipline(dbDiscipline);
////            manager.merge(labWork);
////            update if exist and creat if not
//            manager.persist(labWork);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.log(Level.WARNING, "There were some exceptions during adding Element. " + ex.getMessage());
        } finally {
            manager.close();
        }
    }
}
