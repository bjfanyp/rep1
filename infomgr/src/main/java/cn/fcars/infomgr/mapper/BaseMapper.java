package cn.fcars.infomgr.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fanyp on 2018/11/27.
 */
public interface BaseMapper<T,T1> {

    T findByID(@Param("id") Integer id);

    T findByName(@Param("name") String name);

    List<T> findAll();

    List<T> findByQuery(T1 data);

    int add(T t);

    int update(T t);

    int delete(T t);

    int check(String name);

    int checkExist(String id,String userID);

    int deleteByID(@Param("id")Integer id);
}
