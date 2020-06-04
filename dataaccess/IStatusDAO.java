package dataaccess;

/**
 * Interface of Status
 * @author MARTHA
 * @version 04/06/2020
 */
public interface IStatusDAO {
    public void addStatus (String status);
    public int searchIdStatus (String status);
}
