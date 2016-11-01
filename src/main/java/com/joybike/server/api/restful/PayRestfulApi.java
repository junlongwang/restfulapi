package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.RechargeType;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.Enum.SecurityStatus;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.impl.ThirdPayServiceImpl;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.dto.RefundDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.wxtenpay.util.WxDealUtil;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import com.joybike.server.api.util.XStreamUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

    private final Logger logger = Logger.getLogger(PayRestfulApi.class);

    @Autowired
    private PayRestfulService payRestfulService;
    @Autowired
    private ThirdPayService ThirdPayService;
    @Autowired
    private OrderRestfulService orderRestfulService;
    @Autowired
    private UserRestfulService userRestfulService;

    private String wxAppmch_id = "1404387302";
    private String wxPubmch_id = "1401808502";
    /**
     * 充值：可充值押金、预存现金
     *
     * @param payBean
     * @return
     */
    @RequestMapping(value = "deposit",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> deposit(@RequestBody ThirdPayBean payBean) {
        long userId= payBean.getUserId();
        if (payBean != null && String.valueOf(userId) != null) {
            //押金充值
            if (payBean.getRechargeType() == 1) {
                try {
                    String rechargeResult = forRecharge(payBean, userId);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, rechargeResult));
                } catch (Exception e) {
                    return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Recharge_Error.getErrorCode(), ReturnEnum.BankDepositOrderList_Error.getErrorDesc() + "-" + e.getMessage(), null));
                }
            } else {
                //余额充值
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

    /**
     * 微信充值回调入口
     * @param request
     * @return
     */
    @RequestMapping(value = "paynotify")
    public String payOfNotify(HttpServletRequest request) {
        DataInputStream in;
        String wxNotifyXml = "";
        try {
            in = new DataInputStream(request.getInputStream());
            byte[] dataOrigin = new byte[request.getContentLength()];
            in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中

            if(null != in) in.close(); // 关闭数据流
            wxNotifyXml = new String(dataOrigin); // 从字节数组中得到表示实体的字符串
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("支付回调通知：" + wxNotifyXml.toString());
        WxNotifyOrder wxNotifyOrder = XStreamUtils.toBean(wxNotifyXml, WxNotifyOrder.class);
        String responseHtml = "success";
        String mch_id = wxNotifyOrder.getMch_id();
        String returncode = "";
        if (wxNotifyOrder.getTransaction_id() != null) {
            returncode = ThirdPayService.callBack(wxNotifyOrder);
        }
        if (returncode.equals("success")) {
            if (mch_id.equals(wxAppmch_id)) {
                responseHtml = WxDealUtil.notifyResponseXml();
                String out_trade_no = wxNotifyOrder.getOut_trade_no();
                long id = Long.valueOf(out_trade_no);
                String payDocumentId = wxNotifyOrder.getTransaction_id();
                String merchantId = "";
                int pay_at = UnixTimeUtils.StringDateToInt(wxNotifyOrder.getTime_end());
                try {
                    int result = 0;
                    bankDepositOrder bankDepositOrder = payRestfulService.getbankDepostiOrderByid(id);
                    if (bankDepositOrder != null){
                        if(bankDepositOrder.getRechargeType() == RechargeType.deposit.getValue()){
                            //通过订单Id修改微信支付凭证和支付时间以及订单支付状态
                            result = payRestfulService.updateDepositOrderById_Yajin(id,payDocumentId,pay_at,2);
                            //同时更新用户状态
                            if (result > 0){
                                userInfo userInfo = new userInfo();
                                userInfo.setId(bankDepositOrder.getUserId());
                                userInfo.setSecurityStatus(SecurityStatus.normal.getValue());
                                userRestfulService.updateUserInfo(userInfo);
                            }
                        }
                        //余额充值成功更新充值订单信息
                        else{
                            result = payRestfulService.updateDepositOrderById(id, PayType.weixin, payDocumentId, merchantId, pay_at);
                        }
                    }
                    String attach = wxNotifyOrder.getAttach();
//                    if(attach != null && attach != ""){
//                        Long consumeid = Long.valueOf(attach);
//                    }
                    if (result > 0) {
                        return responseHtml;
                    }
                } catch (Exception e) {
                    return "";
                }
            } else{
                responseHtml = WxDealUtil.notifyResponseXml();
                String out_trade_no = wxNotifyOrder.getOut_trade_no();
                long id = Long.valueOf(out_trade_no);
                String payDocumentId = wxNotifyOrder.getTransaction_id();
                String merchantId = "";
                int pay_at = UnixTimeUtils.StringDateToInt(wxNotifyOrder.getTime_end());
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
     * @param refundDto
     * @return
     */
    @RequestMapping(value = "refund", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> refund(@RequestBody RefundDto refundDto) {
        if(refundDto.getUserId() > 0){
            bankDepositOrder order = payRestfulService.getDepositOrderId(refundDto.getUserId());
            if(order != null){
                logger.info("充值信息为：" + order.toString() + "的退款开始");
                Long rechargeid = order.getId();
                String payDocumentid = order.getPayDocumentid();
                int channelid = order.getPayType();
                Long refundId = refund(order);
                logger.info("退款订单为：" + refundId + "退款开始");
                if(refundId > 0){
                    ThirdPayBean payBean = new ThirdPayBean();
                    payBean.setOrderMoney(order.getCash());
                    payBean.setChannelId(order.getPayType());
                    payBean.setTransaction_id(order.getPayDocumentid());
                    payBean.setCosumeid(order.getId());
                    payBean.setRefundid(refundId);
                    //调用第三方支付退款操作
                    logger.info("退款订单信息为:" + payBean.toString());
                    String result = ThirdPayService.executeRefund(payBean);
                    logger.info("退款订单id为：" + payBean.getRefundid() + "的退款状态：" + result);
                    if("SUCCSE".equals(result)){
                        int res_uprefund = payRestfulService.updateRefundOrderStatusById(payBean.getRefundid());
                        logger.info("退款订单信息为:" + payBean.getRefundid() + "的退款状态:" + res_uprefund);
                        userInfo user = new userInfo();
                        user.setId(order.getUserId());
                        user.setSecurityStatus(0);
                        int res_upUser = 0;
                        try {
                            res_upUser = userRestfulService.updateUserInfo(user);
                            logger.info("用户ID为:" + order.getUserId() + "的用户状态更新结果为" + res_upUser);
                        }catch (Exception e){
                            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.refund_Error.getErrorCode(),ReturnEnum.refund_Error.getErrorDesc(), "退款失败"));
                        }
                        if(res_uprefund >0 && res_upUser >0){
                            return ResponseEntity.ok(new Message<String>(true, 0, null, "押金退款已经受理，后续状态在48小时内注意查看系统消息！"));
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok(new Message<String>(false, ReturnEnum.refund_Error.getErrorCode(),ReturnEnum.refund_Error.getErrorDesc(), "退款失败"));
    }

    //押金充值
    public String forRecharge(ThirdPayBean payBean, long userId) {
        bankDepositOrder order = createDepositRechargeOrder(payBean, userId);
//        long orderId = 0;
        try {
            long orderId = payRestfulService.depositRecharge(order);
            if (orderId >0){
                payBean.setId(orderId);
                return ThirdPayService.execute(payBean);
            }else{
                return "";
            }
        } catch (Exception e) {
           throw new RestfulException(ReturnEnum.Recharge_Error);
        }
    }

    Long refund(bankDepositOrder order){
        bankRefundOrder bankRefundOrder = new bankRefundOrder();
        bankRefundOrder.setUserId(order.getUserId());
        bankRefundOrder.setRefundAmount(order.getCash());
        bankRefundOrder.setRefundType(0);
        bankRefundOrder.setOrderId(order.getId());
        bankRefundOrder.setCreateAt(UnixTimeUtils.now());
        try {
            return payRestfulService.creatRefundOrder(bankRefundOrder);
        } catch (Exception e) {
            return null;
        }
    }


    public String recharge(ThirdPayBean payBean, long userId){
        bankDepositOrder order = createRechargeOrder(payBean, userId);
        try {
            long orderId = payRestfulService.depositRecharge(order);
            if (orderId > 0){
                payBean.setId(orderId);
                return ThirdPayService.execute(payBean);
            }else{
                return "";
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.Recharge_Error);
        }
    }

    public bankDepositOrder createRechargeOrder(ThirdPayBean payBean, long userId) {
        bankDepositOrder order = new bankDepositOrder();
        order.setUserId(userId);
        order.setCash(payBean.getOrderMoney());
        order.setAward(payBean.getOrderMoneyFree());
        order.setPayType(payBean.getChannelId());
        order.setCreateAt(UnixTimeUtils.now());
        order.setRechargeType(payBean.getRechargeType());
        order.setStatus(1);
        return order;
    }

    //组合充值信息
    public bankDepositOrder createDepositRechargeOrder(ThirdPayBean payBean, long userId) {
        bankDepositOrder order = new bankDepositOrder();
        order.setUserId(userId);
        order.setCash(payBean.getOrderMoney());
        order.setPayType(payBean.getChannelId());
        order.setCreateAt(UnixTimeUtils.now());
        order.setRechargeType(payBean.getRechargeType());
        order.setStatus(1);
        order.setRemark(payBean.getPruductDesc());
        return order;
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
