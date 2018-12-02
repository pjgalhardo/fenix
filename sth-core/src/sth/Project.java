package sth;

import java.io.Serializable;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Student;
import sth.Course;

public class Project implements Serializable {
    private static final long serialVersionUID = 201810051538L;
    private String _name;
    // private String _description;
    private boolean _closed;

    Project(String name) {
        _name = name;
        // _description = description;
        _closed = false;
    }

    public String getName() {
        return _name;
    }

    public boolean getStatus() {
        return _closed;
    }

    void close() {
        _closed = true;
    }
}