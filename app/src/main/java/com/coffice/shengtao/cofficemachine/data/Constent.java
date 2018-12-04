package com.coffice.shengtao.cofficemachine.data;

public class Constent {

    //扫码支付的  预订单 接口
    public static final String  URL_unifiedorder="https://api.mch.weixin.qq.com/pay/unifiedorder";
    //退款请求的接口
    public static final String  URL_TUIKUAN="https://api.mch.weixin.qq.com/secapi/pay/refund";
    //查询 订单结构的接口
    public static final String  URL_CHAXUN_DINGDAN="https://api.mch.weixin.qq.com/pay/orderquery";
    //条形码 扫描  支付的接口
    public static final String  URL_micropay="https://api.mch.weixin.qq.com/pay/micropay";
    public static final String RESULT_CODE="result_code";
    public static final String RETURN_CODE="return_code";
    public static final String TRADE_STATE="trade_state";
    public static final String TRADE_STATE_DESC="trade_state_desc";
    public static final String ERROR_CODE="err_code";
    public static final String SUCCESS="SUCCESS";
    public static final String PAYERROR="FAIL";
    public static final String POST="POST";
}
