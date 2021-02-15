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
 * window for adding a new category to the database.
 * adds new category to main table (categories), and creates new table for the new category.
 */
public class ViewAddCategory implements IView {

    private IViewModel vm;
    private ViewAddCategory.appUI ui;

    public ViewAddCategory(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewAddCategory.this.ui = new appUI();
                ui.start();
            }
        });
    }

    @Override
    public void setViewModel(IViewModel vm) {this.vm = vm; }

    @Override
    public void showMessage(String text) {  ui.showMessage(text); }

    @Override
    public void showItems(LinkedList<Category.CostItem> arrCostItem) { }

    @Override
    public void categories(ArrayList<String> arrCat) { }


    public class appUI {

        private JFrame frameAddCat;

        private JPanel panelMain;
        private JPanel panelBottom;
        private JPanel panelTitle;
        private JPanel panelTextField;
        private JPanel panelbtAdd;
        private JPanel pannelMessage;

        private JButton btAdd;
        private JButton btBack;

        private JTextField tfCategory;
        private JTextField tfMessage;

        private JLabel lbTitle;
        private JLabel lbMessage;

        public appUI (){

            //creating frame
            frameAddCat = new JFrame("Add New Category");
            frameAddCat.setLocation(480,150);

            //creating panels
            panelBottom = new JPanel();
            panelMain = new JPanel();
            panelTitle =new JPanel();
            panelTextField = new JPanel();
            panelbtAdd =new JPanel();
            pannelMessage = new JPanel();

            //creating buttons
            btAdd = new JButton("Add");
            btBack = new JButton("Back");

            //creating text field
            tfCategory =new JTextField(20);
            tfMessage = new JTextField(30);

            //creating labels
            lbTitle = new JLabel("Add Category");
            lbMessage = new JLabel("Message:");
        }

        public void start(){

            //handling window closing
            frameAddCat.addWindowListener(new WindowAdapter() {
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

            //panels layout
            panelMain.setLayout(new GridLayout(4,1));
            panelTitle.setLayout(new FlowLayout(1,0,20));
            panelTextField.setLayout(new FlowLayout(1));
            panelbtAdd.setLayout(new FlowLayout(1,0,0));

            //colors for text field
            tfCategory.setBackground(new Color(255,99,71));
            tfMessage.setBackground(new Color(255,99,71));

            // label font customize
            lbTitle.setFont(new Font("mv Boli", Font.BOLD,20));
            lbMessage.setFont(new Font("mv Boli", Font.BOLD,15));

            // button font customize
            btBack.setFont(new Font("mv Boli", Font.BOLD,13));
            btAdd.setFont(new Font("mv Boli", Font.BOLD,13));

            //adding components to panels
            panelTitle.add(lbTitle);
            panelTextField.add(tfCategory);
            panelbtAdd.add(btAdd);
            pannelMessage.add(lbMessage);
            pannelMessage.add(tfMessage);

            //adding panels to main panel
            panelMain.add(panelTitle);
            panelMain.add(panelTextField);
            panelMain.add(panelbtAdd);
            panelMain.add(pannelMessage);

            //set layout for panel bottom.
            panelBottom.setLayout( new FlowLayout(3,10,10) );

            //adding components to panel button
            panelBottom.add(btBack);

            //frame layout
            frameAddCat.setLayout(new BorderLayout());

            //add panels to frame
            frameAddCat.add(panelMain,BorderLayout.CENTER);
            frameAddCat.add(panelBottom,BorderLayout.SOUTH);

            /**
             * every category must have a name.
             * validates the name field before creating the new category.
             */
            //handling add button click
            btAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String addCategoryName = tfCategory.getText();

                        //check if category name include space
                        for(int i=0; i<addCategoryName.length(); i++){
                            if(addCategoryName.charAt(i) == ' '){
                                throw new CostManagerException("Category name not valid");
                            }
                        }
                        //Check if the category name exists
                        if(addCategoryName.length()==0) {
                            throw new CostManagerException("Category name not valid.");
                        } else {
                            Category c = new Category(addCategoryName);
                            vm.newCategory(c);
                        }
                    } catch (CostManagerException ex) {
                        ViewAddCategory.this.showMessage("Adding category failed "+ex.getMessage());
                    }
                }
            });

            /**
             * hides window and returns to home view.
             */
            //handling back button click
            btBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewHome homeView = new ViewHome();
                    homeView.setViewModel(vm);
                    vm.setView(homeView);
                    frameAddCat.setVisible(false);
                }
            });

            //display frame
            frameAddCat.setSize(400,320);
            frameAddCat.setResizable(false);
            frameAddCat.setVisible(true);
        }

        /**
         * small message displays at the bottom of the window, after pressing the add button.
         * @param text verifies if the category was added successfully or not and specifies the problem.
         */
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
    }
}
