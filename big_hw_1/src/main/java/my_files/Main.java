package my_files;

import my_files.command.Command;
import my_files.command.CreateOperationCommand;
import my_files.command.TimedCommandDecorator;
import my_files.exports.CSVExportVisitor;
import my_files.imports.CSVDataimporter;
import my_files.model.Common_Facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Common_Facade facade = new Common_Facade();


        /* это уже точно работает
        JsonDataImporter importer = new JsonDataImporter(facade);
        //Перекинуть потом data.json в папку resources
        importer.importFile("src/main/resources/data.json");
        */

        CSVDataimporter CDI = new CSVDataimporter(facade);
        CDI.importFile("src/main/resources/data.csv");
        
        //Для проверки можно вывести, сколько объектов импортировано
        System.out.println("Импортированные аккаунты: " + facade.getAccounts().size());
        facade.getAccounts().forEach(account -> {
            System.out.println("Account ID: " + account.getId());
            System.out.println("Account Name: " + account.getName());
            System.out.println("Account Balance: " + account.getBalance());
            System.out.println();
        });

        System.out.println("Импортированные категории: " + facade.getCategories().size());
        facade.getCategories().forEach(category -> {
            System.out.println("Category ID: " + category.getId());
            System.out.println("Category Name: " + category.getName());
            System.out.println();
        });

        System.out.println("Импортированные операции: " + facade.getOperations().size());
        facade.getOperations().forEach(operation -> {
            System.out.println("Operation ID: " + operation.getId());
            System.out.println("Operation Amount: " + operation.getSum());
            System.out.println("Operation Date: " + operation.getTime());
            System.out.println("Operation Category ID: " + operation.getCategory_id());
            System.out.println("Operation Account ID: " + operation.getBA_id());
            System.out.println("Operation Description: " + operation.getDescription());
            System.out.println();
        });


        //Пример команды для создания операции
        Command createOpCommand = new CreateOperationCommand(facade, true, 1, 100.0, /*LocalDateTime.now(), Время вроде как тут не нужно */ 1, "Test operation");
        Command timedCommand = new TimedCommandDecorator(createOpCommand);
        timedCommand.execute();


        
        //тест первого нормально экспортера (в json)
        /* чтоб не портить данные в json, закомментил
        JsonExportVisitor JEV = new JsonExportVisitor();
        facade.exportData(JEV);
        JEV.exportToFile("src/main/resources/data.json");
        */

        //тест второго экспортера (в csv)
        CSVExportVisitor CEV = new CSVExportVisitor();
        facade.exportData(CEV);
        CEV.exportToFile("src/main/resources/data.csv");

    }
}