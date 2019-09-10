import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommunityCenterTest {
    private CommunityCenter fifoCommunityCenter;
    private CommunityCenter gravityCommunityCenter;

    @Before
    public void initialiseCommunityCenterWithFifo() {
        fifoCommunityCenter = new CommunityCenter(TriageType.FIFO);
    }

    @Before
    public void initialiseCommunityCenterWithGravity() {
        gravityCommunityCenter = new CommunityCenter(TriageType.GRAVITY);
    }

    @Test
    public void shouldHaveOnePatientInNurseQueue() {
        // Given 1 a community center using fifo sorting
        // When sorting a new patient
        fifoCommunityCenter.triagePatient("Joe", 2);

        // Then the new patient should be the first in the Queue
        String patientName = fifoCommunityCenter.getNurseQueue().getFirst().getName();
        assertEquals("Joe", patientName);
        assertEquals(1, fifoCommunityCenter.getNurseQueue().size());
    }

    @Test
    public void shouldHave2PatientInTheNurseQueue() {
        // Given 1 patient in the nurse Queue in the community center
        // using a fifo sorting algorithm
        fifoCommunityCenter.triagePatient("Joe", 2);

        // When sorting a new patient
        fifoCommunityCenter.triagePatient("Bob", 3);

        // Then the new patient should be the last one in the Queue
        String lastPatientName = fifoCommunityCenter.getNurseQueue().getLast().getName();
        assertEquals("Bob", lastPatientName);
        assertEquals(2, fifoCommunityCenter.getNurseQueue().size());
    }

    @Test
    public void shouldHave2PatientInTheNurseQueueWithNewPatientInFirstPosition() {
        // Given 1 patient with a gravity 2 in the community center
        // using a gravity sorting algorithm
        gravityCommunityCenter.triagePatient("Joe", 2);

        // When sorting a new patient with a gravity of 7
        gravityCommunityCenter.triagePatient("Bob", 7);

        // Then the new patient should be in the first position in the Queue
        String firstPatientName = gravityCommunityCenter.getNurseQueue().getFirst().getName();
        assertEquals("Bob", firstPatientName);
    }

    @Test
    public void shouldHave3PatientsInNurseQueueWithNewPatientIn2Position() {
        // Given 1 patient with gravity 2 and another patient with gravity 6
        // in the community center using a gravity sorting algorithm
        gravityCommunityCenter.triagePatient("Joe", 2);
        gravityCommunityCenter.triagePatient("Bob", 7);

        // When sorting a new patient with a gravity of 5
        gravityCommunityCenter.triagePatient("Real", 5);

        // Then the new patient should be in second position in the Queue
        String newPatientName = gravityCommunityCenter.getNurseQueue().get(1).getName();
        String lastPatientName = gravityCommunityCenter.getNurseQueue().getLast().getName();
        assertEquals("Real", newPatientName);
        assertEquals("Joe", lastPatientName);
    }

    @Test
    public void shouldNotAddAnyPatientToFifoNurseQueue() {
        // Given an empty nurse Queue in a community center using fifo
        // as the sorting algorithm
        // When sorting a new patient with a gravity of 1
        fifoCommunityCenter.triagePatient("Joe", 1);

        // Then no new patient should be added to the nurse Queue
        int numberOfPatientInQueue = fifoCommunityCenter.getNurseQueue().size();
        assertEquals(0, numberOfPatientInQueue);
    }

    @Test
    public void shouldNotAddAnyPatientToGravityNurseQueue() {
        // Given an empty nurse Queue in a community center using
        // gravity sorting algorithm
        // When sorting a new patient with a gravity of 1
        gravityCommunityCenter.triagePatient("Joe", 1);

        // Then no new patient shoud be added to the nurse Queue
        int numberOfPatientsInQueue = gravityCommunityCenter.getNurseQueue().size();
        assertEquals(0, numberOfPatientsInQueue);
    }

    @Test
    public void shouldAddNewPatientAtTheEndOfNurseQueue() {
        // Given 1 patient with gravity 3 another patient with
        // gravity 7 in a community center using gravity sorting
        // algorithm
        gravityCommunityCenter.triagePatient("Joe", 3);
        gravityCommunityCenter.triagePatient("Bob", 7);

        // When sorting a new patient with gravity 4
        gravityCommunityCenter.triagePatient("Real", 4);

        // Then new patient should be at the end of the nurse Queue
        String newPatientName = gravityCommunityCenter.getNurseQueue().getLast().getName();
        assertEquals("Real", newPatientName);
    }
}