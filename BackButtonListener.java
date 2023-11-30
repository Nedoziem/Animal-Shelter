import java.awt.event.*;
import javax.swing.*;

class BackButtonListener implements ActionListener {
  private JButton backButton;

  public BackButtonListener(JButton backButton) {
    this.backButton = backButton;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Main.zeroScreen();
  }
}