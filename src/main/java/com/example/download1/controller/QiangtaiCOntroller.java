package com.example.download1.controller;

import com.example.download1.dao.UserDao;
import com.example.download1.dao.UserMoneyDao;
import com.example.download1.entity.User;
import com.example.download1.entity.UserMoney;
import com.example.download1.utils.PropertiesUtil;
import com.example.download1.utils.QiangtaiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/qiangtai")
public class QiangtaiCOntroller {
    private static final Logger logger = LoggerFactory.getLogger(QiangtaiCOntroller.class);

    @Resource
    public UserDao userDao;
    @Resource
    public UserMoneyDao userMoneyDao;
    @Value("${user1.name1}")
    private String username;
    @Value("${spring.datasource.url}")
    private String jdbcurl;

    @Scheduled(cron = "50 31 23 * * ? ")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    @ResponseBody
    public void Login() {
        logger.info("开始调用：{}",new Date().getTime());
        Map result = new HashMap();
        String baseUrl = "https://www.sekahui.com/wap/login.php?mendian=317340&r=";
        Map param = new HashMap();
        Map loginParam = new HashMap();
        loginParam.put("mendian", "317340");
        loginParam.put("email", "13077310619");
        loginParam.put("password", "why19960924");
//        loginParam.put("email", "18797777204");
//        loginParam.put("password", "13574151189dede");
        param.put("loginParam",loginParam);


        List<String> ShengWufirstArray = Arrays.asList("1275,15:30","1275,16:00","1275,16:30","1275,17:00");
        List<String> ShengWusecondArray = Arrays.asList("1275,17:30","1275,18:00","1275,18:30","1275,19:00");
        List<String> firstArray2 = Arrays.asList("1272,16:00","1272,16:30", "1272,17:00",
                "1272,17:30");
        List<String> secondArray = Arrays.asList("1502,16:00","1502,16:30", "1502,17:00",
                "1502,17:30");
//        List<String> thirdArray = Arrays.asList("1274,15:30","1274,16:00","1274,16:30","1274,17:00");
        List<String> thirdArray = Arrays.asList("1274,16:00","1274,16:30","1274,17:00","1274,17:30");


        List<List<String>> timeParam = new ArrayList<>();
        timeParam.add(getDeskTime());
//        timeParam.add(ShengWusecondArray);
//        timeParam.add(firstArray2);
//        timeParam.add(secondArray);
//        timeParam.add(thirdArray);

        param.put("timeParam",timeParam);

        try {
//            String returnStr = QiangtaiService.getResoucesByLoginCookies(baseUrl, param);
//            result.put("returnStr",returnStr);
        } catch (Exception e) {
            result.put("code",0);
            result.put("Message","抢台失败！"+e.getMessage());
            e.printStackTrace();
        }
        logger.info("login调用结束");

        result.put("code",1);
        result.put("Message","抢台成功！");
    }

    @RequestMapping(value = "/insertUser",method = RequestMethod.POST)
    public void insertUser(){
        logger.info("开始测试新增用户信息");
        User user = new User("小新");
        int uid = userDao.insertUser(user);
        logger.info("新增用户uid：{},user:{}",uid,user);
        final int i = userMoneyDao.insertUserMoney(new UserMoney(user.getId(), new BigDecimal("10000001.213")));
        UserMoney userMoney = new UserMoney();
        userMoney.setUid(user.getId());
        final List<UserMoney> userMonies = userMoneyDao.selectUserMoneyList(userMoney);
        logger.info("测试结束，新增用户成功:{}，用户信息：{}",i,userMonies);
    }

    /**
     * 测试获得启动环境
     */
    @RequestMapping(value = "/getEnvName",method = RequestMethod.POST)
    public String getEnvName(){
        Map property = PropertiesUtil.read("application-Druid");
        String envName = property.get("envName").toString();
        String jdbcurl = property.get("jdbc.url").toString();
        logger.info("username:{},envName:{},jdbcurl:{}",username, envName,jdbcurl);
        return "username:"+username+"，envName:"+ envName;
    }

    /**
     * 随机获取时间和柜台编号
     * @return  时间柜台信息请求参数
     */
    public static List<String> getDeskTime(){
//        int[] theDesk = {1272,1502,1274,1274,1275};
        int[] theDesk = {1502,1274,1274,1275};
        String[] firstTime = {",15:30",",16:00",",16:30",",17:00"};
        String[] secondTime = {",16:00",",16:30",",17:00",",17:30"};
        List<String []> theTime = new ArrayList<>();
        theTime.add(firstTime);
        theTime.add(secondTime);
        int desk = (int)(Math.random()*10)%4;
        int time = (int)(Math.random()*10)%2;
        String[] chosedTime = theTime.get(time);
        for (int i = 0; i < chosedTime.length; i++) {
            chosedTime[i] = theDesk[desk] + chosedTime[i];
        }
        List<String> list = Arrays.asList(chosedTime);
//        logger.info("抢到了柜台:{}号,//list信息:{}",desk<2?desk+1:desk>3?"生物实验柜台":3,list);
        logger.info("抢到了柜台:{}号,//list信息:{}",desk<1?desk+2:desk>2?"生物实验柜台":3,list);
        return list;
    }

    public static void main(String[] args) {
//        int[] ints = {0,0,0,0,0};

//        for (int i = 0; i < 100; i++) {
//            double random = Math.random();
//            random = random*10;
//            Integer a = (int)random%5;
//            System.out.println(random+"--a:--"+a);
//            ints[a]++;
//        }
//        for (int i:ints) {
//            System.out.println(i);
//        }

        logger.info("开始调用：{}",new Date().getTime());
        Map result = new HashMap();
        String baseUrl = "https://www.sekahui.com/wap/login.php?mendian=317340&r=";
        Map param = new HashMap();
        Map loginParam = new HashMap();
        loginParam.put("mendian", "317340");
        loginParam.put("email", "13077310619");
        loginParam.put("password", "why19960924");
//        loginParam.put("email", "18797777204");
//        loginParam.put("password", "13574151189dede");
        param.put("loginParam",loginParam);


        List<String> ShengWufirstArray = Arrays.asList("1275,15:30","1275,16:00","1275,16:30","1275,17:00");
        List<String> ShengWusecondArray = Arrays.asList("1275,17:30","1275,18:00","1275,18:30","1275,19:00");
        List<String> firstArray2 = Arrays.asList("1272,16:00","1272,16:30", "1272,17:00","1272,17:30");
        List<String> secondArray = Arrays.asList("1502,16:00","1502,16:30", "1502,17:00", "1502,17:30");
//        List<String> thirdArray = Arrays.asList("1274,15:30","1274,16:00","1274,16:30","1274,17:00");
        List<String> thirdArray = Arrays.asList("1274,16:00","1274,16:30","1274,17:00","1274,17:30");
//        List<String> thirdArray = Arrays.asList("1274,20:00","1274,20:30","1274,21:00", "1274,21:30");


        List<List<String>> timeParam = new ArrayList<>();
        timeParam.add(getDeskTime());
//        timeParam.add(ShengWusecondArray);
//        timeParam.add(firstArray2);
//        timeParam.add(secondArray);
//        timeParam.add(thirdArray);

        param.put("timeParam",timeParam);

        try {
            String returnStr = QiangtaiService.getResoucesByLoginCookies(baseUrl, param);
            result.put("returnStr",returnStr);
        } catch (Exception e) {
            result.put("code",0);
            result.put("Message","抢台失败！"+e.getMessage());
            e.printStackTrace();
        }
        logger.info("login调用结束");


    }
}
