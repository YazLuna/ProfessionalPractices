package dataaccess;

import domain.ReportPartial;
import java.util.List;

/**
 * Creation of the interface IReportPartialDAO
 * @author Ivana Correa
 * @version 08/05/2020
 */

public interface IReportPartialDAO {
    public int addReportPartial(ReportPartial reportPartial);
    public int deleteReportPartial(int numberReport);
    public int visualizeReportPartial(int numberReport);
    public List<ReportPartial> allReportPartial();
}
