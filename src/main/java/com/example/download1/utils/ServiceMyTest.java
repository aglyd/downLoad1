package com.example.download1.utils;

import com.example.download1.entity.User;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List排序测试类
 */
public class ServiceMyTest {

    public static void main(String[] args) throws ParseException {
//        String dateTime = "202210";
//        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMM");
//        Calendar calendar = Calendar.getInstance();
//        Date today = simpleDateFormat.parse(dateTime);
//        calendar.setTime(today);
//        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
//        System.out.println(simpleDateFormat.format(calendar.getTime()));
//        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
//        System.out.println(simpleDateFormat.format(calendar.getTime()));

//        test1("11");

        //去除重复测试
        duplicate();

    }

    /**
     * 测试空值排序
     */
    public static void test(){
        Map<String,String> map = new HashMap<>();
        map.put("FINANCING_MONEY","90.22");
        map.put("FINANCING_MONEY1","90");
        map.put("RR","R1");
        Map<String,String> map1 = new HashMap<>();
        map1.put("FINANCING_MONEY","2");
        map1.put("FINANCING_MONEY1","80");
        map1.put("RR","");
        Map<String,String> map2 = new HashMap<>();
        map2.put("FINANCING_MONEY","98.33");
        map2.put("FINANCING_MONEY1","100");
        map2.put("RR","R2");
        Map<String,String> map3 = new HashMap<>();
        map3.put("FINANCING_MONEY","45.11");
        map3.put("FINANCING_MONEY1","3");
        map3.put("RR","R3");
        List<Map<String,String>> rows = new ArrayList<>();
        List<String> rows2 = new ArrayList<>();
        rows.add(map);
        rows.add(map1);
        rows.add(map2);
        rows.add(map3);

        //Comparator.nullsFirst后面
//        Collections.sort(rows, Comparator.comparingDouble(m -> Double.valueOf(m.get("FINANCING_MONEY"))));
        Collections.sort(rows, Comparator.comparingDouble(m -> Integer.parseInt(m.get("FINANCING_MONEY1"))));
//        Collections.sort(rows, Comparator.comparing(e -> MapUtils.getDouble(e, "FINANCING_MONEY"), Comparator.nullsFirst(Double::compareTo)));
//        Collections.sort(rows, Comparator.comparing(e -> MapUtils.getInteger(e, "FINANCING_MONEY"), Comparator.nullsFirst(Integer::compareTo)));
//        Collections.sort(rows,Comparator.comparing(m -> m.get("RR"),Comparator.nullsFirst(String::compareTo).reversed()));  //此方法提供了排序方法可以.reversed()
        //Comparator.nullsFirst放前面，数字类型空指针还是会报错
//        Collections.sort(rows,Comparator.nullsFirst(Comparator.comparing((Map<String,String> m) -> m.get("FINANCING_MONEY")).reversed()));      //null不会报空指针，但是会当成String无法正确比较，也可以用.reversed()
//        Collections.sort(rows2,Comparator.nullsFirst(Comparator.comparing(m -> m)));
//        Collections.sort(rows,Comparator.nullsFirst(Comparator.comparing(m -> Double.parseDouble(m.get("FINANCING_MONEY")))));  //null会报空指针
//        Collections.sort(rows,Comparator.nullsFirst(Comparator.comparing(m -> Integer.valueOf(m.get("FINANCING_MONEY")))));  //null会报空指针
//        Collections.sort(rows,Comparator.nullsFirst(Comparator.comparing(e -> MapUtils.getDouble(e, "FINANCING_MONEY"))));      //null会报空指针
//        Collections.sort(rows,Comparator.nullsFirst(Comparator.comparing(mapOrDefault100(e -> MapUtils.getDouble(e, "FINANCING_MONEY")))));      //null会捕获当作设定值100比较，不会报空指针，相当于自己实现了一个Comparator.nulls100,因为没有null了Comparator.nullsFirst也永远不会生效
//        Collections.sort(rows,Comparator.comparing(mapOrDefaultZero(e -> MapUtils.getDouble(e, "FINANCING_MONEY"))));      //null会捕获当作0比较，不会报空指针，相当于自己实现了一个Comparator.nullsFirst
//        Collections.sort(rows,Comparator.nullsFirst(Comparator.comparing(m -> m.get("RR"))));     //比较String，为null不会报错

//        Collections.sort(rows, (m1,m2) -> MapUtils.getDouble(m2,"FINANCING_MONEY").compareTo(MapUtils.getDouble(m1,"FINANCING_MONEY")));
//        List<Map<String, String>> valueNullLastList = rows.stream().sorted(Comparator.comparing(e -> MapUtils.getDouble(e, "FINANCING_MONEY"), Comparator.nullsLast(Double::compareTo))).collect(Collectors.toList());


        System.out.println(rows);
        Collections.reverse(rows);
        System.out.println(rows);
    }

    //用Optional来设置默认值，如果为空就设置为你想要的规则，比如我这里，想放最后
    //这里比较的因为都是Double，如果为空则返回0，相当于实现了Comparator.nullsFirst，如果外层再套一层Comparator.nullsFirst会不再生效，因为比较值null已经变为了设定值0
    private static Function<Map,Double> mapOrDefaultZero(Function<Map, Double> func){
        return vo -> Optional.ofNullable(func.apply(vo)).orElse(0D);
    }
    private static Function<Map,Double> mapOrDefault100(Function<Map, Double> func){
        return vo -> Optional.ofNullable(func.apply(vo)).orElse(100D);
    }

    /**
     * 测试.reversed()
     * @param serviceId
     */
    public static void test1(String serviceId){
        List<Map<String,String>> rows = new ArrayList<>();
        List<User> rows2 = new ArrayList<>();
        rows2.add(new User(1,"1a"));
        rows2.add(new User(1,"1b"));
        rows2.add(new User(2,"2b"));
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();
        Map map4 = new HashMap();
        map1.put("PLAN_SIZE", "3240600000");
        map2.put("PLAN_SIZE", "3763543000");
        map3.put("PLAN_SIZE", "3674243000");
        map4.put("PLAN_SIZE", "3465643000");
        map1.put("REAL_INTEREST_RATE", "0.3274");
        map2.put("REAL_INTEREST_RATE", "0.4555");
        map3.put("REAL_INTEREST_RATE", "0.2142");
        map4.put("REAL_INTEREST_RATE", "0.6432");
        rows.add(map1);
        rows.add(map2);
        rows.add(map3);
        rows.add(map4);
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        String sortKey = "";
        if (serviceId.equals("1")) {
            sortKey = "PLAN_SIZE";
        } else if (serviceId.equals("2")) {
            sortKey = "RAMAIN_AMT";
        } else if (serviceId.equals("3")) {
            sortKey = "OPERATION_VALUE";
        } else {
            sortKey = "REAL_INTEREST_RATE";
        }
        System.out.println("初始顺序："+rows);
        String finalSortKey = sortKey;
//        List<Bean> list = new ArrayList<>();
//        rows = rows.stream().sorted((m1,m2) -> new BigDecimal(m2.get(finalSortKey)).compareTo(new BigDecimal(m1.get(finalSortKey)))).collect(Collectors.toList());
//        rows = rows.stream().sorted(Comparator.comparing(m -> m.get(finalSortKey))).collect(Collectors.toList());
        rows = rows.stream().sorted(Comparator.comparingDouble(m -> Double.valueOf(m.get(finalSortKey)))).collect(Collectors.toList());
//          Collections.sort(rows, Comparator.comparingDouble(m -> Double.valueOf(m.get(finalSortKey))));
//        rows = rows.stream().sorted((m1,m2) -> MapUtils.getDouble(m2,finalSortKey).compareTo(MapUtils.getDouble(m1,finalSortKey))).collect(Collectors.toList());
//        Collections.sort(rows,(m1,m2) -> new BigDecimal(m2.get(finalSortKey)).compareTo(new BigDecimal(m1.get(finalSortKey))));
//        Collections.sort(rows,(m1,m2) -> Integer.parseInt(m2.get(finalSortKey)) - (Integer.parseInt(m1.get(finalSortKey))));
//        Collections.sort(rows2,Comparator.comparing(s -> s.getName()).reversed());   //会报错，s.getCompanyCode()方法找不到
//        Collections.sort(rows2,Comparator.comparing(User::getName).reversed());    //可以用
//        Collections.sort(rows2,Comparator.comparing((s1,s2) -> (s1.getId() - s2.getId))); //可用
        System.out.println("第一次排序："+rows);
        Collections.reverse(rows);
        System.out.println("反转排序"+rows);

    }

    /**
     * 获取上月当天和去年当天的日期
     * @throws ParseException
     */
   public static void dateUtil() throws ParseException {
        String dateTime = "20250330";
        String lastMonTime,lastYearTime;
        SimpleDateFormat dateFormat =new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(dateTime));
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
        lastMonTime = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
        lastYearTime = dateFormat.format(calendar.getTime());
    }

    /**
     * 分组统计
     */
    public static void getGroupCount(){
        List<User> userList = new ArrayList<>();
        userList.add(new User(1,"1a"));
        userList.add(new User(1,"1b"));
        userList.add(new User(1,"1a"));
        userList.add(new User(2,"2a"));
        Map<Integer, Map<String, Long>> collect = userList.stream().collect(Collectors.groupingBy(User::getId, Collectors.groupingBy(User::getName, Collectors.counting())));
        System.out.println(collect);
        Map<String, Long> map = userList.stream().collect(Collectors.groupingBy(User::getName,Collectors.counting()));
        System.out.println(map);
    }

     /**
     * 去重
     */
    public static void duplicate(){
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "1a");
        User user2 = new User(1, "1b");
        User user3 = new User(1, "1a");
        User user4 = new User(2, "1c");
        userList.add(user2);
        userList.add(user1);
        userList.add(user3);
        userList.add(user4);
        Set<User> treeSet = new TreeSet(Comparator.comparing(User::getId));
        User user5 = new User(1, "2b");
        User user6 = new User(1, "2a");
        User user7 = new User(1, "2a");
        User user8 = new User(2, "2c");
        treeSet.add(user5);
        treeSet.add(user6);
        treeSet.add(user7);
        treeSet.add(user8);
        System.out.println("treeSet："+treeSet);

        //将List转成其他集合
        Set treeSet1 = userList.stream().collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getId))));
        System.out.println("List转treeSet1:"+treeSet1);
//        TreeSet<User> treeSet2 = userList.stream().collect(Collectors.toCollection(TreeSet::new));  //必须实现Comparable接口或者编写Comparator比较器,制定其比较规则，否则会报错
//        System.out.println("List转treeSet2:"+treeSet2);

        Set treeSet2 = userList.stream().collect(Collectors.toCollection(() -> treeSet));  //用已有的treeSet，需要注意如果此实例中已有重复键则不会再添加userList中的重复键，如此处userList中都是重复键，则返回结果还是同treeSet
        System.out.println("List转treeSet2:"+treeSet2);

        List<String> stringList = new ArrayList<>();
        stringList.add("1");
        stringList.add("2");
        stringList.add("1");
        stringList.add("3");
        TreeSet<String> stringSet = stringList.stream().collect(Collectors.toCollection(TreeSet::new)); //string中默认有对比方法
        System.out.println("List转StringSet:"+ stringSet);

        //List转成Treeset去重，然后再转回List
        ArrayList<User> list = userList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> treeSet), ArrayList::new));
        System.out.println("list："+list);
        ArrayList<User> list2 = userList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getId))), ArrayList::new));
        System.out.println("list2："+list2);
        ArrayList<User> list3 = userList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), ArrayList::new));
        System.out.println("list3："+list2);
    }



}
