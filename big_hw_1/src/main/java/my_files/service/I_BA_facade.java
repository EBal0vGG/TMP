package my_files.service;

import my_files.model.BA;

// создание, изменение, получение, удаление
public interface I_BA_facade {
    public void createBA(String name);
    public void deleteBA(int id);

    public void get_BA_info(int id);
    public BA get_ba(int id); // отдаем ссылку на объект

    public void add_to_balance(int id, double diff);
    public void change_BA_name(int id, String new_name);


}
