package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

/**
 * 4.4.2. Answer survey.
 */
public class DoAnswerSurvey extends Command<SchoolManager> {

  Input<String> _discipline;
  Input<String> _project;
  Input<Integer> _hours;
  Input<String> _comment;

  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
    _project = _form.addStringInput(Message.requestProjectName());
    _hours = _form.addIntegerInput(Message.requestProjectHours());
    _comment = _form.addStringInput(Message.requestComment());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    if (_receiver.hasDiscipline(_discipline.value())) {
      if (_receiver.hasProject(_discipline.value(), _project.value())) {
        if (_receiver.hasSurvey(_discipline.value(), _project.value())) {
          if (_receiver.hasSubmission(_discipline.value(), _project.value())) {
            _receiver.doAnswerSurvey(_discipline.value(), _project.value(), _hours.value(), _comment.value());
          } else {
            throw new NoSuchProjectException(_discipline.value(), _project.value());
          }
        } else {
          throw new NoSurveyException(_discipline.value(), _project.value());
        }
      } else {
        throw new NoSuchProjectException(_discipline.value(), _project.value());
      }
    } else {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }

}
