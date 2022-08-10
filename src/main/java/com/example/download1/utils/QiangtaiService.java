package com.example.download1.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

public class QiangtaiService {

    private static final Logger logger = LoggerFactory.getLogger(QiangtaiService.class);


    /**
     * 根据登录Cookie获取资源
     * 一切异常均未处理，需要酌情检查异常
     *
     * @throws Exception
     */
    public static String getResoucesByLoginCookies(String url, Map<String, Object> param) throws IOException, InterruptedException {

        DefaultHttpClient client = new DefaultHttpClient(new PoolingClientConnectionManager());

        String resultStr = "";
        String user_id = "623758";  //老婆的id
        /**
         * 第一次请求登录页面 获得cookie
         * 相当于在登录页面点击登录，此处在URL中 构造参数，
         * 如果参数列表相当多的话可以使用HttpClient的方式构造参数
         * 此处不赘述
         */

        /**
         * 带着登录过的cookie请求下一个页面，可以是需要登录才能下载的url
         * 此处使用的是iteye的博客首页，如果登录成功，那么首页会显示【欢迎XXXX】
         *
         */
        //模拟表单，放置登录参数
        List<NameValuePair> paramList = new ArrayList<>();
        if (param.get("loginParam") != null) {
            Map<String,String> loginParam = (Map) param.get("loginParam");
            for (String key : loginParam.keySet()) {
                paramList.add(new BasicNameValuePair(key,loginParam.get(key)));
            }
            if ("18797777204".equals(loginParam.get("email"))) {
                user_id = "1086056";    //我的id
            }
        }

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList,"utf-8");
        HttpPost loginPost = new HttpPost(url);
        loginPost.setEntity(formEntity);
        HttpResponse loginResponse = client.execute(loginPost);
        logger.info("登录请求返回：{}",loginResponse.getFirstHeader("location"));
//        loginResponse.getEntity().getContent().close();//关闭结果集

//        CookieStore cookieStore = client.getCookieStore();
//        client.setCookieStore(cookieStore);


        //此参数方式不可行
        /*  List<NameValuePair> paramList2 = new ArrayList<>();
        paramList2.add(new BasicNameValuePair("user_id","1086056"));
        paramList2.add(new BasicNameValuePair("mendianbianhao","317340"));
        paramList2.add(new BasicNameValuePair("create_time",String.valueOf(getSecondTimestamp(new Date()))));
        paramList2.add(new BasicNameValuePair("yuding_ri","1657728000"));//7月14日
        paramList2.add(new BasicNameValuePair("yuding_shi[]","1274,19:00"));
        paramList2.add(new BasicNameValuePair("yuding_shi[]","1274,19:30"));
        paramList2.add(new BasicNameValuePair("room_id",""));
        ;*/

        //获取后天的时间戳
        final Long dayAfterTomorrowTime = getDayAfterTomorrow(2);
        int dayAfterTomorrowSecond = getSecondTimestamp(dayAfterTomorrowTime);
        Long dayOfTomorrowTime = getDayAfterTomorrow(1);    //createTime明天零时
        logger.info("预订时间戳ri：{}，create_time：{}", dayAfterTomorrowSecond,getSecondTimestamp(dayOfTomorrowTime));
        //组装请求参数信息
        List<HttpPost> httpPostList = null;
        if (null != param.get("timeParam")) {
           httpPostList = getHttpPostDataList((List<List<String>>) param.get("timeParam"), dayAfterTomorrowSecond,getSecondTimestamp(dayOfTomorrowTime), user_id);
        }



        int roundCount = 0;
        //请求调用时间倒计时：到零点时：当前时间+1的零时 = （提前获取的）后天零时
        /*while (true)
        {
            dayOfTomorrowTime = getDayAfterTomorrow(1);
            roundCount++;
            if (dayOfTomorrowTime >= dayAfterTomorrowTime){
                break;
            }
            Thread.sleep(10);
        }*/
        logger.info("当前时间戳：{},循环次数为：{}", dayOfTomorrowTime, roundCount);

        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        List<Future> futureList = new ArrayList<>();
        for (HttpPost httpPost:httpPostList) {
          /*  logger.info("开始抢台：{}",httpPost);
            HttpResponse response = client.execute(httpPost);
            logger.info("抢台结束：{}",response.getFirstHeader("location"));
            response.getEntity().getContent().close();//关闭结果集*/

            executorService.execute(() -> {
                HttpResponse response = null;
                try {
                    response = client.execute(httpPost);
                    logger.info("{}抢台结束返回结果集：{}",Thread.currentThread().getName(),response.getFirstHeader("location"));
                    response.getEntity().getContent().close();//关闭结果集
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                logger.info("抢台结束返回结果：{}", response.getFirstHeader("location"));
//                final String location = response.getFirstHeader("location").toString();
//                if (location.startsWith("Location: /wap/yuyue_chenggong_quanbu.php")) {
//                    logger.info("预约成功！");
//                }else {       //失败：Location: /wap/mendian_yuyue_quanbu.php
//                    logger.info("预约失败！抢台结束返回结果：{}", location);
//                }
            });
        }
       /* for (Future future:futureList) {
            try {
                logger.info("获得返回结果future.get："+future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }*/


        /**
         * 将请求结果放到文件系统中保存为 myindex.html,便于使用浏览器在本地打开 查看结果
         */
//        String pathName = "d:\\index.html";
//        writeHTMLtoFile(entity, pathName);
//        executorService.shutdown();

//        try {
//            invokeRemote(httpPostList,client);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return resultStr;
    }


    /**
     * 多线程并发请求，并获取每个线程的返回结果
     * @param httpPostList
     * @param client
     * @throws Exception
     */
    public static void invokeRemote(List<HttpPost> httpPostList, HttpClient client) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 20, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(15),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        Set <Callable< String >> callable = new HashSet <> ();
        for (HttpPost httpPost :httpPostList) {
            callable.add(new Callable < String > () {
                public String call() throws Exception {
                    logger.info("开始抢台：{}",Thread.currentThread().getName());
                    HttpResponse response = client.execute(httpPost);
                    logger.info("抢台结束返回结果：{}", response.getFirstHeader("location"));
                    return Thread.currentThread().getName()+",返回结果：";
                }
            });
        }

        List <Future< String >> futures = executorService.invokeAll(callable);
//        final List<Future<String>> futures = executor.invokeAll(callable);

        logger.info("调用结束，开始返回");
        for (Future < String > future: futures) {
            logger.info("future.get = {}", future.get(30, TimeUnit.SECONDS));
        }
        executorService.shutdown();
    }

    /**
     * 组装httppost请求信息
     * @param timeParam 预定信息
     * @param day       请求日期
     * @param user_id   用户id
     * @return
     * @throws UnsupportedEncodingException
     */
    public static List<HttpPost> getHttpPostDataList(List<List<String>> timeParam,int day,int createTime,String user_id) throws UnsupportedEncodingException {
        List<HttpPost> httpPostList = new ArrayList<>();
        String qiangtaiUrl = "https://www.sekahui.com/wap/room_yuyue_quanbu.php?mendianbianhao=317340";
        for (List<String> timeParamList:timeParam) {
            createTime++;
            HttpPost httpPost = new HttpPost(qiangtaiUrl);
            httpPost.setHeader("Content-Type","multipart/form-data; boundary=----WebKitFormBoundary5LR8iB0TeBYrPc4x");
            //抢台参数设定
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, "----WebKitFormBoundary5LR8iB0TeBYrPc4x", Charset.defaultCharset());
            multipartEntity.addPart("user_id", new StringBody(user_id, Charset.forName("UTF-8")));
            multipartEntity.addPart("mendianbianhao", new StringBody("317340", Charset.forName("UTF-8")));  //实验室编号
            multipartEntity.addPart("create_time", new StringBody(String.valueOf(createTime), Charset.forName("UTF-8")));
            multipartEntity.addPart("yuding_ri", new StringBody(String.valueOf(day), Charset.forName("UTF-8")));   //待抢日期时间戳
            for (String timeParamItem:timeParamList) {
                multipartEntity.addPart("yuding_shi[]", new StringBody(timeParamItem, Charset.forName("UTF-8")));
            }
            multipartEntity.addPart("room_id", new StringBody("", Charset.forName("UTF-8")));
            httpPost.setEntity(multipartEntity);
            httpPostList.add(httpPost);
        }
        return httpPostList;
    }

    /**
     * 将时间戳精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Long time){
        if (null == time) {
            return 0;
        }
        String timestamp = String.valueOf(time);
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

    /**
     * 精确到毫秒的时间戳
     * @param date
     * @return
     */
    public static Long getTime(Date date){
        Long now = date.getTime();      //当前日期的时间戳
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        //可以设定特定时间
//        calendar.add(Calendar.DAY_OF_MONTH,+2); //后天
//        calendar.set(Calendar.DAY_OF_WEEK, 2);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.add(Calendar.SECOND,5);
        return calendar.getTimeInMillis();
    }

    /**
     * 获得明天或者后天的零时时间戳
     * @return
     */
    public static Long getDayAfterTomorrow(int day) {
        DateTime now = new DateTime();
        return new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0, 0, 0).plusDays(day).getMillis();
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


    public void testTime() throws InterruptedException {
        Date date = new Date();
        final long time = date.getTime();
        System.out.println(time+"///seconde"+getSecondTimestamp(time));
        final Long FiveSecondTime = getTime(date);
        System.out.println("5秒后时间："+FiveSecondTime);
        Long count = 0L;
        long now = 0L;
        while (true) {
            now = new Date().getTime();
            if (now >= FiveSecondTime) {
                break;
            }
            count++;
            Thread.sleep(10);
        }
        logger.info("当前时间为：{},循环次数:{}",now,count);
    }

    public static int addIntNum(int count){
        Integer i = count;
        i+=2;
        count++;
        System.out.println("add:i:"+i+",count:"+count);
        Integer i2 = i;
        i2++;
        System.out.println("i:"+i+",i2:"+i2);
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        final QiangtaiService qiangtaiService = new QiangtaiService();
//        qiangtaiService.testTime();
//        System.out.println(getSecondTimestamp(getDayAfterTomorrow(1)));
        int i = 0;
        final int i1 = addIntNum(i);
        System.out.println(i+"//"+i1);
    }
}
