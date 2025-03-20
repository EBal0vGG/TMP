package my_files.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import my_files.service.I_BA_facade;
import my_files.service.I_Category_facade;
import my_files.service.I_Operation_facade;

public class Common_Facade implements I_BA_facade, I_Category_facade, I_Operation_facade {
    private List<BA> Accounts = new ArrayList<>();
    private List<Category> Categories = new ArrayList<>();
    private List<Operation> Operations = new ArrayList<>();

    // I_BA_facade
    @Override
    public void createBA(String name) {
        BA newBA = new BA(name);
        Accounts.add(newBA);
        System.out.println("Счет \"" + newBA.getName() + "\" успешно создан (ID: " + newBA.getId() + ").");
    }

    @Override
    public void deleteBA(int id) {
        for (BA account : Accounts) {
            if (account.id == id) {
                Accounts.remove(account);
                System.out.println("Счет с ID " + id + " удален.");
                return;
            }
        }
        System.out.println("Счет с ID " + id + " не найден.");
    }

    @Override
    public void get_BA_info(int id) {
        for (BA account : Accounts) {
            if (account.id == id) {
                System.out.println("ID: " + account.id + " Name: " + account.name + " Balance: " + account.balance);
                break;
            }
        }
    }

    @Override
    public BA get_ba(int id) {
        for (BA account : Accounts) {
            if (account.id == id) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void add_to_balance(int id, double diff) {
        for (BA account : Accounts) {
            if (account.id == id) {
                account.balance += diff;
                break;
            }
        }
    }

    @Override
    public void change_BA_name(int id, String new_name) {
        for (BA account : Accounts) {
            if (account.id == id) {
                account.name = new_name;
                System.out.println("Имя счета с ID " + id + " изменено.");
                return;
            }
        }
        System.out.println("Счет с ID " + id + " не найден.");
    }

    // I_Category_facade
    @Override
    public void createCategory(String name, boolean is_expenditure) {
        Category newCategory = new Category(name, is_expenditure);
        Categories.add(newCategory);
        System.out.println("Категория \"" + newCategory.getName() + "\" успешно создана (ID: " + newCategory.getId() + ").");
    }

    @Override
    public void deleteCategory(int id) {
        for (Category category : Categories) {
            if (category.id == id) {
                Categories.remove(category);
                System.out.println("Категория с ID " + id + " удалена.");
                return;
            }
        }
        System.out.println("Категория с ID " + id + " не найдена.");
    }

    @Override
    public void deleteCategory(String name) {
        for (Category category : Categories) {
            if (category.name.equals(name)) {
                Categories.remove(category);
                System.out.println("Категория \"" + name + "\" удалена.");
                return;
            }
        }
        System.out.println("Категория \"" + name + "\" не найдена.");
    }

    @Override
    public void get_Category_info(int id) {
        for (Category category : Categories) {
            if (category.id == id) {
                System.out.println("ID: " + category.id + " Name: " + category.name + " isExpenditure: " + category.isExpenditure);
                break;
            }
        }
    }

    @Override
    public Category get_Category(int id) {
        for (Category category : Categories) {
            if (category.id == id) {
                return category;
            }
        }
        return null;
    }

    @Override
    public void change_Category_name(int id, String new_name) {
        for (Category category : Categories) {
            if (category.id == id) {
                category.name = new_name;
                System.out.println("Имя категории с ID " + id + " изменено.");
                return;
            }
        }
        System.out.println("Категория с ID " + id + " не найдена.");
    }


    // I_Operation_facade
    @Override
    public void createOperation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id, String descr) {
        Operation newOperation = new Operation(is_exp, ba_id, Sum, Time, cat_id, descr);
        Operations.add(newOperation);

        double diff = is_exp ? -Sum : Sum;
        add_to_balance(ba_id, diff);

        System.out.println("Операция (ID: " + newOperation.getId() + ") создана и баланс счета обновлен.");
    }

    @Override
    public void createOperation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id) {
        Operation newOperation = new Operation(is_exp, ba_id, Sum, Time, cat_id);
        Operations.add(newOperation);

        double diff = is_exp ? -Sum : Sum;
        add_to_balance(ba_id, diff);
        System.out.println("Операция создана и баланс счета обновлен.");
    }

    @Override
    public void deleteOperation(int id) {
        for (Operation operation : Operations) {
            if (operation.id == id) {

                double rollBackDiff = operation.isExpenditure ? operation.sum : -operation.sum;
                add_to_balance(operation.BA_id, rollBackDiff);

                Operations.remove(operation);
                System.out.println("Операция с ID " + id + " удалена.");
                return;
            }
        }
        System.out.println("Операция с ID " + id + " не найдена.");
    }

    @Override
    public void deleteOperation(int BA_id, LocalDateTime Time) {
        for (Operation operation : Operations) {
            if (operation.BA_id == BA_id && operation.time.equals(Time)) {

                double rollbackDiff = operation.isExpenditure ? operation.sum : -operation.sum;
                add_to_balance(operation.BA_id, rollbackDiff);

                Operations.remove(operation);
                System.out.println("Операция, совершенная \"" + Time + "\" удалена.");
                return;
            }
        }
        System.out.println("Операция, совершенная \"" + Time + "\" не найдена.");
    }

    @Override
    public void get_Operation_info(int id) {
        for (Operation operation : Operations) {
            if (operation.id == id) {
                System.out.println("ID: " + operation.id + " isExpenditure: " + operation.isExpenditure + " BA_id: " + operation.BA_id + " Sum: " + operation.sum + " Time: " + operation.time + " Category_id: " + operation.category_id + " Description: " + operation.description);
                return;
            }
        }
        System.out.println("Операция с ID " + id + " не найдена.");
    }

    @Override
    public Operation get_Operation(int id) {
        for (Operation operation : Operations) {
            if (operation.id == id) {
                return operation;
            }
        }
        return null;
    }

    @Override
    public void change_description(int id, String new_description) {
        for (Operation operation : Operations) {
            if (operation.id == id) {
                operation.description = new_description;
                System.out.println("Описание операции с ID " + id + " изменено.");
                return;
            }
        }
        System.out.println("Операция с ID " + id + " не найдена.");
    }

    @Override
    public void change_description(int BA_id, LocalDateTime Time, String new_description) {
        for (Operation operation : Operations) {
            if (operation.BA_id == BA_id && operation.time.equals(Time)) {
                operation.description = new_description;
                System.out.println("Описание операции, совершенной аккаунтом " + BA_id + " в " + Time + " изменено.");
                return;
            }
        }
        System.out.println("Операция, совершенной аккаунтом " + BA_id + " в " + Time + " не найдена.");
    }

    //Аналитика
    public double calculateNetIncome (LocalDateTime start, LocalDateTime end) {
        double income = 0;
        for (Operation operation : Operations) {
            if (!operation.isExpenditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                income += operation.sum;
            }
        }
        return income;
    }

    public double calculateNetExpenditures(LocalDateTime start, LocalDateTime end) {
        double expenditure = 0;
        for (Operation operation : Operations) {
            if (operation.isExpenditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                expenditure += operation.sum;
            }
        }
        return expenditure;
    }


    public double calculateNetProfit(LocalDateTime start, LocalDateTime end) {
        return calculateNetIncome(start, end) - calculateNetExpenditures(start, end);
    }
    public void printNetProfit(LocalDateTime start, LocalDateTime end) {
        double profit = calculateNetProfit(start, end);
        System.out.println("Чистая прибыль за период: " + profit);
    }

    public void printNetIncome(LocalDateTime start, LocalDateTime end) {
        System.out.println("Доход за период с " + start + " по " + end + ": " + calculateNetIncome(start, end));
    }


    public Map<Integer, Double> groupOperationsByCategory(LocalDateTime start, LocalDateTime end) {
        Map<Integer, Double> result = new HashMap<>();
        for (Operation operation : Operations) {
            if (operation.time.isAfter(start) && operation.time.isBefore(end)) {
                if (result.containsKey(operation.category_id)) {
                    result.put(operation.category_id, result.get(operation.category_id) + operation.sum);
                } else {
                    result.put(operation.category_id, operation.sum);
                }
            }
        }
        return result;
    }

    public double calculateAccountIncome(int id, LocalDateTime start, LocalDateTime end) {
        double income = 0;
        for (Operation operation : Operations) {
            if (operation.BA_id == id && !operation.isExpenditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                income += operation.sum;
            }
        }
        return income;
    }

    public double calculateAccountExpenditure(int id, LocalDateTime start, LocalDateTime end) {
        double expenditure = 0;
        for (Operation operation : Operations) {
            if (operation.BA_id == id && operation.isExpenditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                expenditure += operation.sum;
            }
        }
        return expenditure;
    }

    public double calculateAccountProfit(int id, LocalDateTime start, LocalDateTime end) {
        return calculateAccountIncome(id, start, end) - calculateAccountExpenditure(id, start, end);
    }

    public Map<Integer, Double> groupOpsByCatForAccount(int id, LocalDateTime start, LocalDateTime end) {
        Map<Integer, Double> result = new HashMap<>();
        for (Operation operation : Operations) {
            if (operation.BA_id == id && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                if (result.containsKey(operation.category_id)) {
                    result.put(operation.category_id, result.get(operation.category_id) + operation.sum);
                } else {
                    result.put(operation.category_id, operation.sum);
                }
            }
        }
        return result;
    }

    //Работа с файлами и данными
    public void exportData(my_files.exports.ExportVisitor visitor) {
        System.out.println("Экспорт данных");
        for (BA account : Accounts) {
            account.accept(visitor);
        }
        for (Category category : Categories) {
            category.accept(visitor);
        }
        for (Operation operation : Operations) {
            operation.accept(visitor);
        }
    }

    public void clearData() {
        Accounts.clear();
        Categories.clear();
        Operations.clear();
    }

    public List<BA> getAccounts() {
        return Accounts;
    }

    public List<Category> getCategories() {
        return Categories;
    }

    public List<Operation> getOperations() {
        return Operations;
    }
}
