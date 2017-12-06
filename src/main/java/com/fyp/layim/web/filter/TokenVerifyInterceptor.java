package com.fyp.layim.web.filter;

import com.fyp.layim.domain.result.JsonResult;
import com.fyp.layim.service.auth.TokenVerify;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.tio.utils.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenVerifyInterceptor extends HandlerInterceptorAdapter {
    private final static Logger logger = LoggerFactory.getLogger(TokenVerifyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        boolean flag = false;
        if(StringUtils.isNotBlank(token)){
            String userId = TokenVerify.IsValid(token);
            flag = userId != null;
            if(flag){
                request.setAttribute("LAYIM_UID",userId);
            }
        }
        //验证没有通过
        if(!flag){
            response.setStatus(HttpStatus.OK.value());
            //解决乱码问题
            response.setContentType("application/json;charset=utf-8");
            JsonResult result = JsonResult.fail("invalid token");
            response.getWriter().print(Json.toJson(result));
        }
        return flag;

    }
}
