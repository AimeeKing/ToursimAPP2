package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aimee.bottombar.UI.ListDropDownAdapter;
import com.example.aimee.bottombar.recycleview.FeedItem;
import com.squareup.picasso.Picasso;
import com.umeng.message.PushAgent;
import com.yyydjk.library.DropDownMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//这一页是搜索结果页


public class list_activity extends Activity {
    private String headers[]={"排序方式","场地","年龄"};

    private String sort[]={"价格排序","最新排序","综合排序","热度排序"};;

    private String ages[]={"不限","小学","幼儿","宝宝"};

    private String place[]={"不限","户外","室内"};


    private DropDownMenu dropDownMenu;
    private ListDropDownAdapter sortAdapter;
    private List<View> popupViews = new ArrayList<>();

    private List<FeedItem> feedsList;
    private ProgressBar progressBar;
    private RecyclerView contentView;
    private MyrecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_float);

        initView();
        //友盟推送要求，每个activity都要用这个函数，不然会导致广播发送不成功
        PushAgent.getInstance(getBaseContext()).onAppStart();


    }

    private void initmenu()
    {
        final String menus[][]={sort,place,ages};
        for(int i=0;i<3;i++) {
            final int pos=i;
            ListView sortview = new ListView(this);
            sortview.setDividerHeight(0);
            sortAdapter = new ListDropDownAdapter(this, Arrays.asList(menus[i]));
            sortview.setAdapter(sortAdapter);
            sortview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    sortAdapter.setCheckItem(position);
                    dropDownMenu.setTabText(position == 0 ? headers[pos] : menus[pos][position]);
                    dropDownMenu.closeMenu();
                }
            });
            popupViews.add(sortview);
        }
    }

    private void initView() {
        progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        dropDownMenu= (DropDownMenu) findViewById(R.id.dropDownMenu);
        View v =findViewById(R.id.list_toolbar);
        //设置搜索框方法
        EditText editText= (EditText) v.findViewById(R.id.search_et_input);
        Bundle bundle=getIntent().getExtras();
        editText.setText(bundle.getString("key"));
        editText.clearFocus();//失去焦点

        //设置下拉框
        initmenu();
        //放下面的recycle
        contentView = new RecyclerView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);;
        contentView.setLayoutManager(layoutManager);

        final String url = "http://javatechig.com/?json=get_recent_posts&count=45";
        new AsyncHttpTask().execute(url);

        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (dropDownMenu.isShowing()) {
            dropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class AsyncHttpTask extends AsyncTask<String,Void,Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected void onPostExecute(Integer result) {//处理来自doinbackground的结果
            progressBar.setVisibility(View.GONE);

            if(result == 1)
            {
                adapter = new MyrecyclerAdapter(list_activity.this,feedsList);
                contentView.setAdapter(adapter);
            }
            else
                Toast.makeText(list_activity.this, "fail to fetch data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url=new URL(params[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                int statusCode=urlConnection.getResponseCode();
                if(statusCode == 200)
                {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuffer response = new StringBuffer();
                    String line;
                    while((line=r.readLine())!=null)
                    {
                        response.append(line);
                    }

                    parseResult(response.toString());
                    result = 1;//表示成功
                }
                else
                    result = 0;//表示获取数据失败

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  result;
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));

                feedsList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public class MyrecyclerAdapter extends  RecyclerView.Adapter<MyrecyclerAdapter.CustomViewHolder>{
        private List<FeedItem> feedItemList;
        private Context mContext;


        public MyrecyclerAdapter(Context context,List<FeedItem> feedItems)
        {
            mContext=context;
            feedItemList=feedItems;

        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // each data item is just a string in this case
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final CustomViewHolder holder, int position) {
            final FeedItem feedItem = feedItemList.get(position);

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //使用Picasso下载图片
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.beautyto)
                    .placeholder(R.drawable.beautyto)
                    .into(holder.imageView);

          /*  ImageLoader imageLoader = Appcontroller.getInstance().getImageLoader();


            //2.设置监听器
            ImageLoader.ImageListener listener =
                    ImageLoader.getImageListener(holder.imageView, R.drawable.mengmeizi, R.drawable.mengmeizi);
            //3.获取图片
            imageLoader.get(feedItem.getThumbnail(), listener);
            */

            holder.textView.setText(Html.fromHtml(feedItem.getTitle()));


            //handle click event on both title and image click
            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);

            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    FeedItem feedItem1 = feedItemList.get(position);
                    //  Toast.makeText(mContext,feedItem1.getTitle(),Toast.LENGTH_LONG).show();
                    // int id = feedItem1.getId();
                    //然后把id给新的activity,新的activity利用id去查文章
                    int id = 1010;
                   /* Intent i =new Intent(MainActivity.this, Main3Activity.class);

                    //  View sharedView = holder.imageView;//feedItem1.getThumbnail()
                    // String transitionName ="tran_img";
                    //  ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,sharedView,transitionName);
                    //   Bundle bundle=transitionActivityOptions.toBundle();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id",id);
                    startActivity(i, bundle);
                    */
                }
            };

            holder.imageView.setOnClickListener(clickListener);
            holder.textView.setOnClickListener(clickListener);

        }

        @Override
        public int getItemCount() {
            return (null != feedItemList ? feedItemList.size() : 0);
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;

            public CustomViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
                this.textView = (TextView) view.findViewById(R.id.title);
            }
        }
    }

}
