package dao;

import dto.CountryDTO;
import dto.DivisionDTO;
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
 * The class Division dao extends base dao
 */
public class DivisionDao extends BaseDao {

    /**
     *
     * Gets the division by identifier
     *
     * @param id  the id
     * @return the division by identifier
     */
    public DivisionDTO getDivisionById(int id) {

        String query = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        List<DivisionDTO> lst = getDivisionByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the division by country
     *
     * @param country  the country
     * @return the division by country
     */
    public List<DivisionDTO> getDivisionByCountry(String country) {

        String query = "SELECT d.* FROM countries c, first_level_divisions d WHERE c.country = ? and c.Country_ID = d.Country_ID;";
        List<DivisionDTO> lst = getDivisionByQuery(query,country);
        return lst;
    }


    /**
     *
     * Gets the division by field
     *
     * @param field  the field
     * @param value  the value
     * @return the division by field
     */
    public DivisionDTO getDivisionByField(String field, String value) {

        String query = String.format("SELECT * FROM first_level_divisions WHERE %s = ? LIMIT 1;", field);
        List<DivisionDTO> lst = getDivisionByQuery(query,value);
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the division by query
     *
     * @param query  the query
     * @param param  the param
     * @return the division by query
     */
    private List<DivisionDTO> getDivisionByQuery(String query, String param) {

        List<DivisionDTO> lst = new ArrayList<DivisionDTO>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(param != null) {
                stmt.setString(1, param);
            }
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                lst.add(AppUtil.getDivisionDTOFromRS(results));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
        return lst;
    }

}