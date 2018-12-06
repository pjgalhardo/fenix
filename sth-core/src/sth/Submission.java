package sth;

import java.io.Serializable;

public class Submission implements Serializable {

    private String _name;
    private static final long serialVersionUID = 201810051538L;

    public Submission(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }
}