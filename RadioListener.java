import java.awt.event.*;
import javax.swing.*;
import java.util.Enumeration;

class RadioListener implements ActionListener {

  private ButtonGroup group;
  private JLabel errorMessage;

  public RadioListener(ButtonGroup group, JLabel errorMessage) {
    this.group = group;
    this.errorMessage = errorMessage;
  }

  // directs user to different screens based on which radio button they pressed before hitting the submit button
  public void actionPerformed(ActionEvent e) {
    String buttonPressed = getSelectedButtonText(group);
    if (buttonPressed.equals("View available pets")) {
      Main.petScreen();
    } else if (buttonPressed.equals("Log in (managers)")) {
      Main.loginScreen();
    } else if (buttonPressed.equals("Apply to adopt")) {
      Main.applyAdoptScreen();
    } else if (buttonPressed.equals("Apply to foster")) {
      Main.applyFosterScreen();
    } else if (buttonPressed.equals("Submit foster progress report")) {
      Main.progressReportScreen();
    } else {
      errorMessage.setText("<html>It looks like no option was selected. </br>Please select an option.</html>");
    }
  }

  public String getSelectedButtonText(ButtonGroup buttonGroup) {
    for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
      AbstractButton button = buttons.nextElement();

      if (button.isSelected()) {
        return button.getText();
      }
    }
    String noneSelected = "none";
    return noneSelected;
  }

}
