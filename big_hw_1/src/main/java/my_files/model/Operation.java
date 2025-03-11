package my_files.model;

import java.time.LocalDateTime;

public class Operation {
    public final int id;
    private static int lastIDused = 0;

    public final boolean isExpemditure; //type
    final int BA_id;
    final double sum;
    final LocalDateTime time;
    final int category_id;
    String description; //опционально

    public Operation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id, String descr) {
        id = ++lastIDused;
        if (is_exp) isExpemditure = true;
        else isExpemditure = false;
        BA_id = ba_id;
        sum = Sum;
        time = Time;
        category_id = cat_id;
        description = descr;
    }

    public Operation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id) {
        id = ++lastIDused;
        isExpemditure = is_exp;
        BA_id = ba_id;
        sum = Sum;
        time = Time;
        category_id = cat_id;
        description = "";
    }

}