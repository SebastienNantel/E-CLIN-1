import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClinicTest {

    private Clinic clinic;

    @Before
    public void initialiseClinic() {
        clinic = new Clinic();
    }

    @Test
    public void shouldHave1PatientInTheDoctorFileAnd0InRadiologyFile() {
        // Given 1 patient with a migrain
        // When Sorting patient
        clinic.triagePatient("Joe", 1, VisibleSymptom.MIGRAINE);

        // Then patient is added to doctor file only and is the first
        String patient = clinic.getDoctorFile().getFirst();
        assertEquals(patient, "Joe");
        assertEquals(clinic.getRadiologyFile().size(), 0);
    }

    @Test
    public void shouldHave2patientInTheDoctorFileAnd0InRadiologyFile() {
        // Given 1 patient already in the clinic and a new patient with flu
        clinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // When sorting the second patient
        clinic.triagePatient("Bob", 1, VisibleSymptom.FLU);

        // Then the second patient should be the last patient in the doctor file
        // and the radiology file should be empty
        String secondPatient = clinic.getDoctorFile().getLast();
        assertEquals(secondPatient, "Bob");
        assertEquals(clinic.getRadiologyFile().size(), 0);
    }

    @Test
    public void shouldHaveTheSamePatientInTheDoctorFileAndRadiologyFile() {
        // Given 1 patient with a sprain
        // When sorting the patient
        clinic.triagePatient("Joe", 1, VisibleSymptom.SPRAIN);

        // Then patient should be in the doctor file and the radiology file
        String doctorFilePatient = clinic.getDoctorFile().getFirst();
        String radiologyFilePatient = clinic.getRadiologyFile().getFirst();
        assertEquals(doctorFilePatient, "Joe");
        assertEquals(radiologyFilePatient, "Joe");
    }

    @Test
    public void shouldHave2PatientInTheDoctorFileAnd1PatientInRadiologyFile() {
        // Given 1 patient ebola and 1 patient with broken bone
        // When sorting the 2 patients
        clinic.triagePatient("Joe", 1, VisibleSymptom.EBOLA);
        clinic.triagePatient("Bob", 1, VisibleSymptom.BROKEN_BONE);

        // Then patient with ebola should be in doctor file only and patient
        // with broken bone should be in doctor file and radiology file
        String patientWithEbola = clinic.getDoctorFile().getFirst();
        String doctorFilePatientWithBrokenBone = clinic.getDoctorFile().getLast();
        String radiologyFilePatientWithBrokenBone = clinic.getRadiologyFile().getFirst();

        assertEquals(patientWithEbola, "Joe");
        assertEquals(doctorFilePatientWithBrokenBone, "Bob");
        assertEquals(radiologyFilePatientWithBrokenBone, "Bob");
        assertEquals(clinic.getRadiologyFile().size(), 1);
        assertEquals(clinic.getDoctorFile().size(), 2);
    }

    @Test
    public void shouldHave4PatientInTheDoctorFileAnd2PatientInRadiologyFile() {
        // Given 1 patient with sprain, 1 patient with cold 1 patient with chest pain
        clinic.triagePatient("Joe", 1, VisibleSymptom.SPRAIN);
        clinic.triagePatient("Bob", 1, VisibleSymptom.COLD);
        clinic.triagePatient("Boby", 1, VisibleSymptom.CHEST_PAIN);

        // When sorting a new patient with broken bone
        clinic.triagePatient("Real", 1, VisibleSymptom.BROKEN_BONE);

        // Then doctor file should have 4 patient and radiology file should have 2
        // patient with the patient at the last position in the queue
        String lastPatientInRadiologyFile = clinic.getRadiologyFile().getLast();
        assertEquals(clinic.getDoctorFile().size(), 4);
        assertEquals(clinic.getRadiologyFile().size(), 2);
        assertEquals(lastPatientInRadiologyFile, "Real");
    }
}