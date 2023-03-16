package dao;

import dto.AppointmentDTO;
import dto.CountryDTO;
import util.AppLogger;
import util.AppUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * The class Country dao extends base dao
 */
public class CountryDao extends BaseDao {

    /**
     *
     * Gets the appointment by identifier
     *
     * @param id  the id
     * @return the appointment by identifier
     */
    public CountryDTO getAppointmentById(int id) {
        String query = "SELECT * FROM countries WHERE Country_ID WHERE Appointment_ID = ?;";
        List<CountryDTO> lst = getCountriesByQuery(query,String.valueOf(id));
        if(lst != null && lst.size() > 0) {
            return lst.get(0);
        }
        return null;
    }


    /**
     *
     * Gets the all countries
     *
     * @return the all countries
     */
    public List<CountryDTO> getAllCountries() {

        String query = "SELECT * FROM countries";
        return getCountriesByQuery(query,null);
    }


    /**
     *
     * Gets the countries by query
     *
     * @param query  the query
     * @param param  the param
     * @return the countries by query
     */
    private List<CountryDTO> getCountriesByQuery(String query, String param) {

        List<CountryDTO> lst = new ArrayList<CountryDTO>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            if(param != null) {
                stmt.setString(1, param);
            }
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                lst.add(AppUtil.getCountryDTOFromRS(results));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AppLogger.error(e.getMessage(),e);
        }
        return lst;
    }
}
