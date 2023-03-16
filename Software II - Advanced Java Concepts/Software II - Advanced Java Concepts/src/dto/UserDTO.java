package dto;


import java.time.LocalDateTime;



/**
 * The class User DT O
 */
public class UserDTO {


    private int id;


    private String username;


    private String password;


    private LocalDateTime createDate;

    private String createdBy;

    private LocalDateTime lastUpdate;

    private String lastUpdatedBy;



    /**
     *
     * It is a constructor.
     *
     * @param id  the id
     * @param username  the username
     * @param password  the password
     * @param createDate  the create date
     * @param lastUpdate  the last update
     * @param lastUpdatedBy  the last updated by
     */
    public UserDTO(int id, String username, String password, LocalDateTime createDate,
                   String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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
     * Gets the username
     *
     * @return the username
     */
    public String getUsername() {

        return username;
    }



    /**
     *
     * Gets the password
     *
     * @return the password
     */
    public String getPassword() {

        return password;
    }



    /**
     *
     * Gets the create date
     *
     * @return the create date
     */
    public LocalDateTime getCreateDate() {

        return createDate;
    }



    /**
     *
     * Gets the created by
     *
     * @return the created by
     */
    public String getCreatedBy() {

        return createdBy;
    }



    /**
     *
     * Gets the last update
     *
     * @return the last update
     */
    public LocalDateTime getLastUpdate() {

        return lastUpdate;
    }



    /**
     *
     * Gets the last updated by
     *
     * @return the last updated by
     */
    public String getLastUpdatedBy() {

        return lastUpdatedBy;
    }

}