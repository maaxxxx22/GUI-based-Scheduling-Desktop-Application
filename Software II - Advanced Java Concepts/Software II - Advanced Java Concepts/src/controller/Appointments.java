package controller;

import dao.AppointmentsDao;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import dto.AppointmentDTO;
import util.AppLogger;
import util.AppUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;



/**
 * The class Appointments
 */
public class Appointments {
    public Label lblUpcomingApptMessage;


    public Label lblCurrentDateTime;


    public Label lblOfficeDateTime;


    public Label lblUTCDateTime;

    public ToggleGroup tgAllWeekMonth;


    public TableView<AppointmentDTO> tbtAppointment;


    public TableColumn<AppointmentDTO, Integer> colId;


    public TableColumn<AppointmentDTO, String> colTitle;


    public TableColumn<AppointmentDTO, String> colDescription;


    public TableColumn<AppointmentDTO, String> colLocation;


    public TableColumn<AppointmentDTO, String> colContact;


    public TableColumn<AppointmentDTO, String> colType;


    public TableColumn<AppointmentDTO, LocalDateTime> colStart;


    public TableColumn<AppointmentDTO, LocalDateTime> colEnd;


    public TableColumn<AppointmentDTO, Integer> colCustomerId;


    public TableColumn<AppointmentDTO, Integer> colUserId;


    public TextField txtAppointmentSearch;


    public Label lblError;


    private static AppointmentDTO appointment;


    FilteredList<AppointmentDTO> filteredAppointments;


    AppointmentsDao appointmentDao = new AppointmentsDao();


    @FXML

/**
 *
 * Initialize
 *
 */
    public void initialize() {


        lblCurrentDateTime.setText(AppUtil.getLocalDateTimeString());
        lblOfficeDateTime.setText(AppUtil.getOfficeDateTimeString());
        lblUTCDateTime.setText(AppUtil.getUTCDateTimeString());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        List<AppointmentDTO> lst = appointmentDao.getAppointmentByMonth();
        ObservableList<AppointmentDTO> lstObser = null;
        try {
            lstObser = AppUtil.convertListToObservableList(lst);
            filteredAppointments = new FilteredList<>(lstObser, b -> true);

            txtAppointmentSearch.textProperty().addListener((observable, oldValue, newValue) -> {

                filteredAppointments.setPredicate(appointmentPredicate(newValue));

                if (filteredAppointments.size() == 0) {
                    appointmentsError("No matching appointments found!");
                } else {
                    cleanError();
                }
            });

            tbtAppointment.setItems(filteredAppointments);


            colStart.setCellFactory(getDateCell(AppUtil.getDateTimeFormatter()));
            colEnd.setCellFactory(getDateCell(AppUtil.getDateTimeFormatter()));


            AppointmentDTO upcoming = appointmentDao.getUpcomingAppointmentByUserId(AppUtil.loginUser.getId());

            if (upcoming != null) {

                String m = "AppointmentDTO " + upcoming.getId() + " is starting at " +
                        AppUtil.getStringFromLocalDateTime(upcoming.getStart());

                lblUpcomingApptMessage.setText(m);
            }
        } catch (SQLException e) {
            AppLogger.error("Error occured in initialize of Appointment Controller",e);
            throw new RuntimeException(e);
        }


    }



    /**
     *
     * Gets the date cell
     * Lambda expression is used to generate TableCell with particular Date format
     *
     * @param format  the format
     * @return the date cell
     *
     */
    public static <ROW,T extends Temporal> Callback<TableColumn<ROW, T>, TableCell<ROW, T>> getDateCell (DateTimeFormatter format) {
        //
        return column -> new TableCell<>() {

            @Override
            protected void updateItem(T item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(format.format(item));
                }
            }
        };
    }



    /**
     *
     * Appointment predicate
     *
     * @param string  the string
     * @return Predicate<AppointmentDTO>
     */
    private Predicate<AppointmentDTO> appointmentPredicate(String string) {


        return part -> {

            if (string == null || string.isEmpty()) {
                return true;
            }

            return searchAppointments(part, string);
        };
    }



    /**
     *
     * Search appointments
     *
     * @param appointment  the appointment
     * @param string  the string
     * @return boolean
     */
    private boolean searchAppointments(AppointmentDTO appointment, String string) {


        String searchString = string.toLowerCase();

        return (appointment.getTitle().toLowerCase().contains(searchString)) ||
                (appointment.getDescription().toLowerCase().contains(searchString)) ||
                (appointment.getType().toLowerCase().contains(searchString)) ||
                Integer.valueOf(appointment.getId()).toString().equals(searchString);
    }



    /**
     *
     * Customers button click
     *
     * @param event  the event
     */
    public void customersButtonClick(ActionEvent event) {

        AppUtil.routeTo(event, "customer", "Customer");
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

        appointment = null;
        AppUtil.openPopUp(event, "createAppointment", "Create Appointment");
        this.refreshGrid();
    }



    /**
     *
     * Edit button click
     *
     * @param event  the event
     */
    public void editButtonClick(ActionEvent event) {
        cleanError();
        appointment = tbtAppointment.getSelectionModel().getSelectedItem();
        if (appointment == null) {
            appointmentsError("You must select an appointment");
        }
        else {
            AppUtil.openPopUp(event, "createAppointment", "Edit Appointment");
            this.refreshGrid();
        }
    }



    /**
     *
     * Delete button click
     *
     * @param event  the event
     */
    public void deleteButtonClick(ActionEvent event) {
        cleanError();
        appointment = tbtAppointment.getSelectionModel().getSelectedItem();
        if (appointment == null) {
            appointmentsError("You must select an appointment");
        }
        else {
            Optional<ButtonType> confirmation = AppUtil.showAlert("confirm","Confirm Delete", "Are you sure to delete the appointment " + appointment.getId() + " of type " + appointment.getType() + "?");
            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                appointmentDao.deleteAppointment(appointment);
                AppUtil.showAlert("info","Info","Appointment Deleted Successfully");
                this.refreshGrid();
            }

        }


    }


    /**
     *
     * All radio click
     *
     * @param event  the event
     * @throws   Exception
     */
    public void allRadioClick(ActionEvent event) throws Exception {
        List<AppointmentDTO> lst = appointmentDao.getAllAppointment();
        this.setListInGrid(lst);
    }



    /**
     *
     * Week radio click
     *
     * @param event  the event
     * @throws   Exception
     */
    public void weekRadioClick(ActionEvent event) throws Exception  {

        List<AppointmentDTO> lst = appointmentDao.getAppointmentByWeek();
        this.setListInGrid(lst);
    }



    /**
     *
     * Month radio click
     *
     * @param event  the event
     * @throws   Exception
     */
    public void monthRadioClick(ActionEvent event) throws Exception  {
        List<AppointmentDTO> lst = appointmentDao.getAppointmentByMonth();
        this.setListInGrid(lst);
    }


    /**
     *
     * Sets the list in grid
     *
     * @param lst  the lst
     * @throws   Exception
     */
    private void setListInGrid(List<AppointmentDTO> lst) throws Exception {
        ObservableList<AppointmentDTO> lstObs = AppUtil.convertListToObservableList(lst);
        this.filteredAppointments = new FilteredList(lstObs, (b) -> {
            return true;
        });
        this.tbtAppointment.setItems(this.filteredAppointments);
    }


    /**
     *
     * Refresh grid
     *
     */
    private void refreshGrid() {
        try {
            switch(((RadioButton)this.tgAllWeekMonth.getSelectedToggle()).getText().toLowerCase()) {
                case "month":
                    this.monthRadioClick(null);
                    break;
                case "week":
                    this.weekRadioClick(null);
                    break;
                case "all":
                    this.allRadioClick(null);

            }
        }
        catch(Exception e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
    }



    /**
     *
     * Appointments error
     *
     * @param msg  the message
     */
    private void appointmentsError(String msg) {

        cleanError();
        lblError.setText("Error: " + msg);
    }



    /**
     *
     * Clear errors
     *
     */
    private void cleanError() {

        lblError.setText("");
    }



    /**
     *
     * Gets the selected appointment
     *
     * @return the selected appointment
     */
    public static AppointmentDTO getSelectedAppointment() {

        return appointment;
    }
}
