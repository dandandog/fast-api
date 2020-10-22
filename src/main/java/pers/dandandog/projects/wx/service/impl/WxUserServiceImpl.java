package pers.dandandog.projects.wx.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.dandandog.framework.wx.jwt.JwtToken;
import com.dandandog.framework.wx.service.WxTokenService;
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
public class WxUserServiceImpl extends BaseServiceImpl<WxUserDao, WxUser> implements WxUserService, WxTokenService {


    @Override
    public String generateToken(String sessionKey, String openId) {
        WxUser user = Optional.ofNullable(getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenId, openId)))
                .orElse(new WxUser(openId))
                .setSessionKey(sessionKey)
                .setSecret(RandomUtil.randomString(16));
        return saveOrUpdate(user) ? JwtTokenUtil.generateToken(user.getId(), user.getSecret()) : null;
    }

    @Override
    public String refreshToken(JwtToken jwtToken) {
        WxUser user = Optional.ofNullable(getById(jwtToken.getUniqueId())).orElseThrow(() ->
                new AuthenticationException("用户不存在"));
        user.setSecret(RandomUtil.randomString(16));
        saveOrUpdate(user);
        return JwtTokenUtil.refreshToken(jwtToken.getToken(), user.getSecret());
    }

    @Override
    public JwtToken buildJwtToken(String token) {
        String uniqueId = JwtTokenUtil.getUniqueId(token);
        String secret = Optional.ofNullable(getById(uniqueId)).orElseThrow(() ->
                new AuthenticationException("用户不存在")).getSecret();
        return JwtToken.build(token, uniqueId, secret);
    }


}
