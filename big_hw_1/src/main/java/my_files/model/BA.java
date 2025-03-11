package my_files.model;


// TODO: Добавить геттеры и сеттеры, подумать, какие еще методы нужны

// Bank account
public class BA {
    public final int id;
    private static int lastIDused = 0;

    public String name;
    //private double balance;
    double balance;

    public BA() {
        id = ++lastIDused;
        name = String.valueOf(id);
        balance = 0;
    }

    public BA(String new_name) {
        id = ++lastIDused;
        name = new_name;
        balance = 0;
    }

    public BA (int Balance) {
        id = ++lastIDused;
        name = String.valueOf(id);
        this.balance = Balance;
    }

    public BA(int Balance, String new_name) {
        id = ++lastIDused;
        name = new_name;
        balance = Balance;
    }

    public BA(int ID, int Balance) {
        id = ID;
        lastIDused = ID;
        name = String.valueOf(id);
        balance = Balance;
    }

    public BA (int ID, String new_name, int Balance) {
        id = ID;
        lastIDused = ID;
        name = new_name;
        balance = Balance;
    }
}
