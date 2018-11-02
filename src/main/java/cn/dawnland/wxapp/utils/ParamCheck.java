package cn.dawnland.wxapp.utils;

import org.springframework.util.StringUtils;

/**
 * Created by liuyong
 *
 * Controller层入参检查
 *
 * 2018-10-16  11-24
 */

public class ParamCheck {


    public static final String NOT_AVALIABLE = "is not available!";

    /**
     * 全部为字符串参数的检查教研
     * @param key 字段名
     * @param value 待检查参数值
     * @return
     */
    public static String StringCheck(String [] key , String[] value){
        int index = -1;
        for(int i = 0 ; i < value.length ; i++){
            if(StringUtils.isEmpty(value[i])){
                index = i;
                break;
            }
        }
        if(index == -1)return null;
        else return key[index]+NOT_AVALIABLE;
    }

    /**
     * 全部为Long参数的检查教研
     * @param key 字段名
     * @param value 待检查参数值
     * @return
     */
    public static String LongCheck(String [] key , Long[] value){
        int index = -1;
        for(int i = 0 ; i < value.length ; i++){
            if(value[i] == null && value[i] < 0){
                index = i;
                break;
            }
        }
        if(index == -1)return null;
        else return key[index]+NOT_AVALIABLE;
    }
}
