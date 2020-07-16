package domain;

import java.util.*;

public enum Month {
    JANUARY("Enero"),
    FEBRUARY("Febrero"),
    MARCH("Marzo"),
    APRIL ("Abril"),
    MAY ("Mayo"),
    JUNE ("Junio"),
    JULY ("Julio"),
    AUGUST ("Agosto"),
    SEPTEMBER ("Septiembre"),
    OCTOBER("Octubre"),
    NOVEMBER ("Noviembre"),
    DECEMBER ("Dicembre");

    private final String month;

    Month(String month) {
        this.month = month;
    }

    public String getMonth () {return  month;}

}
