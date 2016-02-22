package entity;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hanxi
 * @since 11/11/2014
 * @version 1.1.0
 */
public class GenerateStuDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        Connection conn = null;
        Statement stmt = null;
        //ResultSet rs=null;
        PreparedStatement psInsert = null;

        try {
            String dbName = "StuDatabase";
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + dbName + ";create=true", "abc", "123");

            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            //Create Student Table
            stmt.executeUpdate("create table student( stuID numeric(5), name varchar(30), dob date, gender varchar(10), program varchar(20), "
                    + "email varchar(60), address varchar(80), nationality varchar(20), photo varchar(80), primary key(stuID))");

            psInsert = conn.prepareStatement("INSERT INTO Student (stuID, name, DOB, gender, program, email, address, nationality, photo)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)");
            String[] name = new String[100];
            name = rdmName();

            for (int i = 0; i < 84; i++) {
                String fileName = "photoes/IDNow Database Photos/" + (i + 1) + ".jpg";

                psInsert.setInt(1, 43000 + i);
                psInsert.setString(2, name[i]);
                psInsert.setDate(3, rdmDOB());
                psInsert.setString(4, rdmGender());
                psInsert.setString(5, rdmProgram());
                psInsert.setString(6, email(name[i]));
                psInsert.setString(7, rdmAddress());
                psInsert.setString(8, rdmNationality());
                psInsert.setString(9, fileName);
                psInsert.execute();
            }

            //Create Visit table
            stmt.executeUpdate("create table visit (time date, reason varchar(60), stuID numeric(5), category varchar(60),primary key(stuID, time))");
            psInsert = conn.prepareStatement("INSERT INTO Visit (stuID, time, reason, category)"
                    + "VALUES (?,?,?,?)");

            for (int i = 0; i < 100; i++) {
                String category = rdmCategory();
                psInsert.setInt(1, 43000 + (new Random().nextInt(84)));
                psInsert.setDate(2, visitTime());
                psInsert.setString(3, rdmReason(category));
                psInsert.setString(4, category);
                psInsert.execute();
            }

            //Create Annuuncement table
            stmt.execute("create table announcement (annSerialNO numeric(5), time date, content varchar(60), announcer varchar(20),primary key(annSerialNO))");
            psInsert = conn.prepareStatement("INSERT INTO Announcement (annSerialNO, time, content, announcer)" + "VALUES (?,?,?,?)");

            for (int i = 0; i < 20; i++) {
                psInsert.setInt(1, i + 1);
                psInsert.setDate(2, visitTime());
                psInsert.setString(3, rdmContent());
                psInsert.setString(4, rdmAnnouncer());
                psInsert.execute();
            }

            //Create Administrator table
            stmt.execute("create table administrator (administrator varchar(20), password varchar(20))");
            psInsert = conn.prepareStatement("INSERT INTO Administrator (administrator, password)" + "VALUES (?, ?)");
            psInsert.setString(1, "abc");
            psInsert.setString(2, "123");
            psInsert.execute();

            conn.commit();
            conn.close();
            stmt.close();
            psInsert.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * email To generate a email address according to the student name
     *
     * @param name
     * @return email address
     */
    public static String email(String name) {
        name = name.replace(" ", "");
        return name.substring(0, 6) + "@andrew.cmu.edu";
    }

    /**
     * visitTime To return a random date
     *
     * @return visit time
     */
    public static Date visitTime() {
        Date visitTime = new Date(114, 1 + (new Random().nextInt(12)), 1 + (new Random().nextInt(28)));
        return visitTime;
    }

    /**
     * rdmName To create 100 names randomly
     *
     * @return a name string array that contains 100 name
     */
    public static String[] rdmName() {
        String[] firstName = {"Oriel", "Vania", "Jeff", "James", "Preston", "Katie", "Castiel", "Angela", "Clair", "Chad"};
        String[] lastName = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Wilson", "Moore", "White"};
        String[] name = new String[100];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                name[i * 10 + j] = firstName[i] + " " + lastName[j];
            }
        }
        return name;
    }

    /**
     * rdmBOD To create a birthday randomly
     *
     * @return birthday
     */
    public static Date rdmDOB() {
        Random rdm = new Random();
        Date dob = new Date((85 + rdm.nextInt(10)), (1 + rdm.nextInt(12)), (1 + rdm.nextInt(27)));
        return dob;
    }

    /**
     * rdmGender To return a gender(male/female) randomly
     *
     * @return gender
     */
    public static String rdmGender() {
        String[] gender = {"female", "male"};
        return gender[new Random().nextInt(2)];
    }

    /**
     * rdmProgram To return a program (MISM, MIST, PPM) randomly
     *
     * @return program title
     */
    public static String rdmProgram() {
        String[] program = {"MISM", "MIST", "PPM"};
        return program[new Random().nextInt(3)];
    }

    /**
     * rdmAddress To return an address randomly
     *
     * @return address
     */
    public static String rdmAddress() {
        String[] building = {"Torrens Building", "Williams Building", "Hill Building", "Evans Building", "Rogers Building", "Brooks Building"};
        String[] street = {"Morgan Street", "Wood Street", "Bell Street", "Kelly Street", "Howard Street", "King William Street"};
        return building[new Random().nextInt(6)] + ", " + street[new Random().nextInt(6)] + ", " + "Adelaide";
    }

    /**
     * rdmNationality To return a nationality randomly
     *
     * @return nationality
     */
    public static String rdmNationality() {
        String[] nations = {"China", "Japan", "German", "Americaa", "Korea", "India", "Colombia", "Venezuela"};
        return nations[new Random().nextInt(8)];
    }

    /**
     * rdmCategory To return a random visit category
     *
     * @return category
     */
    public static String rdmCategory() {
        String[] category = {"Personal affairs", "Lost and Found", "Academy", "School affairs"};
        return category[new Random().nextInt(4)];
    }

    /**
     * rdmReason To return a random visit reason according to the category title
     *
     * @param category
     * @return reason
     */
    public static String rdmReason(String category) {
        String[] categorys = {"Personal affairs", "Lost and Found", "Academy", "School affairs"};

        if (category.equals(categorys[0])) {
            String reason[] = {"Look for Nereshnee", "Ask for leaving", "Pick up mails", "Look for Suzana", "Make an appointment with Colin"};
            return reason[new Random().nextInt(5)];
        } else if (category.equals(categorys[1])) {
            String reason[] = {"Advertise for the owner of a lost thing", " Looking for a Lost Article"};
            return reason[new Random().nextInt(2)];
        } else if (category.equals(categorys[2])) {
            String reason[] = {"Ask for assignment paper", "Ask for the assignment result", "Ask for the exam result", "Find the contact number of professor", "Ask for the exam paper"};
            return reason[new Random().nextInt(5)];
        } else {
            String reason[] = {"Ask for tuition details", "Ask for social activities", "", "Request to repair"};
            return reason[new Random().nextInt(3)];
        }
    }

    /**
     * rdmContent To return a random announcement content
     *
     * @return announcement content
     */
    public static String rdmContent() {
        String content[] = {"Lecture in Classroom 1", "Lecture in the Seminer room", "Suspend classes", "Take school uniform", "Collect material"};
        return content[new Random().nextInt(5)];
    }

    /**
     * rdmAnnouncer To return a random announcer of an announcement
     *
     * @return announcer
     */
    public static String rdmAnnouncer() {
        String content[] = {"Suzana Hanic", "Katie", "Colin Underwood", "Nereshnee Shunmugam", "David Danenberg", "Nathan Urban"};
        return content[new Random().nextInt(6)];
    }
}
