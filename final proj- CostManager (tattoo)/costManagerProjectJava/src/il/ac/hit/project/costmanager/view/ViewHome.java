package il.ac.hit.project.costmanager.view;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.viewmodel.IViewModel;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * first window you see when running the program, from here you get to all other windows.
 * using Jtattoo to improve swing UI.
 * @see ApplicationUI constructor for J tattoo.
 */

public class ViewHome implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    public ViewHome() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewHome.this.ui = new ApplicationUI();
                ViewHome.this.ui.start();
            }
        });
    }

    @Override
    public void setViewModel(IViewModel vm) { this.vm = vm; }

    @Override
    public void showMessage(String text) {}

    @Override
    public void showItems(LinkedList<Category.CostItem> arrCostItem) {}

    @Override
    public void categories(ArrayList<String> arrCat) {

    }

    public class ApplicationUI
    {
        private JFrame frameHome;

        private JPanel panelHome;
        private JPanel panelMain;
        private JPanel panelCenter;
        private JPanel panelCenterTop;
        private JPanel panelCenterBottom;
        private JPanel panelIcon;
        private JPanel panelTitle;
        private JPanel panelS;

        private JLabel panelImage;
        private JLabel lbTitle;
        private JLabel s;

        private JButton btAddCat;
        private JButton btDelCat;
        private JButton btAddCost;
        private JButton btDReport;
        private JButton btDPieChart;

        public ApplicationUI () {
            /**
             * using jtattoo jar 1.6.13 version, for improving the swing UI.
             */
            try {
                UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            } catch (ClassNotFoundException e) {
               e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            //creating frame
            frameHome = new JFrame("Cost Manager");
            frameHome.setLocation(380,150);

            //creating panels
            panelHome = new JPanel();
            panelMain = new JPanel();
            panelCenter = new JPanel();
            panelCenterTop = new JPanel();
            panelCenterBottom = new JPanel();
            panelIcon = new JPanel();
            panelImage = new JLabel();
            panelTitle = new JPanel();
            panelS = new JPanel();

            //creating buttons
            btAddCat =new JButton("Add Category");
            btDelCat =new JButton("Delete Category");
            btAddCost =new JButton("Add Cost Item");
            btDReport =new JButton("Detailed Report");
            btDPieChart =new JButton("Pie Chart");

            //creating jlabel
            lbTitle = new JLabel("Cost Manager");
            s = new JLabel("M&L");

        }

        public void start() {

            //handling window closing
            frameHome.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            //panels setting layout
            panelHome.setLayout(new GridLayout(1,2));
            panelMain.setLayout(new GridLayout(5,1));
            panelCenter.setLayout(new BorderLayout());
            panelCenterTop.setLayout(new FlowLayout(1,0,20));
            panelCenterBottom.setLayout(new FlowLayout(1,0,0));
            panelIcon.setLayout(new FlowLayout(1,0,0));
            panelImage.setLayout(new FlowLayout(1,0,0));
            panelS.setLayout(new FlowLayout(2,10,0));

            // title for panel center top
            lbTitle.setFont(new Font("mv Boli", Font.PLAIN,33));
            panelTitle.add(lbTitle);

            //s for panel center bottom
            s.setFont(new Font("mv Boli", Font.PLAIN,10));
            panelS.add(s);

            //adding buttons to panel center bottom
            panelMain.add(panelTitle);
            panelMain.add(btAddCat);
            panelMain.add(btDelCat);
            panelMain.add(btAddCost);
            panelMain.add(btDReport);
            panelMain.add(btDPieChart);

            //creating image on panel icon.
            panelImage.setIcon(new ImageIcon(ViewHome.class.getResource("image/1.jpg")));
            panelIcon.add(panelImage);

            //adding panel center top and center bottom to panel center.
            panelCenterTop.add(panelTitle);
            panelCenterBottom.add(panelMain);
            panelCenter.add(panelCenterTop,BorderLayout.NORTH);
            panelCenter.add(panelCenterBottom,BorderLayout.CENTER);
            panelCenter.add(panelS,BorderLayout.SOUTH);

            //adding panel icon and center to panel home.
            panelHome.add(panelIcon);
            panelHome.add(panelCenter);

            //adding panel main to frameHome
            frameHome.add(panelHome);

            // size of panel main
            panelMain.setPreferredSize(new Dimension(170, 200));

            //colors for buttons
            btAddCat.setBackground(new Color(255,99,71));
            btDelCat.setBackground(new Color(255,99,71));
            btAddCost.setBackground(new Color(255,99,71));
            btDReport.setBackground(new Color(255,99,71));
            btDPieChart.setBackground(new Color( 255,99,71));

            //colors for font buttons
            btAddCat.setForeground(new Color(255,255,255));
            btDelCat.setForeground(new Color(255, 255, 255));
            btAddCost.setForeground(new Color(255, 255, 255));
            btDReport.setForeground(new Color(255, 255, 255));
            btDPieChart.setForeground(new Color(255, 255, 255));
            btAddCat.setBorderPainted(true);

            //border of buttons
            Border b = BorderFactory.createLineBorder(new Color(255, 255, 255),2);
            btAddCat.setBorder(b);
            btDelCat.setBorder(b);
            btAddCost.setBorder(b);
            btDReport.setBorder(b);
            btDPieChart.setBorder(b);
            Border b1 = BorderFactory.createLineBorder(new Color(255,99,71),3);
            panelHome.setBorder(b1);

            //font of buttons
            Font f = new Font("mv Boli", Font.BOLD, 18);
            btAddCat.setFont(f);
            btDelCat.setFont(f);
            btAddCost.setFont(f);
            btDReport.setFont(f);
            btDPieChart.setFont(f);

            //size of buttons
            Dimension d = new Dimension(60, 35);
            btAddCat.setPreferredSize(d);
            btDelCat.setPreferredSize(d);
            btAddCost.setPreferredSize(d);
            btDReport.setPreferredSize(d);
            btDPieChart.setPreferredSize(d);

            //
            btAddCat.setFocusPainted(false);

            //display frame
            frameHome.setSize(700,450);
            frameHome.setResizable(false);
            frameHome.setVisible(true);

            /**
             * hides the home view and displays the add category view.
             */
            //handling add category button click
            btAddCat.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewAddCategory addCategoryView = new ViewAddCategory();
                    addCategoryView.setViewModel(vm);
                    vm.setView(addCategoryView);
                    frameHome.setVisible(false);
                }
            });

            /**
             * hides the home view and displays the delete category view.
             */
            //handling delete category button click
            btDelCat.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    ViewDeleteCategory deleteCategoryView = new ViewDeleteCategory();
                    deleteCategoryView.setViewModel(vm);
                    vm.setView(deleteCategoryView);
                    frameHome.setVisible(false);
               }
            });

            /**
             * hides the home view and displays the add cost view.
             */
            //handling cost item adding button click
            btAddCost.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewAddCostItem costItemView = new ViewAddCostItem();
                    costItemView.setViewModel(vm);
                    vm.setView(costItemView);
                    frameHome.setVisible(false);
                }
            });

            /**
             * hides the home view and displays the DReport view.
             */
            //handling detailed report button click
            btDReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewDReport dReportView = new ViewDReport();
                    dReportView.setViewModel(vm);
                    vm.setDReport(dReportView);
                    vm.setView(dReportView);
                    frameHome.setVisible(false);
                }
            });

            /**
             * hides the home view and displays the pie chart view.
             */
            //handling pie chart button click
            btDPieChart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewPieChart pChartView = new ViewPieChart();
                    pChartView.setViewModel(vm);
                    vm.setPieChart(pChartView);
                    vm.setView(pChartView);
                    frameHome.setVisible(false);
                }
            });
        };
    }
}














                                                /**
                                                                    מגישים:
                                                 מור כהן - 204272231
                                                 ליאור משולם - 204478721
                                                 **/







