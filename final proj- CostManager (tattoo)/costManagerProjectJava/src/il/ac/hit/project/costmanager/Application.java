package il.ac.hit.project.costmanager;

import il.ac.hit.project.costmanager.model.CostManagerException;
import il.ac.hit.project.costmanager.model.DerbyDBModel;
import il.ac.hit.project.costmanager.model.IModel;
import il.ac.hit.project.costmanager.view.IView;
import il.ac.hit.project.costmanager.view.ViewHome;
import il.ac.hit.project.costmanager.viewmodel.IViewModel;
import il.ac.hit.project.costmanager.viewmodel.ViewModel;


/**
 * from here the program starts to run.
 */
public class Application {

    public static void main(String[] args) throws CostManagerException {

        //creating the application components
        IModel model = new DerbyDBModel();
        IView viewHome = new ViewHome();
        IViewModel vm = new ViewModel();

        //connecting the components with each other
        viewHome.setViewModel(vm);
        vm.setModel(model);
        vm.setView(viewHome);
    }
}
