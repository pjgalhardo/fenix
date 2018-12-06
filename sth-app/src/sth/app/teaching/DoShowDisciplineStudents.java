package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchDisciplineException;

/**
 * 4.3.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {

  Input<String> _discipline;

  /**
   * @param receiver
   */
  public DoShowDisciplineStudents(SchoolManager receiver) {
    super(Label.SHOW_COURSE_STUDENTS, receiver);
    _discipline = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException, NoSuchDisciplineException {
    _form.parse();
    if (_receiver.isTeacherDiscipline(_discipline.value())) {
      _display.popup(_receiver.doShowDisciplineStudents(_discipline.value()));
    } else {
      throw new NoSuchDisciplineException(_discipline.value());
    }
  }

}
