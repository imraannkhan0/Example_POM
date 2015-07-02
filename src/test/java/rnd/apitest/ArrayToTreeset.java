package rnd.apitest;

import com.jcabi.immutable.Array;

import java.util.*;

/**
 * Created by Sabyasachi on 20/6/2015.
 */
public class ArrayToTreeset {

    public static void main(String [] args){
        Integer[] arr={0,0,0,1,2,3,4,5,5,4,2,6};

List list=new ArrayList(Arrays.asList(arr));
        Set set=new TreeSet();


        for(int i:arr){
            set.add(i);
        }

        System.out.println(Arrays.toString(arr));
        System.out.println(list);
    }
}
