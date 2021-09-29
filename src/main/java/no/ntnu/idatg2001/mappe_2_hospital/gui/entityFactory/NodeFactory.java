package no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatg2001.mappe_2_hospital.exceptions.SwitchCaseException;


/**
 * A factory class for creating GUI elements that extends the object type Node.
 * @author Sander Osvik Brekke
 * @version 23.04.2021
 */
public class NodeFactory extends Node {

    private NodeFactory() {
        super();
    }

    /**
     * Method for getting a node, using the getInstance() method to get the correct node
     * @param type String type of node
     * @return the instance of the node type
     */
    public static Node getNode(String type) {
        Node node = null;
        try {
            node = getInstance(type);
        } catch (SwitchCaseException e) {
            e.printStackTrace();
        }
        return node;
    }

    /**
     * Method to give getNode the correct node type.
     * @param type the type of node to be instantiated
     * @return Node object that is created
     * @throws SwitchCaseException if the type given is not a part of the switch case.
     */
    private static Node getInstance(String type) throws SwitchCaseException {
        switch (type) {
            case ("BorderPane") :
                return new BorderPane();
            case ("AnchorPane") :
                return new AnchorPane();
            case ("VBox") :
                return new VBox();
            case ("HBox") :
                return new HBox();
            case ("ToolBar") :
                return new ToolBar();
            case ("MenuBar") :
                return new MenuBar();
            case ("Text") :
                return new Text();
            case ("Label") :
                return new Label();
            case ("Button") :
                return new Button();
            case ("ButtonBar") :
                return new ButtonBar();
            default :
                throw new SwitchCaseException("The Instance you are trying to create is not found in the switch case");
        }
    }

}
