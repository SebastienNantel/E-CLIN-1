import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClinicTest {

    private Clinic fifoClinic;
    private Clinic gravityClinic;

    @Before
    public void initialiseFifoClinic() {
        fifoClinic = new Clinic(TriageType.FIFO);
    }

    @Before
    public void initialiseGravityClinic() {
        gravityClinic = new Clinic(TriageType.GRAVITY);
    }

    @Test
    public void shouldHave1PatientInTheDoctorFileAnd0InRadiologyFile() {
        // Given 1 patient with a migrain
        // When Sorting patient with the FIFO
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.MIGRAINE);

        // Then patient is added to doctor file only and is the first
        String patientName = fifoClinic.getDoctorFile().getFirst().getName();
        assertEquals(patientName, "Joe");
        assertEquals(fifoClinic.getRadiologyFile().size(), 0);
    }

    @Test
    public void shouldHave2patientInTheDoctorFileAnd0InRadiologyFile() {
        // Given 1 patient already in the clinic and a new patient with flu
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // When sorting the second patient
        fifoClinic.triagePatient("Bob", 1, VisibleSymptom.FLU);

        // Then the second patient should be the last patient in the doctor file
        // and the radiology file should be empty
        String secondPatientName = fifoClinic.getDoctorFile().getLast().getName();
        assertEquals(secondPatientName, "Bob");
        assertEquals(fifoClinic.getRadiologyFile().size(), 0);
    }

    @Test
    public void shouldHaveTheSamePatientInTheDoctorFileAndRadiologyFile() {
        // Given 1 patient with a sprain
        // When sorting the patient
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.SPRAIN);

        // Then patient should be in the doctor file and the radiology file
        String doctorFilePatientName = fifoClinic.getDoctorFile().getFirst().getName();
        String radiologyFilePatientName = fifoClinic.getRadiologyFile().getFirst().getName();
        assertEquals(doctorFilePatientName, "Joe");
        assertEquals(radiologyFilePatientName, "Joe");
    }

    @Test
    public void shouldHave2PatientInTheDoctorFileAnd1PatientInRadiologyFile() {
        // Given 1 patient ebola and 1 patient with broken bone
        // When sorting the 2 patients
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.EBOLA);
        fifoClinic.triagePatient("Bob", 1, VisibleSymptom.BROKEN_BONE);

        // Then patient with ebola should be in doctor file only and patient
        // with broken bone should be in doctor file and radiology file
        String patientWithEbolaName = fifoClinic.getDoctorFile().getFirst().getName();
        String doctorFilePatientWithBrokenBoneName = fifoClinic.getDoctorFile().getLast().getName();
        String radiologyFilePatientWithBrokenBoneName = fifoClinic.getRadiologyFile().getFirst().getName();

        assertEquals(patientWithEbolaName, "Joe");
        assertEquals(doctorFilePatientWithBrokenBoneName, "Bob");
        assertEquals(radiologyFilePatientWithBrokenBoneName, "Bob");
        assertEquals(fifoClinic.getRadiologyFile().size(), 1);
        assertEquals(fifoClinic.getDoctorFile().size(), 2);
    }

    @Test
    public void shouldHave4PatientInTheDoctorFileAnd2PatientInRadiologyFile() {
        // Given 1 patient with sprain, 1 patient with cold 1 patient with chest pain
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.SPRAIN);
        fifoClinic.triagePatient("Bob", 1, VisibleSymptom.COLD);
        fifoClinic.triagePatient("Boby", 1, VisibleSymptom.CHEST_PAIN);

        // When sorting a new patient with broken bone
        fifoClinic.triagePatient("Real", 1, VisibleSymptom.BROKEN_BONE);

        // Then doctor file should have 4 patient and radiology file should have 2
        // patient with the patient at the last position in the queue
        String lastPatientInRadiologyFileName = fifoClinic.getRadiologyFile().getLast().getName();
        assertEquals(fifoClinic.getDoctorFile().size(), 4);
        assertEquals(fifoClinic.getRadiologyFile().size(), 2);
        assertEquals(lastPatientInRadiologyFileName, "Real");
    }

    @Test
    public void shouldHave2PatientInDoctorFileWithNewPatientInFirstPosition() {
        // Given 1 patient in the doctor file and the clinic is sorting by gravity
        gravityClinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // When sorting a new patient with a gravity of 7 with the flu
        gravityClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // Then the new patient should be in the first position of the file
        String firstPatientInDoctorFileName = gravityClinic.getDoctorFile().getFirst().getName();
        assertEquals("Bob", firstPatientInDoctorFileName);
    }

    @Test
    public void shouldHave2PatientInBothFileWithNewPatientInLastPositionOfRadiologyFile() {
        // Given 1 patient in the doctor file and the radiology file with the clinic sorting
        // by gravity
        gravityClinic.triagePatient("Joe", 1, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with a gravity of 7 with broken bone
        gravityClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in the last position of the radiologie file
        String patientName = gravityClinic.getRadiologyFile().getLast().getName();
        assertEquals("Bob", patientName);
    }

    @Test
    public void shouldHave3PatientsInDoctorFileWithNewPatientInSecondPosition() {
        // Given 1 patient with gravity 7 and another patient with gravity 5 with the
        // clinic sorting by gravity
        gravityClinic.triagePatient("Joe", 5, VisibleSymptom.FLU);
        gravityClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // When sorting a new patient with a gravity of 6
        gravityClinic.triagePatient("Real", 6, VisibleSymptom.FLU);

        // Then the new patient should be placed at the second place in the file
        String patientName = gravityClinic.getDoctorFile().get(1).getName();
        String lastPatientName = gravityClinic.getDoctorFile().getLast().getName();
        assertEquals("Real", patientName);
        assertEquals("Joe", lastPatientName);
    }
}