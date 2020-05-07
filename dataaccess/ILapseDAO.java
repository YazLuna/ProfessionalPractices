package dataaccess;

import java.util.List;

public interface ILapseDAO {
    public void updateLapse (String lapse);
    public int searchLapse (String lapse);
    public List<String> getAllLapse ();
}
