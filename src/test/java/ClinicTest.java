import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClinicTest {

    private Clinic fifoClinic;
    private Clinic gravityClinic;
    private Clinic fifoDoctorGravityRadiologyClinic;
    private Clinic gravityDoctorFifoRadiologyClinic;

    @Before
    public void initialiseFifoClinic() {
        fifoClinic = new Clinic(TriageType.FIFO);
    }

    @Before
    public void initialiseGravityClinic() {
        gravityClinic = new Clinic(TriageType.GRAVITY);
    }

    @Before
    public void initialiseFifoDoctorFileGravityRadiologyFile() {
        fifoDoctorGravityRadiologyClinic = new Clinic(TriageType.FIFO, TriageType.GRAVITY);
    }

    @Before
    public void initialiseGravityDoctorFileFifoRadiologyFile() {
        gravityDoctorFifoRadiologyClinic = new Clinic(TriageType.GRAVITY, TriageType.FIFO);
    }

    @Test
    public void shouldHave1PatientInTheDoctorFileAnd0InRadiologyFile() {
        // Given 1 patient with a migrain
        // When Sorting patient with the FIFO
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.MIGRAINE);

        // Then patient is added to doctor file only and is the first
        String patientName = fifoClinic.getDoctorFile().getFirst().getName();
        assertEquals(patientName, "Joe");
        assertEquals(0, fifoClinic.getRadiologyFile().size());
    }

    @Test
    public void shouldHave2patientInTheDoctorFileAnd0InRadiologyFile() {
        // Given 1 patient already in the clinic and a new patient with flu
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.FLU);

        // When sorting the second patient
        fifoClinic.triagePatient("Bob", 2, VisibleSymptom.FLU);

        // Then the second patient should be the last patient in the doctor file
        // and the radiology file should be empty
        String secondPatientName = fifoClinic.getDoctorFile().getLast().getName();
        assertEquals("Bob", secondPatientName);
        assertEquals(0, fifoClinic.getRadiologyFile().size());
    }

    @Test
    public void shouldHaveTheSamePatientInTheDoctorFileAndRadiologyFile() {
        // Given 1 patient with a sprain
        // When sorting the patient
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.SPRAIN);

        // Then patient should be in the doctor file and the radiology file
        String doctorFilePatientName = fifoClinic.getDoctorFile().getFirst().getName();
        String radiologyFilePatientName = fifoClinic.getRadiologyFile().getFirst().getName();
        assertEquals("Joe", doctorFilePatientName);
        assertEquals("Joe", radiologyFilePatientName);
    }

    @Test
    public void shouldHave2PatientInTheDoctorFileAnd1PatientInRadiologyFile() {
        // Given 1 patient ebola and 1 patient with broken bone
        // When sorting the 2 patients
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.EBOLA);
        fifoClinic.triagePatient("Bob", 2, VisibleSymptom.BROKEN_BONE);

        // Then patient with ebola should be in doctor file only and patient
        // with broken bone should be in doctor file and radiology file
        String patientWithEbolaName = fifoClinic.getDoctorFile().getFirst().getName();
        String doctorFilePatientWithBrokenBoneName = fifoClinic.getDoctorFile().getLast().getName();
        String radiologyFilePatientWithBrokenBoneName = fifoClinic.getRadiologyFile().getFirst().getName();
        assertEquals("Joe", patientWithEbolaName);
        assertEquals("Bob", doctorFilePatientWithBrokenBoneName);
        assertEquals("Bob", radiologyFilePatientWithBrokenBoneName);
        assertEquals(1, fifoClinic.getRadiologyFile().size());
        assertEquals(2, fifoClinic.getDoctorFile().size());
    }

    @Test
    public void shouldHave4PatientInTheDoctorFileAnd2PatientInRadiologyFile() {
        // Given 1 patient with sprain, 1 patient with cold 1 patient with chest pain
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.SPRAIN);
        fifoClinic.triagePatient("Bob", 2, VisibleSymptom.COLD);
        fifoClinic.triagePatient("Boby", 2, VisibleSymptom.CHEST_PAIN);

        // When sorting a new patient with broken bone
        fifoClinic.triagePatient("Real", 2, VisibleSymptom.BROKEN_BONE);

        // Then doctor file should have 4 patient and radiology file should have 2
        // patient with the patient at the last position in the queue
        String lastPatientInRadiologyFileName = fifoClinic.getRadiologyFile().getLast().getName();
        assertEquals(4, fifoClinic.getDoctorFile().size());
        assertEquals(2, fifoClinic.getRadiologyFile().size());
        assertEquals("Real", lastPatientInRadiologyFileName);
    }

    @Test
    public void shouldHave2PatientInDoctorFileWithNewPatientInFirstPosition() {
        // Given 1 patient in the doctor file and the clinic is sorting by gravity
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.FLU);

        // When sorting a new patient with a gravity of 7 with the flu
        gravityDoctorFifoRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // Then the new patient should be in the first position of the file
        String firstPatientInDoctorFileName = gravityDoctorFifoRadiologyClinic.getDoctorFile().getFirst().getName();
        assertEquals("Bob", firstPatientInDoctorFileName);
    }

    @Test
    public void shouldHave2PatientInBothFileWithNewPatientInLastPositionOfRadiologyFile() {
        // Given 1 patient in the doctor file and the radiology file with the clinic sorting
        // by gravity
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with a gravity of 7 with broken bone
        gravityDoctorFifoRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in the last position of the radiology file
        String patientName = gravityDoctorFifoRadiologyClinic.getRadiologyFile().getLast().getName();
        assertEquals("Bob", patientName);
    }

    @Test
    public void shouldHave3PatientsInDoctorFileWithNewPatientInSecondPosition() {
        // Given 1 patient with gravity 7 and another patient with gravity 5 with the
        // clinic sorting by gravity
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 5, VisibleSymptom.FLU);
        gravityDoctorFifoRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // When sorting a new patient with a gravity of 6
        gravityDoctorFifoRadiologyClinic.triagePatient("Real", 6, VisibleSymptom.FLU);

        // Then the new patient should be placed at the second place in the file
        String patientName = gravityDoctorFifoRadiologyClinic.getDoctorFile().get(1).getName();
        String lastPatientName = gravityDoctorFifoRadiologyClinic.getDoctorFile().getLast().getName();
        assertEquals("Real", patientName);
        assertEquals("Joe", lastPatientName);
    }

    @Test
    public void shouldOnlyHaveOnePatientInDoctorFile() {
        // Given an empty doctor file
        // When adding a new patient in a gravity sorting clinic
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.FLU);

        // The the doctor file should have one patient in the file at the first
        // position
        String patientName = gravityDoctorFifoRadiologyClinic.getDoctorFile().getFirst().getName();
        assertEquals("Joe", patientName);
    }

    @Test
    public void shouldHave2PatientInRadiologyFileWithTheNewPatientInFirstPosition() {
        // Given 1 patient with broken bone with gravity 1 and the clinic sorting
        // the doctor file with FIFO and the radiology sorting with gravity
        fifoDoctorGravityRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with gravity 7 with broken bone
        fifoDoctorGravityRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in first position
        String newPatientName = fifoDoctorGravityRadiologyClinic.getRadiologyFile().getFirst().getName();
        assertEquals("Bob", newPatientName);
        assertEquals(2, fifoDoctorGravityRadiologyClinic.getRadiologyFile().size());
    }

    @Test
    public void shouldHave3PatientInRadiologyFileWithNewPatientInSecondPosition() {
        // Given 1 patient with sprain with gravity 5 and another patient with
        // broken bone with gravity 7 with a clinic sorting radiology with gravity
        fifoDoctorGravityRadiologyClinic.triagePatient("Joe", 5, VisibleSymptom.SPRAIN);
        fifoDoctorGravityRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with sprain with gravity 6
        fifoDoctorGravityRadiologyClinic.triagePatient("Real", 6, VisibleSymptom.SPRAIN);

        // Then the new patient should be in second position in the file
        String newPatientName = fifoDoctorGravityRadiologyClinic.getRadiologyFile().get(1).getName();
        String lastPatientName = fifoDoctorGravityRadiologyClinic.getRadiologyFile().getLast().getName();
        assertEquals("Real", newPatientName);
        assertEquals("Joe", lastPatientName);
    }

    @Test
    public void shouldHaveOnlyOnePatientInRadiologyFile() {
        // Given an empty radiology file
        // When adding sorting a new patient with a gravity clinic
        fifoDoctorGravityRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in first position of the file
        String patientName = fifoDoctorGravityRadiologyClinic.getRadiologyFile().getFirst().getName();
        assertEquals("Joe", patientName);
        assertEquals(1, fifoDoctorGravityRadiologyClinic.getRadiologyFile().size());
    }

    @Test
    public void shouldNotAddAnyPatientToFifoDoctorFile() {
        // Given an empty doctor file in a clinic using a fifo
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // Then no new patient should be added to the file
        int numberOfPatientInDoctorFile = fifoClinic.getDoctorFile().size();
        assertEquals(0, numberOfPatientInDoctorFile);
    }

    @Test
    public void shouldNotAddAnyPatientToFifoRadiologyFile() {
        // Given an empty radiology file in a clinic using a fifo
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.BROKEN_BONE);

        // Then no patient should be added to the file
        int numberOfPatientInFile = fifoClinic.getRadiologyFile().size();
        assertEquals(0, numberOfPatientInFile);
    }

    @Test
    public void shouldNotAddAnyPatientToGravityDoctorFile() {
        // Given an empty doctor file in a clinic using a gravity
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        gravityClinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // Then no patient should be added to the file
        int numberOfPatientInFile = gravityClinic.getRadiologyFile().size();
        assertEquals(0, numberOfPatientInFile);
    }

    @Test
    public void shouldNotAddAnyPatientToGravityRadiologyfile() {
        // Given an empty radiology file in a clinic using a gravity
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        gravityClinic.triagePatient("Joe", 1, VisibleSymptom.BROKEN_BONE);

        // Then no patient should be added to the file
        int numberOfPatientInFile = gravityClinic.getRadiologyFile().size();
        assertEquals(0, numberOfPatientInFile);
    }
}