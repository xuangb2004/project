/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quoct
 */
public class Customer {
    private int customerId;
    private String name;
    private String CCCD;
    private String phoneNumber;
    private String address;
    private String gender;
    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public Customer(int customerId, String name, String CCCD, String phoneNumber, String address, String gender, int age) {
        this.customerId = customerId;
        this.name = name;
        this.CCCD = CCCD;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.age = age;
    }
    public Customer() {
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Customer(int customerId, String name, String CCCD, String phoneNumber, String address, String gender) {
        this.customerId = customerId;
        this.name = name;
        this.CCCD = CCCD;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
    }
 public Customer( String name, String CCCD, String phoneNumber, String address, String gender,int age) {
        this.name = name;
        this.CCCD = CCCD;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.age = age;
    }
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getCCCD() {
        return CCCD;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

   
}
