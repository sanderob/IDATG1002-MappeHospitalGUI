package no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory;

import javafx.scene.control.Alert;
import no.ntnu.idatg2001.mappe_2_hospital.exceptions.SwitchCaseException;

/**
 * Factory class for objects of type Alert
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class AlertFactory extends Alert {

    /**
     * Constructor for an Alert object
     * @param alertType the alert object created
     */
    private AlertFactory(Alert.AlertType alertType) {
        super(alertType);
    }

    /**
     * Method for getting an instance of the alert object, called by other classes
     * @param type String, the type of Alert box
     * @return the Alert box object created by the factory.
     */
    public static Alert getInstance(String type) {
        Alert alert = null;
        try {
            alert = getAlertBox(type);
        } catch (SwitchCaseException e) {
            e.printStackTrace();
        }
        return alert;
    }


    /**
     * Method containgin the switch case to create the right kind of alert box.
     * @param type String, type of alert box to create. Either "CONFIRMATION" or "INFORMATION".
     * @return the alert box created by the factory method.
     * @throws SwitchCaseException If the type passed to the method is not a part of the switch case.
     */
    private static Alert getAlertBox(String type) throws SwitchCaseException {
        switch (type) {
            case "INFORMATION":
                return new AlertFactory(AlertType.INFORMATION);
            case "CONFIRMATION":
                return new AlertFactory(AlertType.CONFIRMATION);
            default:
                throw new SwitchCaseException("The alert box type is not a part of the switch case");
        }
    }

}
