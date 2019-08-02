package com.example.api;

import com.example.dto.commndto.response.ResponseVo;
import com.example.dto.request.DemoRequest;
import com.example.dto.response.DemoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description:费用查询接口api
 * @Version1.0 19/03/14
 */
@FeignClient(name = "cmis-product-webapp")
public interface DemoApi {
    @RequestMapping(value = "/productFee/query", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseVo<DemoResponse> productFeeQuery(@RequestBody @Validated DemoRequest demoRequest);

}
