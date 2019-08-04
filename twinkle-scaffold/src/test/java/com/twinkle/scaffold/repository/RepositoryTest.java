package com.twinkle.scaffold.repository;

import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.twinkle.scaffold.AbstractTest;
import com.twinkle.scaffold.component.auth.repo.UserRepo;
import com.twinkle.scaffold.component.auth.repo.domain.ResourceOperation;
import com.twinkle.scaffold.component.auth.repo.domain.Role;
import com.twinkle.scaffold.component.auth.repo.domain.User;
import com.twinkle.scaffold.component.cfig.repo.ConfigEntryRepo;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigEntry;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月27日 下午10:02:07 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class RepositoryTest extends AbstractTest{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ConfigEntryRepo configEntryRepo;
    
    @Test
    public void userRepositoryTest(){
        Optional<User> user1 = userRepo.findById(1L);
        User u = user1.get();
        for (Role role : u.getRoleList()) {
            for (ResourceOperation operation : role.getOperationList()) {
                System.out.println("资源code:"+operation.getResourceCode()+",操作code:"+operation.getCode());
            }
        }
    }
    
    @Test
    public void configEntryRepoTest(){
        Optional<ConfigEntry> configEntry1 = configEntryRepo.findById("MAP_1");
        ConfigEntry configEntry = configEntry1.get();
        System.out.println(JSONObject.toJSONString(configEntry.getConfigItemList()));
    }
}
