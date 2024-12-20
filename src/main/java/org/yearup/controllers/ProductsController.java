package org.yearup.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Product;
import org.yearup.data.ProductDao;

import java.math.BigDecimal;
import java.util.List;

//Products controller that will handle requests

@RestController //Tell springBoot that this class will handle requests
@RequestMapping("products") // Specifies the base URL for the controller (/Products)
@CrossOrigin // Give permission to backend API to accept request from the front-end
public class ProductsController
{
    private ProductDao productDao; //creating an object. interface interact with the database to get product

    // Autowired controller to inject the ProductDao so controller can call it methods to interact with the database
    @Autowired
    public ProductsController(ProductDao productDao)
    {
        this.productDao = productDao;
    }

    @GetMapping("") //Mapping the method with no additional path
    @PreAuthorize("permitAll()") //No permission is necessary
    //Parameters to filter the search
    public List<Product> search(@RequestParam(name="cat", required = false) Integer categoryId,
                                @RequestParam(name="minPrice", required = false) BigDecimal minPrice,
                                @RequestParam(name="maxPrice", required = false) BigDecimal maxPrice,
                                @RequestParam(name="color", required = false) String color
                                )
    {
        try
        {
            return productDao.search(categoryId, minPrice, maxPrice, color); //product dao is called with it parameters to perform the search
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @GetMapping("{id}") //Id here is dynamic. Can be replaced with any valid ID in the URL
    @PreAuthorize("permitAll()") //Allows any user to run this (even if they are not logged in)
    public Product getById(@PathVariable int id )
    {
        try
        {
            var product = productDao.getById(id); // Calling getByIDmethod from the MysqlProductDAO.

            if(product == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return product;
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product addProduct(@RequestBody Product product)
    {
        try
        {
            return productDao.create(product);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    //TODO: Bug Updated fixed.Update the product instead of creating a new one
    //The wrong method was being called (created) which created duplicate products, now calling the Update method from Dao
    @PutMapping("{id}") //Maps method to a Put (Update) request
    @PreAuthorize("hasRole('ROLE_ADMIN')") //Only user with the role_admin can access it
    public void updateProduct(@PathVariable int id, @RequestBody Product product) //Path maps the ID and Request Map the JSON body of the request
    {
        try //Handle any errors
        {
            //Calling update method from the productDao.Everytime a product is updated it will not create a duplicated product.
            productDao.update(id, product);
            //have to provide 2 inputs id, products because the update method requires two input parameters - id and product.
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");

            //No return. doest need a response body
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable int id)
    {
        try
        {
            var product = productDao.getById(id); //calling getbyid method from productdao

            if(product == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            productDao.delete(id);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
