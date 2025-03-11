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
    }

    @Override
    public void deleteBA(int id) {
        for (BA account : Accounts) {
            if (account.id == id) {
                Accounts.remove(account);
                break;
            }
        }
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
                break;
            }
        }
    }

    // I_Category_facade
    @Override
    public void createCategory(String name, boolean is_expenditure) {
        Category newCategory = new Category(name, is_expenditure);
        Categories.add(newCategory);
    }

    @Override
    public void deleteCategory(int id) {
        for (Category category : Categories) {
            if (category.id == id) {
                Categories.remove(category);
                break;
            }
        }
    }

    @Override
    public void deleteCategory(String name) {
        for (Category category : Categories) {
            if (category.name.equals(name)) {
                Categories.remove(category);
                break;
            }
        }
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
                break;
            }
        }
    }


    // I_Operation_facade
    @Override
    public void createOperation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id, String descr) {
        Operation newOperation = new Operation(is_exp, ba_id, Sum, Time, cat_id, descr);
        Operations.add(newOperation);

        double diff = is_exp ? -Sum : Sum;
        add_to_balance(ba_id, diff);
    }

    @Override
    public void createOperation(boolean is_exp, int ba_id, double Sum, LocalDateTime Time, int cat_id) {
        Operation newOperation = new Operation(is_exp, ba_id, Sum, Time, cat_id);
        Operations.add(newOperation);

        double diff = is_exp ? -Sum : Sum;
        add_to_balance(ba_id, diff);
    }

    @Override
    public void deleteOperation(int id) {
        for (Operation operation : Operations) {
            if (operation.id == id) {
                Operations.remove(operation);
                break;
            }
        }
    }

    @Override
    public void deleteOperation(int BA_id, LocalDateTime Time) {
        for (Operation operation : Operations) {
            if (operation.BA_id == BA_id && operation.time.equals(Time)) {
                Operations.remove(operation);
                break;
            }
        }
    }

    @Override
    public void get_Operation_info(int id) {
        for (Operation operation : Operations) {
            if (operation.id == id) {
                System.out.println("ID: " + operation.id + " isExpenditure: " + operation.isExpemditure + " BA_id: " + operation.BA_id + " Sum: " + operation.sum + " Time: " + operation.time + " Category_id: " + operation.category_id + " Description: " + operation.description);
                break;
            }
        }
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
                break;
            }
        }
    }

    @Override
    public void change_description(int BA_id, LocalDateTime Time, String new_description) {
        for (Operation operation : Operations) {
            if (operation.BA_id == BA_id && operation.time.equals(Time)) {
                operation.description = new_description;
                break;
            }
        }
    }

    //Аналитика
    public double calculateNetIncome (LocalDateTime start, LocalDateTime end) {
        double income = 0;
        for (Operation operation : Operations) {
            if (!operation.isExpemditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                income += operation.sum;
            }
        }
        return income;
    }

    public double calculateNetExpenditures(LocalDateTime start, LocalDateTime end) {
        double expenditure = 0;
        for (Operation operation : Operations) {
            if (operation.isExpemditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                expenditure += operation.sum;
            }
        }
        return expenditure;
    }


    public double calculateNetProfit(LocalDateTime start, LocalDateTime end) {
        return calculateNetIncome(start, end) - calculateNetExpenditures(start, end);
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
            if (operation.BA_id == id && !operation.isExpemditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
                income += operation.sum;
            }
        }
        return income;
    }

    public double calculateAccountExpenditure(int id, LocalDateTime start, LocalDateTime end) {
        double expenditure = 0;
        for (Operation operation : Operations) {
            if (operation.BA_id == id && operation.isExpemditure && operation.time.isAfter(start) && operation.time.isBefore(end)) {
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
        System.err.println("ПОКА ВООБЩЕ НЕ РЕАЛИЗОВАНО - ПО СУТИ, ЭТО ЭКСПОРТ В КОНСОЛЬ");
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
}
