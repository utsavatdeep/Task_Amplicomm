package com.example.demo.dao;

import java.util.List;

public interface DAO<T> {

    public void insert(T t);
    public void delete(String key);
    public Object select(String key);
    public boolean isExists(String key);
    public void update(Object t, Object r);
}
