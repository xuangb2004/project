package View;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
//import Controller.RoomController;
import Controller.AccountController;
import Controller.DetailRoomController;
import Controller.MainController;
import Controller.RoomCrudController;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author quoct
 */
public class MainHotelManagement {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Quản lý khách sạn");
        Login view = new Login();
        DashBoard dashBoard = new DashBoard();
        AccountController controller = new AccountController(view);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(1030,450);
        jFrame.getContentPane().add(view);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
