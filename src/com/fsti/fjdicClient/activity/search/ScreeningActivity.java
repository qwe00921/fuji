package com.fsti.fjdicClient.activity.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.activity.BaseActivity;
import com.fsti.fjdicClient.adapter.BaesExpandableAdapter;
import com.fsti.fjdicClient.bean.ExitApplication;

public class ScreeningActivity extends BaseActivity implements OnClickListener {
    private ExpandableListView        expand_ctv;
    private List<String>              parentList = new ArrayList<String>();
    private Map<String, List<String>> childList  = new HashMap<String, List<String>>();
    private List<String>              childItem  = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        ExitApplication.getInstance().addActivity(this);
        init();
    }

    @Override
    public void initValue() {
        System.out.println("initValue");
        expand_ctv = (ExpandableListView) findViewById(R.id.ep_listview);
        for (int i = 0; i < 5; i++) {
            String parent = "标题" + i;
            parentList.add(parent);
            childItem = new ArrayList<String>();
            for (int j = 0; j < (i + 2) * 2; j++) {
                childItem.add("数据" + i + j);
            }
            childList.put(parent, childItem);
        }
        BaesExpandableAdapter adapter = new BaesExpandableAdapter(this, parentList, childList);
        expand_ctv.setAdapter(adapter);

        // 设置点击背景色
        expand_ctv.setSelector(android.R.color.white);

    }

    @Override
    public void bindEvent() {
        findViewById(R.id.common_title_return).setOnClickListener(this);
        // 子类点击事件
        expand_ctv.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(myContext, childList.get(parentList.get(groupPosition)).get(childPosition), 1000).show();
                return true;
            }
        });
        // 父类点击事件
        expand_ctv.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (expand_ctv.isGroupExpanded(groupPosition)) {
                    expand_ctv.collapseGroup(groupPosition);
                } else {
                    expand_ctv.expandGroup(groupPosition);
                }

                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.common_title_return:

            break;

        default:
            break;
        }

    }

}
