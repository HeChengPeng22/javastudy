package com.hcp;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T> {
    int insert(T entity);

    int updateById(Serializable id);

    int deleteById(Serializable id);

    T selectById(Serializable id);

    List<T> selectList();
}
