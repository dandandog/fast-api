package pers.dandandog.projects.wx.controller;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.rest.controller.ApiController;
import com.dandandog.framework.rest.model.ApiResult;
import com.dandandog.framework.wx.config.WxConfig;
import com.dandandog.framework.wx.service.AuthTokenService;
import com.dandandog.framework.wx.utils.JwtHeaderUtil;
import io.swagger.annotations.*;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.dandandog.projects.wx.entity.AppUser;
import pers.dandandog.projects.wx.model.vo.AppUserVo;
import pers.dandandog.projects.wx.service.WxUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JohnnyLiu
 */
@RestController
@RequestMapping("/wx")
@Api(value = "System API", tags = {"微信相关API"})
public class AppWxController extends ApiController {

    private static final String APP_GIFT = "gift";

    @Resource
    private WxUserService wxUserService;

    @Resource
    private AuthTokenService authTokenService;

    @GetMapping("/index")
    @ApiOperation(value = "index", response = String.class)
    public ApiResult<String> index() {
        return success("hello world");
    }

    @GetMapping("/login")
    @ApiOperation(value = "登入获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, dataType = "String", value = "微信code")
    })
    @ApiResponse(code = 20000, message = "操作成功", response = String.class)
    public ApiResult<String> login(@RequestParam String code, HttpServletResponse response) throws WxErrorException {
        WxMaJscode2SessionResult session = WxConfig.getMaService(APP_GIFT).getUserService().getSessionInfo(code);
        String token = authTokenService.generateToken(session.getSessionKey(), session.getOpenid());
        JwtHeaderUtil.setAuthHeader(response, token);
        return success(token);
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取微信用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, dataType = "String", value = "微信appId"),
            @ApiImplicitParam(name = "encryptedData", required = true, dataType = "String", value = "加密的数据"),
            @ApiImplicitParam(name = "iv", required = true, dataType = "String", value = "偏移量")
    })
    @ApiResponse(code = 200, message = "OK", response = AppUserVo.class)
    public ApiResult<AppUserVo> user(@RequestParam String code, @RequestParam String encryptedData, @RequestParam String iv) {
        WxMaUserInfo wxMaUserInfo = WxConfig.getMaService(APP_GIFT).getUserService().getUserInfo(code, encryptedData, iv);
        AppUser wxUser = MapperUtil.mapFrom(wxMaUserInfo, AppUser.class);
        wxUserService.saveOrUpdate(wxUser, new LambdaQueryWrapper<AppUser>().eq(AppUser::getOpenId, wxMaUserInfo.getOpenId()));
        AppUserVo vo = MapperUtil.mapTo(wxUser, AppUserVo.class);
        return success(vo);
    }


}
