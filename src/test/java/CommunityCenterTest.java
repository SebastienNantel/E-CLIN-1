import org.junit.Test;

import static org.junit.Assert.*;

public class CommunityCenterTest {
    @Test
    public void whenTriagingPatient_thenPatientIsAddedToNurseWaitingList() {
        IQueue nurseQueue = new FifoQueue();
        Department cslsc = new Clsc(nurseQueue);
        CommunityCenter communityCenter = new CommunityCenter(cslsc);
        Patient patient = new Patient("Bob", 2, VisibleSymptom.FLU);
        communityCenter.triagePatient(patient);
        assertEquals(patient, nurseQueue.getNextPatient());
    }
}