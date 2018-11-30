package com.coffice.shengtao.cofficemachine.data.model;

public class Alipay_Trade_Precreate_Return {


    /**
     * alipay_trade_precreate_response : {"code":"10000","msg":"Success","out_trade_no":"tradeprecreate15435434001118144548","qr_code":"https://qr.alipay.com/bax09956lmosovak4a9y401a"}
     * sign : EinKdFmaDsGNcxWNfn0QpckWyu79JB73l/QGZcbu6VL4FTSa734eyuEPFB2On7vuQyNZElHXxoT/4GZKEF+pEGyCH/qJ24K74iOd7RNZI9zN1h757dU4WOKBb0THTqaTAjf+CiF8+3ybpDacfr7pChBpnrryb53/ls5ZDXN5wX3iCOKMGFcPffgieHtXgIKOhl6/YnYa5fCdGsIF95bXKUpAab3gnp3+ljivKfktXNe2eaaC/H52Y8vTViY/7Fx/VQWhC8rkrqn225nOs0EJWgB9p65imSBYXzpl9RlMiLNpRGTgA9xNvop/Aa5xpffFrTc0YbGZIQdRg6GXs/ShZA==
     */

    private AlipayTradePrecreateResponseBean alipay_trade_precreate_response;
    private String sign;

    public AlipayTradePrecreateResponseBean getAlipay_trade_precreate_response() {
        return alipay_trade_precreate_response;
    }

    public void setAlipay_trade_precreate_response(AlipayTradePrecreateResponseBean alipay_trade_precreate_response) {
        this.alipay_trade_precreate_response = alipay_trade_precreate_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class AlipayTradePrecreateResponseBean {
        /**
         * code : 10000
         * msg : Success
         * out_trade_no : tradeprecreate15435434001118144548
         * qr_code : https://qr.alipay.com/bax09956lmosovak4a9y401a
         */

        private String code;
        private String msg;
        private String out_trade_no;
        private String qr_code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getQr_code() {
            return qr_code;
        }

        public void setQr_code(String qr_code) {
            this.qr_code = qr_code;
        }
    }
}
