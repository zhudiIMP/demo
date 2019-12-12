package com.example.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@Service("AsyncMainService")
public class AsyncMainService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncMainService.class);
    @Autowired
    private AsyncService asyncService;
    @Async
    public void executeMainAsync(int id, CountDownLatch latchMain,ConcurrentHashMap<String,String> chm) {

        try {
            logger.info("id="+id+" start main thread");
            logger.info("id="+id+" 进行不查库的入参校验和组合校验。。。");
            CountDownLatch latch = new CountDownLatch(6);
            asyncService.executeAsync(id,latch,chm);
            asyncService.executeAsync1(id,latch,chm);
            asyncService.executeAsync2(id,latch,chm);
            asyncService.executeAsync3(id,latch,chm);
            asyncService.executeAsync4(id,latch,chm);
            asyncService.executeAsync5(id,latch,chm);
            latch.await();
            logger.info("id="+id+" end main thread");
        }catch (Exception e){
        }finally {
            latchMain.countDown();
        }
    }
}
