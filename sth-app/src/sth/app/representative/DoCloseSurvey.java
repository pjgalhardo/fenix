package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.ClosingSurveyException;

/**
 * 4.5.4. Close survey.
 */
public class DoCloseSurvey extends Command<SchoolManager> {

  Input<String> _discipline;
  Input<String> _project;

  /**
   * @param receiver
   */
  public DoCloseSurvey(SchoolManager receiver) {
    super(Label.CLOSE_SURVEY, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
    _project = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    if (_receiver.hasDiscipline(_discipline.value())) {
      if (_receiver.hasProject(_discipline.value(), _project.value())) {
        if (_receiver.hasSurvey(_discipline.value(), _project.value())) {
          try {
            _receiver.doCloseSurvey(_discipline.value(), _project.value());
          } catch (Exception e) {
            throw new ClosingSurveyException(_discipline.value(), _project.value());
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
