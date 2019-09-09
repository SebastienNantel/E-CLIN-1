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
    public void shouldHaveOnePatientInNurseFile() {
        // Given 1 a community center using fifo sorting
        // When sorting a new patient
        fifoCommunityCenter.triagePatient("Joe", 2);

        // Then the new patient should be the first in the file
        String patientName = fifoCommunityCenter.getNurseFile().getFirst().getName();
        assertEquals("Joe", patientName);
        assertEquals(1, fifoCommunityCenter.getNurseFile().size());
    }

    @Test
    public void shouldHave2PatientInTheNurseFile() {
        // Given 1 patient in the nurse file in the community center
        // using a fifo sorting algorithm
        fifoCommunityCenter.triagePatient("Joe", 2);

        // When sorting a new patient
        fifoCommunityCenter.triagePatient("Bob", 3);

        // Then the new patient should be the last one in the file
        String lastPatientName = fifoCommunityCenter.getNurseFile().getLast().getName();
        assertEquals("Bob", lastPatientName);
        assertEquals(2, fifoCommunityCenter.getNurseFile().size());
    }

    @Test
    public void shouldHave2PatientInTheNurseFileWithNewPAtientInFirstPosition() {
        // Given 1 patient with a gravity 2 in the community center
        // using a gravity sorting algorithm
        gravityCommunityCenter.triagePatient("Joe", 2);

        // When sorting a new patient with a gravity of 7
        gravityCommunityCenter.triagePatient("Bob", 7);

        // Then the new patient should be in the first position in the file
        String firstPatientName = gravityCommunityCenter.getNurseFile().getFirst().getName();
        assertEquals("Bob", firstPatientName);
    }

    @Test
    public void shouldHave3PatientsInNurseFileWithNewPatientIn2Position() {
        // Given 1 patient with gravity 2 and another patient with gravity 6
        // in the community center using a gravity sorting algorithm
        gravityCommunityCenter.triagePatient("Joe", 2);
        gravityCommunityCenter.triagePatient("Bob", 7);

        // When sorting a new patient with a gravity of 5
        gravityCommunityCenter.triagePatient("Real", 5);

        // Then the new patient should be in second position in the file
        String newPatientName = gravityCommunityCenter.getNurseFile().get(1).getName();
        String lastPatientName = gravityCommunityCenter.getNurseFile().getLast().getName();
        assertEquals("Real", newPatientName);
        assertEquals("Joe", lastPatientName);
    }

}