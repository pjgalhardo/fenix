package sth;

import sth.SchoolManager;
import sth.exceptions.NoSuchPersonException;
import sth.Person;
import sth.Course;
import java.util.Map;
import java.util.TreeMap;
import java.util.Locale;
import java.util.Collections;
import java.text.RuleBasedCollator;
import java.util.Comparator;
import java.text.ParseException;

public class Student extends Person {
    private static final long serialVersionUID = 201810051538L;
    Course _course;
    Map<String, Discipline> _disciplines;

    public Student(int id, int phoneNumber, String name) {
        super(id, phoneNumber, name);
        _disciplines = new TreeMap<String, Discipline>();

    }

    void becomeRepresentative() {
        _course.addRepresentative(this);
    }

    public boolean isRepresentative() {
        return _course.getRepresentative(this) != null;
    }

    void returnToStudent() {
        try {
            _course.removeRepresentative(this);
        } catch (NoSuchPersonException e) {
            System.out.println("No such person.");
        }
    }

    void addDiscipline(Discipline discipline) {
        if (discipline.getCourse() == _course) {
            if (_course.getDiscipline(discipline.getName()) != null) {
                if (_disciplines.get(discipline.getName()) == null) {
                    if (_disciplines.size() < 6) {
                        _disciplines.put(discipline.getName(), discipline);

                    }
                }
            }
        }

    }

    public Course getCourse() {
        return _course;
    }

    void setCourse(Course course) {
        _course = course;
    }

    public Discipline getDiscipline(String disciplineName) {
        return _disciplines.get(disciplineName);
    }

    private String printDisciplines() {
        String result = "";
        Map<String, Discipline> _sortedDisciplines;
        try {
            String portugueseRules = "< a,A < á,Á < â, Â < ã, Ã < b,B < c,C < ç,Ç < d,D < e,E < é,É < ê,Ê < f,F < g,G < h,H < i,I < í,Í < j,J < k,K < l,L < m,M < n,N < o,O < ó,Ó < õ,Õ < p,P < q,Q < r,R < s,S < t,T < u,U < ú,Ú < v,V < w,W < x,X < y,Y < z,Z";            RuleBasedCollator collator = new RuleBasedCollator(portugueseRules);
            _sortedDisciplines = new TreeMap<String, Discipline>(collator);

            _sortedDisciplines.putAll(_disciplines);
            if (_course != null) {
                for (Map.Entry<String, Discipline> entry : _sortedDisciplines.entrySet()) {
                    Discipline discipline = entry.getValue();
                    result += "\n* " + discipline.getCourse().getName() + " - " + discipline.getName();
                }
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        if (!isRepresentative()) {
            return "ALUNO|" + super.toString() + printDisciplines();
        } else {
            return "DELEGADO|" + super.toString() + printDisciplines();
        }
    }
}