import java.util.LinkedList;

public class CommunityCenter extends Hospital{
    private TriageType triageType;
    private LinkedList<Patient> nurseQueue = new LinkedList<Patient>();

    public CommunityCenter(TriageType triageType) {
        this.triageType = triageType;
    }

    public void triagePatient(String name, int gravity) {
        Patient patient = new Patient(name, gravity, null);
        if (gravity > 1) {
            if (triageType == TriageType.FIFO || gravity < 5) {
                fifoTriage(patient);
            } else {
                gravityTriage(patient);
            }
        }
    }

    private void gravityTriage(Patient patient) {
        int indexToAddPatient = getIndexOfPatientPositionInGravityTriage(patient, nurseQueue);
        if (indexToAddPatient != -1) {
            nurseQueue.add(indexToAddPatient, patient);
        } else {
            nurseQueue.add(patient);
        }
    }

    private void fifoTriage(Patient patient) {
        nurseQueue.add(patient);
    }

    public LinkedList<Patient> getNurseQueue() {
        return nurseQueue;
    }
}
