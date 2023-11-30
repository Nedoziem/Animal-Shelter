import java.awt.event.*;
import javax.swing.*;
import java.util.Enumeration;

class LoginListener implements ActionListener {
  private JButton loginButton;

  public LoginListener(JButton loginButton, JLabel errormessage) {
    this.loginButton = loginButton;
    loginButton.addActionListener(this);
    loginButton.setActionCommand("Login");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
       Main.loginScreen();
  }
  }

