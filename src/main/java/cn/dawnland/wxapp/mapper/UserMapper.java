package cn.dawnland.wxapp.mapper;

import cn.dawnland.wxapp.models.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface UserMapper {

    /** id查余额 */
    @Select("select balance from `user` where id = #{id} and archive = 0")
    BigDecimal selectUserBanlance(@Param("id") Long id);

    /** 余额加减 */
    @UpdateProvider(type = UserProvider.class, method = "addOrReduceBanlanceById")
    void addOrReduceBanlanceById(@Param("symbol") String symbol, @Param("bills") BigDecimal bills, @Param("id") Long id);

    /** 封停分销权限 */
    @Update("update user set disable = 1, disable_time = #{disableTime} where id = #{userId}")
    void updateUserDisable(@Param("userId") Long userId, @Param("disableTime") Date disableTime);

    /** openid查user */
    @Select("select a.* from `user` a where open_id = #{openid} and a.archive = 0")
    User selectUserByOpenId(@Param("openid") String openid);

    /** 新用户插入 */
    @Insert("insert into user(open_id, session_key, create_at)" +
            "values(#{openId}, #{sessionKey}, now())")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void insertUser(User user);

    /** updatesessionkeybyopenid */
    @Update("update user set session_key = #{sessionKey}, login_ip = #{loginIp}, login_time = now() where open_id = #{openId} and archive = 0")
    void updateSessionKeyByOpenId(@Param("openId") String openId, @Param("sessionKey") String sessionKey, @Param("loginIp") String loginIp);

    /** 更新用户信息 */
    @UpdateProvider(type = UserProvider.class, method = "updateUserInfo")
    void updateUserInfo(User user);

    class UserProvider{
        public String addOrReduceBanlanceById(@Param("symbol") String symbol, @Param("bills") BigDecimal bills, @Param("id") Long id){
            return new SQL(){{
                UPDATE("user");
                if (symbol.equals("+")){
                    SET("balance = balance + #{bills}");
                }else{
                    SET("balance = balance - #{bills}");
                }
                WHERE("id = #{id}");
            }}.toString();
        }
        public String updateUserInfo(User user){
            return new SQL(){{
                UPDATE("user");
                SET("country = #{country}");
                SET("union_id = #{unionId}");
                SET("sex = #{sex}");
                SET("province = #{province}");
                SET("city = #{city}");
                SET("avatar = #{avatar}");
                SET("nickname = #{nickname}");
                WHERE("open_id = #{openId}");
            }}.toString();
        }
    }

}
