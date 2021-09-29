package no.ntnu.idatg2001.mappe_2_hospital.db;

import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

/**
 * DataBase class for the patients database
 * @author Sander Osvik Brekke
 * version 01.05.2021
 */
public class PatientDB {

    private static PatientDB db;
    private static final String PERSISTENCE_UNIT_NAME = "patientsdb";
    private final EntityManagerFactory entityManagerFactory;


    /**
     * Constructor that creates a new EntityManagerFactory.
     */
    protected PatientDB() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /**
     * Singleton constructor for the database connection – is only created if it has not yet been created or
     * if it is closed
     * @return
     */
    protected static PatientDB getInstance() {
        if (db == null || !db.isEntityFactoryOpen()) {
            db = new PatientDB();
        }
        return db;
    }

    /**
     * Updates the information of the patient in the DB when the information is updated in the list
     * @param patient the patient to be updated
     */
    protected void updateInDB(Patient patient) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(patient);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Updates the information of a patient where the social security number has been changed
     * @param newPatient the patient to update the information of
     * @param oldNumber the old social security number, to refer to the object in the db
     */
    protected void updateInDB(Patient newPatient, String oldNumber) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Patient patient = em.find(newPatient.getClass(), oldNumber);
            em.remove(patient);
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(newPatient);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    /**
     * Adds a single patient to the database
     * @param patient the patient to add
     */
    protected void addToDatabase(Patient patient) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(patient);
            em.getTransaction().commit();
        } finally {
            closeEntityManager(em);
        }
    }

    /**
     * Returns a complete list woth the patients in the database
     * @return List with all patients in the database
     */
    protected List<Patient> getPatientsFromDB() {
        EntityManager em = getEntityManager();

        Query q = em.createQuery("select t from Patient t");

        @SuppressWarnings("unchecked")
        List<Patient> list = q.getResultList();
        return list;
    }

    /**
     * Removes a single patient from the database
     * @param patient the patient to remove
     */
    protected void removeFromDB(Patient patient) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(patient)) {
                patient = em.merge(patient);
            }
            em.remove(patient);
            em.getTransaction().commit();
        } finally {
            closeEntityManager(em);
        }
    }

    /**
     * Creates a new EntityManager object.
     *
     * @return new EntityManager object.
     */
    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Closes the EntityManager if it exists and its open.
     *
     * @param entityManager EntityManager to close.
     */
    private void closeEntityManager(EntityManager entityManager) {
        if (entityManager != null && entityManager.isOpen()) entityManager.close();
    }

    /**
     * Returns boolean if the entity factory is open
     * @return if entity factory is open
     */
    protected boolean isEntityFactoryOpen() {
        return entityManagerFactory.isOpen();
    }

    /**
     * Method that closes the EntityManagerFactory.
     */
    protected void closeFactory() {
        entityManagerFactory.close();
    }

}
