package pers.dandandog.projects.wx.controller;

import com.dandandog.framework.rest.controller.ApiController;
import com.dandandog.framework.rest.model.ApiResponse;
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

    @Resource
    private WxUserService wxUserService;


    @GetMapping("/token")
    @ApiOperation(value = "获取token", response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, dataType = "String", value = "微信code")
    })
    public ApiResponse<String> login(@RequestParam String code, HttpServletResponse response) throws WxErrorException {
        String token = wxUserService.generateToken(code);
        JwtHeaderUtil.setAuthHeader(response, token);
        return success(token);
    }

    @GetMapping("/user")
    @ApiOperation(value = "获取微信用户信息", response = WxUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", required = true, dataType = "String", value = "微信appId"),
            @ApiImplicitParam(name = "encryptedData", required = true, dataType = "String", value = "加密的数据"),
            @ApiImplicitParam(name = "iv", required = true, dataType = "String", value = "偏移量")
    })
    public ApiResponse<WxUser> user(@RequestParam String code, @RequestParam String encryptedData, @RequestParam String iv) throws WxErrorException {
        WxUser wxUser = wxUserService.getWxUserInfo(code, encryptedData, iv);
        return success(wxUser);
    }


}
