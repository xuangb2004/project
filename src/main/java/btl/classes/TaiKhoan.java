package btl.classes;

public class TaiKhoan {
  public enum Role {
    ADMIN, HOTEL, GUEST
  }
  private static int accountId;
  private static Role role;

  public static int getAccountId() {
    return accountId;
  }

  public static Role getRole() {
    return role;
  }

  public static void setUser(int accountId, int role) {
    TaiKhoan.accountId = accountId;
    switch (role) {
      case 0 -> TaiKhoan.role = Role.ADMIN;
      case 1 -> TaiKhoan.role = Role.HOTEL;
      case 2 -> TaiKhoan.role = Role.GUEST;
    }
  }
}