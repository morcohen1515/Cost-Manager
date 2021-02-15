package il.ac.hit.project.costmanager.view;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.model.CostManagerException;
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
 * window for deleting a specific category.
 * when deleting category you also delete all the cost items it has.
 */

public class ViewDeleteCategory implements IView {

    private IViewModel vm;
    private ViewDeleteCategory.appUI ui;

    public ViewDeleteCategory() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewDeleteCategory.this.ui = new appUI();
                ui.start();
            }
        });
    }

    @Override
    public void setViewModel(IViewModel vm) { this.vm = vm; }

    @Override
    public void showMessage(String text) {  ui.showMessage(text); }

    @Override
    public void showItems(LinkedList<Category.CostItem> arrCostItem) {  }

    @Override
    public void categories(ArrayList<String> arrCat) {
        ui.setArrCategoriesToComboBox(arrCat);
    }

    public class appUI {

        private JFrame frameDelCat;

        private JPanel panelMain;
        private JPanel panelBottom;
        private JPanel panelTitle;
        private JPanel panelTextField;
        private JPanel panelbtDel;
        private JPanel pannelMessage;

        private JButton btDel;
        private JButton btBack;

        private JComboBox<String> cbCategory;

        private JTextField tfMessage;

        private JLabel lbTitle;
        private JLabel lbMessage;

        public appUI (){

            //creating frame
            frameDelCat = new JFrame("Delete Category");
            frameDelCat.setLocation(480,150);

            //creating panels
            panelBottom = new JPanel();
            panelMain = new JPanel();
            panelTitle =new JPanel();
            panelTextField = new JPanel();
            panelbtDel =new JPanel();
            pannelMessage = new JPanel();

            //creating buttons
            btDel = new JButton("Delete");
            btBack = new JButton("Back");

            //creating combo box
            cbCategory = new JComboBox<>();

            //creating text field
            tfMessage = new JTextField(30);

            //creating labels
            lbTitle = new JLabel("Delete Category");
            lbMessage = new JLabel("Message:");

        }

        public void start(){

            //initialization combo box
            vm.setDeleteCategory(ViewDeleteCategory.this);
            vm.getAllCategoriesForComboBoxViewDeleteCategory();

            //handling window closing
            frameDelCat.addWindowListener(new WindowAdapter() {
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

            //panel main layout
            panelMain.setLayout(new GridLayout(4,1));

            //inner panels layout
            panelTitle.setLayout(new FlowLayout(1,0,20));
            panelTextField.setLayout(new FlowLayout(1));
            panelbtDel.setLayout(new FlowLayout(1,0,0));

            //colors for panels
            panelMain.setBackground(new Color(245,245,245));
            panelTitle.setOpaque(false);
            panelTextField.setOpaque(false);
            panelbtDel.setOpaque(false);
            pannelMessage.setOpaque(false);
            panelBottom.setBackground(new Color(245,245,245));

            //colors for text field
            cbCategory.setBackground(new Color(255, 99, 71));

            //tfCategory.setBackground(new Color(60,179,113));
            tfMessage.setBackground(new Color(255,99,71));

            // label font customize
            lbTitle.setFont(new Font("mv Boli", Font.BOLD,20));
            lbMessage.setFont(new Font("mv Boli", Font.BOLD,15));

            //adding components to panels
            panelTitle.add(lbTitle);
            panelTextField.add(cbCategory);
            panelbtDel.add(btDel);
            pannelMessage.add(lbMessage);
            pannelMessage.add(tfMessage);

            //adding panels to main panel
            panelMain.add(panelTitle);
            panelMain.add(panelTextField);
            panelMain.add(panelbtDel);
            panelMain.add(pannelMessage);

            //adding panels to bottom panel
            panelBottom.setLayout( new FlowLayout(3) );

            //adding components to panel button
            panelBottom.add(btBack);

            //frame layout
            frameDelCat.setLayout(new BorderLayout());

            //add panels to frame
            frameDelCat.add(panelMain,BorderLayout.CENTER);
            frameDelCat.add(panelBottom,BorderLayout.SOUTH);

            /**
             * deletes the category from main table (categories), and deletes the category table with all the reports.
             */
            //handling delete button click
            btDel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String AddCategoryName = cbCategory.getSelectedItem().toString();
                        if(AddCategoryName==null || AddCategoryName.length()==0) {
                            throw new CostManagerException("Category not selected.");
                        } else {
                            Category c = new Category(AddCategoryName);
                            vm.deleteCategory(c);
                        }
                    } catch (CostManagerException ex) {
                        ViewDeleteCategory.this.showMessage("deleting category failed "+ex.getMessage());
                    }
                }
            });

            /**
             * hides window and return to home view.
             */
            //handling back button click
            btBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewHome homeView = new ViewHome();
                    homeView.setViewModel(vm);
                    vm.setView(homeView);
                    frameDelCat.setVisible(false);
                }
            });

            //display frame
            frameDelCat.setSize(400,320);
            frameDelCat.setResizable(false);
            frameDelCat.setVisible(true);
        }

        /**
         * small message displays at the bottom of the window, after pressing the delete button.
         * @param text verifies if category deleted successfully or not and specifies the problem.
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

        //method for initializing the combo box, to which category you wish to delete.
        public void setArrCategoriesToComboBox(ArrayList<String> arrCategories) {
            for(int i=0; i<arrCategories.size(); i++){
                this.cbCategory.addItem(arrCategories.get(i));
            }
        }
    }
}
