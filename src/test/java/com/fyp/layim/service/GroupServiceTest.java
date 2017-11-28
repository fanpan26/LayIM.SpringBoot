package com.fyp.layim.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author fyp
 * @crate 2017/11/2 20:42
 * @project SpringBootLayIM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void mapTest(){
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        HashMap<Object,Object> map = new HashMap<>(5);
//        HashMap<HashMapTest,String> map = new HashMap<>(5);
//
//        map.put(new HashMapTest("fanyuepan"),"fanyuepan");
//        map.put(new HashMapTest("fanyuepan1"),"fanyuepan1");
//        map.put(new HashMapTest("fanyuepan2"),"fanyuepan2");
//        map.put(new HashMapTest("fanyuepan3"),"fanyuepan3");
//        Set<Map.Entry<HashMapTest,String>> set = map.entrySet();
//       Set<HashMapTest> keys = map.keySet();
//      String value = map.get(new HashMapTest("fanyuepan"));
    }

    private void print(String ex,Integer n) {
        String out = "";
        String space = "";
        String binary = Integer.toBinaryString(n);

        int zeroLength = 8 - binary.length();
        for (int i = 0; i < zeroLength; i++) {
            out+="0";
        }

        int exLength = 8 - ex.length();
        for (int i = 0; i < exLength; i++) {
            space+=" ";
        }
        ex+=space;
        out += binary;
        System.out.println(ex + "：" + out);
    }
    @Test
    public void MapSizeTest(){
        int n = 220;
        // 00001001
       print("n",n);
       print("n>>>1",n>>>1);
       print("n|n>>>1",n|n>>>1);
        /**
         * n>>>1   00000100
         * n       00001001
         * n|n>>>1 00001101
         * */
        n = n | n >>> 1;
        /**
         * n>>>2    00000011
         * n        00001101
         * n|n>>>2  00001111
         * */
        print("n",n);
        print("n>>>2",n>>>2);
        print("n|n>>>2",n|n>>>2);
        n = n | n >>> 2;
        /**
         * n>>>4   00000000
         * n       00001111
         * n|n>>>4 00001111
         * */
        print("n",n);
        print("n>>>4",n>>>4);
        print("n|n>>>4",n|n>>>4);
        n = n | n >>> 4;
        /**
         * n>>>8   00000000
         * n       00001111
         * n|n>>>8 00001111
         * */
        print("n",n);
        print("n>>>8",n>>>8);
        print("n|n>>>8",n|n>>>8);
        n = n | n >>> 8;
        /**
         * n>>>16   00000000
         * n        00001111
         * n|n>>>16 00001111
         * */
        print("n",n);
        print("n>>>16",n>>>16);
        print("n|n>>>16",n|n>>>16);
        n = n | n >>> 16;

        System.out.println("最终的结果为："+(n+1));
        //  (n < 0) ? 1 : (n >= 10000) ? 10000 : n + 1;
    }


    @Test
    public void hash() {
       String key="abc";
        int h;
        int res = (h = key.hashCode()) ^ (h >>> 16);
        print("res =", res);
    }

}