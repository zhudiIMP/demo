package com.example.api;

import com.example.dto.commndto.response.Head;
import com.example.dto.commndto.response.ResponseVo;
import com.example.dto.request.DemoRequest;
import com.example.dto.response.DemoResponse;
import com.example.service.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

@RestController
public class DemoApiImpl extends BaseController implements DemoApi {
    private static final Logger logger = LoggerFactory.getLogger(DemoApiImpl.class);
    @Autowired
    private ThreadService threadService;

    @RequestMapping(value = "/demo/query", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVo<DemoResponse> productFeeQuery(@RequestBody @Validated DemoRequest demoRequest){
        DemoResponse demoResponse = new DemoResponse();
        return ResponseVo.success(new Head(),  demoResponse);
    }

    @RequestMapping(value = "/demo/thread", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ConcurrentHashMap<String,String>> thread(String arg){
        Random r = new Random();
        int id = r.nextInt(1000);
        List<String> list = new ArrayList<>();
        for(int i=0;i<=2;i++){
            list.add(""+i);
        }
        logger.info("id="+id+" 接口响应开始");
        logger.info("根据redis返回结果，决定是否响应");
        CopyOnWriteArrayList<ConcurrentHashMap<String,String>> chmList = new CopyOnWriteArrayList<>();
        ConcurrentHashMap<String,String> mapId = new ConcurrentHashMap<>();
        mapId.put("id",""+id);
        chmList.add(mapId);
        threadService.startMain(id,list,chmList);
        for(Map<String,String> map : chmList){
            logger.info(map.toString());
        }
        logger.info("id="+id+" 接口响应结束");
        return chmList;
    }
}
