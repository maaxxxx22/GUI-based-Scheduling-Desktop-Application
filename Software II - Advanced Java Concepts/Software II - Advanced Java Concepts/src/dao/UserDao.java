package dao;

import dto.ContactDTO;
import dto.CustomerDTO;
import dto.UserDTO;
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
 * The class User dao extends base dao
 */
public class UserDao extends BaseDao {
    /**
     *
     * User dao
     *
     * @return public
     */
    public UserDao() {


    }


    /**
     *
     * Validate user
     *
     * @param username  the username
     * @param password  the password
     * @return UserDTO
     */
    public UserDTO validateUser(String username, String password) {

        String query = "SELECT * FROM users WHERE User_Name = ? and Password = ? LIMIT 1;";
        UserDTO user = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                user = AppUtil.getUserDTOFromRS(results);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }

        return user;
    }


    /**
     *
     * Gets the all users
     *
     * @return the all users
     */
    public List<UserDTO> getAllUsers() {

        String query = "SELECT * FROM users;";
        List<UserDTO> lst = getUserByQuery(query,null);
        return lst;
    }


    /**
     *
     * Gets the user by identifier
     *
     * @param id  the id
     * @return the user by identifier
     */
    public UserDTO getUserById(int id) {

        String query = "SELECT * FROM users WHERE User_ID = ?;";
        List<UserDTO> lst = getUserByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the user by query
     *
     * @param query  the query
     * @param param  the param
     * @return the user by query
     */
    private List<UserDTO> getUserByQuery(String query, String param) {

        List<UserDTO> lst = new ArrayList<UserDTO>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(param != null) {
                stmt.setString(1, param);
            }
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                lst.add(AppUtil.getUserDTOFromRS(results));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
        return lst;
    }
}