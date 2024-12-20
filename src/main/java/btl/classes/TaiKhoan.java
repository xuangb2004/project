package btl.classes;

public class TaiKhoan {
  public enum Role {
    ADMIN, HOTEL, GUEST
  }

  Role role;
  String user, password;
}