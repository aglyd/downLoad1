package com.example.download1.controller;


import com.example.download1.utils.GetTxt;
import com.example.download1.utils.HttpUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/downLoad")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(com.example.download1.controller.HelloController.class);

    @RequestMapping("/hello")
    public String Hello(){
        return "admin/index";
    }


    @RequestMapping("/model")
    public String test02(Model model){
        model.addAttribute("name","mm1");
        return "admin/index";
    }

    //session存
    @RequestMapping("/sesput")
    public String test03(ModelMap model){
        model.put("pass","aa");
        System.out.println("session存了个数据");
        return "admin/index";
    }



    @RequestMapping(value = "/douluodalu",method = RequestMethod.GET)
    public String downLoad(ModelMap modelMap){
        String baseUrl = "https://img.wubizigeng.com/mhfile/douluodalu1/";
        String capt = "";
        String fileName = "";
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //循环开启线程下载任务
        for (int i= 289030;i<=289032;i++){
            capt = i + "/";
            for (int j = 0; j < 30; j++) {
                fileName = String.format("%04d",j)+".jpg";
                //直接开多线程下载，会内存溢出
//                Thread downLoadT = new Thread();
//                DownLoadPic downLoadPic = new DownLoadPic();
//                downLoadPic.setUrl(baseUrl+capt+fileName);
//                downLoadPic.start();

                //使用线程池创建多线程
                String finalCapt = capt;        //如果参数是变化的，则需重新定义一个局部变量copy到内部类中
                String finalFileName = fileName;
                String url = baseUrl+capt+fileName;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        logger.info("正在打印："+finalCapt+finalFileName);   //输入capt/fileName会报错
                        HttpUtils.getHttpPic(url);
                        logger.info("下载完成"+url);
                    }
                });
            }
        }
        executorService.shutdown();
        logger.info("下载结束");
        return "admin/hello";
    }

//    @Scheduled(fixedDelay = 5000)
    @RequestMapping("/txt")
    public void test06(){
//        GetTxt.downTxt("http://www.shicimingju.com/book/sanguoyanyi/2.html");
        System.out.println("进入txt");
//        return "admin/hello";
    }

    @RequestMapping("homePage")
    public String homePage(HttpServletRequest request, Model model){
        System.out.println("进入homepage");
        return "admin/hello";
    }

    public static void main(String[] args) {
//        DownLoadPic downLoadPic = new DownLoadPic();
//        downLoadPic.setUrl("");
//        Thread downLoadT = new Thread(downLoadPic);
//        downLoadT.start();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String url = "";
        for (int i = 0; i < 10; i++) {
            url = i+"";
            String finalUrl = url;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("开始下载："+finalUrl);
                    try {
                        Thread.currentThread().sleep(2000);
                        //Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("下载结束");
                }
            });
        }
        executorService.shutdown();

    }
}
