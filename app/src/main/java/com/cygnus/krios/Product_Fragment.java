package com.cygnus.krios;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Product_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String[] TEXTS = {"Image #1", "Image #2", "Image #3", "Image #4"};
    private static final int[] IMAGES = {R.drawable.banner2_4, R.drawable.banner6_7,
            R.drawable.banner5_4, R.drawable.free_shipping};
    public int i = 0;
    LinearLayout linear_layout_Technical_Details, linear_layout_Product_Description, linear_layout_Ratings_Reviews, linear_layout_Tags, linear_tvreview_product,
            linear_image_Perview, linear_image_Next;
    Button btnTechnical_Details, btnProduct_Description, btnRatings_Reviews, btnTags;
    EditText etQty;
    ImageView imageView_product, imageView_preview, imageView_next/*, imageView_minus, imageView_plus*/;
    int value = 0;
    Button btn_plus, btn_minus;
    private int mPosition = 0;

    private OnFragmentInteractionListener mListener;

    public Product_Fragment() {
        // Required empty public constructor
    }


    public static Product_Fragment newInstance(String param1, String param2) {
        Product_Fragment fragment = new Product_Fragment();
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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        findid(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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

    void AddFrag(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameHomeFreg, fragment).commit();
    }

    private void findid(View view) {
        linear_layout_Technical_Details = (LinearLayout) view.findViewById(R.id.linear_layout_Technical_Details);
        linear_layout_Product_Description = (LinearLayout) view.findViewById(R.id.linear_layout_Product_Description);
        linear_layout_Ratings_Reviews = (LinearLayout) view.findViewById(R.id.linear_layout_Ratings_Reviews);
        linear_layout_Tags = (LinearLayout) view.findViewById(R.id.linear_layout_Tags);
        linear_tvreview_product = (LinearLayout) view.findViewById(R.id.linear_tvreview_product);
        btnTechnical_Details = (Button) view.findViewById(R.id.btnTechnical_Details);
        btnProduct_Description = (Button) view.findViewById(R.id.btnProduct_Description);
        btnRatings_Reviews = (Button) view.findViewById(R.id.btnRatings_Reviews);
        btnTags = (Button) view.findViewById(R.id.btnTags);
//        imageView_minus = (ImageView) view.findViewById(R.id.imageView_minus);
//        imageView_plus = (ImageView) view.findViewById(R.id.imageView_plus);
        imageView_product = (ImageView) view.findViewById(R.id.imageView_product);
        imageView_preview = (ImageView) view.findViewById(R.id.imageView_preview);
        imageView_next = (ImageView) view.findViewById(R.id.imageView_next);
        etQty = (EditText) view.findViewById(R.id.etQty);

        btn_minus = (Button) view.findViewById(R.id.btn_minus);
        btn_plus = (Button) view.findViewById(R.id.btn_add);

    }

    private void Layout_Hide_Show(String str) {

        if (str == "1") {
            btnTechnical_Details.setBackgroundResource(R.color.orange);
            btnTechnical_Details.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Technical_Details.setVisibility(View.VISIBLE);

            btnProduct_Description.setBackgroundResource(R.color.white);
            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white);
            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);

        } else if (str == "2") {
            btnTechnical_Details.setBackgroundResource(R.color.white);
            btnTechnical_Details.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Technical_Details.setVisibility(View.GONE);

            btnProduct_Description.setBackgroundResource(R.color.orange);
            btnProduct_Description.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Product_Description.setVisibility(View.VISIBLE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white);
            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);

        } else if (str == "3") {

            btnTechnical_Details.setBackgroundResource(R.color.white);
            btnTechnical_Details.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Technical_Details.setVisibility(View.GONE);

            btnProduct_Description.setBackgroundResource(R.color.white);
            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.orange);
            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Ratings_Reviews.setVisibility(View.VISIBLE);

            btnTags.setBackgroundResource(R.color.white);
            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);
        } else if (str == "4") {
            btnTechnical_Details.setBackgroundResource(R.color.white);
            btnTechnical_Details.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Technical_Details.setVisibility(View.GONE);


            btnProduct_Description.setBackgroundResource(R.color.white);
            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.orange);
            btnTags.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Tags.setVisibility(View.VISIBLE);


        } else {
            btnTechnical_Details.setBackgroundResource(R.color.orange);
            btnTechnical_Details.setTextColor(getResources().getColor(R.color.white));
            linear_layout_Technical_Details.setVisibility(View.VISIBLE);

            btnProduct_Description.setBackgroundResource(R.color.white);
            btnProduct_Description.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Product_Description.setVisibility(View.GONE);

            btnRatings_Reviews.setBackgroundResource(R.color.white);
            btnRatings_Reviews.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Ratings_Reviews.setVisibility(View.GONE);

            btnTags.setBackgroundResource(R.color.white);
            btnTags.setTextColor(getResources().getColor(R.color.orange));
            linear_layout_Tags.setVisibility(View.GONE);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
