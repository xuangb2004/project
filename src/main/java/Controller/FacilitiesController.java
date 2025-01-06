/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Employee;
import Model.Facilities;
import Model.FacilitiesDAO;
import Model.RoomDao;
import View.AddFacilititesToRoom;
import View.FacilitiesView;
import View.InformationFacilitiesView;
import View.TableFacilities;
import View.UpdateEmployee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author quoct
 */
public class FacilitiesController {

    private FacilitiesView view;
    private FacilitiesDAO data;
    private TableFacilities model;
    private InformationFacilitiesView facilitiesView;
    private AddFacilititesToRoom addtoRoom;
    private RoomDao roomDao;
    public FacilitiesController(FacilitiesView view) {
        this.view = view;
        roomDao = new RoomDao();
        model = new TableFacilities();
        data = new FacilitiesDAO();
        setTable();
        view.clickInTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    view.showPopup(e);
                }
            }
        });
        view.informationClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facilitiesView = new InformationFacilitiesView();
                showInforFacilities(facilitiesView);
                Facilities getFacilities = setTableItemSelected();
                List<String> roomId = data.getRoom(getFacilities.getId());
                String room = "";
                for(String str : roomId){
                    room += str  + " ";
                }
                System.out.println(room);
                facilitiesView.setAllText(getFacilities, room);

            }
        });
        view.addFacilitiesClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               addtoRoom = new AddFacilititesToRoom();
               showAddFacilitiesToRoom(addtoRoom);
               List<String> listRoom = roomDao.getListRoomToFacilities();
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                model.addElement(listRoom);
                addtoRoom.setComboBoxRoom(model);
            }
        });
    }

    public void setTable() {
        List<Facilities> list = data.getAllFacilities();
        model.setData(list);
        view.setTable(model);
    }

    public void showInforFacilities(InformationFacilitiesView facilitiesView) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(550, 450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Thông tin chi tiết thiết bị");
        jFrame.getContentPane().add(facilitiesView);
        jFrame.setVisible(true);
    }
      public void showAddFacilitiesToRoom(AddFacilititesToRoom add) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(312, 312);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Thêm thiết bị cho phòng");
        jFrame.getContentPane().add(add);
        jFrame.setVisible(true);
    }

    public Facilities setTableItemSelected() {
        int rowSelected = view.getRowSelected();
        int id = (int) model.getValueAt(rowSelected, 0);
        String name = (String) model.getValueAt(rowSelected, 1);
        double price = (double) model.getValueAt(rowSelected, 4);
        int quantity = (int) model.getValueAt(rowSelected, 2);
        Date date_buy = (Date) model.getValueAt(rowSelected, 3);
        String status = (String) model.getValueAt(rowSelected, 5);
        Facilities facilities = new Facilities(id, name, price, quantity, date_buy, status);
        return facilities;
    }
}
