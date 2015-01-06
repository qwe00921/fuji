package com.fsti.fjdicClient.util.uppay ;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import com.unionpay.UPPayAssistEx;

public class Uppay extends Activity implements Callback {
    private static final String LOG_TAG = "PayDemo";
    private Context mContext = null;
    private Activity mactivity;
    private static final int PLUGIN_NOT_INSTALLED = -1;
    private static final int PLUGIN_NEED_UPGRADE = 2;

    /*****************************************************************
     * mMode参数解释：
     *      "00" - 启动银联正式环境
     *      "01" - 连接银联测试环境
     *****************************************************************/
//    private String mMode = "01";
    private String mMode = "00";
  
    public void pay(String TN,Context Context,Activity activity) {
        mContext = Context;
        mactivity=activity;
     
            /************************************************* 
             * 
             *  步骤2：通过银联工具类启动支付插件 
             *  
             ************************************************/
            int ret = UPPayAssistEx.startPay(mactivity, null, null, TN, mMode);
            if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
                // 需要重新安装控件
                Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("提示");
                builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();
                               
                            }
                        });

                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();
                                UPPayAssistEx.installUPPayPlugin(mContext);
                            }
                        });
                builder.create().show();

            }
            Log.e(LOG_TAG, "" + ret);
        
      
    }

    
   
    int startpay(Activity act, String tn, int serverIdentifier) {
        return 0;
    }



	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
