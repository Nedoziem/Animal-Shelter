import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

class ProgressReportListener implements ActionListener {
  private JLabel errorMessage;
  private ArrayList<FosterParent> fosterParents;

  public ProgressReportListener(JLabel errorMessage, ArrayList<FosterParent> fosterParents) {
    this.errorMessage = errorMessage;
    this.fosterParents = fosterParents;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < fosterParents.size(); i++) {
      System.out.println("start");
      if (fosterParents.get(i).getID().equals(Main.IDTextProg.getText()) && !Main.reportText.getText().isEmpty()) {
        System.out.println("match found");
        String s = "ID: " + Main.IDTextProg.getText() + " Report: " + Main.reportText.getText() + "\n";
        fosterParents.get(i).submitProgressReport(s);
        errorMessage.setText("<html>Your report has been submitted.</html>");
        return;
      } 
        else if (Main.IDTextProg.getText().isEmpty() || Main.reportText.getText().isEmpty()) {
        errorMessage.setText("<html>Please fill all fields.</html>");

     } else 
        errorMessage.setText("<html>Match not found. Please try again.</html>");
           System.out.println("match not found");
      }
    }
  }

