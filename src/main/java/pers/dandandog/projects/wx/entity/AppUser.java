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
@TableName("app_user")
public class AppUser extends BaseEntity {

    /**
     * 小程序openid
     */

    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String gender;
    /**
     * 语言
     */
    private String language;
    /**
     * 市
     */
    private String city;
    /**
     * 省
     */
    private String province;
    /**
     * 国
     */
    private String country;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 唯一键
     */
    private String unionId;

    /**
     * 密钥
     */
    private String secret;
    /**
     * sessionKey
     */
    private String sessionKey;

    public AppUser() {

    }

    public AppUser(String openId) {
        this.openId = openId;
    }
}
