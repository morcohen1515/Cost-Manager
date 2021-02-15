package il.ac.hit.project.costmanager.view;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.model.CostManagerException;
import il.ac.hit.project.costmanager.viewmodel.IViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * window for viewing all your report for a specific category, in a specific time period.
 * can also delete reports from this window.
 */
public class ViewDReport implements IView {

    private appUI ui;
    private IViewModel vm1;

    /**
     *
     */
    public ViewDReport() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewDReport.this.ui = new appUI();
                ViewDReport.this.ui.start();
            }
        });
    }

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm1 = vm;
    }

    @Override
    public void showMessage(String text) { ui.showMessage(text); }

    /**
     * displays all the reports, through the showCostItem method.
     * @param arrCostItem linked list with all the matching reports.
     */
    @Override
    public void showItems(LinkedList<Category.CostItem> arrCostItem) {
       ui.showCostItems(arrCostItem);
    }

    /**
     * gets all the categories from the main table (categories), to display in the combo box.
     * @param arrCat arrayList of string for all the categories.
     */
    @Override
    public void categories(ArrayList<String> arrCat) {
        ui.setArrCategoriesToComboBox(arrCat);
    }

    public class appUI {

        private JFrame frameDR;

        private JTable costItemsTable;

        private DefaultTableModel costItemsModel;

        private JScrollPane scrollPane;

        private JPanel panelTop;
        private JPanel panelTitle;
        private JPanel panelMain;
        private JPanel panelWest;
        private JPanel panelCategory;
        private JPanel panelDateX;
        private JPanel panelDateY;
        private JPanel panelGetReport;
        private JPanel panelDeleteReport;
        private JPanel panelButton;
        private JPanel panelMessage;

        private JLabel lbTitle;
        private JLabel lbCategory;
        private JLabel lbDateX;
        private JLabel lbDateY;
        private JLabel lbMassage;

        private JComboBox<String> cbCategories;

        private JSpinner DReportDateXSpinner;
        private JSpinner DReportDateYSpinner;

        private JButton btGetDReport;
        private JButton btDeleteReport;
        private JButton btBack;

        private JTextField tfMessage;

        public appUI() {

            // create frame
            frameDR = new JFrame("CostManager- Detailed Report");
            frameDR.setLocation(250,100);

            // creating model, table and scroll pane
            costItemsModel = new DefaultTableModel();
            costItemsTable = new JTable(costItemsModel);
            scrollPane = new JScrollPane(costItemsTable);

            //creating panels
            panelTop = new JPanel();
            panelTitle = new JPanel();
            panelMain = new JPanel();
            panelWest = new JPanel();
            panelCategory = new JPanel();
            panelDateX = new JPanel();
            panelDateY = new JPanel();
            panelGetReport = new JPanel();
            panelDeleteReport = new JPanel();
            panelButton = new JPanel();
            panelMessage = new JPanel();

            //creating labels
            lbTitle = new JLabel("Detailed Report");
            lbCategory = new JLabel("Category name: ");
            lbDateX = new JLabel("From: ");
            lbDateY = new JLabel("To: ");
            lbMassage = new JLabel("Massage: ");

            // creating JSpinners
            DReportDateXSpinner = new JSpinner();
            DReportDateXSpinner.setModel(new SpinnerDateModel());
            Dimension d2 = new Dimension(130, 20);
            DReportDateXSpinner.setPreferredSize(d2);
            DReportDateYSpinner = new JSpinner();
            DReportDateYSpinner.setModel(new SpinnerDateModel());
            Dimension d3 = new Dimension(130, 20);
            DReportDateYSpinner.setPreferredSize(d3);

            //creating combo box
            cbCategories = new JComboBox();

            // creating get report button
            btGetDReport = new JButton("Get Report");

            // creating delete report button
            btDeleteReport = new JButton("Delete Report");

            // creating back button
            btBack = new JButton("Back");

            //creating text filed for massage
            tfMessage = new JTextField(30);
        }

        public void start() {

            //initialization combo box
            vm1.setDReport(ViewDReport.this);
            vm1.getAllCategoriesForComboBoxViewDReport();

            //handling window closing
            frameDR.addWindowListener(new WindowAdapter() {
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

            //panel frame layout
            frameDR.setLayout(new BorderLayout());

            //panel top layout
            panelTop.setLayout(new BorderLayout());

            //panel main layout
            panelMain.setLayout(new BorderLayout());

            //panel button layout
            panelButton.setLayout(new BorderLayout());

            //panel west layout
            panelWest.setLayout(new GridLayout(5, 1));
            panelCategory.setLayout(new FlowLayout(3, 5, 0));
            panelDateX.setLayout(new FlowLayout(3, 5, 0));
            panelDateY.setLayout(new FlowLayout(3, 12, 0));
            panelButton.setLayout(new FlowLayout(3, 10, 10));
            panelGetReport.setLayout(new FlowLayout(1, 5, 0));
            panelDeleteReport.setLayout(new FlowLayout(1, 5, 0));

            // add columns to model table
            costItemsModel.addColumn("Sum");
            costItemsModel.addColumn("Currency");
            costItemsModel.addColumn("Description");
            costItemsModel.addColumn("Date");

            //adding components to top panel
            panelTitle.add(lbTitle);
            panelTop.add(panelTitle);

            //adding components to west panel
            panelWest.add(cbCategories);
            panelWest.add(DReportDateXSpinner);
            panelWest.add(DReportDateYSpinner);
            panelWest.add(btGetDReport);
            panelWest.add(btDeleteReport);

            //adding components to panels(in the button)
            panelMessage.add(lbMassage);
            panelMessage.add(tfMessage);

            //adding components to button panel
            panelButton.add(btBack, BorderLayout.WEST);
            panelButton.add(panelMessage);

            //adding components to main panel
            panelMain.add(panelWest, BorderLayout.WEST);
            panelMain.add(scrollPane, BorderLayout.CENTER);

            //adding components to panels(in the west)
            panelCategory.add(lbCategory);
            panelCategory.add(cbCategories);
            panelDateX.add(lbDateX);
            panelDateX.add(DReportDateXSpinner);
            panelDateY.add(lbDateY);
            panelDateY.add(DReportDateYSpinner);
            panelGetReport.add(btGetDReport);
            panelDeleteReport.add(btDeleteReport);

            //adding to panel west
            panelWest.add(panelCategory);
            panelWest.add(panelDateX);
            panelWest.add(panelDateY);
            panelWest.add(panelGetReport);
            panelWest.add(panelDeleteReport);

            //adding panels to frame
            frameDR.add(panelTop, BorderLayout.NORTH);
            frameDR.add(panelWest, BorderLayout.WEST);
            frameDR.add(panelMain, BorderLayout.CENTER);
            frameDR.add(panelButton, BorderLayout.SOUTH);

            //back ground color for panels
            panelTop.setBackground(new Color(245,245,245));
            panelTitle.setBackground(new Color(245,245,245));
            panelWest.setBackground(new Color(245,245,245));
            panelCategory.setBackground(new Color(245,245,245));
            panelDateX.setBackground(new Color(245,245,245));
            panelDateY.setBackground(new Color(245,245,245));
            panelButton.setBackground(new Color(245,245,245));
            panelMain.setBackground(new Color(245,245,245));
            panelButton.setBackground(new Color(245,245,245));
            panelGetReport.setBackground(new Color(245,245,245));
            panelDeleteReport.setBackground(new Color(245,245,245));
            panelMessage.setBackground(new Color(245,245,245));

            // color for table
            costItemsTable.setSelectionBackground(Color.GREEN);
            costItemsTable.setForeground(Color.black);

            //color for massage text field
            tfMessage.setBackground(new Color(255, 99, 71));

            // color for DRTable
            costItemsTable.setBackground(new Color(255, 99, 71));

            // label font customize
            lbTitle.setFont(new Font("mv Boli", Font.BOLD, 30));
            lbMassage.setFont(new Font("mv Boli", Font.BOLD, 15));

            /**
             * gets all reports of a category in the given time period.
             * displays reports through showCostItem method.
             * @see showCostItems .
             */
            //handling get report button click
            btGetDReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Category c = null;

                    try {
                        c = new Category(cbCategories.getSelectedItem().toString());
                        Date dateX = (Date) DReportDateXSpinner.getValue();
                        Date dateY = (Date) DReportDateYSpinner.getValue();

                        vm1.getDReport(dateX, dateY, c);
                    } catch (CostManagerException costManagerException) {
                        costManagerException.printStackTrace();
                    }

                }
            });

            /**
             * gets the selected report from the Jtable component, and deletes the cost item from the category table.
             */
            //handling delete report button click
            btDeleteReport.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Category c = null;
                    try {
                        c = new Category(cbCategories.getSelectedItem().toString());
                    } catch (CostManagerException costManagerException) {
                        costManagerException.printStackTrace();
                    }
                    int i = costItemsTable.getSelectedRow();
                    if(i!=-1){
                        double sum = Double.parseDouble(costItemsTable.getValueAt(i,0).toString());
                        String des = costItemsTable.getValueAt(i,2).toString();
                        String currency = costItemsTable.getValueAt(i,1).toString();
                        Timestamp date = new Timestamp (System.currentTimeMillis());
                        date = Timestamp.valueOf(costItemsTable.getValueAt(i,3).toString());

                        try {
                            vm1.deleteCostItem(c.new CostItem(sum,currency,des,date));
                        } catch (CostManagerException costManagerException) {
                            costManagerException.printStackTrace();
                        }
                        costItemsModel.removeRow(costItemsTable.getSelectedRow());
                    }else{
                        ViewDReport.this.showMessage("no items selected.");
                    }
                }
            });

            /**
             * hides the window and return to home view.
             */
            //handling back button click
            btBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewHome viewHome = new ViewHome();
                    viewHome.setViewModel(vm1);
                    vm1.setView(viewHome);
                    frameDR.setVisible(false);
                }
            });

            //display frame
            frameDR.setSize(850, 500);
            frameDR.setResizable(false);
            frameDR.setVisible(true);
        }

        /**
         * small message displays at the bottom of the window, after pressing the add/delete button.
         * @param text verifies if the cost item was added/deleted successfully or not and specifies the problem.
         */
        //display message
        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tfMessage.setText(text);
                    }
                });
            }
        }

        /**
         * displays reports through the Jtable component.
         * @param arrCostItem linked list with all the matching cost items.
         */
        //display cost item
        public void showCostItems(LinkedList<Category.CostItem> arrCostItem) {

            costItemsModel.setRowCount(0);

            for (Category.CostItem i : arrCostItem) {
                costItemsModel.addRow(new Object[]{i.getSum(), i.getCurrency(), i.getDescription(), i.getDate()});
            }
        }

        //method for initializing the combo box, from which category you wish to get the reports from.
        public void setArrCategoriesToComboBox(ArrayList<String> arrCategories) {
            for(int i=0; i<arrCategories.size(); i++){
                this.cbCategories.addItem(arrCategories.get(i));
            }
        }
    }
}

