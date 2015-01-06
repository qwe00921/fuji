package com.vtion.health.pay;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;
import com.fsti.fjdicClient.R;

public class AlixPay {
	private Activity mActivity;
	private static final int RQF_PAY = 1;
	private AlixPayDialog payDialog;
	private String orderNum;
	private String price;

	public AlixPay(Activity activity) {
		this.mActivity = activity;
		payDialog = new AlixPayDialog(activity);
	}

	public void pay(String subject, String orderNum, String price,
			String notify_url) {
		try {
			String urlString = "dd" + notify_url;
			this.orderNum = orderNum;
			this.price = price;
			String info = getNewOrderInfo(subject, orderNum, price, urlString);
			String sign = Rsa.sign(info, Keys.PRIVATE);
			sign = URLEncoder.encode(sign);
			info += "&sign=\"" + sign + "\"&" + getSignType();
			// start the pay.
			// Tools.printf("info = " + info);

			final String orderInfo = info;
			new Thread() {
				public void run() {
					AliPay alipay = new AliPay(mActivity, mHandler);

					// 设置为沙箱模式，不设置默认为线上环境
					// alipay.setSandBox(true);

					String result = alipay.pay(orderInfo);

					// Tools.printf("result = " + result);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			Toast.makeText(mActivity, R.string.remote_call_failed,
					Toast.LENGTH_SHORT).show();
		}
	}

	private String getNewOrderInfo(String subject, String orderNum,
			String price, String notify_url) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);
		sb.append("\"&out_trade_no=\"");
		// sb.append(getOutTradeNo());
		sb.append(orderNum);
		sb.append("\"&subject=\"");
		sb.append(subject);
		sb.append("\"&body=\"");
		sb.append("糖主任产品");
		sb.append("\"&total_fee=\"");
		sb.append(price);
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		// sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
		sb.append(URLEncoder.encode(notify_url));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		// sb.append("\"&it_b_pay=\"30m");
		sb.append("\"");

		return new String(sb);
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		// Tools.printf("outTradeNo: " + key);
		return key;
	}

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY: {
				result.parseResult();
				// Tools.printf("result.resultStatus==" + result.resultStatus);
				if (result.isSignOk) {
					// Tools.printf(result.result);
					if (result.rs.equals("9000")) {
						// AppConfig.finalHttp.post(AppConfig.NetworkUrl,
						// AppMethods.getInstance().AlipayPaySuccess(mActivity,
						// AppFile.getVersioncode(mActivity), orderNum, price,
						// AppFile.desPassword(mActivity)),
						// new AjaxCallBack<Object>() {
						//
						// @Override
						// public void onFailure(Throwable t, int errorNo,
						// String strMsg) {
						// // TODO Auto-generated method stub
						// super.onFailure(t, errorNo, strMsg);
						// Tools.printf("pay_onFailure" + t.toString());
						// }
						//
						// @Override
						// public void onSuccess(Object t) {
						// // TODO Auto-generated method stub
						// super.onSuccess(t);
						// Tools.printf("pay_onSuccess" + t.toString());
						// }
						//
						// });
						// // 去支付成功页面
						// Intent intent = new Intent(mActivity,
						// UiPaySuccess.class);
						// intent.putExtra("orderNum", orderNum);
						// intent.putExtra("payType", "支付宝");
						// intent.putExtra("price", price);
						// intent.putExtra("orderState", "支付成功");
						// mActivity.startActivity(intent);
						// if (mActivity instanceof UiShoppingList) {
						// mActivity.finish();
						// }

					} else {
						if (!payDialog.isShowing()) {
							// payDialog.setTvMessage(mActivity.getResources().getString(R.string.pay_faliure));
							payDialog.setTvMessage(result.getResult());
							payDialog.show();
						}
					}

				} else {
					if (!payDialog.isShowing()) {
						// payDialog.setTvMessage(mActivity.getResources().getString(R.string.pay_faliure));
						payDialog.setTvMessage(result.getResult());
						payDialog.show();
					}

				}

			}
				break;
			default:
				break;
			}
		};
	};

}
