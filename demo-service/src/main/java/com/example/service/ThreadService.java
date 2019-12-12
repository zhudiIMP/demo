package com.example.service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

public interface ThreadService {
    void startMain(int id,List<String> list,CopyOnWriteArrayList<ConcurrentHashMap<String,String>> chmList);
}
