package dataaccess;

import domain.ReportPartial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportPartialDAOImpl implements IReportPartialDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet result;

    public ReportPartialDAOImpl() { connexion= new Connexion(); }

    @Override
    public void addReportPartial(ReportPartial reportPartial) {
        try {
            connection = connexion.getConnection();
            String queryFoundReportPartial= "INSERT INTO reportPartial (numberReport, resultsObtained, hoursCovered, observations, objective, methodology) VALUES (?,?,?,?,?,?)";
            PreparedStatement sentence = connection.prepareStatement(queryFoundReportPartial);
            result = sentence.executeQuery();
            while (result.next()){
                sentence.setInt(1, reportPartial.getNumberReport());
                sentence.setString(2, reportPartial.getResultsObtained());
                sentence.setInt(3, reportPartial.getHoursCovered());
                sentence.setString(4, reportPartial.getObservations());
                sentence.setString(5, reportPartial.getObjective());
                sentence.setString(6, reportPartial.getMethodology());
            }
        }catch(SQLException ex){
            Logger.getLogger(ReportPartialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    @Override
    public void deleteReportPartial(int numberReport) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("DELETE ReportPartial INNER JOIN user ON practitioner.idUser = user.idUser WHERE numberReport = ?");
            sentence.setInt(1, numberReport);
            sentence.executeQuery();
        }catch(SQLException ex){
            Logger.getLogger(ReportPartialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    @Override
    public void visualizeReportPartial(int numberReport) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("SELECT * FROM ReportPartial WHERE numberReport = ?");
            sentence.setInt(1, numberReport);
            sentence.executeQuery();
        }catch(SQLException ex){
            Logger.getLogger(ReportPartialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    @Override
    public List<ReportPartial> allReportPartial() {
        List<ReportPartial> reportPartials = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("SELECT * FROM ReportPartial WHERE enrollment.Practitioner = enrollmentPracticing.ReportPartial");
            while(result.next()){
                ReportPartial reportPartial = new ReportPartial();
                reportPartial.setNumberReport(result.getInt("number report"));
                reportPartial.setResultsObtained(result.getString("results obtained"));
                reportPartial.setHoursCovered(result.getInt("hours covered"));
                reportPartial.setObservations(result.getString("observations"));
                reportPartial.setObjective(result.getString("objective"));
                reportPartial.setMethodology(result.getString("methodology"));
                reportPartials.add(reportPartial);
            }
        }catch(SQLException ex){
            Logger.getLogger(ReportPartialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return reportPartials;
    }
}
