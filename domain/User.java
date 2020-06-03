package domain;

import javafx.scene.image.ImageView;

import java.io.File;

/**
 * DAO User
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
    private String status;
    private String password;
    private String userType;
    private String userName;
    private File profilePicture;

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

    public File getProfilePicture(){
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture){
        this.profilePicture = profilePicture;
    }

}