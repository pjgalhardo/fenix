package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import sth.exceptions.ImportFileException;
import sth.exceptions.NoSuchPersonException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  Input<String> _file;

  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);

  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try {
      if (!_receiver.fileExists()) {
        _file = _form.addStringInput(Message.openFile());
        _form.parse();
        _receiver.setFile(_file.value());
      }
      _receiver.openFile();

    } catch (NoSuchPersonException ife) {
      ife.printStackTrace();
      System.exit(0);
    }

  }
}
