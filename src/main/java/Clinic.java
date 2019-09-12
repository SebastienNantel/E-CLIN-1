import java.util.List;

public class Clinic {
    private List<Department> departments;

    public Clinic(List<Department> departments){
        this.departments = departments;
    }

    public void triagePatient(Patient patient) {
        for (Department department : departments) {
            department.triage(patient);
        }
    }
}
