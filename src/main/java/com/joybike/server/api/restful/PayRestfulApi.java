package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.impl.ThirdPayServiceImpl;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.thirdparty.wxtenpay.util.WxDealUtil;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LongZiyuan on 2016/10/16.
 */

//"/api/pay"
@RequestMapping("/pay")
@RestController()
public class PayRestfulApi {

    @Autowired
    private PayRestfulService payRestfulService;
    @Autowired
    private ThirdPayService ThirdPayService;
    @Autowired
    private OrderRestfulService orderRestfulService;

    /**
     * 充值：可充值押金、预存现金
     *
     * @param payBean
     * @return
     */
    @RequestMapping(value = "deposit",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> deposit(@RequestBody ThirdPayBean payBean,@RequestParam("userId") long userId) {
        if (payBean != null && String.valueOf(userId) != null) {
            if (payBean.getRechargeType() == 1) {
                try {
                    String rechargeResult = forRecharge(payBean, userId);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, rechargeResult));
                } catch (Exception e) {
                    return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Recharge_Error.getErrorCode(), ReturnEnum.BankDepositOrderList_Error.getErrorDesc() + "-" + e.getMessage(), null));
                }
            } else {
                try {
                    String rechargeResult = recharge(payBean, userId);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, rechargeResult));
                } catch (Exception e) {
                    return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Recharge_Error.getErrorCode(), ReturnEnum.BankDepositOrderList_Error.getErrorDesc() + "-" + e.getMessage(), null));
                }
            }
        }
        return ResponseEntity.ok(new Message<String>(false,ReturnEnum.Recharge_Error.getErrorCode(),ReturnEnum.BankDepositOrderList_Error.getErrorDesc(),"payBean或userid为空"));
    }


    @RequestMapping(value = "paynotify", method = RequestMethod.POST)
    public String payOfNotify(@RequestBody HttpServletRequest request) {
        String responseHtml = "success";
        String channleId = request.getParameter("attach");
        String returncode = "";
        if (request.getParameter("transaction_id") != null || request.getParameter("trade_no") != null) {
            returncode = ThirdPayService.callBack(request);
        }
        if (returncode != null) {
            if (channleId.equals("117")) {
                responseHtml = WxDealUtil.notifyResponseXml();
                String out_trade_no = request.getParameter("out_trade_no");
                long id = Long.valueOf(out_trade_no);
                String payDocumentId = request.getParameter("transaction_id");
                String merchantId = "";
                int pay_at = UnixTimeUtils.StringDateToInt(request.getParameter("time_end"));
                try {
                    int result = payRestfulService.updateDepositOrderById(id, PayType.weixin, payDocumentId, merchantId, pay_at);
                    if (result > 0) {
                        return responseHtml;
                    }
                } catch (Exception e) {
                    return "";
                }
            } else if (channleId.equals("118")) {
                responseHtml = WxDealUtil.notifyResponseXml();
                String out_trade_no = request.getParameter("out_trade_no");
                long id = Long.valueOf(out_trade_no);
                String payDocumentId = request.getParameter("transaction_id");
                String merchantId = "";
                int pay_at = UnixTimeUtils.StringDateToInt(request.getParameter("time_end"));
                try {
                    int result = payRestfulService.updateDepositOrderById(id, PayType.weixin, payDocumentId, merchantId, pay_at);
                    if (result > 0) {
                        return responseHtml;
                    }
                } catch (Exception e) {
                    return "";
                }
            } else {
                String out_trade_no = request.getParameter("out_trade_no");
                long id = Long.valueOf(out_trade_no);
                String payDocumentId = request.getParameter("transaction_id");
                String merchantId = "";
                int pay_at = UnixTimeUtils.StringDateToInt(request.getParameter("time_end"));
                try {
                    int result = payRestfulService.updateDepositOrderById(id, PayType.weixin, payDocumentId, merchantId, pay_at);
                    if (result > 0) {
                        return responseHtml;
                    }
                } catch (Exception e) {
                    return "";
                }
            }
        }
        return "";
    }

    /**
     * 获取消费明细
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getConsumeLogs", method = RequestMethod.GET)
    public ResponseEntity<Message<List<bankConsumedOrder>>> getConsumeLogs(@RequestParam("userId") long userId) {
        try {
            List<bankConsumedOrder> list = payRestfulService.getBankConsumedOrderList(userId);
            return ResponseEntity.ok(new Message<List<bankConsumedOrder>>(true, 0, null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<bankConsumedOrder>>(false, ReturnEnum.ConsumedOrderList_Error.getErrorCode(), ReturnEnum.ConsumedOrderList_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }

    }

    /**
     * 获取充值明细
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getDepositLogs", method = RequestMethod.GET)
    public ResponseEntity<Message<List<bankDepositOrder>>> getDepositLogs(@RequestParam("userId") long userId) {

        try {
            List<bankDepositOrder> list = payRestfulService.getBankDepositOrderList(userId);
            return ResponseEntity.ok(new Message<List<bankDepositOrder>>(true, 0, null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<bankDepositOrder>>(false, ReturnEnum.BankDepositOrderList_Error.getErrorCode(), ReturnEnum.BankDepositOrderList_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 押金退款
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "refund", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> refund(@RequestParam("userId") long userId) {
        if(String.valueOf(userId) != null){
            return ResponseEntity.ok(new Message<String>(true, 0,null, "押金退款已经受理，后续状态在48小时内注意查看系统消息！"));
        }
        return ResponseEntity.ok(new Message<String>(false, ReturnEnum.refund_Error.getErrorCode(),ReturnEnum.refund_Error.getErrorDesc(), "退款失败"));
    }

    public String forRecharge(ThirdPayBean payBean, long userId) {
        bankDepositOrder order = createDepositRechargeOrder(payBean, userId);
        if (order != null && order.getId() != null) {
            payBean.setId(order.getId());
            return ThirdPayService.execute(payBean);
        }
        return null;
    }

    public String recharge(ThirdPayBean payBean, long userId){
        bankDepositOrder order = createRechargeOrder(payBean, userId);
        if (order != null && order.getId() != null) {
            payBean.setId(order.getId());
            return ThirdPayService.execute(payBean);
        }
        return null;
    }

    public bankDepositOrder createRechargeOrder(ThirdPayBean payBean, long userId) {
        bankDepositOrder order = new bankDepositOrder();
        order.setUserId(userId);
        order.setCash(payBean.getOrderMoney());
        order.setAward(payBean.getOrderMoneyFree());
        order.setPayType(payBean.getChannelId());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Integer creatTime = Integer.valueOf(date.format(new Date()));
        order.setCreateAt(creatTime);
        order.setRechargeType(payBean.getRechargeType());
        try {
            payRestfulService.depositRecharge(order);
            return order;
        } catch (Exception e) {
            return null;
        }
    }

    public bankDepositOrder createDepositRechargeOrder(ThirdPayBean payBean, long userId) {
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
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取产品列表
     *
     * @return
     */
    @RequestMapping(value = "product", method = RequestMethod.GET)
    public ResponseEntity<Message<List<product>>> productList() {
        try {
            List<product> list = orderRestfulService.getProductList();
            return ResponseEntity.ok(new Message<List<product>>(true, 0, null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<product>>(false, ReturnEnum.Product_Error.getErrorCode(), ReturnEnum.Product_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }
}
