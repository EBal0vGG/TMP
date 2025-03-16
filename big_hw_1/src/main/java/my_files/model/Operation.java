package my_files.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import my_files.exports.ExportVisitor;



public class Operation {
    public final int id;
    private static int lastIDused = 0;

    public final boolean isExpenditure; //type
    final int BA_id;
    final double sum;
    final LocalDateTime time;
    final int category_id;
    String description; //опционально

    /*@JsonCreator
    public Operation(
            @JsonProperty("isExpenditure") boolean is_exp,
            @JsonProperty("BA_id") int ba_id,
            @JsonProperty("sum") double Sum,
            @JsonProperty("time") LocalDateTime Time,
            @JsonProperty("category_id") int cat_id,
            @JsonProperty("description") String descr) {
        id = ++lastIDused;
        if (is_exp) isExpenditure = true;
        else isExpenditure = false;
        BA_id = ba_id;
        sum = Sum;
        time = Time;
        category_id = cat_id;
        description = descr;
    }*/
    public Operation(
            boolean is_exp,
            int ba_id,
            double Sum,
            LocalDateTime Time,
            int cat_id,
            String descr) {
        id = ++lastIDused;
        if (is_exp) isExpenditure = true;
        else isExpenditure = false;
        BA_id = ba_id;
        sum = Sum;
        time = Time;
        category_id = cat_id;
        description = descr;
    }

    public Operation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id) {
        id = ++lastIDused;
        isExpenditure = is_exp;
        BA_id = ba_id;
        sum = Sum;
        time = Time;
        category_id = cat_id;
        description = "";
    }

    
    @JsonCreator
    public Operation(@JsonProperty("id") Integer id,
                     @JsonProperty("isExpenditure") boolean isExpenditure,
                     @JsonProperty("BA_id") int BA_id,
                     @JsonProperty("sum") double sum,
                     @JsonProperty("time") LocalDateTime time,
                     @JsonProperty("category_id") int category_id,
                     @JsonProperty("description") String description) {
        if (id != null) {
            this.id = id;
            if (id > lastIDused) {
                lastIDused = id;
            }
        } else {
            this.id = ++lastIDused;
        }
        this.isExpenditure = isExpenditure;
        this.BA_id = BA_id;
        this.sum = sum;
        this.time = time;
        this.category_id = category_id;
        this.description = description;
    }
    

    //МНЕ ЭТО ВООБЩЕ НЕ НРАВИТСЯ!!!!
    // НАДО, ЧТОБ только визитор из классов вне папки model мог получить доступ к полям
    public double get_amount() {
        return sum;
    }


    public void accept(ExportVisitor visitor) {
        visitor.visit(this);
    }


    //потом мб убрать надо
    public int getId() {
        return id;
    }
    public double getAmount() {
        return sum;
    }
    public LocalDateTime getDate() {
        return time;
    }
    public int getCategoryId() {
        return category_id;
    }
    public int getAccountId() {
        return BA_id;
    }
    public String getDescription() {
        return description;
    }

}