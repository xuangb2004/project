package View;

import Model.AccountDAO;
import Model.Employee;
import Model.EmployeeDAO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class EmployeeView extends JPanel {

    private JLabel title;
    private JTextField searchEmployee;
    private JPanel mainJPanel;
    private JTable tableEmployee;
    private JButton exportButton;
    private JMenuBar menuBar;
    private JMenuItem updateItem;
    private JPopupMenu popupMenuUpdate;
    private JMenuItem deleteItem;
    public EmployeeView() {
        setLayout(new BorderLayout());

        title = new JLabel("Quản lý nhân viên");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        searchEmployee = new JTextField();
        searchEmployee.setPreferredSize(new Dimension(500, 30));
        searchEmployee.setFont(new Font("Arial", Font.PLAIN, 14));
        searchEmployee.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(searchEmployee, BorderLayout.CENTER);

        mainJPanel = new JPanel(new BorderLayout());
        mainJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tableEmployee = new JTable();

        mainJPanel.add(new JScrollPane(tableEmployee), BorderLayout.CENTER);
        add(mainJPanel, BorderLayout.CENTER); // Changed to CENTER for better layout

        ImageIcon exportIcon = new ImageIcon("‪C:\\Users\\quoct\\Downloads\\print.png");

        exportButton = new JButton("Xuất Excel", exportIcon);
        popupMenuUpdate = new JPopupMenu();
        updateItem = new JMenuItem("Sửa nhân viên");
        deleteItem = new JMenuItem("Xóa nhân viên");
        popupMenuUpdate.add(updateItem);
        popupMenuUpdate.add(deleteItem);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(600, 500));
    }

    // Methods for interacting with the view
    public void searchClick(DocumentListener listener) {
        searchEmployee.getDocument().addDocumentListener(listener);
    }

    public void setTable(TableModelEmployee model) {
        this.tableEmployee.setModel(model);
    }

    public String getTextChange() {
        return searchEmployee.getText();
    }

    public void printTableSalary(ActionListener listener) {
        exportButton.addActionListener(listener);
    }

    public void clickInTable(MouseAdapter adapter) {
        tableEmployee.addMouseListener(adapter);

    }

    public void updateItemListener(ActionListener listener) {
        updateItem.addActionListener(listener);
    }
    public void deleteItemListener(ActionListener listener) {
        deleteItem.addActionListener(listener);
    }
    public void showPopup(MouseEvent e) {
            popupMenuUpdate.show(e.getComponent(), e.getX(), e.getY());

    }
    public int getRowSelected(){
        return tableEmployee.getSelectedRow();
    }
}
