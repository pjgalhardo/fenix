package sth;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.File;

import java.util.Collection;
import java.util.ArrayList;

import java.io.FileNotFoundException;
import sth.exceptions.ImportFileException;
import sth.exceptions.BadEntryException;
import sth.exceptions.NoSuchPersonException;
import sth.Student;
import sth.Teacher;
import sth.Administrative;

public class Parser {

  private School _school;
  private Person _person;
  private boolean _representative = false;
  private String category;

  Parser(School s) {
    _school = s;
  }

  void readFile(String fileName) throws IOException, BadEntryException {
    File file = new File(fileName);

    try (BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
      for (String line; (line = buffer.readLine()) != null;) {
        if (line.startsWith("#")) {
          changePerson(line.substring(2));
        } else {
          String[] parts = line.split("\\|");
          if (parts.length > 4) {
            throw new BadEntryException("Too many arguments");
          }
          category = parts[0];
          int id = Integer.parseInt(parts[1]);
          int phoneNumber = Integer.parseInt(parts[2]);
          if (category.equals("ALUNO")) {
            _person = new Student(id, phoneNumber, parts[3]);
          } else if (category.equals("DELEGADO")) {
            _person = new Student(id, phoneNumber, parts[3]);
            _representative = true;
          } else if (category.equals("DOCENTE")) {
            _person = new Teacher(id, phoneNumber, parts[3]);
          } else if (category.equals("FUNCIONÁRIO")) {
            _person = new Administrative(id, phoneNumber, parts[3]);
          } else {
            throw new BadEntryException("No role");
          }
          _school.addPerson(_person, category);
        }
      }
    } catch (IOException e) {
      throw new IOException();
    }

  }

  private void changeRepresentative(String[] linesplit) {
    if (category.equals("DELEGADO")) {
      Student student = (Student) _person;
      if (_school.getCourse(linesplit[0]) == null) {
        _school.addCourse(new Course(linesplit[0]));
      }
      if (student.getCourse() == null) {
        student.setCourse(_school.getCourse(linesplit[0]));
      }
      if (student.getCourse().getDiscipline(linesplit[1]) == null) {
        student.getCourse().addDiscipline(new Discipline(linesplit[1]));
        student.getCourse().getDiscipline(linesplit[1]).setCourse(student.getCourse());
      }
      if (student.getDiscipline(linesplit[1]) == null) {
        student.addDiscipline(student.getCourse().getDiscipline(linesplit[1]));
      }
      _school.getCourse(linesplit[0]).getDiscipline(linesplit[1]).addStudent(student);
      student.becomeRepresentative();
      _representative = false;
    }
  }

  private void changeStudent(String[] linesplit) {

    if (category.equals("ALUNO")) {
      Student student = (Student) _person;

      if (_school.getCourse(linesplit[0]) == null) {
        _school.addCourse(new Course(linesplit[0]));
      }
      if (_school.getCourse(linesplit[0]).getDiscipline(linesplit[1]) == null) {
        _school.getCourse(linesplit[0]).addDiscipline(new Discipline(linesplit[1]));
        _school.getCourse(linesplit[0]).getDiscipline(linesplit[1]).setCourse(_school.getCourse(linesplit[0]));
      }
      if (student.getCourse() == null) {
        student.setCourse(_school.getCourse(linesplit[0]));
      }
      if (student.getDiscipline(linesplit[1]) == null) {
        student.addDiscipline(_school.getCourse(linesplit[0]).getDiscipline(linesplit[1]));
      }
      _school.getCourse(linesplit[0]).getDiscipline(linesplit[1]).addStudent(student);
    }
  }

  private void changeTeacher(String[] linesplit) {
    if (category.equals("DOCENTE")) {
      Teacher teacher = (Teacher) _person;
      if (_school.getCourse(linesplit[0]) == null) {
        _school.addCourse(new Course(linesplit[0]));
      }
      if (_school.getCourse(linesplit[0]).getDiscipline(linesplit[1]) == null) {
        _school.getCourse(linesplit[0]).addDiscipline(new Discipline(linesplit[1]));
        _school.getCourse(linesplit[0]).getDiscipline(linesplit[1]).setCourse(_school.getCourse(linesplit[0]));
      }
      teacher.addDiscipline(_school.getCourse(linesplit[0]).getDiscipline(linesplit[1]));
    }
  }

  private void changePerson(String line) throws BadEntryException {
    if (_person == null) {
      throw new BadEntryException("No person");
    } else {
      String[] linesplit = line.split("\\|");
      if (category.equals("FUNCIONÁRIO")) {
        throw new BadEntryException("Bad input.");
      } else if (category.equals("ALUNO") && !_representative) {
        changeStudent(linesplit);
      } else if (category.equals("DELEGADO") && _representative) {
        changeRepresentative(linesplit);
      } else if (category.equals("DOCENTE")) {
        changeTeacher(linesplit);
      }
    }
  }
}