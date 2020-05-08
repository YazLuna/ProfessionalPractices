package domain;

import dataaccess.ReportDAOImpl;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private  String name;
    private  String activities;
    private  int score;
    private  String completionDate;
    private  String deliverDate;

    public Report() {
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getActivities () {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public int getScore () {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCompletionDate () {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getDeliverDate () {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public int addReport() {
        int result;
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        result = reportDAO.addReport(this);
        return result;
    }

    public int deleteReport(String name) {
        int result;
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        result = reportDAO.deleteReport(name);
        return result;
    }

    public int visualizeReport(String name) {
        int result;
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        result = reportDAO.visualizeReport(name);
        return result;
    }

    public int updateReport(String name) {
        int result;
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        result = reportDAO.updateReport(name, this);
        return result;
    }

    public List<Report> allReport() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        List<Report> reports = new ArrayList<>();
        reports = reportDAO.allReport();
        return reports;
    }

}