package sth;

public class OpenedSurvey extends SurveyState {

    private static final long serialVersionUID = 1L;

    OpenedSurvey(Survey survey) {
        super(survey, "Opened");
    }

    public void open() throws Exception {
        throw new Exception("Trying to open an opened survey.");
    }

    public void close() {
        getSurvey().setState(new ClosedSurvey(getSurvey()));
    }

    public void finish() throws Exception {
        throw new Exception("Trying to finish an opened survey.");
    }

    public void cancel() {
    }
}