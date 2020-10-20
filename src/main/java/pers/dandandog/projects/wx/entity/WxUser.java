package pers.dandandog.projects.wx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author JohnnyLiu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_user")
public class WxUser extends BaseEntity {

    /**
     * 小程序openid
     */
    private String openId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 小程序session
     */
    private String sessionKey;

    /**
     * 密碼
     */
    private String secret;


    public WxUser(String openId) {
        this.openId = openId;
    }
}
