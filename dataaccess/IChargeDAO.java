package dataaccess;

import java.util.List;

/**
 * Interface of Charge
 * @author MARTHA
 * @version 08/05/2020
 */
public interface IChargeDAO {
    public boolean addCharge (String name);
    public int getIdCharge(String name);
    public List<String> getAllCharge();
}
