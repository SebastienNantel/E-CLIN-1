import java.util.LinkedList;

public interface IQueue {
    public void addPatient(Patient patient);

    public Patient getNextPatient();

    public boolean isEmpty();
}
