package com.cygnus.krios;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product_Full_Activity extends AppCompatActivity {


    public static List<UserData> Itme_Full_Detail = new ArrayList<>();
    LinearLayout linear_layout_Technical_Details, linear_layout_Product_Description,
            linear_layout_Ratings_Reviews, linear_layout_Tags, linear_tvreview_product,
            linear_image_Perview, linear_image_Next;
    FrameLayout framelayout_review;
    Button btnTechnical_Details, btnProduct_Description, btnRatings_Reviews, btnTags;
    EditText etQty;
    ImageView imageView_product, imageView_preview, imageView_next/*, imageView_minus, imageView_plus*/;
    int value = 0;
    Button btn_plus, btn_minus;
    TextView textView_material, textView_product_name, textView_product_id, textView_dimension,
            textView_unit, textView_finish, textView_discription;
    TextView textView_ragular_price, textView_final_price, textView_discount, textview_instock;
    int screenHeight, screenWidth;
    int imgHeight, imgWidth;
    Dialog dialog;
    String URL;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__full);
        findid();
        Log.v("Product_Full", "True");
        ConstantData.CheckNetwork(Product_Full_Activity.this);
        new Get_Full_Item_Details().execute("");
//        SetData(position);
        getSupportActionBar().hide();
        btnTechnical_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Layout_Hide_Show("1");
            }
        });
        btnProduct_Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Layout_Hide_Show("2");

            }
        });

        btnRatings_Reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Layout_Hide_Show("3");

            }
        });

        btnTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Layout_Hide_Show("4");

            }
        });
        linear_tvreview_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddFrag(new Product_Review());

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                value = value + 1;

                Log.d("value_plus--->>>", "" + value);
                etQty.setText("" + value);

            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value == 0) {
                    value = 0;
                } else {
                    value = value - 1;
                }
                Log.d("value_minus---", "" + value);
                etQty.setText("" + value);

            }
        });

        imageView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageView_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageView_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageLoader imageLoader;
                DisplayImageOptions options;
                LayoutInflater inflater = null;
                inflater = (LayoutInflater) Product_Full_Activity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(Product_Full_Activity.this));
                options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                        .showImageOnLoading(R.mipmap.logo)
                        .cacheOnDisk(true).considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565).build();
                dialog = new Dialog(Product_Full_Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_image);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setLayout(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                final ImageView image;
                image = (ImageView) dialog
                        .findViewById(R.id.imageView);
                final ProgressBar progress = new ProgressBar(Product_Full_Activity.this);
                imageLoader.displayImage(URL, image, options, new SimpleImageLoadingListener() {
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
                dialog.show();
            }
        });

    }

    void AddFrag(Fragment fragment) {
        bundle.putString("URL", URL);
        bundle.putString("entryid", ConstantData.Entity_id);
        bundle.putString("Name", Itme_Full_Detail.get(0).getName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.framelayout_review, fragment).addToBackStack(null).commit();
    }

    private void SetData() {

        LayoutInflater inflater = null;

        ImageLoader imageLoader;

        DisplayImageOptions options;

        inflater = (LayoutInflater) Product_Full_Activity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(Product_Full_Activity.this));
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .showImageOnLoading(R.drawable.logo)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();


        URL = Itme_Full_Detail.get(0).getImage_url();

        final ProgressBar progress = new ProgressBar(Product_Full_Activity.this);
        imageView_product.getLayoutParams().height = imgHeight;
        imageView_product.getLayoutParams().width = imgWidth;
        imageLoader.displayImage(URL, imageView_product, options, new SimpleImageLoadingListener() {
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
        textView_product_name.setText(Itme_Full_Detail.get(0).getName());
        textView_material.setText(Itme_Full_Detail.get(0).getMaterial());
        textView_product_id.setText(Itme_Full_Detail.get(0).getSku());
        textView_dimension.setText(Itme_Full_Detail.get(0).getProduct_dimension());
        textView_unit.setText(Itme_Full_Detail.get(0).getProduct_unit());
        textView_finish.setText(Itme_Full_Detail.get(0).getProduct_finish());
        textView_discription.setText(Itme_Full_Detail.get(0).getDescription());
        textView_ragular_price.setText("Rs. " + Itme_Full_Detail.get(0).getRegular_price_with_tax());
        textView_final_price.setText("Rs. " + Itme_Full_Detail.get(0).getFinal_price_with_tax());
        int regular_price = Itme_Full_Detail.get(0).getRegular_price_with_tax();
        int final_price = Itme_Full_Detail.get(0).getFinal_price_with_tax();

        if (regular_price > final_price) {
            textView_discount.setVisibility(View.VISIBLE);
            textView_final_price.setVisibility(View.VISIBLE);
            int discount = 100 - (100 * final_price / regular_price);
            textView_discount.setText("" + discount + "% OFF");
            textView_ragular_price.setBackgroundResource(R.drawable.underlinecentr);


        } else {
            textView_discount.setVisibility(View.GONE);
            textView_final_price.setVisibility(View.GONE);
        }
        LinearLayout layout_price = (LinearLayout) findViewById(R.id.layout_price);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < Itme_Full_Detail.get(0).tier_price.size(); i++) {
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundResource(R.drawable.layout_border);

            TextView tv_qty = new TextView(this);
            TextView tv_price = new TextView(this);
            lparams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lparams.setMargins(3, 3, 3, 3);

            tv_qty.setLayoutParams(lparams);
            tv_price.setLayoutParams(lparams);

            tv_qty.setPadding(5, 5, 5, 5);
            tv_price.setPadding(5, 5, 5, 5);

            tv_qty.setText("QTY : +" + Itme_Full_Detail.get(0).tier_price.get(i).getQty());
            tv_qty.setTextColor(Color.parseColor("#6B8622"));
            tv_price.setTextAppearance(Product_Full_Activity.this, android.R.style.TextAppearance_DeviceDefault_Medium);
            tv_price.setTextColor(Color.RED);
            tv_price.setText("Price : " + Itme_Full_Detail.get(0).tier_price.get(i).getPrice_with_tax());

            ll.addView(tv_qty);
            ll.addView(tv_price);
            layout_price.addView(ll, layoutParams);
        }
    }

    public void getDisplayHightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        imgHeight = (int) (screenHeight * 0.20); // 10% of screen.
        imgWidth = (int) (screenWidth * 0.30); // 25% of screen.

    }

    @Override
    public void onBackPressed() {
        int b = getSupportFragmentManager().getBackStackEntryCount();
        int a = getFragmentManager().getBackStackEntryCount();
        Log.v("getBackStackEntryCount", "" + a);
        Log.v("getBackStackEntryCount", " Fragement : " + b);
        if (b > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void findid() {
        linear_layout_Technical_Details = (LinearLayout) findViewById(R.id.linear_layout_Technical_Details);
        linear_layout_Product_Description = (LinearLayout) findViewById(R.id.linear_layout_Product_Description);
        linear_layout_Ratings_Reviews = (LinearLayout) findViewById(R.id.linear_layout_Ratings_Reviews);
        linear_layout_Tags = (LinearLayout) findViewById(R.id.linear_layout_Tags);
        linear_tvreview_product = (LinearLayout) findViewById(R.id.linear_tvreview_product);
        btnTechnical_Details = (Button) findViewById(R.id.btnTechnical_Details);
        btnProduct_Description = (Button) findViewById(R.id.btnProduct_Description);
        btnRatings_Reviews = (Button) findViewById(R.id.btnRatings_Reviews);
        btnTags = (Button) findViewById(R.id.btnTags);
        framelayout_review = (FrameLayout) findViewById(R.id.framelayout_review);
        imageView_product = (ImageView) findViewById(R.id.imageView_product);
        imageView_preview = (ImageView) findViewById(R.id.imageView_preview);
        imageView_next = (ImageView) findViewById(R.id.imageView_next);
        etQty = (EditText) findViewById(R.id.etQty);

        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_plus = (Button) findViewById(R.id.btn_add);
        textView_material = (TextView) findViewById(R.id.textView_material);
        textView_product_id = (TextView) findViewById(R.id.textView_product_id);
        textView_dimension = (TextView) findViewById(R.id.textView_dimension);
        textView_finish = (TextView) findViewById(R.id.textView_finish);
        textView_discription = (TextView) findViewById(R.id.textView_discription);
        textView_unit = (TextView) findViewById(R.id.textView_unit);
        textView_ragular_price = (TextView) findViewById(R.id.textView_ragular_price);
        textView_final_price = (TextView) findViewById(R.id.textView_final_price);
        textview_instock = (TextView) findViewById(R.id.textView_instock);
//        textview_instock.setText(Html.fromHtml("<p><span style='text-decoration: line-through'> in stock </span></p>"));
        textview_instock.setText("in stock");
//        textview_instock.setBackground(R.drawable.underlinecentr);

//        textview_instock.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView_discount = (TextView) findViewById(R.id.textView_discount);
        textView_product_name = (TextView) findViewById(R.id.textView_product_name);
        getDisplayHightWidth();
    }

    private void Layout_Hide_Show(String str) {

        if (str == "1") {
            btnTechnical_Details.setBackgroundResource(R.color.white_dark);
//            btnTechnical_Details.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Technical_Details.setVisibility(View.VISIBLE);

            btnProduct_Description.setBackgroundResource(R.color.white);
//            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
//            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white);
//            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);

        } else if (str == "2") {
            btnTechnical_Details.setBackgroundResource(R.color.white);
//            btnTechnical_Details.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Technical_Details.setVisibility(View.GONE);

            btnProduct_Description.setBackgroundResource(R.color.white_dark);
//            btnProduct_Description.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Product_Description.setVisibility(View.VISIBLE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
//            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white);
//            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);

        } else if (str == "3") {

            btnTechnical_Details.setBackgroundResource(R.color.white);
//            btnTechnical_Details.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Technical_Details.setVisibility(View.GONE);

            btnProduct_Description.setBackgroundResource(R.color.white);
//            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white_dark);
//            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Ratings_Reviews.setVisibility(View.VISIBLE);

            btnTags.setBackgroundResource(R.color.white);
//            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);
        } else if (str == "4") {
            btnTechnical_Details.setBackgroundResource(R.color.white);
//            btnTechnical_Details.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Technical_Details.setVisibility(View.GONE);


            btnProduct_Description.setBackgroundResource(R.color.white);
//            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
//            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white_dark);
//            btnTags.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Tags.setVisibility(View.VISIBLE);


        } else {
            btnTechnical_Details.setBackgroundResource(R.color.white_dark);
//            btnTechnical_Details.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Technical_Details.setVisibility(View.VISIBLE);

            btnProduct_Description.setBackgroundResource(R.color.white);
//            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
//            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white);
//            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);
        }

    }

    private class Get_Full_Item_Details extends
            AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            ConstantData.progressdialogshow(Product_Full_Activity.this);


        }

        @Override
        protected String doInBackground(String... urls) {
            String responceStr = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://kriosdirect.com/api/rest/products/" + ConstantData.Entity_id);
                httpGet.setHeader("Accept", "application/json");
                HttpResponse response = httpclient.execute(httpGet);

                Log.d("WebInvoke", "Saving: "
                        + response.getStatusLine().getStatusCode());

                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        responceStr = EntityUtils.toString(entity);
                    }
                }
            } catch (Exception e) {
                responceStr = "error:" + e.toString();
            }

            Log.v("responceStr", responceStr);
            return responceStr;
        }

        @Override
        protected void onPostExecute(String result) {
            ConstantData.progressdialogclose(Product_Full_Activity.this);
            if (result != null) {
                if (result.startsWith("error:")) {
                    ConstantData.progressdialogclose(Product_Full_Activity.this);
                    return;

                } else {
                    try {
                        JSONObject obj = new JSONObject(result);
                        UserData data = new UserData();

                        data.setEntity_id(obj.getString("entity_id").replace("'", "").replace("null" , ""));
                        data.setType_id(obj.getString("type_id").replace("'", ""));
                        data.setSku(obj.getString("sku").replace("'", ""));
                        data.setMeta_title(obj.getString("meta_title").replace("'", ""));
                        data.setMeta_description(obj.getString("meta_description").replace("'", ""));
                        data.setMaterial(obj.getString("material").replace("'", ""));
                        data.setProduct_finish(obj.getString("product_finish").replace("'", "").replace("null" , ""));
                        data.setProduct_dimension(obj.getString("product_dimension").replace("'", ""));
                        data.setProduct_unit(obj.getString("product_unit").replace("'", "").replace("null" , ""));
                        data.setDescription(obj.getString("description").replace("'", ""));
                        data.setShort_description(obj.getString("short_description").replace("'", ""));
                        data.setName(obj.getString("name").replace("'", ""));
                        data.setMeta_keyword(obj.getString("meta_keyword").replace("'", ""));
                        data.setRegular_price_with_tax(obj.getInt("regular_price_with_tax"));
                        data.setRegular_price_without_tax(obj.getInt("regular_price_without_tax"));
                        data.setFinal_price_with_tax(obj.getInt("final_price_with_tax"));
                        data.setFinal_price_without_tax(obj.getInt("final_price_without_tax"));
                        data.setIs_saleable(obj.getString("is_saleable").replace("'", ""));
                        data.setImage_url(obj.getString("image_url").replace("'", ""));


                        JSONArray jsonarray = new JSONArray(obj.getJSONArray("tier_price").toString());
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            String qty = jsonobject.getString("qty");
                            String price_with_tax = jsonobject.getString("price_with_tax");
                            String price_without_tax = jsonobject.getString("price_without_tax");

                            Temp_Data temp_data = new Temp_Data();
                            temp_data.setQty(jsonobject.getString("qty"));
                            temp_data.setPrice_with_tax(jsonobject.getString("price_with_tax"));
                            temp_data.setPrice_without_tax(jsonobject.getString("price_without_tax"));


                            data.tier_price.add(temp_data);
                            Log.v("qty 1 ", qty);
                            Log.v("price_with_tax 1 ", price_with_tax);
                            Log.v("price_without_tax 1 ", price_without_tax);


                        }
                        Itme_Full_Detail.clear();
                        Itme_Full_Detail.add(data);
                        SetData();


                    } catch (Exception e) {
                        Log.e("Exception 1 ", e.toString());
                    }


                }

            }
        }
    }
}
