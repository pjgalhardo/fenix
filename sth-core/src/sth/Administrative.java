package sth;

import sth.SchoolManager;
import sth.Person;

public class Administrative extends Person {
    private static final long serialVersionUID = 201810051538L;

    public Administrative(int id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "FUNCIONÁRIO|" + super.toString();
    }
}