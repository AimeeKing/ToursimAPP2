package com.example.aimee.bottombar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout mTabhome;
    private LinearLayout mTabact;
    private LinearLayout mTabtopic;
    private LinearLayout mTabmy;

    private ImageButton mImg[];
    private Fragment[]mTab;
    private TextView[]mText;
    private int imgWhite[] = { R.drawable.shouye, R.drawable.huodong, R.drawable.huati, R.drawable.wode };
    private int imgBlack[] = { R.drawable.zhuyeblack, R.drawable.huodongblack, R.drawable.quanziblack, R.drawable.wodeblack };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //动画
        setupWindowAnimations();

        //initBar();
        initView();

        initEvernt();
    }

    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(slide);

        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setReturnTransition(fade);
    }


//设置动画用的
    private void setViewWidth(View view, int x) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = x;
        view.setLayoutParams(params);
    }



    private void initView() {
        mTab = new Fragment[4];
        mImg = new ImageButton[4];
        mText = new TextView[4];
        mTabhome = (LinearLayout) findViewById(R.id.home);
        mTabact = (LinearLayout) findViewById(R.id.activity);
        mTabtopic = (LinearLayout) findViewById(R.id.topic);
        mTabmy = (LinearLayout) findViewById(R.id.my);

        mImg[0] = (ImageButton) findViewById(R.id.id_tab_home_img);
        mImg[1] = (ImageButton) findViewById(R.id.id_tab_act_img);
        mImg[2] = (ImageButton) findViewById(R.id.id_tab_top_img);
        mImg[3] = (ImageButton) findViewById(R.id.id_tab_my_img);

        mText[0] = (TextView) findViewById(R.id.id_tab_home_txt);
        mText[1] = (TextView) findViewById(R.id.id_tab_act_txt);
        mText[2] = (TextView) findViewById(R.id.id_tab_top_txt);
        mText[3] = (TextView) findViewById(R.id.id_tab_my_txt);

        initFragment(0);



    }
    private void initEvernt() {
        mTabhome.setOnClickListener(this);
        mTabact.setOnClickListener(this);
        mTabtopic.setOnClickListener(this);
        mTabmy.setOnClickListener(this);


        //消息推送
        PushAgent mPushAgent=PushAgent.getInstance(getBaseContext());
        mPushAgent.enable();//打开客户端通知服务，关闭用 disable()
        //开启推送并设置注册的回调处理
        mPushAgent.enable(new IUmengRegisterCallback() {

            @Override
            public void onRegistered(String registrationId) {
                //onRegistered方法的参数registrationId即是device_token
                Log.d("device_token", registrationId);
            }
        });
    }
    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fmtran = fm.beginTransaction();
        for(int i = 0;i < 4;i++)
        {
            if(mTab[i] != null)
                fmtran.hide(mTab[i]);
            mImg[i].setImageResource(imgWhite[i]);
            mText[i].setTextColor(Color.GRAY);
        }
        fmtran.commit();
        switch (v.getId())
        {
            case R.id.home:
                //如果按的是第一个界面
                initFragment(0);
                break;
            case R.id.activity:
                initFragment(1);
                break;
            case R.id.topic:
                initFragment(2);
                break;
            case R.id.my:
                initFragment(3);
                break;
        }
    }

    private void initFragment(int i) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fmtran = fm.beginTransaction();
        switch (i) {
            case 0:
            if (mTab[0] == null) {
                mTab[0] = new fragment_Home(MainActivity.this);
                fmtran.add(R.id.id_content, mTab[0]);
            } else
                fmtran.show(mTab[0]);
                break;
            case 1:
                if (mTab[1] == null) {
                    mTab[1] = new fragment_Activity();
                    fmtran.add(R.id.id_content, mTab[1]);
                } else
                    fmtran.show(mTab[1]);
                break;
            case 2:
                if (mTab[2] == null) {
                    mTab[2] = new fragment_Topic();
                    fmtran.add(R.id.id_content, mTab[2]);
                } else
                    fmtran.show(mTab[2]);
                break;
            case 3:
                if (mTab[3] == null) {
                    mTab[3] = new fragment_My(MainActivity.this);
                    fmtran.add(R.id.id_content, mTab[3]);
                } else
                    fmtran.show(mTab[3]);
                break;
        }
        mImg[i].setImageResource(imgBlack[i]);
        mText[i].setTextColor(Color.BLACK);
        fmtran.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }




}
