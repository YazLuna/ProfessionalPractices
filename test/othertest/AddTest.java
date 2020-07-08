package test.othertest;

import dataaccess.CoordinatorDAOImpl;
import dataaccess.StatusDAOImpl;
import dataaccess.TeacherDAOImpl;
import domain.Practitioner;
import domain.Search;
import domain.Teacher;
import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.apache.commons.codec.binary.Hex;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTest {

    @Test
    public void testAddPractitioner() {
        boolean result;
        Practitioner practitioner = new Practitioner();
        practitioner.setName("Lucio");
        practitioner.setLastName("Garcia");
        practitioner.setGender(1);
        practitioner.setEmail("lu@gmail.com");
        practitioner.setAlternateEmail("luGar@gmail.com");
        practitioner.setPhone("2281901267");
        practitioner.setEnrollment("s18098984");
        practitioner.setPeriod("FEBRERO-JULIO 2020");
        practitioner.setPassword("lucio244");
        result = practitioner.addPractitioner();
        Assert.assertEquals(1,result);

    }

    @Test
    public void testAddTeacher() {
        boolean result = false;
        Teacher teacher = new Teacher();
        teacher.setName("Pablo");
        teacher.setLastName("Hernandez");
        teacher.setGender(1);
        teacher.setEmail("pablo@gmail.com");
        teacher.setAlternateEmail("pabloHer@gmail.com");
        teacher.setPhone("2281334676");
        teacher.setStaffNumber(3);
        teacher.setRegistrationDate("2020-04-29");
        teacher.setPassword("d9a11bc382287cf0c7c585e7a79fdfda90cc6f9db586ef2bb6d88d81d9edb97941591" +
                "61229ddcfabc4ec24c29dad037605a5f48a67da5ec535b6a131309812ef");
        teacher.setUserName("pablito");
        int isActive = teacher.activeTeachers();
        if(isActive <= Search.FOUND.getValue()){
            boolean validateUser = teacher.validateUserAdd(teacher.getEmail(), teacher.getAlternateEmail(),
                    teacher.getPhone(), teacher.getUserName());
            if (validateUser) {
                result = teacher.addTeacher();
            }
        }
        Assert.assertTrue(result);
    }


    @Test
    public void testSearchIsActive() throws SQLException {
        boolean result =false;
        CoordinatorDAOImpl us = new CoordinatorDAOImpl();
        result=us.activeCoordinator();
        Assert.assertTrue(result);
    }

    @Test
    public void testEncryption() throws NoSuchAlgorithmException {
        MessageDigest md = null;
        String password = "1234";
        md= MessageDigest.getInstance("SHA-512");
        md.update(password.getBytes());
        byte[] mb = md.digest();
        System.out.println(Hex.encodeHex(mb));
    }

    @Test
    public void activeTeacher() {
        TeacherDAOImpl teacherDAO = new TeacherDAOImpl();
        int result = teacherDAO.activeTeachers();
        System.out.println(result);
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int result2= statusDAO.searchIdStatus("Active");
        System.out.println(result2);
    }

   /* @Test
    public void password (){
        String password = "yaz";
            String passwordEncrypt= null;
            try{
                MessageDigest md;
                md= MessageDigest.getInstance("SHA-512");
                md.update(password.getBytes());
                byte[] mb = md.digest();
                passwordEncrypt= String.valueOf(Hex.encodeHex(mb));
                System.out.println(passwordEncrypt);
            }catch (NoSuchAlgorithmException e){
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create an encrypt Password", e);
            }


    }*/

    @Test
    public void month(){
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String periodo;
        if(Integer.parseInt(month) > 1 && Integer.parseInt(month) <= 7){
            periodo = "FEBRERO-JULIO "+year;
        } else{
            periodo = "AGOSTO-ENERO "+year+ " " +(Integer.parseInt(year)+1);
        }
        System.out.println(periodo);
    }
    @Test
    public void validateEnrollment () {
        String enrollment = "S18012124";
        boolean isValidEnrollment;
        Pattern pattern = Pattern
                .compile("^[S]"+ "[0-9]{8}");
        Matcher mather = pattern.matcher(enrollment);
        isValidEnrollment = mather.find();
        Assert.assertTrue(isValidEnrollment);
    }
}
