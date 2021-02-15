package il.ac.hit.project.costmanager.view;

import il.ac.hit.project.costmanager.model.Category.CostItem;
import il.ac.hit.project.costmanager.viewmodel.IViewModel;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * every view for this program has to implement these basic methods.
 */
public interface IView {
    public void setViewModel(IViewModel vm);
    public void showMessage(String text);
    public void showItems(LinkedList<CostItem> arrCostItem);
    public void categories(ArrayList<String> arrCat);
}
