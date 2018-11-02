package cn.dawnland.wxapp.utils.wxConfig;

import com.github.binarywang.wxpay.config.WxPayConfig;

/**
 * 微信支付配置类
 */
public class WxConfig {
    private static WxPayConfig config = null;

    public static WxPayConfig getConfig() {
        if(config == null){
            config = new WxPayConfig();
            config.setAppId("wxcfdee18b2b62c513");
            config.setMchId("1516802721");
            config.setTradeType("JSAPI");
            config.setMchKey("hWOHWReAHcJVd7XTpxurEBCk7ugnj5VX ");
        }
        return config;
    }
}
