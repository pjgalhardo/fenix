package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;

//FIXME import other classes if needed

/**
 * 4.3.3. Show project submissions.
 */
public class DoShowProjectSubmissions extends Command<SchoolManager> {

  Input<String> _projectName;
  Input<String> _discipline;

  /**
   * @param receiver
   */
  public DoShowProjectSubmissions(SchoolManager receiver) {
    super(Label.SHOW_PROJECT_SUBMISSIONS, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    if (_receiver.isDiscipline(_discipline.value())) {
      if (_receiver.isDisciplineProject(_discipline.value(), _projectName.value())) {
        _display.popup(_receiver.showProjectSubmissions(_discipline.value(), _projectName.value()));
      } else {
        throw new NoSuchProjectException(_discipline.value(), _projectName.value());
      }
    } else {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }

}
