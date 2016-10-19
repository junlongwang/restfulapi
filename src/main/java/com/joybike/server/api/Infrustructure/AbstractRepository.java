package com.joybike.server.api.Infrustructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by 58 on 2016/10/12.
 */
public abstract class AbstractRepository<T> implements IRepository<T> {

    protected Class<T> entityClass;

    public NamedParameterJdbcTemplate jdbcTemplate;


    public static final String SQL_INSERT = "insert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_DELETE = "delete";


    static String INSERT_SQL = null;
    static String UPDATE_SQL = null;
    static String DELETE_SQL = null;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    @SuppressWarnings("unchecked")
    public AbstractRepository() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        System.out.println("Dao实现类T是：" + entityClass.getName());

        INSERT_SQL = makeSql(SQL_INSERT);
        UPDATE_SQL = makeSql(SQL_UPDATE);
        DELETE_SQL = makeSql(SQL_DELETE);
    }

    public abstract long save(T model);

    public abstract int update(T model);

    public abstract int update(String namedSql, T javaBean);

    public abstract void delete(Long id);

    public abstract int execSQL(String sql, Map map);

    public abstract T findById(Long id);

    public abstract T findById(T model);

    public abstract T getModel(String sql, Object[] params);

    public abstract List<T> query(String sql, Object[] parms);

    public abstract List<T> getList(String sql, Map paramValue);

    public abstract int getCount(Map<String, String> where);

    public abstract int getCount(String sql ,Map map);



    // 生成基础SQL
    private String makeSql(String sqlFlag) {
        StringBuffer sql = new StringBuffer();
        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            sql.append(" INSERT INTO " + entityClass.getSimpleName());
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = fields[i].getName();
                if (column.equals("id")) { // id 代表主键
                    continue;
                }
                sql.append(column).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            for (int i = 0; fields != null && i < fields.length; i++) {
                if (fields[i].getName().equals("id")) { // id 代表主键
                    continue;
                }
                sql.append(MessageFormat.format(":{0},", fields[i].getName()));

                //sql.append("?,");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            sql.append(" UPDATE " + entityClass.getSimpleName() + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = fields[i].getName();
                if (column.equals("id")) { // id 代表主键
                    continue;
                }
                //sql.append(column).append("=").append("?,");

                sql.append(column).append("=").append(MessageFormat.format(":{0},", fields[i].getName()));
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id=:id");
        } else if (sqlFlag.equals(SQL_DELETE)) {
            sql.append(" DELETE FROM " + entityClass.getSimpleName() + " WHERE id=:id");
        }
        System.out.println("SQL=" + sql);
        return sql.toString();

    }
}
