import java.util.*;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Pet {
  // Data members
  private String name;
  private int age;
  private String breed;
  private String medication;
  private String type;
  private int petID;
  private String isFostered;

  // Constructor
  public Pet(String n, int a, String t, String b, String m, int i, String isFostered) {
    name = n;
    age = a;
    type = t;
    breed = b;
    medication = m;
    petID = i;
    this.isFostered = isFostered;
  }

  // Getters and Setters
  public String getName() {
    return name;
  }

  public void setName(String n) {
    name = n;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int a) {
    age = a;
  }

  public String getBreed() {
    return breed;
  }

  public void setBreed(String b) {
    breed = b;
  }

  public String getMedication() {
    return medication;
  }

  public void setMedication(String m) {
    medication = m;
  }

  public String getType() {
    return type;
  }

  public void setType(String t) {
    type = t;
  }

  public int getPetID() {
    return petID;
  }

  public void setPetID(int p) {
    petID = p;
  }

  public String getFosterStatus(){
    return isFostered;
  }

  public void setFosterStatus(String isFostered){
    this.isFostered = isFostered;
  }

  public String toString() {
    return name + " " + age + " " + type + " " + breed
        + " " + medication + " " + petID;
  }

  //special toString method to display pets to users
  public String toStringPublic() {
    return "Name: " + name + "<br/>" + "Age: " + age + "<br/>" + "Breed: " + breed + "<br/>" + "PetId: " + petID
        + "<br/>";
  }

  //method that deletes a pet from system
  public void deletePet() {
    String tempFile = "temp.txt";
    File oldFile = new File("pets.txt");
    File newFile = new File(tempFile);
    String line;

    try {
      FileWriter f = new FileWriter(tempFile, true);
      BufferedWriter b = new BufferedWriter(f);
      PrintWriter p = new PrintWriter(b);

      FileReader fr = new FileReader("pets.txt");
      BufferedReader br = new BufferedReader(fr);

      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        String name = parts[0];

        if (!name.equals(this.getName())) {
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
      File dump = new File("pets.txt");
      newFile.renameTo(dump);
    } catch (Exception e) {
      System.out.println("Error");
      e.printStackTrace();
    }

  }

  //method to add a specific pet to system
  public static void addPet(Pet p) {
    // String line;
    try {
      FileWriter file = new FileWriter("pets.txt", true);
      // BufferedWriter b = new BufferedWriter(file);
      PrintWriter pr = new PrintWriter(file);
      // String[] petInfo = p.toString().split(" ");
      String newP = p.toString();
      pr.println(newP);
      pr.flush();
      pr.close();

    } catch (Exception e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
  }

}