package btl.classes;

public class TaiKhoan {
  public enum Role {
    ADMIN, HOTEL, GUEST, UNKNOWN
  }

  private static int accountId = -1;
  private static Role role = Role.UNKNOWN;

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

  public static void logout() {
    TaiKhoan.accountId = -1;
    TaiKhoan.role = Role.UNKNOWN;
  }
}