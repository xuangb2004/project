
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.Room;
import Model.Service;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author quoct
 */
public class ServiceItem extends JPanel{
    

    private Service data;

    public ServiceItem() {
        initComponents();
        setOpaque(true);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(100, 100));
        
    }

    public void setData(Service data) {
        this.data = data;
        labelNameService.setText(data.getNameService());
        labelServicePrice.setText(String.valueOf(data.getPrice()) + "(VND)");
  
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
    }

    public void actionPerformed(ActionListener listener) {
        jMenuItem1.addActionListener(listener);
    }

    public void handleMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            jPopupMenu1.show(this, evt.getX(), evt.getY());
        }
    }

    private void initComponents() {
        Font defaultFont = new Font("Arial", Font.PLAIN, 12);
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        labelNameService = new javax.swing.JLabel();
        labelServicePrice = new javax.swing.JLabel();
        labelNameService.setFont(defaultFont);
        labelServicePrice.setFont(defaultFont);
        jMenuItem1.setText("Đặt Phòng");

        jMenuItem2.setText("Xem Thông Tin Phòng");

        jPopupMenu1.add(jMenuItem1);
        jPopupMenu1.add(jMenuItem2);

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(153, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelNameService.setText("Cơm rang dưa bò");

        labelServicePrice.setText("10000(VNĐ)");

        GridBagConstraints gridBagConstraints;

        jPanel1.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(labelNameService, gridBagConstraints);


        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(labelServicePrice, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jPanel1, gridBagConstraints);
    }

    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel labelNameService;
    private javax.swing.JLabel labelServicePrice;
}