public class Clsc implements Department {

    private final IQueue nurseQueue;

    public Clsc(IQueue nurseQueue) {
        this.nurseQueue = nurseQueue;
    }

    public void triage(Patient patient) {
        nurseQueue.addPatient(patient);
    }
}
