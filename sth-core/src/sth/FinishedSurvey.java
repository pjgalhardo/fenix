package sth;

public class FinishedSurvey extends SurveyState {

    private static final long serialVersionUID = 1L;

    FinishedSurvey(Survey survey) {
        super(survey, "Finished");
    }

    public void open() throws Exception {
        throw new Exception("Trying to open a finished survey.");
    }

    public void close() {
        getSurvey().setState(new ClosedSurvey(getSurvey()));
    }

    public void finish() {
    }

    public void cancel() {
    }
}