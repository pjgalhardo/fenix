package sth;

import java.util.Map;
import java.util.TreeMap;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.Person;
import sth.Discipline;

public class Teacher extends Person {
    private static final long serialVersionUID = 201810051538L;
    Map<String, Discipline> _disciplines;

    public Teacher(int id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
        _disciplines = new TreeMap<String, Discipline>();
    }

    void addDiscipline(Discipline discipline) {
        if (_disciplines.get(discipline.getName()) == null) {
            _disciplines.put(discipline.getName(), discipline);
        }
    }

    Discipline getDiscipline(String name) {
        return _disciplines.get(name);
    }

    private String printDisciplines() {
        String result = "";
        for (Map.Entry<String, Discipline> entry : _disciplines.entrySet()) {
            Discipline discipline = entry.getValue();
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