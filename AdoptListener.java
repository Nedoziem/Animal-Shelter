import java.awt.event.*;
import javax.swing.*;
import java.util.Enumeration;

class AdoptListener implements ActionListener {

  private JTextField nameTextAdopt;
  private JTextField emailTextAdopt;
  private JTextField petIDTextAdopt;
  private JTextField applicationTextAdopt;
  private JLabel errorMessage;

  public AdoptListener(JTextField nameTextAdopt, JTextField emailTextAdopt, JTextField petIDTextAdopt,
      JTextField applicationTextAdopt,
      JLabel errorMessage) {
    this.nameTextAdopt = nameTextAdopt;
    this.emailTextAdopt = emailTextAdopt;
    this.petIDTextAdopt = petIDTextAdopt;
    this.applicationTextAdopt = applicationTextAdopt;
    this.errorMessage = errorMessage;
    //System.out.println("woo" + " " + nameTextAdopt);
  }

  public void actionPerformed(ActionEvent e) {
    //System.out.println("goo" + " " + nameTextAdopt);
    if (nameTextAdopt.getText().equals("") || emailTextAdopt.getText().equals("") || petIDTextAdopt.getText().equals("") || applicationTextAdopt.getText().equals("")) {
       errorMessage.setText("<html>Please answer all fields.</html>");
      return;
    } else {
      errorMessage.setText("<html>Your form was submitted.</html>");
Main.adoptApplicationSubmit(nameTextAdopt.getText(), emailTextAdopt.getText(), petIDTextAdopt.getText(), applicationTextAdopt.getText());
      
      }
      return;
    }
  }