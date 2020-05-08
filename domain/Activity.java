package domain;

import dataaccess.ActivityDAOImpl;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private int value;
    private String description;
    private String name;
    private String deliverDate;

    public Activity () {
        
    }
    
    public int getValue () {
        return value;
    }

    public void setValue (int value) {
        this.value = value;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDeliverDate () {
        return deliverDate;
    }

    public void setDeliverDate (String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public int addActivity() {
        int result;
        ActivityDAOImpl activityDAO = new ActivityDAOImpl();
        result = activityDAO.addActivity(this);
        return result;
    }

    public int deleteActivity(String name) {
        int result;
        ActivityDAOImpl activityDAO = new ActivityDAOImpl();
        result = activityDAO.deleteActivity(name);
        return result;
    }

    public int visualizeActivity(String name) {
        int result;
        ActivityDAOImpl activityDAO = new ActivityDAOImpl();
        result = activityDAO.visualizeActivity(name);
        return result;
    }

    public int updateActivity(String name) {
        int result;
        ActivityDAOImpl activityDAO = new ActivityDAOImpl();
        result = activityDAO.updateActivity(name, this);
        return result;
    }

    public List<Activity> allActivity() {
        ActivityDAOImpl activityDAO = new ActivityDAOImpl();
        List<Activity> activities = new ArrayList<>();
        activities = activityDAO.allActivity();
        return activities;
    }
}