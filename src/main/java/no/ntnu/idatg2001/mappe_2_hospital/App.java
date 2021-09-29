package no.ntnu.idatg2001.mappe_2_hospital;

import no.ntnu.idatg2001.mappe_2_hospital.db.PatientController;
import no.ntnu.idatg2001.mappe_2_hospital.gui.PatientRegisterApplication;

/**
 * Main app class
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class App {

    public static void main(String[] args) {
        PatientController pc = PatientController.getInstance();
        PatientRegisterApplication.main(args);
        pc.closeConnection();
    }
}
