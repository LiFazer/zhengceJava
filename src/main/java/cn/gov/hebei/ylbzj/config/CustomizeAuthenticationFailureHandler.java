package cn.gov.hebei.ylbzj.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("code",100);

        String result = "";
        if (e instanceof BadCredentialsException) {
            result = "账号或密码错误";
        } else if (e instanceof InternalAuthenticationServiceException) {
            result = "该用户不存在";
        } else {
            result = "账号或密码错误";
        }
        json.put("msg",result);
        httpServletResponse.getWriter().write(JSON.toJSONString(json));
    }
}











