package com.example.aimee.bottombar;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.aimee.bottombar.recycleview.FeedItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aimee on 2016/3/19.
 */
public class fragment_Home extends Fragment implements ViewPager.OnPageChangeListener,AdapterView.OnItemClickListener{
    private LinearLayout linearLayout;//放点的集合
    private List<ImageView> img;//图片集合
    private ImageView dot[];//点集合
    private ViewPager viewPager;
    private String path[];//图片地址
    private List<FeedItem> feedsList;
    private Context mContext;
    public fragment_Home(Context context)
    {
        mContext = context;
    }
    //bar
    private Spinner spinner;
    private List<String> cityList;
    private SpinnerAdapter adapter;
    //searchview
    private EditText searchview;
    private Toolbar toolbar;

    private LayoutInflater inflater;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        initBar(v);
        initview(v);
        return v;
    }

    private void process_json(JSONObject response) {
        try {
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));

                feedsList.add(item);

            }
            path=new String[feedsList.size()];
            for (int i = 0; i < feedsList.size(); i++) {
                path[i] = feedsList.get(i).getThumbnail();
            }

            //放点
            dot = new ImageView[path.length];
            for (int i = 0; i < path.length; i++) {
                ImageView imgview = new ImageView(mContext);
                imgview.setLayoutParams(new ActionBar.LayoutParams(10, 10));
                dot[i] = imgview;
                if (i == 0) {
                    //初始化的时候第0张图片被选择
                    dot[i].setBackgroundResource(R.drawable.login_point);
                } else {
                    dot[i].setBackgroundResource(R.drawable.login_point);
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;
                linearLayout.addView(imgview, layoutParams);
            }


            img = new ArrayList<>();
            for (int i = 0; i < path.length; i++) {
                ImageView imgview = new ImageView(mContext);
                imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
                img.add(imgview);
                setPic(imgview, path[i]);//装载图片
            }

            viewPager.setAdapter(new MyAdapter(img, mContext));

            viewPager.addOnPageChangeListener(fragment_Home.this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBar(View v)
    {
        toolbar =(Toolbar)v.findViewById(R.id.toolbar_activity);
      //  v.setSupportActionBar(toolbar);

        //spinner
        spinner= (Spinner) v.findViewById(R.id.spinner);
        cityList=new ArrayList<>();
        cityList.add("杭州");
        cityList.add("其他");
        adapter=new SpinnerAdapter();
        adapter.addItems(cityList);
        spinner.setAdapter(adapter);


        searchview = (EditText) v.findViewById(R.id.search_et_input);


        searchview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //此处为处理得到焦点时的情况
                    //跳转到新的界面
                    searchview.clearFocus();
                    Intent intent = new Intent(mContext,SearchActivity.class);//跳转到搜索界面
                    startActivity(intent);



                } else {
                    //失去焦点时的情况
                }
            }
        });
    }
    private void initview( View v) {
        linearLayout = (LinearLayout)v. findViewById(R.id.dots);
        viewPager = (ViewPager) v.findViewById(R.id.pager);

        String url = "http://javatechig.com/?json=get_recent_posts&count=4";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        process_json(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });


        AppControl.getInstance().addToRequestQueue(jsObjRequest);

    }

    private void setPic(final ImageView imgview, String s) {
        //装载图片
        //通过毕加索来加载图片，其实用volley也可以
      /*  Picasso.with(getBaseContext()).load(s)
                .error(R.drawable.mengmeizi)
                .placeholder(R.drawable.mengmeizi)
                .into(imgview);
                */
        ImageRequest request = new ImageRequest(s,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imgview.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        imgview.setImageResource(R.drawable.mengmeizi);
                    }
                });


        AppControl.getInstance().addToRequestQueue(request, "pics");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //当按下spinner下的按钮时的反应
        switch (position){
            case 0:
                Toast.makeText(mContext, "你按了" + cityList.get(0), Toast.LENGTH_LONG);
                break;
            case 1:
                Toast.makeText(mContext,"你按了"+cityList.get(1),Toast.LENGTH_LONG);
        }
    }

    public class SpinnerAdapter extends BaseAdapter {
        private List<String> mItem = new ArrayList<>();
        public int getCount() {
            return mItem.size();
        }

        @Override
        public Object getItem(int position) {
            return mItem.get(position);
        }

        public void clear()
        {
            mItem.clear();
        }

        public void addItem(String item)
        {
            mItem.add(item);
        }

        private void addItems(List<String> items)
        {
            mItem.addAll(items);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView==null||!convertView.getTag().toString().equals("DROPDOWN"))
            {
                convertView=inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
                convertView.setTag("DROPDOWN");
            }
            TextView tv= (TextView) convertView.findViewById(R.id.spiner_item);
            tv.setText(getTitle(position));
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null||!convertView.getTag().toString().equals("NON_DROPDOWN"))
            {
                convertView = inflater.inflate(R.layout.
                        spinner_item, parent, false);
                convertView.setTag("NON_DROPDOWN");
            }
            TextView textView = (TextView) convertView.findViewById(R.id.spinnertitle);
            textView.setText(getTitle(position));

            return convertView;
        }





        private  String getTitle(int position)
        {

            return position>=0&&position<mItem.size()?mItem.get(position):"杭州";
        }
    }







    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i = 0; i < dot.length; i++) {
            if (arg0 == i) {
                dot[i].setImageResource(R.drawable.login_point_selected);
            } else {
                dot[i].setImageResource(R.drawable.login_point);
            }
            if(arg0 !=0)
                dot[0].setImageResource(R.drawable.login_point);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class MyAdapter extends PagerAdapter {
        private List<ImageView> imageViews;
        private Context context;

        public MyAdapter(List<ImageView> imageViews, Context context) {
            this.imageViews = imageViews;
            this.context = context;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(imageViews.get(position));
            return imageViews.get(position);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
