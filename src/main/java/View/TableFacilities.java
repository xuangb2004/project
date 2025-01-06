/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Customer;
import Model.Facilities;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quoct
 */
public class TableFacilities extends AbstractTableModel{
      private List<Facilities> list;
    private String[] columnNames = {"Mã thiết bị","Tên thiết bị","Số lượng","Ngày mua","Giá tiền","Trạng thái"};

    public TableFacilities() {
        list = new ArrayList<>();
    }
   @Override
    public int getRowCount() {
        return list.size();
    }
    public void setData(List<Facilities> list) {
            this.list = list;
            fireTableDataChanged(); // thông báo dữ liệu bảng thay đổi
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column] ;
    }
  @Override
    public int getColumnCount() {
        return columnNames.length ;
    }

       @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Facilities facilities = list.get(rowIndex);
        if (facilities != null) {
            switch (columnIndex) {
                case 0:
                    return facilities.getId();
                case 1:
                    return facilities.getName();
                case 2:
                    return facilities.getQuantity();
                case 3: 
                    return facilities.getDate_buy();
                case 4:
                    return facilities.getPrice();
                case 5:
                    return facilities.getStatus();
            }
        }
        return null;
    }
}
