package sth;

public class ClosedProject extends ProjectState {

    private static final long serialVersionUID = 201810051538L;

    public ClosedProject(Project project) {
        super(project, true);
    }

    public void close() {

    }

    public boolean isOpen() {
        return false;
    }
}