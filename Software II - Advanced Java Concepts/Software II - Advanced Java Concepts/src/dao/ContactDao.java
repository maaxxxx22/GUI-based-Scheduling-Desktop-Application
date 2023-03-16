package dao;

import dto.AppointmentDTO;
import dto.ContactDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.AppLogger;
import util.AppUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The class Contact dao extends base dao
 */
public class ContactDao extends BaseDao {


    /**
     *
     * Gets the all contacts
     *
     * @return the all contacts
     */
    public List<ContactDTO> getAllContacts() {

        String query = "SELECT * FROM contacts";
        List<ContactDTO> lst = getContactByQuery(query,null);
        return lst;
    }


    /**
     *
     * Gets the contact by identifier
     *
     * @param id  the id
     * @return the contact by identifier
     */
    public ContactDTO getContactById(int id) {

        String query = "SELECT * FROM contacts WHERE Contact_ID = ?;";
        List<ContactDTO> lst = getContactByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the contact by query
     *
     * @param query  the query
     * @param param  the param
     * @return the contact by query
     */
    private List<ContactDTO> getContactByQuery(String query, String param) {

        List<ContactDTO> lst = new ArrayList<ContactDTO>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(param != null) {
                stmt.setString(1, param);
            }
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                lst.add(AppUtil.getContactDTOFromRS(results));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
        return lst;
    }
}
