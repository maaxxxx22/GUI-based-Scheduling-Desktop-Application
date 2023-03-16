package util;

import dto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The class Application util
 */
public class AppUtil {

    public static UserDTO loginUser = null;


    /**
     *
     * Route to
     *
     * @param event  the event
     * @param view  the view
     * @param title  the title
     */
    public static void routeTo(ActionEvent event, String view, String title) {


        try {
            Stage screen = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(AppUtil.class.getResource("/ui/" + view + ".fxml"));
            screen.setTitle(title);
            screen.setScene(new Scene(scene));
            screen.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * Open pop up
     *
     * @param event  the event
     * @param view  the view
     * @param title  the title
     */
    public static void openPopUp(ActionEvent event, String view, String title) {

        try {
            Parent root = FXMLLoader.load(AppUtil.class.getResource("/ui/" + view + ".fxml"));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * Close pop up
     *
     * @param event  the event
     */
    public static void closePopUp(ActionEvent event) {

        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    /**
     *
     * Gets the local date time string
     *
     * @return the local date time string
     */
    public static String getLocalDateTimeString() {

        return dtf.format(ZonedDateTime.now());
    }


    /**
     *
     * Gets the string from local date time
     *
     * @param ldt  the ldt
     * @return the string from local date time
     */
    public static String getStringFromLocalDateTime(LocalDateTime ldt) {

        return dtf.format(ldt);
    }


    /**
     *
     * Gets the office date time string
     *
     * @return the office date time string
     */
    public static String getOfficeDateTimeString() {

        return dtf.format(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")));
    }


    /**
     *
     * Gets the UTC date time string
     *
     * @return the  UTC date time string
     */
    public static String getUTCDateTimeString() {

        return dtf.format(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")));
    }


    /**
     *
     * Local date time to UTC string
     *
     * @param ldt  the ldt
     * @return String
     */
    public static String localDateTimeToUTCString(LocalDateTime ldt) {

        return dtf.format(ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")));
    }


    /**
     *
     * Local date time to EST
     *
     * @param ldt  the ldt
     * @return ZonedDateTime
     */
    public static ZonedDateTime localDateTimeToEST(LocalDateTime ldt) {

        return ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
    }


    /**
     *
     * Gets the date time formatter
     *
     * @return the date time formatter
     */
    public static DateTimeFormatter getDateTimeFormatter() {

        return dtf;
    }


    /**
     *
     * Gets the identifier from combo string
     *
     * @param string  the string
     * @return the identifier from combo string
     */
    public static int getIdFromComboString(String string) {


        Matcher m = Pattern.compile("^(\\d+)\\s.*").matcher(string);

        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }

        return 0;
    }

    /**
     *
     * Show alert
     *
     * @param type  the type
     * @param title  the title
     * @param message  the message
     * @return Optional<ButtonType>
     */
    public static Optional<ButtonType> showAlert(String type,String title,String message) {

        Alert.AlertType alertType = null;
        switch (type.toLowerCase()) {
            case "info":
                alertType = Alert.AlertType.INFORMATION;
                break;
            case "confirm":
                alertType = Alert.AlertType.CONFIRMATION;
                break;
            case "error":
                alertType = Alert.AlertType.ERROR;
                break;
        }
        Alert alert =new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }


    /**
     *
     * Convert list to observable list
     *
     * @param lst  the lst
     * @return ObservableList<E>
     * @throws   SQLException
     */
    public static <E> ObservableList<E> convertListToObservableList(List<E> lst) throws SQLException {

        ObservableList<E> lstRet = FXCollections.observableList(lst);
        return lstRet;
    }

    /**
     *
     * Gets the user DTO from RS
     *
     * @param rs  the rs
     * @return the user DTO from RS
     * @throws   SQLException
     */
    public static UserDTO getUserDTOFromRS(ResultSet rs) throws SQLException {

        UserDTO dto =  new UserDTO(
                rs.getInt("User_ID"),
                rs.getString("User_Name"),
                rs.getString("Password"),
                rs.getTimestamp("Create_Date").toLocalDateTime(),
                rs.getString("Created_By"),
                rs.getTimestamp("Last_Update").toLocalDateTime(),
                rs.getString("Last_Updated_By")
        );
        return dto;
    }


    /**
     *
     * Gets the appointment DTO from RS
     *
     * @param rs  the rs
     * @return the appointment DTO from RS
     * @throws   SQLException
     */
    public static AppointmentDTO getAppointmentDTOFromRS(ResultSet rs) throws SQLException {

        AppointmentDTO dto = new AppointmentDTO(
                rs.getInt("Appointment_ID"),
                rs.getString("Title"),
                rs.getString("Description"),
                rs.getString("Location"),
                rs.getString("Type"),
                rs.getTimestamp("Start").toLocalDateTime(),
                rs.getTimestamp("End").toLocalDateTime(),
                rs.getTimestamp("Create_Date").toLocalDateTime(),
                rs.getString("Created_By"),
                rs.getTimestamp("Last_Update").toLocalDateTime(),
                rs.getString("Last_Updated_By"),
                rs.getInt("Customer_ID"),
                rs.getInt("User_ID"),
                rs.getInt("Contact_ID"),
                rs.getString("Customer_Name"),
                rs.getString("User_Name"),
                rs.getString("Contact_Name")
        );
        return dto;
    }


    /**
     *
     * Gets the contact DTO from RS
     *
     * @param rs  the rs
     * @return the contact DTO from RS
     * @throws   SQLException
     */
    public static ContactDTO getContactDTOFromRS(ResultSet rs) throws SQLException {

        ContactDTO dto =  new ContactDTO(
                rs.getInt("Contact_ID"),
                rs.getString("Contact_Name"),
                rs.getString("Email")
        );
        return dto;
    }


    /**
     *
     * Gets the customer DTO from RS
     *
     * @param rs  the rs
     * @return the customer DTO from RS
     * @throws   SQLException
     */
    public static CustomerDTO getCustomerDTOFromRS(ResultSet rs) throws SQLException {

        CustomerDTO dto = new CustomerDTO(
                rs.getInt("Customer_ID"),
                rs.getString("Customer_Name"),
                rs.getString("address"),
                rs.getString("Postal_Code"),
                rs.getString("Phone"),
                rs.getTimestamp("Create_Date").toLocalDateTime(),
                rs.getString("Created_By"),
                rs.getTimestamp("Last_Update").toLocalDateTime(),
                rs.getString("Last_Updated_By"),
                rs.getInt("Division_ID"),
                rs.getString("Division"),
                rs.getString("Country"),
                rs.getInt("Country_ID")
        );
        return dto;
    }


    /**
     *
     * Gets the country DTO from RS
     *
     * @param rs  the rs
     * @return the country DTO from RS
     * @throws   SQLException
     */
    public static CountryDTO getCountryDTOFromRS(ResultSet rs) throws SQLException {

        CountryDTO dto = new CountryDTO(
                rs.getInt("Country_ID"),
                rs.getString("Country"),
                rs.getTimestamp("Create_Date").toLocalDateTime(),
                rs.getString("Created_By"),
                rs.getTimestamp("Last_Update").toLocalDateTime(),
                rs.getString("Last_Updated_By")
        );
        return dto;
    }


    /**
     *
     * Gets the division DTO from RS
     *
     * @param rs  the rs
     * @return the division DTO from RS
     * @throws   SQLException
     */
    public static DivisionDTO getDivisionDTOFromRS(ResultSet rs) throws SQLException {

        DivisionDTO dto = new DivisionDTO(
                rs.getInt("Division_ID"),
                rs.getString("division"),
                rs.getTimestamp("Create_Date").toLocalDateTime(),
                rs.getString("Created_By"),
                rs.getTimestamp("Last_Update").toLocalDateTime(),
                rs.getString("Last_Updated_By"),
                rs.getInt("Country_ID")
        );
        return dto;
    }

}