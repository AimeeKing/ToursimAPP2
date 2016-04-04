package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.umeng.message.PushAgent;

public class content_activity extends Activity implements ObservableScrollViewCallbacks {
    //这里显示活动具体内容
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final boolean TOOLBAR_IS_STICKY = true;

    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;
    private android.widget.Toolbar toolbar;
    private int mToolbarColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_activity);
        super.onCreate(savedInstanceState);
        //友盟推送要求，每个activity都要用这个函数，不然会导致广播发送不成功
        PushAgent.getInstance(getBaseContext()).onAppStart();

        // setupWindowAnimations();//设置跳转动画

        mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        toolbar= (android.widget.Toolbar) findViewById(R.id.tool_bar);
        setActionBar(toolbar);
        mToolbarColor = getResources().getColor(R.color.primary);


        if (!TOOLBAR_IS_STICKY) {
            toolbar.setBackgroundColor(Color.TRANSPARENT);
        }
        mActionBarSize =getActionBarSize();


        mImageView = findViewById(R.id.image);//头图
        mOverlayView = findViewById(R.id.overlay);//覆盖头图的图
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);//scrollview
        mScrollView.setScrollViewCallbacks(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(getTitle());//标题在图片上
        setTitle(null);//设置标题为空
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(content_activity.this, "FAB is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(mFab, 1);//设置他的大小吗
        ViewHelper.setScaleY(mFab, 1);



        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
                // mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);
                onScrollChanged(0,false,false);
                //如果想让页面从0 开始那么就输入
                // onScrollChanged(0, false, false);
                //或者
                //mScrollView.scrollTo(0, 1);
                //mScrollView.scrollTo(0, 0);
                //不能用mScrollView.scrollTo(0, 0); 这样不会引起调用onScrollChanged().
                //并且必须写在这个函数里面，不然写在外面并没有什么软用
            }
        });

        //设置头图下面的网页
        WebView webView = (WebView) findViewById(R.id.text);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://aimeeking.xicp.net:18358//MyFirstHtml/MyHtml.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }

            @Override   //转向错误时的处理
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
   /*     if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    //实际执行当华东界面时产生的动画的函数
    @Override
    public void onScrollChanged(int scrollY, boolean b, boolean b1) {
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(mTitleView, 0);//到这个位置的时候开始缩放
        ViewHelper.setPivotY(mTitleView, 0);
        ViewHelper.setScaleX(mTitleView, scale);//设置他的大小
        ViewHelper.setScaleY(mTitleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        if (TOOLBAR_IS_STICKY) {
            titleTranslationY = Math.max(0, titleTranslationY);
        }
        ViewHelper.setTranslationY(mTitleView, titleTranslationY);//移动他的位置

        // Translate FAB
        int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
                mActionBarSize - mFab.getHeight() / 2,
                maxFabTranslationY);
        //当他还没到顶部的时候

        if(TOOLBAR_IS_STICKY)
        {
            fabTranslationY = Math.max(0+mFab.getWidth()/2,fabTranslationY);
        }
        ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
        ViewHelper.setTranslationY(mFab, fabTranslationY);//移动浮点的位置

/*
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
                // which causes FAB's OnClickListener not working.
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
                lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
                lp.topMargin = (int) fabTranslationY;
                mFab.requestLayout();
            } else {
                ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
                ViewHelper.setTranslationY(mFab, fabTranslationY);//移动浮点的位置

            }
        }
        */


        // Show/hide FAB
        if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }


        //设置toolbar的颜色
        if (TOOLBAR_IS_STICKY) {
            // Change alpha of toolbar background
            if (-scrollY + mFlexibleSpaceImageHeight <= mActionBarSize) {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(1, mToolbarColor));
            } else {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, mToolbarColor));
            }
        } else {
            // Translate Toolbar
            if (scrollY < mFlexibleSpaceImageHeight) {
                ViewHelper.setTranslationY(toolbar, 0);
            } else {
                ViewHelper.setTranslationY(toolbar, -scrollY);
            }
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }


    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }


    private void showFab() {
        if (!mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            ViewPropertyAnimator.animate(mFab).cancel();
            ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
            mFabIsShown = false;
        }
    }
}
