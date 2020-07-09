package dataaccess;

import java.util.List;

/**
 * Interface of Lapse
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ITermDAO {
    void addTerm(String lapse);
    int searchTerm (String lapse);
    List<String> getAllTerm ();
}
