package entity;

import java.util.ArrayList;
import java.sql.*;//Yunjing has changed this to sql DATE

/**
 * This class is to store the data of student
 *
 * @author Castiel Huang
 * @version 1.1.0
 * @since 10/11/2014
 */
public class Student {

    private int stuSerialNo;
    private String photoURL;
    private String name;
    private Date DOB;   //also import java.sql.Date;?
    private String gender;
    private String program;
    private String email;
    private String address;
    private String nationality;
    private int stuID;
    private int receptionCounter;
    private ArrayList<Visit> visits = new ArrayList();
    private ArrayList<Announcement> announcements = new ArrayList();

    public void setStuSerialNo(int no) {
        this.stuSerialNo = no;
    }

    public int getStuSerialNo() {
        return stuSerialNo;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photo) {
        this.photoURL = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getStuID() {
        return stuID;
    }

    public void setStuID(int stuID) {
        this.stuID = stuID;
    }

    public void setReceptionCounter(int counter) {
        this.receptionCounter = counter;
    }

    public int getReceptionCounter() {
        return receptionCounter;
    }

    public ArrayList<Visit> getVisits() {
        return visits;
    }

    public void setVisits(ArrayList<Visit> visits) {
        this.visits = visits;
    }

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }
}
