package com.example.download1.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTxt {

    private static final Logger logger = LoggerFactory.getLogger(GetTxt.class);

    //单个小说文件下载
    //** 1、根据小说存放位置创建file对象2、根据网页结构编写正则，创建pattern对象3、编写循环，创建向所有小说章节页面发起网络请求的url对象4、网络流BufferReader5、创建输入流6、循环读取请求得到的内容，使用正则匹配其中的内容7、将读取到的内容写入本地文件，知道循环结束8、注意代码中的异常处理* @param args//
    public static void downTxt(String url) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 执行url之后的响应
        CloseableHttpResponse responseResult = null;

        // 1、根据小说存放位置创建file对象
        File file = new File("F:\\downLoad\\xiaoshuo\\xiaoshuo2.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 2、根据网页结构编写正则，创建pattern对象
        String regex_content = "<p.*?>(.*?)</p>";
        String regex_title = "<title>(.*?)</title>";
        Pattern p_content = Pattern.compile(regex_content);
        Pattern p_title = Pattern.compile(regex_title);
        Matcher m_content;
        Matcher m_title = null;
        // 3、编写循环，创建向所有小说章节页面发起网络请求的url对象
        for (int i = 1; i <= 1; i++) {
            logger.info("第" + i + "章开始下载。。。");
            try {

                // 创建uri
                URIBuilder builder = new URIBuilder(url);

                URI uri = builder.build();

                // 创建http GET请求
                HttpGet httpGet = new HttpGet(uri);

                // 执行请求
                responseResult = httpclient.execute(httpGet);
                // 创建每一个页面的url对象

                int statusCode = responseResult.getStatusLine().getStatusCode();

                if (statusCode == 200) {
                    // 创建网络读取流
                    BufferedReader reader = new BufferedReader(new StringReader(EntityUtils.toString(responseResult.getEntity(), "utf8")));

                    // 4、读取网络内容网络流BufferReader
                    String str = null;
                    // 5、创建输入流
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

                    while ((str = reader.readLine()) != null) {
                        m_title = p_title.matcher(str);

                        m_content = p_content.matcher(str);
                        // 获取小说标题并写入本地文件
                        Boolean isEx = m_title.find();
                        if (isEx) {
                            String title = m_title.group();
                            // 清洗得到的数据
                            title = title.replace("<title>", "").replace("</title>", "");
                            logger.info(title);
                            writer.write("第" + i + "章：" + title + "\n");
                        }
                        while (m_content.find()) {
                            String content = m_content.group();
                            // 清洗得到的数据
                            content = content.replace("<p>", "").replace("</p>", "").replace("&nbsp;", "").replace("?", "");
                            // 把小说内容写入文件
                            writer.write(content + "\n");
                        }

                    }
                        logger.info("第" + i + "章下载完成.........");
                        writer.write("\n\n");
                        writer.close();
                        reader.close();

                    }

                } catch(Exception e){
                    logger.info("下载失败");
                    e.printStackTrace();
                }
            }

    }
    
}
