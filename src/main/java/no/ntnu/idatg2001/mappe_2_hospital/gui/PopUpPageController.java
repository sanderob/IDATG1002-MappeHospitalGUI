package no.ntnu.idatg2001.mappe_2_hospital.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import no.ntnu.idatg2001.mappe_2_hospital.db.PatientController;
import no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory.ViewGenerator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the PopUpPage fxml document
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class PopUpPageController implements Initializable {

    private UserData userData;
    private PatientController pc;


    /**
     * Defining {@code @FXML}-fields from the FXML file
     */
    @FXML
    TextField firstNameInput;

    @FXML
    TextField lastNameInput;

    @FXML
    TextField socialSecurityNumberInput;

    @FXML
    TextField gpInput;

    @FXML
    TextField diagnosisInput;

    @FXML
    Label headerLabel;

    @FXML
    Button okButton;

    @FXML
    Button cancelButton;

    /**
     * Method calls when the cancel button is clicked in the add/edit window
     */
    public void cancelClick() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /***
     * Method called when the OK button i clicked in the add/edit window
     */
    public void okClick() {
        Boolean exists = false;
        Boolean error = false;
        Boolean socialSecurityNumberChanged = false;
        String oldNumber = userData.getPatient().getSocialSecurityNumber();
        try {
            userData.getPatient().setFirstName(firstNameInput.getText());
            userData.getPatient().setLastName(lastNameInput.getText());
            userData.getPatient().setGeneralPractitioner(gpInput.getText());
            userData.getPatient().setDiagnosis(diagnosisInput.getText());
            
                    if (userData.getType() == Type.EDIT) {
                        if (!(userData.getPatient().getSocialSecurityNumber().equals(socialSecurityNumberInput.getText()))) {
                            if (!(pc.socialSecurityNumberExists(socialSecurityNumberInput.getText()))) {
                                oldNumber = userData.getPatient().getSocialSecurityNumber();
                                socialSecurityNumberChanged = true;
                                userData.getPatient().setSocialSecurityNumber(socialSecurityNumberInput.getText());
                            } else {
                                exists = true;
                            }
                        }
                    }
                    else if (userData.getType() == Type.ADD) {
                        if (!(pc.socialSecurityNumberExists(socialSecurityNumberInput.getText()))) {
                            userData.getPatient().setSocialSecurityNumber(socialSecurityNumberInput.getText());
                            pc.addPatient(userData.getPatient());
                        }
                        else {
                            exists = true;
                        }
                    }
        } catch (IllegalArgumentException e) {
            ViewGenerator.getIllegalArgumentBox();
            error = true;
        }

        Stage stage = (Stage) okButton.getScene().getWindow();

        if (exists) {
            ViewGenerator.getSocialSecurityNumberExistsBox();
            error = true;
        }

        if (!error) {
            if (!socialSecurityNumberChanged) {
                pc.updateDB(userData.getPatient());
            }
            else {
                pc.updateDB(userData.getPatient(), oldNumber);
            }
            stage.close();
        }
    }

    /**
     * Method that creates the fields and fills in the text boxes when the add/edit window is generated
     */
    private void showWindow() {
        if (userData.getType() == Type.ADD) {
            headerLabel.setText("Add new Patient");
            userData.setUserDataPatient(new Patient());
        }
        else if (userData.getType() == Type.EDIT) {

            headerLabel.setText("Edit Patient");
            firstNameInput.setText(userData.getPatient().getFirstName());
            lastNameInput.setText(userData.getPatient().getLastName());
            socialSecurityNumberInput.setText(userData.getPatient().getSocialSecurityNumber());
            gpInput.setText(userData.getPatient().getGeneralPractitioner());
            diagnosisInput.setText(userData.getPatient().getDiagnosis());
        }
    }


    /**
     * The first method that is called when the scene is set to the PopUpPage.fxml
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pc = PatientController.getInstance();
        userData = UserData.getUserData();
        showWindow();
    }

}
