package com.joybike.server.api.restful;

import com.joybike.server.api.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 58 on 2016/10/16.
 */
@RequestMapping("/pay")
@RestController()
public class PayRestfulApi {

    /**
     * 充值：可充值押金、预存现金
     * @param depositOrder
     * @return
     */
    @RequestMapping(value = "deposit",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> deposit(@RequestBody bankDepositOrder depositOrder)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"充值成功！"));
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


}
