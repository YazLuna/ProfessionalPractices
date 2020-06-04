package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.Search;

public class StatusDAOImpl implements IStatusDAO  {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public StatusDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public void addStatus(String status) {
        String queryAddUserStatus = "INSERT INTO Status (status)  VALUES (?)";
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceAddUserStatus = connection.prepareStatement(queryAddUserStatus);
            sentenceAddUserStatus.setString(1, status);
            sentenceAddUserStatus.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            connexion.closeConnection();
        }
    }

    @Override
    public int searchIdStatus(String status) {
        int idUserStatus = Search.NOTFOUND.getValue();
        String queryUserStatus = "Select idStatus from Status where status =?";
        try {
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement(queryUserStatus);
            sentence.setString(1, status);
            result = sentence.executeQuery();
            while (result.next()) {
                idUserStatus = result.getInt("idStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMethodDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return idUserStatus;
    }
}
