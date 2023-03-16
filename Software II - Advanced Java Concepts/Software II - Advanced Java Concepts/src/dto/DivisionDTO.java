package dto;


import java.time.LocalDateTime;


/**
 * The class Division DT O
 */
public class DivisionDTO {


    private int id;


    private String division;


    private LocalDateTime createDate;


    private String createdBy;


    private LocalDateTime lastUpdate;


    private String lastUpdatedBy;


    private int countryId;


    private CountryDTO country;




    /**
     *
     * It is a constructor.
     *
     * @param id  the id
     * @param division  the division
     * @param createDate  the create date
     * @param createdBy  the created by
     * @param lastUpdate  the local date time
     * @param lastUpdatedBy  the last updated by
     * @param countryId  the country identifier
     */
    public DivisionDTO(int id, String division, LocalDateTime createDate, String createdBy,
                       LocalDateTime lastUpdate, String lastUpdatedBy, int countryId) {

        this.id = id;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
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
     * Gets the name
     *
     * @return the name
     */
    public String getName() {

        return division;
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



    /**
     *
     * Gets the country
     *
     * @return the country
     */
    public CountryDTO getCountry() {

        return country;
    }
}