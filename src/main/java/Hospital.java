import java.util.LinkedList;

public abstract class Hospital {
    protected int getIndexOfPatientPositionInGravityTriage(Patient patient, LinkedList<Patient> patientFile) {
        int patientGravity = patient.getGravity();
        for (Patient patientAlreadyInFile : patientFile) {
            if (patientGravity > patientAlreadyInFile.getGravity()) {
                return patientFile.indexOf(patientAlreadyInFile);
            }
        }
        return -1;
    }
}
