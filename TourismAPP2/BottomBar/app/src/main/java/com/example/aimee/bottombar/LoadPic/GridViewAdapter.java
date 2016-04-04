package com.example.aimee.bottombar.LoadPic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.aimee.bottombar.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.DeviceUtils;

/**
 * Created by Aimee on 2016/3/23.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<PhotoInfo> mList;
    private LayoutInflater mInflater;
    private int mScreenWidth;
    private Activity activity;


    public GridViewAdapter(Activity activity, List<PhotoInfo> list) {
        this.mList = list;
        this.mInflater = LayoutInflater.from(activity);
        this.activity=activity;
        this.mScreenWidth = DeviceUtils.getScreenPix(activity).widthPixels;
    }


    @Override
    public int getCount() {

        return mList.size()>=9?9:mList.size()+1;//mList.size()
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.ic_gf_default_photo)
                .showImageForEmptyUri(R.drawable.ic_gf_default_photo)
                .showImageOnLoading(R.drawable.ic_gf_default_photo).build();

        ImageView ivPhoto = (ImageView) mInflater.inflate(R.layout.adapter_photo_list_item, null);
        setHeight(ivPhoto);

            if((position <mList.size())||(mList.size()>=9)) {
                PhotoInfo photoInfo = mList.get(position);
                ImageLoader.getInstance().displayImage("file:/" + photoInfo.getPhotoPath(), ivPhoto, options);
            }
        else
                ivPhoto.setBackgroundResource(R.drawable.add);

        return ivPhoto;
    }
    private void setHeight(final View convertView) {
        int height = mScreenWidth / 3 - 8;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }
}
