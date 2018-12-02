package sth;

import java.util.Map;
import java.util.TreeMap;
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
  private Map<String, Course> _courses;
  private Parser _parser;

  public School() {
    _users = new TreeMap<Integer, Person>();
    _courses = new TreeMap<String, Course>();
  }

  public Person getPerson(int id) {
    return _users.get(id);
  }

  public Course getCourse(String courseName) {
    return _courses.get(courseName);
  }

  void addPerson(Person person) throws NoSuchPersonException {
    if (getPerson(person.getId()) == null) {
      _users.put(person.getId(), person);
    }
  }

  void removePerson(Person person) throws NoSuchPersonException {
    if (getPerson(person.getId()) != null) {
      _users.remove(person.getId(), person);
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

  void showAllPersons() {
    for (Map.Entry<Integer, Person> entry : _users.entrySet()) {
      Person person = entry.getValue();
      System.out.println(person);
    }
  }

  void searchPerson(String name) {
    Map<String, Person> persons = new TreeMap<String, Person>();
    for (Map.Entry<Integer, Person> entry : _users.entrySet()) {
      Person person = entry.getValue();
      if (person.getName().contains(name)) {
        persons.put(person.getName(), person);
      }
    }
    for (Map.Entry<String, Person> entry : persons.entrySet()) {
      Person person = entry.getValue();
      System.out.println(person);
    }
    persons.clear();
  }

  /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */

  void importFile(String filename) throws BadEntryException, IOException {
    _parser = new Parser(this);
    _parser.readFile(filename);
    _parser = null;
  }

}
