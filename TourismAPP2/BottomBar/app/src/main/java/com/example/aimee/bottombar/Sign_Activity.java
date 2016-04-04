package com.example.aimee.bottombar;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

public class Sign_Activity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionMenuView amview;
    private ViewPager viewPager;
    private TabLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slidetab);

        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        amview=(ActionMenuView)findViewById(R.id.amvMenu);
        amview.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
        // getSupportActionBar();
        setSupportActionBar(toolbar);
        //显示箭头
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //不显示标题
        viewPager=(ViewPager)findViewById(R.id.pager);
        setupWithViewPager(viewPager);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tableLayout=(TabLayout)findViewById(R.id.tabs);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabTextColors(R.color.unclick, R.color.grey);

        //友盟推送要求，每个activity都要用这个函数，不然会导致广播发送不成功
        PushAgent.getInstance(getBaseContext()).onAppStart();
    }

    private void setupWithViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(),"登录");
        adapter.addFragment(new SignUpFragment(),"注册");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList= new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment,String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.back) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
