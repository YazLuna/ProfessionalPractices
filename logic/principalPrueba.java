package logic;


import dataaccess.ResponsibleProjectDAOImpl;

public class principalPrueba {

    public static void main(String[] args) {
        ResponsibleProjectDAOImpl addResponsible = new ResponsibleProjectDAOImpl();
        /*boolean result = addResponsible.searchCharge("Holi");
        assert result!=true : "El Cargo ya existe : addCharge(name)" + result;
        System.out.println("Se registro "+result);*/
        System.out.println(addResponsible.addCharge("Holi"));

    }
}
