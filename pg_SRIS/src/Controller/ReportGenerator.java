package Controller;

import entity.Announcement;
import entity.Student;
import entity.Visit;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Yunjing Li
 * @version 1.1.0
 * @since 19/11/2014
 */
public class ReportGenerator {

    /**
     * set model to generate report on student visit list by a given date range
     * and category.
     *
     * @param a total student list
     * @param f date from
     * @param t date to
     * @param c category
     * @return model for JTable
     */
    public static DefaultTableModel stuModel(ArrayList<Student> a, Date f, Date t, String c) {
        ArrayList<Student> returnList = new ArrayList();
        for (Student s : a) {
            ArrayList<Visit> visitTemp = s.getVisits();
            for (Visit v : visitTemp) {
                if (v.getTime().after(f) && v.getTime().before(t) && v.getCategory().equals(c)) {
                    returnList.add(s);
                }
            }
        }
        Object[][] getResult = new Object[returnList.size()][5];
        String[] title = {"Student ID", "Name", "Gender", "Program", "Nationality"};
        for (int i = 0; i < returnList.size(); i++) {
            getResult[i][0] = returnList.get(i).getStuID();
            getResult[i][1] = returnList.get(i).getName();
            getResult[i][2] = returnList.get(i).getGender();
            getResult[i][3] = returnList.get(i).getProgram();
            getResult[i][4] = returnList.get(i).getNationality();
        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(getResult, title);
        return model;
    }

    /**
     * set model to generate report on visit frequency list by a given date
     * range and category.
     *
     * @param a total student list
     * @param f date from
     * @param t date to
     * @param c category/gender/program
     * @return model for JTable
     */
    public static DefaultTableModel freModel(ArrayList<Student> a, Date f, Date t, String c) {
        ArrayList<Student> returnList = new ArrayList();
        int visitFrequency = 0;
        if (c.equals("Personal affairs") || c.equals("Lost and Found") || c.equals("Academy") || c.equals("School affairs")) {
            for (Student s : a) {
                ArrayList<Visit> visitTemp = s.getVisits();
                for (Visit v : visitTemp) {
                    if (v.getTime().after(f) && v.getTime().before(t) && v.getCategory().equals(c)) {
                        visitFrequency++;
                    }
                }
            }
        } else if (c.equals("male") || c.equals("female")) {
            for (Student s : a) {
                ArrayList<Visit> visitTemp = s.getVisits();
                for (Visit v : visitTemp) {
                    if (v.getTime().after(f) && v.getTime().before(t) && s.getGender().equals(c)) {
                        visitFrequency++;
                    }
                }
            }
        } else {
            for (Student s : a) {
                ArrayList<Visit> visitTemp = s.getVisits();
                for (Visit v : visitTemp) {
                    if (v.getTime().after(f) && v.getTime().before(t) && s.getProgram().equals(c)) {
                        visitFrequency++;
                    }
                }
            }
        }
        Object[][] getResult = new Object[1][1];
        String[] title = {"Visit Frequency"};

        getResult[0][0] = visitFrequency;

        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(getResult, title);
        return model;
    }

    /**
     * model to show a student's visit list
     *
     * @param v visit list of a student
     * @return model
     */
    public static DefaultTableModel visitModel(ArrayList<Visit> v) {

        Object[][] getResult = new Object[v.size()][3];
        String[] title = {"Time", "Reason", "Category"};
        for (int i = 0; i < v.size(); i++) {
            getResult[i][0] = v.get(i).getTime();
            getResult[i][1] = v.get(i).getReason();
            getResult[i][2] = v.get(i).getCategory();

        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(getResult, title);
        return model;
    }

    public static TableModel announceModel(ArrayList<Announcement> a) {
        Object[][] getResult = new Object[a.size()][3];
        String[] title = {"Time", "Content", "Announcer"};
        for (int i = 0; i < a.size(); i++) {
            getResult[i][0] = a.get(i).getTime();
            getResult[i][1] = a.get(i).getContent();
            getResult[i][2] = a.get(i).getAnnouncer();

        }
        DefaultTableModel model = new DefaultTableModel();
        model.setDataVector(getResult, title);
        return model;
    }
}
