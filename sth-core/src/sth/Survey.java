package sth;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Integer, Answer> _answers;
    private SurveyState _state = new CreatedSurvey(this);

    public Survey() {
        _answers = new TreeMap<Integer, Answer>();
    }

    public void setState(SurveyState state) {
        _state = state;
    }

    public String getState() {
        return _state.getState();
    }

    public boolean isEmpty() {
        return _answers.isEmpty();
    }

    public void open() throws Exception {
        try {
            _state.open();
        } catch (Exception e) {
            throw new Exception("Error opening survey.");
        }
    }

    public void close() throws Exception {
        try {
            _state.close();
        } catch (Exception e) {
            throw new Exception("Error closing survey.");
        }
    }

    public void finish() throws Exception {
        try {
            _state.finish();
        } catch (Exception e) {
            throw new Exception("Error finishing survey.");
        }

    }

    public void cancel() {
        _state.cancel();
    }

    public void answer(Student student, int hours, String comment) {
        Answer answer = new Answer(hours, comment);
        _answers.put(student.getId(), answer);
    }

    public String showResults() {
        String result = "";
        int soma = 0;
        int maximo = -1;
        int minimo = -1;
        for (Map.Entry<Integer, Answer> entry : _answers.entrySet()) {
            int hours = entry.getValue().getHours();
            soma += hours;
            if (hours > maximo) {
                maximo = hours;
            }
            if (minimo == -1 || hours < minimo) {
                minimo = hours;
            }
        }
        return result += " * Número de respostas: " + _answers.size()
                + "\n * Tempos de resolução (horas) (mínimo, médio, máximo): " + minimo + ", " + maximo + ", "
                + soma / _answers.size();

    }
}