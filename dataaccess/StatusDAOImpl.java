package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Search;
import exception.Exception;
import exception.TelegramBot;

/**
 * Implementation of the IStatusDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class StatusDAOImpl implements IStatusDAO  {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    /**
     * Constructor for the StatusDAOImpl class
     */
    public StatusDAOImpl() {
        connexion = new Connexion();
    }

    /**
     * Method to add a Status
     * @param status The status parameter defines the status
     */
    @Override
    public void addStatus(String status) {
        String queryAddUserStatus = "INSERT INTO Status (status)  VALUES (?)";
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceAddUserStatus = connection.prepareStatement(queryAddUserStatus);
            sentenceAddUserStatus.setString(1, status);
            sentenceAddUserStatus.executeUpdate();
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        }finally {
            connexion.closeConnection();
        }
    }

    /**
     * Method to get id of the status
     * @param status The status parameter defines the status
     */
    @Override
    public int searchIdStatus(String status) {
        int idUserStatus = Search.NOTFOUND.getValue();
        String queryUserStatus = "SELECT idStatus FROM Status WHERE status =?";
        try {
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement(queryUserStatus);
            sentence.setString(1, status);
            result = sentence.executeQuery();
            while (result.next()) {
                idUserStatus = result.getInt("idStatus");
            }
        } catch (SQLException exception) {
            new Exception().log(exception);
            TelegramBot.sendToTelegram(exception.getMessage());
        } finally {
            connexion.closeConnection();
        }
        return idUserStatus;
    }
}
