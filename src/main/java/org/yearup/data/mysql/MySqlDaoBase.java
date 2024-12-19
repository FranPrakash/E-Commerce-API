package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
//Parent class
public abstract class MySqlDaoBase
{
    private DataSource dataSource;
    @Autowired
    public MySqlDaoBase(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
//establishing connection with the database
    protected Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }
}
