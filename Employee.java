import java.util.*;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

class Employee extends Person {

  // Data members
  private int id;
  static int idCount = 0;

  // Constructor
  public Employee(String n, String e, String p) {
    super(n, e, p);
    id = idCount;
    idCount += 1;
  }

  // Getters
  public int getID() {
    return id;
  }

  public int getIDCount() {
    return idCount;
  }

  // Setters
  public void setID(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    String info = this.getName() + " " + this.getEmail() + " " + this.getPhone() + " " + id;
    return info;
  }
  
  //method to delete an employee
  public static void deleteEmployee(Employee e) {
    String tempFile = "temp.txt";
    File oldFile = new File("employees.txt");
    File newFile = new File(tempFile);
    String line;

    try {
      FileWriter f = new FileWriter(tempFile, true);
      BufferedWriter b = new BufferedWriter(f);
      PrintWriter p = new PrintWriter(b);

      FileReader fr = new FileReader("employees.txt");
      BufferedReader br = new BufferedReader(fr);

      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        String empType = parts[0];
        String name = parts[1] + " " + parts[2];

        if (!name.equals(e.getName())) {
          p.println(line);
        }
      }
      p.flush();
      p.close();
      fr.close();
      br.close();
      f.close();
      b.close();

      oldFile.delete();
      File dump = new File("employees.txt");
      newFile.renameTo(dump);
    } catch (Exception x) {
      System.out.println("Error");
      x.printStackTrace();
    }

  }

  //method to add an employee to the system
  public static void addEmployee(Employee e) {
    try {
      FileWriter file = new FileWriter("employees.txt", true);
      PrintWriter pr = new PrintWriter(file);
      String newE = "E " + e.toString();
      pr.println(newE);
      pr.flush();
      pr.close();

    } catch (Exception c) {
      System.out.println("File not found");
      c.printStackTrace();
    }
  }

}