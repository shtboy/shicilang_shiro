package com.shicilang.config.filter;

import cn.hutool.core.util.StrUtil;
import com.shicilang.config.exceptions.IdentityException;
import com.shicilang.util.secure.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author shicilang
 * @create 2019-02-01 15:56
 * @desc 鉴权请求URL访问权限拦截器
 */
@Slf4j
public class UrlPermissionFilter extends AccessControlFilter {
    /**
     * 表示是否允许访问 ，如果允许访问返回true，否则false； 这里的o参数 有兴趣的可以研究讨论下
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String url = getPathWithinApplication(servletRequest);
        if ("/error".equals(url)) return true;
        log.info("当前用户正在访问的 url => " + url);
        // 先判断一下是不是登录  验证token
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        String token = servletRequest1.getHeader("Authorization");
        if (!"/login".equals(url) && StrUtil.isEmpty(token)) {
            IdentityException identityException = new IdentityException("请求头缺少或携带无效标识:Authorization");
            servletRequest.setAttribute("noneAuthorization", identityException);
            throw identityException;
        }
        if (StrUtil.isNotEmpty(token)) {
            if (!SecureUtil.authUser(token)) throw new RuntimeException("token无效错误");
        }
        return subject.isPermitted(url);
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了； 如果返回 true 表示需要继续处理； 如果返回 false
     * 表示该拦截器实例已经处理了，将直接返回即可。
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        String msg = SecurityUtils.getSubject().isAuthenticated() ? "当前账户无接口["+servletRequest1.getRequestURI()+"]权限操作" : "账户登录态失效,请在登录后重试";
        IdentityException exception = new IdentityException(msg);
        servletRequest1.setAttribute("identityException", exception);
        throw new RuntimeException(exception);
    }

}