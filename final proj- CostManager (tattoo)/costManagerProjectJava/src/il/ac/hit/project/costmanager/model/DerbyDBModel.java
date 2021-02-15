package il.ac.hit.project.costmanager.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * handles all communication with the derby database.
 * every override method starts with connection to the database.
 * database has main table for its categories, and every category has its own table for all cost items.
 */

public class DerbyDBModel implements IModel{
    //variable to connect to the database.
    //make sure it listens to port 1527.
    public static String driver = "org.apache.derby.jdbc.ClientDriver";
    public static String protocol = "jdbc:derby://localhost:1527/gagamoDB;create=true";
    //variable use for first time running.
    public final static String tableAlreadyExistsCode = "X0Y32";

    public DerbyDBModel() throws CostManagerException {

        //if first time running needs to create the categories table.
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            statement.execute("CREATE TABLE categories (name VARCHAR(30))");


        }catch (SQLException e  ) {
            if(!e.getSQLState().equals(tableAlreadyExistsCode)) {     // else do nothing
                try {
                    throw new CostManagerException("Problem with creating categories table.");
                } catch (CostManagerException e1) {
                    throw new CostManagerException(e.getMessage(),e.getCause());
                }
            }
        }finally {
            if(statement!=null) try{ statement.close(); }catch(Exception e) { }
            if(connection!=null) try{ connection.close(); }catch(Exception e) { }
        }

    }

    /**
     * adds the cost item to the correct category table.
     * @param costItem is the cost item to be added.
     * @throws CostManagerException
     */
    @Override
    public void addCostItem(Category.CostItem costItem) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection=null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            statement.executeUpdate("INSERT INTO "+costItem.getCategoryName()+" (DESCRIPTION, PRICE, CURRENCY, DATE) VALUES('"+costItem.getDescription()+"','"+costItem.getSum()+"','"+costItem.getCurrency()+"','"+costItem.getDate()+"')");

        } catch (ClassNotFoundException | SQLException e) {
            throw new CostManagerException(e.getMessage(),e.getCause());

        }finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
        }
        
    }

    /**
     * deletes cost item from the correct category table.
     * @param costItem the cost item to be deleted.
     * @throws CostManagerException
     */
    @Override
    public void deleteCostItem(Category.CostItem costItem) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM " + costItem.getCategoryName() + " WHERE date = '" + costItem.getDate() + "'");
        } catch (ClassNotFoundException | SQLException e) {
            throw new CostManagerException(e.getMessage(),e.getCause());

        } finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
        }

    }

    /**
     * adds new category to main table (categories), and creates new table for the new category.
     * @param category the new category to be added.
     * @throws CostManagerException
     */
    @Override
    public void newCategory(Category category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs= null;

        try {
            connection=null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT NAME FROM CATEGORIES");
            
            while (rs.next()){
                if(rs.getString(1).equals(category.getName())){
                    throw new CostManagerException("category already exists.");
                }
            }
            
            statement.execute("INSERT INTO CATEGORIES (NAME) VALUES ('"+category.getName()+"')");
            statement.execute("CREATE TABLE "+category.getName()+" (price VARCHAR(30), currency VARCHAR(10), description VARCHAR(100), date TIMESTAMP) ");

        } catch (ClassNotFoundException | SQLException e) {
            throw new CostManagerException(e.getMessage(),e.getCause());
        }finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{ rs.close(); } catch(Exception e) {}
        }

    }

    /**
     * deletes category from main table (categories), and also deletes the category table with all the reports in it.
     * @param category catefory to be deleted.
     * @throws CostManagerException
     */
    @Override
    public void deleteCategory(Category category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection=null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            statement.executeUpdate( "DELETE FROM CATEGORIES WHERE NAME = '"+category.getName()+"'");
            statement.executeUpdate( "DROP TABLE "+category.getName());

        } catch (ClassNotFoundException | SQLException e){
            throw new CostManagerException(e.getMessage(),e.getCause());

        }finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * gets all reports from a specific category.
     * reports are filtered in the view-model according to the given dates.
     * @param category get reports from this category.
     * @return linked list with all reports in the category.
     * @throws CostManagerException
     */
    @Override
    public LinkedList<Category.CostItem> getDReport(Category category) throws CostManagerException {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection=null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT * FROM "+category.getName()+"");

            LinkedList<Category.CostItem> arrCostItem = new LinkedList<>();
            while (rs.next()){
                Category.CostItem item = category.new CostItem(Double.parseDouble(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getTimestamp(4));
                arrCostItem.add(item);
            }

            return arrCostItem;

        } catch (ClassNotFoundException | SQLException e) {
            throw new CostManagerException(e.getMessage(),e.getCause());
        } finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{ rs.close(); } catch(Exception e) {}
        }
    }

    /**
     * gets all reports from all categories, in the given time period.
     * @param dateX date from...
     * @param dateY date to...
     * @return arrayList of categories, each slot has category name and total sum (of all reports in that category).
     * @throws CostManagerException
     */
    @Override
    public ArrayList<Category> getPieChart(Date dateX, Date dateY) throws CostManagerException {
        Connection connection = null;
        Statement statement = null, statement1 = null;
        ResultSet rs = null, rs1 = null;

        try {
            connection=null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();
            
            rs = statement.executeQuery("SELECT * FROM CATEGORIES");

            statement1 = connection.createStatement();
            ArrayList<Category> arrCat = new ArrayList<>();
            int i=0;
            while (rs.next()){
                rs1 = statement1.executeQuery("SELECT * FROM "+rs.getString(1));
                arrCat.add(new Category(rs.getString(1)));
                while(rs1.next()){
                    if(rs1.getDate(4).after(dateX) && rs1.getDate(4).before(dateY)){
                        arrCat.get(i).addTotalPrice(rs1.getDouble(1));
                    }
                }
                i++;
            }
            return arrCat;

        } catch (ClassNotFoundException | SQLException e) {
            throw new CostManagerException(e.getMessage(),e.getCause());
        } finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{ rs.close(); } catch(Exception e) {}
        }

    }

    /**
     * gets all categories from main table (categories).
     * @return arrayList of string with all categories.
     * method mainly used for initializing the combo box in the view.
     * @throws CostManagerException
     */
    @Override
    public ArrayList<String> getAllCategoriesFromDB() throws CostManagerException{

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection=null;
            Class.forName(driver);

            connection = DriverManager.getConnection(protocol);
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT NAME FROM CATEGORIES");
            ArrayList<String> arrCategoryName=new ArrayList<>();
            while(rs.next()){
                arrCategoryName.add(rs.getString(1));
            }

            return arrCategoryName;

        } catch (ClassNotFoundException | SQLException e) {
            throw new CostManagerException(e.getMessage(),e.getCause());
        } finally {
            if(statement!=null) try{statement.close();}catch(Exception e){}
            if(connection!=null) try{connection.close();}catch(Exception e){}
            if(rs!=null) try{ rs.close(); } catch(Exception e) {}
        }
    }
}
