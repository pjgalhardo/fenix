package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {

  Input<String> _project;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _project = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
  }

}
