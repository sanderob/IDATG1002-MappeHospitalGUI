package no.ntnu.idatg2001.mappe_2_hospital.fileSerialization;

import javafx.stage.FileChooser;
import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import no.ntnu.idatg2001.mappe_2_hospital.db.PatientController;
import no.ntnu.idatg2001.mappe_2_hospital.gui.PatientRegisterApplication;
import no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory.ViewGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * Class to save the current list of patients to a CSV-file on the computer of the user
 * @author Sander Osvik Brekke
 */
public class SaveCSV {

    private static File file;
    private static int counter;
    private static PatientController pc;
    private static String status;

    /**
     * Method that creates the CSV file at the end users chosen location and path
     */
    public static void createFile() {
        pc = PatientController.getInstance();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("Patient_register_save_file" + (LocalDate.now()).toString());
        file = fileChooser.showSaveDialog(PatientRegisterApplication.getStage());
        if (file != null) {
            if (!file.exists()) {
                writeToFile();
            } else if (ViewGenerator.getOverwriteAlert()) {
                writeToFile();
            } else {
                file = null;
            }
        }
    }

    /**
     * Method that writes the complete list of patients to the file created by another method
     */
    private static void writeToFile() {
        counter = 0;
        List<Patient> patientList = pc.getList();
        try (FileWriter fw = new FileWriter(file.getAbsolutePath()); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("firstName;lastName;generalPractitioner;socialSecurityNumber;diagnosis");
            for (Patient p : patientList) {
                bw.write(p.getFirstName() + ";");
                bw.write(p.getLastName() + ";");
                bw.write(p.getGeneralPractitioner() + ";");
                bw.write(p.getSocialSecurityNumber() + ";");
                if (p.getDiagnosis() != null) {
                    bw.write(p.getDiagnosis() + ";");
                }
                bw.newLine();
                counter++;
            }
            status = ("Exported " + Integer.toString(counter) + " Patients to " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * The public method to be called when you want to save the DB to a file
     * @return String, the new status label text
     */
    public static String saveCSV() {
        if (file == null) {
            status = ("Export of patients was aborted");
        }
        return status;
    }
}
