package my_files.service;

import my_files.model.Category;

// создание, изменение, получение, удаление
public interface I_Category_facade {
    public void createCategory(String name, boolean is_expenditure);

    public void deleteCategory(int id);
    public void deleteCategory(String name);

    public void get_Category_info(int id);
    public Category get_Category(int id); // отдаем ссылку на объект

    public void change_Category_name(int id, String new_name);
}
