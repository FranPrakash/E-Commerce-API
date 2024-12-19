package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Class that has the implementation of the methods to interact with the database (Category)
//Follows a similar pattern

@Component //Annotation to identifier as bean
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao //Implements categoryDao and override it methods
{
    //Constructor
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    // Get all categories from the database (Categories table from mySQL)
    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categories = new ArrayList<>();
       //SQL Query
        String sql = "SELECT * FROM categories;";

        //Method to connect with the database, From the parent class (DAO BASE)
        try(Connection connection = getConnection()){

            // execute the SQL Query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Execute send the SQL query to the database and return to the ResultSet
            ResultSet row = preparedStatement.executeQuery(sql);

            //While loop to loop in each row of the result set (query result)
            while(row.next()){
                Category category = mapRow(row); //Take each row of the Result set and assign it to an object (like categories)

                categories.add(category); //Add the category to the database
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return categories; //The list is returned after all categories is mapped and added to the list
    }

    //Get category by id from the databse
    @Override
    public Category getById(int categoryId)
    {
        //SQL query
        String sql = "SELECT * FROM categories WHERE category_id = ?"; // ? placeholder for the categoryId that will be provided when the method is called

        //establish connection with the database
        try (Connection connection = getConnection())
        {
            //Execute the SQL query
            PreparedStatement statement = connection.prepareStatement(sql);
            //Replace the interrogation mark , category id passed, 1 position of the placeholder
            statement.setInt(1, categoryId);
            //Execute the query by calling execute query method
            ResultSet row = statement.executeQuery();

            if (row.next()) //If there is  a category it will return te result set that has the result of the query
            {
                return mapRow(row); //(Map row  converts the ResultSet into a Category
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null; //if nothing is found return null
    }

    //Create a new category in the categories table
    @Override
    public Category create(Category category)
    {
        //SQL query
        String sql = "INSERT INTO categories(category_id, name, description) " +
                " VALUES (?, ?, ?);"; // 3 values will be inserted into the categories table

        try (Connection connection = getConnection())
        {
            //Execute the sql query
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //Set the values of the category
            statement.setInt(1, category.getCategoryId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());

             //Execute the statement  returning the number of row affected by the query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) { // if the rows affected is grater than 0 it was successful
               //contains the new category
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) { //if it contains data
                    // Retrieve the auto-incremented ID
                    int categoryId = generatedKeys.getInt(1);


                    return getById(categoryId); //if successful return the category id
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null; // if no row was created the method will return null
    }


    //Method to updated new categories in the database
    @Override
    public void update(int categoryId, Category category)
    {
        // SQl Query
        String sql = "UPDATE categories" +
                " SET category_id = ? " +
                "   , name = ? " +
                "   , description = ? " +
                " WHERE category_id = ?;"; //Category_id is a primary key

        //Connect with the database
        try (Connection connection = getConnection())
        {
            //Execute the query  with it parameters
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, category.getCategoryId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());
            statement.setInt(4,categoryId);

            //calling of execute update method to return the  number of rows affected by the query
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

//Delete categorys from the database
    @Override
    public void delete(int categoryId)
    {
        // SQL query
        String sql = "DELETE FROM categories " +
                " WHERE category_id = ?;";
          //Connect with the database
        try (Connection connection = getConnection()) //calling this getconnection method
        {
            //Execute the query and set the parameters (question marks)
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);

//calling of execute update method to return the  number of rows affected by the query
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    //Helper method to map convert each row of the query into an object .
    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
