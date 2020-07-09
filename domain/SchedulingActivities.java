package domain;

import java.util.List;
import dataaccess.SchedulingActivitiesDAOImpl;

/**
 * Class Scheduling Activities
 * @author MARTHA
 * @version 08/05/2020
 */
public class SchedulingActivities {
        private String activity;
        private String month;
        private int idSchedulingActivities;

        public void setActivity (String activity) {
            this.activity = activity;
        }

        public String getActivity () {
            return  activity;
        }

        public void setMonth (String month) {
            this.month = month;
        }

        public  String getMonth () {
            return month;
        }

        public void setIdSchedulingActivities (int idSchedulingActivities) {
            this.idSchedulingActivities = idSchedulingActivities;
        }

        public int getIdSchedulingActivities () {
            return idSchedulingActivities;
        }

        /**
         * Method for modify the SchedulingActivities
         * @param schedulingActivitiesEdit define the data of the SchedulingActivities
         * @param datesUpdate the fields to modify
         * @return if the SchedulingActivities was modify
         */
        public static boolean modifySchedulingActivities (SchedulingActivities schedulingActivitiesEdit, List<String> datesUpdate){
            boolean isModifySchedulingActivities;
            SchedulingActivitiesDAOImpl schedulingActivitiesDAO = new SchedulingActivitiesDAOImpl();
            isModifySchedulingActivities = schedulingActivitiesDAO.modifySchedulingActivities(schedulingActivitiesEdit, datesUpdate);
            return isModifySchedulingActivities;
        }

}
