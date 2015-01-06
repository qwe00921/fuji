package com.fsti.fjdicClient.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {
    private static Toast    mToast;
    private static Handler  mhandler = new Handler(Looper.getMainLooper());
    private static Runnable r        = new Runnable() {
                                         public void run() {
                                             if (null != mToast) {
                                                 mToast.cancel();
                                             }
                                         };
                                     };

    public static void showToast(Context context, String text) {
        mhandler.removeCallbacks(r);
        if (text != null && !TextUtils.isEmpty(text.trim()) && !"null".equals(text)) {
            if (null != mToast) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
        mhandler.postDelayed(r, 5000);
    }

    public static void showLongToast(Context context, String text) {
        mhandler.removeCallbacks(r);
        if (text != null && !TextUtils.isEmpty(text.trim()) && !"null".equals(text)) {
            if (null != mToast) {
                mToast.setText(text);
            } else {
                mToast = Toast.makeText(context, text, 3000);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        }
        mhandler.postDelayed(r, 5000);
    }

    public static void showToast(Context context, int strId) {
        showToast(context, context.getString(strId));
    }

    public static void showFalseToast(Context context) {
        showToast(context, "暂无网络信号");
    }
}
