package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

/**
 * 4.2.4. Search person.
 */
public class DoSearchPerson extends Command<SchoolManager> {

  Input<String> _person;

  /**
   * @param receiver
   */
  public DoSearchPerson(SchoolManager receiver) {
    super(Label.SEARCH_PERSON, receiver);
    _person = _form.addStringInput(Message.requestPersonName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    _display.popup(_receiver.doSearchPerson(_person.value()));
  }

}
