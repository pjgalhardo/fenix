package sth;

import sth.OpenedSurvey;

public class CreatedSurvey extends SurveyState {

    private static final long serialVersionUID = 1L;

    CreatedSurvey(Survey survey) {
        super(survey, "Created");
    }

    public void open() {
        getSurvey().setState(new OpenedSurvey(getSurvey()));
    }

    public void close() throws Exception {
        throw new Exception("Trying to close a created survey.");
    }

    public void finish() throws Exception {
        throw new Exception("Trying to finish a created survey.");
    }

    public void cancel() {
    }
}