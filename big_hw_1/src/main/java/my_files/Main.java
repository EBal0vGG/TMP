package my_files;

import java.util.Scanner;

import my_files.di.DI;
import my_files.exports.CSVExportVisitor;
import my_files.exports.JsonExportVisitor;
import my_files.exports.YamlExportVisitor;
import my_files.imports.CSVDataimporter;
import my_files.imports.JsonDataImporter;
import my_files.imports.YamlDataImporter;
import my_files.model.Common_Facade;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Приложение банка запущено ===");

        DI di = new DI();
        Common_Facade facade = new Common_Facade();
        di.registerSingleton(Common_Facade.class, facade);
        
        Scanner scanner = new Scanner(System.in);
        /*
        System.out.println("Выберите формат файла для импорта данных:");
        System.out.println("1 - JSON");
        System.out.println("2 - CSV");
        System.out.println("3 - YAML");
        System.out.print("Введите номер формата: ");
        */

        
        boolean validChoice = false;
        String choice = "";
        while (!validChoice) {
            System.out.println("Выберите формат файла для импорта данных:");
            System.out.println("1 - JSON");
            System.out.println("2 - CSV");
            System.out.println("3 - YAML");
            System.out.print("Введите номер формата: ");
            choice = scanner.nextLine().trim();
            if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                validChoice = true;
            } else {
                System.out.println("Неверный выбор. Пожалуйста, попробуйте ещё раз.");
            }
        }
        
        switch (choice) {
            case "1":
                System.out.println("Импорт данных из JSON...");
                JsonDataImporter jsonImporter = new JsonDataImporter(facade);
                jsonImporter.importFile("src/main/resources/data.json");
                break;
            case "2":
                System.out.println("Импорт данных из CSV...");
                CSVDataimporter csvImporter = new CSVDataimporter(facade);
                csvImporter.importFile("src/main/resources/data.csv");
                break;
            case "3":
                System.out.println("Импорт данных из YAML...");
                YamlDataImporter yamlImporter = new YamlDataImporter(facade);
                yamlImporter.importFile("src/main/resources/data.yaml");
                break;
            default:
                System.out.println("Неверный выбор. Импорт не выполнен.");
        }
        
        // Теперь можно запустить event-loop, в котором пользователь выполняет CRUD и аналитику
        boolean exit = false;
        while (!exit) {
            printMenu();
            String option = scanner.nextLine().trim();
            try {
                switch (option.toLowerCase()) {
                    case "1":
                        listAccounts(facade);
                        break;
                    case "2":
                        createAccount(facade, scanner);
                        break;
                    case "3":
                        updateAccountName(facade, scanner);
                        break;
                    case "4":
                        deleteAccount(facade, scanner);
                        break;
                    case "5":
                        listCategories(facade);
                        break;
                    case "6":
                        createCategory(facade, scanner);
                        break;
                    case "7":
                        updateCategoryName(facade, scanner);
                        break;
                    case "8":
                        deleteCategory(facade, scanner);
                        break;
                    case "9":
                        listOperations(facade);
                        break;
                    case "10":
                        createOperation(facade, scanner);
                        break;
                    case "11":
                        updateOperationDescription(facade, scanner);
                        break;
                    case "12":
                        deleteOperation(facade, scanner);
                        break;
                    case "13":
                        calculateNetProfit(facade, scanner);
                        break;
                        case "14":
                        calculateTotalIncome(facade, scanner);
                        break;
                    case "15":
                        calculateTotalExpenditure(facade, scanner);
                        break;
                    case "16":
                        calculateAccountProfit(facade, scanner);
                        break;
                    case "17":
                        groupOperationsByCategory(facade, scanner);
                        break;
                    case "18":
                        groupOpsByCategoryForAccount(facade, scanner);
                        break;
                    case "exit":
                    case "0":
                        exit = true;
                        break;
                    default:
                        System.out.println("Неверная команда. Повторите попытку.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
            System.out.println(); // пустая строка для разделения вывода
        }
        
        // При завершении работы экспортируем данные во все три файла
        System.out.println("Экспорт данных в JSON, CSV и YAML...");
        
        JsonExportVisitor jsonExportVisitor = new JsonExportVisitor();
        facade.exportData(jsonExportVisitor);
        jsonExportVisitor.exportToFile("src/main/resources/data.json");
        
        CSVExportVisitor csvExportVisitor = new CSVExportVisitor();
        facade.exportData(csvExportVisitor);
        csvExportVisitor.exportToFile("src/main/resources/data.csv");
        
        YamlExportVisitor yamlExportVisitor = new YamlExportVisitor();
        facade.exportData(yamlExportVisitor);
        yamlExportVisitor.exportToFile("src/main/resources/data.yaml");
        
        System.out.println("Выход из приложения.");
        scanner.close();
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }


    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число (с плавающей точкой, если нужно).");
            }
        }
    }

    private static java.time.LocalDateTime readLocalDateTime(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return java.time.LocalDateTime.parse(input);
            } catch (Exception e) {
                System.out.println("Ошибка: введите дату и время в формате yyyy-MM-ddTHH:mm.");
            }
        }
    }

    private static boolean readBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Ошибка: введите true или false.");
            }
        }
    }
    
    private static void printMenu() {
        System.out.println("==== Меню банка ====");
        System.out.println("1. Список счетов");
        System.out.println("2. Создать счет");
        System.out.println("3. Изменить имя счета");
        System.out.println("4. Удалить счет");
        System.out.println("5. Список категорий");
        System.out.println("6. Создать категорию");
        System.out.println("7. Изменить имя категории");
        System.out.println("8. Удалить категорию");
        System.out.println("9. Список операций");
        System.out.println("10. Создать операцию");
        System.out.println("11. Изменить описание операции");
        System.out.println("12. Удалить операцию");
        System.out.println("13. Аналитика: Чистая прибыль за период");
        System.out.println("14. Аналитика: Общий доход за период");
        System.out.println("15. Аналитика: Общие расходы за период");
        System.out.println("16. Аналитика: Прибыль по счёту");
        System.out.println("17. Аналитика: Группировка всех операций по категориям");
        System.out.println("18. Аналитика: Группировка операций по категориям для счёта");
        System.out.println("0 или 'exit' - Выход");
        System.out.print("Выберите опцию: ");
    }
    
    // CRUD для счетов
    private static void listAccounts(Common_Facade facade) {
        System.out.println("==== Счета ====");
        facade.getAccounts().forEach(account ->
            System.out.println("ID: " + account.getId() +
                               ", Имя: " + account.getName() +
                               ", Баланс: " + account.getBalance())
        );
    }
    
    private static void createAccount(Common_Facade facade, Scanner scanner) {
        System.out.print("Введите имя нового счета: ");
        String name = scanner.nextLine();
        facade.createBA(name);
        System.out.println("Счет создан.");
    }
    
    private static void updateAccountName(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID счета для изменения: ");
        System.out.print("Введите новое имя счета: ");
        String newName = scanner.nextLine();
        facade.change_BA_name(id, newName);
        //System.out.println("Имя счета изменено.");
    }
    
    private static void deleteAccount(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID счета для удаления: ");
        facade.deleteBA(id);
        //System.out.println("Счет удален.");
    }
    
    // CRUD для категорий
    private static void listCategories(Common_Facade facade) {
        System.out.println("==== Категории ====");
        facade.getCategories().forEach(cat ->
            System.out.println("ID: " + cat.getId() +
                               ", Имя: " + cat.getName() +
                               ", Тип: " + (cat.isExpenditure ? "Расход" : "Доход"))
        );
    }
    
    private static void createCategory(Common_Facade facade, Scanner scanner) {
        System.out.print("Введите имя новой категории: ");
        String name = scanner.nextLine();
        boolean isExpense = readBoolean(scanner, "Введите тип (true для расхода, false для дохода): ");
        facade.createCategory(name, isExpense);
        System.out.println("Категория создана.");
    }
    
    private static void updateCategoryName(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID категории для изменения: ");
        System.out.print("Введите новое имя категории: ");
        String newName = scanner.nextLine();
        facade.change_Category_name(id, newName);
        //System.out.println("Имя категории изменено.");
    }
    
    private static void deleteCategory(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID категории для удаления: ");
        facade.deleteCategory(id);
        //System.out.println("Категория удалена.");
    }
    
    // CRUD для операций
    private static void listOperations(Common_Facade facade) {
        System.out.println("==== Операции ====");
        facade.getOperations().forEach(op -> {
            System.out.println("ID: " + op.getId() +
                               ", Тип: " + (op.getIsExpenditure() ? "Расход" : "Доход") +
                               ", Счет ID: " + op.getBA_id() +
                               ", Сумма: " + op.getSum() +
                               ", Дата: " + op.getTime() +
                               ", Категория ID: " + op.getCategory_id() +
                               ", Описание: " + op.getDescription());
        });
    }
    
    private static void createOperation(Common_Facade facade, Scanner scanner) {
        boolean isExpense = readBoolean(scanner, "Введите тип операции (true для расхода, false для дохода): ");
        int accountId = readInt(scanner, "Введите ID счета: ");
        double sum = readDouble(scanner, "Введите сумму: ");
        int catId = readInt(scanner, "Введите ID категории: ");
        System.out.print("Введите описание операции: ");
        String descr = scanner.nextLine();
        facade.createOperation(isExpense, accountId, sum, java.time.LocalDateTime.now(), catId, descr);
        //System.out.println("Операция создана.");
    }
    
    private static void updateOperationDescription(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID операции для изменения: ");
        System.out.print("Введите новое описание: ");
        String desc = scanner.nextLine();
        facade.change_description(id, desc);
        //System.out.println("Описание операции изменено.");
    }
    
    private static void deleteOperation(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID операции для удаления: ");
        facade.deleteOperation(id);
        //System.out.println("Операция удалена.");
    }
    
    // Аналитика: Расчет чистой прибыли за выбранный период
    private static void calculateNetProfit(Common_Facade facade, Scanner scanner) {
        java.time.LocalDateTime start = readLocalDateTime(scanner, "Введите начальную дату (yyyy-MM-ddTHH:mm): ");
        java.time.LocalDateTime end = readLocalDateTime(scanner, "Введите конечную дату (yyyy-MM-ddTHH:mm): ");
        double profit = facade.calculateNetProfit(start, end);
        System.out.println("Чистая прибыль за период: " + profit);
    }

    // Аналитика: Расчет общего дохода за период
    private static void calculateTotalIncome(Common_Facade facade, Scanner scanner) {
        java.time.LocalDateTime start = readLocalDateTime(scanner, "Введите начальную дату (yyyy-MM-ddTHH:mm): ");
        java.time.LocalDateTime end = readLocalDateTime(scanner, "Введите конечную дату (yyyy-MM-ddTHH:mm): ");
        double income = facade.calculateNetIncome(start, end);
        System.out.println("Общий доход за период: " + income);
    }

    // Аналитика: Расчет общих расходов за период
    private static void calculateTotalExpenditure(Common_Facade facade, Scanner scanner) {
        java.time.LocalDateTime start = readLocalDateTime(scanner, "Введите начальную дату (yyyy-MM-ddTHH:mm): ");
        java.time.LocalDateTime end = readLocalDateTime(scanner, "Введите конечную дату (yyyy-MM-ddTHH:mm): ");
        double expenditure = facade.calculateNetExpenditures(start, end);
        System.out.println("Общие расходы за период: " + expenditure);
    }

    // Аналитика: Расчет прибыли по конкретному счёту
    private static void calculateAccountProfit(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID счета: ");
        java.time.LocalDateTime start = readLocalDateTime(scanner, "Введите начальную дату (yyyy-MM-ddTHH:mm): ");
        java.time.LocalDateTime end = readLocalDateTime(scanner, "Введите конечную дату (yyyy-MM-ddTHH:mm): ");
        double profit = facade.calculateAccountProfit(id, start, end);
        System.out.println("Прибыль для счета ID " + id + " за указанный период: " + profit);
    }

    // Аналитика: Группировка операций по категориям (для всех счетов)
    private static void groupOperationsByCategory(Common_Facade facade, Scanner scanner) {
        java.time.LocalDateTime start = readLocalDateTime(scanner, "Введите начальную дату (yyyy-MM-ddTHH:mm): ");
        java.time.LocalDateTime end = readLocalDateTime(scanner, "Введите конечную дату (yyyy-MM-ddTHH:mm): ");
        var groups = facade.groupOperationsByCategory(start, end);
        System.out.println("Суммы по категориям:");
        groups.forEach((catId, sum) ->
            System.out.println("Категория ID " + catId + " => " + sum)
        );
    }

    // Аналитика: Группировка операций по категориям для конкретного счета
    private static void groupOpsByCategoryForAccount(Common_Facade facade, Scanner scanner) {
        int id = readInt(scanner, "Введите ID счета: ");
        java.time.LocalDateTime start = readLocalDateTime(scanner, "Введите начальную дату (yyyy-MM-ddTHH:mm): ");
        java.time.LocalDateTime end = readLocalDateTime(scanner, "Введите конечную дату (yyyy-MM-ddTHH:mm): ");
        var groups = facade.groupOpsByCatForAccount(id, start, end);
        System.out.println("Для счета ID " + id + ", суммы по категориям:");
        groups.forEach((catId, sum) ->
            System.out.println("Категория ID " + catId + " => " + sum)
        );
    }
}