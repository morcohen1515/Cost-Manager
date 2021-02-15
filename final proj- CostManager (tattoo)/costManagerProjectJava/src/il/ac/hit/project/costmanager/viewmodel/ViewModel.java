package il.ac.hit.project.costmanager.viewmodel;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.model.CostManagerException;
import il.ac.hit.project.costmanager.model.IModel;
import il.ac.hit.project.costmanager.view.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * the connection between the View and the Model.
 * all methods here are void.
 * implementing here all the logic behind the view.
 */
public class ViewModel implements IViewModel {

    private IModel model;
    private IView view;
    private ViewDeleteCategory viewDeleteCategory;
    private ViewAddCostItem viewAddCostItem;
    private ViewDReport dReport;
    private ViewPieChart pieChart;
    private ExecutorService pool;

    public ViewModel() {
        pool = Executors.newFixedThreadPool(10);
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public void setDReport(ViewDReport dReport) {
        this.dReport = dReport;
    }

    @Override
    public void setPieChart(ViewPieChart pieChart) {
        this.pieChart = pieChart;
    }

    @Override
    public void setDeleteCategory(ViewDeleteCategory viewDeleteCategory) {
        this.viewDeleteCategory = viewDeleteCategory;
    }

    @Override
    public void setAddCostItem(ViewAddCostItem viewAddCostItem) {
        this.viewAddCostItem = viewAddCostItem;
    }

    /**
     * after validating the cost item, calls the model for adding the cost item to the database.
     * after calling the model displays a message successful or not.
     * @param r the cost item to be added.
     */
    @Override
    public void addCostItem(Category.CostItem r) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.addCostItem(r);
                    view.showMessage("cost item was added successfully");
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * calling the model for deleting from database.
     * after calling the model displays message successful or not.
     * @param r the cost item to be deleted.
     */
    @Override
    public void deleteCostItem(Category.CostItem r) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deleteCostItem(r);
                    view.showMessage("cost item deleted successfully");
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * calls the models for adding the category.
     * after calling the model displays message successful or not.
     * @param category the new category to be added.
     */
    @Override
    public void newCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.newCategory(category);
                    view.showMessage("category added successfully");
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * calls the models for deleting the category.
     * after calling the model displays message successful or not.
     * @param category the category to be deleted.
     */
    @Override
    public void deleteCategory(Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    model.deleteCategory(category);
                    view.showMessage("category deleted successfully");
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * calls the model for getting all the reports.
     * after calling the model, filters them through the given dates.
     * if encounters a problem displays message through the show message method.
     * @param x date from...
     * @param y date to...
     * @param category gets the reports from this category.
     */
    @Override
    public void getDReport(Date x, Date y, Category category) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    LinkedList<Category.CostItem> CostItemsBeforeFilter = new LinkedList<>();
                    LinkedList<Category.CostItem> CostItemsAfterFilter = new LinkedList<>();
                    CostItemsBeforeFilter = model.getDReport(category);

                    for( int i = 0; i < CostItemsBeforeFilter.size(); i++){
                        if( CostItemsBeforeFilter.get(i).getDate().after(x) && CostItemsBeforeFilter.get(i).getDate().before(y) ){
                            CostItemsAfterFilter.add(CostItemsBeforeFilter.get(i));
                        }
                    }
                   dReport.showItems(CostItemsAfterFilter);
                    if(CostItemsAfterFilter.isEmpty()){
                        view.showMessage("no reports from " +category.getName() + " in this date!");
                    } else {
                        view.showMessage("reports of " + category.getName() + "!");
                    }
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * calls the model for getting total cost (of all the reports) of every category.
     * displays the pie chart through the showPieChart method.
     * @param dateX date from...
     * @param dateY date to...
     */
    @Override
    public void getPieChart(Date dateX, Date dateY) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<Category> arrCat = new ArrayList<>();
                    arrCat = model.getPieChart(dateX,dateY);
                    pieChart.showPieChart(arrCat);
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * for initializing the combo box for the delete category view.
     */
    @Override
    public void getAllCategoriesForComboBoxViewDeleteCategory()  {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    viewDeleteCategory.categories(model.getAllCategoriesFromDB());
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * for initializing the combo box for the add cost item view.
     */
    @Override
    public void getAllCategoriesForComboBoxViewAddCostItem() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    viewAddCostItem.categories(model.getAllCategoriesFromDB());
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }

    /**
     * for initializing the combo box for the DReport view.
     */
    @Override
    public void getAllCategoriesForComboBoxViewDReport() {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    dReport.categories(model.getAllCategoriesFromDB());
                } catch(CostManagerException e) {
                    view.showMessage(e.getMessage());
                }
            }
        });
    }
}