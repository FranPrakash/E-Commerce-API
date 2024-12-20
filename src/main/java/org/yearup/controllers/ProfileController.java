package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

//Categories controller that will handle requests

// added the annotations to make this a REST controller
@RestController
@RequestMapping("profile") //Specifies the base URL for the controller (/profile)
@CrossOrigin // give permission to backend API to accept request from the front-end
public class ProfileController {

    //Declaration variable
    private ProfileDao profileDao;
    private UserDao userDao;

    // Autowired controller to inject the ProfileDao and UsertDao so controller can call it methods to interact with the database
    //constructor
    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }


    // Get user profile Data
    @GetMapping("") //Handle request for the /profile path
    @PreAuthorize("isAuthenticated()")
    // is authenticated because just logging in user should be able to access the profile
    public Profile getProfile(Principal principal) {

        try {
            // get the currently logged in username
            String userName = principal.getName();

            //  Find the user in the database based on the username retrieved from principal
            User user = userDao.getByUserName(userName);
            //Calling user get id to get user ID
            int userId = user.getId();

            //calling getUserById to return user the user profile related to the ID
            return profileDao.getByUserId(userId);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    //Method to update profile
    @PutMapping("") //Handle request for the /profile path
    @PreAuthorize("isAuthenticated()") // only logged in user are able to update the profile
    public void updateProfile(Principal principal, @RequestBody Profile profile) {
        try {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            //Calling user get id to get user ID and identifier the profile to be updated
            int userId = user.getId();

            //update the new profile
            profileDao.update(userId, profile);

        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


}





