package dataaccess;

import domain.Project;
import domain.SchedulingActivities;

import java.util.List;

/**
 * Interface of SchedulingActivities
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ISchedulingActivitiesDAO {
    public boolean addSchedulingActivities (int idProject, SchedulingActivities schedulingActivitiesProject);
    public List<SchedulingActivities> getAllSchedulingActivities (int idProject);
}
