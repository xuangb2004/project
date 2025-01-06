/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Employee;
import Model.Room;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author quoct
 */
public class TableModelEmployee extends AbstractTableModel{
     private List<Employee> list;
    private String[] columnNames = {"Mã Nhân Viên","Tên Nhân Viên", "Tuổi","Giới tính","Số Điện Thoại","Vị trí","Lương"};

    public TableModelEmployee() {
        list = new ArrayList<>();
    }
   @Override
    public int getRowCount() {
        return list.size();
    }
    public void setData(List<Employee> list) {
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
        Employee employee = list.get(rowIndex);
        if (employee != null) {
            switch (columnIndex) {
                case 0:
                    return employee.getEmployeeID();
                case 1:
                    return employee.getName();
                case 2:
                    return employee.getAge();
                case 3: 
                    return employee.getGender();
                case 4:
                    return employee.getNumberPhone();
                case 5: 
                    return employee.getPosition();
                case 6:
                    return employee.getSalary();
           
            }
        }
        return null;
    }
}
