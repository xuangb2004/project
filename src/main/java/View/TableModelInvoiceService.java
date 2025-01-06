/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Model.Room;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import Model.Room;
import Model.ServiceInVoiceDAO;
import Model.ServiceInvoice;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quoct
 */
public class TableModelInvoiceService extends AbstractTableModel{
    private List<ServiceInvoice> list;
    private String[] columnNames = {"Mã hóa đơn","Mã dịch vụ","Mã phòng","Số lượng","Đơn giá"};

    public TableModelInvoiceService() {
        list = new ArrayList<>();
    }
   @Override
    public int getRowCount() {
        return list.size();
    }
    public void setData(List<ServiceInvoice> list) {
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
           ServiceInvoice service = list.get(rowIndex);
        if (service != null) {
            switch (columnIndex) {
                case 0:
                    return service.getServiceInvoiceId();
                case 1:
                    return service.getServiceID();
                case 2:
                    return service.getRoomID();
                case 3: 
                    return service.getQuantity();
                case 4: 
                    return service.getTotalPrice();
          
            }
        }
        return null;
    }

 
     
}
