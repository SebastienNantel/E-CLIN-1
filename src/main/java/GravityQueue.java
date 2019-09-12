import java.util.LinkedList;

public class GravityQueue implements IQueue{
    private LinkedList<Patient> patients = new LinkedList<Patient>();

    public void addPatient(Patient patient) {
        if (patient.getGravity() > 1) {
            if (patient.getGravity() > 5) {
                int indexToAddPatient = getIndexOfPatientPositionInGravityTriage(patient);
                if (indexToAddPatient != -1) {
                    patients.add(indexToAddPatient, patient);
                } else {
                    patients.add(patient);
                }
            } else {
                patients.add(patient);
            }
        }
    }

    public Patient getNextPatient() {
       Patient patient = patients.getFirst();
       patients.removeFirst();
       return patient;
    }

    public boolean isEmpty() {
        return patients.isEmpty();
    }

    private int getIndexOfPatientPositionInGravityTriage(Patient patient) {
        int patientGravity = patient.getGravity();
        for (Patient patientAlreadyInQueue : patients) {
            if (patientGravity > patientAlreadyInQueue.getGravity()) {
                return patients.indexOf(patientAlreadyInQueue);
            }
        }
        return -1;
    }
}
