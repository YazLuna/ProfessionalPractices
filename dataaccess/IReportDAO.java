package dataaccess;

import domain.Report;
import java.util.List;

public interface IReportDAO {
    public int addReport(Report report);
    public int deleteReport(String name);
    public int visualizeReport(String name);
    public int updateReport(String name, Report report);
    public List<Report> allReport();
}
