package cn.fcars.infomgr.service;


import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T,T1>{
    T findByID(Integer id);
    T findByStringID(String id) throws Exception;
    T findByName(String name);

    List<T> findAll();
    List<T> findByQuery(T1 data);
    PageInfo<T> findByPage(T1 data);
    Integer check(String name);
    boolean checkExist(String id,String name);
    void add(T data) throws Exception;
    void update(T data) throws Exception;
    void deleteByID(Integer id) throws Exception;
    int delete(T data) throws Exception;
}
