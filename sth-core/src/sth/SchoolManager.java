package sth;

import java.io.IOException;
import java.io.FileNotFoundException;
import sth.exceptions.BadEntryException;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonException;

import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * The façade class.
 */
public class SchoolManager {

  private School _school;
  private Person _loggedUser;
  private String _file = "";

  public SchoolManager() {
  }

  void checkLogin() throws NoSuchPersonException {
    if (_loggedUser != null) {
      if (_school.getPerson(_loggedUser.getId()) == null) {
        throw new NoSuchPersonException(_loggedUser.getId());
      }
    }
  }

  /**
   * @param datafile
   * @throws ImportFileException
   * @throws InvalidCourseSelectionException
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _school = new School();
      _school.importFile(datafile);
    } catch (BadEntryException | IOException e) {
      throw new ImportFileException(e);
    }
    try {
      checkLogin();
    } catch (NoSuchPersonException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param id
   */
  public void login(int id) throws NoSuchPersonException {
    _loggedUser = _school.getPerson(id);
    if (_loggedUser == null) {
      throw new NoSuchPersonException(id);
    }
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    return (_loggedUser instanceof Administrative);
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean hasProfessor() {
    return (_loggedUser instanceof Teacher);
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean hasStudent() {
    return (_loggedUser instanceof Student);
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean hasRepresentative() {
    if (_loggedUser instanceof Student) {
      Student student = (Student) _loggedUser;
      return student.isRepresentative();
    }
    return false;
  }

  public School getSchool() {
    return _school;
  }

  public void setSchool(School school) {
    _school = school;
  }

  public void doChangePhoneNumber(int phoneNumber) {
    _loggedUser.changePhoneNumber(phoneNumber);
    try {
      _school.removePerson(_loggedUser);

      _school.addPerson(_loggedUser);
      doShowPerson();
    } catch (NoSuchPersonException e) {
      e.printStackTrace();
    }
  }

  public void doSearchPerson(String name) {
    _school.searchPerson(name);
  }

  public void doShowPerson() {
    System.out.println(_loggedUser);
  }

  public void doShowAllPersons() {
    _school.showAllPersons();
  }

  public void doShowDisciplineStudents(String discipline) {
    if (_loggedUser instanceof Teacher) {
      Teacher teacher = (Teacher) _loggedUser;
      teacher.doShowAllDisciplineStudents(discipline);
    }
  }

  public Discipline getDiscipline(String discipline) {
    if (_loggedUser instanceof Teacher) {
      Teacher teacher = (Teacher) _loggedUser;
      return teacher.getDiscipline(discipline);
    }
    return null;
  }

  public boolean fileExists() {
    return !(_file.equals(""));
  }

  public String getFile() {
    return _file;
  }

  public void writeFile() {
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_file)));
      oos.writeObject(_school);
      oos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeFile(String file) {
    _file = file;
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_file)));
      oos.writeObject(_school);
      oos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void openFile() throws NoSuchPersonException {
    try {
      ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_file)));
      _school = (School) ois.readObject();
      if (_school.getPerson(_loggedUser.getId()) == null) {
        throw new NoSuchPersonException(_loggedUser.getId());
      }
      _loggedUser = _school.getPerson(_loggedUser.getId());
      ois.close();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  public void openFile(String file) throws NoSuchPersonException {
    _file = file;
    try {
      ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_file)));
      _school = (School) ois.readObject();
      if (_school.getPerson(_loggedUser.getId()) == null) {
        throw new NoSuchPersonException(_loggedUser.getId());
      }
      _loggedUser = _school.getPerson(_loggedUser.getId());
      ois.close();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  public void doCreateProject(String discipline, String projectName) {
    if (_loggedUser instanceof Teacher) {
      Teacher teacher = (Teacher) _loggedUser;
      Discipline _discipline = teacher.getDiscipline(discipline);
      if (_discipline != null) {
        _discipline.createProject(projectName);
      }
    }
  }

  public void doCloseProject(String discipline, String projectName) {
    if (_loggedUser instanceof Teacher) {
      Teacher teacher = (Teacher) _loggedUser;
      Discipline _discipline = teacher.getDiscipline(discipline);
      if (_discipline != null) {
        Project _project = _discipline.getProject(projectName);
        if (_project != null) {
          _project.close();
        }
      }
    }
  }
}