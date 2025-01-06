package Model;

import com.mysql.cj.xdevapi.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quoct
 */
public class EmployeeDAO {

    private JavaConnection javaConnection;
    private AccountDAO accountDAO;
    public EmployeeDAO() {
        javaConnection = new JavaConnection();
    }

    public List<Employee> getListNhanVien() {
        List<Employee> list = new ArrayList<>();
        Connection con = javaConnection.getConnection();
        String query = "Select * from employee where position != ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "Admin");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                String employeeId = res.getString("EmployeeID");
                String name = res.getString("name");
                int age = res.getInt("age");
                String gender = res.getString("gender");
                String numberPhone = res.getString("numberPhone");
                String position = res.getString("position");
                double salary = 0;
                if (getTimeKeeping(employeeId) > 0 && position.equals("Lễ Tân")) {
                    salary = res.getDouble("salary") * getTimeKeeping(employeeId);
                }
                if (!position.equals("Lễ Tân")) {
                    salary = res.getDouble("salary");
                }
                list.add(new Employee(employeeId, name, age, gender, numberPhone, position, salary));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public List<Employee> searchEmployee(String nameSearch) {
        List<Employee> list = new ArrayList<>();
        Connection con = javaConnection.getConnection();
        String query = "Select * from employee where name LIKE ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "%" + nameSearch + "%");
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                String employeeId = res.getString("EmployeeID");
                String name = res.getString("name");
                int age = res.getInt("age");
                String gender = res.getString("gender");
                String numberPhone = res.getString("numberPhone");
                String position = res.getString("position");
                double salary = res.getDouble("salary");
                list.add(new Employee(employeeId, name, age, gender, numberPhone, position, salary));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return list;
    }

    public double getSalary(String empID) {
        double salary = 0;
        Connection con = javaConnection.getConnection();
        String query = "Select salary from employee where EmployeeID = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, empID);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                salary = res.getDouble("salary");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            javaConnection.closeConnection(con);
        }
        return salary;
    }

    public boolean insertEmployee(Employee employee) {
        Connection con = javaConnection.getConnection();
        String query = "INSERT INTO employee (EmployeeID, name, age, gender, numberPhone, position, salary,Timekeeping) VALUES (?, ?, ?, ?, ?, ?, ?,0)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, employee.getEmployeeID());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getGender());
            preparedStatement.setString(5, employee.getNumberPhone());
            preparedStatement.setString(6, employee.getPosition());
            preparedStatement.setDouble(7, employee.getSalary());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            javaConnection.closeConnection(con);
        }
    }

    public boolean deleteEmployee(String empId) {
        Connection con = javaConnection.getConnection();
        String query = "Delete from employee where EmployeeID = ? ";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, empId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            javaConnection.closeConnection(con);
        }
    }

    public boolean updateEmployee(Employee e) {
        Connection con = javaConnection.getConnection();
        String query = "UPDATE employee SET name = ?, age = ?, gender = ?, numberPhone = ?, position = ?, salary = ? WHERE EmployeeID = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, e.getName());
            preparedStatement.setInt(2, e.getAge());
            preparedStatement.setString(3, e.getGender());
            preparedStatement.setString(4, e.getNumberPhone());
            preparedStatement.setString(5, e.getPosition());
            preparedStatement.setDouble(6, e.getSalary());

            preparedStatement.setString(7, e.getEmployeeID());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            javaConnection.closeConnection(con);
        }
    }

    public boolean updateTimeKeeping(String employeeID) {
        String query = "UPDATE employee SET Timekeeping = Timekeeping + 1 WHERE EmployeeID  = ?";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, employeeID);

            int updatedRows = statement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Cập nhật CountLogin thành công cho người dùng: " + employeeID);
                return true;
            } else {
                System.out.println("Không có người dùng có tên " + employeeID + " trong cơ sở dữ liệu.");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật CountLogin cho người dùng: " + employeeID, ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Lỗi khi đóng kết nối", ex);
                }
            }
        }
        return false;
    }

    public int getTimeKeeping(String emp) {
     String query = "Select Timekeeping as total FROM employee WHERE EmployeeID = ?";
        Connection con = javaConnection.getConnection();
        int time = 0;
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, emp);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                time = res.getInt("total");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật CountLogin cho người dùng:  ex");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Lỗi khi đóng kết nối", ex);
                }
            }
        }
        return time;
    }

    public void updateTimeKeepingReset() {
        String query = "UPDATE employee SET Timekeeping = ?  ";
        Connection con = javaConnection.getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, 0);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật CountLogin cho người dùng:  ex");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, "Lỗi khi đóng kết nối", ex);
                }
            }
        }
    }

}
