package com.example.aimee.bottombar.tony.utils.statics;

import android.os.Bundle;
import android.os.Message;

import java.util.Date;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class Global {
    public final static String LocalHost = "http://10.0.2.2:8080/TourismServer2/";
    public static final int NLG = 233;
    public static final int TPC = 122;
    public static final int PKG = 123;
    public static final int ACT = 213;

    public static final int NotHR = 2345;
    public static final int IsHRbtFail = 1334;
    public static final int IsHRadSuccess = 3224;

    public static String getUniqueId(String name,String string){
//		Date date = new Date();
        Date date = DateUtil.GetDateFromString(string);
        if(date!=null){
            return ParseMD5.parseStrToMd5U32(name+String.valueOf(date.getTime()));
        }else{
            return ParseMD5.parseStrToMd5U32(name+string);
        }

    }

    public final static int SUCCESS = 0x234;
    public final static int FAIL = 0x123;

    public static Message DealFinal(byte[] bytes,int resultType){
        String result = new String(bytes);
        Bundle bundle = new Bundle();
        Message msg = new Message();
        bundle.putString("result",result);
        msg.what=resultType;
        msg.setData(bundle);
        return msg;
    }
}
