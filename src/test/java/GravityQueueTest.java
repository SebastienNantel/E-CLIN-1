import org.junit.Test;

import static org.junit.Assert.*;

public class GravityQueueTest {
    @Test
    public void whenTriaging2PatientWithGravityLowerThan5_thenPatientAreAddedToWaitingFileInFifo() {
        IQueue queue = new GravityQueue();
        Patient firstPatient = new Patient("Bob", 2, VisibleSymptom.FLU);
        Patient secondPatient = new Patient("Joe", 3, VisibleSymptom.CHEST_PAIN);

        queue.addPatient(firstPatient);
        queue.addPatient(secondPatient);

        assertEquals(firstPatient, queue.getNextPatient());
        assertEquals(secondPatient, queue.getNextPatient());
    }

    @Test
    public void whenTriaging2PatientWithGravityHigherThan5_thenPatientAreAddedByGravityToWaitingFile() {
        IQueue queue = new GravityQueue();
        Patient firstPatient = new Patient("Joe", 6, VisibleSymptom.CHEST_PAIN);
        Patient secondPatient = new Patient("Bob", 7, VisibleSymptom.CHEST_PAIN);

        queue.addPatient(firstPatient);
        queue.addPatient(secondPatient);

        assertEquals(secondPatient, queue.getNextPatient());
        assertEquals(firstPatient, queue.getNextPatient());
    }

    @Test
    public void whenTriagingPatientWithGravityOf1_thenPatientIsNotAddedToWaitingFile() {
        IQueue queue = new GravityQueue();
        Patient patient = new Patient("Bob", 1, VisibleSymptom.CHEST_PAIN);

        queue.addPatient(patient);

        assertTrue(queue.isEmpty());
    }
}