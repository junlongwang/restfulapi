package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
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
    final String getSubscribeInfoByBicycleCode = "select * from subscribeInfo where vehicleId = :vehicleId and status = :status";

    @Override
    public subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId,SubscribeStatus status) throws Exception {
        try {
            Map map = new HashMap();
            map.put("vehicleId", vehicleId);
            map.put("status",status.getValue());
            try {
                return (subscribeInfo) this.jdbcTemplate.queryForObject(getSubscribeInfoByBicycleCode, map, new BeanPropertyRowMapper(subscribeInfo.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 删除过期
     * @return
     * @throws Exception
     */
    final String deleteByExpireSql = "delete from subscribeinfo where :endAt - createAt > 900 and status <>2";
    @Override
    public int deleteByExpire() throws Exception {
        try {
            Map map = new HashMap();
            map.put("endAt", UnixTimeUtils.now());
            return execSQL(deleteByExpireSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    final String deleteByOrderCodeSql = "delete from subscribeinfo where subscribeCode = :subscribeCode";

    @Override
    public int deleteByOrderCode(String orderCode) {
        try {
            Map map = new HashMap();
            map.put("subscribeCode", orderCode);
            return execSQL(deleteByOrderCodeSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
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
    public int deleteSubscribeInfo(long userId, String vehicleId) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("vehicleId", vehicleId);
            return execSQL(deleteSubscribeInfoSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
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
    public int updateSubscribeInfo(long userId, String vehicleId, SubscribeStatus subscribeStatus) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("vehicleId", vehicleId);
            map.put("status", subscribeStatus.getValue());
            return execSQL(updateSubscribeInfoSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }


    /**
     * 根据预约ID获取预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    final String getSubscribeInfoByIdSql = "select * from subscribeInfo where id = :id";

    @Override
    public subscribeInfo getSubscribeInfoById(long id) throws Exception {
        try {
            Map map = new HashMap();
            map.put("id", id);
            try {
                return (subscribeInfo) this.jdbcTemplate.queryForObject(getSubscribeInfoByIdSql, map, new BeanPropertyRowMapper(subscribeInfo.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }


    /**
     * 根据用户信息查找预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    final String getSubscribeInfoByUserId = "select * from subscribeInfo where userId = :userId and status = :status";

    @Override
    public subscribeInfo getSubscribeInfoByUserId(long userId,SubscribeStatus status) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("status",status.getValue());
            try {
                return (subscribeInfo) this.jdbcTemplate.queryForObject(getSubscribeInfoByUserId, map, new BeanPropertyRowMapper(subscribeInfo.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

}
