package dataaccess;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import exception.Exception;
import exception.TelegramBot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.util.Properties;

/**
 * Connexion
 * @author Yazmin
 * @version 17/07/2020
 */

public class Connexion {
	private Connection connexion;
	private String dataBase;
	private String user;
	private String password;

	/**
	 * Method to open the connection
	 * @return Connection
	 */
	public Connection getConnection() throws SQLException {
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
		}catch(IOException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		}
		try {
			connexion=DriverManager.getConnection (dataBase, user, password);
		} catch (CommunicationsException exception){
			new Exception().log(exception);
		}
	}

	/**
	 * Method to close connection
	 */
	public void closeConnection(){
		if(connexion!=null){
			try {
				if(!connexion.isClosed()){
					connexion.close();
				}
			} catch (SQLException exception) {
				new Exception().log(exception);
				TelegramBot.sendToTelegram(exception.getMessage());
			}
		}
	}

}
