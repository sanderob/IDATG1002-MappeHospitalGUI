package no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import no.ntnu.idatg2001.mappe_2_hospital.exceptions.SwitchCaseException;
import no.ntnu.idatg2001.mappe_2_hospital.gui.Type;
import no.ntnu.idatg2001.mappe_2_hospital.gui.UserData;

import java.io.IOException;
import java.util.Optional;

/**
 * Class for different help methods needed by the GUI controller classes
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class ViewGenerator {


    /**
     * Method that creates either a window for adding patients or for editing paitients
     * @param type the type of window, either {@code "ADD"} or {@code "EDIT"}
     * @param patient the patient to be edited, or a new patient if it is added
     * @param darkMode if the window should be in darkmode.
     * @return the new status label text
     */
    public String getInputWindow(Type type, Patient patient, boolean darkMode) throws SwitchCaseException {
        UserData userData = UserData.getUserData();
        Stage stage = new Stage();
        Scene scene;
        String status;

        switch (type) {
            case EDIT: {
                userData.setUserDataType(Type.EDIT);
                userData.setUserDataPatient(patient);
                stage.setTitle("Patient Register – Edit Patient");
                status = "Edited one patient";
                break;
            }
            case ADD: {
                userData.setUserDataType(Type.ADD);
                stage.setTitle("Patient Register – Add new Patient");
                status = "Added one patient";
                break;
            }
            default: {
                throw new SwitchCaseException("The switch case input does not exist");
            }

        }

        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("/FXML/popuppage.fxml")));
            if (darkMode) {
                scene.getStylesheets().add("FXML/dark-mode.css");
            }
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.showAndWait();
        return status;
    }


    /**
     * Method that asks the user if it really wants to delete something
     * @return true or false if the user wants to delete it
     */
    public static boolean getDeleteAlert() {
        Alert alert = AlertFactory.getInstance("CONFIRMATION");
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this patient from the list?");

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }

    /**
     * A method that asks the user if it wants to overwrite a file that when exporting a CSV-file
     * @return true or false if the user wants to overwrite the file
     */
    public static boolean getOverwriteAlert() {
        Alert alert = AlertFactory.getInstance("CONFIRMATION");
        alert.setTitle("Overwrite Confirmation Dialog");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Are you sure you want to overwrite the existing file?");

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }

    public static boolean getClearAlert() {
        Alert alert = AlertFactory.getInstance("CONFIRMATION");
        alert.setTitle("Clear Confirmation Dialog");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Are you sure you want to clear and delete the database?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Method that alerts the user that one or more of the fields during an add or an edit is invalid
     */
    public static void getIllegalArgumentBox() {
        Alert alert = AlertFactory.getInstance("INFORMATION");
        alert.setTitle("Illegal input");
        alert.setHeaderText("You have entered an illegal input");
        alert.setContentText("The fields first name, last name and social security number may not be empty and " +
                "may not begin with a space. The field social security number may not contain anything other than digits");
        alert.showAndWait();
    }

    /**
     * A method that creates the about information box from the menu
     */
    public static void getABoutBox() {
        Alert alert = AlertFactory.getInstance("INFORMATION");
        alert.setTitle("About");
        alert.setHeaderText(" Patient Register \n v0.1-SNAPSHOT");
        alert.setContentText(" A patient register \n created with joy by: (C)Sander Osvik Brekke \n 2021-05-21");
        alert.showAndWait();
    }

    /**
     * A method that gives the user a warning if they press the delete button when there are no patients selected
     */
    public static void getNotSelectedAlert() {
        Alert alert = AlertFactory.getInstance("INFORMATION");
        alert.setTitle("Failed to edit");
        alert.setHeaderText("No patients are selected");
        alert.setContentText("You are not able to edit a patient without selecting it first." +
                " Please select a patient in the list and try again.");
        alert.showAndWait();
    }

    /**
     * A method that gives the user a warning when they are adding or editing a patient and tries to save a social
     * security number that already exists in the database
     */
    public static void getSocialSecurityNumberExistsBox() {
        Alert alert = AlertFactory.getInstance("INFORMATION");
        alert.setTitle("Failed to edit");
        alert.setHeaderText("Patient identity already exists");
        alert.setContentText("The social security number you are entering is already existing with another patient.");
        alert.showAndWait();
    }
}
