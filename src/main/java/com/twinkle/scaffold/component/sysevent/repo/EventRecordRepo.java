package com.twinkle.scaffold.component.sysevent.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twinkle.scaffold.component.sysevent.repo.domain.EventRecord;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月18日 下午4:46:12 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Repository
public interface EventRecordRepo extends JpaRepository<EventRecord,Long>{

}
