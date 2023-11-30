import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class FosterParent extends Person {

  // Data members
  private String ID;
  private Pet pet;
  private int petID;

  // Constructor
  public FosterParent(String n, String e, String p, String ID, int petID, Pet pet1) {
    super(n, e, p);
    this.ID = ID;
    this.petID = pet1.getPetID();
    this.pet = pet1;
  }

  // Getters
  public String getID(){
     return ID;
   }

  public Pet getPet(){
    return pet;
  }

  public int getPetID(){
    return petID;
  }

  // Setters
  public void setID(String i){
    ID = i;
  }

  public void setPet(Pet p){
    pet = p;
  }

  public void setPetID(int s){
    petID = s;
  }

  // toString method
@Override
 public String toString(){
  String info = "Foster parent name:" + this.getName() + " email: " + this.getEmail() + "phone number: " + this.getPhone();
  return info;
 }

  //method that enables foster parents to fill out foster form
  public String fillOutFosterForm(Pet pet){
    String name = this.getName();
    String phone = this.getPhone();
    return name + "." + phone + "." + pet.getName() + pet.getPetID();
  }


  //method that enables foster parents to submit progress report
  public void submitProgressReport(String s) {
      try{ 
        FileWriter fw = new FileWriter("progress.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
      bw.write(s);
      bw.close();
      fw.close();
    } catch (IOException e) {
      System.out.println("Error");
      e.printStackTrace();
    }
  }


}