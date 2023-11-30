import java.util.*;

public class Person {
  private String name;
  private String email;
  private String phone;

  // Constructor
  public Person(String n, String e, String p) {
    name = n;
    email = e;
    phone = p;
  }

  // Getters and Setters
  public String getName() {
    return name;
  }

  public void setName(String n) {
    name = n;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String e) {
    email = e;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String p) {
    phone = p;
  }

  public String toString() {
    return "Person [name:" + name + ", email:" + email + ", phone:" + phone + "]";
  }

}
