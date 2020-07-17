package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.util.List;
import domain.Coordinator;
import domain.Search;
import exception.TelegramBot;
import exception.Exception;

/**
 * CoordinatorDAOImpl
 * @author Yazmin
 * @version 07/07/2020
 */
public class CoordinatorDAOImpl extends UserMethodDAOImpl implements ICoordinatorDAO {
	private final Connexion connexion;
	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;

	/**
	 * Constructor CoordinatorDAOImpl class
	 */
	public CoordinatorDAOImpl() {
		connexion = new Connexion();
	}

	/**
	 * Method for adding a Coordinator
	 * @param coordinator Object to add
	 * @return true if successful false if not
	 */
	@Override
	public boolean addCoordinator(Coordinator coordinator) {
		boolean resultAdd = false;
		int idUser = searchIdUser(coordinator.getEmail(),coordinator.getAlternateEmail(),coordinator.getPhone());
		try {
			connection = connexion.getConnection();
			String queryAddCoordinator = "INSERT INTO Coordinator (staffNumber, registrationDate, idUser, idAdministrator) VALUES (?,?,?,?)";
			preparedStatement = connection.prepareStatement(queryAddCoordinator);
			preparedStatement.setInt(1, coordinator.getStaffNumber());
			preparedStatement.setString(2, coordinator.getRegistrationDate());
			preparedStatement.setInt(3, idUser);
			preparedStatement.setInt(4, coordinator.getIdAdministrator());
			preparedStatement.executeUpdate();
			addRelations(idUser, coordinator.getStatus(), coordinator.getUserType());
			resultAdd = true;
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return resultAdd;
	}

	/**
	 * Method to obtain a Coordinator Active
	 * @return Coordinator Object
	 */
	@Override
	public Coordinator getCoordinator() {
		Coordinator coordinator = new Coordinator();
		try {
			connection = connexion.getConnection();
			String queryGetCoordinator = "SELECT name, lastName, gender, email, alternateEmail, phone, staffNumber, status" +
					", registrationDate FROM Coordinator, User, UserType,Status,User_Status WHERE" +
					" Coordinator.idUser=User.idUser AND UserType.type=? AND Status.idStatus =" +
					" User_Status.idStatus AND User_Status.idUser = User.idUser AND" +
					" User_Status.idUserType = UserType.idUserType";
			preparedStatement = connection.prepareStatement(queryGetCoordinator);
			preparedStatement.setString(1, "Coordinator");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				coordinator.setName(resultSet.getString("name"));
				coordinator.setLastName(resultSet.getString("lastName"));
				coordinator.setGender(resultSet.getInt("gender"));
				coordinator.setEmail(resultSet.getString("email"));
				coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
				coordinator.setPhone(resultSet.getString("phone"));
				coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
				coordinator.setRegistrationDate(resultSet.getString("registrationDate"));
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return coordinator;
	}

	/**
	 * Method to obtain a coordinator according to their staffNumber
	 * @param staffNumber from coordinator
	 * @return Coordinator Object
	 */
	@Override
	public Coordinator getCoordinatorSelected(int staffNumber) {
		Coordinator coordinator = new Coordinator();
		try {
			connection = connexion.getConnection();
			String queryGetCoordinatorSelected = "SELECT name, lastName, gender, email, alternateEmail, phone, staffNumber " +
					", status FROM Coordinator, User, UserType, Status, User_Status WHERE Coordinator.idUser=User.idUser AND" +
					" UserType.type=? AND Coordinator.staffNumber=? AND Status.idStatus = User_Status.idStatus AND " +
					" User_Status.idUser = User.idUser AND User_Status.idUserType = UserType.idUserType";
			preparedStatement = connection.prepareStatement(queryGetCoordinatorSelected);
			preparedStatement.setString(1, "Coordinator");
			preparedStatement.setInt(2, staffNumber);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				coordinator.setName(resultSet.getString("name"));
				coordinator.setLastName(resultSet.getString("lastName"));
				coordinator.setGender(resultSet.getInt("gender"));
				coordinator.setEmail(resultSet.getString("email"));
				coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
				coordinator.setPhone(resultSet.getString("phone"));
				coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
				coordinator.setStatus(resultSet.getString("status"));
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return coordinator;
	}

	/**
	 * Method of obtaining the list of Coordinators
	 * @return Coordinators List
	 */
	@Override
	public List<Coordinator> getCoordinators() {
		List<Coordinator> coordinators = new ArrayList<>();
		try {
			connection = connexion.getConnection();
			Statement consult = connection.createStatement();
			resultSet = consult.executeQuery("SELECT name, lastName, staffNumber FROM Coordinator INNER JOIN User ON" +
					" Coordinator.idUser = User.idUser");
			while (resultSet.next()) {
				Coordinator coordinator = new Coordinator();
				coordinator.setName(resultSet.getString("name"));
				coordinator.setLastName(resultSet.getString("lastName"));
				coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
				coordinators.add(coordinator);
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return coordinators;
	}

	/**
	 * Method for obtaining information from all Coordinators
	 * @return List with complete information of Coordinators
	 */
	@Override
	public List<Coordinator> getCoordinatorsInformation() {
		List<Coordinator> coordinators = new ArrayList<>();
		int idUserType = searchIdUserType("Coordinator");
		try {
			connection = connexion.getConnection();
			String queryGetAllInformation = "SELECT name, lastName, staffNumber, email, alternateEmail, phone, status FROM Coordinator" +
					", User, User_Status,Status WHERE Coordinator.idUser = User.idUser AND User_Status.idUser = User.idUser AND" +
					" User_Status.idUser = User.idUser AND Status.idStatus = User_Status.idStatus AND idUserType =?";
			preparedStatement = connection.prepareStatement(queryGetAllInformation);
			preparedStatement.setInt(1, idUserType);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Coordinator coordinator = new Coordinator();
				coordinator.setName(resultSet.getString("name"));
				coordinator.setLastName(resultSet.getString("lastName"));
				coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
				coordinator.setEmail(resultSet.getString("email"));
				coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
				coordinator.setPhone(resultSet.getString("phone"));
				coordinator.setStatus(resultSet.getString("status"));
				coordinators.add(coordinator);
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return coordinators;
	}

	/**
	 * Dynamic method to modify a coordinator
	 * @param staffNumberOrigin from Coordinator
	 * @param coordinatorEdit Object with new information
	 * @param datesUpdate Fields to modify
	 * @return True if update, false if not
	 */
	@Override
	public boolean updateCoordinator(int staffNumberOrigin, Coordinator coordinatorEdit, List<String>datesUpdate) {
		boolean result = false;
		String datesUpdateCoordinator = datesUpdate.get(0)+ "= ?, ";
		List<String> change = new ArrayList<>();
		change.add("get"+datesUpdate.get(0));
		for (int indexDatesUpdate = 1 ; indexDatesUpdate < datesUpdate.size();  indexDatesUpdate ++) {
			if ( indexDatesUpdate == datesUpdate.size() -1){
				datesUpdateCoordinator = datesUpdateCoordinator + datesUpdate.get(indexDatesUpdate)  + "= ?";
			} else {
				datesUpdateCoordinator = datesUpdateCoordinator + datesUpdate.get( indexDatesUpdate)  + "= ?,";
			}
			change.add("get"+datesUpdate.get( indexDatesUpdate));
		}
		String sentence = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser SET " +datesUpdateCoordinator+
				" WHERE Coordinator.staffNumber = " +staffNumberOrigin;
		try{
			connection = connexion.getConnection();
			preparedStatement = connection.prepareStatement(sentence);
			Class classCoordinator = coordinatorEdit.getClass();
			for(int indexPreparedStatement = 1 ; indexPreparedStatement <= datesUpdate.size(); indexPreparedStatement++){
				Method methodCoordinator;
				boolean isString = true;
				try {
					methodCoordinator = classCoordinator.getMethod(change.get(indexPreparedStatement - 1));
					String isWord = (String) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
				} catch (ClassCastException classCastException) {
					isString = false;
				}
				if(isString){
					methodCoordinator = classCoordinator.getMethod(change.get(indexPreparedStatement - 1));
					String word = (String) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
					preparedStatement.setString(indexPreparedStatement,word);
				} else{
					methodCoordinator = classCoordinator.getMethod(change.get(indexPreparedStatement - 1));
					int integer = (int) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
					preparedStatement.setInt(indexPreparedStatement, integer);
				}
			}
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException | ReflectiveOperationException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to recover a deleted Coordinator
	 * @param staffNumber from Coordinator
	 * @return True if recover, false if not
	 */
	@Override
	public boolean recoverCoordinator(int staffNumber) {
		boolean result = false;
		StatusDAOImpl statusDao = new StatusDAOImpl();
		int idUserStatus = statusDao.searchIdStatus("Active");
		int idUserType = searchIdUserType("Coordinator");
		try {
			connection = connexion.getConnection();
			String queryRecoverCoordinator = "UPDATE Coordinator INNER JOIN User_Status SET User_Status.idStatus=?" +
					", Coordinator.dischargeDate =? WHERE User_Status.idUser = Coordinator.idUser AND" +
					" Coordinator.staffNumber =? AND User_Status.idUserType =?";
			connection.prepareStatement(queryRecoverCoordinator);
			preparedStatement.setInt(1, idUserStatus);
			preparedStatement.setString(2, null);
			preparedStatement.setInt(3, staffNumber);
			preparedStatement.setInt(4, idUserType);
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to delete a Coordinator
	 * @param status Inactive
	 * @param dischargeDate from Coordinator
	 * @return True if delete, False if not
	 */
	@Override
	public boolean deleteCoordinator(String status, String dischargeDate) {
		boolean result = false;
		StatusDAOImpl statusDao = new StatusDAOImpl();
		int idUserStatus = statusDao.searchIdStatus(status);
		int idUserType = searchIdUserType("Coordinator");
		try {
			connection = connexion.getConnection();
			String queryDeleteCoordinator = "UPDATE Coordinator INNER JOIN User_Status SET User_Status.idStatus =?" +
					", Coordinator.dischargeDate =? WHERE User_Status.idUser = Coordinator.idUser AND" +
					" User_Status.idUserType =?";
			preparedStatement = connection.prepareStatement(queryDeleteCoordinator);
			preparedStatement.setInt(1, idUserStatus);
			preparedStatement.setString(2, dischargeDate);
			preparedStatement.setInt(3,idUserType);
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to know if there is an active coordinator
	 * @return If there is an active coordinator
	 */
	@Override
	public int activeCoordinator() {
		int isActive = Search.NOTFOUND.getValue();
		StatusDAOImpl statusDAO = new StatusDAOImpl();
		int idUserStatus = statusDAO.searchIdStatus("Active");
		try {
			connection = connexion.getConnection();
			String queryActiveCoordinator = "SELECT staffNumber FROM Coordinator, UserType, User_Status, User_UserType WHERE" +
					" User_Status.idUser = Coordinator.idUser AND UserType.type=? AND User_UserType.idUser =" +
					" Coordinator.idUser AND User_UserType.idUserType = UserType.idUserType AND" +
					" User_Status.idUserType = UserType.idUserType AND User_Status.idStatus =?";
			preparedStatement = connection.prepareStatement(queryActiveCoordinator);
			preparedStatement.setString(1, "Coordinator");
			preparedStatement.setInt(2, idUserStatus);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				isActive = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			isActive = Search.EXCEPTION.getValue();
		} finally {
			connexion.closeConnection();
		}
		return isActive;
	}

	/**
	 * Method to know if there is any coordinator
	 * @return 0 if there are no coordinators, one if there are and -1 if an exception occurred
	 */
	@Override
	public int areCoordinator() {
		int areCoordinator = Search.NOTFOUND.getValue();
		try {
			connection = connexion.getConnection();
			String queryAreCoordinator = "SELECT staffNumber FROM Coordinator, UserType, User_Status, User_UserType WHERE" +
					" User_Status.idUser = Coordinator.idUser AND UserType.type =? AND User_UserType.idUser = " +
					"Coordinator.idUser AND User_UserType.idUserType = UserType.idUserType AND " +
					"User_Status.idUserType = UserType.idUserType";
			preparedStatement = connection.prepareStatement(queryAreCoordinator);
			preparedStatement.setString(1, "Coordinator");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				areCoordinator = Search.FOUND.getValue();
			}
		} catch (SQLException exception) {
			new Exception().log(exception);
			TelegramBot.sendToTelegram(exception.getMessage());
			areCoordinator = Search.EXCEPTION.getValue();
		} finally {
			connexion.closeConnection();
		}
		return areCoordinator;
	}

	/**
	 * Method to know if that Coordinator is Teacher
	 * @param coordinator Object
	 * @return True if is Teacher, false if not
	 */
	@Override
	public boolean isTeacher(Coordinator coordinator) {
		boolean isTeacher = searchUserAcademic(coordinator.getName(), coordinator.getLastName(), coordinator.getEmail()
				, coordinator.getAlternateEmail(), coordinator.getPhone(), coordinator.getGender());
		return isTeacher;
	}
}