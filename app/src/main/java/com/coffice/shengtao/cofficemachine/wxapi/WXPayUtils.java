package com.coffice.shengtao.cofficemachine.wxapi;

import android.util.Log;

/***微信二维码支付**/
public class WXPayUtils {
       /* private static String strResponse = null;
        private static String url = "";
        public static String nonceStr = "";
        public static String outTradeNo = "";

         * 微信生成签名

        private static String genPackageSign(List<NameValuePair> params) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < params.size(); i++) {
                sb.append(params.get(i).getName());
                sb.append('=');
                sb.append(params.get(i).getValue());
                sb.append('&');
            }
            sb.append("key=");
            sb.append(AlipayUtils.key);

            String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
                    .toUpperCase();
            Log.e("orion", packageSign);
            return packageSign;
        }

        *** 微信获取签名 **
        public static String genProductArgs(String nonceStr, String outTradeNo,
                                            String totalFee) {
            try {
                List<NameValuePair> packageParams = new LinkedList<>();
                packageParams.add(new BasicNameValuePair("appid",
                        AlipayUtils.appid));
                packageParams.add(new BasicNameValuePair("body", AlipayUtils.bodyname));
                packageParams
                        .add(new BasicNameValuePair("mch_id", AlipayUtils.mchid));
                packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
                packageParams.add(new BasicNameValuePair("notify_url",
                        "http://www.weixin.qq.com/wxpay/pay.php"));
                packageParams
                        .add(new BasicNameValuePair("out_trade_no", outTradeNo));
                packageParams.add(new BasicNameValuePair("spbill_create_ip",
                        AlipayUtils.ip));
                packageParams.add(new BasicNameValuePair("total_fee", totalFee));
                packageParams.add(new BasicNameValuePair("trade_type", "NATIVE"));

                String sign = genPackageSign(packageParams);

                return sign;

            } catch (Exception e) {
                LogUtils.saveFileToSMB(LogUtils.getExceptionInfo(e));
                e.printStackTrace();
                return null;
            }

        }

         微信获取签名
        public static String QgenProductArgs(String nonceStr, String outTradeNo) {
            try {
                List<NameValuePair> packageParams = new LinkedList<>();
                packageParams.add(new BasicNameValuePair("appid",
                        AlipayUtils.appid));
                packageParams
                        .add(new BasicNameValuePair("mch_id", AlipayUtils.mchid));
                packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
                packageParams
                        .add(new BasicNameValuePair("out_trade_no", outTradeNo));

                String sign = genPackageSign(packageParams);

                return sign;

            } catch (Exception e) {
                LogUtils.saveFileToSMB(LogUtils.getExceptionInfo(e));
                e.printStackTrace();
                return null;
            }

        }

        微信获取随机字符串nonce_str
        public static String getNonceStr() {
            Random random = new Random();
            nonceStr = MD5.getMessageDigest(String.valueOf(
                    System.currentTimeMillis() + random.nextInt(10000)).getBytes());
            return nonceStr;
        }

        微信获取随机字符串out_trade_no
        public static String getOutTradNo() {
            Random random = new Random();
            outTradeNo = MD5.getMessageDigest(String.valueOf(
                    System.currentTimeMillis() + random.nextInt(10000)).getBytes());
            return outTradeNo;
        }

         微信生成二维码URL **
        public static void sendWxPayRequest(final String body, final String nonceStr,
                                            final String outTradeNo, final String totalFee,
                                            final Handler handler) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // 构造HTTP请求
                    HttpClient httpclient = new HttpClient();
                    PostMethod postMethod = new PostMethod(
                            "https://api.mch.weixin.qq.com/pay/unifiedorder");
                    StringBuffer requestStr = new StringBuffer("<xml>");
                    requestStr.append("<appid><![CDATA[");
                    requestStr.append(AlipayUtils.appid);
                    requestStr.append("]]></appid>");
                    requestStr.append("<body><![CDATA[");
                    requestStr.append(body);
                    requestStr.append("]]></body>");
                    requestStr.append("<mch_id><![CDATA[");
                    requestStr.append(AlipayUtils.mchid);
                    requestStr.append("]]></mch_id>");
                    requestStr.append("<nonce_str><![CDATA[");
                    requestStr.append(nonceStr);
                    requestStr.append("]]></nonce_str>");
                    requestStr.append("<notify_url><![CDATA[");
                    requestStr.append("http://www.weixin.qq.com/wxpay/pay.php");
                    requestStr.append("]]></notify_url>");
                    requestStr.append("<out_trade_no><![CDATA[");
                    requestStr.append(outTradeNo);
                    requestStr.append("]]></out_trade_no>");
                    requestStr.append("<spbill_create_ip><![CDATA[");
                    requestStr.append(AlipayUtils.ip);
                    requestStr.append("]]></spbill_create_ip>");
                    requestStr.append("<total_fee><![CDATA[");
                    requestStr.append(totalFee);
                    requestStr.append("]]></total_fee>");
                    requestStr.append("<trade_type><![CDATA[");
                    requestStr.append("NATIVE");
                    requestStr.append("]]></trade_type>");
                    requestStr.append("<sign><![CDATA[");
                    requestStr.append(WXPayUtils.genProductArgs(nonceStr,
                            outTradeNo, totalFee));
                    requestStr.append("]]></sign>");
                    requestStr.append("</xml>");
                    // 发送请求
                    try {
                        RequestEntity entity = new StringRequestEntity(
                                requestStr.toString(), "text/xml", "UTF-8");
                        postMethod.setRequestEntity(entity);
                        httpclient.executeMethod(postMethod);
                        strResponse = new String(postMethod.getResponseBody(),
                                "utf-8");
                        Log.e("strResponse", strResponse);
//                  LogUtils.saveToFile(strResponse);
                        Message msg = Message.obtain();
                        if (!"FAIL".equals(strResponse.split("CDATA\\[")[1].split("]]")[0])){
                            url = "weixin"
                                    + strResponse.split("weixin")[1].split("]]")[0];
                            msg.what = 0;
                            msg.obj = url;
                        }else {
                            msg.what = 8;
                        }
                        handler.sendMessage(msg);
                    } catch (HttpException e) {
                        LogUtils.saveFileToSMB(LogUtils.getExceptionInfo(e));
                        e.printStackTrace();
                    } catch (IOException e) {
                        LogUtils.saveFileToSMB(LogUtils.getExceptionInfo(e));
                        e.printStackTrace();
                    } finally {
                        postMethod.releaseConnection();
                    }
                }
            }).start();
        }

         微信查询订单状态
        public static void queryWxPayRequest(final String nonceStr,
                                             final String outTradeNo, final Handler handler) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // 构造HTTP请求
                    HttpClient httpclient = new HttpClient();
                    PostMethod postMethod = new PostMethod(
                            "https://api.mch.weixin.qq.com/pay/orderquery");
                    StringBuffer requestStr = new StringBuffer("<xml>");
                    requestStr.append("<appid><![CDATA[");
                    requestStr.append(AlipayUtils.appid);
                    requestStr.append("]]></appid>");
                    requestStr.append("<mch_id><![CDATA[");
                    requestStr.append(AlipayUtils.mchid);
                    requestStr.append("]]></mch_id>");
                    requestStr.append("<nonce_str><![CDATA[");
                    requestStr.append(nonceStr);
                    requestStr.append("]]></nonce_str>");
                    requestStr.append("<out_trade_no><![CDATA[");
                    requestStr.append(outTradeNo);
                    requestStr.append("]]></out_trade_no>");
                    requestStr.append("<sign><![CDATA[");
                    requestStr.append(WXPayUtils.QgenProductArgs(nonceStr,
                            outTradeNo));
                    requestStr.append("]]></sign>");
                    requestStr.append("</xml>");
                    // 发送请求
                    try {
                        RequestEntity entity = new StringRequestEntity(requestStr
                                .toString(), "text/xml", "UTF-8");
                        postMethod.setRequestEntity(entity);
                        httpclient.executeMethod(postMethod);
                        strResponse = new String(postMethod.getResponseBody(),
                                "utf-8");
                        Log.e("strResponse", strResponse);
                        String state = "";
                        if (!"FAIL".equals(strResponse.split("CDATA\\[")[1].split("]]")[0])){
                            state = strResponse.split("trade_state")[1]
                                    .split("\\[")[2].split("]]")[0];
                        }
                        Message msg = Message.obtain();
                        msg.what = 1;
                        msg.obj = state;
                        handler.sendMessage(msg);
                        Log.e("state", state);
                    } catch (Exception e) {
                        LogUtils.saveFileToSMB(LogUtils.getExceptionInfo(e));
                        e.printStackTrace();
                    } finally {
                        postMethod.releaseConnection();
                    }
                }
            }).start();
        }
    }*/
}
