package pers.dandandog.projects.wx.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.dandandog.projects.wx.entity.AppUser;

/**
 * @author JohnnyLiu
 */
@Mapper
public interface AppUserDao extends BaseMapper<AppUser> {
}
