package pers.dandandog.projects.wx.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.dandandog.framework.wx.jwt.JwtToken;
import com.dandandog.framework.wx.service.AuthTokenService;
import com.dandandog.framework.wx.utils.JwtTokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;
import pers.dandandog.projects.wx.dao.WxUserDao;
import pers.dandandog.projects.wx.entity.AppUser;
import pers.dandandog.projects.wx.service.WxUserService;

import java.util.Optional;

/**
 * @author JohnnyLiu
 */
@Service
public class WxUserServiceImpl extends BaseServiceImpl<WxUserDao, AppUser> implements WxUserService, AuthTokenService {


    @Override
    public String generateToken(String sessionKey, String openId) {
        AppUser user = Optional.ofNullable(getOne(new LambdaQueryWrapper<AppUser>().eq(AppUser::getOpenId, openId)))
                .orElse(new AppUser(openId))
                .setSessionKey(sessionKey)
                .setSecret(RandomUtil.randomString(16));
        return saveOrUpdate(user) ? JwtTokenUtil.generateToken(user.getId(), user.getSecret()) : null;
    }

    @Override
    public String refreshToken(JwtToken jwtToken) {
        AppUser user = Optional.ofNullable(getById(jwtToken.getUniqueId())).orElseThrow(() ->
                new AuthenticationException("用户不存在"));
        user.setSecret(RandomUtil.randomString(16));
        String newToken = JwtTokenUtil.refreshToken(jwtToken.getToken(), user.getSecret());
        if (!ObjectUtil.equal(newToken, jwtToken.getToken())) {
            saveOrUpdate(user);
        }
        return newToken;
    }

    @Override
    public JwtToken buildJwtToken(String token) {
        String uniqueId = JwtTokenUtil.getUniqueId(token);
        String secret = Optional.ofNullable(getById(uniqueId)).orElseThrow(() ->
                new AuthenticationException("用户不存在")).getSecret();
        return JwtToken.build(token, uniqueId, secret);
    }


}
