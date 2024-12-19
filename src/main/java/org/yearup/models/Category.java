package org.yearup.models;

//has the same structure as the category table in the database to provide a mapping/representation between the data in the database.

public class Category
{
    private int categoryId;
    private String name;
    private String description;

    public Category()
    {
    }
//Constructor
    public Category(int categoryId, String name, String description)
    {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    //Getters and Setters
    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
