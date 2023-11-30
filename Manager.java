import java.util.*;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

class Manager extends Employee {

  // Data members
  private String username;
  private String passcode;

  // Constructor
  public Manager(String n, String e, String p, String u, String c) {
    super(n, e, p);
    username = u;
    passcode = c;
  }

  // Constructor for when the Manager is logged out
  public Manager() {
    super("", "", "");
    username = "";
    passcode = "";
  }

  // Getters
  public String getUsername() {
    return username;
  }

  public String getPasscode() {
    return passcode;
  }

  // Setters
  public void setUsername(String username) {
    this.username = username;
  }

  public void setPasscode(String passcode) {
    this.passcode = passcode;
  }

  @Override
  public String toString() {
    String info = "Manager name:" + this.getName() + "email: " + this.getEmail() + "phone number: " + this.getPhone()
        + "id: " + this.getID();
    return info;
  }

  // method that adds approved adoption application to text file
  public ArrayList<Pet> approveAdoptionApplication(String s, ArrayList<String> list, int current, ArrayList<Pet> pets) {
    // add to txt file
    try {
      FileWriter fw = new FileWriter("adopters.txt", true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(s);
      bw.close();
      fw.close();
    } catch (IOException e) {
      System.out.println("Error");
      e.printStackTrace();
    }
    // remove from active adoption applications
    Main.activeAdoptApplications.remove(current);
    // remove pet from txt file by tokenizing and searching
    String[] token = s.split(" ");
    int petID = Integer.parseInt(token[3]);
    // System.out.println("pet token " + petID);
    for (int i = 0; i < pets.size(); i++) {
      if (pets.get(i).getPetID() == petID) {
        Pet foundPet = pets.get(i);
        pets.remove(foundPet); // remove pet from arraylist
        foundPet.deletePet(); // remove pet from txt file
      }
    }
    System.out.println("Approved.");
    Main.populatePets();
    return pets;
  }

  public void denyAdoptionApplication(int current, String email) {
    // remove from active applications
    Main.activeAdoptApplications.remove(current);
    // offer up email to email customer denial.
    System.out.println("The e-mail to contact this foster about their denial is " + email + ".");
    System.out.println("Denied.");
  }

  // method that handles approving a foster application
  public ArrayList<FosterParent> approveFosterApplication(String s, ArrayList<String> list, int current, ArrayList<Pet> pets,
      ArrayList<FosterParent> fosterParents, String email) {
    // create random foster id
    Random rand = new Random();
    int randomNum = rand.nextInt((10 - 1) + 1) + 1;
    String generatedString = new String("fosID" + randomNum + "n" + randomNum);
      //add foster parent to txt file
      String[] t = s.split(" ");
      String fn = t[0] + " " + t[1];
      String femail = t[2];
      String fphone = t[3];
      String fpID = t[4];
      String fID = generatedString;
    try {
      FileWriter fw = new FileWriter("fosters.txt", true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write("\n"  + fn + " " + femail + " " + fphone + " " + fID + " " + fpID);
      bw.close();
      fw.close();
    } catch (IOException e) {
      System.out.println("Error");
      e.printStackTrace();
    } 
    // remove from active foster applications
    Main.activeFosterApplications.remove(current);
    // update pets foster status
    String[] token = s.split(" ");
    int fpetID = Integer.parseInt(token[4]);
    System.out.println("pet token " + fpetID);
    Pet foundPet;
    for (int i = 0; i < pets.size(); i++) {
      if (pets.get(i).getPetID() == fpetID) {
        foundPet = pets.get(i);
        for(int j = 0; j < fosterParents.size(); j++){
      if(fosterParents.get(j).getEmail().equals(email)){
        fosterParents.get(j).setPetID(fpetID);
      }
      else{
        FosterParent newFoster = new FosterParent(fn,femail,fphone,fID,fpetID,foundPet);
        fosterParents.add(newFoster);
      }
    }
        String name = foundPet.getName();
        String age = String.valueOf(foundPet.getAge());
        String type = foundPet.getType();
        String breed = foundPet.getBreed();
        String med = foundPet.getMedication();
        String petID = String.valueOf(foundPet.getPetID());
        String fs = foundPet.getFosterStatus();
        String newFS = "F";
        String n = " ";
        String a = " "; 
        String nt = " ";
        String b = " ";
        String m = " ";
        String pID = " ";
        // if(fs.equals("NF")){
        //   foundPet.setFosterStatus("F");
          try{ 
          Scanner scanner;
          String temFile = "temp.txt";
          File petsFile = new File("pets.txt");
          File tempFile = new File("temp.txt");
          FileWriter fw = new FileWriter(tempFile, true);
          BufferedWriter bw = new BufferedWriter(fw);
          PrintWriter pw = new PrintWriter(bw);
          scanner = new Scanner(petsFile);
          scanner.useDelimiter(" ");
            while(scanner.hasNext()){
               n = scanner.next();
               a = scanner.next();
               nt = scanner.next();
               b = scanner.next();
               m = scanner.next();
               pID = scanner.next();
               newFS = scanner.next();
               pw.println(name + " " + age + " " + type + " " + breed + " " +  med + " " + petID + " " + newFS);
              }
                scanner.close();
                pw.flush();
                pw.close();
      }catch (IOException e) {
      System.out.println("Error");
      e.printStackTrace();
    } 
        }
      }
    System.out.println("Approved.");
    return fosterParents;
  }
  
  

  public void denyFosterApplication(int current, String email) {
    // remove from active applications
     Main.activeFosterApplications.remove(current);
    // offer up email to email them denial.
    System.out.println("The e-mail to contact this foster about their denial is " + email + ".");
    System.out.println("Denied.");
  }

  public void makeNewManager(Scanner s) {
    System.out.println(
        "Enter the manager's first name, last name, email, phone number, username, and password, all separated by commas.\nAn example: Ruby Goldberg goldb@gmail.com 4045155143 rgold ilikekittens\n");
    String userInput = s.nextLine();
    String[] tokens = userInput.split(" ");
    String plainPass = tokens[5];
    String encryPass = Main.convertToBinary(plainPass);
    String newManager = tokens[0] + " " + tokens[1] + " " + tokens[2] + " " + tokens[3] + " " + tokens[4] + encryPass;
    try {
      FileWriter file = new FileWriter("employees.txt", true);
      // BufferedWriter b = new BufferedWriter(file);
      PrintWriter pr = new PrintWriter(file);
      String newM = "M " + newManager;
      pr.println(newM);
      pr.flush();
      pr.close();

    } catch (Exception c) {
      System.out.println("File not found");
      c.printStackTrace();
    }

    Main.populateEmployees();
    System.out.println("\nThe new manager has been added. Save the password for your files.");
    return;
  }
}