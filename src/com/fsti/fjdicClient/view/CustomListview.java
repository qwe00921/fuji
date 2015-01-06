package com.fsti.fjdicClient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/************************************************************
 * 内容摘要 �?p> 作�? ：sq 创建时间 �?013-6-24 下午5:31:56 当前版本号：v1.0 历史记录 : 日期 : 2013-6-24 下午5:31:56 修改人： 描述 :
 ************************************************************/
public class CustomListview extends ListView {

    public CustomListview(Context context) {
        super(context);
    }

    public CustomListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
