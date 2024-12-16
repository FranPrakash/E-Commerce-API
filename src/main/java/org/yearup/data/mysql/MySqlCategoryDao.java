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

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        // DONE: get all categories
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories;";
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rows = preparedStatement.executeQuery(sql);
            while(rows.next()){
                Category category = new Category();
                category.setCategoryId(rows.getInt("category_id"));
                category.setName(rows.getString("name"));
                category.setDescription(rows.getString("description"));
                categories.add(category);
            }
        }
        catch (SQLException e){ System.out.println(e); }
        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        // TODO: get category by id
        Category myCategory = new Category();
        String sql = "SELECT * FROM categories WHERE category_id = "+categoryId;
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rows = preparedStatement.executeQuery(sql);
            while(rows.next()){
                myCategory.setCategoryId(rows.getInt("category_id"));
                myCategory.setName(rows.getString("name"));
                myCategory.setDescription(rows.getString("description"));
                break;
            }
        }
        catch (SQLException e){ System.out.println(e); }
        return myCategory;
    }

    @Override
    public Category create(Category category)
    {
        // DONE: create a new category. Created based on the MySQLProductDao

        String sql = "INSERT INTO categories(category_id, name, description) " +
                " VALUES (?, ?, ?);";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, category.getCategoryId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());

            int rowsAffected = statement.executeUpdate(); //When execute the statement will give a value of how many roles were affected in the database

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Retrieve the auto-incremented ID
                    int categoryId = generatedKeys.getInt(1);

                    // get the newly inserted category
                    return getById(categoryId);
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public void update(int categoryId, Category category)
    {
        // Done: update category
        String sql = "UPDATE categories" +
                " SET category_id = ? " +
                "   , name = ? " +
                "   , description = ? " +
                " WHERE category_id = ?;"; //Category_id is a primary key
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, category.getCategoryId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDescription());
            statement.setInt(4,categoryId);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int categoryId)
    {
        // TODO: delete category
    }

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
