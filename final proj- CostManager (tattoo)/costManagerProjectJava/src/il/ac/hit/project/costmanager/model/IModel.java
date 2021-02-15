package il.ac.hit.project.costmanager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * every module for this program needs to implement these basic methods.
 */

public interface IModel {
    public void addCostItem(Category.CostItem r) throws CostManagerException;
    public void deleteCostItem(Category.CostItem r) throws CostManagerException;
    public void newCategory(Category category) throws CostManagerException;
    public void deleteCategory(Category category) throws CostManagerException;
    public LinkedList<Category.CostItem> getDReport(Category category) throws CostManagerException;
    public ArrayList<Category> getPieChart(Date dateX, Date dateY) throws CostManagerException;
    public ArrayList<String> getAllCategoriesFromDB() throws CostManagerException;
}
