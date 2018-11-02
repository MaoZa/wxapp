package cn.dawnland.wxapp.utils.Result;

/**
 * Created by liuyong
 * 2018-10-16  10-42
 */

public enum ResultCode {

    SUCCESS("success" ,0),

    FAIL("fail",1),

    UNAUTHORIZED("unauthorized",2);

    //TODO 待扩展状态字段

    private int code;

    private String message;

    private ResultCode(String message , int code){
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
