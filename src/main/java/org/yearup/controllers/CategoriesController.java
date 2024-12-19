package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import java.util.List;

//Missing annotations and methods implementation. Added try catch in all the methods

// added the annotations to make this a REST controller
@RestController //Tell springBoot that this class will handle requests
@RequestMapping("categories") //Specifies the base URL for the controller (/categories)
@CrossOrigin // give permission to backend API to accept request from the front-end
public class CategoriesController {

    private CategoryDao categoryDao; // interface interact with the database to get category
    private ProductDao productDao; //interact with the database to get product

   // Autowired controller to inject the categoryDao and ProductDao so controller can call it methods to interact with the database
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // GetAll Method. Get all categories of the database
    @GetMapping("") //This annotation is for getting request for this method.
    @PreAuthorize("permitAll()") //Anyone can access this endpoint

    public List<Category> getAll() { //return list of category model class that represent the table

        try { //Handle exceptions may happens when interacting with the database

            //Calling getallcategories method from the MysqlcategoriesDAO. Method interact with the database to get all category from the database
            return categoryDao.getAllCategories();

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // Method to get category by ID
    @GetMapping("{id}") //Id here is dynamic. Can be replaced with any valid ID in the URL
    @PreAuthorize("permitAll()") //Allows any user to run this (even if they are not logged in)
    public Category getById(@PathVariable int id) {
        // DONE: get the category by id
        try {
            var category = categoryDao.getById(id); //Calling getByIDmethod from the MysqlcategoriesDAO.

            if(category == null) //return null if no category is found
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return category; //If category is found the method will return it

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    //the url to return list of products which have category as 1
    //Added request in postman collection getProductByCategoryID
    @GetMapping("{categoryId}/products") //added request in postman collection getProductByCategoryID
    public List<Product> getProductsByCategoryId(@PathVariable int categoryId) {
        try {
            // DONE: get a list of product by categoryId
            return productDao.listByCategoryId(categoryId);

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    //DONE: add annotation to call this method for a POST action
    @PostMapping()
    //DONE: add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        try {
            // DONE: insert the category
            return categoryDao.create(category);

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // Done: add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // Done: add annotation to ensure that only an ADMIN can call this function
    //Created
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {

            //Before productDao.create(product);
            categoryDao.update(id, category);

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // Done: add annotation to call this method for a DELETE action - the url path must include the categoryId
    // Done: add annotation to ensure that only an ADMIN can call this function
    //Created this in postman
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCategory(@PathVariable int id) {
        try {
            // Done: delete the category by id
            var category = categoryDao.getById(id);

            if (category == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.delete(id);
        }
        catch (Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }
}
