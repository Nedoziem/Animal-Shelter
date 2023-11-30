import java.awt.event.*;
import javax.swing.*;
import java.util.Enumeration;

class FosterListener implements ActionListener {

  private JTextField firstNameTextAdopt;
  private JTextField lastNameTextAdopt;
  private JTextField emailTextAdopt;
  private JTextField petIDTextAdopt;
  private JTextField phoneTextAdopt;
  private JTextField applicationTextAdopt;
  private JLabel errorMessage;  

  public FosterListener(JTextField firstNameTextAdopt, JTextField lastNameTextAdopt, JTextField emailTextAdopt, JTextField phoneTextAdopt, JTextField petIDTextAdopt,
      JTextField applicationTextAdopt,
      JLabel errorMessage) {
    this.firstNameTextAdopt = firstNameTextAdopt;
    this.lastNameTextAdopt = lastNameTextAdopt;
    this.emailTextAdopt = emailTextAdopt;
    this.phoneTextAdopt = phoneTextAdopt;
    this.petIDTextAdopt = petIDTextAdopt;
    this.applicationTextAdopt = applicationTextAdopt;
    this.errorMessage = errorMessage;
  }

  // if all fields are filled, calls fosterApplicationSubmit method
  public void actionPerformed(ActionEvent e) {
    if (firstNameTextAdopt.getText().equals("") || lastNameTextAdopt.getText().equals("") || emailTextAdopt.getText().equals("") || petIDTextAdopt.getText().equals("") || phoneTextAdopt.getText().equals("") || applicationTextAdopt.getText().equals("")) {
       errorMessage.setText("<html>Please answer all fields.</html>");
      return;
    } else {
      errorMessage.setText("<html>Your form was submitted.</html>");
Main.fosterApplicationSubmit(firstNameTextAdopt.getText(), lastNameTextAdopt.getText(), emailTextAdopt.getText(), phoneTextAdopt.getText(), petIDTextAdopt.getText(), applicationTextAdopt.getText());
      }
      return;
    }
  }