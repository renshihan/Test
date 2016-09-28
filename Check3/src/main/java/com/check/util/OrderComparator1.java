package com.check.util;

import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class OrderComparator1 {
    public static void main(String[] args) {
        List<Integer> list= new ArrayList<Integer>();
        Object a[]=new Object[]{1,"阿斯蒂芬",3};
        for(int i=0;i<10;i++){
            list.add(10-i);
        }
        OrderComparator.sort(a);
        for (Object aa:a){
            System.out.println("排序-->"+aa);
        }

    }
}
