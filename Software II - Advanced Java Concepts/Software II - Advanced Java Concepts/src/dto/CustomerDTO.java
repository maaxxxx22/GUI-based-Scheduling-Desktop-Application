package dto;


import java.time.LocalDateTime;



/**
 * The class Customer DT O
 */
public class CustomerDTO {


    private int id = 0;


    private String name;


    private String address;


    private String postCode;


    private String phone;


    private LocalDateTime createDate;


    private String createdBy;


    private LocalDateTime lastUpdate;


    private String lastUpdatedBy;


    private int divisionId;

    private String divisionName;

    private String countryName;

    private int countryId;





    /**
     *
     * It is a constructor.
     *
     * @param id  the id
     * @param name  the name
     * @param address  the address
     * @param postCode  the post code
     * @param phone  the phone
     * @param createDate  the create date
     * @param createdBy  the string
     * @param lastUpdate  the last update
     * @param lastUpdatedBy  the last updated by
     * @param divisionId  the division identifier
     * @param divisionName  the division name
     * @param countryName  the country name
     * @param countryId  the country identifier
     */
    public CustomerDTO(int id, String name, String address, String postCode, String phone, LocalDateTime createDate,
                       String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId, String divisionName, String countryName, int countryId) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryName = countryName;
        this.countryId = countryId;
    }



    /**
     *
     * It is a constructor.
     *
     * @param name  the name
     * @param address  the address
     * @param postCode  the post code
     * @param phone  the phone
     * @param divisionId  the division identifier
     */
    public CustomerDTO(String name, String address, String postCode, String phone, int divisionId) {

        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.phone = phone;
        this.divisionId = divisionId;
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

        return name;
    }



    /**
     *
     * Sets the name
     *
     * @param name  the name
     */
    public void setName(String name) {

        this.name = name;
    }



    /**
     *
     * Gets the address
     *
     * @return the address
     */
    public String getAddress() {

        return address;
    }



    /**
     *
     * Sets the address
     *
     * @param address  the address
     */
    public void setAddress(String address) {

        this.address = address;
    }



    /**
     *
     * Gets the post code
     *
     * @return the post code
     */
    public String getPostCode() {

        return postCode;
    }



    /**
     *
     * Sets the post code
     *
     * @param postCode  the post code
     */
    public void setPostCode(String postCode) {

        this.postCode = postCode;
    }



    /**
     *
     * Gets the phone
     *
     * @return the phone
     */
    public String getPhone() {

        return phone;
    }



    /**
     *
     * Sets the phone
     *
     * @param phone  the phone
     */
    public void setPhone(String phone) {

        this.phone = phone;
    }



    /**
     *
     * Gets the division identifier
     *
     * @return the division identifier
     */
    public int getDivisionId() {

        return divisionId;
    }



    /**
     *
     * Sets the division identifier
     *
     * @param divisionId  the division identifier
     */
    public void setDivisionId(int divisionId) {

        this.divisionId = divisionId;
    }


    /**
     *
     * Sets the division name
     *
     * @param divisionName  the division name
     */
    public void setDivisionName(String divisionName) {

        this.divisionName = divisionName;
    }


    /**
     *
     * Sets the country name
     *
     * @param countryName  the country name
     */
    public void setCountryName(String countryName) {

        this.countryName = countryName;
    }


    /**
     *
     * Gets the division name
     *
     * @return the division name
     */
    public String getDivisionName() {

        return divisionName;
    }


    /**
     *
     * Gets the country name
     *
     * @return the country name
     */
    public String getCountryName() {

        return countryName;
    }


    /**
     *
     * Gets the country identifier
     *
     * @return the country identifier
     */
    public int getCountryId() {

        return countryId;
    }


    /**
     *
     * Sets the country identifier
     *
     * @param countryId  the country identifier
     */
    public void setCountryId(int countryId) {

        this.countryId = countryId;
    }
}