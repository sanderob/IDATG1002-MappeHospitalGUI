package no.ntnu.idatg2001.mappe_2_hospital.db;

import no.ntnu.idatg2001.mappe_2_hospital.components.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientControllerTest {


    public static PatientController pc;
    public static Patient p1;
    public static Patient p2;
    public static int listSize;

    @BeforeEach
    void initialize() {
        pc = PatientController.getInstance();
        pc.clearDatabase();
        p1 = new Patient("Test", "lastName", "sssss", "testgp");
        p2 = new Patient("Test2", "lastName", "sss", "testgp2");
    }

    @AfterEach
    void closeConnection() {
        pc.closeConnection();
    }

    @Test
    void addPatientTest() {
        listSize = pc.getList().size();
        Patient p = new Patient("Test", "lastName", "sssss", "testgp");
        pc.addPatient(p);
        assertEquals((listSize + 1), pc.getList().size());
    }

    @Test
    void removePatientTest() {
        pc.addPatient(p1);
        pc.addPatient(p2);
        listSize = pc.getList().size();
        pc.removePatient(p1);
        assertEquals(pc.getList().size() + 1, listSize);
        assertEquals(pc.getList().get(0), p2);
    }

    @Test
    void socialSecurityNumberExistsTest() {
        pc.addPatient(p1);
        assertTrue(pc.socialSecurityNumberExists(p1.getSocialSecurityNumber()));
        assertFalse(pc.socialSecurityNumberExists(p2.getSocialSecurityNumber()));
    }

    @Test
    void clearDatabaseTest() {
        pc.addPatient(p1);
        pc.clearDatabase();
        assertEquals(0, pc.getList().size());
    }

    @Test
    void updateDatabaseWithSocialNumberTest() {
        pc.addPatient(p1);
        assertEquals("sssss", p1.getSocialSecurityNumber());
        p1.setSocialSecurityNumber("123");
        pc.updateDB(p1, "sssss");
        assertEquals("123", pc.getList().get(0).getSocialSecurityNumber());
    }

    @Test
    void updateDatabaseWithoutSocialNumberTest() {
        pc.addPatient(p1);
        assertEquals("Test", p1.getFirstName());
        p1.setFirstName("NewName");
        pc.updateDB(p1);
        assertEquals("NewName", pc.getList().get(0).getFirstName());
    }
}
