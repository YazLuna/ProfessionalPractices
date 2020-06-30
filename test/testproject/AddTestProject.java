package test.testproject;

import dataaccess.SchedulingActivitiesDAOImpl;
import domain.SchedulingActivities;
import org.junit.Assert;
import org.junit.Test;
import domain.Project;
import domain.LinkedOrganization;
import domain.ResponsibleProject;

import java.util.ArrayList;
import java.util.List;


public class AddTestProject {
    @Test
    public void testRegisterProject () {
        boolean result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Requerimientos RTU");
        project.setDescription("Diseña el diagrama de Casos de Uso con el fin de identificar las funcionalidades");
        project.setObjectiveGeneral("Instalacion del SGBD Oracle para poder empezar el desarrollo y pruebas");
        project.setObjectiveInmediate("Creacion de una tabla y algunos procedimientos con el fin de probar que la instalacion");
        project.setObjectiveMediate("Refinar requerimientos");
        project.setMethodology("Casos de Uso");
        project.setResources("Modelo Conceptual de la Base de Datos");
        project.setLapse("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(4);
        project.setActivitiesAndFunctions("Documentación de las decisiones de disenio que se han tomado");
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
        project.setOrganization(organization);
        responsible.setName("Jahir");
        responsible.setLastName("Betanzos");
        responsible.setEmail("jar_12@outlook.com");
        responsible.setCharge("Gerente");
        project.setResponsible(responsible);
        result = project.registerProject();
        Assert.assertEquals(true,result);
    }

    @Test
    public void testRegisterRepeatedProject () {
        boolean result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Requerimientos de practicas profesionales");
        project.setDescription("Desarrollar un Sistema Web que gestione los procesos académicos que realizan las " +
                "diferentes áreas dentro de la Universidad Veracruzana dentro de un mismo portal.");
        project.setObjectiveGeneral("Optimizar los procesos de consulta y seguimiento de los académicos.");
        project.setObjectiveInmediate("Revisión, análisis y documentación de requerimientos académicos con las áreas\n" +
                "involucradas durante este desarrollo.\n" +
                "Revisión y análisis de la arquitectura.\n" +
                "Desarrollo de prototipo.");

        project.setObjectiveMediate("Modificación de documentación y " +
                "Modificación de prototipos.");
        project.setMethodology("Proceso de desarrollo iterativo y Design Sprint, SCRUM");
        project.setResources("1 Ingeniero de software/programador Web " +
                "Recursos materiales:\n" +
                "Computadoras de escritorio\n" +
                "IDE para programación (Visual Studio con C#)\n" +
                "Acceso a Internet\n" +
                "Documentación de procesos");
        project.setActivitiesAndFunctions("Realizar a cabo la documentación del desarrollo del proyecto de la primera fase del " +
                "proyecto, mediante el modelado de casos de uso, la descripción de los mismos y " +
                "modelo de dominio, desarrollar sobre lenguaje C# y servicios dentro de un API, " +
                "además de trabajar en equipo dentro del departamento.");
        project.setResponsabilities("Cumplir con las funciones y actividades que sean asignadas\n" +
                "Cumplir en tiempo y forma con las entregas de prototipos y productos\n" +
                "Desarrollar en un ambiente colaborativo\n" +
                "Trabajar de acuerdo a los estándares establecidos");
        project.setDaysHours("A acordar con el estudiante (en horario de oficina)");
        project.setDuration(200);
        project.setQuantityPractitioner(3);
        project.setPlacesAvailable(3);
        project.setLapse("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(66);
        organization.setName("Dirección de Desarrollo Informático de Apoyo Académicos");
        organization.setEmail("acolunga@uv.mx");
        organization.setPhoneNumber("2281848962");
        project.setOrganization(organization);
        responsible.setEmail("martha_15_7@outlook.com");
        project.setResponsible(responsible);
        List<SchedulingActivities> listSchedulingActivities = new ArrayList<>();
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        schedulingActivities.setActivity("Análisis de requerimientos, recopilación de información, documentación y revisión " +
                "de procedimientos. Capacitación en el IDE de desarrollo y metodología.");
        schedulingActivities.setMonth("Agosto");
        listSchedulingActivities.add(schedulingActivities);

        SchedulingActivities schedulingActivities1 = new SchedulingActivities();
        schedulingActivities1.setActivity("Programación de un módulo asignado, documentación cambios y de nuevos " +
                "requerimientos.");
        schedulingActivities1.setMonth("Septiembre");
        listSchedulingActivities.add(schedulingActivities1);

        SchedulingActivities schedulingActivities2 = new SchedulingActivities();
        schedulingActivities2.setActivity("Pruebas y ajustes");
        schedulingActivities2.setMonth("Octubre");
        listSchedulingActivities.add(schedulingActivities2);
        project.setSchedulingActivitiesProject(listSchedulingActivities);
        result = project.registerProject();
        Assert.assertEquals(true,result);
    }

    @Test
    public void testAddSchedulingActivities(){
        List<SchedulingActivities> listSchedulingActivities = new ArrayList<>();
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        schedulingActivities.setActivity("Análisis de requerimientos, recopilación de información, documentación y revisión\n" +
                "de procedimientos. Capacitación en el IDE de desarrollo y metodología.");
        schedulingActivities.setMonth("Agosto");
        listSchedulingActivities.add(schedulingActivities);

        SchedulingActivities schedulingActivities1 = new SchedulingActivities();
        schedulingActivities1.setActivity("Programación de un módulo asignado, documentación cambios y de nuevos\n" +
                "requerimientos.");
        schedulingActivities1.setMonth("Septiembre");
        listSchedulingActivities.add(schedulingActivities1);

        SchedulingActivities schedulingActivities2 = new SchedulingActivities();
        schedulingActivities2.setActivity("Pruebas y ajustes");
        schedulingActivities2.setMonth("Octubre");
        listSchedulingActivities.add(schedulingActivities2);
        SchedulingActivitiesDAOImpl schedulingActivitiesDAO = new SchedulingActivitiesDAOImpl();
        schedulingActivitiesDAO.addSchedulingActivities(34, schedulingActivities);
        schedulingActivitiesDAO.addSchedulingActivities(34, schedulingActivities1);
        schedulingActivitiesDAO.addSchedulingActivities(34, schedulingActivities2);
    }

    @Test
    public void testRegisterProjectError () {
        boolean result;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Requerimientos de practicas profesionales");
        project.setDescription("Desarrollar un Sistema Web que gestione los procesos académicos que realizan las\n" +
                "diferentes áreas dentro de la Universidad Veracruzana dentro de un mismo portal.");
        project.setObjectiveGeneral("Optimizar los procesos de consulta y seguimiento de los académicos.");
        project.setObjectiveInmediate("Revisión, análisis y documentación de requerimientos académicos con las áreas\n" +
                "involucradas durante este desarrollo.\n" +
                "Revisión y análisis de la arquitectura.\n" +
                "Desarrollo de prototipo.");

        project.setObjectiveMediate("Modificación de documentación y\n" +
                "Modificación de prototipos.");
        project.setMethodology("Proceso de desarrollo iterativo y Design Sprint, SCRUM");
        project.setResources("1 Ingeniero de software/programador Web\n" +
                "Recursos materiales:\n" +
                "Computadoras de escritorio\n" +
                "IDE para programación (Visual Studio con C#)\n" +
                "Acceso a Internet\n" +
                "Documentación de procesos");
        project.setLapse("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(2);
        project.setActivitiesAndFunctions("Realizar a cabo la documentación del desarrollo del proyecto de la primera fase del\n" +
                "proyecto, mediante el modelado de casos de uso, la descripción de los mismos y\n" +
                "modelo de dominio, desarrollar sobre lenguaje C# y servicios dentro de un API,\n" +
                "además de trabajar en equipo dentro del departamento.");
        project.setResponsabilities("Cumplir con las funciones y actividades que sean asignadas\n" +
                "Cumplir en tiempo y forma con las entregas de prototipos y productos\n" +
                "Desarrollar en un ambiente colaborativo\n" +
                "Trabajar de acuerdo a los estándares establecidos");
        project.setDuration(200);
        project.setQuantityPractitioner(3);
        organization.setName("Dirección de Desarrollo Informático de Apoyo Académicos");
        organization.setEmail("acolunga@uv.mx");
        organization.setPhoneNumber("2281848962");
        organization.setAddress("Circuito");
        organization.setState("Veracruz");
        organization.setSector("Educativo");
        organization.setCity("Xalapa");
        project.setOrganization(organization);
        responsible.setName("Gustavo Antonio");
        responsible.setLastName("Ruiz Zapata");
        responsible.setEmail("guruiz@uv.mx");
        responsible.setCharge("Jefe de departamento de Tecnología Educativa");
        project.setResponsible(responsible);
        result = project.registerProject();
        Assert.assertEquals(true,result);
    }

    @Test
    public void testRequestProjects () {
        boolean result;
        Project project = new Project();
        project.setIdProject(8);
        result = project.requestProject("zS18012149");
        Assert.assertEquals(true,result);
    }

    @Test
    public void testAssignProject () {
        Project project = new Project();
        project.setIdProject(8);
        boolean result = project.assingProject("zS18012149");
        Assert.assertEquals(true,result);
    }
}
