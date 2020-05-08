package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Properties;

/**
 * DAO User
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
        Properties prop = new Properties();
        try {
            prop.load( Connexion.class.getClassLoader().getResourceAsStream("dataaccess/propertiesDataAccess") );
            dataBase = prop.getProperty("dataBase");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        }catch(IOException ex)
        {
            System.err.println("Error accessing properties file");
            ex.printStackTrace();

        }
        connexion=DriverManager.getConnection (dataBase,user,password);
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
