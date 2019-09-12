public class CommunityCenter {
    private Department nurseDepartment;

    public CommunityCenter(Department nurseDepartment) {
        this.nurseDepartment = nurseDepartment;
    }

    public void triagePatient(Patient patient) {
        nurseDepartment.triage(patient);
    }
}
