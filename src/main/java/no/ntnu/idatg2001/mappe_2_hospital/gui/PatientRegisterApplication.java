package no.ntnu.idatg2001.mappe_2_hospital.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class representing the GUI mainframe
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class PatientRegisterApplication extends Application {

    private static Scene scene;
    private static Stage stage;

    /**
     * The first method called when the application is started, making the GUI window, stage and first scene accordin
     * to the MainPage.fxml
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Patient Register");
        scene = new Scene(FXMLLoader.load(getClass().getResource("/FXML/mainpage.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Getter for the main stage
     * @return the main stage
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Getter for the current scene at the stage
     * @return
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * Method for closing the application
     */
    public static void closeApp() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }

}
