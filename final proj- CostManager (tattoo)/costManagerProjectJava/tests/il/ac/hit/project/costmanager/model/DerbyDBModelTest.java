package il.ac.hit.project.costmanager.model;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * tests for class DerbyDBModelTest in model.
 * @see DerbyDBModelTest
 */

public class DerbyDBModelTest {

    private static DerbyDBModel dm;

    // this c`tor word before every test.
    @Before
    public void setUp() throws CostManagerException {
        dm = new DerbyDBModel();
    }

    @Test
    public void newCategory() throws CostManagerException {
        // new category.
        Category c = new Category("newCategory");
        //size of categories before insert a new category.
        int sizeCategoryList = dm.getAllCategoriesFromDB().size();
        // add a new category to category table in DB.
        dm.newCategory(c);
        //size of categories after insert a new category.
        int expectedValue = dm.getAllCategoriesFromDB().size();
        // size of category + 1.
        assertEquals("checking if number of category matches after adding a new one.", expectedValue ,sizeCategoryList + 1);
        System.out.println("new Category");
    }

    @Test
    public void deleteCostItem() throws CostManagerException {
        // category to delete.
        Category c = new Category("newCategory");
        // size of categories before delete a category.
        int sizeCategoryList = dm.getAllCategoriesFromDB().size();
        // delete category.
        dm.deleteCategory(c);
        // size of categories after delete a category.
        int expectedValue = dm.getAllCategoriesFromDB().size();
        assertEquals("checking if number of category matches after delete a category", expectedValue ,sizeCategoryList - 1);
        System.out.println("delete Cost Item");
    }

    @Test
    public void addCostItem() throws CostManagerException{
        // new category.
        Category c = new Category("testCategory");
        // add a new category to DB.
        dm.newCategory(c);
        // new cost item.
        Category.CostItem r = c.new CostItem("test", 15, "test");
        // add a new cost item to "testCategory" category.
        dm.addCostItem(r);
        assertNotNull("checking if not null.",dm.getDReport(c));
        System.out.println("add Cost Item");
    }

    @Test
    public void getDReport() throws CostManagerException {
        // new category.
        Category c = new Category("testCategory");
        assertNotNull("checking if number of cost items if not null.", dm.getDReport(c));
        System.out.println("get DReport");
    }

    @AfterClass
    public static void tearDown() throws CostManagerException{
        Category c = new Category("testCategory"); // new category to delete.
        dm.deleteCategory(c);                            // delete category from DB.
        System.out.println("tear down");
    }
}