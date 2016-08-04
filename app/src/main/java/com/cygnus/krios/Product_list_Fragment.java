package com.cygnus.krios;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Product_list_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Product_list_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Product_list_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GridView gridView;
    List<UserData> All_Menu_list = new ArrayList<>();
    int screenHeight, screenWidth;
    int imgHeight, imgWidth;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String Entry_ID;
    private OnFragmentInteractionListener mListener;

    public Product_list_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Product_list_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Product_list_Fragment newInstance(String param1, String param2) {
        Product_list_Fragment fragment = new Product_list_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("level");
            mParam2 = getArguments().getString("entryid");
            mParam3 = getArguments().getString("Name");

            Log.v("level", mParam1);
            Log.v("entryid", mParam2);
            Log.v("Name", mParam3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list_vertical, container, false);

        gridView = (GridView) view.findViewById(R.id.gridView);
        getDisplayHightWidth();


        if (ConstantData.isNetworkConnected(getActivity())) {
            new Get_Product_List().execute();
        } else {
            ConstantData.DisplayAlert("Alert , Network !!!!", "Please Check Your Network And Try Again ", getActivity());
        }
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getDisplayHightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        imgHeight = (int) (screenHeight * 0.20); // 10% of screen.
        imgWidth = (int) (screenWidth * 0.30); // 25% of screen.
//        imgcenter.getLayoutParams().height = imgHeight;
//        imgcenter.getLayoutParams().width = imgWidth;

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class List_Menu extends BaseAdapter {

//        private LayoutInflater inflater;
//        private DisplayImageOptions options = null;

        public ImageLoader imageLoader;

        DisplayImageOptions options;

        private List<UserData> mData;

        @SuppressWarnings("unused")
        private LayoutInflater inflater = null;

        public List_Menu() {
            inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
            options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .showImageOnLoading(R.mipmap.logo)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            Log.v("getCount", "" + All_Menu_list.size());
            return All_Menu_list.size();

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
                    getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_product_list, parent, false);

                holder.imageView_item = (ImageView) convertView.findViewById(R.id.imageView_item);
                holder.textView_Item_Name = (TextView) convertView.findViewById(R.id.textView_Item_Name);
                holder.textView_final_price = (TextView) convertView.findViewById(R.id.textView_final_price);
                holder.textView_Item_Name.setSelected(true);
                holder.layout_product = (LinearLayout) convertView.findViewById(R.id.layout_product);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String URL = All_Menu_list.get(position).getImage_url();


//            final ImageView imageView;
            final ProgressBar progress = new ProgressBar(getActivity());


            holder.imageView_item.getLayoutParams().height = imgHeight;
            holder.imageView_item.getLayoutParams().width = imgWidth;


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


            holder.textView_Item_Name.setText(All_Menu_list.get(position).getName());
            holder.textView_final_price.setText("Rs . " + All_Menu_list.get(position).getFinal_price_with_tax());
            holder.layout_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("ON CLICK", "layout_menu_list : " + position);
                    Entry_ID = All_Menu_list.get(position).getEntity_id();
//                    Name_Item = All_Menu_list.get(position).getName();
//                    Log.v("layout Entry_ID", Entry_ID);
//                    Set_ActionBar_Title(mParam3 + "-->" + Name_Item);
//                    Fragement();


                    ConstantData.Entity_id = "" + Entry_ID;

//                    Intent i = new Intent(getActivity(), Product_list_Fragment.class);
//                    i.putExtra("position", position);
//                    startActivity(i);
                    startActivity(new Intent(getActivity(), Product_Full_Activity.class));
                }
            });

            return convertView;
        }
    }

    private class ViewHolder {
        LinearLayout layout_product;
        private TextView textView_final_price;
        private ImageView imageView_item;
        private TextView textView_Item_Name;

    }

    private class Get_Product_List extends
            AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            ConstantData.progressdialogshow(getActivity());


        }

        @Override
        protected String doInBackground(String... urls) {
            String responceStr = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://kriosdirect.com/api/rest/products?category_id=" + mParam2);
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

            Log.v("responce Product List", responceStr);
            return responceStr;
        }

        @Override
        protected void onPostExecute(String result) {
            ConstantData.progressdialogclose(getActivity());
            if (result != null) {
                if (result.startsWith("error:")) {
                    ConstantData.progressdialogclose(getActivity());
                    return;

                } else {
                    try {

                        JSONObject obj = new JSONObject(result);
                        JSONObject json_data_obj = new JSONObject();
                        Iterator<String> keys1 = obj.keys();
                        while (keys1.hasNext()) {

                            String key = (String) keys1.next();
                            json_data_obj = obj.getJSONObject(key);
//                            JSONObject json_data_obj = obj.getJSONObject(obj2.toString());

                            UserData userdata = new UserData();
//                            userdata.setEntity_id("abc");

                            userdata.setEntity_id(json_data_obj.getString("entity_id").replace("'", ""));
                            userdata.setType_id(json_data_obj.getString("type_id").replace("'", ""));
                            userdata.setSku(json_data_obj.getString("sku").replace("'", ""));
                            userdata.setMeta_title(json_data_obj.getString("meta_title").replace("'", ""));
                            userdata.setMeta_description(json_data_obj.getString("meta_description").replace("'", ""));
                            userdata.setMaterial(json_data_obj.getString("material").replace("'", ""));
                            userdata.setProduct_finish(json_data_obj.getString("product_finish").replace("'", ""));
                            userdata.setProduct_dimension(json_data_obj.getString("product_dimension").replace("'", ""));
                            userdata.setProduct_unit(json_data_obj.getString("product_unit").replace("'", ""));
                            userdata.setDescription(json_data_obj.getString("description").replace("'", ""));
                            userdata.setShort_description(json_data_obj.getString("short_description").replace("'", ""));
                            userdata.setName(json_data_obj.getString("name").replace("'", ""));
                            userdata.setMeta_keyword(json_data_obj.getString("meta_keyword").replace("'", ""));
                            userdata.setRegular_price_with_tax(json_data_obj.getInt("regular_price_with_tax"));
                            userdata.setRegular_price_without_tax(json_data_obj.getInt("regular_price_without_tax"));
                            userdata.setFinal_price_with_tax(json_data_obj.getInt("final_price_with_tax"));
                            userdata.setFinal_price_without_tax(json_data_obj.getInt("final_price_without_tax"));
                            userdata.setIs_saleable(json_data_obj.getString("is_saleable").replace("'", ""));
                            userdata.setImage_url(json_data_obj.getString("image_url").replace("'", ""));

                            All_Menu_list.add(userdata);
                        }// end

                    } catch (Exception e) {
                        Log.e("Exception 1 ", e.toString());
                    }


                    gridView.setAdapter(new List_Menu());

                }

            }
        }
    }
}
