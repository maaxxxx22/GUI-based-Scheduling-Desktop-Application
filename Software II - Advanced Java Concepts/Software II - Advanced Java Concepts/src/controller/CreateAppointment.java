package controller;

import dao.AppointmentsDao;
import dao.ContactDao;
import dao.CustomerDao;
import dao.UserDao;
import dto.AppointmentDTO;
import dto.ContactDTO;
import dto.CustomerDTO;
import dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import util.AppUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * The class Create and update appointment
 */
public class CreateAppointment {


    @FXML
    public Label lblTitle;


    @FXML
    public Label lblDescription;


    @FXML
    public Label lblLocation;


    @FXML
    public Label lblType;


    @FXML
    public Label lblStart;


    @FXML
    public Label lblEnd;


    @FXML
    public Label lblCustomer;


    @FXML
    public Label lblContact;


    @FXML
    public Label lblUser;


    @FXML
    public TextField txtId;


    @FXML
    public TextField txtTitle;


    @FXML
    public TextField txtDescription;


    @FXML
    public TextField txtLocation;


    @FXML
    public TextField txtType;


    @FXML
    public DatePicker dtStart;


    @FXML
    public TextField txtStartTime;


    @FXML
    public DatePicker dtEnd;


    @FXML
    public TextField txtEndTime;


    @FXML
    public ComboBox<String> cmbCustomer;


    @FXML
    public ComboBox<String> cmbContact;


    @FXML
    public ComboBox<String> cmbUser;


    @FXML
    public Label addAppointmentErrorLabel;

    AppointmentsDao appointmentDao = new AppointmentsDao();
    CustomerDao customerDao = new CustomerDao();
    ContactDao contactDao = new ContactDao();
    UserDao userDao = new UserDao();

    @FXML

/**
 *
 * Initialize
 *
 */
    public void initialize() {

        AppointmentDTO appointment = Appointments.getSelectedAppointment();

        dtStart.setConverter(getConverter());
        dtEnd.setConverter(getConverter());


        ObservableList<String> contactSelectList = FXCollections.observableArrayList();
        ObservableList<String> userSelectList = FXCollections.observableArrayList();
        ObservableList<String> customerSelectList = FXCollections.observableArrayList();
        try {
            List<ContactDTO> lstContacts = contactDao.getAllContacts();
            List<UserDTO> lstUsers = userDao.getAllUsers();
            List<CustomerDTO> lstCustomers = customerDao.getAllCustomers();

            if (lstContacts != null) {
                for (ContactDTO contact: lstContacts) {
                    contactSelectList.add(contact.getId() + " - " + contact.getName());
                }
            }

            if (lstUsers != null) {
                for (UserDTO user: lstUsers) {
                    userSelectList.add(user.getId() + " - " + user.getUsername());
                }
            }

            if (lstCustomers != null) {
                for (CustomerDTO customer: lstCustomers) {
                    customerSelectList.add(customer.getId() + " - " + customer.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        cmbContact.setItems(contactSelectList);
        cmbUser.setItems(userSelectList);
        cmbCustomer.setItems(customerSelectList);

        if (appointment != null) {
            txtId.setText(String.valueOf(appointment.getId()));
            txtTitle.setText(appointment.getTitle());
            txtDescription.setText(appointment.getDescription());
            txtLocation.setText(appointment.getLocation());
            txtType.setText(appointment.getType());
            dtStart.setValue(appointment.getStart().toLocalDate());
            txtStartTime.setText(appointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm")));
            dtEnd.setValue(appointment.getEnd().toLocalDate());
            txtEndTime.setText(appointment.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")));
            cmbCustomer.setValue(appointment.getCustomerId() + " - " + customerDao.getCustomerById(appointment.getCustomerId()).getName());
            cmbContact.setValue(appointment.getContactId() + " - " + contactDao.getContactById(appointment.getContactId()).getName());
            cmbUser.setValue(appointment.getUserId() + " - " + userDao.getUserById(appointment.getUserId()).getUsername());
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


        if (!validate()) { return; }

        Boolean status;

        if (txtId.getText().isEmpty()) {
            status = createAppointment();
        } else {
            status = updateAppointment();
        }

        if (Boolean.TRUE.equals(status)) {
            AppUtil.closePopUp(event);
        }
    }



    /**
     *
     * Create appointment
     *
     * @return Boolean
     */
    private Boolean createAppointment() {


        int contactId = getIdFromComboBox(cmbContact);
        int userId = getIdFromComboBox(cmbUser);
        int customerId = getIdFromComboBox(cmbCustomer);

        try {
            AppointmentDTO dto = new AppointmentDTO(
                    txtTitle.getText(),
                    txtDescription.getText(),
                    txtLocation.getText(),
                    txtType.getText(),
                    getStart(),
                    getEnd(),
                    contactId,
                    userId,
                    customerId
            );

            appointmentDao.saveAppointment(dto);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            AppUtil.showAlert("error","Add Appointment", "There was an unexpected error adding the appointment");
        }

        return false;
    }



    /**
     *
     * Update appointment
     *
     * @return Boolean
     */
    private Boolean updateAppointment() {

        int contactId = getIdFromComboBox(cmbContact);
        int userId = getIdFromComboBox(cmbUser);
        int customerId = getIdFromComboBox(cmbCustomer);
        try {
            AppointmentDTO a = appointmentDao.getAppointmentById(Integer.parseInt(txtId.getText()));

            a.setTitle(txtTitle.getText());
            a.setDescription(txtDescription.getText());
            a.setLocation(txtLocation.getText());
            a.setType(txtType.getText());
            a.setStart(getStart());
            a.setEnd(getEnd());
            a.setContactId(contactId);
            a.setUserId(userId);
            a.setCustomerId(customerId);

            appointmentDao.updateAppointment(a);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            AppUtil.showAlert("error","Update Appointment", "There was an unexpected error updating the appointment");
        }

        return false;
    }




    /**
     *
     * Cancel click handler
     *
     * @param event  the event
     */
    public void cancelClickHandler(ActionEvent event) {

        AppUtil.closePopUp(event);
    }



    /**
     *
     * Validate
     *
     * @return Boolean
     */
    private Boolean validate() {


        cleanError();

        return !hasMissingData() && !hasInvalidDateTime() && !hasOverlappingAppointment();
    }



    /**
     *
     * Has invalid date time
     *
     * @return Boolean
     */
    private Boolean hasInvalidDateTime() {


        boolean hasInvalidDateTime = true;

        String errorTitle = "Appointment Date/Time";

        int startHour = AppUtil.localDateTimeToEST(getStart()).getHour();
        int endHour = AppUtil.localDateTimeToEST(getEnd()).getHour();

        if (startHour < 8 || startHour >= 22 || endHour < 8 || endHour >= 22) {
            AppUtil.showAlert("error",errorTitle, "The appointment time is outside of business hours (08:00 - 22:00)");
        } else if (getStart().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now()))) {
            AppUtil.showAlert("error",errorTitle, "The appointment start date/time is in the past!");
        } else if (getEnd().isBefore(ChronoLocalDateTime.from(ZonedDateTime.now()))) {
            AppUtil.showAlert("error",errorTitle, "The appointment end date/time is in the past!");
        } else if (getEnd().isBefore(ChronoLocalDateTime.from(getStart()))) {
            AppUtil.showAlert("error",errorTitle, "The appointment start date/time is after the end date/time!");
        } else {
            hasInvalidDateTime = false;
        }

        return hasInvalidDateTime;
    }



    /**
     *
     * Has overlapping appointment
     *
     * @return Boolean
     */
    private Boolean hasOverlappingAppointment() {

        List<AppointmentDTO> appointments = appointmentDao.getAppointmentsByCustomerId(getIdFromComboBox(cmbCustomer));
        int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
        for (AppointmentDTO a : appointments) {


            if (id == a.getId()) {
                continue;
            }

            if (a.getStart().isBefore(getEnd()) && getStart().isBefore(a.getEnd())) {
                AppUtil.showAlert("error","Appointment date/time", "The appointment overlaps with appointment " + a.getId());
                return true;
            }
        };

        return false;
    }



    /**
     *
     * Has missing data
     *
     * @return Boolean
     */
    private Boolean hasMissingData() {


        boolean hasMissingData = true;

        String message = "Appointment %s is required!";

        if (txtTitle.getText().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "title"));
            lblTitle.setTextFill(Color.RED);
        } else if (txtDescription.getText().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "description"));
            lblDescription.setTextFill(Color.RED);
        } else if (txtLocation.getText().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "location"));
            lblLocation.setTextFill(Color.RED);
        } else if (txtType.getText().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "type"));
            lblType.setTextFill(Color.RED);
        } else if (dtStart.getValue() == null) {
            addAppointmentErrorLabel.setText(String.format(message, "start date"));
            lblStart.setTextFill(Color.RED);
        } else if (txtStartTime.getText().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "start time"));
        } else if (dtEnd.getValue() == null) {
            addAppointmentErrorLabel.setText(String.format(message, "end date"));
            lblEnd.setTextFill(Color.RED);
        } else if (txtEndTime.getText().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "end time"));
            lblEnd.setTextFill(Color.RED);
        } else if (cmbCustomer.getSelectionModel().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "customer"));
            lblCustomer.setTextFill(Color.RED);
        } else if (cmbContact.getSelectionModel().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "contact"));
            lblContact.setTextFill(Color.RED);
        } else if (cmbUser.getSelectionModel().isEmpty()) {
            addAppointmentErrorLabel.setText(String.format(message, "user"));
            lblUser.setTextFill(Color.RED);
        } else {
            hasMissingData = false;
        }

        return hasMissingData;
    }


    /**
     *
     * Gets the identifier from combo box
     *
     * @param comboBox  the combo box
     * @return the identifier from combo box
     */
    private int getIdFromComboBox(ComboBox comboBox) {

        return AppUtil.getIdFromComboString((String) comboBox.getSelectionModel().getSelectedItem());
    }



    /**
     *
     * Clear errors
     *
     */
    private void cleanError() {

        addAppointmentErrorLabel.setText("");

        lblTitle.setTextFill(Color.BLACK);
        lblDescription.setTextFill(Color.BLACK);
        lblLocation.setTextFill(Color.BLACK);
        lblType.setTextFill(Color.BLACK);
        lblStart.setTextFill(Color.BLACK);
        lblEnd.setTextFill(Color.BLACK);
        lblCustomer.setTextFill(Color.BLACK);
        lblContact.setTextFill(Color.BLACK);
        lblUser.setTextFill(Color.BLACK);
    }



    /**
     *
     * Gets the converter
     *
     * @return the converter
     */
    private StringConverter<LocalDate> getConverter() {


        return new StringConverter<LocalDate>() {

            private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override

/**
 *
 * To string
 *
 * @param localDate  the local date
 * @return String
 */
            public String toString(LocalDate localDate) {


                if (localDate == null) {
                    return "";
                }

                return dtf.format(localDate);
            }

            @Override

/**
 *
 * From string
 *
 * @param dateString  the date string
 * @return LocalDate
 */
            public LocalDate fromString(String dateString) {


                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }

                return LocalDate.parse(dateString, dtf);
            }
        };
    }



    /**
     *
     * Gets the start
     *
     * @return the start
     */
    private LocalDateTime getStart() {

        String startString = dtStart.getValue() + " " + txtStartTime.getText();
        return LocalDateTime.parse(startString, AppUtil.getDateTimeFormatter());
    }



    /**
     *
     * Gets the end
     *
     * @return the end
     */
    private LocalDateTime getEnd() {

        String endString = dtEnd.getValue() + " " + txtEndTime.getText();
        return LocalDateTime.parse(endString, AppUtil.getDateTimeFormatter());
    }
}
