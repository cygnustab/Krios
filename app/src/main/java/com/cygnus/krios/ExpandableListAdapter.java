package com.cygnus.krios;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mitesh on 6/22/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    ExpandableListView expandList;
    private Context mContext;
    private List<JsonResult> mListDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<JsonResult, List<String>> mListDataChild;

    public ExpandableListAdapter(Context context, List<JsonResult> listDataHeader, HashMap<JsonResult, List<String>> listChildData, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
    }


    @Override
    public int getGroupCount() {
        int i = mListDataHeader.size();
//        Log.d("GROUPCOUNT", String.valueOf(i));
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
//        if (groupPosition != 4) {
        childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .size();
//        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Log.d("CHILD", mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition).toString());
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        JsonResult headerTitle = (JsonResult) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.img_pluse);
        if (isExpanded) {
            icon.setImageResource(R.mipmap.minus);
        } else {
            icon.setImageResource(R.mipmap.add);
        }

        if (groupPosition == 0) {
            icon.setVisibility(View.GONE);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.submenu);
//        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getIconName());
//        headerIcon.setImageResource(headerTitle.getIconImg());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);
        LinearLayout submenu_layout = (LinearLayout) convertView
                .findViewById(R.id.submenu_layout);

//        if (isLastChild) {
//
//            txtListChild.setBackgroundResource(R.color.orange);
//        } else {
//            txtListChild.setBackgroundResource(R.color.grey);
//
//        }

        txtListChild.setText(childText);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        Log.i("isChildSelectable", groupPosition + " : " + childPosition);

        return true;
    }
}
