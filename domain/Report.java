package domain;

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
}