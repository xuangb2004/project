/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Account;
import Model.AccountDAO;
import Model.EmployeeDAO;
import View.DashBoard;
import View.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author quoct
 */
public class AccountController {

    private Login view;
    private AccountDAO accountData;
    private DashBoard dashBoard;
    private EmployeeDAO data;
    public AccountController(Login view) {
        this.view = view;
        accountData = new AccountDAO();
        dashBoard = new DashBoard();
        data = new EmployeeDAO();
        view.clickLoginButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = view.getTextLogin();
                if (accountData.loginAccount(account.getUsername(), account.getPassword())) {
                    JOptionPane.showMessageDialog(view, "Đăng nhập thành công", "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
                    String name = accountData.getNameEmployee(account.getUsername());
                    String us = account.getUsername();
                    closeFrame();
                    showDashboard(name, us);

                } else {
                    JOptionPane.showMessageDialog(view, "Đăng nhập thất bại", "Thông Báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        view.clickButtonTimeKepper(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String input = JOptionPane.showInputDialog(view,"Nhập mã nhân viên của bạn","Thông báo",JOptionPane.INFORMATION_MESSAGE);
              if(data.updateTimeKeeping(input)){
                    JOptionPane.showMessageDialog(view, "Chấm công thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
              }
              else{
                   JOptionPane.showMessageDialog(view, "Không tồn tại trong cơ sở dữ liệu","Thông báo",JOptionPane.WARNING_MESSAGE);
              }
             
            }
        });
    }

    private void showDashboard(String username, String us) {
        JFrame dashboardFrame = new JFrame("Dashboard");
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainController mainController = new MainController(dashBoard);
        dashBoard.setTextEmployee(username);
        dashBoard.setNameUser(us);
        dashboardFrame.getContentPane().add(dashBoard);
        dashboardFrame.pack();
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setVisible(true);
    }

    public void closeFrame() {
        JFrame frame = (JFrame) view.getRootPane().getParent();
        frame.dispose();
    }
}
