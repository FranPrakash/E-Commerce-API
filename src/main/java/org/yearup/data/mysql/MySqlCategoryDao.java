package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

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
        // TODO: create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // TODO: update category
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
