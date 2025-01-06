/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Booking;
import Model.Room;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quoct
 */
public class TableRoomBooking extends AbstractTableModel{
    private List<Booking> list;
    private String[] columnNames = {"Mã đặt phòng","Tên phòng", "Mã khách hàng","Ngày đặt phòng","Ngày trả phòng","Giá phòng","Số người ở","Hình thức thuê","Tiền đặt cọc"};

    public TableRoomBooking() {
        list = new ArrayList<>();
    }
   @Override
    public int getRowCount() {
        return list.size();
    }
    public void setData(List<Booking> list) {
            this.list = list;
            fireTableDataChanged(); // thông báo dữ liệu bảng thay đổi
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
  @Override
    public int getColumnCount() {
        return columnNames.length;
    }

       @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Booking booking = list.get(rowIndex);
        if (booking != null) {
            switch (columnIndex) {
                case 0:
                    return booking.getBookingId();
                case 1:
                    return booking.getRoomId();
                case 2:
                    return booking.getCustomerId();
                case 3: 
                    return booking.getCheckInDate();
                case 4: 
                    return booking.getCheckOutDate();
                case 5:
                    return booking.getPrice();
                case 6: 
                    return booking.getNumberOfPeople();
                case 7: 
                    return booking.getRoomRental();
                case 8: 
                    return booking.getInvest();
            }
        }
        return null;
    }
}
