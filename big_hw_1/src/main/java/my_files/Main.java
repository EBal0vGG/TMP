package my_files;

import my_files.command.Command;
import my_files.command.CreateOperationCommand;
import my_files.command.TimedCommandDecorator;
import my_files.exports.CSVExportVisitor;
import my_files.imports.JsonDataImporter;
import my_files.model.Common_Facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Common_Facade facade = new Common_Facade();


        JsonDataImporter importer = new JsonDataImporter(facade);
        //Перекинуть потом data.json в папку resources
        importer.importFile("src/main/java/my_files/imports/data.json");
        
        //Для проверки можно вывести, сколько объектов импортировано
        System.out.println("Импортированные аккаунты: " + facade.getAccounts().size());
        System.out.println("\n" + facade.getAccounts() + "\n");
        System.out.println("Импортированные категории: " + facade.getCategories().size());
        System.out.println("\n" + facade.getCategories() + "\n");
        System.out.println("Импортированные операции: " + facade.getOperations().size());
        System.out.println("\n" + facade.getOperations() + "\n");


        //Пример команды для создания операции
        Command createOpCommand = new CreateOperationCommand(facade, true, 1, 100.0, /*LocalDateTime.now(), Время вроде как тут не нужно */ 1, "Test operation");

        Command timedCommand = new TimedCommandDecorator(createOpCommand);

        timedCommand.execute();

        CSVExportVisitor exportVisitor = new CSVExportVisitor();
        facade.exportData(exportVisitor);
    }
}