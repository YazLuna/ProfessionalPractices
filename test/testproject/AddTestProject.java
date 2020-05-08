package test.testproject;

import org.junit.Assert;
import org.junit.Test;
import domain.Project;
import domain.LinkedOrganization;
import domain.ResponsibleProject;


public class AddTestProject {
    @Test
    public void testRegisterProject () {
        String result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Requerimientos de practicas profesionales");
        project.setDescription("Disenia el diagrama de Casos de Uso con el fin de identificar las funcionalidades");
        project.setObjectiveGeneral("Instalación del SGBD Oracle para poder empezar el desarrollo y pruebas");
        project.setObjectiveInmediate("Creación de una tabla y algunos procedimientos con el fin de probar que la instalacion");
        project.setObjectiveMediate("Refinar requerimientos");
        project.setMethodology("Casos de Uso");
        project.setResources("Modelo Conceptual de la Base de Datos");
        project.setLapse("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(2);
        project.setActivities("Documentación de las decisiones de disenio que se han tomado");
        project.setResponsabilities("Diagrama Entidad-Relación para describir el modelo logico de la Base de Datos");
        project.setDuration(3);
        project.setQuantityPractitioner(3);
        organization.setName("BANX");
        organization.setEmail("banx@gmail.com");
        organization.setPhoneNumber("2281848962");
        organization.setAddress("Calle Norte Quince, LT. 251 MZ.");
        organization.setState("Mexico");
        organization.setSector("Educativo");
        organization.setCity("Mexico");
        organization.setDirectUsers(20);
        organization.setIndirectUsers(10);
        project.setOrganization(organization);
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        project.setResponsible(responsible);
        result = project.registerProject();
        Assert.assertEquals("The project was successfully registered",result);
    }

    @Test
    public void testRegisterRepeatedProject () {
        String result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Requerimientos de practicas profesionales");
        project.setDescription("Disenia el diagrama de Casos de Uso con el fin de identificar las funcionalidades");
        project.setObjectiveGeneral("Instalación del SGBD Oracle para poder empezar el desarrollo y pruebas");
        project.setObjectiveInmediate("Creación de una tabla y algunos procedimientos con el fin de probar que la instalacion");
        project.setObjectiveMediate("Refinar requerimientos");
        project.setMethodology("Casos de Uso");
        project.setResources("Modelo Conceptual de la Base de Datos");
        project.setLapse("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(2);
        project.setActivities("Documentación de las decisiones de disenio que se han tomado");
        project.setResponsabilities("Diagrama Entidad-Relación para describir el modelo logico de la Base de Datos");
        project.setDuration(3);
        project.setQuantityPractitioner(3);
        organization.setName("BANX");
        organization.setEmail("banx@gmail.com");
        organization.setPhoneNumber("2281848962");
        organization.setAddress("Calle Norte Quince, LT. 251 MZ.");
        organization.setState("Mexico");
        organization.setSector("Educativo");
        organization.setCity("Mexico");
        organization.setDirectUsers(20);
        organization.setIndirectUsers(10);
        project.setOrganization(organization);
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        project.setResponsible(responsible);
        result = project.registerProject();
        Assert.assertEquals("There is a project with the same registered name",result);
    }

    @Test
    public void testRegisterProjectError () {
        String result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
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
        project.setLapse("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(2);
        organization.setName("BANX");
        organization.setEmail("banx@gmail.com");
        organization.setPhoneNumber("2281848962");
        organization.setAddress("Calle Norte Quince, LT. 251 MZ.");
        organization.setState("Mexico");
        organization.setSector("Educativo");
        organization.setCity("Mexico");
        organization.setDirectUsers(20);
        organization.setIndirectUsers(10);
        project.setOrganization(organization);
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        project.setResponsible(responsible);
        result = project.registerProject();
        Assert.assertEquals("The project was successfully registered",result);
    }

    @Test
    public void testRequestProjects () {
        String result;
        Project project = new Project();
        project.setIdProject(8);
        result = project.requestProject("zS18012149");
        Assert.assertEquals("The project is request correctly",result);
    }

    @Test
    public void testAssignProject () {
        Project project = new Project();
        project.setIdProject(8);
        String result = project.assingProject("zS18012149");
        Assert.assertEquals("The practitioner was assigned correctly",result);
    }
}
