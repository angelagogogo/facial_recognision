package entity;

import java.util.Date;

/**
 * This class is to store the information of announcement
 *
 * @author Castiel Huang
 * @version 1.0.0
 * @since 10/11/2014
 */
public class Announcement {

    private int annSerialNo;
    private Date time;
    private String content;
    private String announcer;

    public Announcement(int annSerialNo, Date time, String content, String announcer) {
        this.annSerialNo = annSerialNo;
        this.time = time;
        this.content = content;
        this.announcer = announcer;
    }

    public int getAnnSerialNo() {
        return annSerialNo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnnouncer() {
        return announcer;
    }

    public void setAnnouncer(String announcer) {
        this.announcer = announcer;
    }

}
