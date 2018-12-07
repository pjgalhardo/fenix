package sth;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import sth.SchoolManager;
import sth.Person;
import sth.Discipline;

public class Teacher extends Person {
    private static final long serialVersionUID = 201810051538L;
    ArrayList<Discipline> _disciplines;

    public Teacher(int id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
        _disciplines = new ArrayList<Discipline>();
    }

    boolean hasCourse(Course course) {
        for (int i = 0; i < _disciplines.size(); i++) {
            Course aux = _disciplines.get(i).getCourse();
            if (aux.equals(course)) {
                return true;
            }
        }
        return false;
    }

    void addDiscipline(Discipline discipline) {
        if (hasCourse(discipline.getCourse())) {
            Map<String, Discipline> aux = new TreeMap<String, Discipline>();
            int first = -1;
            for (int i = 0; i < _disciplines.size(); i++) {
                Discipline disc_aux = _disciplines.get(i);
                Course course = disc_aux.getCourse();
                if (course.equals(discipline.getCourse())) {
                    if (first == -1) {
                        first = i;
                    }
                    aux.put(disc_aux.getName(), disc_aux);
                    _disciplines.remove(i);
                }
            }
            if (first != -1) {
                for (Map.Entry<String, Discipline> entry : aux.entrySet()) {
                    _disciplines.add(first, entry.getValue());
                    first++;
                }
            }
        } else {
            _disciplines.add(discipline);
        }
    }

    Discipline getDiscipline(String name) {
        for (int i = 0; i < _disciplines.size(); i++) {
            Discipline discipline = _disciplines.get(i);
            if (discipline.getName().equals(name)) {
                return discipline;
            }
        }
        return null;

    }

    private String printDisciplines() {
        String result = "";
        for (int i = 0; i < _disciplines.size(); i++) {
            Discipline discipline = _disciplines.get(i);
            result += "\n* " + discipline.getCourse().getName() + " - " + discipline.getName();
        }
        return result;
    }

    void doShowAllDisciplineStudents(String discipline) {
        Discipline _discipline = getDiscipline(discipline);
        if (_discipline != null) {
            _discipline.showAllStudents();
        }
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "DOCENTE|" + super.toString() + printDisciplines();
    }
}