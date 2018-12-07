package sth;

import java.io.Serializable;
import sth.Discipline;
import sth.Student;
import sth.exceptions.NoSuchPersonException;
import java.util.Map;
import java.util.TreeMap;

public class Course implements Serializable {
    private static final long serialVersionUID = 201810051538L;
    private String _name;
    private Map<String, Discipline> _disciplines;
    private Map<Integer, Student> _students;
    private Map<Integer, Student> _representatives;

    public Course(String name) {
        _name = name;
        _students = new TreeMap<Integer, Student>();
        _disciplines = new TreeMap<String, Discipline>();
        _representatives = new TreeMap<Integer, Student>();
    }

    public String getName() {
        return _name;
    }

    void addDiscipline(Discipline discipline) {
        if (_disciplines.get(discipline.getName()) == null) {
            _disciplines.put(discipline.getName(), discipline);
        }
    }

    Discipline getDiscipline(String name) {
        return _disciplines.get(name);
    }

    void addStudent(Student student) {
        if (_students.get(student.getId()) == null) {
            _students.put(student.getId(), student);
        }

    }

    void addRepresentative(Student student) {
        if (_representatives.get(student.getId()) == null) {
            if (_representatives.size() < 7) {
                _representatives.put(student.getId(), student);
            }
        }
    }

    Student getRepresentative(Student student) {
        return _representatives.get(student.getId());
    }

    void removeRepresentative(Student student) throws NoSuchPersonException {
        if (_representatives.get(student.getId()) != null) {
            _representatives.remove(student.getId());
        } else {
            throw new NoSuchPersonException(student.getId());
        }

    }

}