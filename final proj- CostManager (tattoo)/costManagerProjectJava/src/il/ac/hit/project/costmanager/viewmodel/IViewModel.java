package il.ac.hit.project.costmanager.viewmodel;

import il.ac.hit.project.costmanager.model.Category;
import il.ac.hit.project.costmanager.model.IModel;
import il.ac.hit.project.costmanager.view.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * all the basic methods the ViewModel should implement.
 */
public interface IViewModel {

    public void setView(IView view);
    public void setModel(IModel model);
    public void setDReport(ViewDReport dReport);
    public void setPieChart(ViewPieChart pieChart);
    public void setDeleteCategory(ViewDeleteCategory viewDeleteCategory);
    public void setAddCostItem(ViewAddCostItem viewAddCostItem);
    public void addCostItem(Category.CostItem item);
    public void deleteCostItem(Category.CostItem r);
    public void newCategory(Category category);
    public void deleteCategory(Category category);
    public void getDReport(Date x, Date y, Category category);
    public void getPieChart(Date dateX, Date dateY);
    public void getAllCategoriesForComboBoxViewDeleteCategory();
    public void getAllCategoriesForComboBoxViewAddCostItem();
    public void getAllCategoriesForComboBoxViewDReport();

}
