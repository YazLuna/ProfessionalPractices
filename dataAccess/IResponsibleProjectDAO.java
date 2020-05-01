package dataAccess;

import domain.ResponsibleProject;

public interface IResponsibleProjectDAO {
    public ResponsibleProject getResponsibleProject (int idResponsible);
    public void updateResponsibleProject (ResponsibleProject responsible);
    public void actualizationResponsibleProject (ResponsibleProject responsible);
    public int searchResponsibleProject (String email);
    public void updateCharge (String name);
    public int searchCharge (String name);
}
