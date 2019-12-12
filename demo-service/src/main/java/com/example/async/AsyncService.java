package com.example.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
@Service("AsyncService")
public class AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);
    @Async
    public void executeAsync(int id,CountDownLatch latch,ConcurrentHashMap<String,String> chm) {
        try{
            long start = System.currentTimeMillis();
            Thread.sleep(1000);
            long end = System.currentTimeMillis();
            logger.info("id="+id+" 查库校验1完成"+" 耗时：" + (end - start) + "毫秒");
            chm.put("step1","ok");
        }catch (Exception e){

        }finally {
            latch.countDown();
        }

    }

    @Async
    public void executeAsync1(int id,CountDownLatch latch,ConcurrentHashMap<String,String> chm) {
        try{
            long start = System.currentTimeMillis();
            Thread.sleep(2000);
            long end = System.currentTimeMillis();
            logger.info("id="+id+" 查库校验2完成"+" 耗时：" + (end - start) + "毫秒");
            chm.put("step2","ok");
        }catch (Exception e){

        }finally {
            latch.countDown();
        }
    }

    @Async
    public void executeAsync2(int id,CountDownLatch latch,ConcurrentHashMap<String,String> chm) {
        try{
            long start = System.currentTimeMillis();
            Thread.sleep(1000);
            long end = System.currentTimeMillis();
            logger.info("id="+id+" 查库校验3完成"+" 耗时：" + (end - start) + "毫秒");
            chm.put("step3","ok");
        }catch (Exception e){

        }finally {
            latch.countDown();
        }
    }

    @Async
    public void executeAsync3(int id,CountDownLatch latch,ConcurrentHashMap<String,String> chm) {
        try{
            long start = System.currentTimeMillis();
            Thread.sleep(4000);
            long end = System.currentTimeMillis();
            logger.info("id="+id+" 接口校验1完成"+" 耗时：" + (end - start) + "毫秒");
            chm.put("step4","ok");
        }catch (Exception e){

        }finally {
            latch.countDown();
        }
    }

    @Async
    public void executeAsync4(int id,CountDownLatch latch,ConcurrentHashMap<String,String> chm) {
        try{
            long start = System.currentTimeMillis();
            Thread.sleep(5000);
            long end = System.currentTimeMillis();
            logger.info("id="+id+" 接口校验2完成"+" 耗时：" + (end - start) + "毫秒");
            chm.put("step5","ok");
        }catch (Exception e){

        }finally {
            latch.countDown();
        }
    }

    @Async
    public void executeAsync5(int id,CountDownLatch latch,ConcurrentHashMap<String,String> chm) {
        try{
            long start = System.currentTimeMillis();
            Thread.sleep(3000);
            long end = System.currentTimeMillis();
            logger.info("id="+id+" 接口校验3完成"+" 耗时：" + (end - start) + "毫秒");
            chm.put("step6","ok");
        }catch (Exception e){

        }finally {
            latch.countDown();
        }
    }
}
