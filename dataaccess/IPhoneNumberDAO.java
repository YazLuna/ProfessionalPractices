package dataaccess;

import domain.PhoneNumber;

import java.util.List;

public interface IPhoneNumberDAO {
    public boolean addPhoneNumber(PhoneNumber phoneNumber, int idLinkedOrganization);
    public List<PhoneNumber> getAllPhoneNumber(int idLinkedOrganization);
    public boolean modifyPhoneNumber (PhoneNumber phoneNumber, List<String> datesUpdate);
    public int validateRepeatPhoneNumber (String phoneNumber);
    public int validateRepeatPhoneNumberExist (String phoneNumber,int idPhoneNumberOrigin);
}
