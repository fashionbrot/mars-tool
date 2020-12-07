package com.github.fashionbrot.tool;

import com.github.fashionbrot.tool.constant.CharsetConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author fashionbrot
 * @date 2020/12/07
 */
@Slf4j
public class HttpClientUtil {

    private HttpClientUtil(){

    }

    public static HttpResult httpGet(String url){
        return httpGet(url,null,null,CharsetConstant.UTF_8,2000,2000);
    }

    public static HttpResult httpGet(String url,String charset){
        return httpGet(url,null,null,charset,2000,2000);
    }

    public static HttpResult httpGet(String url,String charset,List<String> headers){
        return httpGet(url,headers,null,charset,2000,2000);
    }

    public static HttpResult httpGet(String url,String charset,List<String> headers,List<String> paramValues){
        return httpGet(url,headers,paramValues,charset,2000,2000);
    }

    public static HttpResult httpGet(String url,String charset,List<String> headers,int readTimeoutMs,int connectTimeOutMs){
        return httpGet(url,headers,null,charset,readTimeoutMs,connectTimeOutMs);
    }

    public static HttpResult httpGet(String url,String charset,int readTimeoutMs,int connectTimeOutMs){
        return httpGet(url,null,null,charset,readTimeoutMs,connectTimeOutMs);
    }



    /**
     * 发送GET请求
     * @param url               url
     * @param headers           header
     * @param paramValues       value
     * @param encoding          encoding
     * @param readTimeoutMs     readTimeoutMs
     * @param connectTimeout    connectTimeout
     * @return HttpResult
     */
    public static  HttpResult httpGet(String url,Object headers, List<String> paramValues,String encoding, int readTimeoutMs,int connectTimeout) {

        String encodedContent = encodingParams(paramValues);
        url += (null == encodedContent) ? "" : ("?" + encodedContent);

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout( readTimeoutMs);

            setHeaders(conn, headers);
            conn.connect();

            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            if (inputStream != null) {
                resp = toString(inputStream, encoding);
            }
            return new HttpResult(respCode, resp);
        } catch (Exception e){
            log.error("httpGet error url:{} msg:{}", url, e.getMessage());
            return new HttpResult(500, e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error");
                }
            }
        }
    }


    /**
     * GET 请求
     * @param url           url
     * @param headers       header
     * @param paramValues   value
     * @return HttpResult
     */
    public static  HttpResult httpGet(String url, List<String> headers, List<String> paramValues){
        return httpGet(url,headers,paramValues, CharsetConstant.UTF_8,5000,5000);
    }


    /**
     * 发送 POST 请求
     * @param url
     * @param headers
     * @param paramValues
     * @param encoding
     * @param readTimeoutMs
     * @param connectTimeoutMs
     * @return
     */
    public static HttpResult httpPost(String url, Map<String,Object> headers,Map<String,Object> paramValues,
                                      String encoding, int readTimeoutMs,int connectTimeoutMs){
        String content = encodingParams(headers);
        return httpPost(url,headers,content,encoding,readTimeoutMs,connectTimeoutMs);
    }

    /**
     * 发送 POST 请求
     * @param url
     * @param headers
     * @param paramValues
     * @param encoding
     * @param readTimeoutMs
     * @param connectTimeoutMs
     * @return
     */
    public static HttpResult httpPost(String url, List<String> headers, List<String> paramValues,
                                              String encoding, int readTimeoutMs,int connectTimeoutMs){
        String content = encodingParams(paramValues);
        return httpPost(url,headers,content,encoding,readTimeoutMs,connectTimeoutMs);
    }

    /**
     * 发送 POST 请求
     * @param url               url
     * @param headers           header
     * @param encoding          encoding
     * @param readTimeoutMs     readTimeoutMs
     * @param connectTimeoutMs  connectTimeoutMs
     * @return HttpResult
     */
    static public HttpResult httpPost(String url,
                                      Object headers,
                                      String content,
                                      String encoding,
                                      int readTimeoutMs,
                                      int connectTimeoutMs){

        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(connectTimeoutMs);
            conn.setReadTimeout(readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            setHeaders(conn, headers);

            outputStream = conn.getOutputStream();
            if (outputStream != null) {
                outputStream.write(StringUtil.isNotEmpty(content) ? content.getBytes() : ArrayUtil.EMPTY_BYTE_ARRAY);
                outputStream.flush();
            }
            int respCode = conn.getResponseCode();
            String responseBody = null;
            if (inputStream != null) {
                responseBody = toString(inputStream, encoding);
            }
            return new HttpResult(respCode, responseBody);
        }catch (Exception e){
            log.error("httpPost error url:{} msg:{}", url, e.getMessage());
            return new HttpResult(500, "Server Internal Error");
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error");
                }
            }
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close error");
                }
            }
        }
    }

    /**
     * 发送POST
     * @param url           URL
     * @param headers       header
     * @param paramValues   value
     * @return HttpResult
     */
    public static  HttpResult httpPost(String url, List<String> headers, List<String> paramValues) {
        return httpPost(url, headers, paramValues, CharsetConstant.UTF_8, 5000,5000);
    }

    private static void setHeaders(HttpURLConnection conn,Object headers) {
        if (headers!=null){
            if (headers instanceof List){
                List<String> headList= (List<String>) headers;
                if (CollectionUtil.isNotEmpty(headList)) {
                    for (Iterator<String> iter = headList.iterator(); iter.hasNext(); ) {
                        conn.addRequestProperty(iter.next(), iter.next());
                    }
                }
            }else if (headers instanceof Map){
                Map<String,Object> objectMap = (Map<String, Object>) headers;
                if (CollectionUtil.isNotEmpty(objectMap)){
                    objectMap.forEach((k,v)->{
                        conn.addRequestProperty(k,v+"");
                    });
                }
            }
        }
        conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="+CharsetConstant.UTF_8);
    }


    private static String encodingParams(List<String> paramValues) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtil.isEmpty(paramValues)) {
            return null;
        }
        for (Iterator<String> iter = paramValues.iterator(); iter.hasNext(); ) {
            sb.append(iter.next()).append("=");
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    private static String encodingParams(Map<String,Object> paramValues) {
        if (CollectionUtil.isEmpty(paramValues)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        paramValues.forEach((k,v)-> {
            if (sb.length()!=0){
                sb.append("&");
            }
            sb.append(k).append("=").append(v+"");
        });
        return sb.toString();
    }


    private static  String toString(InputStream input, String encoding)  {
        try {
            return CharStreamUtil.toString(new InputStreamReader(input,encoding==null? CharsetConstant.UTF_8:encoding));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();
        map.put("1","1");
        map.put("test",2);
        map.put("test2",212321);
        System.out.println(encodingParams(map));
    }
}
