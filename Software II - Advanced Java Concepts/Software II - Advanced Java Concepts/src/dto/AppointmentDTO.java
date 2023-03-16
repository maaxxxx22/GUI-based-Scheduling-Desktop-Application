package dto;

import java.time.LocalDateTime;




/**
 * The class Appointment DT O
 */
public class AppointmentDTO {


    private int id = 0;


    private String title;


    private String description;


    private String location;


    private String type;


    private LocalDateTime start;


    private LocalDateTime end;


    private LocalDateTime createDate;


    private String createdBy;


    private LocalDateTime lastUpdate;


    private String lastUpdatedBy;


    private int customerId;


    private int userId;


    private int contactId;


    /**
     *
     * Gets the customer name
     *
     * @return the customer name
     */
    public String getCustomerName() {

        return customerName;
    }


    /**
     *
     * Sets the customer name
     *
     * @param customerName  the customer name
     */
    public void setCustomerName(String customerName) {

        this.customerName = customerName;
    }


    /**
     *
     * Gets the user name
     *
     * @return the user name
     */
    public String getUserName() {

        return userName;
    }


    /**
     *
     * Sets the user name
     *
     * @param userName  the user name
     */
    public void setUserName(String userName) {

        this.userName = userName;
    }


    /**
     *
     * Gets the contact name
     *
     * @return the contact name
     */
    public String getContactName() {

        return contactName;
    }


    /**
     *
     * Sets the contact name
     *
     * @param contactName  the contact name
     */
    public void setContactName(String contactName) {

        this.contactName = contactName;
    }

    private String customerName;

    private String userName;

    private String contactName;





    /**
     *
     * It is a constructor.
     *
     * @param id  the id
     * @param title  the title
     * @param description  the description
     * @param location  the location
     * @param type  the type
     * @param start  the start
     * @param end  the local date time
     * @param createDate  the create date
     * @param createdBy  the created by
     * @param lastUpdate  the last update
     * @param lastUpdatedBy  the string
     * @param customerId  the customer identifier
     * @param userId  the user identifier
     * @param contactId  the contact identifier
     * @param customerName  the customer name
     * @param userName  the user name
     * @param contactName  the contact name
     */
    public AppointmentDTO(int id, String title, String description, String location, String type, LocalDateTime start,
                          LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate,
                          String lastUpdatedBy, int customerId, int userId, int contactId,String customerName,String userName,String contactName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.customerName = customerName;
        this.userName = userName;
        this.contactName = contactName;

    }



    /**
     *
     * It is a constructor.
     *
     * @param title  the title
     * @param description  the description
     * @param location  the location
     * @param type  the type
     * @param start  the start
     * @param end  the local date time
     * @param contactId  the contact identifier
     * @param userId  the user identifier
     * @param customerId  the customer identifier
     */
    public AppointmentDTO(String title, String description, String location, String type, LocalDateTime start,
                          LocalDateTime end, int contactId, int userId, int customerId) {

        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.contactId = contactId;
        this.userId = userId;
        this.customerId = customerId;

    }





    /**
     *
     * Gets the identifier
     *
     * @return the identifier
     */
    public int getId() {

        return id;
    }



    /**
     *
     * Gets the title
     *
     * @return the title
     */
    public String getTitle() {

        return title;
    }



    /**
     *
     * Sets the title
     *
     * @param title  the title
     */
    public void setTitle(String title) {

        this.title = title;
    }



    /**
     *
     * Gets the description
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }



    /**
     *
     * Sets the description
     *
     * @param description  the description
     */
    public void setDescription(String description) {

        this.description = description;
    }



    /**
     *
     * Gets the location
     *
     * @return the location
     */
    public String getLocation() {

        return location;
    }



    /**
     *
     * Sets the location
     *
     * @param location  the location
     */
    public void setLocation(String location) {

        this.location = location;
    }



    /**
     *
     * Gets the type
     *
     * @return the type
     */
    public String getType() {

        return type;
    }



    /**
     *
     * Sets the type
     *
     * @param type  the type
     */
    public void setType(String type) {

        this.type = type;
    }



    /**
     *
     * Gets the start
     *
     * @return the start
     */
    public LocalDateTime getStart() {

        return start;
    }



    /**
     *
     * Sets the start
     *
     * @param start  the start
     */
    public void setStart(LocalDateTime start) {

        this.start = start;
    }



    /**
     *
     * Gets the end
     *
     * @return the end
     */
    public LocalDateTime getEnd() {

        return end;
    }



    /**
     *
     * Sets the end
     *
     * @param end  the end
     */
    public void setEnd(LocalDateTime end) {

        this.end = end;
    }





    /**
     *
     * Gets the customer identifier
     *
     * @return the customer identifier
     */
    public int getCustomerId() {

        return customerId;
    }



    /**
     *
     * Sets the customer identifier
     *
     * @param customerId  the customer identifier
     */
    public void setCustomerId(int customerId) {

        this.customerId = customerId;
    }





    /**
     *
     * Gets the user identifier
     *
     * @return the user identifier
     */
    public int getUserId() {

        return userId;
    }



    /**
     *
     * Sets the user identifier
     *
     * @param userId  the user identifier
     */
    public void setUserId(int userId) {

        this.userId = userId;
    }





    /**
     *
     * Gets the contact identifier
     *
     * @return the contact identifier
     */
    public int getContactId() {

        return contactId;
    }



    /**
     *
     * Sets the contact identifier
     *
     * @param contactId  the contact identifier
     */
    public void setContactId(int contactId) {

        this.contactId = contactId;
    }





    @Override

/**
 *
 * Equals
 *
 * @param obj  the obj
 * @return boolean
 */
    public boolean equals(Object obj) {


        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final AppointmentDTO other = (AppointmentDTO) obj;

        return this.id == other.getId();
    }
}