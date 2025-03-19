package my_files.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import my_files.exports.ExportVisitor;

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

    public BA (double Balance) {
        id = ++lastIDused;
        name = String.valueOf(id);
        this.balance = Balance;
    }

    public BA(double Balance, String new_name) {
        id = ++lastIDused;
        name = new_name;
        balance = Balance;
    }

    public BA(int ID, double Balance) {
        id = ID;
        lastIDused = ID;
        name = String.valueOf(id);
        balance = Balance;
    }

    @JsonCreator
    public BA(
        @JsonProperty("id") Integer id,
        @JsonProperty("name") String name,
        @JsonProperty("balance") Double balance
    ) {
        if (id != null) {
            this.id = id;
            if (id > lastIDused) {
                lastIDused = id;
            }
        } else {
            this.id = ++lastIDused;
        }
        this.name = name;
        this.balance = balance != null ? balance : 0;
    }

    public BA (int ID, String new_name, int Balance) {
        id = ID;
        lastIDused = ID;
        name = new_name;
        balance = Balance;
    }

    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }

    public double getBalance() {
        return balance;
    }

    // потом мб убрать надо
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
