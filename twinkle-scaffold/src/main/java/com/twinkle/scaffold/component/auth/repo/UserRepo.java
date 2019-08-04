package com.twinkle.scaffold.component.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twinkle.scaffold.component.auth.repo.domain.User;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月27日 下午9:40:35 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long>{

    User findByLoginName(String loginName);
}
