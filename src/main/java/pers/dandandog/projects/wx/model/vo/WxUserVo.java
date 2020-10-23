package pers.dandandog.projects.wx.model.vo;

import com.dandandog.framework.mapstruct.model.Url;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author JohnnyLiu
 */
@Data
public class WxUserVo {

    @ApiModelProperty(
            value = "昵称",
            example = "张三"
    )
    private String nickName;

    @ApiModelProperty(
            value = "头像",
            example = "http://www.xxx.com/avatar.jpg"
    )
    private Url avatarUrl;
}
