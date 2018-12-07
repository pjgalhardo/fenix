package sth;

import java.io.Serializable;
import sth.SchoolManager;
import sth.Student;
import sth.Course;

import java.util.Map;
import java.util.TreeMap;

public class Project implements Serializable {
    private static final long serialVersionUID = 201810051538L;
    private String _name;
    // FIXME: tirar description de comentario
    // private String _description;
    private Map<Integer, Submission> _submissions;
    private ProjectState _projectState = new OpenProject(this);
    private Survey _survey;

    Project(String name) {
        _name = name;
        // _description = description;
        _submissions = new TreeMap<Integer, Submission>();
    }

    public String getName() {
        return _name;
    }

    public void close() {
        _projectState.close();
        if (_survey != null) {
            if (_survey.getState().equals("Created")) {
                try {
                    _survey.open();
                } catch (Exception e) {
                    // ignore because its never thrown in this case
                }

            }
        }
    }

    public void setState(ProjectState state) {
        _projectState = state;
    }

    public boolean isOpen() {
        return _projectState.isOpen();
    }

    public boolean hasSubmitted(int student) {
        return (_submissions.get(student) != null);
    }

    public void submit(String discipline, Student student, String submission) {
        if (hasSubmitted(student.getId()))
            _submissions.remove(student.getId());
        _submissions.put(student.getId(), new Submission(submission));
        showSubmissions(discipline);
    }

    public String showSubmissions(String discipline) {
        String submissions = discipline + " - " + _name;
        for (Map.Entry<Integer, Submission> entry : _submissions.entrySet()) {
            submissions += "\n* ";
            int student = entry.getKey();
            submissions += student + " - ";
            Submission submission = entry.getValue();
            submissions += submission.getName();
        }
        return submissions;
    }

    public Survey getSurvey() {
        return _survey;
    }

    public void createSurvey() {
        _survey = new Survey();
    }

    public void cancelSurvey() {
        if (_survey.getState().equals("Closed")) {
            _survey.cancel();
        }
        _survey = null;
    }

    public void closeSurvey() throws Exception {
        try {
            _survey.close();
        } catch (Exception e) {
            throw new Exception("Error closing survey.");
        }

    }

    public void openSurvey() throws Exception {
        try {
            _survey.open();
        } catch (Exception e) {
            throw new Exception("Error opening survey.");
        }
    }

    public void finishSurvey() throws Exception {
        try {
            _survey.finish();
        } catch (Exception e) {
            throw new Exception("Error finishing survey.");
        }
    }

    public boolean isSurveyFinished() {
        return _survey.getState().equals("Finished");
    }

    public String showSurveyResults() {
        return " * Número de submissões: " + _submissions.size() + "\n" + _survey.showResults();
    }
}