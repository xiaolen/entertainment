package com.etoak.crawl.controller;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @Author: 秦渊渊
 * @Date: 2018/11/23 10:39
 */
public class LambdaTest {

    public static void main(String[] args) {
        Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);
        //lambda遍历map集合
        items.forEach((key, value) -> System.out.println(key + "==============" + value));

        //lambda遍历map集合并且加入判断
        items.forEach((key, value) -> {
            if ("A".equals(key)) {
                System.out.println(value + "=============");
            }
        });

        //lambda遍历set集合
        Set<String> set = new HashSet<>();
        set.add("Q");
        set.add("W");
        set.add("E");
        set.add("R");
        set.add("T");
        //括号中s表示集合中的每一项，名称任意写
        set.forEach(ss -> System.out.println(ss + "---------------------"));
        set.forEach(s -> System.out.println(s + "$$$$$$$$$$$$$$$$$$$"));

        set.forEach(s -> {
            if ("A".equals(s) == true) {
                System.out.println("s");
            }
        });

//----------------Stream----------------------------------------------
//        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
//        List<Integer> numsWithoutNull = nums.stream().filter(num -> num != null).
//        collect(() -> new ArrayList<Integer>(),
//        (list, item) -> list.add(item),
//        (list1, list2) -> list1.addAll(list2));
//        System.out.println(numsWithoutNull+"777777777777777777777");


        List<Integer> nums0 = Lists.newArrayList(1, null, 3, 4, null, 6);
        nums0.stream().filter(num -> num != null).count();


        //将集合找那个的数据去空放入一个行的集合中，使用strem
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        List<Integer> numsWithoutNull = nums.stream().filter(num -> num != null).collect(
                () -> new ArrayList<Integer>(),
                (list, item) -> list.add(item),
                (list1, list2) -> list1.addAll(list2));

        //方法二跟上面方法相同
        List<Integer> numsWithoutNull11 = nums.stream().filter(num -> num != null).collect(
                ArrayList::new,
                ArrayList::add,
                ArrayList::addAll
        );

        System.out.println(numsWithoutNull + "777777777777777777777");


    }

}
