package com.example.aimee.bottombar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aimee.bottombar.recycleview.FeedItem;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//这个是第三个界面的话题界面
public class acttopic_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private MyrecycleAdapter myrecycleAdapter;
    private ProgressBar progressBar;
    private FloatingActionsMenu  mfab;
    private FloatingActionButton afab;
    private FloatingActionButton tfab;
    private List<FeedItem> feedsList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_acttopic, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar.setVisibility(View.VISIBLE);





        mfab= (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mfab.isExpanded())
                {
                    mfab.collapse();
                }
                else mfab.expand();

            }
        });
        afab = (FloatingActionButton) v.findViewById(R.id.add_activity);
        tfab = (FloatingActionButton) v.findViewById(R.id.add_topic);
        afab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_LONG).show();
                Intent i=new Intent(getActivity(),add_activity.class);
                startActivity(i);
            }
        });


        tfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_LONG).show();
                Intent i=new Intent(getActivity(),add_activity.class);
                startActivity(i);
            }
        });

        /*下载数据通过URL*/
        final String url = "http://javatechig.com/?json=get_recent_posts&count=45";
        new AsyncHttpTask().execute(url);




        return v;
    }

    public class MyrecycleAdapter extends  RecyclerView.Adapter<MyrecycleAdapter.CustomViewHolder>{
        private List<FeedItem> feedItemList;
        private Context mContext;

        public MyrecycleAdapter(Context context,List<FeedItem> feedItems)
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_topic,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            final FeedItem feedItem = feedItemList.get(position);

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //download image using picasso library
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.mengmeizi)
                    .placeholder(R.drawable.mengmeizi)
                    .into(holder.imageView);


            holder.textView.setText(Html.fromHtml(feedItem.getTitle()));


            //handle click event on both title and image click
            holder.textView.setTag(holder);
            holder.imageView.setTag(holder);
            holder.ev.setText(getString(R.string.android5_0_text));

            View.OnClickListener clickListener = new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {
                    CustomViewHolder holder = (CustomViewHolder) v.getTag();
                    int position = holder.getAdapterPosition();
                    FeedItem feedItem1 = feedItemList.get(position);
                     Toast.makeText(mContext, feedItem1.getTitle(), Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(mContext,feedItem1.getTitle(),Toast.LENGTH_LONG).show();
                    // int id = feedItem1.getId();
                    //然后把id给新的activity,新的activity利用id去查文章
                   /* int id = 1010;
                    Intent i =new Intent(getActivity(), content_activity.class);

                    View sharedView = holder.imageView;//feedItem1.getThumbnail()
                    String transitionName ="tran_img";
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(),sharedView,transitionName);
                    Bundle bundle=transitionActivityOptions.toBundle();
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
            protected ExpandableTextView ev;


            public CustomViewHolder(View view) {
                super(view);
                this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
                this.textView = (TextView) view.findViewById(R.id.title);
                this.ev=(ExpandableTextView) view.findViewById(R.id.expand_text_view);
            }
        }
    }

    private class AsyncHttpTask extends AsyncTask<String,Void,Integer> {


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

        @Override
        protected void onPreExecute() {
            //之前做的
            getActivity().setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if(result == 1)
            {
                myrecycleAdapter = new MyrecycleAdapter(getActivity(),feedsList);
                recyclerView.setAdapter(myrecycleAdapter);
            }
            else
                Toast.makeText(getActivity(), "fail to fetch data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
