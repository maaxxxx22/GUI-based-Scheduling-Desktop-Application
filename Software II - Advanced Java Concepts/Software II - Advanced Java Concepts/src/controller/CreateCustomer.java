package controller;

import dao.CountryDao;
import dao.CustomerDao;
import dao.DivisionDao;
import dto.CountryDTO;
import dto.CustomerDTO;
import dto.DivisionDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import util.AppLogger;
import util.AppUtil;


import java.util.List;



/**
 * The class Create and Update customer
 */
public class CreateCustomer {
    @FXML
    private TextField txtId;


    @FXML
    public Label lblName;


    @FXML
    private TextField txtName;


    @FXML
    public Label lblPhone;


    @FXML
    private TextField txtPhone;


    @FXML
    public Label lblAddress;


    @FXML
    private TextField txtAddress;


    @FXML
    public Label lblPost;


    @FXML
    private TextField txtPost;


    @FXML
    public Label lblCountry;


    @FXML
    private ComboBox<String> cmbCountry;


    @FXML
    public Label lblDivision;


    @FXML
    private ComboBox<String> cmbDivision;


    @FXML
    public Label lblError;

    CountryDao countryDao = new CountryDao();
    CustomerDao customerDao = new CustomerDao();

    DivisionDao divisionDao = new DivisionDao();

    @FXML

/**
 *
 * Initialize
 *
 */
    public void initialize() {

        CustomerDTO customer = controller.Customer.getSelectedCustomer();
        ObservableList<String> countrySelectList = FXCollections.observableArrayList();
        try {
            List<CountryDTO> countries = countryDao.getAllCountries();
            if (countries != null) {
                for (CountryDTO country: countries) {
                    countrySelectList.add(country.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }

        cmbCountry.setItems(countrySelectList);

        if (customer == null) {

            cmbDivision.setDisable(true);
        } else {

            String countryValue = customer.getCountryId() + " - " +
                    customer.getCountryName();

            txtId.setText(String.valueOf(customer.getId()));
            txtName.setText(customer.getName());
            txtPhone.setText(customer.getPhone());
            txtAddress.setText(customer.getAddress());
            txtPost.setText(customer.getPostCode());
            cmbCountry.setValue(countryValue);

            initializeDivisionSelector();
            cmbDivision.setValue(customer.getDivisionId() + " - " + customer.getDivisionName());
        }
    }


    @FXML

/**
 *
 * Save click handler
 *
 * @param event  the event
 */
    public void saveClickHandler(ActionEvent event) {

        if (!validate()) {
            return;
        }

        Boolean status = null;

        if (txtId.getText().isEmpty()) {
            status = createCustomer();
        } else {
            status = updateCustomer();
        }

        if (Boolean.TRUE.equals(status)) {
            AppUtil.closePopUp(event);
        }
    }


    /**
     *
     * Create customer
     *
     * @return Boolean
     */
    private Boolean createCustomer() {

        try {
            CustomerDTO dto = new CustomerDTO(
                    txtName.getText(),
                    txtAddress.getText(),
                    txtPost.getText(),
                    txtPhone.getText(),
                    AppUtil.getIdFromComboString((String) cmbDivision.getSelectionModel().getSelectedItem())
            );
            customerDao.saveCustomer(dto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
            AppUtil.showAlert("error", "Add Customer", "There was an unexpected error adding the customer");
        }
        return false;
    }



    /**
     *
     * Update customer
     *
     * @return Boolean
     */
    private Boolean updateCustomer() {

        try {
            CustomerDTO c = customerDao.getCustomerById(Integer.parseInt(txtId.getText()));
            c.setName(txtName.getText());
            c.setAddress(txtAddress.getText());
            c.setPostCode(txtPost.getText());
            c.setPhone(txtPhone.getText());
            c.setDivisionId(AppUtil.getIdFromComboString((String) cmbDivision.getSelectionModel().getSelectedItem()));
            customerDao.updateCustomer(c);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
            AppUtil.showAlert("error","Update Customer", "There was an unexpected error updating the customer");
        }

        return false;
    }



    /**
     *
     * Validate
     *
     * @return Boolean
     */
    private Boolean validate() {

        cleanError();
        String message = "CustomerDTO %s is required!";
        if (txtName.getText().isEmpty()) {
            lblError.setText(String.format(message, "name"));
            lblName.setTextFill(Color.RED);
            return false;
        } else if (txtPhone.getText().isEmpty()) {
            lblError.setText(String.format(message, "phone"));
            lblPhone.setTextFill(Color.RED);
            return false;
        } else if (txtAddress.getText().isEmpty()) {
            lblError.setText(String.format(message, "address"));
            lblAddress.setTextFill(Color.RED);
            return false;
        } else if (txtPost.getText().isEmpty()) {
            lblError.setText(String.format(message, "post code"));
            lblPost.setTextFill(Color.RED);
            return false;
        } else if (cmbCountry.getSelectionModel().getSelectedItem().isEmpty()) {
            lblError.setText(String.format(message, "country"));
            lblCountry.setTextFill(Color.RED);
            return false;
        } else if (cmbDivision.getSelectionModel().getSelectedItem().isEmpty()) {
            lblError.setText(String.format(message, "division"));
            lblDivision.setTextFill(Color.RED);
            return false;
        }

        return true;
    }



    /**
     *
     * Clear errors
     *
     */
    private void cleanError() {

        lblError.setText("");
        lblName.setTextFill(Color.BLACK);
        lblPhone.setTextFill(Color.BLACK);
        lblAddress.setTextFill(Color.BLACK);
        lblPost.setTextFill(Color.BLACK);
        lblCountry.setTextFill(Color.BLACK);
        lblDivision.setTextFill(Color.BLACK);
    }


    @FXML

/**
 *
 * Cmb country select handler
 *
 * @param event  the event
 */
    public void cmbCountrySelectHandler(ActionEvent event) {

        initializeDivisionSelector();
    }


    /**
     *
     * Initialize division selector
     *
     */
    private void initializeDivisionSelector() {

        cmbDivision.setValue("");
        ObservableList<String> divisionSelectList = FXCollections.observableArrayList();
        try {
            List<DivisionDTO> divisions = divisionDao.getDivisionByCountry((String) cmbCountry.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (DivisionDTO division: divisions) {
                    divisionSelectList.add(division.getId() + " - " + division.getName());
                }
            }
            cmbDivision.setItems(divisionSelectList);
            cmbDivision.setDisable(false);
        } catch (Exception e){
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
    }

    @FXML

/**
 *
 * Cancel click handler
 *
 * @param event  the event
 */
    public void cancelClickHandler(ActionEvent event) {
        AppUtil.closePopUp(event);
    }
}
