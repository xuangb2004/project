/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Customer;
import Model.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quoct
 */
public class TableModelCustomer extends AbstractTableModel{
    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author quoct
 */
     private List<Customer> list;
    private String[] columnNames = {"Mã Khách Hàng","Tên", "Tuổi","CCCD","Số Điện Thoại","Địa Chỉ"};

    public TableModelCustomer() {
        list = new ArrayList<>();
    }
   @Override
    public int getRowCount() {
        return list.size();
    }
    public void setData(List<Customer> list) {
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
        Customer customer = list.get(rowIndex);
        if (customer != null) {
            switch (columnIndex) {
                case 0:
                    return customer.getCustomerId();
                case 1:
                    return customer.getName();
                case 2:
                    return customer.getAge();
                case 3: 
                    return customer.getCCCD();
                case 4:
                    return customer.getPhoneNumber();
                case 5: 
                    return customer.getAddress(); 
                
            }
        }
        return null;
    }
}
