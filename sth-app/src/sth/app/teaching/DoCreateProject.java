package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.DuplicateProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

//FIXME import other classes if needed

/**
 * 4.3.1. Create project.
 */
public class DoCreateProject extends Command<SchoolManager> {

  Input<String> _projectName;
  Input<String> _discipline;

  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
    _projectName = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();

    if (!_receiver.isDiscipline(_discipline.value())) {
      throw new NoSuchDisciplineException(_discipline.value());
    }
    if (_receiver.isDisciplineProject(_discipline.value(), _projectName.value())) {
      throw new DuplicateProjectException(_discipline.value(), _projectName.value());
    }

    _receiver.doCreateProject(_discipline.value(), _projectName.value());

  }

}
