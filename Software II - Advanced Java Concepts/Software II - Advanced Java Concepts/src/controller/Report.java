package controller;


import dao.AppointmentsDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import util.AppLogger;
import util.AppUtil;


import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;


/**
 * The class Report for Report UI
 */
public class Report {
    @FXML
    public Hyperlink customerReportLink;


    @FXML
    public Hyperlink contactReportLink;


    @FXML
    public Hyperlink userReportLink;


    @FXML
    private TextFlow reportsTextFlow;

    private AppointmentsDao appointmentsDao = new AppointmentsDao();



    @FXML

/**
 *
 * Initialize
 *
 */
    public void initialize() {

        Text blank = new Text("\n\n\nSelect a report\n");
        blank.setFill(Color.GRAY);
        blank.setFont(Font.font("CiscoSans", FontWeight.NORMAL, 14));
        reportsTextFlow.getChildren().addAll(blank);
        reportsTextFlow.setTextAlignment(TextAlignment.CENTER);
    }



    /**
     *
     * Customers button click
     *
     * @param event  the event
     */
    public void customersButtonClick(ActionEvent event) {

        AppUtil.routeTo(event, "customer", "Customers");
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
     * Customer report link click
     *
     * @param event  the event
     */
    public void customerReportLinkClick(ActionEvent event)  {


        customerReportLink.setVisited(false);

        reportsTextFlow.getChildren().clear();
        reportsTextFlow.setTextAlignment(TextAlignment.LEFT);


        Text title = new Text("Number of Customer Appointments by Type And Month\n\n");
        title.setFill(Color.BLACK);
        title.setFont(Font.font("CiscoSans", FontWeight.BOLD, 24));
        reportsTextFlow.getChildren().addAll(title);


        Text header = new Text("Type, Month, Count\n");
        header.setFill(Color.BLACK);
        header.setFont(Font.font("CiscoSans", FontWeight.BOLD, 16));
        reportsTextFlow.getChildren().addAll(header);

        this.refreshGrid("customer");
    }


    /**
     *
     * Gets the string for customer report
     *
     * @param rs  the rs
     * @return the string for customer report
     * @throws   Exception
     */
    private String getStringForCustomerReport(ResultSet rs) throws Exception {

        String row = rs.getString("Type") + ", " +
                rs.getString("Month") + ", " +
                rs.getString("Count");

        return row;
    }



    /**
     *
     * Contact report link click
     *
     * @param event  the event
     */
    public void contactReportLinkClick(ActionEvent event) {

        contactReportLink.setVisited(false);

        reportsTextFlow.getChildren().clear();
        reportsTextFlow.setTextAlignment(TextAlignment.LEFT);


        Text title = new Text("Contact Schedule\n\n");
        title.setFill(Color.BLACK);
        title.setFont(Font.font("CiscoSans", FontWeight.BOLD, 24));
        reportsTextFlow.getChildren().addAll(title);


        Text header = new Text("Contact, Appointment ID, Title, Description, Start, End, Customer ID\n");
        header.setFill(Color.BLACK);
        header.setFont(Font.font("CiscoSans", FontWeight.BOLD, 16));
        reportsTextFlow.getChildren().addAll(header);

        this.refreshGrid("contact");
    }


    /**
     *
     * Gets the string for contact report
     *
     * @param rs  the rs
     * @return the string for contact report
     * @throws   Exception
     */
    private String getStringForContactReport(ResultSet rs) throws Exception {

        DateTimeFormatter dtf = AppUtil.getDateTimeFormatter();
        String start = dtf.format(rs.getTimestamp("Start").toLocalDateTime());
        String end = dtf.format(rs.getTimestamp("End").toLocalDateTime());

        String row = rs.getString("Contact_Name") + ", " +
                rs.getInt("Appointment_ID") + ", " +
                rs.getString("Title") + ", " +
                rs.getString("Description") + ", " +
                start + ", " + end + ", " +
                rs.getInt("Customer_ID");

        return row;
    }



    /**
     *
     * User report link click
     *
     * @param event  the event
     */
    public void userReportLinkClick(ActionEvent event) {

        userReportLink.setVisited(false);

        reportsTextFlow.getChildren().clear();
        reportsTextFlow.setTextAlignment(TextAlignment.LEFT);


        Text title = new Text("User Schedule by Location\n\n");
        title.setFill(Color.BLACK);
        title.setFont(Font.font("CiscoSans", FontWeight.BOLD, 24));
        reportsTextFlow.getChildren().addAll(title);


        Text header = new Text("User, Location, Appointment ID, Title, Description, Start, End, Customer ID\n");
        header.setFill(Color.BLACK);
        header.setFont(Font.font("CiscoSans", FontWeight.BOLD, 16));
        reportsTextFlow.getChildren().addAll(header);

        this.refreshGrid("user");
    }


    /**
     *
     * Gets the string for user report
     *
     * @param rs  the rs
     * @return the string for user report
     * @throws   Exception
     */
    private String getStringForUserReport(ResultSet rs) throws Exception {

        DateTimeFormatter dtf = AppUtil.getDateTimeFormatter();
        String start = dtf.format(rs.getTimestamp("Start").toLocalDateTime());
        String end = dtf.format(rs.getTimestamp("End").toLocalDateTime());

        String row = rs.getString("User_Name") + ", " +
                rs.getString("Location") + ", " +
                rs.getInt("Appointment_ID") + ", " +
                rs.getString("Title") + ", " +
                rs.getString("Description") + ", " +
                start + ", " + end + ", " +
                rs.getInt("Customer_ID");

        return row;
    }


    /**
     *
     * Refresh grid
     *
     * @param reportType  the report type
     */
    private void refreshGrid(String reportType) {

        ResultSet rs = appointmentsDao.getResultSetForReport(reportType);
        try {
            while (rs.next()) {
                String val = "";
                switch (reportType) {
                    case "customer":
                        val = this.getStringForCustomerReport(rs);
                        break;
                    case "contact":
                        val = this.getStringForContactReport(rs);
                        break;
                    case "user":
                        val = this.getStringForUserReport(rs);

                }

                Text text = new Text(val + "\n");
                text.setFill(Color.BLACK);
                text.setFont(Font.font("CiscoSans", FontWeight.NORMAL, 16));

                reportsTextFlow.getChildren().addAll(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
    }
}