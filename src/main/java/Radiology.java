public class Radiology implements Department {
    private IQueue queue;

    public Radiology(IQueue queue) {
        this.queue = queue;
    }

    public void triage(Patient patient) {
        if (patient.isGoingInRadiology()) {
            queue.addPatient(patient);
        }
    }
}
