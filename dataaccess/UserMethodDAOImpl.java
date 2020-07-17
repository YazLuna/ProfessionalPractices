package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import domain.Search;
import domain.User;
import exception.TelegramBot;
import exception.Exception;

/**
 * UserMethodDAOImpl
 * @author Yazmin
 * @version 16/07/2020
 */

public class UserMethodDAOImpl implements IUserMethodDAO{
	private final Connexion connexion;
	private Connection connection;
	private ResultSet resultSet;
	PreparedStatement preparedStatement;

	public UserMethodDAOImpl() {
		connexion = new Connexion();
	}

	/**
	 * Method to add a user
	 * @param user to Add
	 * @return true if added and false if not
	 */
	@Override
	public boolean addUser(User user) {
		boolean result = false;
		try {
			connection = connexion.getConnection();
			String queryAddUser = "INSERT INTO User  (name, lastName, gender, email,  alternateEmail" +
					", phone)  VALUES (?,?, ?, ?,?,?)";
			preparedStatement = connection.prepareStatement(queryAddUser);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setInt(3, user.getGender());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getAlternateEmail());
			preparedStatement.setString(6, user.getPhone());
			preparedStatement.executeUpdate();
			int idUserAdd = searchIdUser(user.getEmail(), user.getAlternateEmail(), user.getPhone());
			LoginAccountDAOImpl loginAccountDAO = new LoginAccountDAOImpl();
			result = loginAccountDAO.createLoginAccount(user.getUserName(),user.getPassword(),idUserAdd);
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to know if a Academic is valid
	 * @param staffNumber from Academic
	 * @param email from Academic
	 * @param alternateEmail from Academic
	 * @param phone from Academic
	 * @param userName from Academic
	 * @return if are a valid Academic to add
	 */
	@Override
	public int validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName) {
		int valid = validateUserAdd(email, alternateEmail, phone, userName);
		if(valid == Search.NOTFOUND.getValue()){
			valid = validateStaffNumberTwoAcademics(staffNumber);
		}
		return valid;
	}

	/**
	 * Method to know if a user is valid
	 * @param email from user
	 * @param alternateEmail from user
	 * @param phone from user
	 * @return if are a valid user
	 */
	@Override
	public int validateUser(String email, String alternateEmail, String phone) {
		int valid = searchEmail(email);
		if(valid == Search.NOTFOUND.getValue()){
			valid = searchAlternateEmail(alternateEmail);
			if(valid == Search.NOTFOUND.getValue()){
				valid = searchPhone(phone);
			}
		}
		return valid;
	}

	/**
	 * Method to know if a user is valid to add
	 * @param email from user
	 * @param alternateEmail from user
	 * @param phone from user
	 * @param userName from user
	 * @return if are a valid user to add
	 */
	@Override
	public int validateUserAdd(String email, String alternateEmail, String phone, String userName) {
		int valid = validateUser (email, alternateEmail, phone);
		if(valid == Search.NOTFOUND.getValue()){
			LoginAccountDAOImpl loginAccountDAO = new LoginAccountDAOImpl();
			valid = loginAccountDAO.searchUserName(userName);
		}
		return valid;
	}

	/**
	 * Method to know if a Academic is valid to update
	 * @param staffNumber from Academic
	 * @param email from Academic
	 * @param alternateEmail from Academic
	 * @param phone from Academic
	 * @return if are a valid Academic to update
	 */
	@Override
	public int validateAcademicUpdate(int staffNumber, String email, String alternateEmail, String phone) {
		int valid = validateUser (email, alternateEmail, phone);
		System.out.println(valid);
		if(valid == Search.NOTFOUND.getValue()){
			valid = validateStaffNumberTwoAcademic(staffNumber);
			System.out.println(valid);
		}
		return valid;
	}

	protected int validateStaffNumberTwoAcademic(int staffNumberSearch) {
		int resultValidate = validateStaffNumberCoordinator(staffNumberSearch);
		if(resultValidate == Search.NOTFOUND.getValue()) {
			resultValidate = validateStaffNumberTeacher(staffNumberSearch);
		}
		return resultValidate;
	}

	protected int searchStaffNumberCoordinator(int staffNumberSearch) {
		int staffNumber = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchStaffNumber = "Select staffNumber from Coordinator where staffNumber=?";
			preparedStatement = connection.prepareStatement(querySearchStaffNumber);
			preparedStatement.setInt(1, staffNumberSearch);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				staffNumber = resultSet.getInt("staffNumber");
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		}finally {
			connexion.closeConnection();
		}
		return staffNumber;
	}

	protected int searchStaffNumberTeacher(int staffNumberSearch)  {
		int staffNumber = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchStaffNumber =
					"Select staffNumber from Teacher where staffNumber=?";
			preparedStatement = connection.prepareStatement(querySearchStaffNumber);
			preparedStatement.setInt(1, staffNumberSearch);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				staffNumber = resultSet.getInt("staffNumber");
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		}finally {
			connexion.closeConnection();
		}
		return staffNumber;
	}

	protected boolean searchUserAcademic(String name,String lastName,String email,String alternateEmail,String phone,int gender) {
		boolean search = false;
		try {
			connection = connexion.getConnection();
			String querySearchUser = "SELECT idUser FROM User WHERE name =? AND lastName =? AND email =? AND alternateEmail =? AND phone =? AND gender =?";
			preparedStatement = connection.prepareStatement(querySearchUser);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, alternateEmail);
			preparedStatement.setString(5, phone);
			preparedStatement.setInt(6, gender);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				search = true;
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return search;
	}

	protected int validateStaffNumberTeacher(int staffNumberSearch)  {
		int valid = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchStaffNumber =
					"Select staffNumber from Teacher where staffNumber=?";
			preparedStatement = connection.prepareStatement(querySearchStaffNumber);
			preparedStatement.setInt(1, staffNumberSearch);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				valid = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			valid = Search.EXCEPTION.getValue();
		}finally {
			connexion.closeConnection();
		}
		return valid;
	}

	protected int validateStaffNumberCoordinator(int staffNumberSearch)  {
		int valid = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchStaffNumber =
					"Select staffNumber from Coordinator where staffNumber=?";
			preparedStatement = connection.prepareStatement(querySearchStaffNumber);
			preparedStatement.setInt(1, staffNumberSearch);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				valid = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			valid = Search.EXCEPTION.getValue();
		}finally {
			connexion.closeConnection();
		}
		return valid;
	}

	protected int validateStaffNumberTwoAcademics(int staffNumberSearch) {
		int staffNumber = searchStaffNumberTeacher(staffNumberSearch);
		if(staffNumber == Search.NOTFOUND.getValue() ){
			staffNumber = searchStaffNumberCoordinator(staffNumberSearch);
		}
		return staffNumber;
	}

	protected void addRelations(int idUserAdd, String status, String userType) {
		StatusDAOImpl statusDAO = new StatusDAOImpl();
		int idUserStatusSearch = statusDAO.searchIdStatus(status);
		int idUserTypeSearch = searchIdUserType(userType);
		addUserUserStatus(idUserAdd, idUserStatusSearch,idUserTypeSearch);
		addUserUserType(idUserAdd, idUserTypeSearch);
	}

	protected int searchIdUser(String email, String alternateEmail, String phone) {
		int idUser = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchIdUser = "Select idUser from User where email=? AND alternateEmail=? AND phone=?";
			preparedStatement = connection.prepareStatement(querySearchIdUser);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, alternateEmail);
			preparedStatement.setString(3, phone);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				idUser = resultSet.getInt("idUser");
			}
		} catch (SQLException ex) {
			new Exception().log(ex);
			TelegramBot.sendToTelegram(ex.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return idUser;
	}

	protected int searchIdUserType(String userType) {
		int idUserType = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchIdUserType = "Select idUserType from UserType where type =?";
			preparedStatement = connection.prepareStatement(querySearchIdUserType);
			preparedStatement.setString(1, userType);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				idUserType = resultSet.getInt("idUserType");
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return idUserType;
	}

	private int searchAlternateEmail(String alternateEmail)  {
		int search = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchAlternateEmail = "Select alternateEmail from User where alternateEmail =?";
			preparedStatement = connection.prepareStatement(querySearchAlternateEmail);
			preparedStatement.setString(1, alternateEmail);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				search = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			search = Search.EXCEPTION.getValue();
		} finally {
			connexion.closeConnection();
		}
		return search;
	}

	private int searchPhone(String phone) {
		int search = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchPhone = "Select phone from User where phone =?";
			preparedStatement = connection.prepareStatement(querySearchPhone);
			preparedStatement.setString(1, phone);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				search = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			search = Search.EXCEPTION.getValue();
		} finally {
			connexion.closeConnection();
		}
		return search;
	}

	private int searchEmail(String email)  {
		int search = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String querySearchEmail = "Select email from User where email =?";
			preparedStatement = connection.prepareStatement(querySearchEmail);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				search = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			search = Search.EXCEPTION.getValue();
		} finally {
			connexion.closeConnection();
		}
		return search;
	}

	private void addUserUserType(int idUserAdd, int idUserTypeSearch) {
		try {
			connection = connexion.getConnection();
			String queryAddUserUserType = "INSERT INTO User_UserType (idUser, idUserType)  VALUES (?,?)";
			preparedStatement = connection.prepareStatement(queryAddUserUserType);
			preparedStatement.setInt(1, idUserAdd);
			preparedStatement.setInt(2, idUserTypeSearch);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		}finally {
			connexion.closeConnection();
		}
	}

	private void addUserUserStatus(int idUserAdd, int idUserStatus, int idUserType){
		try {
			connection = connexion.getConnection();
			String queryAddUserUserStatus = "INSERT INTO User_Status (idUser, idStatus, idUserType)  VALUES (?,?,?)";
			preparedStatement = connection.prepareStatement(queryAddUserUserStatus);
			preparedStatement.setInt(1, idUserAdd);
			preparedStatement.setInt(2, idUserStatus);
			preparedStatement.setInt(3, idUserType);
			preparedStatement.executeUpdate();
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		}finally {
			connexion.closeConnection();
		}
	}

}
