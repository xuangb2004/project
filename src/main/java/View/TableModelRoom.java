/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Room;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quoct
 */
public class TableModelRoom extends AbstractTableModel{
    private List<Room> list;
    private String[] columnNames = {"Mã Phòng","Tên phòng", "Loại phòng","Trạng thái","Diện tích","Số người ở","Vị trí","Giá phòng"};

    public TableModelRoom() {
        list = new ArrayList<>();
    }
   @Override
    public int getRowCount() {
        return list.size();
    }
    public void setData(List<Room> list) {
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
        Room room = list.get(rowIndex);
        if (room != null) {
            switch (columnIndex) {
                case 0:
                    return room.getIdRoom();
                case 1:
                    return room.getNameRoom();
                case 2:
                    return room.getDescriptionRoom();
                case 3: 
                    return room.getStatus();
                case 4: 
                    return room.getArea();
                case 5: 
                    return room.getQuantity();
                case 6:
                    return room.getLocation();
                case 7: 
                    return room.getPrice();
            }
        }
        return null;
    }
}
