package pers.dandandog.projects.wx.service;

import com.dandandog.framework.core.service.ICacheService;
import com.dandandog.framework.wx.service.WxTokenService;
import pers.dandandog.projects.wx.entity.WxUser;

/**
 * @author JohnnyLiu
 */
public interface WxUserService extends ICacheService<WxUser>, WxTokenService {


    WxUser getWxUserInfo(String code, String encryptedData, String iv);


}
