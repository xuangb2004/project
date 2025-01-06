/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Booking;
import Model.BookingDao;
import Model.Customer;
import Model.CustomerDao;
import Model.RoomDao;
import View.RoomManagement;
import View.RoomBooking;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author quoct
 */
public class BookingController {

    private RoomBooking view;
    private CustomerDao customerSql;
    private BookingDao bookingSql;
    private RoomDao roomSql;

    public BookingController(RoomBookingListener eventClick) {
        this.eventClick = eventClick;
    }

    public void setEventClick(RoomBookingListener eventClick) {
        this.eventClick = eventClick;
    }
    private RoomBookingListener eventClick;

    public BookingController(RoomBooking view) {
        this.view = view;
        this.customerSql = new CustomerDao();
        this.bookingSql = new BookingDao();
        this.roomSql = new RoomDao();
        view.addClickButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRoomBooked();
            }
        });

    }

    private void fireRoomAddedEvent() {
        if (eventClick != null) {
            eventClick.roomBooking();
        }
    }

    private void setRoomBooked() {
        Customer inforCustomer = view.getAllText();
        customerSql.insertCustomer(inforCustomer);
        Booking booking = view.getAllTextBooking();
        long diffDay = minusDay(booking.getCheckInDate(), booking.getCheckOutDate());
        if (hasEmptyFields(booking, inforCustomer)) {
            JOptionPane.showMessageDialog(view, "Vui lòng không để trống thông tin quan trọng", "Thông báo đặt phòng", JOptionPane.INFORMATION_MESSAGE);
        } else if (diffDay < 0 || booking.getPrice() - booking.getInvest() < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng đặt phòng hợp lệ", "Thông báo đặt phòng", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(Integer.valueOf(booking.getNumberOfPeople()) > roomSql.getQuantityRoom(booking.getRoomId())){
            JOptionPane.showMessageDialog(view, "Số người ở không vượt quá ngưỡng cho phép", "Thông báo đặt phòng", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            if(booking.getRoomRental().equals("Theo giờ")){
                booking.setCheckOutDate(booking.getCheckInDate());
            }
            bookingSql.insertBooking(booking, inforCustomer.getCustomerId());
            JOptionPane.showMessageDialog(view, "Đặt phòng thành công", "Thông báo đặt phòng", JOptionPane.INFORMATION_MESSAGE);
            roomSql.updateStatusRoom(booking.getRoomId(), "Có người ở");
            fireRoomAddedEvent();
            closeFrame();
        }
    }

    public long minusDay(LocalDateTime checkin, LocalDateTime checkout) {
    long differenceInDays = 0;
    if (checkin != null && checkout != null) {
        Duration duration = Duration.between(checkin, checkout);
        differenceInDays = duration.toDays();
    }
    return differenceInDays;
}

    private boolean hasEmptyFields(Booking booking, Customer inforCustomer) {
        return String.valueOf(booking.getBookingId()).isEmpty() || booking.getNumberOfPeople().isEmpty()
                || inforCustomer.getAddress().isEmpty() || inforCustomer.getCCCD().isEmpty()
                || inforCustomer.getPhoneNumber().isEmpty() || inforCustomer.getName().isEmpty() || String.valueOf(inforCustomer.getAge()).isEmpty() || booking.getCheckOutDate() == null;
    }

    public void closeFrame() {
        JFrame frame = (JFrame) view.getRootPane().getParent();
        frame.dispose();
    }
}
