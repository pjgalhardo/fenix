package sth;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;
import sth.exceptions.BadEntryException;
import sth.exceptions.NoSuchPersonException;
import java.text.RuleBasedCollator;
import sth.exceptions.ImportFileException;
import java.io.FileNotFoundException;
import sth.Parser;

import java.text.ParseException;

/**
 * School implementation.
 */
public class School implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;
  // private int _nextid = 100000;

  private Map<Integer, Person> _users;
  private Map<Integer, Student> _students;
  private Map<Integer, Administrative> _administratives;
  private Map<Integer, Teacher> _teachers;
  private Map<String, Course> _courses;

  public School() {
    _students = new TreeMap<Integer, Student>();
    _teachers = new TreeMap<Integer, Teacher>();
    _administratives = new TreeMap<Integer, Administrative>();
    _courses = new TreeMap<String, Course>();
    _users = new TreeMap<Integer, Person>();
  }

  public Person getPerson(int id) {
    return _users.get(id);
  }

  public Student getStudent(int id) {
    return _students.get(id);
  }

  public Teacher getTeacher(int id) {
    return _teachers.get(id);
  }

  public Administrative getAdministrative(int id) {
    return _administratives.get(id);
  }

  public Course getCourse(String courseName) {
    return _courses.get(courseName);
  }

  void addPerson(Person person, String category) {
    boolean success = false;

    if (person != null) {
      if (category.equals("DOCENTE")) {
        Teacher teacher = (Teacher) person;
        if (getTeacher(teacher.getId()) == null) {
          _teachers.put(teacher.getId(), teacher);
          success = true;
        }
      } else if (category.equals("ALUNO") || category.equals("DELEGADO")) {
        Student student = (Student) person;
        if (getStudent(student.getId()) == null) {
          _students.put(student.getId(), student);
          success = true;
        }
      } else if (category.equals("FUNCIONÁRIO")) {
        Administrative administrative = (Administrative) person;
        if (getAdministrative(administrative.getId()) == null) {
          _administratives.put(administrative.getId(), administrative);
          success = true;
        }
      }
      if (success) {
        _users.put(person.getId(), person);
      }
    }
  }

  void addCourse(Course course) {
    if (getCourse(course.getName()) == null) {
      _courses.put(course.getName(), course);
    }
  }

  /*
   * int getNextId() { return _nextid; }
   */

  String showAllPersons() {
    String persons = "";
    for (Map.Entry<Integer, Person> entry : _users.entrySet()) {
      String person = entry.getValue().toString();
      if (persons.equals("")) {
        persons += person;
      } else {
        persons += ("\n" + person);
      }
    }
    return persons;
  }

  String searchPerson(String name) {
    String strings = "";
    try {
      String portugueseRules = "< a,A < á,Á < â, Â < ã, Ã < b,B < c,C < ç,Ç < d,D < e,E < é,É < ê,Ê < f,F < g,G < h,H < i,I < í,Í < j,J < k,K < l,L < m,M < n,N < o,O < ó,Ó < õ,Õ < p,P < q,Q < r,R < s,S < t,T < u,U < ú,Ú < v,V < w,W < x,X < y,Y < z,Z";    
      RuleBasedCollator collator = new RuleBasedCollator(portugueseRules);
      Map<String, Person> persons = new TreeMap<String, Person>(collator);

      for (Map.Entry<Integer, Person> entry : _users.entrySet()) {
        Person person = entry.getValue();
        if (person.getName().contains(name)) {
          persons.put(person.getName(), person);
        }
      }
      for (Map.Entry<String, Person> entry : persons.entrySet()) {
        if (strings.equals("")) {
          strings += entry.getValue().toString();
        } else {
          strings += ("\n" + entry.getValue().toString());
        }
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strings;
  }

  /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */

  void importFile(String filename) throws BadEntryException, IOException {
    Parser _parser = new Parser(this);
    _parser.readFile(filename);
  }

  boolean isaAdministrative(Person person) {
    for (Map.Entry<Integer, Administrative> entry : _administratives.entrySet()) {
      if (person.equals(entry.getValue())) {
        return true;
      }
    }
    return false;
  }

  boolean isaTeacher(Person person) {
    for (Map.Entry<Integer, Teacher> entry : _teachers.entrySet()) {
      if (person.equals(entry.getValue())) {
        return true;
      }
    }
    return false;
  }

  boolean isaRepresentative(Person person) {
    for (Map.Entry<Integer, Student> entry : _students.entrySet()) {
      if (person.equals(entry.getValue())) {
        Student student = (Student) person;
        return student.isRepresentative();
      }
    }
    return false;
  }

  boolean isaStudent(Person person) {
    for (Map.Entry<Integer, Student> entry : _students.entrySet()) {
      if (person.equals(entry.getValue())) {
        return true;
      }
    }
    return false;
  }
}
