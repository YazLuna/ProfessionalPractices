package dataaccess;

import domain.ReportPartial;
import java.util.List;

public interface IReportPartialDAO {
    public int addReportPartial(ReportPartial reportPartial);
    public int deleteReportPartial(int numberReport);
    public int visualizeReportPartial(int numberReport);
    public List<ReportPartial> allReportPartial();
}
