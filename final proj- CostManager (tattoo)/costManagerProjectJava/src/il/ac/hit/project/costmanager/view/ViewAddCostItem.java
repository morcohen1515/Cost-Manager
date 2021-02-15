package il.ac.hit.project.costmanager.view;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.model.CostManagerException;
import il.ac.hit.project.costmanager.model.Currency;
import il.ac.hit.project.costmanager.viewmodel.IViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * window for adding a new cost item to its correct category.
 */
public class ViewAddCostItem implements IView {

    private IViewModel vm;
    private appUI ui;

    public ViewAddCostItem(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewAddCostItem.this.ui = new ViewAddCostItem.appUI();
                ui.start();
            }
        });
    }

    @Override
    public void setViewModel(IViewModel vm) { this.vm = vm; }

    @Override
    public void showMessage(String text) { ui.showMessage(text); }

    @Override
    public void showItems(LinkedList<Category.CostItem> arrCostItem) {  }

    @Override
    public void categories(ArrayList<String> arrCat) {
        ui.setArrCategoriesToComboBox(arrCat);
    }


    public class appUI {

        private JFrame frameCostItem;

        private JPanel panelMain;
        private JPanel panelBottom;
        private JPanel panelTitle;
        private JPanel panelCategoryName;
        private JPanel panelTextFieldSum;
        private JPanel panelTextFieldDescription;
        private JPanel panelComboCurrency;
        private JPanel panelbtAdd;
        private JPanel panelMessage;

        private JButton btAddCost;
        private JButton btBack;

        private JComboBox<String> cbCategory;
        private JComboBox<String> cbCurrency;

        private JTextField tfMessage;
        private JTextField tfSum;
        private JTextField tfDescription;

        private JLabel lbTitle;
        private JLabel lbCategoryName;
        private JLabel lbSum;
        private JLabel lbDescription;
        private JLabel lbCurrency;
        private JLabel lbMessage;

        public appUI (){

            //creating frame
            frameCostItem = new JFrame("Add cost item");
            frameCostItem.setLocation(380,150);

            //creating panels
            panelMain = new JPanel();
            panelBottom = new JPanel();
            panelTitle = new JPanel();
            panelCategoryName = new JPanel();
            panelTextFieldSum = new JPanel();
            panelTextFieldDescription = new JPanel();
            panelComboCurrency = new JPanel();
            panelbtAdd = new JPanel();
            panelMessage = new JPanel();

            //creating buttons
            btAddCost = new JButton("Add");
            btBack = new JButton("Back");

           //creating combo box
            cbCategory = new JComboBox<>();
            cbCurrency = new JComboBox(Currency.values());

            //creating text field
            tfMessage = new JTextField(30);
            tfSum = new JTextField(20);
            tfDescription = new JTextField(20);

            //creating labels
            lbTitle = new JLabel("New cost item");
            lbCategoryName = new JLabel("Category name: ");
            lbSum = new JLabel("Sum: ");
            lbDescription = new JLabel("Description: ");
            lbCurrency = new JLabel("Currency: ");
            lbMessage = new JLabel("Message: ");
        }

        public void start(){

            //initialization combo box
            vm.setAddCostItem(ViewAddCostItem.this);
            vm.getAllCategoriesForComboBoxViewAddCostItem();

            //handling window closing
            frameCostItem.addWindowListener(new WindowAdapter() {
                /**
                 * Invoked when a window is in the process of being closed.
                 * The close operation can be overridden at this point.
                 *
                 * @param e
                 */
                @Override
                public void windowClosing(WindowEvent e) { System.exit(0);
                }
            });

            //panel main layout
            panelMain.setLayout(new GridLayout(7,1));

            //inner panels layout
            panelTitle.setLayout(new FlowLayout(1,0,5));
            panelCategoryName.setLayout(new FlowLayout(1,0,23));
            panelTextFieldSum.setLayout(new FlowLayout(1,42,20));
            panelTextFieldDescription.setLayout(new FlowLayout(1,0,20));
            panelComboCurrency.setLayout(new FlowLayout(1,0,18));
            panelbtAdd.setLayout(new FlowLayout(1,0,16));

            //colors for panels
            panelMain.setBackground(new Color(245,245,245));
            panelBottom.setBackground(new Color(245,245,245));
            panelTitle.setBackground(new Color(245,245,245));
            panelCategoryName.setBackground(new Color(245,245,245));
            panelTextFieldSum.setBackground(new Color(245,245,245));
            panelTextFieldDescription.setBackground(new Color(245,245,245));
            panelComboCurrency.setBackground(new Color(245,245,245));
            panelbtAdd.setBackground(new Color(245,245,245));
            panelMessage.setBackground(new Color(245,245,245));

            //colors for text field
            tfSum.setBackground(new Color(255, 99, 71));
            tfDescription.setBackground(new Color(255, 99, 71));
            tfMessage.setBackground(new Color(255, 99, 71));

            // color for combo box
            cbCategory.setBackground(new Color(230,230,250));
            cbCurrency.setBackground(new Color(230,230,250));

            // label font customize
            lbTitle.setFont(new Font("mv Boli", Font.BOLD,30));
            lbCategoryName.setFont(new Font("mv Boli", Font.BOLD,14));
            lbSum.setFont(new Font("mv Boli", Font.BOLD,14));
            lbDescription.setFont(new Font("mv Boli", Font.BOLD,14));
            lbCurrency.setFont(new Font("mv Boli", Font.BOLD,14));
            lbMessage.setFont(new Font("mv Boli", Font.BOLD,14));

            //adding components to panels
            panelTitle.add(lbTitle);
            panelCategoryName.add(lbCategoryName);
            panelCategoryName.add(cbCategory);
            panelTextFieldSum.add(lbSum);
            panelTextFieldSum.add(tfSum);
            panelTextFieldDescription.add(lbDescription);
            panelTextFieldDescription.add(tfDescription);
            panelComboCurrency.add(lbCurrency);
            panelComboCurrency.add(cbCurrency);
            panelbtAdd.add(btAddCost);
            panelMessage.add(lbMessage);
            panelMessage.add(tfMessage);

            //adding panels to main panel
            panelMain.add(panelTitle);
            panelMain.add(panelCategoryName);
            panelMain.add(panelTextFieldSum);
            panelMain.add(panelTextFieldDescription);
            panelMain.add(panelComboCurrency);
            panelMain.add(panelbtAdd);

            //adding layout to bottom panel
            panelBottom.setLayout( new FlowLayout(3) );

            //adding components to panel button
            panelBottom.add(btBack);
            panelBottom.add(panelMessage);

            //frame layout
            frameCostItem.setLayout(new BorderLayout());

            //add panels to frame
            frameCostItem.add(panelMain,BorderLayout.CENTER);
            frameCostItem.add(panelBottom,BorderLayout.SOUTH);

            /**
             * every cost item must have sum, description, currency and date.
             * validating fields before inputting the new cost item.
             * adds the new cost item to the correct category table.
             */
            //handling Add cost item button click
            btAddCost.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String categoryName = cbCategory.getSelectedItem().toString();
                        if( categoryName==null || categoryName.length()==0 ) {
                            throw new CostManagerException("category Name cannot be empty");
                        }
                        String description = tfDescription.getText();
                        if( description==null || description.length()==0 ) {
                            throw new CostManagerException("description cannot be empty");
                        }

                        // check if sum is empty.
                        if(tfSum.getText().length() == 0){
                            throw new CostManagerException("sum cannot be empty");
                        }
                        // check if sum include char.
                        for (char ch : tfSum.getText().toCharArray()) {
                            if ( (ch >= 'A' && ch <= 'z')  ) {
                                throw new CostManagerException("sum need to be number.");
                            }
                        }
                        double sum = Double.parseDouble(tfSum.getText());

                        String currency = cbCurrency.getSelectedItem().toString();

                        Category c = new Category(categoryName);
                        Category.CostItem item = c.new CostItem(description, sum, currency);
                        vm.addCostItem(item);

                    } catch (CostManagerException ex) {
                        ViewAddCostItem.this.showMessage("Add cost item failed, "+ex.getMessage());
                    }
                }
            });

            /**
             * hides the window and returns to home view.
             */
            //handling back button click
            btBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewHome homeView = new ViewHome();
                    homeView.setViewModel(vm);
                    vm.setView(homeView);
                    frameCostItem.setVisible(false);
                }
            });

            //display frame
            frameCostItem.setSize(500,400);
            frameCostItem.setResizable(false);
            frameCostItem.setVisible(true);

        }

        /**
         * small message displays at the bottom of the window, after pressing the add button.
         * @param text verifies if the cost item was added successfully or not and specifies the problem.
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

        //method for initializing the combo box, to which category you wish to add the report to.
        public void setArrCategoriesToComboBox(ArrayList<String> arrCategories) {
            for(int i=0; i<arrCategories.size(); i++){
                this.cbCategory.addItem(arrCategories.get(i));
            }
        }
    }
}
