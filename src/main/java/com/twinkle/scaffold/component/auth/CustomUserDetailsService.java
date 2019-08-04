package com.twinkle.scaffold.component.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.component.auth.repo.UserRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    com.twinkle.scaffold.component.auth.repo.domain.User user = userRepo.findByLoginName(username);
        if (user == null) {
            throw new UsernameNotFoundException("username无效");
        }
        List<String> roles = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(user.getRoleList())){
            for (com.twinkle.scaffold.component.auth.repo.domain.Role role : user.getRoleList()) {
                roles.add(role.getCode());
            }
        }
        UserDetails result =  User.withUsername(username)
            .password(user.getPassword())
            .roles(roles.toArray(new String[roles.size()]))
            .build();
        return result;
	}

}
