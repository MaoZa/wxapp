package cn.dawnland.wxapp.service;

import com.alibaba.fastjson.JSONObject;
import cn.dawnland.wxapp.utils.Result.ReturnData;
import cn.dawnland.wxapp.models.User;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/15.
 */
public interface UserService {

    /** id查余额 */
    BigDecimal selectUserBanlance(Long id);

    /** 余额加减 */
    void addOrReduceBanlanceById(String symbol, BigDecimal bills, Long id);

    /** 封停分销权限 */
    void updateUserDisable(Long userId, Date disableTime);

    /** code换取openid */
    ReturnData codeToOpenId(String code, HttpServletRequest request);

    /** openid查user */
    User selectUserByOpenId(String openId);

    /** 保存用户信息 */
    void saveUserInfo(JSONObject userInfo);

}
