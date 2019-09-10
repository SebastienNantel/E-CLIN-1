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
    public void initialiseFifoDoctorQueueGravityRadiologyQueue() {
        fifoDoctorGravityRadiologyClinic = new Clinic(TriageType.FIFO, TriageType.GRAVITY);
    }

    @Before
    public void initialiseGravityDoctorQueueFifoRadiologyQueue() {
        gravityDoctorFifoRadiologyClinic = new Clinic(TriageType.GRAVITY, TriageType.FIFO);
    }

    @Test
    public void shouldHave1PatientInTheDoctorQueueAnd0InRadiologyQueue() {
        // Given 1 patient with a migrain
        // When Sorting patient with the FIFO
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.MIGRAINE);

        // Then patient is added to doctor Queue only and is the first
        String patientName = fifoClinic.getDoctorQueue().getFirst().getName();
        assertEquals(patientName, "Joe");
        assertEquals(0, fifoClinic.getRadiologyQueue().size());
    }

    @Test
    public void shouldHave2patientInTheDoctorQueueAnd0InRadiologyQueue() {
        // Given 1 patient already in the clinic and a new patient with flu
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.FLU);

        // When sorting the second patient
        fifoClinic.triagePatient("Bob", 2, VisibleSymptom.FLU);

        // Then the second patient should be the last patient in the doctor Queue
        // and the radiology Queue should be empty
        String secondPatientName = fifoClinic.getDoctorQueue().getLast().getName();
        assertEquals("Bob", secondPatientName);
        assertEquals(0, fifoClinic.getRadiologyQueue().size());
    }

    @Test
    public void shouldHaveTheSamePatientInTheDoctorQueueAndRadiologyQueue() {
        // Given 1 patient with a sprain
        // When sorting the patient
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.SPRAIN);

        // Then patient should be in the doctor Queue and the radiology Queue
        String doctorQueuePatientName = fifoClinic.getDoctorQueue().getFirst().getName();
        String radiologyQueuePatientName = fifoClinic.getRadiologyQueue().getFirst().getName();
        assertEquals("Joe", doctorQueuePatientName);
        assertEquals("Joe", radiologyQueuePatientName);
    }

    @Test
    public void shouldHave2PatientInTheDoctorQueueAnd1PatientInRadiologyQueue() {
        // Given 1 patient ebola and 1 patient with broken bone
        // When sorting the 2 patients
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.EBOLA);
        fifoClinic.triagePatient("Bob", 2, VisibleSymptom.BROKEN_BONE);

        // Then patient with ebola should be in doctor Queue only and patient
        // with broken bone should be in doctor Queue and radiology Queue
        String patientWithEbolaName = fifoClinic.getDoctorQueue().getFirst().getName();
        String doctorQueuePatientWithBrokenBoneName = fifoClinic.getDoctorQueue().getLast().getName();
        String radiologyQueuePatientWithBrokenBoneName = fifoClinic.getRadiologyQueue().getFirst().getName();
        assertEquals("Joe", patientWithEbolaName);
        assertEquals("Bob", doctorQueuePatientWithBrokenBoneName);
        assertEquals("Bob", radiologyQueuePatientWithBrokenBoneName);
        assertEquals(1, fifoClinic.getRadiologyQueue().size());
        assertEquals(2, fifoClinic.getDoctorQueue().size());
    }

    @Test
    public void shouldHave4PatientInTheDoctorQueueAnd2PatientInRadiologyQueue() {
        // Given 1 patient with sprain, 1 patient with cold 1 patient with chest pain
        fifoClinic.triagePatient("Joe", 2, VisibleSymptom.SPRAIN);
        fifoClinic.triagePatient("Bob", 2, VisibleSymptom.COLD);
        fifoClinic.triagePatient("Boby", 2, VisibleSymptom.CHEST_PAIN);

        // When sorting a new patient with broken bone
        fifoClinic.triagePatient("Real", 2, VisibleSymptom.BROKEN_BONE);

        // Then doctor Queue should have 4 patient and radiology Queue should have 2
        // patient with the patient at the last position in the queue
        String lastPatientInRadiologyQueueName = fifoClinic.getRadiologyQueue().getLast().getName();
        assertEquals(4, fifoClinic.getDoctorQueue().size());
        assertEquals(2, fifoClinic.getRadiologyQueue().size());
        assertEquals("Real", lastPatientInRadiologyQueueName);
    }

    @Test
    public void shouldHave2PatientInDoctorQueueWithNewPatientInFirstPosition() {
        // Given 1 patient in the doctor Queue and the clinic is sorting by gravity
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.FLU);

        // When sorting a new patient with a gravity of 7 with the flu
        gravityDoctorFifoRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // Then the new patient should be in the first position of the Queue
        String firstPatientInDoctorQueueName = gravityDoctorFifoRadiologyClinic.getDoctorQueue().getFirst().getName();
        assertEquals("Bob", firstPatientInDoctorQueueName);
    }

    @Test
    public void shouldHave2PatientInBothQueueWithNewPatientInLastPositionOfRadiologyQueue() {
        // Given 1 patient in the doctor Queue and the radiology Queue with the clinic sorting
        // by gravity
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with a gravity of 7 with broken bone
        gravityDoctorFifoRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in the last position of the radiology Queue
        String patientName = gravityDoctorFifoRadiologyClinic.getRadiologyQueue().getLast().getName();
        assertEquals("Bob", patientName);
    }

    @Test
    public void shouldHave3PatientsInDoctorQueueWithNewPatientInSecondPosition() {
        // Given 1 patient with gravity 7 and another patient with gravity 5 with the
        // clinic sorting by gravity
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 3, VisibleSymptom.FLU);
        gravityDoctorFifoRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // When sorting a new patient with a gravity of 6
        gravityDoctorFifoRadiologyClinic.triagePatient("Real", 6, VisibleSymptom.FLU);

        // Then the new patient should be placed at the second place in the Queue
        String patientName = gravityDoctorFifoRadiologyClinic.getDoctorQueue().get(1).getName();
        String lastPatientName = gravityDoctorFifoRadiologyClinic.getDoctorQueue().getLast().getName();
        assertEquals("Real", patientName);
        assertEquals("Joe", lastPatientName);
    }

    @Test
    public void shouldOnlyHaveOnePatientInDoctorQueue() {
        // Given an empty doctor Queue
        // When adding a new patient in a gravity sorting clinic
        gravityDoctorFifoRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.FLU);

        // The the doctor Queue should have one patient in the Queue at the first
        // position
        String patientName = gravityDoctorFifoRadiologyClinic.getDoctorQueue().getFirst().getName();
        assertEquals("Joe", patientName);
    }

    @Test
    public void shouldHave2PatientInRadiologyQueueWithTheNewPatientInFirstPosition() {
        // Given 1 patient with broken bone with gravity 1 and the clinic sorting
        // the doctor Queue with FIFO and the radiology sorting with gravity
        fifoDoctorGravityRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with gravity 7 with broken bone
        fifoDoctorGravityRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in first position
        String newPatientName = fifoDoctorGravityRadiologyClinic.getRadiologyQueue().getFirst().getName();
        assertEquals("Bob", newPatientName);
        assertEquals(2, fifoDoctorGravityRadiologyClinic.getRadiologyQueue().size());
    }

    @Test
    public void shouldHave3PatientInRadiologyQueueWithNewPatientInSecondPosition() {
        // Given 1 patient with sprain with gravity 5 and another patient with
        // broken bone with gravity 7 with a clinic sorting radiology with gravity
        fifoDoctorGravityRadiologyClinic.triagePatient("Joe", 3, VisibleSymptom.SPRAIN);
        fifoDoctorGravityRadiologyClinic.triagePatient("Bob", 7, VisibleSymptom.BROKEN_BONE);

        // When sorting a new patient with sprain with gravity 6
        fifoDoctorGravityRadiologyClinic.triagePatient("Real", 6, VisibleSymptom.SPRAIN);

        // Then the new patient should be in second position in the Queue
        String newPatientName = fifoDoctorGravityRadiologyClinic.getRadiologyQueue().get(1).getName();
        String lastPatientName = fifoDoctorGravityRadiologyClinic.getRadiologyQueue().getLast().getName();
        assertEquals("Real", newPatientName);
        assertEquals("Joe", lastPatientName);
    }

    @Test
    public void shouldHaveOnlyOnePatientInRadiologyQueue() {
        // Given an empty radiology Queue
        // When adding sorting a new patient with a gravity clinic
        fifoDoctorGravityRadiologyClinic.triagePatient("Joe", 2, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be in first position of the Queue
        String patientName = fifoDoctorGravityRadiologyClinic.getRadiologyQueue().getFirst().getName();
        assertEquals("Joe", patientName);
        assertEquals(1, fifoDoctorGravityRadiologyClinic.getRadiologyQueue().size());
    }

    @Test
    public void shouldNotAddAnyPatientToFifoDoctorQueue() {
        // Given an empty doctor Queue in a clinic using a fifo
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // Then no new patient should be added to the Queue
        int numberOfPatientInDoctorQueue = fifoClinic.getDoctorQueue().size();
        assertEquals(0, numberOfPatientInDoctorQueue);
    }

    @Test
    public void shouldNotAddAnyPatientToFifoRadiologyQueue() {
        // Given an empty radiology Queue in a clinic using a fifo
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        fifoClinic.triagePatient("Joe", 1, VisibleSymptom.BROKEN_BONE);

        // Then no patient should be added to the Queue
        int numberOfPatientInQueue = fifoClinic.getRadiologyQueue().size();
        assertEquals(0, numberOfPatientInQueue);
    }

    @Test
    public void shouldNotAddAnyPatientToGravityDoctorQueue() {
        // Given an empty doctor Queue in a clinic using a gravity
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        gravityClinic.triagePatient("Joe", 1, VisibleSymptom.FLU);

        // Then no patient should be added to the Queue
        int numberOfPatientInQueue = gravityClinic.getRadiologyQueue().size();
        assertEquals(0, numberOfPatientInQueue);
    }

    @Test
    public void shouldNotAddAnyPatientToGravityRadiologyQueue() {
        // Given an empty radiology Queue in a clinic using a gravity
        // sorting algorithm
        // When sorting a new patient with a gravity of 1
        gravityClinic.triagePatient("Joe", 1, VisibleSymptom.BROKEN_BONE);

        // Then no patient should be added to the Queue
        int numberOfPatientInQueue = gravityClinic.getRadiologyQueue().size();
        assertEquals(0, numberOfPatientInQueue);
    }

    @Test
    public void shouldAddNewPatientAtTheEndOfTheDoctorQueue() {
        // Given 1 patient with gravity 7 with flu, another patient with
        // gravity 3 with flu in a clinic using gravity as sorting
        // algorithm
        gravityClinic.triagePatient("Joe", 3, VisibleSymptom.FLU);
        gravityClinic.triagePatient("Bob", 7, VisibleSymptom.FLU);

        // When sorting a new patient with gravity 4 with flu
        gravityClinic.triagePatient("Real", 4, VisibleSymptom.FLU);

        // Then the new patient should be at the end of the doctor Queue
        String newPatientName = gravityClinic.getDoctorQueue().getLast().getName();
        assertEquals("Real", newPatientName);
    }

    @Test
    public void shouldAddNewPatientAtTheEndOfTheRadiologyQueue() {
        // Given 1 patient with gravity 7 with sprain, another patient with
        // gravity 3 with sprain in a clinic using gravity as sorting
        // algorithm
        gravityClinic.triagePatient("Joe", 3, VisibleSymptom.SPRAIN);
        gravityClinic.triagePatient("Bob", 7, VisibleSymptom.SPRAIN);

        // When sorting a new patient with gravity 4 with broken bone
        gravityClinic.triagePatient("Real", 4, VisibleSymptom.BROKEN_BONE);

        // Then the new patient should be at the end of the radiology Queue
        String newPatientName = gravityClinic.getRadiologyQueue().getLast().getName();
        assertEquals("Real", newPatientName);
    }
}