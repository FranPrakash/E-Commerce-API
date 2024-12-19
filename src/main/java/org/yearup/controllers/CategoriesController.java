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

//Categories controller that will handle requests

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

    // Get all categories of the database
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

    // Method to GET category by ID
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


    //This method GET Product ByCategoryID
    @GetMapping("{categoryId}/products") //Annotation defines this method get category id from products
    public List<Product> getProductsByCategoryId(@PathVariable int categoryId) { // @PathVariable maps the category id to the method

        try {

            return productDao.listByCategoryId(categoryId); // return a list of product associated with the categoryId provided

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


    //This method handle POST requests// Add categories to the database
    @PostMapping()
    //Annotation to ensure that only users with Admin permission can call this  method
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category) { //Annotation to tell springboot to "read" the data from the request body

        try {

            return categoryDao.create(category); //Calling create method from Categorydao which will insert the new category object into the database

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


     //This method UPDATE the category with the provided ID
    @PutMapping("{id}") //annotation to map the method to Update request (PUT)
    @PreAuthorize("hasRole('ROLE_ADMIN')") //annotation to allow only admins to update
    public void updateCategory(@PathVariable int id, @RequestBody Category category) { //Path maps the ID and Request Map the JSON body of the request

        try {
            //This was one of the bug, it was productDao.create(product), Now it calls the update method to update the category
            categoryDao.update(id, category); //Id specifies which record to update and the category

        } catch (Exception ex) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    //This method DELETE a category from the database
    @DeleteMapping("{id}") //Id of the category to be deleted
    @PreAuthorize("hasRole('ROLE_ADMIN')") //Only admin can delete a category
    public void deleteCategory(@PathVariable int id) { //Path map the ID

        try {
            // Done: delete the category by id
            var category = categoryDao.getById(id); //Finf the category by id

            if (category == null) //Check if there is a category if so
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            categoryDao.delete(id); //delete method is called
        }
        catch (Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

    }
}
