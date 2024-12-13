package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// DONE: add the annotations to make this a REST controller
@RestController
// add the annotation to make this controller the endpoint for the following url
@RequestMapping("categories")
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;

    // DONE: create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao)
    {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // DONE: add the appropriate annotation for a get action
    @GetMapping(path="/categories")
    // this method will respond to http://localhost:8080/categories
    public List<Category> getAll()
    {
        // DONE: find and return all categories
        return categoryDao.getAllCategories();
    }

    // DONE: add the appropriate annotation for a get action
    @GetMapping(path = "/categories/{id}")
    public Category getById(@PathVariable int id)
    {
        // DONE: get the category by id
        return categoryDao.getById(id);
    }

    // TODO: the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("/categories/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // TODO: get a list of product by categoryId
        return productDao.listByCategoryId(categoryId);
    }

    // TODO: add annotation to call this method for a POST action
    // TODO: add annotation to ensure that only an ADMIN can call this function
    public Category addCategory(@RequestBody Category category)
    {
        // TODO: insert the category
        return null;
    }

    // TODO: add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // TODO: add annotation to ensure that only an ADMIN can call this function
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // TODO: update the category by id
    }


    // TODO: add annotation to call this method for a DELETE action - the url path must include the categoryId
    // TODO: add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id)
    {
        // TODO: delete the category by id
    }
}
