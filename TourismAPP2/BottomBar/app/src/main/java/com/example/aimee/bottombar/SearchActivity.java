package com.example.aimee.bottombar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.message.PushAgent;

public class SearchActivity extends Activity {

    private ImageView imageview;
    private EditText edit;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupWindowAnimations();
        initsearch();
    }

    private void initsearch() {
        edit= (EditText) findViewById(R.id.search_et_input);
        imageview= (ImageView) findViewById(R.id.search_iv_delete);
        imageview.setVisibility(View.VISIBLE);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");//点击叉叉，取消文字
            }
        });

        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND ||
                        actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Intent i = new Intent(SearchActivity.this, list_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("key", edit.getText().toString());
                    i.putExtras(bundle);
                    startActivity(i);
                }
                return false;
            }
        });

        //友盟推送要求，每个activity都要用这个函数，不然会导致广播发送不成功
        PushAgent.getInstance(getBaseContext()).onAppStart();
    }

    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
}
