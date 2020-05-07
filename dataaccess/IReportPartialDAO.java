package dataaccess;

import domain.ReportPartial;
import java.sql.SQLException;
import java.util.List;

public interface IReportPartialDAO {
    public void addReportPartial(ReportPartial reportPartial);
    public void deleteReportPartial(int numberReport);
    public void visualizeReportPartial(int numberReport);
    public List<ReportPartial> allReportPartial();
}
