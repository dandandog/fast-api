package pers.dandandog.projects.wx.model.mapper;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;
import pers.dandandog.projects.wx.entity.AppUser;

/**
 * @author JohnnyLiu
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends IMapper<AppUser, WxMaUserInfo> {
}
