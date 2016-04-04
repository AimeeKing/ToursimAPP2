package com.example.aimee.bottombar.tony.utils.statics;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by TonyJiang on 2016/4/4.
 */
public class GsonUtil {
    @SuppressWarnings("unchecked")
    public static ArrayList JsonArray2JavaList(String result,Class tClass){
        Gson gson = new Gson();
        //创建知识库对象的ArrayList
        ArrayList list = new ArrayList();
        //创建一个JsonElement元素 将result从String转换成JsonElement
        JsonElement el = new JsonParser().parse(result);
        //新建JsonArray
        JsonArray jsonArray = null;
        //判断是否是一个JsonArray
        if(el.isJsonArray()){
            //如果是 则将其转换成JsonArray
            jsonArray = el.getAsJsonArray();
        }
        //新建一个迭代器 获取JsonArray的迭代
        Iterator it = jsonArray.iterator();
        //判断jsonArray中有没有下一个元素
        while(it.hasNext()){
            //存在则获取该元素
            JsonElement e = (JsonElement) it.next();
            //将该元素从JSON转换成bean对象
            Object oValue = gson.fromJson(e,tClass);
            //将该bean对象塞到ArrayList里面去
            list.add(oValue);
        }
        return list;
    }
    @SuppressWarnings("unchecked")
    public static String Java2Json(Object obj){
        Gson gson = new Gson();
        String result = gson.toJson(obj);
        return result;
    }
    @SuppressWarnings("unchecked")
    public static String JavaList2Json(ArrayList list){
        Gson gson = new Gson();
        String result = gson.toJson(list);
        return result;
    }
    @SuppressWarnings("unchecked")
    public static Object Json2Java(String result,Class tClass){
        Gson gson = new Gson();
        return gson.fromJson(result,tClass);
    }
}
