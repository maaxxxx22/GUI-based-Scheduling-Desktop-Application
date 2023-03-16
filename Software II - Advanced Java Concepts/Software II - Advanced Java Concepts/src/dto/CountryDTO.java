package dto;

import java.time.LocalDateTime;


/**
 * The class Country DT O
 */
public class CountryDTO {


    private int countryId;


    private String country;


    private LocalDateTime createDate;


    private String createdBy;


    private LocalDateTime lastUpdate;


    private String lastUpdatedBy;




    /**
     *
     * It is a constructor.
     *
     * @param countryId  the country identifier
     * @param country  the country
     * @param createDate  the create date
     * @param createdBy  the created by
     * @param createDate  the local date time
     * @param lastUpdatedBy  the last updated by
     */
    public CountryDTO(int countryId, String country, LocalDateTime createDate, String createdBy,
                      LocalDateTime lastUpdate, String lastUpdatedBy) {

        this.countryId = countryId;
        this.country = country;
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

        return countryId;
    }



    /**
     *
     * Gets the name
     *
     * @return the name
     */
    public String getName() {

        return country;
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