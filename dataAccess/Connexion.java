package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Properties;

public class Connexion {
    private Connection connexion;
    private String dataBase;
    private String usser;
    private String password;
    
    public Connection getConnection() throws SQLException{
        connect();
        return connexion;
    }
    
    private void connect() throws SQLException{
        Properties prop = new Properties();
        try {
            prop.load( Connexion.class.getClassLoader().getResourceAsStream("dataAccess/propertiesDataAccess") );
            dataBase = prop.getProperty("dataBase");
            usser = prop.getProperty("usser");
            password = prop.getProperty("password");
        }catch(IOException ex)
        {
            System.err.println("Error al acceder al archivo de propiedades");
            ex.printStackTrace();

        }
        connexion=DriverManager.getConnection (dataBase,usser,password);
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
