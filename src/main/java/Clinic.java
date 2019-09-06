import java.util.LinkedList;

public class Clinic {

    private LinkedList<String> doctorFile = new LinkedList<String>();
    private LinkedList<String> radiologyFile = new LinkedList<String>();

    public Clinic(){

    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
        doctorFile.add(name);
        if (visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN) {
            radiologyFile.add(name);
        }
    }

    public LinkedList<String> getDoctorFile() {
        return doctorFile;
    }

    public LinkedList<String> getRadiologyFile() {
        return radiologyFile;
    }
}
