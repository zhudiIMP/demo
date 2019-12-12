package com.example.service.impl;

import com.example.async.AsyncMainService;
import com.example.async.AsyncService;
import com.example.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

@Service("ThreadService")
public class ThreadServiceImpl implements ThreadService {
    private static final Logger logger = LoggerFactory.getLogger(ThreadServiceImpl.class);

    @Autowired
    private AsyncMainService asyncMainService;

    public void startMain(int id, List<String> list, CopyOnWriteArrayList<ConcurrentHashMap<String,String>> chmList){
        try{
            logger.info("startMain  start");
            CountDownLatch latchMain = new CountDownLatch(list.size());
            for(String str : list){
                ConcurrentHashMap<String,String> chm = new ConcurrentHashMap<>();
                asyncMainService.executeMainAsync(id,latchMain,chm);
                chmList.add(chm);
            }
            latchMain.await();
            logger.info("startMain  finished");
        }catch (Exception e){
        }
    }
}
