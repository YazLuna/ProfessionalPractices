package dataaccess;

import domain.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the interface IReportDAO
 * @author Ivana Correa
 * @version  08/05/2020
 */

public class ReportDAOImpl implements IReportDAO{
    private final Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet result;

    public ReportDAOImpl() { connexion= new Connexion(); }

    @Override
    public int addReport(Report report) {
        int finalScore = 0;
        try {
            connection = connexion.getConnection();
            String queryFoundReport = "INSERT INTO report (name, activities, score, completionDate, deliverDate) VALUES (?,?,?,?,?)";
            PreparedStatement sentence = connection.prepareStatement(queryFoundReport);
            result = sentence.executeQuery();
            while (result.next()) {
                sentence.setString(1, report.getName());
                sentence.setString(2, report.getActivities());
                sentence.setInt(3, report.getScore());
                sentence.setString(4, report.getCompletionDate());
                sentence.setString(5, report.getDeliverDate());
            }
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public int deleteReport(String name) {
        int finalScore = 0;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("DELETE ReportPartial INNER JOIN user ON practitioner.idUser = user.idUser WHERE name = ?");
            sentence.setString(1, name);
            sentence.executeQuery();
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public int visualizeReport(String name) {
        int finalScore = 0;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("SELECT * FROM ReportPartial WHERE name = ?");
            sentence.setString(1, name);
            sentence.executeQuery();
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public int updateReport(String name, Report report) {
        int finalScore = 0;
        try{
            connection =connexion.getConnection();
            String queryReport = "UPDATE Report INNER JOIN user ON teacher.idUser = user.idUser SET report.score = ?, report.name = ?, report.activities = ?, report.completionDate = ?, report.deliverDate = ? WHERE name = ?";
            PreparedStatement sentence=connection.prepareStatement(queryReport);
            sentence.setInt(1, report.getScore());
            sentence.setString(2, report.getName());
            sentence.setString(3, report.getActivities());
            sentence.setString(4, report.getCompletionDate());
            sentence.setString(5, report.getDeliverDate());
            sentence.setString(6, name);
            sentence.executeQuery();
            finalScore = 1;
        }catch(SQLException ex){
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return finalScore;
    }

    @Override
    public List<Report> allReport() {
        List<Report> reports = new ArrayList<>();
        try{
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("SELECT * FROM Report WHERE enrollment.Practitioner = enrollmentPracticing.Report");
            while(result.next()){
                Report report = new Report();
                report.setName(result.getString("name"));
                report.setActivities(result.getString("activities"));
                report.setScore(result.getInt("score"));
                report.setScore(result.getInt("score"));
                report.setCompletionDate(result.getString("completion date"));
                report.setDeliverDate(result.getString("deliverDate"));
                reports.add(report);
            }
        }catch(SQLException ex){
            Logger.getLogger(ReportDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
        return reports;
    }
}
