import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ClinicTest {
    private Clinic clinic;
    private IQueue doctorFifoQueue;
    private IQueue radiologyFifoQueue;

    @Before
    public void initialiseClinic() {
        radiologyFifoQueue = new FifoQueue();
        doctorFifoQueue = new FifoQueue();
        Doctor doctor = new Doctor(doctorFifoQueue);
        Radiology radiology = new Radiology(radiologyFifoQueue);
        ArrayList<Department> departments = new ArrayList<Department>();
        departments.add(doctor);
        departments.add(radiology);
        clinic = new Clinic(new ArrayList<Department>(departments));
    }

   @Test
   public void whenTriagingPatient_thenPatientIsAddedToTheDoctorWaitingList() {
       Patient patient = new Patient("Bob", 2, VisibleSymptom.FLU);
       clinic.triagePatient(patient);
       Patient returnedPatient = doctorFifoQueue.getNextPatient();
       assertEquals(patient, returnedPatient);
   }

   @Test
    public void whenTriagingPatientWithTheFlu_thenPatientIsNotAddedToTheRadiologyWaitingList() {
      Patient patient = new Patient("Bob", 2, VisibleSymptom.FLU);
       clinic.triagePatient(patient);
       assertTrue(radiologyFifoQueue.isEmpty());
   }

   @Test
    public void whenTriagingPatientWithBrokenBone_ThenPatientIsAddedToRadiologyWaintingList() {
      Patient patient = new Patient("Bob", 2, VisibleSymptom.BROKEN_BONE);
       clinic.triagePatient(patient);
       assertEquals(patient, radiologyFifoQueue.getNextPatient());
   }

   @Test
    public void whenTriagingPatientWithSprain_thenPatientIsAddedToRadiologyWaitingList() {
      Patient patient = new Patient("Bob", 2, VisibleSymptom.SPRAIN);
       clinic.triagePatient(patient);
       assertEquals(patient, radiologyFifoQueue.getNextPatient());
   }
}
