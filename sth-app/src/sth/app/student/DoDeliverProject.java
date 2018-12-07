package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {

  Input<String> _project;
  Input<String> _discipline;
  Input<String> _message;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _discipline = _form.addStringInput((Message.requestDisciplineName()));
    _project = _form.addStringInput(Message.requestProjectName());
    _message = _form.addStringInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    if (_receiver.isDiscipline(_discipline.value())) {
      if (_receiver.isDisciplineProject(_discipline.value(), _project.value())) {
        if (_receiver.isProjectOpen(_discipline.value(), _project.value())) {
          _receiver.deliverProject(_discipline.value(), _project.value(), _message.value());
        } else {
          throw new NoSuchProjectException(_discipline.value(), _project.value());
        }
      } else {
        throw new NoSuchProjectException(_discipline.value(), _project.value());
      }
    } else {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }
}
