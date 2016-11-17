package com.joybike.server.api.Infrustructure;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.Object;
import java.util.*;


/**
 * Created by 58 on 2016/10/12.
 */
public class Reository<T> extends AbstractRepository<T> {


    @Override
    public long save(T model) {
        SqlParameterSource ps = new BeanPropertySqlParameterSource(model); //从user中取出数据，与sql语句中一一对应将数据换进去
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, ps, keyHolder);
        long id = keyHolder.getKey().intValue();//获得主键
        return id;
    }

    @Override
    public int update(T model) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(model);
        return this.jdbcTemplate.update(UPDATE_SQL, paramSource);
    }

    @Override
    public int update(String namedSql, T javaBean) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(javaBean);
        return this.jdbcTemplate.update(namedSql, paramSource);
    }

    @Override
    public void delete(Long id) {
        String sql = " DELETE FROM " + entityClass.getSimpleName() + " WHERE id=:id";
        Map<String, Long> map = new HashMap<String, Long>();
        map.put("id", id);
        jdbcTemplate.update(sql, map);
    }

    @Override
    public int execSQL(String sql, Map map) {
        return this.jdbcTemplate.update(sql, map);
    }

    @Override
    public T findById(Long id) {
        String sql = "SELECT * FROM " + entityClass.getSimpleName() + " WHERE id=:id";
        SqlParameterSource ps = null;
        try {
            ps = new BeanPropertySqlParameterSource(entityClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //queryForObject 如果为空就会发生异常
        try {
            return (T) jdbcTemplate.queryForObject(sql, ps, new BeanPropertyRowMapper(entityClass));
        } catch (Exception e) {
            return null;
        }

    }


    @Override
    public T findById(T model) {
        String sql = "SELECT * FROM " + entityClass.getSimpleName() + " WHERE id=:id";
        SqlParameterSource ps = new BeanPropertySqlParameterSource(model);
        try {
            return (T) jdbcTemplate.queryForObject(sql, ps, new BeanPropertyRowMapper(entityClass));
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public T getModel(String sql, Object[] params) {
        try {
            return this.jdbcTemplate.getJdbcOperations().queryForObject(sql, params, entityClass);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<T> query(String sql, Object[] object) {
//        Object stu=jdbcTemplate.queryForObject(sql, object, new BeanPropertyRowMapper(entityClass.getName().getClass()));
        return jdbcTemplate.getJdbcOperations().query(sql, object, new BeanPropertyRowMapper(entityClass.getName().getClass()));

    }

    @Override
    public List<T> getList(String sql, Map paramValue) {
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(entityClass);
        return this.jdbcTemplate.query(sql, paramValue, rowMapper);
    }

    @Override
    public int getCount(Map<String, String> where) {
        return this.count(where);
    }

    @Override
    public int getCount(String sql, Map map) {
        try {
            return jdbcTemplate.queryForObject(sql, map, Integer.class);
        } catch (Exception e) {
            return 0;
        }

    }


    private int count(Map<String, String> where) {
        StringBuffer sql = new StringBuffer(" SELECT COUNT(*) FROM " + entityClass.getSimpleName());
        if (where != null && where.size() > 0) {
            sql.append(" WHERE ");
            for (Map.Entry<String, String> me : where.entrySet()) {
                String columnName = me.getKey();
                String columnValue = me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(" AND "); // 没有考虑or的情况
            }
            int endIndex = sql.lastIndexOf("AND");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }

        try {
            return jdbcTemplate.queryForObject(sql.toString(), new HashMap<String, Object>(), Integer.class);
        } catch (Exception e) {
            return 0;
        }

    }

}
