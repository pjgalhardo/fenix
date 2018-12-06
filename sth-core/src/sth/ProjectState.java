package sth;

public abstract class ProjectState {
    private Project _project;
    private boolean _closed;

    public ProjectState(Project project, boolean closed) {
        _project = project;
        _closed = closed;
    }

    public abstract void close();

    public abstract boolean isOpen();

    public Project getProject() {
        return _project;
    }
}