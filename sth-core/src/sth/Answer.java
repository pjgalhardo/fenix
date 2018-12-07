package sth;

import java.io.Serializable;

public class Answer implements Serializable {

    private String _comment;
    private int _hours;
    private static final long serialVersionUID = 201810051538L;

    public Answer(int hours, String comment) {
        _hours = hours;
        _comment = comment;
    }

    public int getHours() {
        return _hours;
    }
}