package com.cygnus.krios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class HorizontalImageAdapter extends BaseAdapter {
    private static ImageView imageView;
    private static ViewHolder holder;
    public ImageLoader imageLoader;
    //    private List plotsImages;
    List<UserData> All_ToDay_Deals = new ArrayList<>();
    DisplayImageOptions options;

    Bundle bundle = new Bundle();
    String Level = "4";
    String Entry_ID;
    String Name_Item;
    private Activity context;
    private LayoutInflater l_Inflater;
    @SuppressWarnings("unused")
    private LayoutInflater inflater = null;

    public HorizontalImageAdapter(Activity context, List<UserData> All_ToDay_Deals) {
        this.context = context;
        this.All_ToDay_Deals = All_ToDay_Deals;
        l_Inflater = LayoutInflater.from(context);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnLoading(R.drawable.logo)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();


    }

    @Override
    public int getCount() {
        return All_ToDay_Deals.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_product_list_horizantal, parent, false);

            holder.imageView_item = (ImageView) convertView.findViewById(R.id.imageView_item);
            holder.textView_Item_Name = (TextView) convertView.findViewById(R.id.textView_Item_Name);
            holder.textView_Item_Name.setSelected(true);
            holder.layout_product = (LinearLayout) convertView.findViewById(R.id.layout_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String URL = All_ToDay_Deals.get(position).getImage_url();


        final ProgressBar progress = new ProgressBar(context);


        imageLoader.displayImage(URL, holder.imageView_item, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progress.setVisibility(View.GONE);
            }
        });


        holder.textView_Item_Name.setText(All_ToDay_Deals.get(position).getName());
        holder.layout_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ON CLICK", "layout_menu_list : " + position);
                Entry_ID = All_ToDay_Deals.get(position).getEntity_id();
                Name_Item = All_ToDay_Deals.get(position).getName();
                Log.v("layout Entry_ID", Entry_ID);
                Log.v("layout Name_Item", Name_Item);
//                Log.v("layout Entry_ID", Entry_ID);

//                Set_ActionBar_Title(Name_Item);

//                Fragement_Direct_ProductList();


            }
        });

        return convertView;
    }


    private class ViewHolder {
        LinearLayout layout_product;
        private ImageView imageView_item;
        private TextView textView_Item_Name;

    }

}