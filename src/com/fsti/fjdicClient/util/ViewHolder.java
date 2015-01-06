package com.fsti.fjdicClient.util;

import android.util.SparseArray;
import android.view.View;

/**
 * 公共ViewHolder�?
 * 
 * @author lwq
 */
public class ViewHolder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    /**
     * 使用例子
     */
    // @Override
    // public View getView(int position, View convertView, ViewGroup parent) {
    //
    // if (convertView == null) {
    // convertView = LayoutInflater.from(context)
    // .inflate(R.layout.banana_phone, parent, false);
    // }
    //
    // ImageView bananaView = ViewHolder.get(convertView, R.id.banana);
    // TextView phoneView = ViewHolder.get(convertView, R.id.phone);
    //
    // BananaPhone bananaPhone = getItem(position);
    // phoneView.setText(bananaPhone.getPhone());
    // bananaView.setImageResource(bananaPhone.getBanana());
    //
    // return convertView;
    // }
}
