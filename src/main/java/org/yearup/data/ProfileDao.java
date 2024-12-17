package org.yearup.data;
import org.yearup.models.Profile;


// You will need to update the DAO to add the getByUserID and update methods
public interface ProfileDao
{
    Profile create(Profile profile); //Create a profile in the database when the user register
    Profile getByUserId(int userId); // it returns profile object when a userId is given
    void update(int userId, Profile profile); //update a profile, based on interfaceProductDao

}
