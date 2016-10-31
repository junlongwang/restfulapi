package myTest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 摊销记录对象
 * @author lijian02
 * @version $Id: myTest.Amortize.java, v 0.1 2015-4-26 上午10:40:31 lijian02 Exp $
 */
public class Amortize implements Serializable {

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    /** 业务系统订单号 */
    private Long payId;

    /** 订单创建时间 */
    private Long  consumeTime;

    /**
     * 流水Id
     */
    private Integer      flowId;

    private Integer productId;

    private Integer cateOne;

    private Integer cateTwo;

    private Integer cateThree;

    /** 城市ID */
    private Integer      cityId      = 0;

    /** 用户ID */
    private Long         userId;

    /**
     * 数据来源
     */
    private String sourceSys="pmc";

    /**
     * 消耗资源数
     */
    private BigDecimal consumeAmount;

    /**
     * 资源总数
     */
    private BigDecimal totalAmount;

    /**
     * 是否最后一次 是：1，否：0
     */
    private Integer isLastTime;

//    /**
//     * 订单金额，如果订单金额为0，不进行摊销调用。此属性不参与序列化
//     */
//    private transient BigDecimal price;
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getIsLastTime() {
        return isLastTime;
    }

    public void setIsLastTime(Integer isLastTime) {
        this.isLastTime = isLastTime;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCateOne() {
        return cateOne;
    }

    public void setCateOne(Integer cateOne) {
        this.cateOne = cateOne;
    }

    public Integer getCateTwo() {
        return cateTwo;
    }

    public void setCateTwo(Integer cateTwo) {
        this.cateTwo = cateTwo;
    }

    public Integer getCateThree() {
        return cateThree;
    }

    public void setCateThree(Integer cateThree) {
        this.cateThree = cateThree;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSourceSys() {
        return sourceSys;
    }

    public void setSourceSys(String sourceSys) {
        this.sourceSys = sourceSys;
    }

    public Long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public static String getSimple() {
        return simple;
    }



    private static String simple(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(simple).format(date);
    }

    private static Double format(Double d) {
        if(d==null){
            return 0.00;
        }
        return new BigDecimal(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static final String simple        = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String toString() {
        return "myTest.Amortize{" +
                "payId=" + payId +
                ", consumeTime=" + consumeTime +
                ", flowId=" + flowId +
                ", productId=" + productId +
                ", cateOne=" + cateOne +
                ", cateTwo=" + cateTwo +
                ", cateThree=" + cateThree +
                ", cityId=" + cityId +
                ", userId=" + userId +
                ", sourceSys='" + sourceSys + '\'' +
                ", consumeAmount=" + consumeAmount +
                ", totalAmount=" + totalAmount +
                ", isLastTime=" + isLastTime +
                '}';
    }
}
