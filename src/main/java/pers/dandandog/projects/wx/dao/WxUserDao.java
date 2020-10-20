package pers.dandandog.projects.wx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.framework.core.service.ICacheService;
import org.apache.ibatis.annotations.Mapper;
import pers.dandandog.projects.wx.entity.WxUser;

/**
 * @author JohnnyLiu
 */
@Mapper
public interface WxUserDao extends BaseMapper<WxUser> {
}
