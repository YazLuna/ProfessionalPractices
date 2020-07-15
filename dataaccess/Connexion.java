package dataaccess;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import exception.Exception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Properties;

/**
 * Connexion
 * @author Yazmin
 * @version 08/05/2020
 */

public class Connexion {
    private Connection connexion;
    private String dataBase;
    private String user;
    private String password;
    
    public Connection getConnection() throws SQLException{
        connect();
        return connexion;
    }
    
    private void connect() throws SQLException{
        Properties properties = new Properties();
        try {
            properties.load( Connexion.class.getClassLoader().getResourceAsStream("dataaccess/propertiesDataAccess") );
            dataBase = properties.getProperty("dataBase");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        }catch(IOException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            new Exception().log(ex);
        }
        try {
            connexion=DriverManager.getConnection (dataBase, user, password);
        } catch (CommunicationsException ex){
            new Exception().log(ex);
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection(){
        if(connexion!=null){
            try {
                if(!connexion.isClosed()){
                    connexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
