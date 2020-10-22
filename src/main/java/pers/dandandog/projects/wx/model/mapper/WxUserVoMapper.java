package pers.dandandog.projects.wx.model.mapper;

import com.dandandog.framework.mapstruct.StandardMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;
import pers.dandandog.projects.wx.entity.WxUser;
import pers.dandandog.projects.wx.model.vo.WxUserVo;

/**
 * @author JohnnyLiu
 */
@Mapper(componentModel = "spring", uses = {QualifierUrl.class})
public interface WxUserVoMapper extends StandardMapper<WxUser, WxUserVo> {
}
