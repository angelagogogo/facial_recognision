package DAO;

import entity.Announcement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hanxi
 * @since 13/11/2014
 * @version 1.1.0
 */
public class AnnDAO {

    private String dbName = "StuDatabase";

    /**
     * getAnnouncement() method To get the announcement records from the
     * announcement table
     *
     * @return ArrayList Announcement
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Announcement> getAnnouncement() throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Announcement> a = new ArrayList<Announcement>();

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");
            stmt = conn.createStatement();

            String query = "SELECT time, content, announcer FROM announcement";
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            //for(int i=0;i<rsmd.getColumnCount();i++){
            //    rs.next();
            int i = 0;
            while (rs.next()) {
                a.add(new Announcement(i + 1, rs.getDate(1), rs.getString(2), rs.getString(3)));
                i++;
            }

            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return a;
    }
}
