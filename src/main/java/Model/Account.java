/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author quoct
 */
public class Account {
    private String username;
    private String password;
    private int countLogin;

    public Account(String username, String password, int countLogin) {
        this.username = username;
        this.password = password;
        this.countLogin = countLogin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getCountLogin() {
        return countLogin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountLogin(int countLogin) {
        this.countLogin = countLogin;
    }
    
}
