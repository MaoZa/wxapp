package cn.dawnland.wxapp.service.impl;

import cn.dawnland.wxapp.service.UserService;
import cn.dawnland.wxapp.utils.RequestUtils;
import com.alibaba.fastjson.JSONObject;
import cn.dawnland.wxapp.config.WxConfig;
import cn.dawnland.wxapp.mapper.UserMapper;
import cn.dawnland.wxapp.utils.Result.ReturnData;
import cn.dawnland.wxapp.models.User;
import cn.dawnland.wxapp.utils.user.UserSession;
import cn.dawnland.wxapp.utils.WXAppletUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class UserServerImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServerImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxConfig wxConfig;

    @Override
    public BigDecimal selectUserBanlance(Long id) {
        return userMapper.selectUserBanlance(id);
    }

    @Override
    public void addOrReduceBanlanceById(String symbol, BigDecimal bills, Long id) {
        userMapper.addOrReduceBanlanceById(symbol, bills, id);
    }

    @Override
    public void updateUserDisable(Long userId, Date disableTime) {
        userMapper.updateUserDisable(userId, disableTime);
    }

    @Override
    public ReturnData codeToOpenId(String code, HttpServletRequest request) {
        logger.info("userimp sessionkey" + request.getSession().getId());
        JSONObject result = WXAppletUserInfo.getSessionKeyOropenid(code, wxConfig);
        if (result.get("errcode") != null){
            return new ReturnData(Integer.parseInt(result.get("errcode").toString()), (String)result.get("errmsg"));
        }
        //查询openid是否已存入数据库
        User user = userMapper.selectUserByOpenId((String) result.get("openid"));
        if (user != null) {
            //不等于空说明已经存入该openid的用户 返回前台登录成功 并存入userSession
            userMapper.updateSessionKeyByOpenId((String) result.get("openid"), (String) result.get("session_key"), request.getRemoteAddr());
            request.getSession().setAttribute("userSession", new UserSession(user.getId(), request.getRemoteAddr() , user.getOpenId()));
            logger.info("userSession" + request.getSession().getAttribute("userSession"));
            return ReturnData.isOk();
        }
        user = new User();
        user.setOpenId((String) result.get("openid"));
        user.setSessionKey((String) result.get("session_key"));
        userMapper.insertUser(user);
//        WXAppletUserInfo.getUserInfo();
        request.getSession().setAttribute("userSession", new UserSession(user.getId(), user.getOpenId(), request.getRemoteAddr()));
        return ReturnData.isOk();
    }

    @Override
    public User selectUserByOpenId(String openId) {
        return userMapper.selectUserByOpenId(openId);
    }

    @Override
    public void saveUserInfo(JSONObject userInfo) {
        User user = new User((String) userInfo.get("nickName"), (String) userInfo.get("openId"), (String) userInfo.get("unionId"), (Integer) userInfo.get("gender"),
                (String) userInfo.get("avatarUrl"), (String) userInfo.get("country"), (String) userInfo.get("province"), (String) userInfo.get("city"));
        userMapper.updateUserInfo(user);
    }

}
