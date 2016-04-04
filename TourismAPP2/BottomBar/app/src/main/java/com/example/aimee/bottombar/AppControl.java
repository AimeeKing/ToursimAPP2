package com.example.aimee.bottombar;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.aimee.bottombar.Volley_util.LruBitmapCache;

/**
 * Created by Aimee on 2016/3/24.
 */
public class AppControl extends Application {

    private static  final String TAG = AppControl.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mimageLoader;


    private static AppControl mInstance;

    public static  synchronized  AppControl getInstance()
    {
        return mInstance;
    }


    public ImageLoader getImageLoader()
    {
        if(mimageLoader==null)
        {
            mimageLoader = new ImageLoader(this.mRequestQueue,new LruBitmapCache());
        }
        return mimageLoader;
    }



    public RequestQueue getRequestQueue()
    {
        if(mRequestQueue == null)
        {
            mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
