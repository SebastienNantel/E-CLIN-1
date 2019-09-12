import org.junit.Test;

import static org.junit.Assert.*;

public class FifoQueueTest {
    @Test
    public void whenTriaging2Patient_thenPatientAreAddedToWaitingListInOrder() {
        IQueue queue = new FifoQueue();
        Patient firstPatient = new Patient("Bob", 2, VisibleSymptom.CHEST_PAIN);
        Patient secondPatient = new Patient("Joe", 2, VisibleSymptom.CHEST_PAIN);

        queue.addPatient(secondPatient);
        queue.addPatient(firstPatient);

        assertEquals(secondPatient, queue.getNextPatient());
        assertEquals(firstPatient, queue.getNextPatient());
    }

    @Test
    public void whenTriaging1PatientWithGravityOf1_thenPatientIsNotAddedToWaitingList() {
        IQueue queue = new FifoQueue();
        Patient patient = new Patient("Bob", 1, VisibleSymptom.CHEST_PAIN);

        queue.addPatient(patient);

        assertTrue(queue.isEmpty());
    }
}