package dao;

import dto.ContactDTO;
import dto.CustomerDTO;
import javafx.collections.ObservableList;
import util.AppLogger;
import util.AppUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The class Customer dao extends base dao
 */
public class CustomerDao extends BaseDao {
    /**
     *
     * Gets the all customers
     *
     * @return the all customers
     */
    public List<CustomerDTO> getAllCustomers() {

        String query = "SELECT cust.*,divs.Division,coun.Country,divs.Country_ID FROM customers cust\n" +
                "Inner Join first_level_divisions divs On divs.Division_ID = cust.Division_ID\n" +
                "Inner Join countries coun On coun.Country_ID = divs.Country_ID";
        List<CustomerDTO> lst = getCustomerByQuery(query,null);
        return lst;
    }


    /**
     *
     * Gets the customer by identifier
     *
     * @param id  the id
     * @return the customer by identifier
     */
    public CustomerDTO getCustomerById(int id) {

        String query = "SELECT cust.*,divs.Division,coun.Country,divs.Country_ID FROM customers cust\n" +
                "Inner Join first_level_divisions divs On divs.Division_ID = cust.Division_ID\n" +
                "Inner Join countries coun On coun.Country_ID = divs.Country_ID WHERE cust.Customer_ID = ?;";
        List<CustomerDTO> lst = getCustomerByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the customer by query
     *
     * @param query  the query
     * @param param  the param
     * @return the customer by query
     */
    private List<CustomerDTO> getCustomerByQuery(String query, String param) {

        List<CustomerDTO> lst = new ArrayList<CustomerDTO>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(param != null) {
                stmt.setString(1, param);
            }
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                lst.add(AppUtil.getCustomerDTOFromRS(results));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
        return lst;
    }


    /**
     *
     * Save customer
     *
     * @param customer  the customer
     */
    public void saveCustomer(CustomerDTO customer) {

        String query = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query, 1);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPostCode());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, AppUtil.getUTCDateTimeString());
            stmt.setString(6, AppUtil.loginUser.getUsername());
            stmt.setString(7, AppUtil.getUTCDateTimeString());
            stmt.setString(8, AppUtil.loginUser.getUsername());
            stmt.setInt(9, customer.getDivisionId());
            stmt.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
            AppLogger.error(exception.getMessage(),exception);
        }
    }


    /**
     *
     * Update customer
     *
     * @param customer  the customer
     */
    public void updateCustomer(CustomerDTO customer) {

        String query = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query, 1);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPostCode());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, AppUtil.getUTCDateTimeString());
            stmt.setString(6, AppUtil.loginUser.getUsername());
            stmt.setInt(7, customer.getDivisionId());
            stmt.setInt(8, customer.getId());
            stmt.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
            AppLogger.error(exception.getMessage(),exception);
        }
    }


    /**
     *
     * Delete customer
     *
     * @param customer  the customer
     */
    public void deleteCustomer(CustomerDTO customer) {

        String query = "DELETE FROM customers WHERE Customer_ID = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customer.getId());
            stmt.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
            AppLogger.error(exception.getMessage(),exception);
        }
    }
}
