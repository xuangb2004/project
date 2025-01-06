/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.AccountDAO;
import Model.BillDAO;
import Model.Booking;
import Model.BookingDao;
import Model.Customer;
import Model.CustomerDao;
import Model.Employee;
import Model.EmployeeDAO;
import Model.FacilitiesDAO;
import Model.InvoiceHotel;
import Model.Room;
import Model.RoomDao;
import Model.ServiceInVoiceDAO;
import Model.ServiceInvoice;
import View.AddEmployee;
import View.AddFacilititesToRoom;
import View.BillView;
import View.CustomerView;
import View.DetailRoom;
import View.ItemPanel;
import View.DashBoard;
import View.EmployeeView;
import View.FacilitiesView;
import View.InvoiceHotelView;
import View.Login;
import View.RevenueInformation;
import View.RoomManagement;
import View.RoomBooking;
import View.TableModelInvoiceService;
import View.TableModelRoom;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author quoct
 */
public class MainController {

    private DashBoard layoutKhachSan;
    private RoomDao data;
    private TableModelRoom model;
    private RoomManagement view;
    private DefaultComboBoxModel<String> modelComboBox1;
    private BookingDao bookingDao;
    private ServiceInVoiceDAO serviceInvoiceDao;
    private CustomerDao customerDao;
    private TableModelInvoiceService modelInvoice;
    private BillView viewBill;
    private AccountDAO accountDAO;
    private BillDAO dataBill;
    private EmployeeView managementEmployee;
    private AddEmployee addEmployee;
    private EmployeeDAO employeeDAO;
    private EmployyeContronler employyeContronler;
    private Login login;
    private CustomerView customerView;
    private FacilitiesView facilitiesView;
    private AddFacilititesToRoom addFacilititesToRoom;
    private FacilitiesDAO dataFacilities;

    public MainController(DashBoard layoutKhachSan) {
        this.layoutKhachSan = layoutKhachSan;
        this.data = new RoomDao();
        modelInvoice = new TableModelInvoiceService();
        this.customerDao = new CustomerDao();
        this.bookingDao = new BookingDao();
        this.accountDAO = new AccountDAO();
        this.dataBill = new BillDAO();
        this.employeeDAO = new EmployeeDAO();
        serviceInvoiceDao = new ServiceInVoiceDAO();
        dataFacilities = new FacilitiesDAO();

        getDataRoom();
        layoutKhachSan.clickRoomManagement(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomManagement list = new RoomManagement();
                showRoom(list);

            }
        });
        layoutKhachSan.addClickRevenueChart(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChartRoom();
            }
        });
        layoutKhachSan.addClickLogout(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login = new Login();
                AccountController accountController = new AccountController(login);
                JFrame frame = (JFrame) layoutKhachSan.getRootPane().getParent();
                frame.dispose();
                JFrame jFrame = new JFrame("Đăng nhập");
                jFrame.setSize(1030, 450);
                jFrame.getContentPane().add(login);
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jFrame.setVisible(true);

            }
        });
        layoutKhachSan.addClickManagementEmployee(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managementEmployee = new EmployeeView();
                showEmployeeManagement(managementEmployee);
                employyeContronler = new EmployyeContronler(managementEmployee);
                LocalDate currentDate = LocalDate.now();
                int dayOfMonth = currentDate.getDayOfMonth();
                if (dayOfMonth == 5) {
                    employeeDAO.updateTimeKeepingReset();
                }
            }
        });
        layoutKhachSan.addClickFacilities(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facilitiesView = new FacilitiesView();
                FacilitiesController controller = new FacilitiesController(facilitiesView);
                JFrame jFrame = new JFrame("Quản lý thiết bị");
                jFrame.setSize(575, 495);
                jFrame.getContentPane().add(facilitiesView);
                jFrame.setVisible(true);
                JMenuBar menuBar = new JMenuBar();
                JMenuItem item = new JMenuItem("Thêm thiết bị vào phòng");
                menuBar.add(item);
                jFrame.setJMenuBar(menuBar);
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addFacilititesToRoom = new AddFacilititesToRoom();
                        showAddFacilitiesToRoom(addFacilititesToRoom);
                        List<String> listRoom = data.getListRoomToFacilities();
                        DefaultComboBoxModel modelRoomComboBox = new DefaultComboBoxModel();
                        for (String x : listRoom) {
                            modelRoomComboBox.addElement(x);
                        }
                        addFacilititesToRoom.setComboBoxRoom(modelRoomComboBox);
                        DefaultComboBoxModel modelNameFacilities = new DefaultComboBoxModel();
                        List<String> listNameFacilities = dataFacilities.getNameFacilities();
                        for (String str : listNameFacilities) {
                            modelNameFacilities.addElement(str);
                        }
                        addFacilititesToRoom.setComboBoxIdFacilities(modelNameFacilities);
                        addFacilititesToRoom.clickAddFacilitiesToRoom(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String selectedRoomItem = modelRoomComboBox.getSelectedItem().toString();
                                int roomId = Integer.parseInt(selectedRoomItem);
                                String nameFacilities = (String) modelNameFacilities.getSelectedItem();
                                int idFacilities = -1;
                                if (nameFacilities.equals("Tivi Lg")) {
                                    idFacilities = 4;
                                } else if (nameFacilities.equals("Ghế sofa")) {
                                    idFacilities = 6;
                                } else {
                                    idFacilities = 5;
                                }
                                if (!dataFacilities.checkExits(roomId, idFacilities)) {

                                    if (dataFacilities.addFacilities(roomId, idFacilities)) {
                                        JOptionPane.showMessageDialog(addFacilititesToRoom, "Thêm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(addFacilititesToRoom, "Phòng đã có sẵn thiết bị này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        });
                    }
                });
            }
        });
        layoutKhachSan.addClickCustomerManagement(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerView = new CustomerView();
                CustomerController controller = new CustomerController(customerView);
                JFrame jFrame = new JFrame("Quản lý khách hàng");
                jFrame.setSize(580, 580);
                jFrame.getContentPane().add(customerView);
                jFrame.setVisible(true);
                JMenuBar menuBar = new JMenuBar();
                JMenu fileMenu = new JMenu("Thao tác");
                JMenuItem newItem = new JMenuItem("Danh sách khách hàng đang ở trong khách sạn");
                JMenuItem newItem2 = new JMenuItem("Danh sách khách hàng ");
                fileMenu.add(newItem2);
                fileMenu.add(newItem);
                newItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.setTableBookingInHotel();

                    }
                });
                newItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.setTable();

                    }
                });
                menuBar.add(fileMenu);
                jFrame.setJMenuBar(menuBar);
            }
        });
        getLabelRoomCount();

    }

    public void getDataRoom() {
        List<Room> list = data.getListRoom();
        for (Room room : list) {
            ItemPanel item = new ItemPanel();
            item.setData(room);
            if (room.getLocation().equals("Tầng 2")) {
                layoutKhachSan.setLayoutScrollViewLocationTwo(item);
            } else {
                layoutKhachSan.setLayoutScrollView(item);
            }
            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        item.handleMouseClicked(e);
                    }
                }
            });
            item.actionPerformed(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (room.getStatus().equals("Bảo trì")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang bảo trì ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else if (room.getStatus().equals("Trống")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang trống ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        DetailRoom detail = new DetailRoom();
                        detail.setIdRoom(room.getIdRoom());
                        showDetailRoom(detail);
                        String nameRoom = room.getNameRoom();
                        detail.setRoom(nameRoom);

                    }

                }

            });
            item.jMenuAddClick(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (room.getStatus().equals("Bảo trì")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang bảo trì ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else if (room.getStatus().equals("Có người ở")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang có người ở ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        RoomBooking booking = new RoomBooking();
                        showBookingRoom(booking);
                        booking.setText("Phòng " + room.getNameRoom());
                        booking.setPrice(room.getPrice());
                        booking.setRoomId(room.getIdRoom());
                    }

                }
            }
            );
            item.actionClickMenuFixed(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int roomId = room.getIdRoom();
                    if (room.getStatus().equals("Có người ở")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang có người ở ", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    } else {
                        data.setStatus(roomId, "Bảo trì");
                        layoutKhachSan.clearScrollView();
                        getDataRoom();
                        getLabelRoomCount();
                    }

                }
            });
            item.actionClickMenuFinish(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int roomId = room.getIdRoom();
                    if (!room.getStatus().equals("Bảo trì")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng có bảo trì đâu?", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    } else {
                        data.setStatus(roomId, "Trống");
                        layoutKhachSan.clearScrollView();
                        getDataRoom();
                        getLabelRoomCount();
                    }
                }
            });
            item.actionClickMenuCheckOut(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int roomId = room.getIdRoom();
                    String roomStatus = room.getStatus();
                    if (roomStatus.equals("Có người ở")) {
                        showBillForCheckOut(room);
                    } else {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang trống?", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            item.actionClickMenuChangeRoom(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (room.getStatus().equals("Trống")) {
                        JOptionPane.showMessageDialog(layoutKhachSan, "Phòng trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int roomId = room.getIdRoom();
                        String roomIdChange = JOptionPane.showInputDialog(layoutKhachSan, "Chọn phòng muốn chuyển", "Chuyển phòng", JOptionPane.INFORMATION_MESSAGE);
                        double priceRoomOld = data.getPrice(roomId);
                        double priceRoomNew = data.getPrice(Integer.valueOf(roomIdChange));
                        System.out.println(priceRoomNew);
                        if (!data.getStatus(Integer.valueOf(roomIdChange)).equals("Trống")) {
                            JOptionPane.showMessageDialog(layoutKhachSan, "Phòng đang bảo trì hoặc có người ở", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else if (!String.valueOf(priceRoomOld).equals(String.valueOf(priceRoomNew))) {
                            bookingDao.changeVipRoom(roomId, Integer.valueOf(roomIdChange), priceRoomNew);
                            JOptionPane.showMessageDialog(layoutKhachSan, "Chuyển phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            data.setStatus(roomId, "Trống");
                            data.setStatus(Integer.valueOf(roomIdChange), "Có người ở");
                            setStatusInDashBoard();
                            serviceInvoiceDao.updateServiceChangeRoom(roomId, Integer.valueOf(roomIdChange));

                        } else if (bookingDao.changeRoom(roomId, Integer.valueOf(roomIdChange))) {
                            JOptionPane.showMessageDialog(layoutKhachSan, "Chuyển phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            data.setStatus(roomId, "Trống");
                            data.setStatus(Integer.valueOf(roomIdChange), "Có người ở");
                            setStatusInDashBoard();
                            serviceInvoiceDao.updateServiceChangeRoom(roomId, Integer.valueOf(roomIdChange));
                        } else {
                            JOptionPane.showMessageDialog(layoutKhachSan, "Phòng không tồn tại trong hệ thống", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }
                }
            });

            layoutKhachSan.revalidate();
            layoutKhachSan.repaint();

        }
    }

    public void showRoom(RoomManagement list) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(953, 635);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Thông tin các phòng có trong khách sạn");
        jFrame.getContentPane().add(list);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        RoomCrudController controller = new RoomCrudController(list);
        controller.showTable();
        // giao tiếp giữa 2 frame dùng interface
        controller.setRoomAddedListener(new RoomAddedListener() {
            @Override
            public void roomAdded() {
                setStatusInDashBoard();
            }

            @Override
            public void roomAddedTwoFloor() {
                setStatusInDashBoard();
            }

            @Override
            public void roomRemove() {
                setStatusInDashBoard();
            }
        });

    }

    public void showDetailRoom(DetailRoom detail) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(1000, 700);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Chi tiết đặt phòng");
        jFrame.getContentPane().add(detail);
        jFrame.setVisible(true);
        DetailRoomController controller = new DetailRoomController(detail);
        controller.showTable();

    }

    public void showEmployeeManagement(EmployeeView employeeView) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Quản lý nhân viên");
        jFrame.getContentPane().add(employeeView);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Thao tác");
        JMenuItem newItem = new JMenuItem("Thêm nhân viên");
        fileMenu.add(newItem);
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee = new AddEmployee();
                showAddEmployee(addEmployee);
                addEmployee.addEmployee(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Employee employee = addEmployee.getTextAll();
                        if (employeeDAO.insertEmployee(employee)) {
                            JOptionPane.showMessageDialog(addEmployee, "Thêm thành công nhân viên" + employee.getName(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            employyeContronler.refreshTable();
                        } else {
                            JOptionPane.showMessageDialog(addEmployee, "Đã tồn tại nhân viên với mã " + employee.getEmployeeID(), "Thông báo", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });
            }
        });
        menuBar.add(fileMenu);
        jFrame.setJMenuBar(menuBar);
    }

    public void showAddEmployee(AddEmployee addEmp) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(291, 516);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Thêm nhân viên");
        jFrame.getContentPane().add(addEmp);
        jFrame.setVisible(true);
    }

    public void showBookingRoom(RoomBooking detail) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(329, 700);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Đặt phòng");
        jFrame.getContentPane().add(detail);
        jFrame.setVisible(true);
        BookingController controller = new BookingController(detail);
        controller.setEventClick(new RoomBookingListener() {
            @Override
            public void roomBooking() {
                layoutKhachSan.clearScrollView();
                getDataRoom();
            }
        });
    }

    public void showBillView(BillView billView) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(600, 700);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Trả phòng");
        jFrame.getContentPane().add(billView);
        jFrame.setVisible(true);
    }

    public void showChartRoom() {
        RevenueInformation revenueInformation = new RevenueInformation();

    }

    public void getLabelRoomCount() {
        String statusAll = String.valueOf(data.countAllRoom());
        String statusValid = String.valueOf(data.countValidRoom("Trống"));
        String statusInvalid = String.valueOf(data.countInValidRoom("Có Người Ở"));
        String statusFixed = String.valueOf(data.countUnRoom("Bảo trì"));
        layoutKhachSan.setTextRoom(statusAll, statusValid, statusInvalid, statusFixed);
    }

    public void setStatusInDashBoard() {
        layoutKhachSan.clearScrollView();
        getDataRoom();
        getLabelRoomCount();
    }

    public void showBillForCheckOut(Room room) {
        viewBill = new BillView();
        List<Booking> bookingList = bookingDao.getAllBookingsConditions(room.getIdRoom());
        String customerId = bookingList.get(0).getCustomerId();
        List<Customer> customerList = customerDao.getInformationCustomer(Integer.valueOf(customerId));
        String customerName = customerList.get(0).getName();
        LocalDateTime checkInDate = bookingList.get(0).getCheckInDate();
        LocalDateTime checkOutDate = bookingList.get(0).getCheckOutDate();

        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double roomPrice = bookingList.get(0).getPrice();

        List<ServiceInvoice> serviceInvoiceList = serviceInvoiceDao.getServiceRoomByRoom(room.getIdRoom());
        double roomServiceTotal = 0;
        for (ServiceInvoice serviceInvoice : serviceInvoiceList) {
            roomServiceTotal += serviceInvoice.getTotalPrice();
        }
        setTableInCheckOut(room.getIdRoom());
        LocalDateTime currentTime = LocalDateTime.now();
        double total = roomPrice + roomServiceTotal;
        if (numberOfDays == 0) {
            Duration duration = Duration.between(checkInDate, currentTime);
            long minutes = duration.toMinutes();
            roomPrice = bookingList.get(0).getPrice() / 24 * (minutes / 60);
            double totalNumberDays = roomPrice + roomServiceTotal;
            viewBill.setTextToLabel(customerName, String.valueOf(checkInDate), bookingList.get(0).getBookingId(), layoutKhachSan.getTextEmployee(), room.getNameRoom(), bookingList.get(0).getNumberOfPeople(), "Số giờ ở : " + String.valueOf(minutes / 60), String.valueOf(roomServiceTotal), String.valueOf(totalNumberDays));
        } else {
            roomPrice = bookingList.get(0).getPrice() * numberOfDays;
            viewBill.setTextToLabel(customerName, String.valueOf(checkInDate), bookingList.get(0).getBookingId(), layoutKhachSan.getTextEmployee(), room.getNameRoom(), bookingList.get(0).getNumberOfPeople(), "Số ngày ở: " + String.valueOf(numberOfDays), String.valueOf(roomServiceTotal), String.valueOf(total));
        }

        viewBill.setTable(modelInvoice);
        setMoneyBill(total);
        String empId = accountDAO.getEmpId(layoutKhachSan.getNameUser());
        System.out.println(empId);
        viewBill.setClickButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookingDao.updateBooking(bookingList.get(0));
                serviceInvoiceDao.updateStatusRoomCheckOut(room.getIdRoom());
                data.setStatus(room.getIdRoom(), "Trống");
                setStatusInDashBoard();
                dataBill.insertBill(Integer.valueOf(bookingList.get(0).getBookingId()), total, empId);
                JFrame frame = (JFrame) viewBill.getRootPane().getParent();
                frame.dispose();
                JOptionPane.showMessageDialog(viewBill, "Trả phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        viewBill.clickPrintToExcel(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Bill Hotel");

                // Tạo dòng header
                XSSFRow headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Mã hóa đơn");
                headerRow.createCell(1).setCellValue("Tên khách hàng");
                headerRow.createCell(2).setCellValue("Ngày nhận phòng");
                headerRow.createCell(3).setCellValue("Ngày trả phòng");
                headerRow.createCell(4).setCellValue("Số ngày ở");
                headerRow.createCell(5).setCellValue("Tổng tiền thanh toán");
                headerRow.createCell(6).setCellValue("Lễ tân");
                // Tạo dòng dữ liệu
                XSSFRow dataRow = sheet.createRow(1);
                dataRow.createCell(0).setCellValue(2);
                dataRow.createCell(1).setCellValue(customerName);
                dataRow.createCell(2).setCellValue(checkInDate.toString());
                dataRow.createCell(3).setCellValue(checkOutDate.toString());
                if (numberOfDays == 0) {
                    dataRow.createCell(4).setCellValue("Thuê theo ngày: " + checkInDate);
                } else {
                    dataRow.createCell(4).setCellValue(numberOfDays);
                }

                dataRow.createCell(5).setCellValue(total);
                dataRow.createCell(6).setCellValue(layoutKhachSan.getTextEmployee());
                // Lưu workbook vào file
                String filePath = "C:\\Users\\quoct\\OneDrive\\Desktop\\InvoiceHotel\\hoadon.xlsx"; // Đường dẫn tệp
                String filePathAll = "C:\\Users\\quoct\\OneDrive\\Desktop\\InvoiceHotel\\tatcahoadon.xlsx";
                FileOutputStream fis = null;
                FileOutputStream fisAll = null;
                try {
                    fis = new FileOutputStream(filePath);
                    fisAll = new FileOutputStream(filePathAll, true);
                    workbook.write(fis);
                    workbook.write(fisAll);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (fis != null || fisAll != null) {
                        try {
                            fis.close();
                            fisAll.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        });

        JFrame frame = new JFrame("Trả phòng khách sạn");
        frame.getContentPane().add(viewBill);

        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void setTableInCheckOut(int idRoom) {
        List<ServiceInvoice> list = serviceInvoiceDao.getServiceRoomByRoom(idRoom);
        modelInvoice.setData(list);
        viewBill.setTable(modelInvoice);
    }

    public void setMoneyBill(double total) {
        viewBill.customerMoneyTextField(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleMoneyChange(total);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                handleMoneyChange(total);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        }
        );
    }

    private void handleMoneyChange(double total) {
        caculatorMoney(total);

    }

    public void caculatorMoney(double total) {
        String newText = viewBill.getTextMoneyCustomer();
        if (!newText.isEmpty()) {

            String moneyCustomer = viewBill.getTextMoneyCustomer();
            double refund = Double.valueOf(moneyCustomer) - total;
            viewBill.setTextFieldMoney(String.valueOf(refund));
        }
    }


    public void showAddFacilitiesToRoom(AddFacilititesToRoom add) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(312, 312);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Thêm thiết bị cho phòng");
        jFrame.getContentPane().add(add);
        jFrame.setVisible(true);
    }
}
