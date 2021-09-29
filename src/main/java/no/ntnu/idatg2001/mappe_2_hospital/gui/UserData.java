package no.ntnu.idatg2001.mappe_2_hospital.gui;

import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;

/** Singleton class that saves information about the current patient being added or edited
 *
 * @author Sander Osvik Brekke
 * @version 01.05.2021
 */
public class UserData {

    private static UserData userData;
    private Type type;
    private Patient patient;

    /**
     * Gets the current userData object, containing edit or add-type and current patient object
     * @return userData-object
     */
    public static UserData getUserData() {
        if (userData == null) {
            userData = new UserData();
        }

        return userData;

    }

    /**
     * Method to set the current patient of the userData object
     * @param newPatient the patient to set
     */
    public void setUserDataPatient(Patient newPatient) {
        if ((newPatient != userData.patient)) {
            userData.patient = newPatient;
        }
    }

    /**
     * Method to set the current type of the edit or add
     * @param newType edit or add
     */
    public void setUserDataType(Type newType) {
        if ((newType != userData.type)) {
            userData.type = newType;
        }
    }

    /**
     * Method to get the current type of the edit or add
     * @return the current type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Method to get the current patient being added or edited
     * @return the current patient
     */
    public Patient getPatient() {
        return this.patient;
    }


}
