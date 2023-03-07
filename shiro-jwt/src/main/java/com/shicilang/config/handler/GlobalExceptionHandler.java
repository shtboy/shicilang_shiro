package com.shicilang.config.handler;


import com.shicilang.config.exceptions.IdentityException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 处理全局异常
 *
 * @author Song
 * @date 2021/12/10 9:30
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String customException(Exception e) {
        logger.error("Exception:", e);
        if (e instanceof UnauthenticatedException) return "无权限操作";
        if (e instanceof IdentityException) return e.getMessage();
        if (e instanceof UnauthorizedException) return "当前账户无权限";
        return "服务端异常,请联系管理员";
    }
}

