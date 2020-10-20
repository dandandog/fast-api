package pers.dandandog.projects.wx.service;

import com.dandandog.framework.core.service.ICacheService;
import com.dandandog.framework.wx.service.TokenService;
import pers.dandandog.projects.wx.entity.WxUser;

/**
 * @author JohnnyLiu
 */
public interface WxUserService extends ICacheService<WxUser>, TokenService {
}
