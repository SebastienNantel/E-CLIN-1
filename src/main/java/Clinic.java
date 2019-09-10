import java.util.LinkedList;

public class Clinic extends Hospital{

    private LinkedList<Patient> doctorQueue = new LinkedList<Patient>();
    private LinkedList<Patient> radiologyQueue = new LinkedList<Patient>();
    private TriageType doctorTriageType;
    private TriageType radiologyTriageType;

    public Clinic(TriageType triageType){
        this.doctorTriageType = triageType;
        this.radiologyTriageType = triageType;
    }

    public Clinic(TriageType doctorQueueTriageType, TriageType radiologyTriageType) {
        this.doctorTriageType = doctorQueueTriageType;
        this.radiologyTriageType = radiologyTriageType;
    }

    public void triagePatient(String name, int gravity, VisibleSymptom visibleSymptom) {
        Patient patient = new Patient(name, gravity, visibleSymptom);
        if (gravity > 1) {
            if (doctorTriageType == TriageType.FIFO || gravity < 5) {
                doctorQueueTriageWithFifo(patient);
            } else {
                doctorQueueTriageWithGravity(patient);
            }
            if (isGoingInRadiology(visibleSymptom)) {
                if (radiologyTriageType == TriageType.FIFO || gravity < 5) {
                    radiologyQueueTriageWithFifo(patient);
                } else {
                    radiologyQueueTriageWithGravity(patient);
                }
            }
        }
    }

    private void doctorQueueTriageWithFifo(Patient patient) {
        doctorQueue.add(patient);
    }

    private void doctorQueueTriageWithGravity(Patient patient) {
        int indexToAddPatient =  getIndexOfPatientPositionInGravityTriage(patient, doctorQueue);
        if (indexToAddPatient != -1) {
            doctorQueue.add(indexToAddPatient, patient);
        } else {
            doctorQueue.add(patient);
        }
    }

    private void radiologyQueueTriageWithGravity(Patient patient) {
        int indexToAddToNewPatient = getIndexOfPatientPositionInGravityTriage(patient, radiologyQueue);
        if (indexToAddToNewPatient != -1) {
            radiologyQueue.add(indexToAddToNewPatient, patient);
        } else {
            radiologyQueue.add(patient);
        }
    }

    private void radiologyQueueTriageWithFifo(Patient patient) {
            radiologyQueue.add(patient);
    }

    private boolean isGoingInRadiology(VisibleSymptom visibleSymptom) {
        return visibleSymptom == VisibleSymptom.BROKEN_BONE || visibleSymptom == VisibleSymptom.SPRAIN;
    }

    public LinkedList<Patient> getDoctorQueue() {
        return doctorQueue;
    }

    public LinkedList<Patient> getRadiologyQueue() {
        return radiologyQueue;
    }
}
