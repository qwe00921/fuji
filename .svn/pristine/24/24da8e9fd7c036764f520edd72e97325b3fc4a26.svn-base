/**
 * 
 */
package com.fsti.fjdicClient.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fsti.fjdicClient.R;
import com.fsti.fjdicClient.bean.GroupBuyingGoodsCategoryEntity;
import com.loopj.android.image.SmartImageView;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author wls
 *
 */
public class HomeMainActivityGVMenuAdapter extends BaseAdapter {


	private Context context;
//	private List<Integer> idlist = new ArrayList<Integer>();
	List<GroupBuyingGoodsCategoryEntity> strtitle;
	//private List<String> strtitle = new ArrayList<String>();
//    private List<String> imgpath = new ArrayList<String>();
    //备用
//    private List<Integer> imglocalresid = new ArrayList<Integer>();
	public HomeMainActivityGVMenuAdapter(Context context,
			List<GroupBuyingGoodsCategoryEntity> strtitle) {
		this.context = context;
		this.strtitle = strtitle;
//		this.imglocalresid = imglocalresid;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strtitle.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg1 == null){
		View view = View.inflate(context, R.layout.homemaingvitem, null);
		SmartImageView imageview = (SmartImageView) view
				.findViewById(R.id.iv);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		if(strtitle.get(arg0).getCategoryImage()==null || "".equals(strtitle.get(arg0).getCategoryImage().trim())){
			if(arg0 == strtitle.size()-1){
				imageview.setImageResource(R.drawable.ic_gengduofenlei);
			}else{
				imageview.setImageResource(R.drawable.ic_launcher);
			}
		}else{
			imageview.setImageUrl(strtitle.get(arg0).getCategoryImage().trim());
		}
		tv.setText(strtitle.get(arg0).getName());
		return view;
		}
		return arg1;
	}


}
