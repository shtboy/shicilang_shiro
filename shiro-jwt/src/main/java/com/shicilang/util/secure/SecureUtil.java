package com.shicilang.util.secure;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shicilang
 * @date 2022/2/19 16:45
 * @Version 1.0
 */
public class SecureUtil {
    public static final String ACCOUNTNAME = "accountName";   //账号名
    private static final String AUTH_SECRET = "SEDFGEGEWRWEWYHDG"; //jwt验签盐

    // 生成token
    public static final String mkToken(String accountName) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put(ACCOUNTNAME, accountName);
                put("TIMESTAMP", System.currentTimeMillis());
            }
        };
        return JWTUtil.createToken(map, AUTH_SECRET.getBytes());
    }

    // 验证身份
    public static boolean authUser(String token) {
        try {
            if (StrUtil.isEmpty(token)) return false;
            if (!JWTUtil.verify(token, AUTH_SECRET.getBytes())) return false;
            Map<String, String> authInfo = SecureUtil.getAuthInfo(token);
            if (StrUtil.isEmpty(authInfo.get(ACCOUNTNAME))) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 解析信息
    public static Map<String, String> getAuthInfo(String token) {
        final JWT jwt = JWTUtil.parseToken(token);
        String accountName = jwt.getPayload(ACCOUNTNAME) == null ? null : jwt.getPayload(ACCOUNTNAME).toString();
        Map<String, String> map = new HashMap();
        map.put(ACCOUNTNAME, accountName);
        return map;
    }

    // 解析token  取出accountName
    public static String getAccountName(String token) {
        Map<String, String> authInfo = getAuthInfo(token);
        if (authInfo.isEmpty() || StrUtil.isEmpty(authInfo.get(ACCOUNTNAME)))
            throw new RuntimeException("token未找到accountName");
        return authInfo.get(ACCOUNTNAME);
    }
}
