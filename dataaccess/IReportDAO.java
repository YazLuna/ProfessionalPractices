package dataaccess;

import domain.Report;
import java.sql.SQLException;
import java.util.List;

public interface IReportDAO {
    public int addReport(Report report);
    public void deleteReport(String name);
    public void visualizeReport(String name);
    public void updateReport(String name, int score);
    public List<Report> allReport();
}
