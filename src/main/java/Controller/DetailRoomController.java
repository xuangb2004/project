/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Booking;
import Model.BookingDao;
import Model.Customer;
import Model.CustomerDao;
import Model.Room;
import Model.Service;
import Model.ServiceDAO;
import Model.ServiceInVoiceDAO;
import Model.ServiceInvoice;
import View.DetailRoom;
import View.RoomBooking;
import View.ServiceItem;
import View.TableModelInvoiceService;
import View.TableRoomBooking;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author quoct
 */
public class DetailRoomController {

    private EventClick clicked;
    private DetailRoom detailRoom;
    private ServiceDAO data;
    private TableModelInvoiceService table;
    private ServiceInVoiceDAO dataServiceInvoice;
    private TableRoomBooking tableRoom;
    private BookingDao bookingData;
    private CustomerDao customerData;
    private DefaultComboBoxModel typeService;

    public DetailRoomController(DetailRoom detailRoom) {
        this.detailRoom = detailRoom;
        this.data = new ServiceDAO();
        this.bookingData = new BookingDao();
        this.dataServiceInvoice = new ServiceInVoiceDAO();
        tableRoom = new TableRoomBooking();
        table = new TableModelInvoiceService();
        customerData = new CustomerDao();
        typeService = new DefaultComboBoxModel();
        getDataService();
        setTableRoom();
        showTable();
        setCustomDetail();
        setPriceService();
        tableService();
        setTypeDescription();
        searchTextChange();
        comboBoxSelected();
    }

    public void getDataService() {
        List<Service> list = data.getAllService();
        for (Service service : list) {
            ServiceItem item = new ServiceItem();
            item.setData(service);
            detailRoom.setLayoutScrollView(item);

            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    if (SwingUtilities.isLeftMouseButton(me)) {
                        String title = "Bạn có muốn thêm món ăn: " + service.getNameService();
                        int index = 1;
                        int result = JOptionPane.showConfirmDialog(item, title, "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            int idRoom = getSelectedIdRoom();
                            if (dataServiceInvoice.checkExitsInVoice(service.getServiceID(), idRoom) > 0) {
                                if (checkQuantityInService(service)) {
                                    dataServiceInvoice.updateQuantityServiceInvoice(service.getServiceID(), idRoom, service.getPrice());
                                    updateQuantityService(service.getNameService(), 1);
  
                                }
                            } else {
                                if (checkQuantityInService(service)) {
                                    ServiceInvoice serviceInvoice = new ServiceInvoice(service.getServiceID(), idRoom, 1, service.getPrice());
                                    dataServiceInvoice.insertServiceInvoice(serviceInvoice);
                                    updateQuantityService(service.getNameService(), 1);
                                }

                            }
                            table.setData(dataServiceInvoice.getServiceRoomByRoom(idRoom));
                            setPriceService();
                        } else if (result == JOptionPane.NO_OPTION) {

                        }
                    }
                }
            });
        }
        detailRoom.revalidate();
        detailRoom.repaint();
    }

    public void tableService() {
        detailRoom.jMenuDeleteClick(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceInvoice service = detailRoom.fillTextClickTable();
                dataServiceInvoice.delteSerivceInvoice(service);
                int idRoom = getSelectedIdRoom();
                table.setData(dataServiceInvoice.getServiceRoomByRoom(idRoom));
                data.updatePlusQuantity(service.getServiceID(), service.getQuantity());
                setPriceService();
            }
        }
        );

    }

    public void showTable() {
        int idRoom = (int) tableRoom.getValueAt(0, 1);
        List<ServiceInvoice> list = dataServiceInvoice.getServiceRoomByRoom(idRoom);
        table.setData(list);
        detailRoom.setTable(table);
    }

    public void setTableRoom() {
        List<Booking> list = bookingData.getAllBookingsConditions(detailRoom.getIdRoom());

        tableRoom.setData(list);
        detailRoom.setInformationBooking(tableRoom);
    }

    public int getSelectedIdRoom() {
        int idRoom = (int) tableRoom.getValueAt(0, 1);
        return idRoom;
    }

    public void setCustomDetail() {
        String idCustomerString = (String) tableRoom.getValueAt(0, 2);
        int idCustomer = Integer.parseInt(idCustomerString);
        List<Customer> listCustomer = customerData.getInformationCustomer(idCustomer);
        String nameCustomer = listCustomer.get(0).getName();
        LocalDateTime dateCheckOut = (LocalDateTime) tableRoom.getValueAt(0, 4);
        LocalDateTime dateCheckIn = (LocalDateTime) tableRoom.getValueAt(0, 3);
        Duration duration = Duration.between(dateCheckIn, dateCheckOut);
        long differenceInDays = duration.toDays();
        Double price = (Double) tableRoom.getValueAt(0, 5);
        int rental = (Integer) tableRoom.getValueAt(0, 8);
        if (differenceInDays == 0) {
            detailRoom.setPriceRoom("Thuê theo giờ");
            detailRoom.setAllTextInCustomer(nameCustomer, dateCheckOut, dateCheckIn, String.valueOf(price));
        } else {
            detailRoom.setPriceRoom(String.valueOf((differenceInDays * price) - rental));
        }
        detailRoom.setAllTextInCustomer(nameCustomer, dateCheckOut, dateCheckIn, String.valueOf(price));
    }

    public void setPriceService() {
        int row = table.getRowCount();
        double totalServicePrice = 0;
        while (row > 0) {
            totalServicePrice += (double) table.getValueAt(row - 1, 4);
            row--;
        }

        detailRoom.setPriceService(String.valueOf(totalServicePrice));
    }

    public void setTypeDescription() {
        List<String> typeServiceList = new ArrayList<>();
        typeServiceList = data.serviceType();
        typeService.addElement("--");
        for (String service : typeServiceList) {
            typeService.addElement(service);

        }

        detailRoom.setComboBoxTypeService(typeService);
    }

    public void searchTextChange() {
        detailRoom.jTextFieldSearchTextChange(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    public void comboBoxSelected() {
        detailRoom.comboBoxItemSelected(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<Service> list;
                String selectedValue = detailRoom.getItemSelected();
                if (selectedValue.equals("Đồ nước")) {
                    selectedValue = "Phở";
                } else if (selectedValue.equals("Đồ uống")) {
                    selectedValue = "Nước";
                }
                list = data.serviceSearch(selectedValue);
                if (selectedValue.equals("--")) {
                    list = data.getAllService();
                }
                detailRoom.clearScrollView();
                comboBoxSelectedSearch(list);
            }
        });
    }

    private void handleTextChange() {
        String newText = detailRoom.getTextSearch();
        List<Service> list = data.serviceSearch(newText);
        detailRoom.clearScrollView();
        for (Service service : list) {
            ServiceItem item = new ServiceItem();
            item.setData(service);
            detailRoom.setLayoutScrollView(item);
        }
        detailRoom.revalidate();
        detailRoom.repaint();
    }

    public void comboBoxSelectedSearch(List<Service> list) {
        for (Service service : list) {
            ServiceItem item = new ServiceItem();
            item.setData(service);
            detailRoom.setLayoutScrollView(item);

            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    if (SwingUtilities.isLeftMouseButton(me)) {
                        String title = "Bạn có muốn thêm món ăn: " + service.getNameService();
                        int result = JOptionPane.showConfirmDialog(item, title, "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            int idRoom = getSelectedIdRoom();
                             if (dataServiceInvoice.checkExitsInVoice(service.getServiceID(), idRoom) > 0) {
                                if (checkQuantityInService(service)) {
                                    dataServiceInvoice.updateQuantityServiceInvoice(service.getServiceID(), idRoom, service.getPrice());
                                    updateQuantityService(service.getNameService(), 1);
  
                                }
                            } else {
                                if (checkQuantityInService(service)) {
                                    ServiceInvoice serviceInvoice = new ServiceInvoice(service.getServiceID(), idRoom, 1, service.getPrice());
                                    dataServiceInvoice.insertServiceInvoice(serviceInvoice);
                                    updateQuantityService(service.getNameService(), 1);
                                }

                            }
                            table.setData(dataServiceInvoice.getServiceRoomByRoom(idRoom));

                            setPriceService();
                        } else if (result == JOptionPane.NO_OPTION) {

                        }
                    }
                }
            });
        }
        detailRoom.revalidate();
        detailRoom.repaint();
    }

    public void updateQuantityService(String text, int quantity) {
        data.updateQuantity(text, quantity);
    }

    public boolean checkQuantityInService(Service service) {
        if (data.getQuantityInService(service.getServiceID()) <= 0) {
            JOptionPane.showMessageDialog(detailRoom, "Món ăn đã hết vui vòng liên hệ bộ phận nhà bếp để cập nhật", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

}
