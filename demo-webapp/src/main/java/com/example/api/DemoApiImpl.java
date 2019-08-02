package com.example.api;

import com.example.dto.commndto.response.Head;
import com.example.dto.commndto.response.ResponseVo;
import com.example.dto.request.DemoRequest;
import com.example.dto.response.DemoResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class DemoApiImpl extends BaseController implements DemoApi {

    @RequestMapping(value = "/productFee/query", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVo<DemoResponse> productFeeQuery(@RequestBody @Validated DemoRequest demoRequest){
        DemoResponse demoResponse = new DemoResponse();
        return ResponseVo.success(new Head(),  demoResponse);
    }
}
