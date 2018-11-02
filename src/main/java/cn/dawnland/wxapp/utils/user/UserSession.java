package cn.dawnland.wxapp.utils.user;

public class UserSession {

    //用户id
    private Long id;
    //登录ip
    private String loginIp;
    //开放id 登录标识
    private String openId;

    public UserSession() {
    }

    public UserSession(Long id, String loginIp, String openId) {
        this.id = id;
        this.loginIp = loginIp;
        this.openId = openId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
