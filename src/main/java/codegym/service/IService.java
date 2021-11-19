package codegym.service;

import java.util.List;

public interface IService<T> {
    void insert(T t);

    T findById(int id);

    boolean update(T t);

    boolean delete(int id);

    List<T> findAll();
}
