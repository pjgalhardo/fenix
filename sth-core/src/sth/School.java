package sth;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;
import sth.exceptions.BadEntryException;
import sth.exceptions.NoSuchPersonException;
import sth.exceptions.ImportFileException;
import java.io.FileNotFoundException;
import sth.Parser;

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

  ArrayList<String> showAllPersons() {
    ArrayList<String> persons = new ArrayList<String>();
    for (Map.Entry<Integer, Person> entry : _users.entrySet()) {
      String person = entry.getValue().toString();
      persons.add(person);
    }
    return persons;
  }

  ArrayList<String> searchPerson(String name) {
    Map<String, Person> persons = new TreeMap<String, Person>();
    ArrayList<String> strings = new ArrayList<String>();
    for (Map.Entry<Integer, Person> entry : _users.entrySet()) {
      Person person = entry.getValue();
      if (person.getName().contains(name)) {
        persons.put(person.getName(), person);
      }
    }
    for (Map.Entry<String, Person> entry : persons.entrySet()) {
      strings.add(entry.getValue().getName());
    }
    persons.clear();
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
