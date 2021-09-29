package no.ntnu.idatg2001.mappe_2_hospital.fileSerialization;

import javafx.stage.FileChooser;
import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import no.ntnu.idatg2001.mappe_2_hospital.db.PatientController;
import no.ntnu.idatg2001.mappe_2_hospital.gui.PatientRegisterApplication;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Class to load a CSV file with patients to the application
 * @author Sander Osvik Brekke
 */
public class LoadCSV {

    private static File file;
    private static String status;
    private static int counter;
    private static PatientController pc;

    /**
     * Method that creates a file object of the CSV file the end user chooses from a custom path
     */
    public static void chooseFile() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        file = chooser.showOpenDialog(PatientRegisterApplication.getStage());
    }

    /**
     * Method that reads the file chosen in a different method. Reads either four or five elements per line, ";"
     * separated. Creates an arrayList with new patients from the CSV file
     * @return ArrayList<Patient> with all the new patients from the file
     */
    private static ArrayList<Patient> readFile() {
        Patient patient;
        String thisLine;
        counter = 0;
        ArrayList<Patient> patientList = new ArrayList<>();
        try (FileReader fr = new FileReader(file.getAbsolutePath()); BufferedReader br = new BufferedReader(fr)) {
            while ((thisLine = br.readLine()) != null) {
                String[] array = thisLine.split(";");
                if (!(array[0].equals("firstName") && array[1].equals("lastName") && array[2]
                        .equals("generalPractitioner") && array[3].equals("socialSecurityNumber"))) {
                    patient = new Patient(array[0], array[1], array[3], array[2]);
                    if (array.length == 5) {
                        patient.setDiagnosis(array[4]);
                    }
                    if (!(pc.socialSecurityNumberExists(patient.getSocialSecurityNumber()))) {
                        patientList.add(patient);
                        counter++;
                    }

                }
            }
            status = ("Added " + Integer.toString(counter) + " Patients imported from " + file.getName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return patientList;
    }

    /**
     * Method that adds the patients from a list to the database and the current tableview list
     * @param patientList the list to add the patients from
     */
    private static void addPatientsFromCSV(ArrayList<Patient> patientList) {
        for (Patient p : patientList) {
            pc.addPatient(p);
        }
    }


    /**
     * Public method to be called to load a CSV file. The method calls other methods in the class and guides the user
     * through it
     * @return String the new status label text for the status bar
     */
    public static String loadCSV() {
        pc = PatientController.getInstance();
        if (file != null) {
            addPatientsFromCSV(readFile());
        }
        return status;

    }


}
