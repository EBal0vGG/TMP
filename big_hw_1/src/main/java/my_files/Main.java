package my_files;

import my_files.command.Command;
import my_files.command.CreateOperationCommand;
import my_files.command.TimedCommandDecorator;
import my_files.model.Common_Facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Common_Facade facade = new Common_Facade();

        Command createOpCommand = new CreateOperationCommand(facade, true, 1, 100.0, /*LocalDateTime.now(), Время вроде как тут не нужно */ 1, "Test operation");

        Command timedCommand = new TimedCommandDecorator(createOpCommand);

        timedCommand.execute();
    }
}