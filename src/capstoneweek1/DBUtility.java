/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstoneweek1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Will
 */
public class DBUtility {

    // creating class variables used to connect to the database.
    // watch this youtube video to add the "connector" - https://www.youtube.com/watch?v=nW13FmTdkjc
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet resultSet = null;

    // dbConnect will connect to the database on the local host
    public void dbConnect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Capstone2019", "root", "mysql");
        Statement stmt = null;
    }

    // checkLogin will take the username and password entered as strings
    // I'm not super great at SQL but I got this to work so far.
    public boolean checkLogin(String inputUserName, String inputPassword) throws SQLException {
        String username = null;
        String password = null;
        
        //first have to creat a statement
        stmt = conn.createStatement();
        // this runs the SQL query - notice the extra single quotes around the string.  Don't forget those.
        resultSet = stmt.executeQuery("SELECT * FROM userLogin WHERE userName = '" + inputUserName + "'");

        //In order to read what the query returns, we have to use resultSet.next(), otherwise we get an error
        //Here I am assigning the correct password with the username
        if (resultSet.next()) {

            username = resultSet.getString("userName");
            password = resultSet.getString("userPassword");
        } else {
            System.out.println("Username does not exist!");
        }

        //checking to see if the user entered password equals the password stored in the database
        if (inputPassword.contains(password)) {
            return true;
        } else {
            return false;
        }

    }
    
    // constructor
    public DBUtility(){};

}