package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class BaseController {
    @Autowired
    protected HttpSession httpSession;

    public String getUserAlias() {
        Map<String, Object> userMap = (Map)this.httpSession.getAttribute("portal_user");
        Object userAlias = userMap.get("userAlias");
        if (StringUtils.isEmpty(userAlias)) {
            return null;
        }else{
            return userAlias.toString();
        }
    }
}
