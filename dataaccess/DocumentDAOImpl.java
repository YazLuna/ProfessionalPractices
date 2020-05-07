package dataaccess;

import domain.Document;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentDAOImpl implements IDocumentDAO{
    private final dataaccess.Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet result;

    public DocumentDAOImpl() { connexion= new Connexion(); }

    @Override
    public void addDocument(Document document) {
        try {
            connection = connexion.getConnection();
            String queryActivity = "INSERT INTO Document (name, document) VALUES (?,?)";
            PreparedStatement sentence = connection.prepareStatement(queryActivity);
            result = sentence.executeQuery();
            while (result.next()){
                sentence.setString(1, document.getName());
                sentence.setBytes(2, document.getDocument());
            }
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    @Override
    public void deleteDocument(String name) {
        try{
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("DELETE Document INNER JOIN user ON Practitioner.idUser = user.idUser WHERE name = ?");
            sentence.setString(1, name);
            sentence.executeQuery();
        }catch(SQLException ex){
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
    }

    @Override
    public void visualizeDocument(String name) {
        try {
            connection = connexion.getConnection();
            PreparedStatement sentence = connection.prepareStatement("SELECT * FROM Document WHERE name = ?");
            sentence.setString(1, name);
            sentence.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ActivityDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
    }
}
