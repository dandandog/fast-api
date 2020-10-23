package pers.dandandog.projects.wx.controller;


import com.dandandog.framework.rest.model.ApiResponse;
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
    public ApiResponse<String> customException(WxErrorException e) {
        return ApiResponse.failed(WxErrorCode.WX_SERVICE_ERROR, e.getMessage());
    }

}
