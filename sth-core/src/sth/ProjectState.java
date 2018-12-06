package sth;

import java.io.Serializable;

public abstract class ProjectState implements Serializable {
    private Project _project;
    private boolean _closed;
    private static final long serialVersionUID = 201810051538L;

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