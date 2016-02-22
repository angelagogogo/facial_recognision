package DAO;

import entity.Visit;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hanxi
 * @since 13/11/2014
 * @version 1.1.0
 */
public class VisitDAO {

    private String dbName = "StuDatabase";

    /**
     * getVisit() method To get the visit record of a student according to the
     * student ID number
     *
     * @param stuID
     * @return visit ArrayList
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<Visit> getVisit(int stuID) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Visit> v = new ArrayList<Visit>();

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");
            stmt = conn.createStatement();

            String query = "SELECT time, reason, category FROM student, visit WHERE student.stuID = visit.stuID AND student.stuID = " + stuID;
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                v.add(new Visit(rs.getDate(1), rs.getString(2), rs.getString(3)));
            }
            stmt.close();
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return v;
    }

    public void setVisit(int stuID, Date time, String reason, String category) throws SQLException, ClassNotFoundException, FileNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement insert = null;
        try {

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");
            //conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "INSERT INTO visit(stuID, time, reason, category) VALUES(?,?,?,?)";
            insert = conn.prepareStatement(sql);
            insert.setInt(1, stuID);
            insert.setDate(2, time);
            insert.setString(3, reason);
            insert.setString(4, category);
            insert.execute();
            conn.commit();
            stmt.close();
            insert.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
