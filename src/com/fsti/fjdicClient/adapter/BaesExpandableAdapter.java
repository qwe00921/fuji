package com.fsti.fjdicClient.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.view.FollowListView;

public class BaesExpandableAdapter extends BaseExpandableListAdapter {

    private List<String>              parentList;
    private Map<String, List<String>> childList;
    private Context                   context;

    public BaesExpandableAdapter(Context context, List<String> parentList, Map<String, List<String>> childList) {
        this.context = context;
        this.childList = childList;
        this.parentList = parentList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(parentList.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_expa_child, null);
        FollowListView gridchild = (FollowListView) convertView.findViewById(R.id.GridView_toolbar);
        final List<String> value = childList.get(parentList.get(groupPosition));
        ChildGridView gridview = new ChildGridView(context, value);
        gridchild.setAdapter(gridview);
        gridchild.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Toast.makeText(context, value.get(arg2), 1000).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // return childList.get(parentList.get(groupPosition)).size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.parentitem, null);
        TextView textview = (TextView) convertView.findViewById(R.id.text_parent);
        textview.setText(parentList.get(groupPosition));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ChildGridView extends BaseAdapter {
        private Context      context;
        private List<String> value;

        ChildGridView(Context context, List<String> value) {
            this.context = context;
            this.value = value;
        }

        @Override
        public int getCount() {
            return value.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expa_gra, null);
            TextView textview = (TextView) convertView.findViewById(R.id.textchild);
            textview.setText(value.get(position));
            return convertView;
        }

    }
}
