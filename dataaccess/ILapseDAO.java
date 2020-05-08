package dataaccess;

import java.util.List;

/**
 * Interface of Lapse
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ILapseDAO {
    public void updateLapse (String lapse);
    public int searchLapse (String lapse);
    public List<String> getAllLapse ();
}
