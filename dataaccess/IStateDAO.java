package dataaccess;

import java.util.List;

/**
 * Interface of State
 * @author MARTHA
 * @version 08/05/2020
 */
public interface IStateDAO {
    public boolean addState(String name);
    public int getIdState(String name);
    public List<String> getAllState();
}
