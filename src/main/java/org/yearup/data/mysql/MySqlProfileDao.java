package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;


//Implementation of the methods to interact with the database (Profile )

//Follows a similar pattern
@Component //Annotation to identifier as bean
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao {

    //Constructor
    public MySqlProfileDao(DataSource dataSource) {
        super(dataSource);
    }

    //Create a new profile to the database
    @Override
    public Profile create(Profile profile) {
        //SQL Query
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //Connect with the database
        try (Connection connection = getConnection()) {
            //Execute the query
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            //Execute the query and insert the new profile to the database
            ps.executeUpdate();

            return profile; //Return the profile

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Get user profile based on the user Id
    @Override
    public Profile getByUserId(int userId) {
        //SQL query
        String sql = "SELECT * FROM profiles WHERE user_id = ?";
        //Connect with the databases
        try (Connection connection = getConnection()) {
            //execute the query
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery(); //Execute the query e store in result set

            if (row.next()) { //check if there is at rows
                return mapRow(row); // if find call the mapRow to convert the data into a profile object
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Update tge
    @Override
    public void update(int userId, Profile profile) {//user id that need to be updated and the profile object that have the user info

        //SQL Query
        String sql = "UPDATE profiles" +
                " SET user_id = ? " +
                "   , first_name = ? " +
                "   , last_name = ? " +
                "   , phone = ? " +
                "   , email = ? " +
                "   , address = ? " +
                "   , city = ? " +
                "   , state = ? " +
                "   , zip =   ?" +
                " WHERE user_id = ?;";
          //Connect with the database
        try (Connection connection = getConnection()) {
            //Set the parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2, profile.getFirstName());
            statement.setString(3, profile.getLastName());
            statement.setString(4, profile.getPhone());
            statement.setString(5, profile.getEmail());
            statement.setString(6, profile.getAddress());
            statement.setString(7, profile.getAddress());
            statement.setString(8, profile.getCity());
            statement.setString(9, profile.getZip());
            statement.setInt(10, userId);
          //execute the query and update the database
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    //Maprow method to read the rows from the database and map the values
    private Profile mapRow(ResultSet row) throws SQLException {
        int userId = row.getInt("user_id");
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        String phone = row.getString("phone");
        String email = row.getString("email");
        String address = row.getString("address");
        String city = row.getString("city");
        String state = row.getString("state");
        String zip = row.getString("zip");
          //Return new profile (populate it fields)
        return new Profile(userId, firstName, lastName, phone, email, address, city, state, zip);
    }


}
