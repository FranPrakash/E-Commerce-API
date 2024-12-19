package org.yearup.data;
import org.yearup.models.Profile;

//Interface have the methods to interact with the database.
//The interface ensures that any implementation of the ProfileDao will have the same methods with the same signatures

public interface ProfileDao
{
    Profile create(Profile profile); //Create a profile in the database when the user register
    Profile getByUserId(int userId); // it returns profile object when a userId is given
    void update(int userId, Profile profile); //update a profile, based on interfaceProductDao

}
