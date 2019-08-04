package com.twinkle.scaffold.modules.biz.moduleA.api;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.common.utils.CurrentUserUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月28日 下午4:29:37 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class DemoController {

    @GetMapping("/auth/test1")
    @ApiOperation(value = "测试接口1", notes = "测试接口1")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization", required = true,defaultValue = "Bearer ") })
    public GeneralResult<String> auth() {
        log.info(CurrentUserUtils.getCurrentUserName());
        log.info(CurrentUserUtils.getCurrentAccessToken());
        return new GeneralResult<String>(ResultCode.OPERATE_SUCCESS,null,"auth");
    }
    
    @GetMapping("/noauth/test2")
    @ApiOperation(value = "测试接口2", notes = "测试接口2")
    public GeneralResult<String> noauth(){
        return new GeneralResult<String>(ResultCode.OPERATE_SUCCESS,null,"noauth");
    }
}
