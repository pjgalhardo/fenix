package sth;

import java.io.Serializable;
import sth.SchoolManager;
import sth.Student;
import sth.Course;
import sth.Project;

import java.util.Map;
import java.util.TreeMap;

public class Discipline implements Serializable {
    private Course _course;
    private String _name;
    private static final long serialVersionUID = 201810051538L;
    // private int _capacity;
    private Map<Integer, Student> _students;
    private Map<String, Project> _projects;

    public Discipline(String name) {
        _name = name;
        // _capacity = capacity;
        _students = new TreeMap<Integer, Student>();
        _projects = new TreeMap<String, Project>();
    }

    public String getName() {
        return _name;
    }

    void setCourse(Course course) {
        _course = course;
    }

    Course getCourse() {
        return _course;
    }
    /*
     * public int getCapacity() { return _capacity; }
     */

    void addStudent(Student student) {
        if (_students.get(student.getId()) == null) {
            // if (_students.size() < _capacity) {
            _students.put(student.getId(), student);
            // }
        }
    }

    String showAllStudents() {
        String studentsOutput = "";
        if (_students.size() > 0) {
            Map<Integer, Student> students = new TreeMap<Integer, Student>();
            for (Map.Entry<Integer, Student> entry : _students.entrySet()) {
                Student student = entry.getValue();
                students.put(student.getId(), student);
            }
            for (Map.Entry<Integer, Student> entry : students.entrySet()) {
                Student student = entry.getValue();
                studentsOutput += student + "\n";
            }
            students.clear();
        }
        return studentsOutput;
    }

    public Project getProject(String name) {
        return _projects.get(name);
    }

    void createProject(String name) {
        if (getProject(name) == null) {
            _projects.put(name, new Project(name));
        }
    }

    void closeProject(String name) {
        Project project = getProject(name);
        if (project != null) {
            project.close();
        }
    }
}