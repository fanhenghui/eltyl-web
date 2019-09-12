package com.eltyl.util;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {
    public static List<Integer> getIntegerList(String ids){
        List<Integer> integerList = new ArrayList<Integer>();
        String[] idList=ids.split(",");
        for(int i=0;i<idList.length;i++){
            if(!idList[i].isEmpty()&&Integer.parseInt(idList[i])>0){
                integerList.add(Integer.parseInt(idList[i]));
            }
        }
        return integerList;
    }
}
