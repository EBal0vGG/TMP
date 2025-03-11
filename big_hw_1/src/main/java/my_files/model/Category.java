package my_files.model;

public class Category {
    public final int id;
    private static int lastIDused = 0;

    String name;
    final boolean isExpenditure;  //  type

    public Category() {
        id = ++lastIDused;
        name = String.valueOf(id);
        isExpenditure = true;
    }


    public Category(String new_name) {
        id = ++lastIDused;
        name = new_name;
        isExpenditure = true;
    }

    public Category(int Id) {
        id = Id;
        lastIDused = Id;
        name = String.valueOf(id);
        isExpenditure = true;
    }

    public Category(boolean b) {
        id = ++lastIDused;
        name = String.valueOf(id);
        isExpenditure = b;
    }

    public Category(String new_name, boolean b) {
        id = ++lastIDused;
        name = new_name;
        isExpenditure = b;
    }

    public Category(String new_name, boolean b, int Id) {
        id = Id;
        lastIDused = Id;
        name = new_name;
        isExpenditure = b;
    }
}
