package com.cygnus.krios;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.readystatesoftware.viewbadger.BadgeView;

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
 * {@link Search.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    List<UserData> data = new ArrayList<UserData>();
    List<UserData> All_Search = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String searchText;
    private String Search_Text;
    private ListView listview_search_item;
    private OnFragmentInteractionListener mListener;

    public Search() {
        // Required empty public constructor
    }

    public static Search newInstance(String param1, String param2) {
        Search fragment = new Search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchText = getArguments().getString("Search_Text");

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (ConstantData.isNetworkConnected(getActivity())) {
                    new Get_Search().execute();
                } else {
                    ConstantData.DisplayAlert("Alert , Network !!!!", "Please Check Your Network And Try Again ", getActivity());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.v("onQueryTextChange : ", "" + newText);
                Search_Text = newText;
                return false;
            }
        });
//

        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.feed_update_count);
        View view = MenuItemCompat.getActionView(item);
//        setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        ImageView imageView_cart = (ImageView) view
                .findViewById(R.id.imageView_cart);

        final BadgeView badge1 = new BadgeView(getActivity(), imageView_cart);
        badge1.setText("" + 5);
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge1.toggle();
        badge1.show();
        imageView_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WishList.class));
                Toast.makeText(getActivity(), "jump to add to cart list", Toast.LENGTH_SHORT).show();
            }
        });
//         super.onCreateOptionsMenu(menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        for (int i = 0; i < 50; i++) {
            UserData mydata = new UserData();
            mydata.setName("ABC");
            mydata.setFinal_price_with_tax(299);
//            data.add(mydata);

        }
        if (ConstantData.isNetworkConnected(getActivity())) {
            new Get_Search().execute();
        } else {
            ConstantData.DisplayAlert("Alert , Network !!!!", "Please Check Your Network And Try Again ", getActivity());
        }
        listview_search_item = (ListView) view.findViewById(R.id.listview_search_item);
//        listview_search_item.setAdapter(new Search_Item());
        // Inflate the layout for this fragment
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
            Log.v("getCount", "" + All_Search.size());
            return All_Search.size();

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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.search_list, parent, false);

                holder.imageView = (ImageView) convertView.findViewById(R.id.imageView_item);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.textView_final_price);
                holder.tvProduct_Name = (TextView) convertView.findViewById(R.id.textView_Item_Name);
                holder.layout_product = (LinearLayout) convertView.findViewById(R.id.layout_product);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.layout_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("getposition : ", position + " : ");
                }
            });

            String URL = All_Search.get(position).getImage_url();
            final ProgressBar progress = new ProgressBar(getActivity());
//            holder.imageView.getLayoutParams().height = imgHeight;
//            holder.imageView.getLayoutParams().width = imgWidth;
            imageLoader.displayImage(URL, holder.imageView, options, new SimpleImageLoadingListener() {
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


            holder.tvProduct_Name.setText(All_Search.get(position).getName());
            holder.tvPrice.setText("Rs . " + All_Search.get(position).getFinal_price_with_tax());
            holder.layout_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("ON CLICK", "layout_menu_list : " + position);
                    String Entry_ID = All_Search.get(position).getEntity_id();


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

    public class Search_Item extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            Log.v("getCount", "" + All_Search.size());
            return All_Search.size();

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


            return convertView;
        }
    }

    private class ViewHolder {
        LinearLayout layout_product;
        private TextView tvPrice, tvProduct_Name;
        private ImageView imageView;

    }

    private class Get_Search extends
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
                HttpGet httpGet = new HttpGet("http://kriosdirect.com/api/rest/products/?order=name&filter[1][attribute]=name&filter[1][like]=%25" + searchText + "%25");
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

                            All_Search.add(userdata);
                        }// end
                        listview_search_item.setAdapter(new List_Menu());

                    } catch (Exception e) {
                        Log.e("Exception 1 ", e.toString());
                    }


                }

            }
        }
    }
}
