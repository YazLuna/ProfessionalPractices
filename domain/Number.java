package domain;

public enum Number {
    ZERO(0),
    ONE(1),
    TWO(2),
    SEVEN(7);
    private final int number;

    Number(int number) {
        this.number= number;
    }

    public int getNumber(){
        return number;
    }
}
