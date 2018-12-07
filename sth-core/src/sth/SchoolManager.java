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

  public int getId() {
    return _loggedUser.getId();
  }

  public void setFile(String file) {
    _file = file;
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
      System.exit(0);
    }
  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    return _school.isaAdministrative(_loggedUser);
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean hasProfessor() {
    return _school.isaTeacher(_loggedUser);
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean hasStudent() {
    return _school.isaStudent(_loggedUser);
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean hasRepresentative() {
    return _school.isaRepresentative(_loggedUser);
  }

  public School getSchool() {
    return _school;
  }

  public void setSchool(School school) {
    _school = school;
  }

  public void doChangePhoneNumber(int phoneNumber) {
    _loggedUser.changePhoneNumber(phoneNumber);
  }

  public String doSearchPerson(String name) {
    return _school.searchPerson(name);
  }

  public String doShowPerson() {
    return _loggedUser.toString();
  }

  public String doShowAllPersons() {
    return _school.showAllPersons();
  }

  public String doShowDisciplineStudents(String discipline) {
    Teacher teacher = (Teacher) _loggedUser;
    return teacher.doShowAllDisciplineStudents(discipline);
  }

  public boolean isDiscipline(String disciplineName) {
    Discipline discipline;
    if (hasStudent()) {
      Student student = (Student) _loggedUser;
      discipline = student.getDiscipline(disciplineName);
    } else {
      discipline = getDiscipline(disciplineName);
    }
    return discipline != null;
  }

  public boolean isTeacherDiscipline(String discipline) {
    Teacher teacher = (Teacher) _loggedUser;
    return (teacher.getDiscipline(discipline) != null);
  }

  public boolean isDisciplineProject(String disciplineName, String project) {
    Discipline discipline;
    if (hasStudent()) {
      Student student = (Student) _loggedUser;
      discipline = student.getDiscipline(disciplineName);
    } else {
      discipline = getDiscipline(disciplineName);
    }
    return discipline.getProject(project) != null;
  }

  public boolean isProjectOpen(String disciplineName, String projectName) {
    Discipline discipline;
    if (hasStudent()) {
      Student student = (Student) _loggedUser;
      discipline = student.getDiscipline(disciplineName);
    } else {
      discipline = getDiscipline(disciplineName);
    }
    Project project = discipline.getProject(projectName);
    return project.isOpen();
  }

  public void deliverProject(String disciplineName, String projectName, String message) {
    Student student = (Student) _loggedUser;
    Discipline discipline = student.getDiscipline(disciplineName);
    Project project = discipline.getProject(projectName);
    project.submit(disciplineName, student, message);
  }

  public Discipline getDiscipline(String discipline) {
    if (hasProfessor()) {
      Teacher teacher = (Teacher) _loggedUser;
      return teacher.getDiscipline(discipline);
    } else if (hasStudent()) {
      Student student = (Student) _loggedUser;
      return student.getDiscipline(discipline);
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

  public void openFile() throws NoSuchPersonException {
    try {
      ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_file)));
      School school = new School();
      school = (School) ois.readObject();
      ois.close();
      if (school.getPerson(_loggedUser.getId()) == null) {
        throw new NoSuchPersonException(_loggedUser.getId());
      } else {
        _school = school;
        _loggedUser = _school.getPerson(_loggedUser.getId());
      }
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  public void doCreateProject(String discipline, String projectName) {
    Teacher teacher = (Teacher) _loggedUser;
    Discipline _discipline = teacher.getDiscipline(discipline);
    _discipline.createProject(projectName);
  }

  public void doCloseProject(String discipline, String projectName) {
    if (hasProfessor()) {
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

  public String showProjectSubmissions(String disciplineName, String projectName) {
    Discipline discipline = getDiscipline(disciplineName);
    Project project = discipline.getProject(projectName);
    return project.showSubmissions(disciplineName);
  }

  public boolean hasDiscipline(String disciplineName) {
    Discipline discipline;
    if (hasStudent()) {
      Student student = (Student) _loggedUser;
      discipline = student.getDiscipline(disciplineName);
    } else {
      discipline = getDiscipline(disciplineName);
    }
    return discipline != null;
  }

  public boolean hasProject(String disciplineName, String projectName) {
    return getDiscipline(disciplineName).getProject(projectName) != null;
  }

  public boolean hasSurvey(String disciplineName, String projectName) {
    return getDiscipline(disciplineName).getProject(projectName).getSurvey() != null;
  }

  public void doCreateSurvey(String disciplineName, String projectName) {
    getDiscipline(disciplineName).getProject(projectName).createSurvey();
  }

  public void doCancelSurvey(String disciplineName, String projectName) {
    getDiscipline(disciplineName).getProject(projectName).cancelSurvey();
  }

  public void doOpenSurvey(String disciplineName, String projectName) throws Exception {
    try {
      getDiscipline(disciplineName).getProject(projectName).openSurvey();
    } catch (Exception e) {
      throw new Exception("Error opening survey.");
    }
  }

  public void doCloseSurvey(String disciplineName, String projectName) throws Exception {
    try {
      getDiscipline(disciplineName).getProject(projectName).closeSurvey();
    } catch (Exception e) {
      throw new Exception("Error closing survey.");
    }
  }

  public void doFinishSurvey(String disciplineName, String projectName) throws Exception {
    try {
      getDiscipline(disciplineName).getProject(projectName).finishSurvey();
    } catch (Exception e) {
      throw new Exception("Error finishing survey.");
    }
  }

  public boolean isSurveyFinished(String disciplineName, String projectName) {
    return getDiscipline(disciplineName).getProject(projectName).isSurveyFinished();
  }

  public boolean hasSurveyAnswers(String disciplineName, String projectName) {
    return getDiscipline(disciplineName).getProject(projectName).getSurvey().isEmpty();
  }

  public boolean hasSubmission(String disciplineName, String projectName) {
    return getDiscipline(disciplineName).getProject(projectName).hasSubmitted(_loggedUser.getId());
  }

  public void doAnswerSurvey(String disciplineName, String projectName, int hours, String comment) {
    Student student = (Student) _loggedUser;
    getDiscipline(disciplineName).getProject(projectName).getSurvey().answer(student, hours, comment);
  }

  public String doShowSurveyResults(String disciplineName, String projectName) {
    Discipline discipline = getDiscipline(disciplineName);
    Project project = discipline.getProject(projectName);
    Survey survey = project.getSurvey();
    String state = survey.getState();
    if (state.equals("Created")) {
      return discipline.getName() + " - " + project.getName() + " (por abrir)";
    } else if (state.equals("Opened")) {
      return discipline.getName() + " - " + project.getName() + " (aberto)";
    } else if (state.equals("Closed")) {
      return discipline.getName() + " - " + project.getName() + " (fechado)";
    } else if (state.equals("Finished")) {
      return discipline.getName() + " - " + project.getName() + "\n"
          + getDiscipline(disciplineName).getProject(projectName).showSurveyResults();
    }
    return "";
  }
}