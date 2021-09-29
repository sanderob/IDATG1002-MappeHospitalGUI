package no.ntnu.idatg2001.mappe_2_hospital.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;

/**
 * Class controlling the communication between the application and the database
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class PatientController {
    private static PatientController patientController;
    private PatientDB db;
    private ObservableList<Patient> list;

    /**
     * Singleton constructor making the first connection to the DB.
     */
    private PatientController() {
        this.db = PatientDB.getInstance();
        this.list = FXCollections.observableArrayList(db.getPatientsFromDB());
    }

    /**
     * Method for getting a patientController-object from the singleton constructor
     * @return PatientController object
     */
    public static PatientController getInstance() {
        if (patientController == null) {
            patientController = new PatientController();
        }
        else if ( !patientController.db.isEntityFactoryOpen()) {
            patientController.db = PatientDB.getInstance();
        }
        return patientController;
    }

    /**
     * Method that adds a patient to the database and to the tableview
     * @param patient the patient to be added
     */
    public void addPatient(Patient patient) {
        list.add(patient);
        db.addToDatabase(patient);
    }

    /**
     * Method that removes a patient from the database and the tableview
     * @param patient the patient to be removed
     */
    public void removePatient(Patient patient) {
        list.remove(patient);
        db.removeFromDB(patient);
    }

    /**
     * Method that checks if there is a patient with the same social security number as the number being passed to the
     * method.
     * @param number social security number to check for duplicates of
     * @return true or false, if it is a duplicate
     */
    public boolean socialSecurityNumberExists(String number) {
        boolean exists = false;
        for (Patient p : list) {
            if (p.getSocialSecurityNumber().equals(number)) {
                exists = true;
            }
        }
        return exists;
    }

    /**
     * Method to get the current observable list attached to the tableview
     * @return ObservableList<Patient> List with all the current patients in the tableview
     */
    public ObservableList<Patient> getList() {
        return list;
    }

    /**
     * Method to clear the database of patients
     */
    public void clearDatabase() {
        for (Patient p : db.getPatientsFromDB()) {
            db.removeFromDB(p);
        }
        list.clear();
    }


    /**
     * Method to update patients in the DB when edited in the list in the memory of the application, if the social
     * security number has been changed
     * @param newPatient the patient to be updated, with the new fields
     * @param oldNumber the old socialSecurityNumber of the patient
     */
    public void updateDB(Patient newPatient, String oldNumber) {
        db.updateInDB(newPatient, oldNumber);
    }

    /**
     * Method to update patients in the DB when edited in the list in the memory of the application, when the social
     * security number is not edited
     * @param patient the patient to update, with the new fields
     */
    public void updateDB(Patient patient) {
        db.updateInDB(patient);
    }

    /**
     * Method to close the entityFactory and therefore close the connection to the database
     */
    public void closeConnection() {
        db.closeFactory();
    }
}
