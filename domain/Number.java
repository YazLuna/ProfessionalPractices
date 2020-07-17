package domain;

public enum Number {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    SEVEN(7),
    FIVE(5),
    TEN(10);

    private final int number;

    Number(int number) {
        this.number= number;
    }

    public int getNumber(){
        return number;
    }
}
