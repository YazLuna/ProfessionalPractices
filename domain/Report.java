package domain;

public class Report {
    protected  String activities;
    protected  int score;
    protected  String completionDate;
    protected  String deliverDate;

    public Report (String activities) {
        this.activities = activities;
    }

    public String getActivities () {
        return activities;
    }

    public void setActivities (String activities) {
        this.activities = activities;
    }

    public int getScore () {
        return score;
    }

    public void setScore (int score) {
        this.score = score;
    }	

    public String getCompletionDate () {
        return completionDate;
    }

    public void setCompletionDate (String completionDate) {
        this.completionDate = completionDate;
    }

    public String getDeliverDate () {
        return deliverDate;
    }

    public void setDeliverDate (String deliverDate) {
        this.deliverDate = deliverDate;
    }

}