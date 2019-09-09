import java.util.LinkedList;

public class Clinic {

    private LinkedList<Patient> doctorFile = new LinkedList<Patient>();
    private LinkedList<Patient> radiologyFile = new LinkedList<Patient>();
    private TriageType triageType;

    public Clinic(TriageType triageType){
        this.triageType = triageType;
    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
        Patient patient = new Patient(name, gravity, visibleSymptom);
        if (triageType == TriageType.FIFO) {
            doctorFileTriageWithFifo(patient);
            radiologyFileTriageWithFifo(patient);
        } else {
            doctorFileTriageWithGravity(patient);
            radiologyFileTriageWithFifo(patient);
        }
    }

    private void doctorFileTriageWithFifo(Patient patient) {
        doctorFile.add(patient);
    }

    private void doctorFileTriageWithGravity(Patient patient) {
        int patientGravity = patient.getGravity();
        int indexToAddPatient = 0;
        if (doctorFile.size() > 0) {
            for (Patient patientAlreadyInFile : doctorFile) {
                if (patientGravity > patientAlreadyInFile.getGravity()) {
                    indexToAddPatient = doctorFile.indexOf(patientAlreadyInFile);
                    break;
                }
            }
            doctorFile.add(indexToAddPatient, patient);
        } else {
            doctorFile.add(patient);
        }
    }

    private void radiologyFileTriageWithFifo(Patient patient) {
        VisibleSymptom patientVisibleSymptom = patient.getVisibleSymptom();
        if (patientVisibleSymptom == VisibleSymptom.BROKEN_BONE || patientVisibleSymptom == VisibleSymptom.SPRAIN) {
            radiologyFile.add(patient);
        }
    }

    public LinkedList<Patient> getDoctorFile() {
        return doctorFile;
    }

    public LinkedList<Patient> getRadiologyFile() {
        return radiologyFile;
    }
}
