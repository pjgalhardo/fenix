package sth;

public class OpenProject extends ProjectState {

    private static final long serialVersionUID = 201810051538L;

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