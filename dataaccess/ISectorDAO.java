package dataaccess;

import java.util.List;

/**
 * Interface of Sector
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ISectorDAO {
    public boolean addSector(String name);
    public int getIdSector(String name);
    public List<String> getAllSector();
}
