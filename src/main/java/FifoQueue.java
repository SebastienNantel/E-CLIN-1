import java.util.LinkedList;

public class FifoQueue implements IQueue{
    private LinkedList<Patient> patients = new LinkedList<Patient>();

    public void addPatient(Patient patient) {
        if (patient.getGravity() > 1) {
            patients.add(patient);
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
}
