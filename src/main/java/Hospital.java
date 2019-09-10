import java.util.LinkedList;

public abstract class Hospital {
    protected int getIndexOfPatientPositionInGravityTriage(Patient patient, LinkedList<Patient> patientQueue) {
        int patientGravity = patient.getGravity();
        for (Patient patientAlreadyInQueue : patientQueue) {
            if (patientGravity > patientAlreadyInQueue.getGravity()) {
                return patientQueue.indexOf(patientAlreadyInQueue);
            }
        }
        return -1;
    }
}
