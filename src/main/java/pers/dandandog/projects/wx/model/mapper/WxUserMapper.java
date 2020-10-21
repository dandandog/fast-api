package pers.dandandog.projects.wx.model.mapper;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.dandandog.framework.mapstruct.StandardMapper;
import org.mapstruct.Mapper;
import pers.dandandog.projects.wx.entity.WxUser;

/**
 * @author JohnnyLiu
 */
@Mapper(componentModel = "spring")
public interface WxUserMapper extends StandardMapper<WxUser, WxMaUserInfo> {
}
