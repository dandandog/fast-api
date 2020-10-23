package pers.dandandog.projects.wx.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.framework.mapstruct.MapperRepo;
import com.dandandog.framework.rest.controller.ApiController;
import com.dandandog.framework.rest.model.ApiResponse;
import com.dandandog.framework.wx.config.WxConfig;
import com.dandandog.framework.wx.service.WxTokenService;
import com.dandandog.framework.wx.utils.JwtHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.dandandog.projects.wx.entity.WxUser;
import pers.dandandog.projects.wx.model.vo.WxUserVo;
import pers.dandandog.projects.wx.service.WxUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JohnnyLiu
 */
@RestController
@RequestMapping("/v1/wx")
@Api(value = "System API", tags = {"微信相关API"})
public class WxMaController extends ApiController {

    private static final String APP_GIFT = "gift";

    @Resource
    private WxUserService wxUserService;

    @Resource
    private WxTokenService wxTokenService;

    @GetMapping("/index")
    @ApiOperation(value = "获取token", response = String.class)
    public ApiResponse<String> index() {
        return success("hello world");
    }

    @GetMapping("/login")
    @ApiOperation(value = "登入获取token", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, dataType = "String", value = "微信code")
    })
    public ApiResponse<String> login(@RequestParam String code, HttpServletResponse response) throws WxErrorException {
        WxMaJscode2SessionResult session = WxConfig.getMaService(APP_GIFT).getUserService().getSessionInfo(code);
        String token = wxTokenService.generateToken(session.getSessionKey(), session.getOpenid());
        JwtHeaderUtil.setAuthHeader(response, token);
        return success(token);
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取微信用户信息", response = WxUserVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, dataType = "String", value = "微信appId"),
            @ApiImplicitParam(name = "encryptedData", required = true, dataType = "String", value = "加密的数据"),
            @ApiImplicitParam(name = "iv", required = true, dataType = "String", value = "偏移量")
    })
    public ApiResponse<WxUserVo> user(@RequestParam String code, @RequestParam String encryptedData, @RequestParam String iv) throws WxErrorException {
        WxMaUserInfo wxMaUserInfo = WxConfig.getMaService(APP_GIFT).getUserService().getUserInfo(code, encryptedData, iv);
        WxUser wxUser = MapperRepo.mapFrom(wxMaUserInfo, WxUser.class);
        wxUserService.saveOrUpdate(wxUser, new LambdaQueryWrapper<WxUser>().eq(WxUser::getOpenId, wxMaUserInfo.getOpenId()));
        WxUserVo vo = MapperRepo.mapTo(wxUser, WxUserVo.class);
        return success(vo);
    }


}
