package sth;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import sth.SchoolManager;
import sth.Person;
import sth.Discipline;
import java.util.Locale;
import java.util.Collections;
import java.text.RuleBasedCollator;
import java.util.Comparator;
import java.text.ParseException;

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
        try {
            String portugueseRules = "< a,A < á,Á < â, Â < ã, Ã < b,B < c,C < ç,Ç < d,D < e,E < é,É < ê,Ê < f,F < g,G < h,H < i,I < í,Í < j,J < k,K < l,L < m,M < n,N < o,O < ó,Ó < õ,Õ < p,P < q,Q < r,R < s,S < t,T < u,U < ú,Ú < v,V < w,W < x,X < y,Y < z,Z";
            RuleBasedCollator collator = new RuleBasedCollator(portugueseRules);

            _disciplines.add(discipline);

            Collections.sort(_disciplines, new Comparator<Discipline>() {
                @Override
                public int compare(Discipline d, Discipline d2) {
                    if (d.getCourse().equals(d2.getCourse())) {
                        return collator.compare(d.getName(), d2.getName());
                    } else {
                        return collator.compare(d.getCourse().getName(), d2.getCourse().getName());
                    }
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // if (hasCourse(discipline.getCourse())) {
        // Map<String, Discipline> aux = new TreeMap<String, Discipline>();
        // int first = -1;
        // for (int i = 0; i < _disciplines.size(); i++) {
        // Discipline disc_aux = _disciplines.get(i);
        // Course course = disc_aux.getCourse();
        // if (course.equals(discipline.getCourse())) {
        // if (first == -1) {
        // first = i;
        // }
        // aux.put(disc_aux.getName(), disc_aux);
        // _disciplines.remove(i);
        // i--;
        // }

        // }
        // aux.put(discipline.getName(), discipline);

        // for (Map.Entry<String, Discipline> entry : aux.entrySet()) {
        // _disciplines.add(first, entry.getValue());
        // first++;
        // }

        // } else {
        // _disciplines.add(discipline);
        // }
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

    String doShowAllDisciplineStudents(String discipline) {
        Discipline _discipline = getDiscipline(discipline);
        return _discipline.showAllStudents();
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "DOCENTE|" + super.toString() + printDisciplines();
    }
}