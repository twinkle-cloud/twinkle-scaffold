package com.twinkle.scaffold.modules.biz.moduleA.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twinkle.scaffold.common.constants.AspectConstant;
import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.common.utils.CurrentUserUtils;
import com.twinkle.scaffold.component.apiscy.FreqLimit;
import com.twinkle.scaffold.component.apiscy.LockLimit;
import com.twinkle.scaffold.modules.biz.moduleA.data.DemoData;

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
    public GeneralResult<String> noauth1(){
        log.debug("debug log");
        log.info("info log");
        int i = 1/0;
        return new GeneralResult<String>(ResultCode.OPERATE_SUCCESS,null,"noauth");
    }
    
    @ApiOperation(value = "测试接口3", notes = "测试接口3")
    @PostMapping("/noauth/test3")
    @FreqLimit(key = "#key",intervalTime=1000)
    public GeneralResult<String> noauth2(@RequestParam("key") String key,@RequestBody DemoData demoData){
        return new GeneralResult<String>(ResultCode.OPERATE_SUCCESS,null,"noauth");
    }
    
    @ApiOperation(value = "测试接口4", notes = "测试接口4")
    @PostMapping("/noauth/test4")
    @LockLimit(key = "#key")
    public GeneralResult<String> noauth3(@RequestParam("key") String key,@RequestBody DemoData demoData){
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new GeneralResult<String>(ResultCode.OPERATE_SUCCESS,null,"noauth");
    }
}
