package entity;

import java.sql.*;  //Yunjing has changed this to sql DATE

/**
 * This class is to store the visiting information
 *
 * @author Castiel Huang
 * @version 1.0.0
 * @since 10/11/2014
 */
public class Visit {

    private int visitSerialNo;
    private Date time;
    private String reason;
    private String category;

    public Visit(Date time, String reason, String category) {
        this.time = time;
        this.reason = reason;
        this.category = category;
    }

    public int getVisitSerialNo() {
        return visitSerialNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
