module no.ntnu.idatg2001.mappe_2_hospital{
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;
    requires jakarta.persistence;
    opens no.ntnu.idatg2001.mappe_2_hospital.components;
    opens no.ntnu.idatg2001.mappe_2_hospital.gui;
    opens no.ntnu.idatg2001.mappe_2_hospital.db;
    exports no.ntnu.idatg2001.mappe_2_hospital.components;
    exports no.ntnu.idatg2001.mappe_2_hospital.gui;
    exports no.ntnu.idatg2001.mappe_2_hospital.gui.entityFactory;
    exports no.ntnu.idatg2001.mappe_2_hospital.db;
    exports no.ntnu.idatg2001.mappe_2_hospital.exceptions;
}