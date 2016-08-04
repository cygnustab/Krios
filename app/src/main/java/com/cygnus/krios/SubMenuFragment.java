package com.cygnus.krios;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubMenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubMenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView Menu_list;
    Databasehelper database;
    List<UserData> All_Sub_Menu_list = new ArrayList<UserData>();
    List<String> Sub_Menu_list = new ArrayList<String>();

    Bundle bundle = new Bundle();
    String Level = "4";
    String Entry_ID;
    String Name_Item;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private OnFragmentInteractionListener mListener;

    public SubMenuFragment() {
        // Required empty public constructor
    }


    public static SubMenuFragment newInstance(String param1, String param2) {
        SubMenuFragment fragment = new SubMenuFragment();
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

            Log.v("All_Sub_Menu_list", "" + All_Sub_Menu_list.size());


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_menu, container, false);

        if (All_Sub_Menu_list.size() == 0) {
            Log.v("If", "" + All_Sub_Menu_list.size());
            Fragement_Direct_ProductList();
        } else {

            for (int i = 0; i < All_Sub_Menu_list.size(); i++) {
                Sub_Menu_list.add(All_Sub_Menu_list.get(i).getName());
            }
            Menu_list = (ListView) view.findViewById(R.id.listView);
            Menu_list.setAdapter(new List_Menu());

            Log.v("Else", "" + All_Sub_Menu_list.size());
        }


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    private void Set_ActionBar_Title(String title) {
        getActivity().setTitle(title);
    }

    private void Fragement() {
        bundle.putString("level", Level);
        bundle.putString("entryid", Entry_ID);
        bundle.putString("Name", Name_Item);
        Product_list_Fragment fragobj = new Product_list_Fragment();
        fragobj.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.show_fragment, fragobj).addToBackStack(null);
        ft.commit();

    }

    private void Fragement_Direct_ProductList() {
        bundle.putString("level", Level);
        bundle.putString("entryid", mParam2);
        bundle.putString("Name", mParam3);
        Product_list_Fragment fragobj = new Product_list_Fragment();
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
            final ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater)
                    getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manu, parent, false);


                holder.TextView_Menu = (TextView) convertView.findViewById(R.id.TextView_Menu);
                holder.layout_menu_list = (LinearLayout) convertView.findViewById(R.id.layout_menu_list);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.TextView_Menu.setText(Sub_Menu_list.get(position).toString());
            holder.layout_menu_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("ON CLICK", "layout_menu_list : " + position);
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

    private class ViewHolder {
        LinearLayout layout_menu_list;
        private TextView TextView_Menu;

    }
}
