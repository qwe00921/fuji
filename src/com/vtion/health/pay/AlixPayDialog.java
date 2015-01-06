package com.vtion.health.pay;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.fsti.fjdicClient.R;

/**
 * ********************************************************** 内容摘要 ：
 * <p>
 * 支付结果对话框 作者 ：zcy 创建时间 ：2014-7-15 上午10:15:49 当前版本号：v1.0 历史记录 : 日期 : 2014-7-15 上午10:15:49 修改人： 描述 :
 */
public class AlixPayDialog implements OnClickListener {
    private Dialog  mDialog;
    private Context mContext;
    private TextView tvTitle, tvMessage;
    private Button   btnAction;

    public AlixPayDialog(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        mDialog = new Dialog(mContext, R.style.dialogstyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_alixpay, null);
        tvTitle = (TextView) view.findViewById(R.id.tv_others);
        tvMessage = (TextView) view.findViewById(R.id.tv_content);
        btnAction = (Button) view.findViewById(R.id.btn_ok);
        // dialog外背景色
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.7f;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        mDialog.setContentView(view);

        btnAction.setOnClickListener(this);
    }

    public void show() {
        mDialog.show();
    }

    public void showCancelable() {
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void isVisible() {
        tvMessage.setVisibility(View.VISIBLE);
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public void setTvMessage(String message) {
        this.tvMessage.setText(message);
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle.setText(tvTitle);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
        case R.id.btn_ok:

            dismiss();

            break;

        default:
            break;
        }
    }

}
