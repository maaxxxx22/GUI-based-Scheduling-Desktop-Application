package dto;




/**
 * The class Contact DT O
 */
public class ContactDTO {


    private int id;


    private String name;


    private String email;



    /**
     *
     * It is a constructor.
     *
     * @param id  the id
     * @param name  the name
     * @param email  the email
     */
    public ContactDTO(int id, String name, String email) {

        this.id = id;
        this.name = name;
        this.email = email;
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
     * Gets the email
     *
     * @return the email
     */
    public String getEmail() {

        return email;
    }
}
