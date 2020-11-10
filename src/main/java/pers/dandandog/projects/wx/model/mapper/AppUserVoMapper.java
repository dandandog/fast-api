package pers.dandandog.projects.wx.model.mapper;

import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;
import pers.dandandog.projects.wx.entity.AppUser;
import pers.dandandog.projects.wx.model.vo.AppUserVo;

/**
 * @author JohnnyLiu
 */
@Mapper(componentModel = "spring", uses = {QualifierUrl.class})
public interface AppUserVoMapper extends IMapper<AppUser, AppUserVo> {
}
