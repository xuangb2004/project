package View;

import Model.Room;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ItemPanel extends JPanel {

    private Room data;

    public ItemPanel() {
        initComponents();
        setOpaque(true);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(120, 100));
        setBackground(Color.GREEN);// Set your preferred size
        setForeground(Color.GREEN);
    }

    public void setData(Room data) {
        this.data = data;
        if (data.getStatus().equals("Trống")) {
            jPanel1.setBackground(Color.GREEN);
        } else if (data.getStatus().equals("Bảo trì")) {
            setBackground(Color.YELLOW);
            jPanel1.setBackground(Color.YELLOW);
        } else {
            setBackground(Color.RED);
            jPanel1.setBackground(Color.RED);
        }

        labelNameRoom.setText("Phòng " + data.getNameRoom());
        labelStatus.setText(data.getStatus());
        jLabel1.setText("(" + data.getDescriptionRoom() + ")");
    }

    public void jMenuAddClick(ActionListener listener) {
        jMenuItem1.addActionListener(listener);
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
        jMenuItem2.addActionListener(listener);
    }

    public void actionClickMenuFixed(ActionListener listener) {
        jMenuItem3.addActionListener(listener);
    }

    public void handleMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            jPopupMenu1.show(this, evt.getX(), evt.getY());
        }
    }

    public void actionClickMenuFinish(ActionListener listener) {
        jMenuItem4.addActionListener(listener);
    }

    public void actionClickMenuCheckOut(ActionListener listener) {
        jMenuItem5.addActionListener(listener);
    }
       public void actionClickMenuChangeRoom(ActionListener listener) {
        jMenuItem6.addActionListener(listener);
    }

    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jMenuItem3 = new JMenuItem();
        jMenuItem4 = new JMenuItem();
        jMenuItem5 = new JMenuItem();
        jMenuItem6 = new JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        labelNameRoom = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jMenuItem1.setText("Nhận phòng");

        jMenuItem2.setText("Chi tiết đặt phòng");
        jMenuItem3.setText("Bảo trì phòng");
        jMenuItem4.setText("Bảo trì hoàn tất");
        jMenuItem5.setText("Trả phòng");
        jMenuItem6.setText("Chuyển phòng");
        jPopupMenu1.add(jMenuItem1);
        jPopupMenu1.add(jMenuItem2);
        jPopupMenu1.add(jMenuItem3);
        jPopupMenu1.add(jMenuItem4);
        jPopupMenu1.add(jMenuItem6);
        jPopupMenu1.add(jMenuItem5);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(153, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelNameRoom.setText("Phòng 202");

        labelStatus.setText("Có người ở");

        jLabel1.setText("Phòng Vip");

        GridBagConstraints gridBagConstraints;

        jPanel1.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(labelNameRoom, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(labelStatus, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(jPanel1, gridBagConstraints);
    }

    private javax.swing.JLabel jLabel1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JLabel labelNameRoom;
    private javax.swing.JLabel labelStatus;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem4;
    private JMenuItem jMenuItem5;
     private JMenuItem jMenuItem6;
}
