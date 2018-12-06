package sth;

public class ClosedProject extends ProjectState {

    public ClosedProject(Project project) {
        super(project, true);
    }

    public void close() {

    }

    public boolean isOpen() {
        return false;
    }
}