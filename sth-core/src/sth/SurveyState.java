package sth;

import java.io.Serializable;

public abstract class SurveyState implements Serializable {

    private static final long serialVersionUID = 1L;
    private Survey _survey;
    private String _state;

    public SurveyState(Survey survey, String state) {
        _survey = survey;
        _state = state;
    }

    public abstract void open() throws Exception;

    public abstract void close() throws Exception;

    public abstract void cancel();

    public abstract void finish() throws Exception;

    public String getState() {
        return _state;
    }

    public Survey getSurvey() {
        return _survey;
    }
}