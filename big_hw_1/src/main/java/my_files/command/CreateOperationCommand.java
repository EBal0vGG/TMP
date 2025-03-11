package my_files.command;

import java.time.LocalDateTime;

import my_files.model.Common_Facade;

public class CreateOperationCommand implements Command {
    private final Common_Facade facade;
    private final boolean isExpense;
    private final int baId;
    private final double sum;
    //private final LocalDateTime time;  //Кажется, это уже будет записано по факту
    private final int categoryId;
    private final String description;

    public CreateOperationCommand(Common_Facade Facade, boolean IsExpense, int BaId, double Sum, /*LocalDateTime Time,*/ int CategoryId, String Description) {
        facade = Facade;
        isExpense = IsExpense;
        baId = BaId;
        sum = Sum;
       //time = Time;
        categoryId = CategoryId;
        description = Description;
    }

    @Override
    public void execute() {
        facade.createOperation(isExpense, baId, sum, LocalDateTime.now(), categoryId, description);
    }
}
