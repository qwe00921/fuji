package com.fsti.fjdicClient.adapter;


import com.fsti.fjdicClient.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	private Context context;
	private int[] resIds = new int[]{ 
			R.drawable.icon,
			R.drawable.icon,
			R.drawable.icon,
			R.drawable.icon
			};
	private static final int ITEM_WIDTH = 95;
    private static final int ITEM_HEIGHT = 100;
    
	public ImageAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resIds.length;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
        if (convertView == null) {
            convertView = new ImageView(context);

            imageView = (ImageView) convertView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new Gallery.LayoutParams(
            		 (ITEM_WIDTH ), (ITEM_HEIGHT)));
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(resIds[position]);

        return imageView;
	}

}
