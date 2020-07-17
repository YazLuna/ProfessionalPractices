package domain;

import dataaccess.UserMethodDAOImpl;

/**
 * User
 * @author Yazmin
 * @version 08/05/2020
 */

public class User{
    protected String name;
    protected String lastName;
    protected int gender;
    protected String email;
    protected String alternateEmail;
    protected String phone;
    protected String status;
    protected String password;
    protected String userType;
    protected String userName;

    public User () {
        this.status= "Active";
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getLastName () {
        return lastName;
    }
    
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }
    
    public int getGender () {
        return gender;
    }
    
    public void setGender (int gender) {
        this.gender = gender;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getAlternateEmail () {
        return alternateEmail;
    }
    
    public void setAlternateEmail (String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }
    
    public String getPhone () {
        return phone;
    }
    
    public void setPhone (String phone) {
        this.phone = phone;
    }
    
    public String getStatus (){
        return status;
    }
    
    public void setStatus (String status) {
        this.status = status;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String  getUserType() {
        return userType;
    }

    public void setUserType (String userType){
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Method to know if a user is valid to add
     * @param email from user
     * @param alternateEmail from user
     * @param phone from user
     * @param userName from user
     * @return if are a valid user to add
     */
    public static int validateUserAdd(String email, String alternateEmail, String phone, String userName){
        UserMethodDAOImpl userMethodDAO = new UserMethodDAOImpl();
        int result = userMethodDAO.validateUserAdd(email, alternateEmail, phone, userName);
        return result;
    }

    /**
     * Method to add a user
     * @param user to Add
     * @return true if added and false if not
     */
    public static boolean addUser(User user){
        UserMethodDAOImpl userMethodDAO = new UserMethodDAOImpl();
        boolean result = userMethodDAO.addUser(user);
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
    public static int validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName){
        UserMethodDAOImpl userMethodDAO = new UserMethodDAOImpl();
        int result = userMethodDAO.validateAcademicAdd(staffNumber, email, alternateEmail, phone, userName);
        return result;
    }

    /**
     * Method to know if a user is valid
     * @param email from user
     * @param alternateEmail from user
     * @param phone from user
     * @return if are a valid user
     */
    public static int validateUser(String email, String alternateEmail, String phone){
        UserMethodDAOImpl userMethodDAO = new UserMethodDAOImpl();
        int result = userMethodDAO.validateUser(email, alternateEmail, phone);
        return result;
    }

    /**
     * Method to know if a Academic is valid to update
     * @param staffNumber from Academic
     * @param email from Academic
     * @param alternateEmail from Academic
     * @param phone from Academic
     * @return if are a valid Academic to update
     */
    public static int validateAcademicUpdate(int staffNumber, String email, String alternateEmail, String phone){
        UserMethodDAOImpl userMethodDAO = new UserMethodDAOImpl();
        int result = userMethodDAO.validateAcademicUpdate(staffNumber, email, alternateEmail, phone);
        return result;
    }

}