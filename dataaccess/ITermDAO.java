package dataaccess;

import java.util.List;

/**
 * Interface of Term
 * @author MARTHA
 * @version 08/05/2020
 */
public interface ITermDAO {
    public boolean addTerm(String lapse);
    public int getIdTerm(String lapse);
}
