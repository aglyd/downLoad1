package com.example.download1.utils;

import net.minidev.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(com.example.download1.utils.HttpUtils.class);
//    public static void main(String[] args) {
//        Map<String, Object> paramAll = new HashMap();
//
//        List APPLY_PRODUCT = new ArrayList();
//
//        Map param = new HashMap();
//        param.put("APPLY_NUMBER", "SXBL20200602143500");
//        param.put("PRODUCT_ID", 28);
//        param.put("PRODUCT_NAME", "通宝");
//        param.put("PRODUCT_SUBITEM", "BLRZ01");
//        param.put("PRODUCT_SUBITEM_NAME","保理");
//        param.put("TYPE", "1");
//
//        APPLY_PRODUCT.add(param);
//
//        paramAll.put("APPLY_PRODUCT",APPLY_PRODUCT.get(0));
//
//        System.out.println(paramAll);
//
//        for (Object key : paramAll.keySet()) {
//            String s = String.valueOf(paramAll.get(key));
//            System.out.println(s);
//        }
//        System.out.println((Map) paramAll.get("APPLY_PRODUCT"));
//
//    }


    public static String doGet(String url, Map<String, Object> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 返回结果
        String resultString = "";

        // 执行url之后的响应
        CloseableHttpResponse response = null;

        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);

            // 将参数封装到uri里面
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, String.valueOf(param.get(key)));
                }
            }

            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {

                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public static String doPost(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

        String resultString = "";
        try {
            String jsonString = JSONObject.toJSONString(param);
            //创建Http Post请求包含（请求头，请求行，请求实体）
            HttpPost httpPost = new HttpPost(url);
            //请求头(json)
            httpPost.setHeader("Content-Type","application/json;charset=utf-8");
            //请求头（表单）
//            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
            //将传入参数放入请求实体
            StringEntity postingString = new StringEntity(jsonString,"utf-8");
            //请求实体
            httpPost.setEntity(postingString);

            // 创建参数列表
//            if (param != null) {
//                List<NameValuePair> paramList = new ArrayList<>();
//                for (String key : param.keySet()) {
//                    paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
//                }
//                // 模拟表单
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
//
//                httpPost.setEntity(entity);
//            }

            // 执行http请求，此时发送请求给后端，后端会找到对应地址的controller
            response = httpClient.execute(httpPost);

            //响应的结果转为string
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPostFormData(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

        String resultString = "";
        try {
//            String jsonString = JSONObject.toJSONString(param);
            //创建Http Post请求包含（请求头，请求行，请求实体）
            HttpPost httpPost = new HttpPost(url);
            //请求头（表单）
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
            httpPost.setHeader("Content-Length","57");
            httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            httpPost.setHeader("Accept-Language","Accept-Language");

            //将传入参数放入请求实体
//            StringEntity postingString = new StringEntity(jsonString,"utf-8");
            //请求实体
//            httpPost.setEntity(postingString);

            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"GBK");

                httpPost.setEntity(entity);
            }

            // 执行http请求，此时发送请求给后端，后端会找到对应地址的controller
            response = httpClient.execute(httpPost);

            //响应的结果转为string
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     * 根据登录Cookie获取资源
     * 一切异常均未处理，需要酌情检查异常
     *
     * @throws Exception
     */
    private static void getResoucesByLoginCookies(String url,Map<String,String> param) throws Exception
    {
        StringBuffer paramStr = new StringBuffer();
        if (param != null) {
            for (String key : param.keySet()) {
                paramStr.append("&"+key+"="+param.get(key));
            }
            url+="?"+paramStr.toString().replaceFirst("&","");
        }



        // 登录成功后想要访问的页面 可以是下载资源 需要替换成自己的iteye Blog地址
        String urlAfter = "http://hx.buscoming.cn/Api/Security/GetLoginAccount";

        DefaultHttpClient client = new DefaultHttpClient(new PoolingClientConnectionManager());

        /**
         * 第一次请求登录页面 获得cookie
         * 相当于在登录页面点击登录，此处在URL中 构造参数，
         * 如果参数列表相当多的话可以使用HttpClient的方式构造参数
         * 此处不赘述
         */
        HttpPost post = new HttpPost(url);
        HttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        CookieStore cookieStore = client.getCookieStore();
        client.setCookieStore(cookieStore);
        logger.info("获得cookie：{}",cookieStore.getCookies());

        /**
         * 带着登录过的cookie请求下一个页面，可以是需要登录才能下载的url
         * 此处使用的是iteye的博客首页，如果登录成功，那么首页会显示【欢迎XXXX】
         *
         */
        HttpGet get = new HttpGet(urlAfter);
        response = client.execute(get);
        entity = response.getEntity();

        /**
         * 将请求结果放到文件系统中保存为 myindex.html,便于使用浏览器在本地打开 查看结果
         */

//        String pathName = "d:\\index.html";
//        writeHTMLtoFile(entity, pathName);
    }

    /**
     * Write htmL to file.
     * 将请求结果以二进制形式放到文件系统中保存为.html文件,便于使用浏览器在本地打开 查看结果
     *
     * @param entity the entity
     * @param pathName the path name
     * @throws Exception the exception
     */
    public static void writeHTMLtoFile(HttpEntity entity, String pathName) throws Exception
    {

        byte[] bytes = new byte[(int) entity.getContentLength()];

        FileOutputStream fos = new FileOutputStream(pathName);

        bytes = EntityUtils.toByteArray(entity);

        fos.write(bytes);

        fos.flush();

        fos.close();
    }


    public static String doJsonStringPost(String url, String param){
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();


        CloseableHttpResponse response = null;

        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type","application/json;charset=utf-8");
            StringEntity postingString = new StringEntity(param,"utf-8");
            httpPost.setEntity(postingString);

            // 创建参数列表
//            if (param != null) {
//                List<NameValuePair> paramList = new ArrayList<>();
//                for (String key : param.keySet()) {
//                    paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
//                }
//                // 模拟表单
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
//
//                httpPost.setEntity(entity);
//            }

            // 执行http请求
            response = httpClient.execute(httpPost);

            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPut(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = null;

        String resultString = "";
        try {
            String jsonString = JSONObject.toJSONString(param);
            //创建Http Post请求包含（请求头，请求行，请求实体）
            HttpPut httpPut = new HttpPut(url);
            //请求头
            httpPut.setHeader("Content-Type","application/json;charset=utf-8");
            //将传入参数放入请求实体
            StringEntity postingString = new StringEntity(jsonString,"utf-8");
            //请求实体
            httpPut.setEntity(postingString);


            // 执行http请求，此时发送请求给后端，后端会找到对应地址的controller
            response = httpClient.execute(httpPut);

            //响应的结果转为string
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doDelete(String url, Map<String, Object> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 返回结果
        String resultString = "";

        // 执行url之后的响应
        CloseableHttpResponse response = null;

        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);

            // 将参数封装到uri里面
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, String.valueOf(param.get(key)));
                }
            }

            URI uri = builder.build();

            // 创建http GET请求
            HttpDelete httpDelete = new HttpDelete(uri);

            // 执行请求
            response = httpclient.execute(httpDelete);

            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {

                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static void doGetFileStream(String url, Map<String, Object> param, HttpServletResponse response, HttpServletRequest request) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 返回结果
        byte[] resultByte = null;

        OutputStream outputStream = null;
        // 执行url之后的响应
        CloseableHttpResponse responseResult = null;

        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);

            // 将参数封装到uri里面
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, String.valueOf(param.get(key)));
                }
            }

            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            responseResult = httpclient.execute(httpGet);

            //设置响应头，响应类型
            String fileName = responseResult.getHeaders("Content-Disposition")[0].getValue().split("filename=")[1];
            logger.info("文件名为" + fileName);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            StringBuilder contentDispositionValue = new StringBuilder();
            contentDispositionValue.append("attachment; filename=")
                    .append(fileName)
                    .append(";")
                    .append("filename*=")
                    .append("utf-8''")
                    .append(fileName);

            response.setHeader("Content-disposition", contentDispositionValue.toString());
            // 判断返回状态是否为200
            if (responseResult.getStatusLine().getStatusCode() == 200) {

                resultByte = EntityUtils.toByteArray(responseResult.getEntity());
                outputStream = response.getOutputStream();
                outputStream.write(resultByte);
                logger.info("文件下载成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (responseResult != null) {
                    responseResult.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void doGetPicStream(String url) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 返回结果
        byte[] resultByte = null;

        OutputStream outputStream = null;
        // 执行url之后的响应
        CloseableHttpResponse responseResult = null;

        File file = null;

        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);


            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            responseResult = httpclient.execute(httpGet);

            //设置响应头，响应类型
            String fileName = url.substring(url.indexOf("douluodalu1")+12);
            fileName = "1.txt";
            logger.info("文件名为" + fileName);
//            response.setContentType("image/jpeg");
//            StringBuilder contentDispositionValue = new StringBuilder();
//            contentDispositionValue.append("attachment; filename=")
//                    .append(fileName)
//                    .append(";")
//                    .append("filename*=")
//                    .append("utf-8''")
//                    .append(fileName);
//
//            response.setHeader("Content-disposition", contentDispositionValue.toString());
            // 判断返回状态是否为200
            int statusCode = responseResult.getStatusLine().getStatusCode();
            if (statusCode == 200) {

                resultByte = EntityUtils.toByteArray(responseResult.getEntity());
                file = new File("F:\\abcd\\download\\" + fileName);
                if (!file.getParentFile().exists()){
                    file.mkdirs();
                }
                outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(resultByte);
                logger.info("文件下载成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (responseResult != null) {
                    responseResult.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getHttpPic(String url) {
        OutputStream out = null;
        InputStream is = null;
        if (StringUtils.isEmpty(url) || url.trim().equals("")) {
            return;
        }
        try {
            URL realUrl = new URL(url);
            File file = null;

            String capt = url.substring(url.indexOf("douluodalu1")+12,url.indexOf("douluodalu1")+18);
            String fileName = url.substring(url.indexOf("douluodalu1")+19);
            logger.info(capt+"文件名为" + fileName);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(1000*5);   //设置连接主机超时（单位：毫秒）
            connection.setReadTimeout(1000*5);      //设置从主机读取数据超时（单位：毫秒）
            // 建立实际的连接
            connection.connect();
            //请求成功

            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                //String filePath = "F:\\downLoad\\douluodalu1\\"+capt+"\\"+ fileName;
                //file = new File(filePath);
                file = new File("F:\\downLoad\\douluodalu2\\"+capt+"\\",fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }


                out = new FileOutputStream(file);
                //10MB的缓存
                byte[] buffer = new byte[10485760];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }

                //转换成json数据处理

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (out != null){
                    out.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}


