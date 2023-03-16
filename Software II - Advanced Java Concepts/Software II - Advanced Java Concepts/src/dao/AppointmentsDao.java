package dao;

import dto.AppointmentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.AppLogger;
import util.AppUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The class Appointments dao extends base dao
 */
public class AppointmentsDao extends BaseDao {


    /**
     *
     * Gets the all appointment
     *
     * @return the all appointment
     */
    public List<AppointmentDTO> getAllAppointment() {

        return getAppointmentsByQuery("SELECT app.*,cust.Customer_Name,usr.User_Name,con.Contact_Name \n" +
                "FROM client_schedule.appointments app\n" +
                "Inner Join customers cust On cust.Customer_ID = app.Customer_ID\n" +
                "Inner Join users usr On usr.User_ID = app.User_ID\n" +
                "Inner Join contacts con On con.Contact_ID = app.Contact_ID",null);
    }


    /**
     *
     * Gets the appointment by week
     *
     * @return the appointment by week
     */
    public List<AppointmentDTO> getAppointmentByWeek() {

        return getAppointmentsByQuery("SELECT app.*,cust.Customer_Name,usr.User_Name,con.Contact_Name \n" +
                "FROM client_schedule.appointments app\n" +
                "Inner Join customers cust On cust.Customer_ID = app.Customer_ID\n" +
                "Inner Join users usr On usr.User_ID = app.User_ID\n" +
                "Inner Join contacts con On con.Contact_ID = app.Contact_ID WHERE WEEK(Start) = WEEK(?)",AppUtil.getLocalDateTimeString());
    }


    /**
     *
     * Gets the appointment by month
     *
     * @return the appointment by month
     */
    public List<AppointmentDTO> getAppointmentByMonth() {

        return getAppointmentsByQuery("SELECT app.*,cust.Customer_Name,usr.User_Name,con.Contact_Name \n" +
                "FROM client_schedule.appointments app\n" +
                "Inner Join customers cust On cust.Customer_ID = app.Customer_ID\n" +
                "Inner Join users usr On usr.User_ID = app.User_ID\n" +
                "Inner Join contacts con On con.Contact_ID = app.Contact_ID WHERE MONTH(Start) = MONTH(?)",AppUtil.getLocalDateTimeString());
    }


    /**
     *
     * Gets the appointment by identifier
     *
     * @param id  the id
     * @return the appointment by identifier
     */
    public AppointmentDTO getAppointmentById(int id) {

        String query = "SELECT app.*,cust.Customer_Name,usr.User_Name,con.Contact_Name \n" +
                "FROM client_schedule.appointments app\n" +
                "Inner Join customers cust On cust.Customer_ID = app.Customer_ID\n" +
                "Inner Join users usr On usr.User_ID = app.User_ID\n" +
                "Inner Join contacts con On con.Contact_ID = app.Contact_ID WHERE Appointment_ID = ?;";
        List<AppointmentDTO> lst = getAppointmentsByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the upcoming appointment by user identifier
     *
     * @param id  the id
     * @return the upcoming appointment by user identifier
     */
    public AppointmentDTO getUpcomingAppointmentByUserId(int id) {


        String query = "SELECT app.*,cust.Customer_Name,usr.User_Name,con.Contact_Name \n" +
                "FROM client_schedule.appointments app\n" +
                "Inner Join customers cust On cust.Customer_ID = app.Customer_ID\n" +
                "Inner Join users usr On usr.User_ID = app.User_ID\n" +
                "Inner Join contacts con On con.Contact_ID = app.Contact_ID WHERE app.User_ID = ? " +
                "AND Start >= now() AND Start <= date_add(now(),interval 15 minute) " +
                "ORDER BY Start ASC LIMIT 1;";
        List<AppointmentDTO> lst = getAppointmentsByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the appointments by customer identifier
     *
     * @param customerId  the customer identifier
     * @return the appointments by customer identifier
     */
    public List<AppointmentDTO> getAppointmentsByCustomerId(int customerId) {

        return getAppointmentsByQuery("SELECT app.*,cust.Customer_Name,usr.User_Name,con.Contact_Name \n" +
                "FROM client_schedule.appointments app\n" +
                "Inner Join customers cust On cust.Customer_ID = app.Customer_ID\n" +
                "Inner Join users usr On usr.User_ID = app.User_ID\n" +
                "Inner Join contacts con On con.Contact_ID = app.Contact_ID WHERE app.Customer_ID = ?",String.valueOf(customerId));
    }


    /**
     *
     * Gets the result set for report
     *
     * @param reportType  the report type
     * @return the result set for report
     */
    public ResultSet getResultSetForReport(String reportType) {

        String query = "";
        switch (reportType) {
            case "customer":
                query = "Select * from (\n" +
                        "SELECT Type, MONTHNAME(Start) as Month, count(*) AS Count FROM appointments \n" +
                        "GROUP BY Type, MONTHNAME(Start))tbl\n" +
                        "ORDER BY Type, Month";
                break;
            case "contact":
                query = "SELECT c.Contact_Name, a.* FROM appointments a, contacts c " +
                        "WHERE a.Contact_ID = c.Contact_ID ORDER BY Contact_Name, Start";
                break;
            case "user":
                query = "SELECT u.User_Name, a.* FROM appointments a, users u " +
                        "WHERE a.User_ID = u.User_ID ORDER BY User_Name, Location, Start";
        }
        try
        {
            return conn.createStatement().executeQuery(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }

        return null;
    }


    /**
     *
     * Gets the appointments by query
     *
     * @param query  the query
     * @param param  the param
     * @return the appointments by query
     */
    private List<AppointmentDTO> getAppointmentsByQuery(String query,String param) {

        List<AppointmentDTO> lst = new ArrayList<AppointmentDTO>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(param != null) {
                stmt.setString(1, param);
            }
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                lst.add(AppUtil.getAppointmentDTOFromRS(results));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
        return lst;
    }


    /**
     *
     * Save appointment
     *
     * @param appointment  the appointment
     */
    public void saveAppointment(AppointmentDTO appointment) {


        String query = "INSERT INTO appointments(" +
                "Title, Description, Location, Type, Start, End, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, appointment.getTitle());
            stmt.setString(2, appointment.getDescription());
            stmt.setString(3, appointment.getLocation());
            stmt.setString(4, appointment.getType());
            stmt.setString(5, AppUtil.localDateTimeToUTCString(appointment.getStart()));
            stmt.setString(6, AppUtil.localDateTimeToUTCString(appointment.getEnd()));
            stmt.setString(7, AppUtil.getUTCDateTimeString());
            stmt.setString(8, AppUtil.loginUser.getUsername());
            stmt.setString(9, AppUtil.getUTCDateTimeString());
            stmt.setString(10, AppUtil.loginUser.getUsername());
            stmt.setInt(11, appointment.getCustomerId());
            stmt.setInt(12, appointment.getUserId());
            stmt.setInt(13, appointment.getContactId());
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
    }


    /**
     *
     * Update appointment
     *
     * @param appointment  the appointment
     */
    public void updateAppointment(AppointmentDTO appointment) {


        String query = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, " +
                " User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, appointment.getTitle());
            stmt.setString(2, appointment.getDescription());
            stmt.setString(3, appointment.getLocation());
            stmt.setString(4, appointment.getType());
            stmt.setString(5, AppUtil.localDateTimeToUTCString(appointment.getStart()));
            stmt.setString(6, AppUtil.localDateTimeToUTCString(appointment.getEnd()));
            stmt.setString(7, AppUtil.getUTCDateTimeString());
            stmt.setString(8, AppUtil.loginUser.getUsername());
            stmt.setInt(9, appointment.getCustomerId());
            stmt.setInt(10, appointment.getUserId());
            stmt.setInt(11, appointment.getContactId());
            stmt.setInt(12, appointment.getId());

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
    }


    /**
     *
     * Delete appointment
     *
     * @param appointment  the appointment
     * @return Boolean
     */
    public Boolean deleteAppointment(AppointmentDTO appointment) {

        String query = "DELETE FROM appointments WHERE Appointment_ID = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, appointment.getId());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }

        return false;
    }


    /**
     *
     * Delete appointment bu customer identifier
     *
     * @param customerId  the customer identifier
     * @return Boolean
     */
    public Boolean deleteAppointmentBuCustomerId(int customerId) {

        String query = "DELETE FROM appointments WHERE Customer_ID = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }

        return false;
    }
}
