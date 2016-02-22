package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is to verify username and password
 *
 * @author Castiel Huang
 * @version 1.1.0
 * @since 18/11/2014
 */
public class LoginCheck {

    private String userName;
    private String password;
    private String dbName = "stuDatabase";

    /**
     * The Constructor
     *
     * @param userName user's username
     * @param password user's password
     */
    public LoginCheck(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Validate username & password
     *
     * @return true if both are matched, false otherwise
     * @throws ClassNotFoundException if the JDBC driver cannot be found
     * @throws SQLException if there is an exception when deploying SQL
     */
    public boolean validate() throws ClassNotFoundException, SQLException {
        String password = "";

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");  //the JDBC driver
        //connect the Derby database
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");

        Statement s = conn.createStatement();   //create statement

        //search for user
        ResultSet rs = s.executeQuery("select password from ADMINISTRATOR where "
                + "administrator = " + "'" + userName + "'");

        //search for user's password
        while (rs.next()) {
            password = rs.getString(1);
        }

        conn.close();    //close the connection once it's done

        //validate password
        if (!password.equals("")) {
            return (this.password.equals(password));
        } //if the password is null, return false
        else {
            return false;
        }

    }

}
