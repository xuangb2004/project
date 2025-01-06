/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerDao {

    private JavaConnection javaConnection;

    public CustomerDao() {
        javaConnection = new JavaConnection();
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = javaConnection.getConnection()) {
            String sql = "SELECT * FROM customer";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customerId"));
                    customer.setName(resultSet.getString("name"));
                    customer.setCCCD(resultSet.getString("CCCD"));
                    customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setGender(resultSet.getString("gender"));
                     customer.setAge(resultSet.getInt("age"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }

    public void insertCustomer(Customer customer) {
        try (Connection connection = javaConnection.getConnection()) {
            String sql = "INSERT INTO customer (name, CCCD, phoneNumber, address, gender,age) VALUES (?, ?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getCCCD());
                preparedStatement.setString(3, customer.getPhoneNumber());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.setString(5, customer.getGender());
                   preparedStatement.setInt(6, customer.getAge());
                preparedStatement.executeUpdate();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCustomerId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Failed to get auto-generated keys, no customerId obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void updateCustomer(Customer customer) {
        try (Connection connection = javaConnection.getConnection()) {
            String sql = "UPDATE customer SET name=?, CCCD=?, phoneNumber=?, address=?, gender=?, age = ? WHERE customerId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getCCCD());
                preparedStatement.setString(3, customer.getPhoneNumber());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.setString(5, customer.getGender());
                preparedStatement.setInt(6, customer.getAge());
                preparedStatement.setInt(7, customer.getCustomerId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        try (Connection connection = javaConnection.getConnection()) {
            String sql = "DELETE FROM customer WHERE customerId=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, customerId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCCCDCustomer(String cccd) {

        Connection con = javaConnection.getConnection();
        String query = "Select CustomerId as cccd  from Customer where CCCD = ? ";
        int customerId = 1;
        PreparedStatement statement;
        try {
            statement = con.prepareStatement(query);
            statement.setString(1, cccd);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                customerId = resultSet.getInt("cccd");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customerId;
    }

    public List<Customer> getInformationCustomer(int customerID) {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = javaConnection.getConnection()) {
            String sql = "SELECT * FROM customer WHERE CustomerId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, customerID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Customer customer = new Customer();
                        customer.setCustomerId(resultSet.getInt("customerId"));
                        customer.setName(resultSet.getString("name"));
                        customer.setCCCD(resultSet.getString("CCCD"));
                        customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                        customer.setAddress(resultSet.getString("address"));
                        customer.setGender(resultSet.getString("gender"));
                            customer.setAge(resultSet.getInt("age"));
                        customers.add(customer);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public List<Customer> getInformationCustomerByName(String name) {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = javaConnection.getConnection()) {
            String sql = "SELECT DISTINCT * FROM customer WHERE name LIKE  ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + name + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Customer customer = new Customer();
                        customer.setCustomerId(resultSet.getInt("customerId"));
                        customer.setName(resultSet.getString("name"));
                        customer.setCCCD(resultSet.getString("CCCD"));
                        customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                        customer.setAddress(resultSet.getString("address"));
                        customer.setGender(resultSet.getString("gender"));
                            customer.setAge(resultSet.getInt("age"));
                        customers.add(customer);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public List<Customer> getAllCustomersWithUniquePhoneNumbers() {
    List<Customer> customers = new ArrayList<>();
    try (Connection connection = javaConnection.getConnection()) {
        String sql = "SELECT DISTINCT * FROM customer";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setName(resultSet.getString("name"));
                customer.setCCCD(resultSet.getString("CCCD"));
                customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                customer.setAddress(resultSet.getString("address"));
                customer.setGender(resultSet.getString("gender"));
                customer.setAge(resultSet.getInt("age"));
                customers.add(customer);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customers;
}
  public List<Customer> getAllCustomerBookingInRealTime() {
    List<Customer> customers = new ArrayList<>();
    try (Connection connection = javaConnection.getConnection()) {
        String sql = "SELECT DISTINCT ctm.customerId, ctm.name, ctm.CCCD, ctm.phoneNumber, ctm.address, ctm.gender, ctm.age " +
                     "FROM customer AS ctm " +
                     "JOIN booking AS bk ON ctm.customerId = bk.customerId " +
                     "WHERE bk.status = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Chưa thanh toán");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customerId"));
                    customer.setName(resultSet.getString("name"));
                    customer.setCCCD(resultSet.getString("CCCD"));
                    customer.setPhoneNumber(resultSet.getString("phoneNumber"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setGender(resultSet.getString("gender"));
                    customer.setAge(resultSet.getInt("age"));
                    customers.add(customer);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customers;
}


}
