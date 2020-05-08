package dataaccess;

import java.util.List;
import domain.Activity;

/**
 * Creation of the interface IActivityDAO
 * @author Ivana Correa
 * @version 08/05/2020
 */

public interface IActivityDAO {
    public int addActivity(Activity activity);
    public int deleteActivity(String name);
    public int visualizeActivity(String name);
    public int updateActivity(String name, Activity activity);
    public List <Activity> allActivity();
}
