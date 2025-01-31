package com.czip.crm.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
*
 */
public class LoginUserUtil {

    /**
     * 从cookie中获取userId
     *
     *

     * @param request
     * @return int
     */
    public static long releaseUserIdFromCookie(HttpServletRequest request) {
        String userIdString = CookieUtil.getCookieValue(request, "userIdStr");
        if (StringUtils.isBlank(userIdString)) {
            return 0;
        }
        Long userId = UserIDBase64.decoderUserID(userIdString);
        return userId;
    }
}
