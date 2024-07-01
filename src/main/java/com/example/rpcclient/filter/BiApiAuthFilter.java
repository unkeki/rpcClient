package com.example.rpcclient.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.rpcclient.config.BiFeignConfigItemBean;
import com.example.rpcclient.domain.BiFeignConfigItem;
import com.example.rpcclient.enums.BiAuthErrorDesc;
import com.example.rpcclient.enums.NonePrintException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "biApiAuthFilter",urlPatterns = {"/api/*"})
public class BiApiAuthFilter implements Filter {

    @Autowired(required = false)
    BiFeignConfigItemBean biFeignConfigItemBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("bi-jwt-token");
        if(StringUtils.isBlank(token)){
            throw new NonePrintException(BiAuthErrorDesc.api_token_is_null.getCode(),BiAuthErrorDesc.api_token_is_null.getDesc());
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            JSONObject sub = JSONObject.parseObject(jwt.getSubject());
            if(!sub.containsKey("agentCode")){
                throw new NonePrintException(BiAuthErrorDesc.agent_code_is_null.getCode(),BiAuthErrorDesc.agent_code_is_null.getDesc());
            }
            if(!sub.containsKey("appId")){
                throw new NonePrintException(BiAuthErrorDesc.app_id_is_null.getCode(),BiAuthErrorDesc.app_id_is_null.getDesc());
            }
            String agentCode = sub.getString("agentCode");
            String appId = sub.getString("appId");
            Algorithm algorithm = Algorithm.HMAC256(getSecret(agentCode,appId));
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        }catch (JWTVerificationException e){
            log.info("BiApiAuthFilter",e);
            throw new NonePrintException(BiAuthErrorDesc.api_verification_error.getCode(),BiAuthErrorDesc.api_verification_error.getDesc());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getSecret(String agentCode,String appId){
        if(biFeignConfigItemBean.getBiFeignConfigItemList().size()>0){
            BiFeignConfigItem item = biFeignConfigItemBean.getBiFeignConfigItemList()
                    .stream().filter(u -> (u.getAgentCode().equals(agentCode) && u.getAppId().equals(appId))).findFirst().get();
            if(item == null){
                log.error("agentCode:"+agentCode+"获取的配置项为空");
                return "";
            }else{
                return item.getSecret();
            }
        }else{
            log.error("agentCode:"+agentCode+"获取的配置项为空");
            return "";
        }
    }
    @Override
    public void destroy() {

    }
}
