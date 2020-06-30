package domain;

import dataaccess.SchedulingActivitiesDAOImpl;
import java.util.List;

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

        public List<String> listMonth () {
            SchedulingActivitiesDAOImpl getAllMonth= new SchedulingActivitiesDAOImpl();
            List<String> listMonth = getAllMonth.getAllMonth();
            return listMonth;
        }
}
