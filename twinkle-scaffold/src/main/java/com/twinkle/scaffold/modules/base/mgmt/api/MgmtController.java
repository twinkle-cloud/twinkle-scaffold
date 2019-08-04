package com.twinkle.scaffold.modules.base.mgmt.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月23日 下午8:36:30 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@RestController
public class MgmtController {
    
    @GetMapping("/test")
    public String test(){
        return "test";
    }
    
}
