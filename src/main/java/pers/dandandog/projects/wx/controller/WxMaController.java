package pers.dandandog.projects.wx.controller;

import com.dandandog.framework.rest.controller.ApiController;
import com.dandandog.framework.rest.model.ApiResponse;
import com.dandandog.framework.wx.utils.JwtHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.dandandog.projects.wx.service.WxUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JohnnyLiu
 */
@Controller
@RequestMapping("/v1/wx")
@Api(value = "System API", tags = {"微信相关API"})
public class WxMaController extends ApiController {

    @Resource
    private WxUserService wxUserService;

    @GetMapping("/login")
    @ApiOperation(value = "获取token", response = ApiResponse.class)
    public ApiResponse<String> login(@RequestParam String appId, @RequestParam String code, HttpServletResponse response) throws WxErrorException {
        String token = wxUserService.generateToken(appId, code);
        JwtHeaderUtil.setAuthHeader(response, token);
        return success(token);
    }
}
