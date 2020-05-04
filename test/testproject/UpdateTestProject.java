package test.testproject;

import domain.LinkedOrganization;
import domain.Project;
import domain.ResponsibleProject;
import org.junit.Assert;
import org.junit.Test;

public class UpdateTestProject {
    @Test
    public void testActualizationProject(){
        int result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setIdProject(3);
        project.setNameProject("Gestion del sistema YGT");
        project.setDescription("Disenia el diagrama de Casos de Uso con el fin de identificar las funcionalidades");
        project.setObjectiveGeneral("Instalación del SGBD Oracle para poder empezar el desarrollo y pruebas");
        project.setObjectiveInmediate("Creación de una tabla y algunos procedimientos con el fin de probar que la instalacion");
        project.setObjectiveMediate("Refinar requerimientos");
        project.setMethodology("Casos de Uso");
        project.setResources("Modelo Conceptual de la Base de Datos");
        project.setActivities("Documentación de las decisiones de disenio que se han tomado");
        project.setResponsabilities("Diagrama Entidad-Relación para describir el modelo logico de la Base de Datos");
        project.setDuration(3);
        project.setQuantityPractitioner(3);
        organization.setName("BANX");
        organization.setEmail("banx@gmail.com");
        organization.setPhoneNumber("2281848962");
        organization.setAdress("Calle Norte Quince, LT. 251 MZ.");
        organization.setState("Mexico");
        organization.setSector("Educativo");
        organization.setCity("Mexico");
        organization.setDirectUsers(30);
        organization.setIndirectUsers(10);
        project.setOrganization(organization);
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        project.setResponsible(responsible);
        result = project.actualizationProject();
        Assert.assertEquals(1,result);
    }

    @Test
    public void testActualizationProjectError(){
        int result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setIdProject(3);
        project.setDescription("Disenia el diagrama de Casos de Uso con el fin de identificar las funcionalidades");
        project.setObjectiveGeneral("Instalación del SGBD Oracle para poder empezar el desarrollo y pruebas");
        project.setObjectiveInmediate("Creación de una tabla y algunos procedimientos con el fin de probar que la instalacion");
        project.setObjectiveMediate("Refinar requerimientos");
        project.setMethodology("Casos de Uso");
        project.setResources("Modelo Conceptual de la Base de Datos");
        project.setActivities("Documentación de las decisiones de disenio que se han tomado");
        project.setResponsabilities("Diagrama Entidad-Relación para describir el modelo logico de la Base de Datos");
        project.setDuration(3);
        project.setQuantityPractitioner(3);
        organization.setName("BANX");
        organization.setEmail("banx@gmail.com");
        organization.setPhoneNumber("2281848962");
        organization.setAdress("Calle Norte Quince, LT. 251 MZ.");
        organization.setState("Mexico");
        organization.setSector("Educativo");
        organization.setCity("Mexico");
        organization.setDirectUsers(30);
        organization.setIndirectUsers(10);
        project.setOrganization(organization);
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        project.setResponsible(responsible);
        result = project.actualizationProject();
        Assert.assertEquals(1,result);
    }
}
