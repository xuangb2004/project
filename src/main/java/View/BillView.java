package View;

import Model.ServiceInVoiceDAO;
import Model.ServiceInvoice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BillView extends JPanel {

    private JPanel mainPanel;
    private JPanel panelNorth;
    private JLabel labelTitle;
    private JPanel panelCenter;
    private JLabel logoHotel;
    private JLabel logoPrint;
    private JPanel panelWest;
    private JPanel panelEast;
    private TableModelInvoiceService tableService;
    private JTable table;
    private ServiceInVoiceDAO dataService;
    private JLabel labelName;
    private JLabel labelDate;
    private JLabel labelId;
    private JLabel labelEmployee;
    private JLabel labelRoom;
    private JLabel labelQuantity;
    private JLabel labelDateCount;
    private JLabel totalService;
    private JLabel totalPrice;
    private JButton buttonClickCheckout;
    private JPanel bottomPanel;
    private JPanel bottomPanel2;
    private JTextField textfieldGive;
    private JTextField textfieldReturn;

    public BillView() {
        init();
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        panelNorth = new JPanel();
        panelNorth.setLayout(new BoxLayout(panelNorth, BoxLayout.X_AXIS)); // Sử dụng BoxLayout theo chiều ngang

        logoHotel = new JLabel(new ImageIcon("C:\\Users\\quoct\\Downloads\\logohotel.png"));
        labelTitle = new JLabel("Hóa đơn trả phòng khách sạn");
        labelTitle.setFont(new Font("Arial", Font.BOLD, 20)); // Đổi font chữ thành Arial
        labelTitle.setForeground(Color.red);
        logoPrint = new JLabel(new ImageIcon("C:\\Users\\quoct\\Downloads\\print.png"));
        logoHotel.setBorder(new EmptyBorder(0, 10, 0, 0));
        logoPrint.setBorder(new EmptyBorder(0, 0, 0, 10));
        panelNorth.add(logoHotel);
        panelNorth.add(Box.createHorizontalGlue());
        panelNorth.add(labelTitle);
        panelNorth.add(Box.createHorizontalGlue());
        panelNorth.add(logoPrint);

        panelWest = new JPanel();
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
        labelName = new JLabel();
        labelDate = new JLabel();
        labelId = new JLabel("");
        labelEmployee = new JLabel();

        labelName.setBorder(new EmptyBorder(10, 30, 0, 0));
        labelDate.setBorder(new EmptyBorder(25, 30, 0, 0));
        labelId.setBorder(new EmptyBorder(20, 30, 0, 0));
        labelEmployee.setBorder(new EmptyBorder(20, 30, 0, 0));

        // Đổi font chữ của các nhãn sang Arial
        Font labelFont = new Font("Arial", Font.PLAIN, 12);
        labelName.setFont(labelFont);
        labelDate.setFont(labelFont);
        labelId.setFont(labelFont);
        labelEmployee.setFont(labelFont);

        panelWest.add(labelName);
        panelWest.add(labelDate);
        panelWest.add(labelId);
        panelWest.add(labelEmployee);

        panelEast = new JPanel();
        panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));
        labelRoom = new JLabel();
        labelQuantity = new JLabel();
        labelDateCount = new JLabel();
        labelRoom.setBorder(new EmptyBorder(50, 0, 0, 30));
        labelQuantity.setBorder(new EmptyBorder(20, 0, 0, 30));
        labelDateCount.setBorder(new EmptyBorder(20, 0, 0, 30));

        // Đổi font chữ của các nhãn sang Arial
        labelRoom.setFont(labelFont);
        labelQuantity.setFont(labelFont);
        labelDateCount.setFont(labelFont);

        panelEast.add(labelRoom);
        panelEast.add(labelQuantity);
        panelEast.add(labelDateCount);

        panelCenter = new JPanel();
        JLabel text = new JLabel("Thông tin sử dụng dịch vụ");
        text.setFont(labelFont);
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
        panelCenter.setBorder(new EmptyBorder(170, 0, 0, 35));
        tableService = new TableModelInvoiceService();
        table = new JTable();
        table.setFillsViewportHeight(true);

        table.setFont(labelFont);

        JScrollPane scrollPane = new JScrollPane(table);
        text.setBorder(new EmptyBorder(0, 100, 0, 0));
        panelCenter.add(text);
        Dimension size = table.getPreferredSize();
        scrollPane.setPreferredSize(new Dimension(size.width + 400, 100 + table.getTableHeader().getPreferredSize().height));
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        table.setFillsViewportHeight(true);
        panelCenter.add(scrollPane);

        totalPrice = new JLabel();
        totalPrice.setFont(labelFont);
        totalPrice.setHorizontalAlignment(SwingConstants.CENTER);
        totalService = new JLabel();
        totalService.setFont(labelFont);
        totalService.setHorizontalAlignment(SwingConstants.CENTER);
        buttonClickCheckout = new JButton("Trả phòng");
        buttonClickCheckout.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        totalService.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalService.setBorder(new EmptyBorder(25, 0, 0, 0));
        bottomPanel.add(totalService);

        totalPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalPrice.setBorder(new EmptyBorder(50, 0, 0, 0));
        bottomPanel.add(totalPrice);

        textfieldGive = new JTextField();
        textfieldGive.setBorder(new EmptyBorder(50, 0, 0, 0));
        textfieldGive.setPreferredSize(new Dimension(150, 50));
        textfieldGive.setMaximumSize(new Dimension(150, 50));
        textfieldGive.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        textfieldReturn = new JTextField();
        textfieldReturn.setPreferredSize(new Dimension(150, 50));
        textfieldReturn.setMaximumSize(new Dimension(150, 50));
        textfieldReturn.setAlignmentX(Component.CENTER_ALIGNMENT);
        textfieldReturn.setBorder(new EmptyBorder(25, 0, 0, 0));
        Border titledBorderGive = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Tiền khách đưa");
        textfieldGive.setBorder(titledBorderGive);

        Border titledBorderReturn = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Tiền trả lại");
        textfieldReturn.setBorder(titledBorderReturn);

        bottomPanel.add(textfieldGive);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bottomPanel.add(textfieldReturn);

        buttonClickCheckout.setPreferredSize(new Dimension(200, 90));
        buttonClickCheckout.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonClickCheckout.setHorizontalAlignment(SwingConstants.CENTER);
        buttonClickCheckout.setVerticalAlignment(SwingConstants.CENTER);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bottomPanel.add(buttonClickCheckout);
        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(panelWest, BorderLayout.WEST);
        mainPanel.add(panelEast, BorderLayout.EAST);
        mainPanel.add(panelCenter, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        textfieldReturn.setEnabled(false);
        add(mainPanel);
    }

    public void setTextToLabel(String name, String date, String id, String employee, String room, String quantity, String dateCount,
            String priceService, String tPrice) {
        if (name != null) {
            labelName.setText("Tên khách hàng " + name);
        }
        if (date != null) {
            labelDate.setText("Ngày đặt phòng " + date);
        }
        if (employee != null) {
            labelEmployee.setText(employee);
        }
        if (id != null) {
            labelId.setText("Mã đặt phòng " + id);
        }
        if (quantity != null) {
            labelQuantity.setText("Số người ở " + quantity);
        }
        if (dateCount != null) {
            labelDateCount.setText(dateCount);
        }
        if (room != null) {
            labelRoom.setText("Phòng " + room);
        }
        if (priceService != null) {
            totalService.setText("Tổng tiền dịch vụ " + priceService);
        }
        if (tPrice != null) {
            totalPrice.setText("Tổng tiền " + tPrice);
        }
    }

    public void setTable(TableModelInvoiceService tableData) {
        table.setModel(tableData);
    }

    public void setClickButton(ActionListener listener) {
        buttonClickCheckout.addActionListener(listener);
    }

    public String getTextMoneyCustomer() {
        return textfieldGive.getText();
    }

    public void setTextFieldMoney(String str) {
        textfieldReturn.setText(str);
    }

    public void customerMoneyTextField(DocumentListener listener) {
        textfieldGive.getDocument().addDocumentListener(listener);
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public void clickPrintToExcel(MouseAdapter adapter) {
        logoPrint.addMouseListener(adapter);
    }

}
