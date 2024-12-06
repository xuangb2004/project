package btl.project;

public class User {

  public enum Role {
    ADMIN, HOTEL, GUEST
  }

  public static Role role;
  public String username;
  public String password;

  public class Admin extends User {
    Admin() {
      role = Role.ADMIN;
      username = "admin";
      password = "admin";
    }
  }

  public class Hotel extends User {
    Hotel() {
      role = Role.HOTEL;
      username = "hotel";
      password = "hotel";
    }
  }

  public class Guest extends User {
    Guest() {
      role = Role.GUEST;
      username = "guest";
      password = "guest";
    }
  }
}
