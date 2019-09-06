public class Main {

    public static void main(String args[]) {
        TriageType doctorTriageType = TriageType.FIFO;
        TriageType radiologyTriageType = TriageType.FIFO;

        Clinic clinic = new Clinic();
        clinic.triagePatient("John", 4, VisibleSymptom.MIGRAINE);
    }
}
