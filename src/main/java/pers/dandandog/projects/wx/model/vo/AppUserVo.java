package pers.dandandog.projects.wx.model.vo;

import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.model.MapperVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author JohnnyLiu
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AppUserVo extends MapperVo {

    @ApiModelProperty(
            value = "昵称",
            example = "张三"
    )
    private String nickname;

    @ApiModelProperty(
            value = "头像",
            example = "http://www.xxx.com/avatar.jpg"
    )
    private MapperUrl avatarUrl;
}
