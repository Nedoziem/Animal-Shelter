/**
 * 
 * @Sara Mehdinia and @Kamila Chzhao and @Nicole Edoziem
 * CMS 340 Final Project
 * Animal Shelter System
 * December 3, 2022
 */

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main implements ActionListener {

  // stores all employees, pets, foster parents, managers, active adoption applications, and active foster applications in separate arraylists
  private static ArrayList<Employee> employees = new ArrayList<Employee>();
  private static ArrayList<Pet> pets = new ArrayList<Pet>();
  private static ArrayList<FosterParent> fosterParents = new ArrayList<FosterParent>();
  private static ArrayList<Manager> managers = new ArrayList<Manager>();
  static ArrayList<String> activeAdoptApplications = new ArrayList<String>();
  static ArrayList<String> activeFosterApplications = new ArrayList<String>();
  
  static boolean access;
  static Manager activeManager; //stores the manager who is logged in
  static PreLoginGUI window;
  
  // all the listeners
  static RadioListener AListener;
  static BackButtonListener AListenerBackButton;
  static LoginListener AListenerLogin;
  static AdoptListener AListenerAdopt;
  static FosterListener AListenerFoster;
  static ProgressReportListener AListenerReport;
  // jtextfields and labels
  static JTextField userText;
  static JPasswordField passText;
  static JTextField nameTextAdopt;
  static JTextField emailTextAdopt;
  static JTextField petIDTextAdopt;
  static JTextField applicationTextAdopt;
  static JTextField firstNameTextFoster;
  static JTextField lastNameTextFoster;
  static JTextField emailTextFoster;
  static JTextField phoneTextFoster;
  static JTextField petIDTextFoster;
  static JTextField applicationTextFoster;
  static JTextField IDTextProg;
  static JTextField reportText;
  static JLabel errorMessageReport;
  static JLabel errorMessageLogin;

  // methods*******************************************************

  // method to populate the pets for the viewing page
  public static void populatePets() {
    
    pets.clear();
    // try-catch instantiating all pets from pets.txt
    try {
      // reads pet data from pets.txt
      File f = new File("pets.txt");
      Scanner input = new Scanner(f);

      // counts how many pets to process
      int numPets = 0;
      while (input.hasNextLine()) {
        input.nextLine();
        numPets++;
      }
      input.close();

      Scanner scan = new Scanner(f);

      // Stores all of the pets in a single ArrayList called pets
      for (int i = 0; i < numPets; i++) {
        String line = scan.nextLine();
        String[] a = line.split(" "); // splits on the blank space
        String name = a[0]; // pet name
        int age = Integer.parseInt(a[1]); // age in years of pet      
        String type = a[2]; // type of animal
        String breed = a[3]; // breed of pet
        String medication = a[4]; // empty String of medication
        int petID = Integer.parseInt(a[5]); // pet ID
        String fosterStatus = a[6]; // foster status
        Pet p = new Pet(name, age, breed, type, medication, petID,fosterStatus);
        pets.add(i, p);
      }
      scan.close(); // Close the Scanner
    } catch (FileNotFoundException e) {
      System.out.println("Error");
      e.printStackTrace(); // will tell you what went wrong
    }
  }

  // method to poulate the Employees------------------------------
  public static void populateEmployees() {
    employees.clear();
    managers.clear();
    // try-catch instantiating all employees from employees.txt
    try {
      // reads employee data from employees.txt
      File f = new File("employees.txt");
      Scanner input = new Scanner(f);

      // counts how many employees to process
      int numEmployees = 0;
      while (input.hasNextLine()) {
        input.nextLine();
        numEmployees++;
      }
      input.close();

      Scanner scan = new Scanner(f);

      // Stores all of the employees in a single ArrayList called employees
      int managerIncrementer = 0;
      for (int i = 0; i < numEmployees; i++) {
        String line = scan.nextLine();
        String[] a = line.split(" "); // splits on the blank space
        char empType = a[0].charAt(0); // employee type: E for employee, M for manager
        String empName = a[1] + " " + a[2]; // name given as first name and last name
        String empEmail = a[3]; // employee email
        String empPhone = a[4]; // employee phone number

        // add to employee arraylist
        if (empType == 'E') { // make a new employee object
          Employee e = new Employee(empName, empEmail, empPhone);
          employees.add(i, e);
        } else if (empType == 'M') { // make a new manager object
          String username = a[5];
          String passcode = a[6];
          Manager m = new Manager(empName, empEmail, empPhone, username, passcode);
          employees.add(i, m);
          managers.add(managerIncrementer, m);
          managerIncrementer += 1;
        }
      }
      scan.close(); // Close the Scanner
    } catch (FileNotFoundException e) {
      System.out.println("Error");
      e.printStackTrace(); // will tell you what went wrong
    }
  }

  // method to run transaction files, done by manager-------------
  public static void processTransaction() {

    // try-catch block
    try {
      File f = new File("transactions.txt");
      FileWriter fw = new FileWriter("pets.txt", true);
      BufferedWriter b = new BufferedWriter(fw);
      PrintWriter pr = new PrintWriter(b);
      FileWriter fww = new FileWriter("employees.txt", true);
      BufferedWriter bw = new BufferedWriter(fww);
      PrintWriter pw = new PrintWriter(bw);
      Scanner input = new Scanner(f);

      // while loop to go through file and tokenize
      while (input.hasNextLine()) {
        String line = input.nextLine();
        String[] token = line.split(" ");

        // If statement to add a new pet
        if (token[0].equals("P")) {
          String name = token[1];
          int age = Integer.parseInt(token[2]);
          String type = token[3];
          String breed = token[4];
          String medication = token[5];
          int petID = Integer.parseInt(token[6]);
          String fosterStatus = token[7];
          Pet p = new Pet(name, age, type, breed, medication, petID, fosterStatus);
          Pet.addPet(p);
        }

        // if statement to add a new employee
        if (token[0].equals("E")) {
          String name = token[1] + " " + token[2];
          String email = token[3];
          String phone = token[4];
          Employee e = new Employee(name, email, phone);
          Employee.addEmployee(e);
        }

        // if statement to delete pet
        if (token[0].equals("DP")) {
          String name = token[1];
          int age = Integer.parseInt(token[2]);
          String breed = token[3];
          String medication = token[4];
          String type = token[5];
          int petID = Integer.parseInt(token[6]);
           String fosterStatus = token[7];
          Pet p = new Pet(name, age, breed, medication, type, petID, fosterStatus);
          p.deletePet();
        }

        // if statement to delete employee
        if (token[0].equals("DE")) {
          String name = token[1] + " " + token[2];
          String email = token[3];
          String phone = token[4];
          Employee e = new Employee(name, email, phone);
          Employee.deleteEmployee(e);
        }
      }
      pr.close();

      pw.close();
      fw.close();
      b.close();
      fww.close();
      bw.close();
      input.close();

    } catch (Exception e) {

      System.out.println("Error in processing transactions.");

      e.printStackTrace();

    }
    populatePets();
    populateEmployees();
    populateFosterParents();

  }

  // method to populate the foster parent arraylist with fosterparent objects---------
  public static void populateFosterParents() {
    fosterParents.clear();
    // try-catch instantiating all fosters from fosters.txt---
    try {
      // read foster parent data from fosters.txt
      File f = new File("fosters.txt");
      Scanner input = new Scanner(f);
      int numFosterParents = 0;
      while (input.hasNextLine()) {
        input.nextLine();
        numFosterParents++;
      }
      input.close();
      // Store all foster parents in a single array list called fosterParents
      Scanner scan = new Scanner(f);
      for (int i = 0; i < numFosterParents; i++) {
        String line = scan.nextLine();
        String[] token = line.split(" ");
        String fosName = token[0] + " " + token[1]; // name given as first name and last name
        String fosEmail = token[2];
        String fosPhone = token[3];
        String fosId = token[4];
        String fosPetID = token[5];
        int searcher = -1;
        for (int j = 0; j < pets.size(); j++) {
          if (pets.get(j).getPetID() == Integer.parseInt(fosPetID)) {
            searcher = j;
          }
        }
        // if statement handling when the foster in the text file does not have a pet that matches based on pet id
        if (searcher == -1) {
          System.out.println("Foster parent " + fosName + " in fosters.txt is stated to be fostering pet ID " + fosPetID
              + ", but that pet is not active.\nShe has not been added to the system as an active foster parent.\nPlease update fosters.txt.\n");
        } else { //make new fosterparent object
          Pet fosPet = pets.get(searcher);
          fosPet.toString();
          FosterParent fosterParent = new FosterParent(fosName, fosEmail, fosPhone, fosId, Integer.parseInt(fosPetID), fosPet);
          fosterParents.add(fosterParent);
        }
      }
      scan.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error");
      e.printStackTrace();
    }
  }

  // method displaying screen that prints out all adoptable pets------------------
  public static void petScreen() { 
    window.getContentPane().removeAll();
    window.getContentPane().repaint(); //resetting window

    JLabel label = new JLabel();
    String txt = "<html>";
    for (Pet tag : pets) { //iterating through pets arraylist and fetching tostring
      txt += tag.toStringPublic() + "<br/>";
    }
    txt += "</html>";
    label.setText(txt); //setting text into label

    label.setVerticalAlignment(JLabel.TOP);
    label.setHorizontalAlignment(JLabel.LEFT);

    JPanel topPanel = new JPanel();
    topPanel.setBackground(Color.white);
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
    topPanel.setBounds(0, 0, 300, 300);

    JButton backButton = new JButton("Back to menu");
    backButton.setPreferredSize(new Dimension(100, 20));
    topPanel.add(label);
    topPanel.add(backButton);

    AListenerBackButton = new BackButtonListener(backButton);
    backButton.addActionListener(AListenerBackButton);

    backButton.setVisible(true);

    //adding a scroll bar
    JScrollPane scroll = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.setVisible(true);
    topPanel.add(scroll, BorderLayout.CENTER);
    window.getContentPane().add(topPanel);
    window.setVisible(true);
    window.validate();
  }

  // method for the first screen the user encounters when running program-----------
  public static void zeroScreen() {
    window.setVisible(true);
    window.getContentPane().removeAll();
    window.getContentPane().repaint();
    window.getContentPane().revalidate();

    JLabel label = new JLabel();
    String txt = "<html>Welcome to the Animal Shelter System!</br></html>";
    label.setText(txt);
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(9, 1, 8, 8));
    topPanel.setBackground(Color.pink);
    topPanel.setBounds(0, 0, 300, 300);
    // radio buttons that present the user's options
    JRadioButton one = new JRadioButton("View available pets");
    JRadioButton two = new JRadioButton("Log in (managers)");
    JRadioButton three = new JRadioButton("Apply to adopt");
    JRadioButton four = new JRadioButton("Apply to foster");
    JRadioButton five = new JRadioButton("Submit foster progress report");
    JButton submit = new JButton("submit");
    submit.setPreferredSize(new Dimension(40, 40));
    ButtonGroup group = new ButtonGroup();
    group.add(one);
    group.add(two);
    group.add(three);
    group.add(four);
    group.add(five);

    //label to be modified in case of errors in input
    JLabel errLabel = new JLabel();
    String errTxt = "<html>Please select an option.</br></html>";
    errLabel.setText(errTxt);

    topPanel.add(label);
    topPanel.add(one);
    topPanel.add(two);
    topPanel.add(three);
    topPanel.add(four);
    topPanel.add(five);
    topPanel.add(submit);
    topPanel.add(errLabel);

    AListener = new RadioListener(group, errLabel);
    submit.addActionListener(AListener);

    window.add(topPanel);
  }

  // method handling login screen----------------------------------------------
  public static void loginScreen() {
    window.getContentPane().removeAll();
    window.getContentPane().repaint();
    // sets up username and password and error message textfields and labels
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(9, 1, 8, 8));
    topPanel.setBackground(Color.white);
    topPanel.setBounds(0, 0, 300, 300);
    JLabel userLabel = new JLabel("Username:");
    userLabel.setBounds(8, 20, 80, 25);
    topPanel.add(userLabel);
    userText = new JTextField(10);
    userText.setBounds(100, 20, 165, 25);
    topPanel.add(userText);
    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(10, 50, 80, 25);
    topPanel.add(passLabel);
    passText = new JPasswordField();
    passText.setBounds(100, 50, 165, 25);
    passLabel.add(passText);
    topPanel.add(passText);
    JButton login = new JButton("Login");
    login.setBounds(10, 80, 80, 25);
    topPanel.add(login);
    login.addActionListener(new Main());
    topPanel.add(login);
    JButton backButton = new JButton("Back to menu");
    backButton.setPreferredSize(new Dimension(100, 20));
    AListenerBackButton = new BackButtonListener(backButton);
    backButton.addActionListener(AListenerBackButton);
    topPanel.add(backButton);
    errorMessageLogin = new JLabel();
    errorMessageLogin.setText("Please enter username and password.");
    topPanel.add(errorMessageLogin);
    window.getContentPane().add(topPanel);
    window.setVisible(true);
    window.validate();
  }

  //method that converts a plain text password into its binary equivalent----------
  public static String convertToBinary(String password) {
    StringBuilder converted = new StringBuilder();
    char[] chars = password.toCharArray();
    for (char aChar : chars) {
      converted.append(
          String.format("%8s", Integer.toBinaryString(aChar)) //converts integer to binary string
              .replaceAll(" ", "0"));
    }
    return converted.toString();
  }

  // method that matches the encrypted password to the encrypted password stored in the manager object--------------------
  public static boolean matchPassword(String encryptedPass, PreLoginGUI window) {
    for (int i = 0; i < managers.size(); i++) {
      if (managers.get(i).getUsername().equals(userText.getText())
          && managers.get(i).getPasscode().equals(encryptedPass)) {
        activeManager = managers.get(i);
        access = true;
        return access;
      } else {
        access = false;
      }
    }
    return access;
  }
  // Inflicts the 
  @Override
  public void actionPerformed(ActionEvent e) {
    String passInput = new String(passText.getPassword());
    String encryptedPass = convertToBinary(passInput);
    String user = userText.getText();
    String password = new String(passText.getPassword());
    if (matchPassword(encryptedPass, window) == true) {
      errorMessageLogin.setText("Login successful.");
      System.out.println("Login successful");
      window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
      managerMainMenu();
    } else if (userText.getText().isEmpty() || passText.getText().isEmpty()) {
      errorMessageLogin.setText("Please input all username and password.");
    } else if (matchPassword(encryptedPass, window) == false) {
      errorMessageLogin.setText("Incorrect login credentials");
    }
  }

  // method that displays the screen for applying to adopt a pet-------------------
  public static void applyAdoptScreen() {
        window.getContentPane().removeAll();
    window.getContentPane().repaint();
    // sets up all input fields and labels
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(9, 1, 8, 8));
    topPanel.setBackground(Color.white);
    topPanel.setBounds(0, 0, 300, 300);
    JLabel nameLabel = new JLabel("Name (first last):");
    nameLabel.setBounds(8, 20, 80, 25);
    topPanel.add(nameLabel);
    nameTextAdopt = new JTextField(10);
    nameTextAdopt.setBounds(100, 20, 165, 25);
    topPanel.add(nameTextAdopt);
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setBounds(8, 20, 80, 25);
    topPanel.add(emailLabel);
    emailTextAdopt = new JTextField(10);
    emailTextAdopt.setBounds(100, 20, 165, 25);
    topPanel.add(emailTextAdopt);
    JLabel petIDLabel = new JLabel("ID of Pet:");
    petIDLabel.setBounds(8, 20, 80, 25);
    topPanel.add(petIDLabel);
    petIDTextAdopt = new JTextField(10);
    petIDTextAdopt.setBounds(100, 20, 165, 25);
    topPanel.add(petIDTextAdopt);
    JLabel applicationLabel = new JLabel("Your background:");
    applicationLabel.setBounds(8, 20, 80, 25);
    topPanel.add(applicationLabel);
    applicationTextAdopt = new JTextField(10);
    applicationTextAdopt.setBounds(100, 20, 165, 25);
    topPanel.add(applicationTextAdopt);
    JButton submit = new JButton("Submit");
    submit.setBounds(10, 80, 80, 25);
    topPanel.add(submit);
    JButton backButton = new JButton("Back to menu");
    backButton.setPreferredSize(new Dimension(100, 20));
    AListenerBackButton = new BackButtonListener(backButton);
    backButton.addActionListener(AListenerBackButton);
    topPanel.add(backButton);
    JLabel errLabel = new JLabel();
    String errTxt = "<html>Please fill out the form.</br></html>";
    errLabel.setText(errTxt);
    topPanel.add(errLabel);

    //links listener that will act when submit button is pressed
    AListenerAdopt = new AdoptListener(nameTextAdopt, emailTextAdopt, petIDTextAdopt, applicationTextAdopt, errLabel);
    submit.addActionListener(AListenerAdopt);

    window.getContentPane().add(topPanel);
    window.setVisible(true);
  }

  // method that displays the screen for applying to foster a pet-------------------
  public static void applyFosterScreen() {
    window.getContentPane().removeAll();
    window.getContentPane().repaint();
    // sets up all input fields and labels
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(9, 1, 8, 8));
    topPanel.setBackground(Color.white);
    topPanel.setBounds(0, 0, 300, 300);
    JLabel firstNameLabel = new JLabel("First name:");
    firstNameLabel.setBounds(8, 20, 80, 25);
    topPanel.add(firstNameLabel);
    firstNameTextFoster = new JTextField(10);
    firstNameTextFoster.setBounds(100, 20, 165, 25);
    topPanel.add(firstNameTextFoster);
    JLabel lastNameLabel = new JLabel("Last name:");
    lastNameLabel.setBounds(8, 20, 80, 25);
    topPanel.add(lastNameLabel);
    lastNameTextFoster = new JTextField(10);
    lastNameTextFoster.setBounds(100, 20, 165, 25);
    topPanel.add(lastNameTextFoster);
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setBounds(8, 20, 80, 25);
    topPanel.add(emailLabel);
    emailTextFoster = new JTextField(10);
    emailTextFoster.setBounds(100, 20, 165, 25);
    topPanel.add(emailTextFoster);
    JLabel phoneLabel = new JLabel("Phone # (no spaces):");
    phoneLabel.setBounds(8, 20, 80, 25);
    topPanel.add(phoneLabel);
    phoneTextFoster = new JTextField(10);
    phoneTextFoster.setBounds(100, 20, 165, 25);
    topPanel.add(phoneTextFoster);
    JLabel petIDLabel = new JLabel("ID of Pet:");
    petIDLabel.setBounds(8, 20, 80, 25);
    topPanel.add(petIDLabel);
    petIDTextFoster = new JTextField(10);
    petIDTextFoster.setBounds(100, 20, 165, 25);
    topPanel.add(petIDTextFoster);
    JLabel applicationLabel = new JLabel("Your background:");
    applicationLabel.setBounds(8, 20, 80, 25);
    topPanel.add(applicationLabel);
    applicationTextFoster = new JTextField(10);
    applicationTextFoster.setBounds(100, 20, 165, 25);
    topPanel.add(applicationTextFoster);
    JButton submit = new JButton("Submit");
    submit.setBounds(10, 80, 80, 25);
    topPanel.add(submit);
    JButton backButton = new JButton("Back to menu");
    backButton.setPreferredSize(new Dimension(100, 20));
    topPanel.add(backButton);
    AListenerBackButton = new BackButtonListener(backButton);
    backButton.addActionListener(AListenerBackButton);
    JLabel errLabel = new JLabel();
    String errTxt = "<html>Please fill out the form.</br></html>";
    errLabel.setText(errTxt);
    topPanel.add(errLabel);

    // links listener that will activate when submit button pressed
    AListenerFoster = new FosterListener(firstNameTextFoster, lastNameTextFoster, emailTextFoster, phoneTextFoster,
        petIDTextFoster, applicationTextFoster,
        errLabel);
    submit.addActionListener(AListenerFoster);

    window.getContentPane().add(topPanel);
    window.setVisible(true);
    window.validate();
  }

  // sets up screen that takes progress reports -------------------------------------
  public static void progressReportScreen() {
    window.getContentPane().removeAll();
    window.getContentPane().repaint();
    // sets up all input fields and labels
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new GridLayout(9, 1, 8, 8));
    topPanel.setBackground(Color.white);
    topPanel.setBounds(0, 0, 300, 300);
    JLabel IDLabel = new JLabel("Your Foster ID:");
    IDLabel.setBounds(8, 20, 80, 25);
    topPanel.add(IDLabel);
    IDTextProg = new JTextField(10);
    IDTextProg.setBounds(100, 20, 165, 25);
    topPanel.add(IDTextProg);
    JLabel reportLabel = new JLabel("Your report:");
    reportLabel.setBounds(8, 20, 80, 25);
    topPanel.add(reportLabel);
    reportText = new JTextField(10);
    reportText.setBounds(100, 20, 165, 25);
    topPanel.add(reportText);
    JButton submit = new JButton("Submit");
    submit.setBounds(10, 80, 80, 25);
    errorMessageReport = new JLabel();
    errorMessageReport.setText("Please fill all fields.");
    // links listener that will activate when submit button is pressed
    AListenerReport = new ProgressReportListener(errorMessageReport, fosterParents);
    submit.addActionListener(AListenerReport);
    topPanel.add(submit);
    JButton backButton = new JButton("Back to menu");
    backButton.setPreferredSize(new Dimension(100, 20));
    topPanel.add(backButton);
    topPanel.add(errorMessageReport);
    AListenerBackButton = new BackButtonListener(backButton);
    backButton.addActionListener(AListenerBackButton);
    window.getContentPane().add(topPanel);
    window.setVisible(true);
    window.validate();
  }

  // method that takes input from adoption application form fields and makes them into one string----------
  public static void adoptApplicationSubmit(String nameTextAdopt, String emailTextAdopt, String petIDTextAdopt,
      String applicationTextAdopt) {
    StringBuilder full = new StringBuilder();
    full.append(nameTextAdopt);
    full.append(" ");
    full.append(emailTextAdopt);
    full.append(" ");
    full.append(petIDTextAdopt);
    full.append(" ");
    full.append(applicationTextAdopt);
    activeAdoptApplications.add(full.toString()); //adds string to the active adoption applications arraylist
    return;
  }

  // method that takes input from foster application form fields and makes it into one string-----------------
  public static void fosterApplicationSubmit(String firstNameTextAdopt, String lastNameTextAdopt, String emailTextAdopt,
      String phoneTextAdopt, String petIDTextAdopt,
      String applicationTextAdopt) {
    // save application to system
    StringBuilder full = new StringBuilder();
    full.append(firstNameTextAdopt);
    full.append(" ");
    full.append(lastNameTextAdopt);
    full.append(" ");
    full.append(emailTextAdopt);
    full.append(" ");
    full.append(phoneTextAdopt);
    full.append(" ");
    full.append(petIDTextAdopt);
    full.append(" ");
    full.append(applicationTextAdopt);
    activeFosterApplications.add(full.toString()); //adding string to the active foster applications arraylist
    return;
  }

  // method called when manager wants to approve or deny adoption applications --------------
  public static void verifyAdoption(Scanner s) {
    // handle if arraylist is not filled
    if (activeAdoptApplications.size() == 0) {
      System.out.println("No adoption applications at this time.");
    } else { // ----------------------
      // loop through and print adoption requests
      int userInput = -1;
      for (int i = 0; i < activeAdoptApplications.size(); i++) {
        System.out.println("Application:");
        String currAdoptionString = activeAdoptApplications.get(i);
        String[] appTokened = currAdoptionString.split(" ");
        String appEmail = appTokened[2];
        System.out.println(currAdoptionString);
        System.out.println("Approve (1) or deny (2)?");
        // bad input handling
        userInput = Integer.parseInt(s.nextLine());
        while (userInput != 1 && userInput != 2) {
          System.out.println("Please type either 1 or 2, then press enter.");
          userInput = Integer.parseInt(s.nextLine());
        }
        // Manager menu option handling--------------
        if (userInput == 1) {
          pets = activeManager.approveAdoptionApplication(currAdoptionString, activeAdoptApplications, i, pets);
        } else if (userInput == 2) {
          activeManager.denyAdoptionApplication(i, appEmail);
        }
      }
      System.out.println("No more applications to display.");
    }
  }

  // method called when manager wants to approve or deny foster applications------------
  public static void verifyFoster(Scanner t) {
    if (activeFosterApplications.size() == 0) {
      System.out.println("No adoption applications at this time.");
      
    } else {
      // loop through and print foster requests
     int userInput = -1;
      for (int i = 0; i < activeFosterApplications.size(); i++) {
        System.out.println("Application:");
        String currFosterString = activeFosterApplications.get(i);
        String[] appTokened = currFosterString.split(" ");
        String appEmail = appTokened[2];
        System.out.println(currFosterString);
        System.out.println("Approve (1) or deny (2)?");
      // bad input handling
      userInput = Integer.parseInt(t.nextLine());
      while (userInput != 1 || userInput != 2) {
        System.out.println("Please type either 1 or 2, then press enter.");
        userInput = Integer.parseInt(t.nextLine());
        
        //Manager menu option handling
        if (userInput == 1) {
    fosterParents = activeManager.approveFosterApplication(currFosterString, activeFosterApplications, i, pets, fosterParents, appEmail);
        } else if (userInput == 2) {
          activeManager.denyFosterApplication(i, appEmail);
        }
      }
      System.out.println("No more applications to display.");
    }
    t.close();
    }
  }

  // method that presents manager with the main menu of options they can take after logging again-----------------
  public static void managerMainMenu() {
    Scanner s = new Scanner(System.in);
    int userInput = -1;
    while (userInput != 5) {
      System.out.println(
          "\nWelcome to the manager menu! Please select one of the following options by typing the number into the console and pressing Enter.\n");
      System.out.println("1. Approve or deny adoption requests.");
      System.out.println("2. Approve or deny foster requests.");
      System.out.println("3. Make a new manager account.");
      System.out.println("4. Run transaction file.");
      System.out.println("5. Log out and return to main window.\n");
      // bad input handling
      if (s.hasNextLine()) {
        try {
          userInput = Integer.parseInt(s.nextLine());
        } catch (NumberFormatException e) {
          System.out.println(
              "Not a valid number. Please input a valid number between 1 and 5.");
        }
      }
      while (userInput != 1 && userInput != 2 && userInput != 3 && userInput != 4 && userInput != 5) {
        if (s.hasNextLine()) {
          try {
            userInput = Integer.parseInt(s.nextLine());
            break;
          } catch (NumberFormatException e) {
            System.out.println("Not a valid number.");
          }
          System.out.println("Please input a valid number between 1 and 5.");
        }
      }
      // Manager menu option handling---------------
      if (userInput == 1) {
        verifyAdoption(s);
      } else if (userInput == 2) {
        verifyFoster(s);
      } else if (userInput == 3) {
        activeManager.makeNewManager(s);
      } else if (userInput == 4) {
        // run transaction file
        System.out.println("Processing transactions.");
        processTransaction();
        System.out.println("Transactions processed.");
      }
      if (userInput == 5) {
        System.out.println("Logging out.");
        // set activemanager to no one
        activeManager = new Manager();
        s.close();
        // open main menu window
        zeroScreen();
      }
    }
    s.close();

  }

  // Main method
  public static void main(String[] args) {

    window = new PreLoginGUI();

    // populates objects from text files
    populatePets();
    populateEmployees();
    populateFosterParents();
    window.setVisible(true);
    // calls first screen
    zeroScreen();

  }

}