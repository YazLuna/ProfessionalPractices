package test.testproject;

import dataaccess.*;
import domain.*;
import exception.Exception;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import telegram.TelegramBot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AddTestProject {
    @After
    public void deleteProject () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM Project where nameProject =?");
            sentenceProject.setString(1, "Sistema Integral Académico");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testRegisterProject () {
        boolean resultRegisterProject;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Sistema Integral Académico");
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
        project.setQuiantityPractitioner(3);
        project.setPlacesAvailable(3);
        project.setTerm("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(11265432);
        organization.setName("Dirección de Desarrollo Informático de Apoyo Académico");
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
        resultRegisterProject = project.registerProject(project);
        Assert.assertTrue(resultRegisterProject);
    }

    @Test
    public void testErrorRegisterProject () {
        boolean resultErrorRegisterProject;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
        project.setNameProject("Sistema Integral Académico");
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
        project.setQuiantityPractitioner(3);
        project.setPlacesAvailable(3);
        project.setTerm("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(1);
        organization.setName("Dirección de Desarrollo Informático de Apoyo Académico");
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
        resultErrorRegisterProject = project.registerProject(project);
        Assert.assertTrue(resultErrorRegisterProject);
    }

    @Test
    public void testErrorNameRegisterProject () {
        boolean resultRegisterProject;
        Project project = new Project();
        LinkedOrganization organization = new LinkedOrganization();
        ResponsibleProject responsible = new ResponsibleProject();
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
        project.setQuiantityPractitioner(3);
        project.setPlacesAvailable(3);
        project.setTerm("FEBRERO-JULIO 2020");
        project.setStaffNumberCoordinator(0);
        organization.setName("Dirección de Desarrollo Informático de Apoyo Académico");
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
        resultRegisterProject = project.registerProject(project);
        Assert.assertTrue(resultRegisterProject);
    }


    @After
    public void deleteSchedulingActivities () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM SchedulingActivities WHERE idProject=? AND month=?");
            sentenceProject.setInt(1, 43);
            sentenceProject.setString(1, "Agosto");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddSchedulingActivities(){
        boolean isAddScheduligActivities;
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        schedulingActivities.setActivity("Análisis de requerimientos, recopilación de información, documentación y revisión\n" +
                "de procedimientos. Capacitación en el IDE de desarrollo y metodología.");
        schedulingActivities.setMonth("Agosto");
        SchedulingActivitiesDAOImpl schedulingActivitiesDAO = new SchedulingActivitiesDAOImpl();
        isAddScheduligActivities = schedulingActivitiesDAO.addSchedulingActivities(43, schedulingActivities);
        Assert.assertTrue(isAddScheduligActivities);
    }


    @Test
    public void testErrorAddSchedulingActivities(){
        boolean isAddScheduligActivities;
        SchedulingActivities schedulingActivities = new SchedulingActivities();
        schedulingActivities.setActivity("Análisis de requerimientos, recopilación de información, documentación y revisión\n" +
                "de procedimientos. Capacitación en el IDE de desarrollo y metodología.");
        SchedulingActivitiesDAOImpl schedulingActivitiesDAO = new SchedulingActivitiesDAOImpl();
        isAddScheduligActivities = schedulingActivitiesDAO.addSchedulingActivities(34, schedulingActivities);
        Assert.assertTrue(isAddScheduligActivities);
    }

    @After
    public void deleteTerm () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM Lapse WHERE lapse=?");
            sentenceProject.setString(1, "AGOSTO-SEPTIEMBRE 2020");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddTerm() {
        boolean isAddTerm;
        String term = "AGOSTO-SEPTIEMBRE 2020";
        TermDAOImpl termDAO = new TermDAOImpl();
        isAddTerm = termDAO.addTerm(term);
        Assert.assertTrue(isAddTerm);
    }

    @Test
    public void testErrorAddTerm() {
        boolean isAddTerm;
        String term = null;
        TermDAOImpl termDAO = new TermDAOImpl();
        isAddTerm = termDAO.addTerm(term);
        Assert.assertTrue(isAddTerm);
    }

    @After
    public void deleteLinkedOrganization () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM LinkedOrganization WHERE name=?");
            sentenceProject.setString(1, "Dirección de Desarrollo Informático de Apoyo Académico");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddLinkedOrganization() {
        boolean isAddLinkedOrganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganization.setName("Dirección de Desarrollo Informático de Apoyo Académico");
        linkedOrganization.setEmail("acolunga@uv.mx");
        linkedOrganization.setAddress("Circuito Aguirre Beltrán S/N");
        linkedOrganization.setSector("Educativo");
        linkedOrganization.setState("Veracruz");
        linkedOrganization.setCity("Xalapa");
        linkedOrganization.setIndirectUsers("Comunidad universitaria y\n" +
                "población en general");
        linkedOrganization.setDirectUsers("Comunidad Academica");
        isAddLinkedOrganization = linkedOrganization.addLinkedOrganization(linkedOrganization);
        Assert.assertTrue(isAddLinkedOrganization);
    }

    @Test
    public void testErrorAddLinkedOrganization() {
        boolean isAddLinkedOrganization;
        LinkedOrganization linkedOrganization = new LinkedOrganization();
        linkedOrganization.setName("Dirección de Desarrollo Informático de Apoyo Académico");
        linkedOrganization.setAddress("Circuito Aguirre Beltrán S/N");
        linkedOrganization.setSector("Educativo");
        linkedOrganization.setState("Veracruz");
        linkedOrganization.setCity("Xalapa");
        linkedOrganization.setIndirectUsers("Comunidad universitaria y\n" +
                "población en general");
        linkedOrganization.setDirectUsers("Comunidad Academica");
        isAddLinkedOrganization = linkedOrganization.addLinkedOrganization(linkedOrganization);
        Assert.assertTrue(isAddLinkedOrganization);
    }

    @After
    public void deleteSector () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM Sector WHERE nameSector=?");
            sentenceProject.setString(1, "Educativo");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddSector() {
        boolean isAddSector;
        String sector = "Educativo";
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddSector = linkedOrganizationDAO.addSector(sector);
        Assert.assertTrue(isAddSector);
    }

    @Test
    public void testErrorAddSector() {
        boolean isAddSector;
        String sector = null;
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddSector = linkedOrganizationDAO.addSector(sector);
        Assert.assertTrue(isAddSector);
    }

    @After
    public void deleteState () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM State WHERE nameState=?");
            sentenceProject.setString(1, "Veracruz");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddState() {
        boolean isAddState;
        String state = "Veracruz";
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddState = linkedOrganizationDAO.addState(state);
        Assert.assertTrue(isAddState);
    }

    @Test
    public void testErrorAddState() {
        boolean isAddState;
        String state = null;
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddState = linkedOrganizationDAO.addState(state);
        Assert.assertTrue(isAddState);
    }

    @After
    public void deleteCity() {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM City WHERE nameCity=?");
            sentenceProject.setString(1, "Xalapa");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddCity() {
        boolean isAddCity;
        String city = "Xalapa";
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddCity = linkedOrganizationDAO.addCity(city);
        Assert.assertTrue(isAddCity);
    }

    @Test
    public void testErrorAddCity() {
        boolean isAddCity;
        String city = null;
        LinkedOrganizationDAOImpl linkedOrganizationDAO = new LinkedOrganizationDAOImpl();
        isAddCity = linkedOrganizationDAO.addCity(city);
        Assert.assertTrue(isAddCity);
    }

    @After
    public void deletePhoneNumber () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM PhoneNumber WHERE idLinkedOrganization=?");
            sentenceProject.setInt(1, 12);
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddPhoneNumber() {
        boolean isAddPhoneNumber;
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setExtensions("12724, 12735");
        phoneNumber.setPhoneNumber("2288421700");
        isAddPhoneNumber = phoneNumber.addPhoneNumber(phoneNumber,12);
        Assert.assertTrue(isAddPhoneNumber);
    }

    @Test
    public void testErrorAddPhoneNumber() {
        boolean isAddPhoneNumber;
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setExtensions("12724, 12735");
        isAddPhoneNumber = phoneNumber.addPhoneNumber(phoneNumber,12);
        Assert.assertTrue(isAddPhoneNumber);
    }

    @After
    public void deleteResponsibleProject () {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM ResponsibleProject WHERE email=?");
            sentenceProject.setString(1,"guruiz@uv.mx");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testRegisterResponsibleProject() {
        boolean isRegisterResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProject.setName("Gustavo Antonio");
        responsibleProject.setLastName("Ruiz Zapata");
        responsibleProject.setEmail("guruiz@uv.mx");
        responsibleProject.setCharge("Jefe de departamento de Tecnología Educativa");
        isRegisterResponsibleProject = responsibleProject.registerResponsibleProject(responsibleProject);
        Assert.assertTrue(isRegisterResponsibleProject);
    }

    @Test
    public void testErrorRegisterResponsibleProject() {
        boolean isRegisterResponsibleProject;
        ResponsibleProject responsibleProject = new ResponsibleProject();
        responsibleProject.setName("Gustavo Antonio");
        responsibleProject.setEmail("guruiz@uv.mx");
        responsibleProject.setCharge("Jefe de departamento de Tecnología Educativa");
        isRegisterResponsibleProject = responsibleProject.registerResponsibleProject(responsibleProject);
        Assert.assertTrue(isRegisterResponsibleProject);
    }

    @After
    public void deleteCharge() {
        final Connexion connexion;
        connexion = new Connexion();
        try{
            Connection connection = connexion.getConnection();
            PreparedStatement sentenceProject = connection.prepareStatement("DELETE FROM Charge WHERE nameCharge=?");
            sentenceProject.setString(1, "Jefe de departamento de Tecnología Educativa");
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }
    }

    @Test
    public void testAddCharge() {
        boolean isAddCharge;
        String charge = "Jefe de departamento de Tecnología Educativa";
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        isAddCharge = responsibleProjectDAO.addCharge(charge);
        Assert.assertTrue(isAddCharge);
    }

    @Test
    public void testErrorAddCharge() {
        boolean isAddCharge;
        String charge = null;
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        isAddCharge = responsibleProjectDAO.addCharge(charge);
        Assert.assertTrue(isAddCharge);
    }
}
