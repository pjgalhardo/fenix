package sth;

import sth.OpenedSurvey;

public class ClosedSurvey extends SurveyState {

    private static final long serialVersionUID = 1L;

    ClosedSurvey(Survey survey) {
        super(survey, "Closed");
    }

    public void open() {
        getSurvey().setState(new OpenedSurvey(getSurvey()));
    }

    public void close() throws Exception {
        throw new Exception("Trying to close a closed survey.");
    }

    public void finish() {
        getSurvey().setState(new FinishedSurvey(getSurvey()));
    }

    public void cancel() {
        getSurvey().setState(new OpenedSurvey(getSurvey()));
    }
}