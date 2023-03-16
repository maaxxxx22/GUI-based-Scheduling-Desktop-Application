package controller;

import dao.AppointmentsDao;
import dao.CustomerDao;
import dto.CustomerDTO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.AppLogger;
import util.AppUtil;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


/**
 * The class Customer for Customer UI
 */
public class Customer {
    @FXML
    public TableView<CustomerDTO> tblCustomer;


    @FXML
    public TableColumn<CustomerDTO, Integer> colId;


    @FXML
    public TableColumn<CustomerDTO, String> colName;


    @FXML
    public TableColumn<CustomerDTO, String> colAddress;


    @FXML
    public TableColumn<CustomerDTO, String> colPostCode;


    @FXML
    public TableColumn<CustomerDTO, String> colPhone;


    @FXML
    public TableColumn<CustomerDTO, String> colCountry;


    @FXML
    public TableColumn<CustomerDTO, String> colDivision;


    @FXML
    public Label showErrorLabel;


    @FXML
    private TextField txtSearch;


    CustomerDao customerDao = new CustomerDao();

    AppointmentsDao appointmentDao = new AppointmentsDao();

    static CustomerDTO selectedCustomer = null;




    @FXML

/**
 *
 * Initialize
 *
 * @param Exception  the exception
 * @throws   Exception
 */
    public void initialize() throws Exception {


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPostCode.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCountry.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        colDivision.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

        this.refreshGrid();
    }



    /**
     *
     * Customer predicate
     *
     * @param string  the string
     * @return Predicate<CustomerDTO>
     */
    private Predicate<CustomerDTO> customerPredicate(String string) {
        return part -> {

            if (string == null || string.isEmpty()) {
                return true;
            }

            return searchCustomers(part, string);
        };
    }



    /**
     *
     * Search customers
     *
     * @param customer  the customer
     * @param string  the string
     * @return boolean
     */
    private boolean searchCustomers(CustomerDTO customer, String string) {


        String searchString = string.toLowerCase();

        return (customer.getName().toLowerCase().contains(searchString)) ||
                (customer.getAddress().toLowerCase().contains(searchString)) ||
                (customer.getPostCode().toLowerCase().contains(searchString)) ||
                (customer.getPhone().toLowerCase().contains(searchString)) ||
                (customer.getCountryName().toLowerCase().contains(searchString)) ||
                (customer.getDivisionName().toLowerCase().contains(searchString)) ||
                Integer.valueOf(customer.getId()).toString().equals(searchString);
    }



    /**
     *
     * Appointments button click
     *
     * @param event  the event
     */
    public void appointmentsButtonClick(ActionEvent event) {

        AppUtil.routeTo(event, "appointment", "Appointments");
    }



    /**
     *
     * Reports button click
     *
     * @param event  the event
     */
    public void reportsButtonClick(ActionEvent event) {

        AppUtil.routeTo(event, "report", "Report");
    }



    /**
     *
     * New button click
     *
     * @param event  the event
     */
    public void newButtonClick(ActionEvent event) {

        selectedCustomer = null;
        AppUtil.openPopUp(event, "createCustomer", "Add Customer");
        this.refreshGrid();
    }



    /**
     *
     * Edit button click
     *
     * @param event  the event
     */
    public void editButtonClick(ActionEvent event) {


        clearError();

        selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showError("You must select a customer");
            return;
        }
        AppUtil.openPopUp(event, "createCustomer", "Edit Customer");
        this.refreshGrid();
    }



    /**
     *
     * Delete click handler
     *
     * @param event  the event
     */
    public void deleteClickHandler(ActionEvent event) {

        clearError();
        selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showError("You must select a customer");
        }
        else {
            Optional<ButtonType> confirmation = AppUtil.showAlert("confirm","Confirm Delete", "Are you sure to delete the Customer?");
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                appointmentDao.deleteAppointmentBuCustomerId(selectedCustomer.getId());
                customerDao.deleteCustomer(selectedCustomer);
                AppUtil.showAlert("info","Info","Customer Deleted Successfully");
                this.refreshGrid();
            }
        }
    }



    /**
     *
     * It is a constructor.
     *
     */
    public static CustomerDTO getSelectedCustomer() {

        return selectedCustomer;
    }


    /**
     *
     * Show error
     *
     * @param msg  the message
     */
    private void showError(String msg) {

        clearError();
        if(showErrorLabel != null) {
            showErrorLabel.setText("Error: " + msg);
        }
    }


    /**
     *
     * Clear error
     *
     */
    private void clearError() {

        if(showErrorLabel != null) {
            showErrorLabel.setText("");
        }

    }


    /**
     *
     * Refresh grid
     *Lambda Expression is used here to set the predicate for Customer List for filtering out the customers
     */
    private void refreshGrid() {

        try {
            List<CustomerDTO> lst = customerDao.getAllCustomers();
            ObservableList<CustomerDTO> lstObs = AppUtil.convertListToObservableList(lst);
            FilteredList<CustomerDTO> filteredCustomers = new FilteredList(lstObs, (b) -> {
                return true;
            });

            txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredCustomers.setPredicate(customerPredicate(newValue));
                if (filteredCustomers.size() == 0) {
                    showError("No matching customers found!");
                } else {
                    clearError();
                }
            });
            tblCustomer.setItems(filteredCustomers);
        }
        catch(Exception ex) {
            ex.printStackTrace();;
            AppLogger.error(ex.getMessage(),ex);
        }
    }
}