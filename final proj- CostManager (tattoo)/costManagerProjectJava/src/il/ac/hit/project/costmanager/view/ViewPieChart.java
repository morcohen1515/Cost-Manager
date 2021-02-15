package il.ac.hit.project.costmanager.view;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.viewmodel.IViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * displays a pie chart of expenses of all categories, in a specific time period.
 * here you do not select a category.
 */
public class ViewPieChart implements IView {

    private appUI ui;
    private IViewModel vm1;

    public ViewPieChart() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewPieChart.this.ui = new appUI();
                ViewPieChart.this.ui.start();
            }
        });
    }

    @Override
    public void setViewModel(IViewModel vm) { this.vm1 = vm; }

    @Override
    public void showMessage(String text) { }

    @Override
    public void showItems(LinkedList<Category.CostItem> arrCostItem) { }

    @Override
    public void categories(ArrayList<String> arrCat) { }


    public class appUI {

        private JFrame pCFrame;
        private ChartFrame frame;

        private JPanel panelTop;
        private JPanel panelTitle;
        private JPanel panelMain;
        private JPanel panelWest;
        private JPanel panelDateX;
        private JPanel panelDateY;
        private JPanel panelGetPieChart;
        private JPanel panelButton;
        private JPanel panelButtonBack;

        private JSpinner dateXSpinner;
        private JSpinner dateYSpinner;

        private JLabel lbDateX;
        private JLabel lbDateY;
        private JLabel lbTitle;

        private JButton btn;
        private JButton btBack;

        public appUI() {

            //creating frame
            pCFrame = new JFrame("Cost Manager- Pie Chart");
            pCFrame.setLocation(190,50);

            //creating panels
            panelTop = new JPanel();
            panelTitle = new JPanel();

            panelMain = new JPanel();
            panelWest = new JPanel();
            panelDateX = new JPanel();
            panelDateY = new JPanel();
            panelGetPieChart = new JPanel();
            panelButton = new JPanel();
            panelButtonBack = new JPanel();

            //creating labels
            lbTitle = new JLabel("Pie chart");
            lbDateX = new JLabel("From: ");
            lbDateY = new JLabel("To: ");

            //creating JSpinners
            dateXSpinner = new JSpinner();
            dateXSpinner.setModel(new SpinnerDateModel());
            Dimension d = new Dimension(130,20);
            dateXSpinner.setPreferredSize(d);
            dateYSpinner = new JSpinner();
            dateYSpinner.setModel(new SpinnerDateModel());
            Dimension d1 = new Dimension(130,20);
            dateYSpinner.setPreferredSize(d1);

            // creating buttons
            btn = new JButton("Get Pie Chart");
            btBack = new JButton("Back");
        }

        public void start(){

            //handling window closing
            pCFrame.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            //panel frame layout
            pCFrame.setLayout(new BorderLayout());

            //panel top layout
            panelTop.setLayout(new BorderLayout());

            //panel main layout
            panelMain.setLayout(new BorderLayout());

            //panel button layout
            panelButton.setLayout(new BorderLayout());

            //panels layout
            panelWest.setLayout(new GridLayout(3,1));
            panelDateX.setLayout(new FlowLayout(1,0,40));
            panelDateY.setLayout(new FlowLayout(1,13,0));
            panelButton.setLayout(new FlowLayout(1,0,0));
            panelButtonBack.setLayout(new FlowLayout(2,5,0));

            //adding components to panels (int the top)
            panelTitle.add(lbTitle);

            //adding to panel top
            panelTop.add(panelTitle);

            //adding components to panels (int the west)
            panelDateX.add(lbDateX);
            panelDateX.add(dateXSpinner);
            panelDateY.add(lbDateY);
            panelDateY.add(dateYSpinner);
            panelGetPieChart.add(btn);

            //adding to panel west
            panelWest.add(panelDateX);
            panelWest.add(panelDateY);
            panelWest.add(panelGetPieChart);

            //adding to panel main
            panelMain.add(panelWest,BorderLayout.CENTER);

            //adding components to panels (int the button)
            panelButtonBack.add(btBack);

            //adding to panel button
            panelButton.add(panelButtonBack);

            //adding panels to frame
            pCFrame.add(panelTop, BorderLayout.NORTH);
            pCFrame.add(panelMain, BorderLayout.CENTER);
            pCFrame.add(panelButton, BorderLayout.SOUTH);
            panelButton.setLayout(new FlowLayout(3,10,10));

            //back ground color for panels
            panelTop.setBackground(new Color(245,245,245));
            panelTitle.setBackground(new Color(245,245,245));
            panelWest.setBackground(new Color(245,245,245));
            panelDateX.setBackground(new Color(245,245,245));
            panelDateY.setBackground(new Color(245,245,245));
            panelGetPieChart.setBackground(new Color(245,245,245));
            panelButton.setBackground(new Color(245,245,245));
            panelMain.setBackground(new Color(245,245,245));
            panelButtonBack.setBackground(new Color(245,245,245));

            // label font customize
            lbTitle.setFont(new Font("mv Boli", Font.BOLD, 30));

            /**
             * gets all reports from all categories in the given time period, and displays them in a pie chart.
             * pie chart displays all categories and there total cost reports, in the given time period.
             * displays them through the showPieChart method.
             * @see showPieChart .
             */
            //handling button click
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Date dateX= (Date)dateXSpinner.getValue();
                    Date dateY= (Date)dateYSpinner.getValue();

                    vm1.getPieChart(dateX,dateY);
                }
            });

            /**
             * hides the window and returns to home view.
             */
            //handling button back click
            btBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(frame != null){
                        frame.setVisible(false);
                    }
                    ViewHome viewHome = new ViewHome();
                    viewHome.setViewModel(vm1);
                    vm1.setView(viewHome);
                    pCFrame.setVisible(false);
                }
            });

            //display window
            pCFrame.setSize(500, 400);
            pCFrame.setVisible(true);
            pCFrame.setLocation(380,150);

        }
    }

    /**
     * using hamcrest-core1.3 and jfreechart-1.0.19 jars for displaying the pie chart.
     * @param arrCat arrayList of category (has two fields name and total sum) and holds all the data needed for displaying the pie chart.
     */
    //display pie chart.
    public void showPieChart(ArrayList<Category> arrCat){

        DefaultPieDataset pieDataset = new DefaultPieDataset();


        for(int i=0; i<arrCat.size(); i++){
            pieDataset.setValue(arrCat.get(i).getName(),arrCat.get(i).getTotalPrice());
        }

        JFreeChart chart = ChartFactory.createPieChart("",pieDataset,true,true,true);
        PiePlot p= (PiePlot)chart.getPlot();
        ui.frame = new ChartFrame("Pie Chart", chart);
        ui.frame.setLocation(380,150);
        ui.frame.setVisible(true);
        ui.frame.setSize(500,400);
    }
}
