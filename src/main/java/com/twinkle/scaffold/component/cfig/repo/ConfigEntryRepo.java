package com.twinkle.scaffold.component.cfig.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twinkle.scaffold.component.cfig.repo.domain.ConfigEntry;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午8:56:56 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Repository
public interface ConfigEntryRepo extends JpaRepository<ConfigEntry,String> {

    Optional<ConfigEntry> findByCodeAndItemStructureTypeAndScopeContaining(String code,Byte itemStructureType,String scope);
    
    List<ConfigEntry> findByCodeInAndScopeContainingOrderByCodeAsc(Collection<String> codes,String scope);

}
