public class Doctor implements Department{
    private IQueue queue;

    public Doctor(IQueue queue) {
        this.queue = queue;
    }

    public void triage(Patient patient) {
        queue.addPatient(patient);
    }
}