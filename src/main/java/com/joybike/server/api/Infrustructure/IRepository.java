package com.joybike.server.api.Infrustructure;

import java.util.List;
import java.util.Map;

/**
 * Created by 58 on 2016/10/12.
 */
public interface IRepository<T> {

    public long save(T model);

    public int update(T model);

    public void delete(Long id) ;

    public int execSQL(String sql, Map map);

    public T findById(Long id);

    public T findById(T model);

    public T getModel(String sql, Object[] params);

    public List<T> query(String sql,Object[] parms);

    public List<T> getList(String sql, Map paramValue);

    public int getCount(Map<String, String> where);
}
