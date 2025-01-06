/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.AccountDAO;
import Model.Employee;
import Model.EmployeeDAO;
import View.EmployeeView;
import View.RoomManagement;
import View.TableModelEmployee;
import View.UpdateEmployee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author quoct
 */
public class EmployyeContronler {

    private TableModelEmployee model;
    private EmployeeView view;
    private EmployeeDAO data;
    private AccountDAO accountData;
    private UpdateEmployee updateEmployee;
    private AccountDAO accountDAO;

    public EmployyeContronler(EmployeeView view) {
        this.view = view;
        accountData = new AccountDAO();
        data = new EmployeeDAO();
        model = new TableModelEmployee();
        accountDAO = new AccountDAO();
        refreshTable();
        view.searchClick(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterData();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filterData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterData();
            }
        });
        getTableSelectedUpdate();
        view.printTableSalary(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Lương nhân viên tháng " + getMonth());
                XSSFRow headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Lương tháng " + getMonth());

                XSSFRow employeeHeaderRow = sheet.createRow(1);
                employeeHeaderRow.createCell(0).setCellValue("Mã nhân viên");
                employeeHeaderRow.createCell(1).setCellValue("Tên nhân viên");
                employeeHeaderRow.createCell(2).setCellValue("Tuổi");
                employeeHeaderRow.createCell(3).setCellValue("Giới tính");
                employeeHeaderRow.createCell(4).setCellValue("Số điện thoại");
                employeeHeaderRow.createCell(5).setCellValue("Vị trí");
                employeeHeaderRow.createCell(6).setCellValue("Lương");

                List<Employee> listPrint = data.getListNhanVien();
                int rowIndex = 2;
                for (Employee emp : listPrint) {
                    XSSFRow dataRow = sheet.createRow(rowIndex++);
                    dataRow.createCell(0).setCellValue(emp.getEmployeeID());
                    dataRow.createCell(1).setCellValue(emp.getName());
                    dataRow.createCell(2).setCellValue(String.valueOf(emp.getAge()));
                    dataRow.createCell(3).setCellValue(emp.getGender());
                    dataRow.createCell(4).setCellValue(emp.getNumberPhone());
                    dataRow.createCell(5).setCellValue(String.valueOf(emp.getPosition()));
                    dataRow.createCell(6).setCellValue(String.valueOf(emp.getSalary()));
                }

                String filePath = "C:\\Users\\quoct\\OneDrive\\Desktop\\EmployeeSalary\\Bảng lương nhân viên tháng " + getMonth() + ".xlsx"; // Đường dẫn tệp
                String filePathAll = "C:\\Users\\quoct\\OneDrive\\Desktop\\EmployeeSalaryAll\\Tổng lương.xlsx";

                FileOutputStream fis = null;
                FileOutputStream fisAll = null;

                try {
                    fis = new FileOutputStream(filePath);
                    workbook.write(fis);

                    FileInputStream inputStream = new FileInputStream(filePathAll);
                    XSSFWorkbook existingWorkbook = new XSSFWorkbook(inputStream);
                    inputStream.close();

                    XSSFSheet existingSheet = existingWorkbook.getSheetAt(0);

                    int newRowNum = existingSheet.getLastRowNum() + 1;
                    XSSFRow newRow = existingSheet.createRow(newRowNum);

                    newRow.createCell(0).setCellValue("Mã nhân viên");
                    newRow.createCell(1).setCellValue("Tên nhân viên");
                    newRow.createCell(2).setCellValue("Tuổi");
                    newRow.createCell(3).setCellValue("Giới tính");
                    newRow.createCell(4).setCellValue("Số điện thoại");
                    newRow.createCell(5).setCellValue("Vị trí");
                    newRow.createCell(6).setCellValue("Lương");

                    for (Row row : sheet) {
                        Row existingRow = existingSheet.createRow(++newRowNum);
                        for (int i = 0; i < row.getLastCellNum(); i++) {
                            Cell existingCell = existingRow.createCell(i);
                            existingCell.setCellValue(row.getCell(i).getStringCellValue());
                        }
                    }

                    fisAll = new FileOutputStream(filePathAll);
                    existingWorkbook.write(fisAll);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                        if (fisAll != null) {
                            fisAll.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        workbook.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void refreshTable() {
        List<Employee> list = data.getListNhanVien();
        model.setData(list);
        view.setTable(model);
    }

    private void filterData() {
        String searchText = view.getTextChange();
        if (searchText != null && !searchText.isEmpty()) {
            List<Employee> searchList = data.searchEmployee(searchText);
            model.setData(searchList);
            view.setTable(model);

        } else {
            refreshTable();
        }
    }

    public int getMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue();
    }

    public void getTableSelectedUpdate() {
        view.clickInTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    view.showPopup(e);
                }
            }
        });
        view.updateItemListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee = new UpdateEmployee();
                showUpdate(updateEmployee);
                Employee employeeGetText = setTableItemSelected();
                if (getCountLogin() == 1) {
                    employeeGetText.setSalary(employeeGetText.getSalary());
                } else {
                    employeeGetText.setSalary(employeeGetText.getSalary() / getCountLogin());
                }
                updateEmployee.setTextUpdate(employeeGetText);
                updateEmployee.clickUpdate(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        data.updateEmployee(updateEmployee.getTextUpdate());
                        refreshTable();
                        JFrame frame = (JFrame) updateEmployee.getRootPane().getParent();
                        frame.dispose();
                        JOptionPane.showMessageDialog(view, "Sửa thông tin nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    }
                });
            }
        });
        view.deleteItemListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowSelected = view.getRowSelected();
                String empID = (String) model.getValueAt(rowSelected, 0);
                if (data.deleteEmployee(empID)) {
                    JOptionPane.showMessageDialog(view, "Xóa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(view, "Nhân viên đang làm việc", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void showUpdate(UpdateEmployee updateEmployee) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(290, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle("Sửa nhân viên");
        jFrame.getContentPane().add(updateEmployee);
        jFrame.setVisible(true);

    }

    public Employee setTableItemSelected() {
        int rowSelected = view.getRowSelected();
        String empID = (String) model.getValueAt(rowSelected, 0);
        String name = (String) model.getValueAt(rowSelected, 1);
        int age = (int) model.getValueAt(rowSelected, 2);
        String gender = (String) model.getValueAt(rowSelected, 3);
        String phone = (String) model.getValueAt(rowSelected, 4);
        String position = (String) model.getValueAt(rowSelected, 5);
        double salary = (double) model.getValueAt(rowSelected, 6);
        Employee employee = new Employee(empID, name, age, gender, phone, position, salary);
        return employee;
    }

    public int getCountLogin() {
        int rowSelected = view.getRowSelected();
        String empID = (String) model.getValueAt(rowSelected, 0);
        return data.getTimeKeeping(empID);
    }


}
