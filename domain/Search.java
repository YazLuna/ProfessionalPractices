package domain;

public enum Search {
    FOUND (1),
    NOTFOUND (0);
    private final int value;

    Search(int value) {
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
