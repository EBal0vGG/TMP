package my_files.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import my_files.exports.ExportVisitor;



public class Operation {
    public final int id;
    private static int lastIDused = 0;

    public final boolean isExpemditure; //type
    final int BA_id;
    final double sum;
    final LocalDateTime time;
    final int category_id;
    String description; //опционально

    @JsonCreator
    public Operation(
            @JsonProperty("isExpemditure") boolean is_exp,
            @JsonProperty("BA_id") int ba_id,
            @JsonProperty("sum") double Sum,
            @JsonProperty("time") LocalDateTime Time,
            @JsonProperty("category_id") int cat_id,
            @JsonProperty("description") String descr) {
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

    //МНЕ ЭТО ВООБЩЕ НЕ НРАВИТСЯ!!!!
    // НАДО, ЧТОБ только визитор из классов вне папки model мог получить доступ к полям
    public double get_amount() {
        return sum;
    }


    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }

}