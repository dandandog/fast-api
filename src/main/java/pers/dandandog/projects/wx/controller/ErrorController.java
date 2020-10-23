package pers.dandandog.projects.wx.controller;


import com.dandandog.framework.rest.model.ApiResult;
import com.dandandog.framework.wx.model.WxErrorCode;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JohnnyLiu
 */
@ControllerAdvice
@RestController
public class ErrorController {

    @ExceptionHandler(WxErrorException.class)
    public ApiResult<String> customException(WxErrorException e) {
        return ApiResult.failed(WxErrorCode.WX_SERVICE_ERROR, e.getMessage());
    }

}
