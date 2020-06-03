package domain;

public enum Gender {
    MALE(1),
    FEMALE(0);
    private final int gender;

    Gender(int gender) {
        this.gender= gender;
    }

    public int getGender(){
        return gender;
    }
}
