package pers.dandandog.projects.wx.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.dandandog.framework.wx.utils.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import pers.dandandog.projects.wx.dao.WxUserDao;
import pers.dandandog.projects.wx.entity.WxUser;
import pers.dandandog.projects.wx.service.WxUserService;

import java.util.Optional;

/**
 * @author JohnnyLiu
 */
@Service
public class WxUserServiceImpl extends BaseServiceImpl<WxUserDao, WxUser> implements WxUserService {


    @Override
    public String generate3rdSession(String sessionKey, String openId) {
        WxUser user = Optional.ofNullable(getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenId, openId)))
                .orElse(new WxUser(openId))
                .setSessionKey(sessionKey)
                .setSecret(RandomUtil.randomString(16));
        return saveOrUpdate(user) ? JwtTokenUtil.generateToken(user.getId(), user.getSecret()) : null;
    }

    @Override
    public String getSecret(String userId) {
        return Optional.ofNullable(getById(userId)).orElseThrow(() ->
                new AuthenticationException("用户不存在")).getSecret();
    }
}
