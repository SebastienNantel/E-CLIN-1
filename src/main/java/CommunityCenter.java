import java.util.LinkedList;

public class CommunityCenter extends Hospital{
    private TriageType triageType;
    private LinkedList<Patient> nurseFile = new LinkedList<Patient>();

    public CommunityCenter(TriageType triageType) {
        this.triageType = triageType;
    }

    public void triagePatient(String name, int gravity) {
        Patient patient = new Patient(name, gravity, null);
        if (gravity > 1) {
            if (triageType == TriageType.FIFO) {
                fifoTriage(patient);
            } else {
                gravityTriage(patient);
            }
        }
    }

    private void gravityTriage(Patient patient) {
        int indexToAddPatient = getIndexOfPatientPositionInGravityTriage(patient, nurseFile);
        if (indexToAddPatient != -1) {
            nurseFile.add(indexToAddPatient, patient);
        } else {
            nurseFile.add(patient);
        }
    }

    private void fifoTriage(Patient patient) {
        nurseFile.add(patient);
    }

    public LinkedList<Patient> getNurseFile() {
        return nurseFile;
    }
}
