package me.ianguuima.dao;

public interface Dao<T> {

    T getMessage(int id);
    void insert(String message);
    void update(String newmessage, int id);
    void delete(T classe);


}
