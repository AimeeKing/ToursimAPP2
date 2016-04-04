package com.example.aimee.bottombar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.square.android.expandabletextview.ExpandableTextView;


public class friend_fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.expendtextview, container, false);
        ExpandableTextView expTv = (ExpandableTextView) v.findViewById(R.id.expand_text_view);
        //expTv.setText("hello world");
        expTv.setText(getString(R.string.android5_0_text));

        return v;
    }


}
