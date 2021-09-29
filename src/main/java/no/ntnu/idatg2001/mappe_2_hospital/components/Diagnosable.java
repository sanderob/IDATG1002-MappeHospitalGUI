package no.ntnu.idatg2001.mappe_2_hospital.components;

/**
 * Interface for classes with diagnosable objects. At the moment, only the class Patient
 * @author Sander Osvik Brekke
 * @version 14.04.2021
 */
public interface Diagnosable {

    /**
     * A setter for the diagnosis of a diagnosable object
     * @param diagnosis the name of the diagnose to be set or given to a diagnosable
     */
    public void setDiagnosis(String diagnosis);
}
