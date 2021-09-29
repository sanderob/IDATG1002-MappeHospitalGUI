package no.ntnu.idatg2001.mappe_2_hospital.db;

import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import org.junit.jupiter.api.*;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PatientDBTest {

    public PatientDB db;
    public PatientController dbc;
    public Patient p1;

    @BeforeEach
    void initialize() {
        db = PatientDB.getInstance();
        dbc = PatientController.getInstance();
        dbc.clearDatabase();
        p1 = new Patient("Test", "lastName", "sssss", "testgp");
    }

    @AfterEach
    void endTesting() {
        if (db.isEntityFactoryOpen()) {
            db.closeFactory();
        }

    }

    @Test
    void getInstanceTest() {
        db = PatientDB.getInstance();
        assertEquals(db.getClass(), PatientDB.class);
    }

    @Test
    void updateInDBWithoutSocialNumberTest() {
        db.addToDatabase(p1);
        p1.setFirstName("TestFirstName");
        assertNotEquals(db.getPatientsFromDB().get(0).getFirstName(), p1.getFirstName());
        db.updateInDB(p1);
        assertEquals(db.getPatientsFromDB().get(0).getFirstName(), p1.getFirstName());
        dbc.removePatient(p1);
    }

    @Test
    void updateInDBWithSocialNumberTest() {
        db.addToDatabase(p1);
        String oldNUmber = p1.getSocialSecurityNumber();
        p1.setSocialSecurityNumber("123");
        assertNotEquals(p1.getSocialSecurityNumber(), db.getPatientsFromDB().get(0).getSocialSecurityNumber());
        db.updateInDB(p1, oldNUmber);
        assertEquals(p1.getSocialSecurityNumber(), db.getPatientsFromDB().get(0).getSocialSecurityNumber());
        dbc.removePatient(p1);
    }

    @Test
    void addToDatabaseTest() {
        assertEquals(0, db.getPatientsFromDB().size());
        db.addToDatabase(p1);
        assertEquals(1, db.getPatientsFromDB().size());
        dbc.removePatient(p1);
    }

    @Test
    void removeFromDatabaseTest() {
        db.addToDatabase(p1);
        assertEquals(1, db.getPatientsFromDB().size());
        db.removeFromDB(p1);
        assertEquals(0, db.getPatientsFromDB().size());
    }

    @Test
    void getPatientsFromDBTest() {
        db.addToDatabase(p1);
        assertEquals(db.getPatientsFromDB().getClass(), Vector.class);
        assertEquals(1, db.getPatientsFromDB().size());
        assertEquals(db.getPatientsFromDB().get(0).getSocialSecurityNumber(), p1.getSocialSecurityNumber());
        dbc.removePatient(p1);
    }

    @Test
    void closeFactoryTest() {
        assertTrue(db.isEntityFactoryOpen());
        dbc.closeConnection();
        assertFalse(db.isEntityFactoryOpen());
    }


}
