package com.cygnus.krios;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    List<MyDataType> data = new ArrayList<MyDataType>();
    ListView Menu_list;
    Databasehelper database;
    List<UserData> All_Sub_Menu_list = new ArrayList<UserData>();
    List<String> Sub_Menu_list = new ArrayList<String>();
    Bundle bundle = new Bundle();
    String Level = "4";
    String Entry_ID;
    String Name_Item;
    // TODO: Rename and change types of parameters
    String Search_Text;
    private String mParam1;
    private String mParam2;
    private String mParam3 = "";
    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private void Set_ActionBar_Title(String title) {
        getActivity().setTitle(title);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            database = new Databasehelper(getActivity());
            try {
                database.createDataBase();
                database.openDatabase();
            } catch (Exception e) {

            }
            mParam1 = getArguments().getString("level");
            mParam2 = getArguments().getString("entryid");
            mParam3 = getArguments().getString("Name");
            All_Sub_Menu_list = database.Get_Menu_Level_3(mParam1, mParam2);

//            ActionBar actionBar  = get().getActionBar();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_menu, container, false);


        for (int i = 0; i < All_Sub_Menu_list.size(); i++) {
            Sub_Menu_list.add(All_Sub_Menu_list.get(i).getName());
        }
        Menu_list = (ListView) view.findViewById(R.id.listView);
        Menu_list.setAdapter(new List_Menu());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.v("onCreateOptionsMenu","onCreateOptionsMenu");
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
                bundle.putString("Search_Text", Search_Text);
                FragmentManager mFragmentManager = getFragmentManager();
                mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                Search Search = new Search();
                Search.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
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
        Log.v("onCreateOptionsMenu","onCreateOptionsMenu  over  " );
//         super.onCreateOptionsMenu(menu);
    }

    private void Fragement() {
        bundle.putString("level", Level);
        bundle.putString("entryid", Entry_ID);
        bundle.putString("Name", Name_Item);
        SubMenuFragment fragobj = new SubMenuFragment();
        fragobj.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.show_fragment, fragobj).addToBackStack(null);
        ft.commit();

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class List_Menu extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            Log.v("getCount", "" + Sub_Menu_list.size());
            return Sub_Menu_list.size();

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

            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            LinearLayout layout_menu_list = null;
            TextView TextView_Menu = null;

            if (convertView == null) {

                convertView = inflater.inflate(
                        R.layout.fragment_manu, parent, false);
                TextView_Menu = (TextView) convertView.findViewById(R.id.TextView_Menu);
                layout_menu_list = (LinearLayout) convertView.findViewById(R.id.layout_menu_list);
            }
//            else {
//                holder = (RecyclerView.ViewHolder) convertView.getTag();
//            }
            Log.v("position", "" + position);
            TextView_Menu.setText(Sub_Menu_list.get(position));
            layout_menu_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Entry_ID = All_Sub_Menu_list.get(position).getEntity_id();
                    Name_Item = All_Sub_Menu_list.get(position).getName();
                    Log.v("layout Entry_ID", Entry_ID);
                    if (mParam3 == null || mParam3 == "") {
                        Set_ActionBar_Title(Name_Item);
                    } else {
                        Set_ActionBar_Title(mParam3 + "  >>  " + Name_Item);

                    }
                    Fragement();
                }
            });
            return convertView;
        }
    }

//    private class ViewHolder {
//        LinearLayout layout_menu_list;
//        private TextView TextView_Menu;
//
//    }
}
