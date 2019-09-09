import java.util.LinkedList;

public class Clinic extends Hospital{

    private LinkedList<Patient> doctorFile = new LinkedList<Patient>();
    private LinkedList<Patient> radiologyFile = new LinkedList<Patient>();
    private TriageType doctorTriageType;
    private TriageType radiologyTriageType;

    public Clinic(TriageType triageType){
        this.doctorTriageType = triageType;
        this.radiologyTriageType = triageType;
    }

    public Clinic(TriageType doctorFileTriageType, TriageType radiologyTriageType) {
        this.doctorTriageType = doctorFileTriageType;
        this.radiologyTriageType = radiologyTriageType;
    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
        Patient patient = new Patient(name, gravity, visibleSymptom);
        if (doctorTriageType == TriageType.FIFO) {
            doctorFileTriageWithFifo(patient);
        } else {
            doctorFileTriageWithGravity(patient);
        }
        if (isGoingInRadiology(visibleSymptom)) {
            if (radiologyTriageType == TriageType.FIFO) {
                radiologyFileTriageWithFifo(patient);
            } else {
                radiologyFileTriageWithGravity(patient);
            }
        }
    }

    private void doctorFileTriageWithFifo(Patient patient) {
        doctorFile.add(patient);
    }

    private void doctorFileTriageWithGravity(Patient patient) {
        int indexToAddPatient =  getIndexOfPatientPositionInGravityTriage(patient, doctorFile);
        if (indexToAddPatient != -1) {
            doctorFile.add(indexToAddPatient, patient);
        } else {
            doctorFile.add(patient);
        }
    }

    private void radiologyFileTriageWithGravity(Patient patient) {
        int indexToAddToNewPatient = getIndexOfPatientPositionInGravityTriage(patient, radiologyFile);
        if (indexToAddToNewPatient != -1) {
            radiologyFile.add(indexToAddToNewPatient, patient);
        } else {
            radiologyFile.add(patient);
        }
    }

    private void radiologyFileTriageWithFifo(Patient patient) {
            radiologyFile.add(patient);
    }

    private boolean isGoingInRadiology(VisibleSymptom visibleSymptom) {
        return visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN;
    }

    public LinkedList<Patient> getDoctorFile() {
        return doctorFile;
    }

    public LinkedList<Patient> getRadiologyFile() {
        return radiologyFile;
    }
}
