package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.model.subscribeInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Repository("SubscribeInfoDao")
public class SubscribeInfoDaoImpl extends Reository<subscribeInfo> implements SubscribeInfoDao {


    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    final String subscribeInfoSql = "select * from subscribeInfo where vehicleId = :vehicleId";

    @Override
    public subscribeInfo getSubscribeInfo( String vehicleId) {
        Map map = new HashMap();
        map.put("vehicleId", vehicleId);
        try {
            return (subscribeInfo) this.jdbcTemplate.queryForObject(subscribeInfoSql, map, new BeanPropertyRowMapper(subscribeInfo.class));
        } catch (Exception e) {
            return null;
        }


    }

    /**
     * 删除车辆预约信息,两种情况，1:取消预约，2:到达15分钟预约时间
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    final String deleteSubscribeInfoSql = "delete from subscribeInfo where userId = :userId and vehicleId = :vehicleId";

    @Override
    public int deleteSubscribeInfo(long userId, String vehicleId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("vehicleId", vehicleId);
        return execSQL(deleteSubscribeInfoSql, map);
    }

    /**
     * 修改预约状态
     *
     * @param userId
     * @param vehicleId
     * @param subscribeStatus
     * @return
     */
    final String updateSubscribeInfoSql = "update subscribeInfo set status = :status where userId = :userId and vehicleId = :vehicleId";

    @Override
    public int updateSubscribeInfo(long userId, String vehicleId, SubscribeStatus subscribeStatus) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("vehicleId", vehicleId);
        map.put("status", subscribeStatus.getValue());
        return execSQL(updateSubscribeInfoSql, map);
    }


    /**
     * 根据车辆ID获取当前使用信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    final String getSubscribeInfoByVehicleId = "select * from subscribeInfo where vehicleId = :vehicleId and status = :status";

    @Override
    public subscribeInfo getSubscribeInfoByVehicleId(String vehicleId, SubscribeStatus subscribeStatus) {
        Map map = new HashMap();
        map.put("vehicleId", vehicleId);
        map.put("status", subscribeStatus.getValue());
        try {
            return (subscribeInfo) this.jdbcTemplate.queryForObject(getSubscribeInfoByVehicleId, map, new BeanPropertyRowMapper(subscribeInfo.class));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 根据用户信息查找预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    final String getSubscribeInfoByUserId = "select * from subscribeInfo where userId = :userId";

    @Override
    public subscribeInfo getSubscribeInfoByUserId(long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        try {
            return (subscribeInfo) this.jdbcTemplate.queryForObject(getSubscribeInfoByUserId, map, new BeanPropertyRowMapper(subscribeInfo.class));
        } catch (Exception e) {
            return null;
        }
    }

//    /**
//     * 根据预约code获取预约订单
//     *
//     * @param userId
//     * @param vehicleId
//     * @return
//     */
//    final String getSubscribeInfoByCode = "select * from subscribeInfo where subscribeCode = :subscribeCode";
//
//    @Override
//    public subscribeInfo getSubscribeInfoByCode(long userId, String vehicleId) {
//        String subscribeCode = String.valueOf(userId) + String.valueOf(vehicleId);
//        Map map = new HashMap();
//        map.put("subscribeCode", subscribeCode);
//        try {
//            return (subscribeInfo) this.jdbcTemplate.queryForObject(getSubscribeInfoByCode, map, new BeanPropertyRowMapper(subscribeInfo.class));
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
