package dataaccess;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import domain.Teacher;
import domain.Search;
import exception.Exception;

/**
 * Teacher DAO Implements
 * @author Yazmin
 * @version 07/07/2020
 */
public class TeacherDAOImpl extends UserMethodDAOImpl implements ITeacherDAO {
	private final Connexion connexion;
	private Connection connection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;

	/**
	 * Constructor TeacherDAOImpl class
	 */
	public TeacherDAOImpl() {
		connexion = new Connexion();
	}

	/**
	 * Method for adding a teacher
	 * @param teacher Object to add
	 * @return true if successful false if not
	 */
	@Override
	public boolean addTeacher(Teacher teacher) {
		boolean resultAdd = false;
		int idUser = searchIdUser(teacher.getEmail(), teacher.getAlternateEmail(), teacher.getPhone());
		try {
			connection = connexion.getConnection();
			String queryAddTeacher = "INSERT INTO Teacher (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
			preparedStatement = connection.prepareStatement(queryAddTeacher);
			preparedStatement.setInt(1, teacher.getStaffNumber());
			preparedStatement.setString(2, teacher.getRegistrationDate());
			preparedStatement.setInt(3, idUser);
			preparedStatement.executeUpdate();
			resultAdd = true;
		} catch (SQLException ex) {
			new Exception().log(ex);
		} finally {
			connexion.closeConnection();
		}
		return resultAdd;
	}

	/**
	 * Method to obtain a teacher according to their staffNumber
	 * @param staffNumber from teacher
	 * @return Teacher Object
	 */
	@Override
	public Teacher getTeacher(int staffNumber) {
		Teacher teacher = new Teacher();
		try {
			connection = connexion.getConnection();
			String queryGetTeacherSelected = "SELECT name, lastName, gender, email, alternateEmail, phone, staffNumber, status FROM Teacher" +
					", User, UserType, Status, User_Status WHERE Teacher.idUser=User.idUser AND UserType.type =? AND" +
					" Teacher.staffNumber =? AND Status.idStatus = User_Status.idStatus AND User_Status.idUser =" +
					" User.idUser AND User_Status.idUserType = UserType.idUserType";
			preparedStatement = connection.prepareStatement(queryGetTeacherSelected);
			preparedStatement.setString(1, "Teacher");
			preparedStatement.setInt(2, staffNumber);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				teacher.setName(resultSet.getString("name"));
				teacher.setLastName(resultSet.getString("lastName"));
				teacher.setGender(resultSet.getInt("gender"));
				teacher.setEmail(resultSet.getString("email"));
				teacher.setAlternateEmail(resultSet.getString("alternateEmail"));
				teacher.setPhone(resultSet.getString("phone"));
				teacher.setStaffNumber(resultSet.getInt("staffNumber"));
				teacher.setStatus(resultSet.getString("status"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return teacher;
	}

	/**
	 * Method of obtaining the list of teachers
	 * @return Teacher List
	 */
	@Override
	public List<Teacher> getTeachers() {
		List<Teacher> teachers = new ArrayList<>();
		try {
			connection = connexion.getConnection();
			Statement consult = connection.createStatement();
			resultSet = consult.executeQuery("SELECT name, lastName, staffNumber FROM Teacher INNER JOIN User ON Teacher.idUser =" +
					" User.idUser");
			while (resultSet.next()) {
				Teacher teacher = new Teacher();
				teacher.setName(resultSet.getString("name"));
				teacher.setLastName(resultSet.getString("lastName"));
				teacher.setStaffNumber(resultSet.getInt("staffNumber"));
				teachers.add(teacher);
			}
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return teachers;
	}

	/**
	 * Method of obtaining the list of active teachers
	 * @return List of active teachers
	 */
	@Override
	public List<Teacher> getTeachersActive() {
		List<Teacher> teachers = new ArrayList<>();
		StatusDAOImpl statusDAO = new StatusDAOImpl();
		int idUserStatus = statusDAO.searchIdStatus("Active");
		int idUserType = searchIdUserType("Teacher");
		try {
			connection = connexion.getConnection();
			String queryTeacherActive = "SELECT name, lastName, staffNumber, email FROM Teacher, User, User_Status WHERE Teacher.idUser =" +
					" User.idUser AND User_Status.idStatus =? AND User_Status.idUser = User.idUser AND idUserType =?";
			preparedStatement = connection.prepareStatement(queryTeacherActive);
			preparedStatement.setInt(1,idUserStatus);
			preparedStatement.setInt(2,idUserType);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Teacher teacher = new Teacher();
				teacher.setName(resultSet.getString("name"));
				teacher.setLastName(resultSet.getString("lastName"));
				teacher.setStaffNumber(resultSet.getInt("staffNumber"));
				teacher.setEmail(resultSet.getString("email"));
				teachers.add(teacher);
			}
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return teachers;
	}

	/**
	 * Method for obtaining information from all teachers
	 * @return List with complete information of teachers
	 */
	@Override
	public List<Teacher> getTeachersInformation() {
		List<Teacher> teachers = new ArrayList<>();
		int idUserType = searchIdUserType("Teacher");
		try {
			connection = connexion.getConnection();
			String queryGetAllInformation = "SELECT name, lastName, staffNumber, email, alternateEmail, phone, status FROM Teacher, User" +
					", User_Status,Status WHERE Teacher.idUser = User.idUser AND User_Status.idUser = User.idUser AND" +
					" User_Status.idUser = User.idUser AND Status.idStatus = User_Status.idStatus AND idUserType =?";
			preparedStatement = connection.prepareStatement(queryGetAllInformation);
			preparedStatement.setInt(1, idUserType);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Teacher teacher = new Teacher();
				teacher.setName(resultSet.getString("name"));
				teacher.setLastName(resultSet.getString("lastName"));
				teacher.setStaffNumber(resultSet.getInt("staffNumber"));
				teacher.setEmail(resultSet.getString("email"));
				teacher.setAlternateEmail(resultSet.getString("alternateEmail"));
				teacher.setPhone(resultSet.getString("phone"));
				teacher.setStatus(resultSet.getString("status"));
				teachers.add(teacher);
			}
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return teachers;
	}

	/**
	 * Dynamic method to modify a teacher
	 * @param staffNumberOrigin from Teacher
	 * @param teacherEdit Object with new information
	 * @param datesUpdate Fields to modify
	 * @return True if update, false if not
	 */
	@Override
	public boolean updateTeacher(int staffNumberOrigin, Teacher teacherEdit, List<String>datesUpdate) {
		boolean result = false;
		String datesUpdateTeacher = datesUpdate.get(0)+ "= ?, ";
		List<String> change = new ArrayList<>();
		change.add("get"+datesUpdate.get(0));
		for (int indexDatesUpdate = 1 ; indexDatesUpdate < datesUpdate.size();  indexDatesUpdate ++) {
			if ( indexDatesUpdate == datesUpdate.size() -1){
				datesUpdateTeacher = datesUpdateTeacher + datesUpdate.get(indexDatesUpdate)  + "= ?";
			} else {
				datesUpdateTeacher = datesUpdateTeacher + datesUpdate.get( indexDatesUpdate)  + "= ?,";
			}
			change.add("get"+datesUpdate.get( indexDatesUpdate));
		}
		String sentence = "UPDATE Teacher INNER JOIN User ON Teacher.idUser = User.idUser SET " +datesUpdateTeacher+
				" WHERE Teacher.staffNumber = " +staffNumberOrigin;
		try{
			connection = connexion.getConnection();
			preparedStatement = connection.prepareStatement(sentence);
			Class classTeacher = teacherEdit.getClass();
			for(int indexPreparedStatement = 1 ; indexPreparedStatement <= datesUpdate.size(); indexPreparedStatement++){
				Method methodTeacher;
				boolean isString = true;
				try {
					methodTeacher = classTeacher.getMethod(change.get(indexPreparedStatement - 1));
					String isWord = (String) methodTeacher.invoke(teacherEdit, new Object[] {});
				} catch (ClassCastException e) {
					isString = false;
				}
				if(isString){
					methodTeacher = classTeacher.getMethod(change.get(indexPreparedStatement - 1));
					String word = (String) methodTeacher.invoke(teacherEdit, new Object[] {});
					preparedStatement.setString(indexPreparedStatement,word);
				} else{
					methodTeacher = classTeacher.getMethod(change.get(indexPreparedStatement - 1));
					int integer = (int) methodTeacher.invoke(teacherEdit, new Object[] {});
					preparedStatement.setInt(indexPreparedStatement, integer);
				}
			}
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException | ReflectiveOperationException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to recover a deleted teacher
	 * @param staffNumber from Teacher
	 * @return True if recover, false if not
	 */
	@Override
	public boolean recoverTeacher(int staffNumber) {
		boolean result = false;
		StatusDAOImpl statusDao = new StatusDAOImpl();
		int idUserStatus = statusDao.searchIdStatus("Active");
		int idUserType = searchIdUserType("Teacher");
		try {
			connection = connexion.getConnection();
			String queryRecoverTeacher = "UPDATE Teacher INNER JOIN User_Status SET User_Status.idStatus =?, Teacher.dischargeDate =? WHERE" +
					" User_Status.idUser = Teacher.idUser AND Teacher.staffNumber =? AND User_Status.idUserType =?";
			preparedStatement = connection.prepareStatement(queryRecoverTeacher);
			preparedStatement.setInt(1, idUserStatus);
			preparedStatement.setString(2, null);
			preparedStatement.setInt(3, staffNumber);
			preparedStatement.setInt(4,idUserType);
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to delete a teacher
	 * @param status Inactive
	 * @param dischargeDate from Teacher
	 * @param staffNumber from Teacher
	 * @return True if delete, False if not
	 */
	@Override
	public boolean deleteTeacher(String status, String dischargeDate, int staffNumber) {
		boolean result = false;
		StatusDAOImpl statusDao = new StatusDAOImpl();
		int idUserStatus = statusDao.searchIdStatus(status);
		int idUserType = searchIdUserType("Teacher");
		try {
			connection = connexion.getConnection();
			String queryDeleteTeacher = "UPDATE Teacher INNER JOIN User_Status SET User_Status.idStatus =?, Teacher.dischargeDate =? WHERE" +
					" User_Status.idUser = Teacher.idUser AND Teacher.staffNumber =? AND User_Status.idUserType =?";
			preparedStatement = connection.prepareStatement(queryDeleteTeacher);
			preparedStatement.setInt(1, idUserStatus);
			preparedStatement.setString(2, dischargeDate);
			preparedStatement.setInt(3,staffNumber);
			preparedStatement.setInt(4,idUserType);
			preparedStatement.executeUpdate();
			result = true;
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return result;
	}

	/**
	 * Method to know the number of active teachers
	 * @return Number of active teachers
	 */
	@Override
	public int activeTeachers() {
		int isActive = Search.NOTFOUND.getValue();
		StatusDAOImpl statusDAO = new StatusDAOImpl();
		int idUserStatus = statusDAO.searchIdStatus("Active");
		try {
			connection = connexion.getConnection();
			String queryActiveTeacher = "SELECT staffNumber FROM Teacher, UserType, User_Status, User_UserType WHERE User_Status.idUser =" +
					" Teacher.idUser AND UserType.type=? AND User_UserType.idUser = Teacher.idUser AND" +
					" User_UserType.idUserType = UserType.idUserType AND User_Status.idUserType =" +
					" UserType.idUserType AND User_Status.idStatus =?";
			preparedStatement = connection.prepareStatement(queryActiveTeacher);
			preparedStatement.setString(1, "Teacher");
			preparedStatement.setInt(2, idUserStatus);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				isActive++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return isActive;
	}

	/**
	 * Method to know if are teachers
	 * @return True if are teachers, false if not
	 */
	@Override
	public boolean areTeachers() {
		boolean areTeachers = false;
		try {
			connection = connexion.getConnection();
			String queryAreTeacher = "SELECT staffNumber FROM Teacher, UserType, User_UserType WHERE UserType.type=? AND" +
					" User_UserType.idUser = Teacher.idUser AND User_UserType.idUserType = UserType.idUserType";
			preparedStatement = connection.prepareStatement(queryAreTeacher);
			preparedStatement.setString(1, "Teacher");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				areTeachers = true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			connexion.closeConnection();
		}
		return areTeachers;
	}

	/**
	 * Method to know if that teacher is coordinator
	 * @param teacher Object
	 * @return True if is Coordinator, false if not
	 */
	@Override
	public boolean isCoordinator(Teacher teacher) {
		boolean isCoordinator = searchUserAcademic(teacher.getName(), teacher.getLastName(), teacher.getEmail()
				, teacher.getAlternateEmail(), teacher.getPhone(), teacher.getGender());
		return isCoordinator;
	}

}