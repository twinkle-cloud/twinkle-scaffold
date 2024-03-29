package com.twinkle.scaffold.component.file.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.twinkle.scaffold.component.file.repo.domain.FileEntry;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月10日 下午10:15:03 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Repository
public interface FileEntryRepo extends JpaRepository<FileEntry,String>{

    @Transactional
    void deleteByIdIn(String[] ids);
    
    List<FileEntry> findByIdIn(String[] ids);
}
