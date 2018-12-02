package sth;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import java.io.Serializable;

public abstract class Person implements Serializable {
    private int _id;
    private String _name;
    private int _phoneNumber;
    private static final long serialVersionUID = 201810051538L;

    public Person(int id, int phoneNumber, String name) {
        _id = id;
        _name = name;
        _phoneNumber = phoneNumber;
    }

    public String getName() {
        return _name;
    }

    public int getId() {
        return _id;
    }

    public int getPhoneNumber() {
        return _phoneNumber;
    }

    public void changePhoneNumber(int phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return _id + "|" + _phoneNumber + "|" + _name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person person = (Person) o;
            return _name.equals(person.getName()) && _id == person.getId() && _phoneNumber == person.getPhoneNumber();
        }
        return false;
    }

}