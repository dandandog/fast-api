package pers.dandandog.projects.wx.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.dandandog.framework.mapstruct.MapperRepo;
import com.dandandog.framework.wx.config.WxConfig;
import com.dandandog.framework.wx.jwt.JwtToken;
import com.dandandog.framework.wx.utils.JwtTokenUtil;
import me.chanjar.weixin.common.error.WxErrorException;
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
    public WxUser getWxUserInfo(String code, String encryptedData, String iv) {
        WxMaService wxMaService = WxConfig.getMaService("gift");
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(code, encryptedData, iv);
        return MapperRepo.mapFrom(wxMaUserInfo, WxUser.class);
    }


    @Override
    public String generateToken(String code) throws WxErrorException {
        WxMaService wxMaService = WxConfig.getMaService("gift");
        WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
        return generate3rdSession(session.getSessionKey(), session.getOpenid());
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

    private String generate3rdSession(String sessionKey, String openId) {
        WxUser user = Optional.ofNullable(getOne(new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenId, openId)))
                .orElse(new WxUser(openId))
                .setSessionKey(sessionKey)
                .setSecret(RandomUtil.randomString(16));
        return saveOrUpdate(user) ? JwtTokenUtil.generateToken(user.getId(), user.getSecret()) : null;
    }


}
