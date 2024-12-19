package org.yearup.data;

import org.yearup.models.Category;

import java.util.List;

//Interface have the methods to interact with the database.
//The interface ensures that any implementation of the CategoryDao will have the same methods with the same signatures
public interface CategoryDao
{
    List<Category> getAllCategories();
    Category getById(int categoryId);
    Category create(Category category);
    void update(int categoryId, Category category);
    void delete(int categoryId);
}
