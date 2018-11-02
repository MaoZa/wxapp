package cn.dawnland.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import cn.dawnland.wxapp.models.User;
import cn.dawnland.wxapp.service.UserService;
import cn.dawnland.wxapp.utils.Result.ReturnData;
import cn.dawnland.wxapp.utils.WXAppletUserInfo;
import cn.dawnland.wxapp.utils.user.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("client/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * code登录
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "openid", method = RequestMethod.POST)
    public ReturnData openid(@RequestParam("code") String code,
                             HttpServletRequest request){
        ReturnData returnData = userService.codeToOpenId(code, request);
        Map data = new HashMap();
        data.put("info", request.getSession().getId());
        returnData.setData(data);
        return returnData;
    }

    /**
     * 保存用户信息
     * @param encryptedData
     * @param iv
     * @param request
     * @return
     */
    @RequestMapping(value = "saveUserInfo", method = RequestMethod.POST)
    public ReturnData savaUserInfo(@RequestParam("encryptedData") String encryptedData,
                                   @RequestParam("iv") String iv,
                                   HttpServletRequest request){
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        User user = userService.selectUserByOpenId(userSession.getOpenId());
        JSONObject userInfo = WXAppletUserInfo.getUserInfo(encryptedData, user.getSessionKey(), iv);
        logger.info(userInfo.toJSONString());
        userService.saveUserInfo(userInfo);
        return new ReturnData(userInfo);
    }


}
