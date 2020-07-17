package dataaccess;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import domain.Number;
import domain.SchedulingActivities;
import exception.Exception;
import exception.TelegramBot;

/**
 * Implementation of the ISchedulingActivitiesDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class SchedulingActivitiesDAOImpl implements ISchedulingActivitiesDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the SchedulingActivitiesDAOImpl class
     */
    public SchedulingActivitiesDAOImpl (){
        connexion= new Connexion();
    }

    @Override
    public boolean addSchedulingActivities(int idProject, SchedulingActivities schedulingActivitiesProject) {
        boolean result = false;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceAddScheduling = connection.prepareStatement("INSERT INTO SchedulingActivities(activity" +
                    ",month, idProject) VALUES(?,?,?)");
            sentenceAddScheduling.setString(1, schedulingActivitiesProject.getActivity());
            sentenceAddScheduling.setString(2, schedulingActivitiesProject.getMonth());
            sentenceAddScheduling.setInt(3, idProject);
            sentenceAddScheduling.executeUpdate();
            result = true;
        }catch(SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            if (connexion != null) {
                connexion.closeConnection();
            }
            return result;
        }
    }

    @Override
    public List<SchedulingActivities> getAllSchedulingActivities(int idProject) {
        List<SchedulingActivities>  listSchedulingActivities = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            String querySchedulingActivities = "SELECT * FROM SchedulingActivities WHERE idProject=?";
            PreparedStatement sentence = connection.prepareStatement(querySchedulingActivities);
            sentence.setInt(1,idProject);
            results = sentence.executeQuery();
            while(results.next()){
                SchedulingActivities schedulingActivities = new SchedulingActivities();
                schedulingActivities.setIdSchedulingActivities(results.getInt("idSchedulingActivities"));
                schedulingActivities.setActivity(results.getString("activity"));
                schedulingActivities.setMonth(results.getString("month"));
                listSchedulingActivities.add(schedulingActivities);
            }
        }catch (SQLException exception){
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally{
            connexion.closeConnection();
        }
        return listSchedulingActivities;
    }

    /**
     * Method to modify the SchedulingProject
     * @param schedulingActivitiesEdit The data of the SchedulingProject
     * @param datesUpdate The fields to be modified
     * @return if the SchedulingProject was modify
     */
    @Override
    public boolean modifySchedulingActivities(SchedulingActivities schedulingActivitiesEdit, List<String> datesUpdate){
        boolean isModifySchedulingActivities = false;
        String sentenceDatesUpdate="";
        List<String> Change = new ArrayList<>();
        for (int indexDatesUpdate = Number.ZERO.getNumber(); indexDatesUpdate < datesUpdate.size(); indexDatesUpdate++) {
            if (indexDatesUpdate == datesUpdate.size() - 1) {
                sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?";
            } else {
                sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?,";
            }
            Change.add("get" + datesUpdate.get(indexDatesUpdate));
        }
        String sentence = "UPDATE SchedulingActivities SET "+sentenceDatesUpdate+ " WHERE idSchedulingActivities = " +
                 schedulingActivitiesEdit.getIdSchedulingActivities();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classSchedulingActivities = schedulingActivitiesEdit.getClass();
            for(int indexPreparedStatement = Number.ONE.getNumber() ; indexPreparedStatement
                    <= datesUpdate.size(); indexPreparedStatement++){
                Method methodSchedulingActivities;
                methodSchedulingActivities = classSchedulingActivities.getMethod(Change.get(indexPreparedStatement - 1));
                String word = (String) methodSchedulingActivities.invoke(schedulingActivitiesEdit, new Object[]{});
                preparedStatement.setString(indexPreparedStatement, word);
            }
            preparedStatement.executeUpdate();
            isModifySchedulingActivities = true;
        } catch (SQLException | ReflectiveOperationException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        } finally {
            connexion.closeConnection();
        }
        return isModifySchedulingActivities;
    }
}
