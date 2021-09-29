package no.ntnu.idatg2001.mappe_2_hospital.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import no.ntnu.idatg2001.mappe_2_hospital.db.PatientController;
import no.ntnu.idatg2001.mappe_2_hospital.exceptions.SwitchCaseException;
import no.ntnu.idatg2001.mappe_2_hospital.fileSerialization.LoadCSV;
import no.ntnu.idatg2001.mappe_2_hospital.fileSerialization.SaveCSV;
import no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory.ViewGenerator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the main page of the application
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class MainPageController implements Initializable {

    /**
     * Defining fields and variables from the fx:id in the FXML document
     */
    @FXML
    private TableView<Patient> tableView;

    @FXML
    private TableColumn<Patient, String> firstNameColumn;

    @FXML
    private TableColumn<Patient, String> lastNameColumn;

    @FXML
    private TableColumn<Patient, String> diagnosisColumn;

    @FXML
    private TableColumn<Patient, String> ssnColumn;

    @FXML
    private TableColumn<Patient, String> gpColumn;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField searchBar;

    @FXML
    private ProgressBar progressBar;

    private ViewGenerator viewGenerator;
    private boolean darkMode = false;
    private PatientController pc;

    /**
     * Method for filtering the table view by a string from the search bar.
     */
    public void setSearchChangeListener() {
        searchBar.textProperty().addListener(new ChangeListener<>() {
            final ObservableList<Patient> completeList = tableView.getItems();

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                ObservableList<Patient> filteredList = FXCollections.observableArrayList();

                if (newValue.length() < oldValue.length()) {
                    tableView.setItems(completeList);
                }

                int columns = tableView.getColumns().size();
                int rows = tableView.getItems().size();

                for (int row = 0; row < rows; row++) {
                    for (int column = 0; column < columns; column++) {
                        String cellValue = "" + tableView.getColumns().get(column).getCellData(row);
                        if (cellValue.toLowerCase().contains(newValue.toLowerCase())) {
                            filteredList.add(tableView.getItems().get(row));
                            break;
                        }
                    }
                }
                tableView.setItems(filteredList);
                statusLabel.setText("Showing search result – " + tableView.getItems().size() + " results");
                if (newValue.length() == 0) {
                    statusLabel.setText("Search finished");
                }
            }
        });
    }


    /**
     * Defines the method called when the add patient button is clicked. Opens a new window and takes information for
     * the fields.
     */
    public void addButtonClick() {
        int count = tableView.getItems().size();
        try {
            String status = viewGenerator.getInputWindow(Type.ADD, null, darkMode);
            if (count < tableView.getItems().size()) {
                setLabelText(status);
            }
        } catch (SwitchCaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * defines the method called when the edit patient button is clicked. Opens a new window with the patient info
     * and lets the user edit
     */
    public void editButtonClick() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            try {
                String status = viewGenerator.getInputWindow(Type.EDIT, tableView.getSelectionModel().getSelectedItem(), darkMode);
                setLabelText(status);
            } catch (SwitchCaseException e) {
                e.printStackTrace();
            }
            tableView.refresh();
        }
        else {
            ViewGenerator.getNotSelectedAlert();
        }
    }


    /**
     * Defines the method called when the delete patient button is clicked. Asks if the user actually wants to
     * delete a patient.
     */
    public void deleteClick() {
        if (ViewGenerator.getDeleteAlert()) {
            pc.removePatient(tableView.getSelectionModel().getSelectedItem());
            setLabelText("One patient was deleted");
        }
    }


    /**
     * Defines the method called when the user clicks the exit button in the menu
     */
    public void exitClick() {
        PatientRegisterApplication.closeApp();
    }


    /**
     * Defines the method called when the user clicks the change viewmode button in the menu. Changes between darkmode
     * and lightmode
     */
    public void viewClick() {
        if (!darkMode) {
            darkMode = true;
            PatientRegisterApplication.getScene().getStylesheets().add("FXML/dark-mode.css");
            setLabelText("Dark mode activated");
        }
        else {
            PatientRegisterApplication.getScene().getStylesheets().remove("FXML/dark-mode.css");
            darkMode = false;
            setLabelText("Dark mode deactivated");
        }
    }


    /**
     * Method defines the method called when the user clicks the about button in the menu
     */
    public void aboutClick() {
        ViewGenerator.getABoutBox();
    }


    /**
     * Method that defines the method called when the user clicks the Import from CSV button.
     */
    public void csvImportClick() {
        final String[] statusText = new String[1];
        LoadCSV.chooseFile();
        progressBar.setDisable(false);
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        new Thread() {
            @Override
            public void run() {
                statusText[0] = LoadCSV.loadCSV();
                Platform.runLater(() -> progressBar.setProgress(0));
                progressBar.setDisable(true);
            }
        }.start();
        setLabelText(statusText[0]);
    }

    /**
     * Method that defines the method called when the user clicks the Export to csv button
     */
    public void csvSaveClick() {
        final String[] statusText = new String[1];
        SaveCSV.createFile();
        progressBar.setDisable(false);
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        new Thread() {
            @Override
            public void run() {
                statusText[0] = SaveCSV.saveCSV();
                Platform.runLater(() -> progressBar.setProgress(0));
                Platform.runLater(() -> setLabelText(statusText[0]));
                progressBar.setDisable(true);
            }
        }.start();
    }

    /**
     * Method to be called when the clear database menu item has been clicked
     */
    public void clearClick() {
        if (ViewGenerator.getClearAlert()) {
            progressBar.setDisable(false);
            progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            new Thread() {
                @Override
                public void run() {
                    pc.clearDatabase();
                    tableView.getItems().clear();
                    Platform.runLater(() -> progressBar.setProgress(0));
                    progressBar.setDisable(true);
                }
            }.start();
            setLabelText("Database and table has been cleared");
        }
    }

    /**
     * Method that initializes the tableview when the application is opened
     */
    public void initializeTable() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("socialSecurityNumber"));
        gpColumn.setCellValueFactory(new PropertyValueFactory<>("generalPractitioner"));
        tableView.setItems(pc.getList());
        setLabelText("OK");
    }

    /**
     * Method called to change the status text in the status field of the home view
     * @param s the string that the status text should be replaced with
     */
    public void setLabelText(String s) {
        this.statusLabel.setText(s);
    }

    /**
     * The first method called when the home page is opened
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.pc = PatientController.getInstance();
        this.viewGenerator = new ViewGenerator();
        this.initializeTable();
        this.setSearchChangeListener();
    }
}
