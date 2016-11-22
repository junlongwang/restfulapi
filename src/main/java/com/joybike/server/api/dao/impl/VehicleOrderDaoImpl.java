package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Repository("VehicleOrderDao")
public class VehicleOrderDaoImpl extends Reository<vehicleOrder> implements VehicleOrderDao {


    /**
     * 修改订单code
     *
     * @param id
     * @return
     */
    final String updateOrderCodeSql = "update vehicleOrder set orderCode = :orderCode where id = :id";

    @Override
    public int updateOrderCode(long id, String orderCode) throws Exception {

        try {
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            map.put("id", id);
            return execSQL(updateOrderCodeSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取没有支付的用户
     *
     * @param vehicleId
     * @return
     */
    final String getNoPayByUserIdSql = "select * from vehicleOrder where userId = :userId and status = :status";

    @Override
    public vehicleOrder getNoPayByUserId(long userId,OrderStatus orderStatus) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("status", orderStatus.getValue());

            try {
                return (vehicleOrder) this.jdbcTemplate.queryForObject(getNoPayByUserIdSql, map, new BeanPropertyRowMapper(vehicleOrder.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }

    }

    /**
     * 锁车时修改订单
     *
     * @param userId
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @return
     * @throws Exception
     */
    final String updateOrderByLockSql = "update vehicleOrder set beforePrice = :beforePrice,afterPrice = :afterPrice,status = :status where userId = :userId and vehicleId = :vehicleId";

    @Override
    public int updateOrderByLock(long userId, String bicycleCode, BigDecimal beforePrice) throws Exception {
        Map orderMap = new HashMap();
        orderMap.put("beforePrice", beforePrice);
        orderMap.put("status", OrderStatus.end.getValue());
        orderMap.put("userId", userId);
        orderMap.put("afterPrice", beforePrice);
        orderMap.put("vehicleId", bicycleCode);
        return execSQL(updateOrderByLockSql, orderMap);
    }

    /**
     * 修改订单支付状态
     *
     * @param id
     * @return
     */
    final String updateByIdSql = "update vehicleOrder set status = :status,payId = :payId where orderCode = :orderCode";

    @Override
    public int updateStatausByCode(String orderCode,long payId) throws Exception{
        Map orderMap = new HashMap();
        orderMap.put("orderCode", orderCode);
        orderMap.put("status", OrderStatus.complete.getValue());
        orderMap.put("payId",payId);
        return execSQL(updateByIdSql, orderMap);
    }

    /**
     * 根据用户ID跟code获取未支付订单
     *
     * @param userId
     * @param orderCode
     * @return
     * @throws Exception
     */
    final String getNoPayByOrder = "select * from vehicleOrder where userId = :userId and orderCode = :orderCode and status = :status";

    @Override
    public vehicleOrder getNoPayByOrder(long userId, String orderCode) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("orderCode", orderCode);
            map.put("status", OrderStatus.end.getValue());

            try {
                return (vehicleOrder) this.jdbcTemplate.queryForObject(getNoPayByOrder, map, new BeanPropertyRowMapper(vehicleOrder.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取用户已完成的骑行订单 已支付完成的
     *
     * @param userId
     * @return
     */
    final String getOrderPaySuccessSql = " select a.id,a.orderCode,a.userId,a.beforePrice,a.afterPrice,a.payId,a.status,a.vehicleId,b.beginAt,b.endAt,b.beginDimension,b.beginLongitude,b.endDimension,b.endLongitude,b.cyclingTime,b.cyclingImg,b.tripDist,b.startAddress,b.endAddress from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
            " where a.userId = ? and a.status = 15";

    @Override
    public List<VehicleOrderDto> getOrderPaySuccess(long userId) throws Exception{
        try {
            Object[] object = new Object[]{userId};
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getOrderPaySuccessSql, object, new BeanPropertyRowMapper(VehicleOrderDto.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取用户已完成的骑行订单 已支付完成的last
     *
     * @param userId
     * @return
     */
    final String getLastOrderPaySuccessSql = " select a.id,a.orderCode,a.userId,a.beforePrice,a.afterPrice,a.payId,a.status,a.vehicleId,b.beginAt,b.endAt,b.beginDimension,b.beginLongitude,b.endDimension,b.endLongitude,b.cyclingTime,b.cyclingImg,b.tripDist,b.startAddress,b.endAddress from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
            " where a.userId = :userId order by endAt desc limit 1";

    @Override
    public VehicleOrderDto getLastOrderPaySuccess(long userId) throws Exception{
        try {
            Map map = new HashMap();
            map.put("userId", userId);

            try {
                return (VehicleOrderDto) this.jdbcTemplate.queryForObject(getLastOrderPaySuccessSql, map, new BeanPropertyRowMapper(VehicleOrderDto.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 根据主键获取订单信息
     *
     * @param id
     * @return
     */
    final String getOrderByidSql = "SELECT * FROM vehicleOrder WHERE id = :id";
    @Override
    public vehicleOrder getOrderByid(long id) throws Exception{
        try {
            Map map = new HashMap();
            map.put("id", id);

            try {
                return (vehicleOrder) this.jdbcTemplate.queryForObject(getOrderByidSql, map, new BeanPropertyRowMapper(vehicleOrder.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取用户已完成的骑行订单 已支付完成的
     *
     * @param userId
     * @return
     */
    final String getOrderByIdSql = " select a.id,a.orderCode,a.userId,a.beforePrice,a.afterPrice,a.payId,a.status,a.vehicleId,b.beginAt,b.endAt,b.beginDimension,b.beginLongitude,b.endDimension,b.endLongitude,b.cyclingTime,b.startAddress,b.endAddress from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
                    " where a.id = :id";

    @Override
    public VehicleOrderDto getOrderById(long id) throws Exception{
        try {
            Map map = new HashMap<>();
            map.put("id",id);
            try {
                return (VehicleOrderDto) this.jdbcTemplate.queryForObject(getOrderByIdSql, map, new BeanPropertyRowMapper(VehicleOrderDto.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }


    /**
     * 获取用户正在使用与已完成的订单
     *
     * @param userId
     * @return
     */
    final String getOrderByUserIdSql = " select a.id,a.orderCode,a.userId,a.beforePrice,a.afterPrice,a.payId,a.status,a.vehicleId,b.beginAt,b.endAt,b.beginDimension,b.beginLongitude,b.endDimension,b.endLongitude,b.cyclingTime,b.startAddress,b.endAddress from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
            " where a.userId = :userId and a.status in (1,2)";

    @Override
    public VehicleOrderDto getOrderByUserId(long userId) throws Exception{
        try {
            Map map = new HashMap<>();
            map.put("userId",userId);
            try {
                return (VehicleOrderDto) this.jdbcTemplate.queryForObject(getOrderByUserIdSql, map, new BeanPropertyRowMapper(VehicleOrderDto.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 根据订单code获取订单信息
     *
     * @param userId
     * @return
     */
    final String getOrderByOrderCode = " select a.id,a.orderCode,a.userId,a.beforePrice,a.afterPrice,a.payId,a.status,a.vehicleId,b.beginAt,b.endAt,b.beginDimension,b.beginLongitude,b.endDimension,b.endLongitude,b.cyclingTime,b.startAddress,b.endAddress from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
            " where a.orderCode = :orderCode";

    @Override
    public VehicleOrderDto getOrderByOrderCode(String orderCode) throws Exception{
        try {
            Map map = new HashMap<>();
            map.put("orderCode",orderCode);
            try {
                return (VehicleOrderDto) this.jdbcTemplate.queryForObject(getOrderByOrderCode, map, new BeanPropertyRowMapper(VehicleOrderDto.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取用户已完成订单的总里程
     * @param userId
     * @return
     * @throws Exception
     */
    final String getTripDistSql = "select sum(b.tripDist) tripDist from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
            " where a.userId = :userId and a.status = 15";
    @Override
    public BigDecimal getTripDist(long userId) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            try{
                return this.jdbcTemplate.queryForObject(getTripDistSql, map, BigDecimal.class);

            }catch (Exception e){
                return BigDecimal.valueOf(0);
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }

    }

    /**
     * 获取用户已完成订单的总里程
     * @param userId
     * @return
     * @throws Exception
     */
    final String getTimesSql = "select sum(b.cyclingTime) cyclingTime from vehicleorder a join orderItem b on (a.orderCode = b.orderCode) " +
            " where a.userId = :userId and a.status = 15";
    @Override
    public Integer getTimes(long userId) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            try{
                return this.jdbcTemplate.queryForObject(getTimesSql, map, Integer.class);

            }catch (Exception e){
                return 0;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }

    }


    /**
     * 根据订单code获取订单信息
     *
     * @param userId
     * @return
     */
    final String getOrderByVehicleId = "select * from vehicleorder where vehicleId = :vehicleId and status = 1 order by id desc limit 1";

    @Override
    public vehicleOrder getOrderByVehicleId(String vehicleId) throws Exception{
        try {
            Map map = new HashMap<>();
            map.put("vehicleId",vehicleId);
            try {
                return (vehicleOrder) this.jdbcTemplate.queryForObject(getOrderByVehicleId, map, new BeanPropertyRowMapper(vehicleOrder.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
