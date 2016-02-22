package DAO;

import entity.Student;
import entity.Visit;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class StuDAO {

    private String dbName = "stuDatabase";

    /**
     * getStudent(in stuID) method To get the a student objects according to the
     * student ID number
     *
     * @param stuID
     * @return student object
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public Student getStudent(int stuID) throws ClassNotFoundException, SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Student s = new Student();

        try {

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");

//            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String query = "SELECT name, photo, dob, gender, program, email, address, nationality FROM student WHERE stuID = " + stuID;
            rs = stmt.executeQuery(query);
            int i = 0;
            rs.next();
            s.setName(rs.getString(1));
            s.setPhotoURL(rs.getString(2));
            s.setDOB(rs.getDate(3));
            s.setGender(rs.getString(4));
            s.setProgram(rs.getString(5));
            s.setEmail(rs.getString(6));
            s.setAddress(rs.getString(7));
            s.setNationality(rs.getString(8));

            s.setVisits(new VisitDAO().getVisit(stuID));
            s.setStuSerialNo(i + 1);
            //s.setReceptionCounter(getVisit(rs.getInt(9)).size());

            //conn.commit();
            stmt.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return s;
    }

    /**
     * getStudent() method To get an ArrayList of all the student objects
     *
     * @return student ArrayList
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<Student> getStudent() throws ClassNotFoundException, SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Student> s = new ArrayList<Student>();
        ArrayList<Visit> v;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");

//            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String query = "SELECT name, photo, dob, gender, program, email, address, nationality, stuID FROM student";
            rs = stmt.executeQuery(query);
            int i = 0;

            while (rs.next()) {
                s.add(new Student());
                s.get(i).setName(rs.getString(1));
                s.get(i).setPhotoURL(rs.getString(2));
                s.get(i).setDOB(rs.getDate(3));
                s.get(i).setGender(rs.getString(4));
                s.get(i).setProgram(rs.getString(5));
                s.get(i).setEmail(rs.getString(6));
                s.get(i).setAddress(rs.getString(7));
                s.get(i).setNationality(rs.getString(8));
                s.get(i).setStuID(rs.getInt(9));
                //v = getVisit(rs.getInt(9))
                v = new VisitDAO().getVisit(rs.getInt(9));
                s.get(i).setVisits(v);

                s.get(i).setStuSerialNo(i + 1);
                s.get(i).setReceptionCounter(v.size());
                i++;
            }
//            conn.commit();
            stmt.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return s;
    }

    /**
     * setStudent() method To insert a student record into the student table
     *
     * @param stuID
     * @param name
     * @param photoURL
     * @param dob
     * @param program
     * @param email
     * @param address
     * @param nationality
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public void setStudent(int stuID, String name, String photoURL, Date dob, String program, String email, String address, String nationality, String gender) throws SQLException, ClassNotFoundException, FileNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement insert = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName, "abc", "123");

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "INSERT INTO student (stuID, name, photo, dob, program, email, address, nationality, gender)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";

            insert = conn.prepareStatement(sql);
            insert.setInt(1, stuID);
            insert.setString(2, name);
            insert.setString(3, photoURL);
            insert.setDate(4, dob);
            insert.setString(5, program);
            insert.setString(6, email);
            insert.setString(7, address);
            insert.setString(8, nationality);
            insert.setString(9, gender);
            insert.execute();

            conn.commit();
            stmt.close();
            insert.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

}
