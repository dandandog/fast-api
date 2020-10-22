package pers.dandandog.projects.wx.model.vo;

import com.dandandog.framework.mapstruct.model.Url;
import lombok.Data;

/**
 * @author JohnnyLiu
 */
@Data
public class WxUserVo {

    private String nickName;

    private Url avatarUrl;
}
