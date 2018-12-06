package sth;

public class OpenProject extends ProjectState {

    public OpenProject(Project project) {
        super(project, false);
    }

    public void close() {
        getProject().setState(new ClosedProject(getProject()));
    }

    public boolean isOpen() {
        return true;
    }

}