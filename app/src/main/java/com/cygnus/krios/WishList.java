package com.cygnus.krios;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class WishList extends AppCompatActivity {
    List<UserData> data = new ArrayList<UserData>();
    ListView listView;

    int screenHeight, screenWidth;
    int imgHeight, imgWidth;
    int lay_Height, lay_Width;
    List<String> list_qty = new ArrayList<String>();
    double qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = WishList.this.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        setTitle("");
        findid();
        getDisplayHightWidth();

        for (int i = 0; i < 50; i++) {
            UserData mydata = new UserData();
            mydata.setName("ABC");
            mydata.setFinal_price_with_tax(i);
            mydata.setQty(1);
            data.add(mydata);

        }
        for (int i = 1; i <= 15; i++) {
            list_qty.add("" + i);
        }

        listView.setAdapter(new Wishlist());
        listView.deferNotifyDataSetChanged();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

//        getActionBar().setDisplayShowTitleEnabled(true);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayUseLogoEnabled(false);
//        getActionBar().setDisplayShowHomeEnabled(true);
        // ///////////////// ACTIONBAR HOME IMAGE //////////////////////
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
//                | ActionBar.DISPLAY_SHOW_CUSTOM);


        getMenuInflater().inflate(R.menu.wishlist_main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            finish();
        }
        return true;

    }

    private void findid() {
        listView = (ListView) findViewById(R.id.listView);

    }

    private ArrayAdapter<String> setInitAdpterspinner(
            List<String> loadSize_Listtemp2) {
        ArrayAdapter<String> qty = new ArrayAdapter<String>(
                WishList.this, R.layout.spinner_blacktext,
                loadSize_Listtemp2);
        qty.setDropDownViewResource(R.layout.spinner_blacktext2);
        // spcategory.setAdapter(catagory);
        return qty;
    }

    public void getDisplayHightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

//        imgHeight = (int) (screenHeight * 0.20); // 10% of screen.
//        imgWidth = (int) (screenWidth * 0.30); // 25% of screen.

        imgHeight = (int) (screenHeight * 0.18); // 10% of screen.
        imgWidth = (int) (screenWidth * 0.30);
        lay_Height = (int) (screenHeight * 0.30); // 10% of screen.
        lay_Width = (int) (screenHeight * 0.40);


    }

    public class Wishlist extends BaseAdapter {
        ImageLoader imageLoader;
        DisplayImageOptions options;

        public Wishlist() {

            Log.v("Start ori dyn", "true");

            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(WishList.this));
            options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .showImageOnLoading(R.drawable.logo)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            Log.v("getCount", "" + data.size());
            return data.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater)
                    WishList.this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(WishList.this).inflate(R.layout.wishlist, parent, false);
                holder.imageView_product = (ImageView) convertView.findViewById(R.id.imageView_product);
//                holder.spinner_qty = (Spinner) convertView.findViewById(R.id.spinner_qty);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
                holder.tv_Add_To_Cart = (TextView) convertView.findViewById(R.id.tv_Add_To_Cart);
                holder.tv_Remove_Item = (TextView) convertView.findViewById(R.id.tv_Remove_Item);
                holder.tvProduct_Name = (TextView) convertView.findViewById(R.id.tvProduct_Name);
                holder.layout_wishlist = (LinearLayout) convertView.findViewById(R.id.layout_wishlist);
                holder.EditText_Qty = (EditText) convertView.findViewById(R.id.EditText_Qty);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView_product.getLayoutParams().height = imgHeight;
            Log.v("imgHeight", imgHeight + "");
            holder.imageView_product.getLayoutParams().width = imgWidth;
            Log.v("imgWidth", imgWidth + "");

//            holder.spinner_qty.setAdapter(setInitAdpterspinner(list_qty));

//            holder.spinner_qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position_spi, long id) {
//
//                    Log.v("position_spi : ", position_spi + " : ");
//                    Log.v("position : ", position + " : ");
//                    qty = Double.parseDouble(holder.spinner_qty.getSelectedItem().toString());
//                    Log.v("setQty spinner", qty + " : ");
//                    data.get(position).setQty(qty);
//                    if (qty > 14) {
//                        holder.spinner_qty.setVisibility(View.GONE);
//                        holder.EditText_Qty.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.spinner_qty.setVisibility(View.VISIBLE);
//                        holder.EditText_Qty.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//            qty = data.get(position).getQty();
//
////            holder.EditText_Qty.removeTextChangedListener((TextWatcher) this);
//            holder.EditText_Qty.setText("" + qty);
//            holder.EditText_Qty.addTextChangedListener((TextWatcher) this);


//            if (qty > 14) {
//                holder.spinner_qty.setVisibility(View.GONE);
//                holder.EditText_Qty.setVisibility(View.VISIBLE);
////                holder.EditText_Qty.setText("" + qty);
//            } else {
//                holder.spinner_qty.setVisibility(View.VISIBLE);
//                holder.EditText_Qty.setVisibility(View.GONE);
//            }

//            for (int j = 0; j < list_qty.size(); j++) {
//                if (Double.parseDouble(list_qty.get(j)) == qty) {
//                    holder.spinner_qty
//                            .setSelection(j);
//                    Log.v("j", j + " : ");
//                    Log.v("qty", qty + " : ");
//
//                }
//            }
//            holder.EditText_Qty.getOnFocusChangeListener().onFocusChange();
            holder.EditText_Qty
                    .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                final int position = Integer.parseInt(v
                                        .getTag().toString());
                                final EditText Caption = (EditText) v;
                                String Temp_qty = "0";
                                if (Caption.getText().toString().equalsIgnoreCase(null) || Caption.getText().toString().equalsIgnoreCase("")) {
                                    Temp_qty = "1";
                                    Log.v("Temp_qty edittext ", qty + " : ");
                                } else {
                                    Temp_qty = Caption.getText().toString();
                                    Log.v("setQty edittext ", qty + " : ");
                                }
                                qty = Double.parseDouble(Temp_qty);


//                                GetArticleList.get(position).setQTY(
//                                        (Caption.getText().toString()));
                                data.get(position).setQty(qty);


                            }
                        }
                    });
            qty = data.get(position).getQty();


            holder.EditText_Qty.setText("" + qty);


            holder.EditText_Qty.setTag(position);

//            holder.EditText_Qty.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    String Temp_qty = "0";
//                    if (holder.EditText_Qty.getText().toString().equalsIgnoreCase(null) || holder.EditText_Qty.getText().toString().equalsIgnoreCase("")) {
//                        Temp_qty = "1";
//                        Log.v("Temp_qty edittext ", qty + " : ");
//                    } else {
//                        Temp_qty = holder.EditText_Qty.getText().toString();
//                        Log.v("setQty edittext ", qty + " : ");
//                    }
//                    qty = Double.parseDouble(Temp_qty);
//
//                    data.get(position).setQty(qty);
//
//                }
//            });
            holder.tvPrice.setText("Rs.132");


            holder.tv_Remove_Item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.tvProduct_Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            String URL = "http://www.kriosdirect.com/media/catalog/product/cache/0/image/9df78eab33525d08d6e5fb8d27136e95/G/0/G0026IT0022_main_4.jpg";
            imageLoader.displayImage(URL, holder.imageView_product, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    Log.v("onLoadingStarted", "onLoadingStarted");
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    Log.v("onLoadingFailed", "onLoadingFailed");
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.v("onLoadingComplete", "onLoadingComplete");
                }
            });
            holder.layout_wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("getposition : ", position + " : ");
                }
            });


            return convertView;
        }
    }

    private class ViewHolder {
        LinearLayout layout_wishlist;
        //        Spinner spinner_qty;
        private TextView tvPrice, tvProduct_Name, tv_Add_To_Cart, tv_Remove_Item;
        private ImageView imageView_product;
        private EditText EditText_Qty;

    }
}
