package dataaccess;

import java.util.List;

/**
 * Interface of City
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ICityDAO {
    public boolean addCity(String name);
    public int getIdCity(String name);
    public List<String> getAllCity();
}
