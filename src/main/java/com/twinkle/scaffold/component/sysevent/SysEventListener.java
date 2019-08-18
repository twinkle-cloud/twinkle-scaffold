package com.twinkle.scaffold.component.sysevent;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.common.data.GeneralEvent;
import com.twinkle.scaffold.common.data.sysevent.SysExceptionData;
import com.twinkle.scaffold.common.data.sysevent.SysTimeoutData;
import com.twinkle.scaffold.component.sysevent.repo.EventRecordRepo;
import com.twinkle.scaffold.component.sysevent.repo.domain.EventRecord;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统事件监听器 <br/>
 * Date:    2019年8月18日 下午2:59:58 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Slf4j
public class SysEventListener {

    @Autowired
    private EventRecordRepo eventRecordRepo;
    
    @EventListener(condition="(#event.data instanceof T(com.twinkle.scaffold.common.data.sysevent.SysExceptionData)) "
            + "or (#event.data instanceof T(com.twinkle.scaffold.common.data.sysevent.SysTimeoutData))")
    @Async("sysEventAsync")
    public void handleSysEvent(GeneralEvent event){
        log.info("handleSysEvent sysEvent = {}",event);
        EventRecord eventRecord = new EventRecord();
        eventRecord.setCode(event.getCode());
        eventRecord.setData(event.getData());
        if(event.getData() instanceof SysExceptionData){
            SysExceptionData sysExceptionData = (SysExceptionData)event.getData();
            eventRecord.setClassName(sysExceptionData.getClassSimpleName());
            eventRecord.setMethodName(sysExceptionData.getMethodName());
            if(sysExceptionData.getException() != null){
                String errorDetail = ExceptionUtils.getStackTrace(sysExceptionData.getException());
                eventRecord.setErrorDetail(errorDetail);
                sysExceptionData.setException(null);
            }
        }
        if(event.getData() instanceof SysTimeoutData){
            SysTimeoutData sysTimeoutData = (SysTimeoutData)event.getData();
            eventRecord.setClassName(sysTimeoutData.getClassSimpleName());
            eventRecord.setMethodName(sysTimeoutData.getMethodName());
            eventRecord.setInvokeTime(sysTimeoutData.getInvokeTime());
        }
        eventRecordRepo.saveAndFlush(eventRecord);
    }
    
}
