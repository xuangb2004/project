package View;

import Model.BookingDao;
import Model.RoomDao;
import Model.ServiceInVoiceDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Class to display revenue information using JFreeChart library.
 */
public class RevenueInformation {

    private final BookingDao bookingData;
    private final ServiceInVoiceDAO serviceDao;
    private final RoomDao roomDao;

    public RevenueInformation() {
        roomDao = new RoomDao();
        bookingData = new BookingDao();
        serviceDao = new ServiceInVoiceDAO();

        JFrame frame = new JFrame("Thống kê doanh thu");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu analystMenu = new JMenu("Thống kê doanh thu đặt phòng");
        JMenu serviceAnalystMenu = new JMenu("Thống kê doanh thu dịch vụ");
        JMenuItem revenueYear = new JMenuItem("Doanh thu theo năm");
        JMenuItem revenueMonth = new JMenuItem("Doanh thu theo tháng");
        JMenuItem revenueRoom = new JMenuItem("Doanh thu theo từng phòng");
        analystMenu.add(revenueYear);
        analystMenu.add(revenueMonth);
        analystMenu.add(revenueRoom);
        analystMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayRoomRevenueChart(frame);
            }
        });
        revenueYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRoomRevenueChart(frame);
            }
        });

        serviceAnalystMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayServiceRevenueChart(frame);
            }
        });
        revenueMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRoomRevenueMonthChart(frame);
            }
        });
        revenueRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRoomRevenueRoombyRoomChart(frame);
            }
        });

        jMenuBar.add(analystMenu);
        jMenuBar.add(serviceAnalystMenu);
        frame.setJMenuBar(jMenuBar);

        displayServiceRevenueChart(frame);

        frame.setVisible(true);
    }

    private void displayRoomRevenueChart(JFrame frame) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ doanh thu đặt phòng khách sạn",
                "Năm", "Doanh thu",
                createRoomRevenueDataset(), org.jfree.chart.plot.PlotOrientation.VERTICAL, false, false, false);
        displayChart(frame, barChart);
    }

    private void displayRoomRevenueMonthChart(JFrame frame) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ doanh thu theo tháng",
                "Tháng", "Doanh thu",
                createRoomRevenueMonthDataset(), org.jfree.chart.plot.PlotOrientation.VERTICAL, false, false, false);
        displayChart(frame, barChart);
    }

    private void displayRoomRevenueRoombyRoomChart(JFrame frame) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Biểu đồ doanh thu theo từng phòng",
                "Doanh thu", "Doanh thu",
                createRoomRevenueRoomByRoomDataset(), org.jfree.chart.plot.PlotOrientation.VERTICAL, false, false, false);
        displayChart(frame, barChart);
    }

    private void displayServiceRevenueChart(JFrame frame) {
        PieDataset dataset = createServiceRevenueDataset();
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Doanh thu dịch vụ các loại đồ ăn", dataset, true, true, true);
        displayChart(frame, pieChart);
    }

    private void displayChart(JFrame frame, JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        frame.getContentPane().removeAll();
        frame.add(chartPanel);
        frame.setTitle(chart.getTitle().getText());
        frame.revalidate();
        frame.repaint();
    }

    private PieDataset createServiceRevenueDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Các món cơm", serviceDao.revenueServiceInvoice("Cơm"));
        dataset.setValue("Các món nước", serviceDao.revenueServiceInvoice("Đồ nước"));
        dataset.setValue("Đồ uống", serviceDao.revenueServiceInvoice("Đồ uống"));
        return dataset;
    }

    private CategoryDataset createRoomRevenueDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double revenue2023 = bookingData.revenueHotelInYear(2023);
        double revenue2024 = bookingData.revenueHotelInYear(2024);

        dataset.addValue(revenue2023, "Doanh thu", "2023");
        dataset.addValue(revenue2024, "Doanh thu", "2024");
        return dataset;
    }

    private CategoryDataset createRoomRevenueMonthDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String monthValue = JOptionPane.showInputDialog("Nhập năm bạn muốn thống kê");

        if (monthValue == null) {
            System.out.println("Nhap vao di da");
        } else {
            int year = Integer.parseInt(monthValue);
            HashMap<String, Integer> mapMonth = new HashMap<>();
            mapMonth.put("January", 1);
            mapMonth.put("February", 2);
            mapMonth.put("March", 3);
            mapMonth.put("April", 4);
            mapMonth.put("May", 5);
            mapMonth.put("June", 6);
            mapMonth.put("July", 7);
            mapMonth.put("August", 8);
            mapMonth.put("September", 9);
            mapMonth.put("October", 10);
            mapMonth.put("November", 11);
            mapMonth.put("December", 12);
            double revenueJanuary = bookingData.revenueHotelInMonth(mapMonth.get("January"), year);
            double revenueFebruary = bookingData.revenueHotelInMonth(mapMonth.get("February"), year);
            double revenueMarch = bookingData.revenueHotelInMonth(mapMonth.get("March"), year);
            double revenueApril = bookingData.revenueHotelInMonth(mapMonth.get("April"), year);
            double revenueMay = bookingData.revenueHotelInMonth(mapMonth.get("May"), year);
            double revenueJune = bookingData.revenueHotelInMonth(mapMonth.get("June"), year);
            double revenueJuly = bookingData.revenueHotelInMonth(mapMonth.get("July"), year);
            double revenueAugust = bookingData.revenueHotelInMonth(mapMonth.get("August"), year);
            double revenueSeptember = bookingData.revenueHotelInMonth(mapMonth.get("September"), year);
            double revenueOctober = bookingData.revenueHotelInMonth(mapMonth.get("October"), year);
            double revenueNovember = bookingData.revenueHotelInMonth(mapMonth.get("November"), year);
            double revenueDecember = bookingData.revenueHotelInMonth(mapMonth.get("December"), year);
            dataset.addValue(revenueJanuary, "Doanh thu", "1");

            dataset.addValue(revenueFebruary, "Doanh thu", "2");

            dataset.addValue(revenueMarch, "Doanh thu", "3");

            dataset.addValue(revenueApril, "Doanh thu", "4");

            dataset.addValue(revenueMay, "Doanh thu", "5");

            dataset.addValue(revenueJune, "Doanh thu", "6");

            dataset.addValue(revenueJuly, "Doanh thu", "7");

            dataset.addValue(revenueAugust, "Doanh thu", "8");

            dataset.addValue(revenueSeptember, "Doanh thu", "9");

            dataset.addValue(revenueOctober, "Doanh thu", "10");

            dataset.addValue(revenueNovember, "Doanh thu", "11");

            dataset.addValue(revenueDecember, "Doanh thu", "12");
        }

        return dataset;
    }

    private CategoryDataset createRoomRevenueRoomByRoomDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Integer> list = new ArrayList<>();
        list = roomDao.getAllRoom();
        List<Double> revenueRoom = new ArrayList<>();
        for (int name : list) {
            double revenue = bookingData.revenueRoomByRoom(name);
            revenueRoom.add(revenue);

        }
        int i = 0;
        for (double total : revenueRoom) {
            dataset.addValue(total, "Doanh thu", list.get(i));
            i++;
        }

        return dataset;
    }
}
