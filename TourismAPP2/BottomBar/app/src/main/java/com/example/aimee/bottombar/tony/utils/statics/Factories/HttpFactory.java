package com.example.aimee.bottombar.tony.utils.statics.Factories;

import android.os.Handler;

import com.example.aimee.bottombar.tony.utils.HttpResult;

import com.example.aimee.bottombar.tony.utils.httpUtils.basics.UserClient;
import com.example.aimee.bottombar.tony.utils.httpUtils.impls.UserClientImpl;
import com.example.aimee.bottombar.tony.utils.statics.Global;
import com.example.aimee.bottombar.tony.utils.statics.GsonUtil;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class HttpFactory {

    public static UserClient getUserClient(Handler handler){
        return new UserClientImpl(handler);
    }

    public static int CheckResult(String string){
        HttpResult httpResult = (HttpResult) GsonUtil.Json2Java(string,HttpResult.class);
        if(httpResult.getResult()!=null){
            if(httpResult.getStatus()==202){
                return Global.IsHRbtFail;
            }else if(httpResult.getStatus()==200){
                return Global.IsHRadSuccess;
            }
        }
        return Global.NotHR;
    }
}

