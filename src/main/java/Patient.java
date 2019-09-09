public class Patient {
    private String name;
    private int gravity;
    private VisibleSymptom visibleSymptom;

    public Patient(String name, int gravity, VisibleSymptom visibleSymptom) {
        this.name = name;
        this.gravity = gravity;
        this.visibleSymptom = visibleSymptom;
    }

    public int getGravity() {
        return gravity;
    }

    public String getName() {
        return name;
    }

    public VisibleSymptom getVisibleSymptom() {
        return visibleSymptom;
    }
}
