package dataaccess;

import java.sql.SQLException;
import java.util.List;
import domain.Activity;

public interface IActivityDAO {
    public int addActivity(Activity activity);
    public int deleteActivity(String name);
    public int visualizeActivity(String name);
    public int updateActivity(String name, int value, String description, String deliverDate);
    public List <Activity> allReport();
}
