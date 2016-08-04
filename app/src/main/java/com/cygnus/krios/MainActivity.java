package com.cygnus.krios;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static final int NUM_ITEMS = 6;
    //    static Button notifCount;
    static int mNotifCount = 0;
    String Level_ToDayList_Direct = "4";
    String Entry_ID_ToDayList_Direct;
    String Name_Item_ToDayList_Direct;
    String Search_Text;
    Databasehelper database;
    AQuery aq = new AQuery(MainActivity.this);
    Serviceresponce Responce;
    ImageView Add_Imageview;
    boolean TempStart = false;
    int screenHeight, screenWidth;
    int imgHeight, imgWidth;
    int lay_Height, lay_Width;
    NavigationView navigationView;
    boolean login = false;
    FrameLayout show_fragment;
    DrawerLayout drawer;
    Toolbar toolbar;
    ImageView imgsearchbar;
    boolean doubleBackToExitPressedOnce = false;
    TextView txt_today_deal, Tv_User_Name, Tv_User_Email;
    List<UserData> All_Menu_list = new ArrayList<>();
    List<UserData> All_ToDay_Deals = new ArrayList<>();
    List<UserData> Main_Menu_list = new ArrayList<>();
    String Level_3 = "3";
    String Level_4 = "4";
    String Entry_ID;
    String Name_Item = "";
    String Name = "";
    Bundle bundle = new Bundle();
    LinearLayout layout_bathroom, layout_hardware, layout_kitchen, layout_today_deal, layout_home_improvement;
    LinearLayout layout_bathroom_accessories, layout_soap_Dispenser, layout_mail_box, layout_Mirror_Cabinet,
            layout_cabinet_handle, layout_door_closer;
    private LinearLayout HorizontalView1;
    private GoogleApiClient client;
    private String ActionBar_Title;
    private DrawerLayout mDrawerLayout;
    private HorizontalImageAdapter imageAdapter;
//    private HorizontalView listView;

//    private List List_Horizontal_Todays_Deal;

    //    private GoogleApiClient client;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDisplayHightWidth();
        findid();

        database = new Databasehelper(MainActivity.this);

        try {
            database.createDataBase();
            database.openDatabase();
        } catch (Exception e) {

        }
        ConstantData.CheckNetwork(MainActivity.this);

        All_Menu_list = database.Get_InsertProduct_categories();
//        try {
////            new Get_ToDay_Deals().execute("");
//        } catch (Exception e) {
//            ConstantData.DisplayAlert("Connection Error !!", "Please check your network and Try Again !!", MainActivity.this);
//        }

        if (All_Menu_list.size() == 0) {
            try {
                new Get_Product_List().execute();
            } catch (Exception e) {
                ConstantData.DisplayAlert("Connection Error !!", "Please check your network and Try Again !!", MainActivity.this);
            }
        } else {
            new Get_ToDay_Deals().execute("");
            Main_Menu_list = database.Get_Menu_Level_2();
            set_Menu();
        }
        Set_Drawaer_Manu_Login_Logout();

        layout_bathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Bathroom";
                Entry_ID = "4";
                setTitle(ActionBar_Title);
                Fragement();
            }
        });
        layout_hardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Hardware";
                Entry_ID = "5";
                setTitle(ActionBar_Title);
                Fragement();
            }
        });
        layout_kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Kitchen";
                Entry_ID = "6";
                setTitle(ActionBar_Title);
                Fragement();
            }
        });
        layout_home_improvement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Home Improvement";
                Entry_ID = "7";
                setTitle(ActionBar_Title);
                Fragement();
            }
        });
        layout_today_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Today's Deal";
                setTitle(ActionBar_Title);
                Entry_ID = "274";
                Fragement_Direct_ToDay_Deals();
            }
        });
        layout_bathroom_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Bathroom Accessories";
                setTitle(ActionBar_Title);
                Entry_ID = "8";
                Fragement_Direct_SubMenu();
            }
        });
        layout_mail_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Mail Box";
                setTitle(ActionBar_Title);
                Entry_ID = "272";
                Fragement_Direct_SubMenu();
            }
        });


        layout_Mirror_Cabinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBar_Title = "Mirror and Cabinet";
                setTitle(ActionBar_Title);
                Entry_ID = "12";
                Fragement_Direct_SubMenu();
            }
        });
        layout_cabinet_handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActionBar_Title = "Cabinet Handles";
                setTitle(ActionBar_Title);
                Entry_ID = "201";
                Fragement_Direct_SubMenu();


            }
        });
        layout_door_closer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActionBar_Title = "Door Closer";
                setTitle(ActionBar_Title);
                Entry_ID = "119";
                Fragement_Direct_SubMenu();


            }
        });
        layout_soap_Dispenser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActionBar_Title = "Soap Dispenser";
                setTitle(ActionBar_Title);
                Entry_ID = "166";
                Fragement_Direct_SubMenu();


            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            int b = getSupportFragmentManager().getBackStackEntryCount();
            int a = getFragmentManager().getBackStackEntryCount();
            Log.v("getBackStackEntryCount", "" + a);
            Log.v("getBackStackEntryCount", " Fragement : " + b);


            if (b > 0) {
                getSupportFragmentManager().popBackStack();
//                setTitle("Home");
                if (b == 1) {
                    setTitle("Home");
                }
            } else {

//                super.onBackPressed();
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                } else {
                }

                this.doubleBackToExitPressedOnce = true;

                Toast.makeText(this, "Please click again to exit Krios", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);


            }


        }


//        }
    }

    public void horizantal_listview_setdata() {

        ImageLoader imageLoader;
        DisplayImageOptions options;
        Log.v("Start ori dyn", "true");

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageOnLoading(R.drawable.logo)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lparams_name = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final ProgressBar progress = new ProgressBar(MainActivity.this);

        for (int i = 0; i < All_ToDay_Deals.size(); i++) {
            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 5, 5, 5);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundResource(R.drawable.layout_border);
            ImageView image = new ImageView(this);
            TextView tv_name = new TextView(this);
            TextView tv_price = new TextView(this);
            lparams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lparams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            lparams_name.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lparams_name.height = LinearLayout.LayoutParams.WRAP_CONTENT;


            lparams.setMargins(3, 3, 3, 3);

//            tv_name.setLayoutParams(lparams_name);
//            tv_price.setLayoutParams(lparams);
            tv_name.setMinLines(3);
            image.setLayoutParams(lparams);

            tv_name.setPadding(5, 5, 5, 5);
            tv_price.setPadding(5, 5, 5, 5);
            image.setPadding(5, 5, 5, 5);

            image.getLayoutParams().height = imgHeight;
            image.getLayoutParams().width = imgWidth;
            tv_name.setText(All_ToDay_Deals.get(i).getName());
            tv_name.setTextColor(Color.parseColor("#6B8622"));
            tv_price.setTextAppearance(MainActivity.this, android.R.style.TextAppearance_DeviceDefault_Medium);
            tv_price.setTextColor(Color.RED);
            tv_price.setText("Rs." + All_ToDay_Deals.get(i).getFinal_price_with_tax());
            String URL = All_ToDay_Deals.get(i).getImage_url();


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
            ll.addView(image);
            ll.addView(tv_name);
            ll.addView(tv_price);
//            HorizontalView1.getLayoutParams().height = lay_Height;
//            HorizontalView1.getLayoutParams().width = lay_Width;

            HorizontalView1.addView(ll, layoutParams);

            final int position = HorizontalView1.indexOfChild(ll);
            HorizontalView1.setTag(position);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("position", "" + position);
                    ConstantData.Entity_id = All_ToDay_Deals.get(position).getEntity_id();
//                    ConstantData.pos = "" + position;
                    startActivity(new Intent(MainActivity.this, Product_Full_Activity.class));
                }
            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                bundle.putString("Search_Text", Search_Text);
                FragmentManager mFragmentManager = getSupportFragmentManager();
                mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Search Search = new Search();
                Search.setArguments(bundle);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.show_fragment, Search).addToBackStack(null);
                ft.commit();
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

        final BadgeView badge1 = new BadgeView(this, imageView_cart);
        badge1.setText("" + 5);
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge1.toggle();
        badge1.show();
        imageView_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WishList.class));
                Toast.makeText(MainActivity.this, "jump to add to cart list", Toast.LENGTH_SHORT).show();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {

            case R.id.action_search:
                return true;


            case R.id.badge:

//                startActivity(new Intent(MainActivity.this, WishList.class));

                return true;


            case R.id.searchbar:

//                getActionBar().hide();

                return true;


            case R.id.action_cart:
                startActivity(new Intent(MainActivity.this, WishList.class));

//
                return true;
            case R.id.action_logout:
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                return true;
            case R.id.action_share:
//                onBackPressed();
                return true;
        }
        return false;
    }


    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        mDrawerLayout.closeDrawers();


//                        Toast.makeText(getApplicationContext(), "request moving quite", Toast.LENGTH_SHORT).show();
                        return true;


                    }
                });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Home) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        } else if (id == R.id.nav_Hardware) {
            ActionBar_Title = "Hardware";
            Entry_ID = "5";
            setTitle(ActionBar_Title);
            Fragement();
        } else if (id == R.id.nav_Bathroom) {
            ActionBar_Title = "Bathroom";
            setTitle(ActionBar_Title);
            Entry_ID = "4";
            Fragement();
        } else if (id == R.id.nav_Kitchens_and_Wardrobes) {
            ActionBar_Title = "Kitchen";
            setTitle(ActionBar_Title);
            Entry_ID = "6";
            Fragement();
        } else if (id == R.id.nav_Home_Improvement) {
            ActionBar_Title = "Home Improvement";
            setTitle(ActionBar_Title);
            Entry_ID = "7";
            Fragement();
        } else if (id == R.id.nav_todays_deal) {
            ActionBar_Title = "Today's Deal";
            setTitle(ActionBar_Title);
            Entry_ID = "274";
            Fragement_Direct_ToDay_Deals();
        } else if (id == R.id.nav_Kitchen_Accessories) {
            ActionBar_Title = "Kitchen Accessories";
            setTitle(ActionBar_Title);
            Entry_ID = "125";
            Fragement();
        } else if (id == R.id.nav_Home_Accessories) {
            ActionBar_Title = "Home Accessories";
            setTitle(ActionBar_Title);
            Entry_ID = "136";
            Fragement();
        } else if (id == R.id.nav_tFurniture_Fittings) {
            ActionBar_Title = "Furniture Fittings ";
            setTitle(ActionBar_Title);
            Entry_ID = "137";
            Fragement();
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        } else if (id == R.id.nav_login) {
            login = true;
            Set_Drawaer_Manu_Login_Logout();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        } else if (id == R.id.nav_logout) {
            login = false;
            Set_Drawaer_Manu_Login_Logout();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cygnus.krios/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cygnus.krios/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void Fragement() {
        bundle.putString("level", Level_3);
        bundle.putString("entryid", Entry_ID);
        bundle.putString("Name", ActionBar_Title);
        MenuFragment fragobj = new MenuFragment();
        fragobj.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.show_fragment, fragobj).addToBackStack("MainActivity");
        ft.commit();

    }

    private void Fragement_Direct_SubMenu() {
        bundle.putString("level", Level_4);
        bundle.putString("entryid", Entry_ID);
        bundle.putString("Name", Name_Item);
        SubMenuFragment fragobj = new SubMenuFragment();
        fragobj.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.show_fragment, fragobj).addToBackStack(null);
        ft.commit();
    }

    private void Fragement_Direct_ToDay_Deals() {
        bundle.putString("level", Level_3);
        bundle.putString("entryid", Entry_ID);
        bundle.putString("Name", Name);
        Product_list_Fragment fragobj = new Product_list_Fragment();
        fragobj.setArguments(bundle);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.show_fragment, fragobj).addToBackStack(null);
        ft.commit();

    }

    private void setanim() {
        Add_Imageview.setBackgroundResource(R.drawable.fram);
        AnimationDrawable gyroAnimation = (AnimationDrawable) Add_Imageview.getBackground();
        gyroAnimation.start();
    }


    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (txt_today_deal.getVisibility() == View.VISIBLE) {
                            txt_today_deal.setVisibility(View.INVISIBLE);
                        } else {
                            txt_today_deal.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    private void Fragment_call() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.show_fragment, new HomeFreg()).addToBackStack("MainActivity");
        ft.commit();
    }

    private void Set_Drawaer_Manu_Login_Logout() {
        if (ConstantData.Login_User == null || ConstantData.Login_User == "") {
            login = false;
        } else {
            Tv_User_Name.setText(ConstantData.Login_User);
            Tv_User_Email.setText(ConstantData.Login_User + "@gmail.com");
            login = true;
        }
        Menu nav_Menu = navigationView.getMenu();
        if (login) {
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
            nav_Menu.findItem(R.id.nav_Account_Dashboard).setVisible(true);
            nav_Menu.findItem(R.id.nav_Account_Information).setVisible(true);
            nav_Menu.findItem(R.id.nav_My_Downloadable_Products).setVisible(true);
            nav_Menu.findItem(R.id.nav_Address_Book).setVisible(true);
            nav_Menu.findItem(R.id.nav_Billing_Agreements).setVisible(true);
            nav_Menu.findItem(R.id.nav_My_Applications).setVisible(true);
            nav_Menu.findItem(R.id.nav_My_Orders).setVisible(true);
            nav_Menu.findItem(R.id.nav_My_Tags).setVisible(true);
            nav_Menu.findItem(R.id.nav_Your_Wish_List).setVisible(true);
            nav_Menu.findItem(R.id.nav_Recurring_Profiles).setVisible(true);
            nav_Menu.findItem(R.id.nav_Newsletter_Subscriptions).setVisible(true);
            nav_Menu.findItem(R.id.nav_My_Product_Reviews).setVisible(true);
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            nav_Menu.findItem(R.id.nav_Account_Dashboard).setVisible(false);
            nav_Menu.findItem(R.id.nav_Account_Information).setVisible(false);
            nav_Menu.findItem(R.id.nav_My_Downloadable_Products).setVisible(false);
            nav_Menu.findItem(R.id.nav_Address_Book).setVisible(false);
            nav_Menu.findItem(R.id.nav_Billing_Agreements).setVisible(false);
            nav_Menu.findItem(R.id.nav_My_Applications).setVisible(false);
            nav_Menu.findItem(R.id.nav_My_Orders).setVisible(false);
            nav_Menu.findItem(R.id.nav_My_Tags).setVisible(false);
            nav_Menu.findItem(R.id.nav_Your_Wish_List).setVisible(false);
            nav_Menu.findItem(R.id.nav_Recurring_Profiles).setVisible(false);
            nav_Menu.findItem(R.id.nav_Newsletter_Subscriptions).setVisible(false);
            nav_Menu.findItem(R.id.nav_My_Product_Reviews).setVisible(false);
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
        }
    }

    private void set_Menu() {
        Menu nav_Menu = navigationView.getMenu();
        for (int i = 0; i < Main_Menu_list.size(); i++) {

            if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Kitchen Accessories")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_Kitchen_Accessories).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_Kitchen_Accessories).setVisible(false);
                }

            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Home Accessories")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_Home_Accessories).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_Home_Accessories).setVisible(false);
                }
            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Furniture Fittings ")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_tFurniture_Fittings).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_tFurniture_Fittings).setVisible(false);
                }
            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Todays Deals")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_todays_deal).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_todays_deal).setVisible(false);
                }
            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Bathroom")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_Bathroom).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_Bathroom).setVisible(false);
                }
            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Hardware")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_Hardware).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_Hardware).setVisible(false);
                }
            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Kitchens and Wardrobes")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_Kitchens_and_Wardrobes).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_Kitchens_and_Wardrobes).setVisible(false);
                }
            } else if (Main_Menu_list.get(i).getName().equalsIgnoreCase("Home  Improvement")) {
                if (Main_Menu_list.get(i).getIs_active().equalsIgnoreCase("1")) {
                    nav_Menu.findItem(R.id.nav_Home_Improvement).setVisible(true);
                } else {
                    nav_Menu.findItem(R.id.nav_Kitchen_Accessories).setVisible(false);
                }
            }
        }
        Tv_User_Name.setText(ConstantData.Login_User);
        Tv_User_Email.setText(ConstantData.Login_User + "@gmail.com");
    }

//    private void Set_ActionBar_Title(String title) {
//        setTitle(title);
//    }

    private void findid() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        Tv_User_Name = (TextView) header.findViewById(R.id.Textview_UserName);
        Tv_User_Email = (TextView) header.findViewById(R.id.Textview_User_Email);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        txt_today_deal = (TextView) findViewById(R.id.txt_today_deal);
        show_fragment = (FrameLayout) findViewById(R.id.show_fragment);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        Add_Imageview = (ImageView) findViewById(R.id.frame_image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgsearchbar = (ImageView) findViewById(R.id.imgsearchbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        layout_bathroom = (LinearLayout) findViewById(R.id.layout_bathroom);
        layout_hardware = (LinearLayout) findViewById(R.id.layout_hardware);
        layout_kitchen = (LinearLayout) findViewById(R.id.layout_kitchen);
        layout_home_improvement = (LinearLayout) findViewById(R.id.layout_home_improvement);
        layout_today_deal = (LinearLayout) findViewById(R.id.layout_today_deal);
        layout_bathroom_accessories = (LinearLayout) findViewById(R.id.layout_bathroom_accessories);
//        layout_towel_rack = (LinearLayout) findViewById(R.id.layout_towel_rack);
        layout_soap_Dispenser = (LinearLayout) findViewById(R.id.layout_soap_Dispenser);
        layout_mail_box = (LinearLayout) findViewById(R.id.layout_mail_box);
        layout_Mirror_Cabinet = (LinearLayout) findViewById(R.id.layout_Mirror_Cabinet);
        layout_cabinet_handle = (LinearLayout) findViewById(R.id.layout_cabinet_handle);
        layout_door_closer = (LinearLayout) findViewById(R.id.layout_door_closer);
        HorizontalView1 = (LinearLayout) findViewById(R.id.HorizontalView1);
        Add_Imageview.getLayoutParams().height = lay_Height;
        blink();
        setanim();

    }


    private void GetData() {
        aq.progress(R.id.progressBar).ajax("http://www.kriosdirect.com/api/rest/products?page=20&limit=10", JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    Gson gson = new GsonBuilder().create();
                    JsonResult jsonResult = new JsonResult();
                    JsonElement json2 = new JsonParser()
                            .parse(json.toString());
                    Log.e("jsonCallback ", url + " JSONObject : " + json2);
                    Responce = gson.fromJson(
                            json2, Serviceresponce.class);
                    try {

                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }

                } else {
                    Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void getDisplayHightWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

//        imgHeight = (int) (screenHeight * 0.20); // 10% of screen.
//        imgWidth = (int) (screenWidth * 0.30); // 25% of screen.

        imgHeight = (int) (screenHeight * 0.25); // 10% of screen.
        imgWidth = (int) (screenWidth * 0.40); // 25% of screen.
        lay_Height = (int) (screenHeight * 0.30); // 10% of screen.
        lay_Width = (int) (screenHeight * 0.40);


    }


    private class Get_Product_List extends
            AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            ConstantData.progressdialogshow(MainActivity.this);
        }

        @Override
        protected String doInBackground(String... urls) {
            String responceStr = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://kriosdirect.com/api/rest/magepim/products/catlist");
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
            ConstantData.progressdialogclose(MainActivity.this);
            if (result != null) {
                if (result.startsWith("error:")) {
                    ConstantData.progressdialogclose(MainActivity.this);
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
                            userdata.setEntity_id(json_data_obj.getString("entity_id").replace("'", "").replace("null", ""));
//                            userdata.setEntity_type_id(json_data_obj.getString("entity_type_id").replace("'", ""));
//                            userdata.setAttribute_set_id(json_data_obj.getString("attribute_set_id").replace("'", ""));
                            userdata.setParent_id(json_data_obj.getString("parent_id").replace("'", "").replace("null", ""));
                            userdata.setMeta_description(json_data_obj.getString("meta_description").replace("'", ""));
//                            userdata.setUpdated_at(json_data_obj.getString("updated_at").replace("'", ""));
                            userdata.setMeta_title(json_data_obj.getString("meta_title").replace("'", "").replace("null", ""));
                            userdata.setPosition(json_data_obj.getString("position").replace("'", "").replace("null", ""));
                            userdata.setLevel(json_data_obj.getString("level").replace("'", "").replace("null", ""));
                            userdata.setDescription(json_data_obj.getString("description").replace("'", "").replace("null", ""));
//                            userdata.setIs_active(json_data_obj.getString("is_active").replace("'", ""));
                            userdata.setName(json_data_obj.getString("name").replace("'", "").replace("null", ""));

                            All_Menu_list.add(userdata);
                        }// end
                        database.InsertProduct_categories(All_Menu_list);
                    } catch (Exception e) {
                        Log.e("Exception 1 ", e.toString());
                    }
                    Main_Menu_list = database.Get_Menu_Level_2();
                    set_Menu();

                    new Get_ToDay_Deals().execute("");


                    ConstantData.progressdialogclose(MainActivity.this);
                }

            }
        }
    }

    private class Get_ToDay_Deals extends
            AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            ConstantData.progressdialogshow(MainActivity.this);


        }

        @Override
        protected String doInBackground(String... urls) {
            String responceStr = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://kriosdirect.com/api/rest/products?category_id=274");
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
            ConstantData.progressdialogclose(MainActivity.this);
            if (result != null) {
                if (result.startsWith("error:")) {
                    ConstantData.progressdialogclose(MainActivity.this);
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

                            All_ToDay_Deals.add(userdata);
                        }// end
                        horizantal_listview_setdata();


                    } catch (Exception e) {
                        Log.e("Exception 1 ", e.toString());
                    }


                }

            }
        }
    }


}