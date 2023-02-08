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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
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

    @Scheduled(cron = "30 59 23 * * ? ")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void Login() {
        logger.info("return Result:{}","调用login开始");
        List<String> ShengWufirstArray = Arrays.asList("1275,15:30","1275,16:00","1275,16:30","1275,17:00");
        List<String> firstArray = Arrays.asList("1272,16:00","1272,16:30", "1272,17:00","1272,17:30");
//        List<String> secondArray = Arrays.asList("1502,16:00","1502,16:30", "1502,17:00", "1502,17:30");
        List<String> secondArray = Arrays.asList("1502,20:00","1502,20:30","1502,21:00","1502,21:30");
        List<String> thirdArray = Arrays.asList("1274,19:30","1274,20:00","1274,20:30","1274,21:00","1274,21:30");

        final Map map = qiangTai(true,null);
        logger.info("调用login结束return Result:{}",map);
    }


    @RequestMapping(value = "/login2",method = RequestMethod.GET)
    public Map Login2(String type,boolean isTrue) {
        logger.info("调用login2开始，请求参数:{}",type);
        Map result = new HashMap();
        List<String> ShengWufirstArray = Arrays.asList("1275,15:30","1275,16:00","1275,16:30","1275,17:00");
        List<String> firstArray = Arrays.asList("1272,16:00","1272,16:30", "1272,17:00","1272,17:30");
        List<String> secondArray = Arrays.asList("1502,19:30","1502,20:00","1502,20:30","1502,21:00","1502,21:30");
        List<String> thirdArray = Arrays.asList("1274,19:30","1274,20:00","1274,20:30","1274,21:00","1274,21:30");

        int i = Integer.parseInt(type);
        List<List<String>> lists = new ArrayList();
        lists.add(ShengWufirstArray);
        lists.add(firstArray);
        lists.add(secondArray);
        lists.add(thirdArray);
        try {
            if (isTrue){
                final Map map = qiangTai(false,lists.get(i));
                logger.info("调用login2返回return Result:{}",map);
                result.put("msg：调用成功！",map);
            }else {
                result.put("msg","请求测试成功,未抢台");
            }

            result.put("status",1);
        }catch (Exception e){
            result.put("status",0);
            result.put("msg","调用失败，"+e.getMessage());
        }

        logger.info("调用login2结束return Result:{}",result);
        return result;
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
    public static List<String> getDeskTime(Long callTime){
        int[] theDesk = {1272,1502,1274,1275,1502,1272,1275,1275,1502,
                1275,1272,1274,1502,1275,1272,1274,1502,1272,1502};  //[5,6,3,5]
//        int[] theDesk = {1502,1274,1274,1275};
        String[] firstTime = {",19:30",",20:00",",20:30",",21:00",",21:30"};
        String[] secondTime = {",20:00",",20:30",",21:00",",21:30"};
        List<String []> theTime = new ArrayList<>();
        theTime.add(firstTime);
        theTime.add(secondTime);
//        int desk = (int)(callTime% theDesk.length);
        int randomNum = (int) (Math.random() * 100000);
        int desk = randomNum%theDesk.length;     //先将double随机数截断为int，然后取模
//        int time = (int)(callTime%theTime.size());
        int time = randomNum%theTime.size();
        logger.info("randonNum:{}，desk：{}，time：{}",randomNum,desk,time);
        String[] chosedTime = theTime.get(time);
        for (int i = 0; i < chosedTime.length; i++) {
            chosedTime[i] = theDesk[desk] + chosedTime[i];
        }
        List<String> list = Arrays.asList(chosedTime);
        int deskNum = theDesk[desk];
        logger.info("抢到了柜台:{}实验柜,//list信息:{}",deskNum==1272?"1号":(deskNum==1274?"3号":(deskNum==1275?"生物":"2号")),list);
//        logger.info("抢到了柜台:{}号,//list信息:{}",desk<1?desk+2:desk>2?"生物实验柜台":3,list);
        return list;
    }

    public static Map qiangTai(boolean waitFlag,List<String> list) {

        long callTime = new Date().getTime();
        logger.info("开始调用：{}", callTime);
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
        List<String> ShengWusecondArray = Arrays.asList("1275,9:00","1275,9:30","1275,10:00","1275,10:30","1275,11:00");
        List<String> firstArray2 = Arrays.asList("1272,16:00","1272,16:30", "1272,17:00","1272,17:30");
        List<String> secondArray = Arrays.asList("1502,16:00","1502,16:30", "1502,17:00", "1502,17:30");
        List<String> thirdArray = Arrays.asList("1274,19:00","1274,19:30","1274,20:00","1274,20:30");


        List<List<String>> timeParam = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            timeParam.add(getDeskTime(callTime));
        }else {
            timeParam.add(list);
        }
//        timeParam.add(ShengWusecondArray);
//        timeParam.add(firstArray2);
//        timeParam.add(secondArray);
//        timeParam.add(thirdArray);

        param.put("timeParam",timeParam);

        try {
            String returnStr = QiangtaiService.getResoucesByLoginCookies(baseUrl, param,waitFlag);
            result.put("returnStr",returnStr);
        } catch (Exception e) {
            result.put("code",0);
            result.put("Message","抢台失败！"+e.getMessage());
            e.printStackTrace();
        }
        logger.info("login调用结束");
        return result;
    }

    public static void main(String[] args) {
//        List<String> ShengWufirstArray = Arrays.asList("1275,15:30","1275,16:00","1275,16:30","1275,17:00");
//        List<String> firstArray = Arrays.asList("1272,16:00","1272,16:30", "1272,17:00","1272,17:30");
//        List<String> secondArray = Arrays.asList("1502,16:00","1502,16:30", "1502,17:00", "1502,17:30");
//        List<String> thirdArray = Arrays.asList("1274,19:00","1274,19:30","1274,20:00","1274,20:30","1274,21:00","1274,21:30");
//
//
//        final Map map = qiangTai(false,firstArray);
//        final Map map = qiangTai(false,null);
//        logger.info("抢台返回Result:{}",map);
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(getDeskTime(1630424640002L));
        }


    }
}
