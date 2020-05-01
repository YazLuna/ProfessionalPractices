package domain;

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

}