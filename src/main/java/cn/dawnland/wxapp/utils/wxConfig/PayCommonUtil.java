package cn.dawnland.wxapp.utils.wxConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayCommonUtil {
    protected static Logger log = LoggerFactory.getLogger(PayCommonUtil.class);

    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createSign(String characterEncoding, SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        parameters.put("notify_url", "http://localhost:8080/ecp-purchase/APP/pay/wx/pay/notify_url");
        parameters.put("trade_type", "APP");
        parameters.put("appid", "wxd4e8680b25f44687");
        parameters.put("mch_id", "1513218221");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {

            Map.Entry entry = (Map.Entry)it.next();
            String k = underline(new StringBuffer((String)entry.getKey())).toString();
            Object v = entry.getValue();
            if(k.equals("class")){
                continue;
            }
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }

        }
        sb.append("key=" + "6DB0775DBEF38E4316D6CB25B948328A");//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        log.info(sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sb.toString();
    }

    public static StringBuffer underline(StringBuffer str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //将当前匹配子串替换为指定字符串，并且将替换后的子串以及其之前到上次匹配子串之后的字符串段添加到一个StringBuffer对象里。
            //正则之前的字符和被替换的字符
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            //把之后的也添加到StringBuffer对象里
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb);
    }
}
