package no.ntnu.idatg2001.mappe_2_hospital.components;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for a patient, implementing the interface Diagnosable
 * @version 14.04.2021
 * @author Sander O. Brekke
 */
@Entity
public class Patient implements Diagnosable {

    @Column
    public String firstName;
    @Column
    private String lastName;
    @Id
    @Column
    private String socialSecurityNumber;
    @Column
    private String diagnosis;
    @Column
    private String generalPractitioner;

    /**
     * Constructor for a Patient, using the constructor of the super (Person)
     * @param firstName The first name of the patient
     * @param lastName The last name of the patient
     * @param socialSecurityNumber the social secutiry number of the patient
     */
    public Patient(String firstName, String lastName, String socialSecurityNumber, String generalPractitioner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.generalPractitioner = generalPractitioner;
    }

    public Patient() {
    }

    /**
     * Setter for the diagnosis
     * @param diagnosis the diagnosis to be set or given
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Getter for the first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name, checking if the input is either empty, begins with a space or is a space
     * @param firstName new first name
     */
    public void setFirstName(String firstName) throws IllegalArgumentException {
        boolean approvedInput = true;
        if (firstName.isBlank() || firstName.isEmpty()) {
            approvedInput = false;
        }
        else if (firstName.equals(" ")) {
            approvedInput = false;
        }

        else if (firstName.charAt(0) == ((" ").charAt(0))) {
            approvedInput = false;
        }

        if (!approvedInput) {
            throw new IllegalArgumentException();
        }
        else {
            this.firstName = firstName;
        }
    }

    /**
     * Getter for the last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name, checking if the input is either empty, begins with a space or is a space
     * @param lastName last name
     */
    public void setLastName(String lastName) throws IllegalArgumentException {
        boolean approvedInput = true;
        if (lastName.isBlank() || lastName.isEmpty()) {
            approvedInput = false;
        }
        else if (lastName.equals(" ")) {
            approvedInput = false;
        }
        else if (lastName.charAt(0) == ((" ").charAt(0))) {
            approvedInput = false;
        }

        if (!approvedInput) {
            throw new IllegalArgumentException();
        }
        else {
            this.lastName = lastName;
        }
    }

    /**
     * Getter for the full name, a string containing both first name and last name
     * @return The full name of the patient, firstName + lastName
     */
    public String getFullName() {
        if (this.firstName == null && this.lastName == null) {
            return null;
        }
        else if (this.firstName == null) {
            return this.lastName;
        }
        else if (this.lastName == null) {
            return this.firstName;
        }
        else {
            return this.firstName + " " + this.lastName;
        }
    }

    /**
     * Getter for the diagnosis
     * @return the diagnosis that is set or given
     */
    public String getDiagnosis() {
        return this.diagnosis;
    }

    /**
     * Getter for the social security number of the patient
     * @return String social security number
     */
    public String getSocialSecurityNumber() {
        return this.socialSecurityNumber;
    }


    /**
     * Setter for the social security number of a patient
     * @param socialSecurityNumber the new social security number
     */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        boolean approvedInput = true;
        if (socialSecurityNumber.isBlank() || socialSecurityNumber.isEmpty()) {
            approvedInput = false;
        }
        else if (socialSecurityNumber.equals(" ")) {
            approvedInput = false;
        }
        else if (socialSecurityNumber.charAt(0) == ((" ").charAt(0))) {
            approvedInput = false;
        }
        else if (!isDigits(socialSecurityNumber)) {
            approvedInput = false;
        }

        if (!approvedInput) {
            throw new IllegalArgumentException();
        }
        else {
            this.socialSecurityNumber = socialSecurityNumber;
        }
    }

    private boolean isDigits(String inputString) {
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }


    /**
     * Setter for the general practitioner assigned to the patient
     * @param generalPractitioner the new general practitioner
     */
    public void setGeneralPractitioner(String generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }

    /**
     * Getter for the general practitioner assigned to the patient
     * @return String, general practitioner
     */
    public String getGeneralPractitioner() {
        return this.generalPractitioner;
    }

    /**
     * Method for returning the print string for the object
     * @return the print string for the object
     */
    public String toString() {
        return "Name :: " + this.getFullName() + "\n" + "Social security number :: " +
                this.getSocialSecurityNumber() + "\n" + "Diagnosis :: " + this.getDiagnosis() +
                "\nGeneral practitioner :: " + this.generalPractitioner;
    }
}
