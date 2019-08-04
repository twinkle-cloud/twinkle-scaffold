package com.twinkle.scaffold.modules.base.cfig.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.common.utils.ApiParamUtils;
import com.twinkle.scaffold.component.cfig.constants.CfigConstants;
import com.twinkle.scaffold.modules.base.cfig.data.ConfigEntrySearchRes;
import com.twinkle.scaffold.modules.base.cfig.service.CfigService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Cfig API <br/>
 * Date:    2019年8月3日 下午10:14:34 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@RestController
@Slf4j
public class CfigController {
    
    @Autowired
    private CfigService cfigService;
    
    @ApiOperation(value = "配置项查询接口", notes = "配置项查询接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "code",value="配置实体code码",example = "[\"MAP_1\",\"LIST_1\",\"TREE_1\"]"),
        @ApiImplicitParam(name = "scope",value="配置作用范围 app,web",example = "web")})
    @GetMapping("/noauth/v1/cfig/configentrys")
    public GeneralResult<List<ConfigEntrySearchRes>> getConfigEntry(
            @RequestParam(value="code",required=true) String code,
            @RequestParam(value="scope",required=true) String scope){
        if(scope.indexOf(",") >= 0 || scope.indexOf(CfigConstants.CONFIGENTRY_SCOPE_SERVICE) >= 0){
            return new GeneralResult<List<ConfigEntrySearchRes>>(ResultCode.PARAM_INVALID,"scope 非法");
        }
        List<String> codeList = ApiParamUtils.convertToStringList(code);
        List<ConfigEntrySearchRes> configList = cfigService.getConfigEntrysByCodes(codeList,scope);
        return new GeneralResult<List<ConfigEntrySearchRes>>(ResultCode.OPERATE_SUCCESS,null,configList);
    }
}
