package dataaccess;

import domain.ResponsibleProject;

import java.util.List;

/**
 * Interface of Responsible Project
 * @author MARTHA
 * @version 008/05/2020
 */
public interface IResponsibleProjectDAO {
    public ResponsibleProject getResponsibleProject (int idResponsible);
    public String addResponsibleProject (ResponsibleProject responsible);
    public String modifyResponsibleProject (ResponsibleProject responsible);
    public int searchIdResponsibleProject(String email);
    public List<String>  getAllResponsible();
}
