package logic;


public class principalPrueba {

    public static void main(String[] args) {
        String sn = " Ana          maria ";

        ValidateAddUser addUser = new ValidateAddUser();

        System.out.println(addUser.validatePhone("2343333330"));
    }
}
