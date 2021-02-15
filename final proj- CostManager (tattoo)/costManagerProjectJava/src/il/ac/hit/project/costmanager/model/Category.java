package il.ac.hit.project.costmanager.model;

import java.sql.Timestamp;

/**
 * each cost item is added to a specific category...
 * must initialize with name
 * cost item is an inner class to category.
 * @see CostItem .
 */
public class Category {

    private String name;
    private double totalPrice;

    public Category(String name) throws CostManagerException {
        setName(name);
        setTotalPrice(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws CostManagerException {
        // check if category name is empty.
        if(name.length() == 0){
            throw new CostManagerException("sum cannot be empty");
        }
        //check if category name include space
        for(int i=0; i<name.length(); i++){
            if(name.charAt(i) == ' '){
                throw new CostManagerException("Category name not valid");
            }
        }
        this.name = name;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    //adds the given price to the total price.
    public void addTotalPrice(double totalPrice) {
        this.totalPrice += totalPrice;
    }

    /**
     * every cost item must have sum,description,date and currency.
     * every cost item is an inner class to its category.
     */
    public class CostItem {

        private String description;
        private double sum;
        private String currency;
        private Timestamp date;

        /**
         * constructor for creating new cost item from view add cost item.
         * @param description
         * @param sum
         * @param currency
         * @throws CostManagerException
         */
        public CostItem(String description, double sum, String currency) throws CostManagerException {
            setDescription(description);
            setSum(sum);
            setCurrency(currency);
            date = new Timestamp(System.currentTimeMillis());
        }

        /**
         * constructor is used while retrieving cost item from DB.
         * @param sum
         * @param currency
         * @param description
         * @param dateCost
         * @throws CostManagerException
         */
        public CostItem(double sum, String currency, String description, Timestamp dateCost) throws CostManagerException {
            setDescription(description);
            setSum(sum);
            setCurrency(currency);
            this.date = dateCost;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) throws CostManagerException {
            if( description==null || description.length()==0 ) {
                throw new CostManagerException("description cannot be empty");
            }

            this.description = description;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) throws CostManagerException {
            // check if sum is empty.
            if(sum == 0){
                throw new CostManagerException("sum cannot be zero");
            }
            this.sum = sum;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Timestamp getDate() {
            return date;
        }

        public void setDate(Timestamp date) {
            this.date = date;
        }

        public String getCategoryName(){
            return getName();
        }
    }
}
