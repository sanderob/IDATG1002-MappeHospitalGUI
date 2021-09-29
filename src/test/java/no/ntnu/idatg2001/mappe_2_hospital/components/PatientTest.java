package no.ntnu.idatg2001.mappe_2_hospital.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    Patient patient;

    @BeforeEach
    void initialization() {
        patient = new Patient();
    }

    @Test
    void patientConstructorTest() {
        assertNull(patient.getFirstName());
        assertNull(patient.getLastName());
        assertNull(patient.getFullName());
        assertNull(patient.getSocialSecurityNumber());
        assertNull(patient.getDiagnosis());
        assertNull(patient.getGeneralPractitioner());
    }

    @Test
    void patientConstructorParameterTest() {
        patient = new Patient("Test", "Tester", "111", "testgp");
        assertNull(patient.getDiagnosis());
        patient.setDiagnosis("Test Diagnosis");
        assertEquals("Test Diagnosis", patient.getDiagnosis());
        assertEquals("Test", patient.getFirstName());
        assertEquals("Tester", patient.getLastName());
        assertEquals("Test Tester", patient.getFullName());
        assertEquals("111", patient.getSocialSecurityNumber());
        assertEquals("testgp", patient.getGeneralPractitioner());
    }

    @Test
    void toStringTest() {
        patient = new Patient("Test", "Tester", "111", "testgp");
        patient.setDiagnosis("Test Diagnosis");
        assertEquals("Name :: Test Tester\nSocial security number :: 111\nDiagnosis :: Test Diagnosis\n" +
                "General practitioner :: testgp", patient.toString());
    }

    @Test
    void illegalArgumentTest() {
        assertNull(patient.getFirstName());
        assertThrows(IllegalArgumentException.class, () -> patient.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> patient.setFirstName(" test"));
        assertThrows(IllegalArgumentException.class, () -> patient.setLastName(" test"));
        assertThrows(IllegalArgumentException.class, () -> patient.setLastName(" "));
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber(" test"));
        assertThrows(IllegalArgumentException.class, () -> patient.setSocialSecurityNumber(" "));
    }

    @Test
    void setterTest() {
        patient.setFirstName("first");
        patient.setLastName("last");
        patient.setGeneralPractitioner("testgp");
        patient.setSocialSecurityNumber("123");
        assertEquals("first", patient.getFirstName());
        assertEquals("last", patient.getLastName());
        assertEquals("testgp", patient.getGeneralPractitioner());
        assertEquals("123", patient.getSocialSecurityNumber());
    }

}
