package com.joybike.server.api.restful;

import com.joybike.server.api.ThirdPayService.IThirdPayService;
import com.joybike.server.api.ThirdPayService.impl.ThirdPayServiceImpl;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.PayRestfulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 58 on 2016/10/16.
 */
//"/api/pay"
@RestController()
public class PayRestfulApi {

    @Autowired
    private PayRestfulService payRestfulService;
    @Autowired
    private IThirdPayService iThirdPayService;

    /**
     * 充值：可充值押金、预存现金
     * @param payBean
     * @return
     */
    @RequestMapping(value = "deposit",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> deposit(@RequestBody ThirdPayBean payBean,@RequestParam("userId") long userId)
    {
        String rechargeResult = forRecharge(payBean, userId);
        return ResponseEntity.ok(new Message<String>(true,null,"牛逼"));
    }

    /**
     * 获取消费明细
     * @param userId
     * @return
     */
    @RequestMapping(value = "getConsumeLogs",method = RequestMethod.GET)
    public ResponseEntity<Message<List<bankConsumedOrder>>> getConsumeLogs(@RequestParam("userId") long userId) {
        return ResponseEntity.ok(new Message<List<bankConsumedOrder>>(true, null, new ArrayList<bankConsumedOrder>()));
    }

    /**
     * 获取充值明细
     * @param userId
     * @return
     */
    @RequestMapping(value = "getDepositLogs",method = RequestMethod.GET)
    public ResponseEntity<Message<List<bankDepositOrder>>> getDepositLogs(@RequestParam("userId") long userId) {
        return ResponseEntity.ok(new Message<List<bankDepositOrder>>(true, null, new ArrayList<bankDepositOrder>()));
    }

    @RequestMapping(value = "refund",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> refund(@RequestParam("userId") long userId)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"押金退款已经受理，后续状态在48小时内注意查看系统消息！"));
    }

    public String forRecharge(ThirdPayBean payBean, long userId){
        bankDepositOrder order = createDepositRechargeOrder(payBean,userId);
        if(order != null && order.getId() != null){
            payBean.setId(order.getId());
            return iThirdPayService.execute(payBean);
        }
        return null;
    }

    public bankDepositOrder createDepositRechargeOrder(ThirdPayBean payBean,long userId){
        bankDepositOrder order = new bankDepositOrder();
        order.setUserId(userId);
        order.setCash(payBean.getOrderMoney());
        order.setPayType(payBean.getChannelId());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer creatTime = Integer.valueOf(date.format(new Date()));
        order.setCreateAt(creatTime);
        order.setRechargeType(payBean.getRechargeType());
        try {
            payRestfulService.depositRecharge(order);
            return order;
        }catch (Exception e){
            return null;
        }
    }
}
