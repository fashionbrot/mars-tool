package com.github.fashionbrot.tool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fashi
 */
public class CookieUtil {


    /**
     * 新增 cookie
     * @param response
     * @param name
     * @param value
     * @param path
     * @param maxAge
     * @param domain
     * @param httpOnly
     * @param secure
     * @param sameSite
     */
    public static void addCookie(HttpServletResponse response, String name, String value, String path, int maxAge, String domain, boolean httpOnly, boolean secure, String sameSite) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);

        response.addCookie(cookie);
        response.addHeader("SameSite",sameSite);
    }


    /**
     * 获取 cookie
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request ,String cookieName){
        Cookie[] cookies = request.getCookies();
        return getCookieValue(cookies,cookieName);
    }


    /**
     * 获取 cookie
     * @param cookieList
     * @param cookieName
     * @return
     */
    public static String getCookieValue(Cookie[] cookieList, String cookieName ) {
        if (ObjectUtil.isNotEmpty(cookieList)){
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    return cookieList[i].getValue();
                }
            }
        }
        return "";
    }


    /**
     * 得到cookie的域名
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName = "";

        String serverName = request.getRequestURL().toString();
        if (ObjectUtil.isEmpty(serverName)) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }
        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }


}
