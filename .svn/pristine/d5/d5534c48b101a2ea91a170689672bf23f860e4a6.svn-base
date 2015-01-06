package com.fsti.fjdicClient.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

public class VersionUpgradeUtil {

    static VersionUpgradeUtil versionUpgradeUtil;
	private static String PREF_DB_ServerApkVersion="lastServerApkVersion";
	private static String DOWNLOAD_APK_URL = "http://220.181.187.155/portal/app/download/71559";
	private static int serverApkVersion = 0;
	private  boolean autoVersionUpgrade=false;
	/**
	 *  版本检测成功
	 */
    private  final int VER_ChECK_SUCESS=1;
    /**
	 * 网络连接失败
	 */
    private  final int VER_NET_CONN_FAIL=2;
    /**
     * 版本检测异常失败
     */
    private  final int VER_CHECK_FAIL=3;
    /**
     * 当前已经是最新版本
     */
    private  final int VER_CURR_LAST=4;
    
    private static Context mContext;
    
    public static final VersionUpgradeUtil getInstance(Context context) {
		if (versionUpgradeUtil == null)
			versionUpgradeUtil = new VersionUpgradeUtil();
		mContext =context;
		return versionUpgradeUtil;
	}
	
	private  boolean isNetworkAvailable(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

	private  String getApkPath() {
		String apkPath = Environment.getExternalStorageDirectory()
				+ "/telecom.apk";
		return apkPath;

	}

	
	private  void updateApkVersion(final Context context , int status) {
		switch (status) {
		case VER_ChECK_SUCESS:
			
			String tipInfo = "有新的版本需要升级吗？";
			tipInfo = String.format(tipInfo, getVersionName(context),
					serverApkVersion);
			new AlertDialog.Builder(context)
					.setTitle("版本更新提示")
					.setMessage(tipInfo)
					.setPositiveButton("版本升级",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
							
									final ProgressDialog progressDialog = ProgressDialog
											.show(context, null, "正在下载中...", true);

									new Thread() {
										public void run() {
											
											int downloadStatus = downloadApk(context);
											
											progressDialog.dismiss();
										
											Looper.prepare();
											installApk(context, downloadStatus);
											Looper.loop();
										}
									}.start();
								}
							})
					.setNeutralButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									saveCheckLastVersion(context);
									dialog.dismiss();
								}
							}).create().show();
			break;
		case VER_NET_CONN_FAIL:
              CreateDialog(context, "网络连接失败");	
			break;
		case VER_CHECK_FAIL:
			CreateDialog(context, "版本检测时发生异常");
			break;
		case VER_CURR_LAST:
			CreateDialog(context, "提示当前已是最新版本");

			break;
		}
	}
	
	
	public void  checkNewVersion()
	{
		  int result = hasNewVersion(mContext); 
	      Looper.prepare();
	      updateApkVersion(mContext, result);   
	      Looper.loop();
	}
	
    private  void CreateDialog(Context context,String message) {   
    	 if(!autoVersionUpgrade)
         {
	        AlertDialog.Builder builder = new Builder(context);   
	        builder.setTitle("提示");   
	        builder.setMessage(message).setNeutralButton("取消",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					
					dialog.dismiss();
				}
			}).create().show();
         }
    	autoVersionUpgrade=true;
    }   

	private  void saveCheckLastVersion(Context ctx)
	{
		SharedPreferences settings = ctx.getSharedPreferences(VersionUpgradeUtil.PREF_DB_ServerApkVersion, 0);     
		SharedPreferences.Editor editor = settings.edit();     
		editor.putInt("PREF_DB_ServerApkVersion", serverApkVersion);     
		editor.commit(); 
	}
	
	private  int hasNewVersion(Context context) {
		int result = VER_ChECK_SUCESS;
		if (isNetworkAvailable(context)) {
			PackageInfo packageInfo = getPackageInfo(context);
			int thisVersion =packageInfo.versionCode;
			String remoteInfo = "1";//JsonUtil.getVersionCode(JsonUtil.getServiceData(CHECK_VERSION_URL, JsonUtil.getParamss(packageInfo)));

			if (null != remoteInfo && !"".equals(remoteInfo.trim())) {
				int iremoteInfo = Integer.parseInt(remoteInfo);
				if (thisVersion > iremoteInfo || iremoteInfo == thisVersion) {
					result = VER_CURR_LAST;//提示当前已是最新版本
				} else {
					serverApkVersion = iremoteInfo;
					SharedPreferences preferences = context.getSharedPreferences(VersionUpgradeUtil.PREF_DB_ServerApkVersion, Activity.MODE_PRIVATE);
					int lastServerApkVersion = preferences.getInt("PREF_DB_ServerApkVersion", 0);
					if(lastServerApkVersion>iremoteInfo||lastServerApkVersion==iremoteInfo)
					{
						result=0;
					}
					
				}
			} else {
				result = VER_CHECK_FAIL;//版本检测时发生异常
			}
		} else {
			result = VER_NET_CONN_FAIL;//网络连接失败
		}
		return result;
	}


	private  void installApk(Context ctx, int downloadStatus) {
		switch (downloadStatus) {
		case 1:
			installApk(ctx, getApkPath());
			break;
		case 2:
			Toast.makeText(ctx, "提示SDCard设备不存在或不可写入", Toast.LENGTH_LONG);
			break;
		case 3:
			Toast.makeText(ctx, "提示版本更新失败", Toast.LENGTH_LONG);
			break;
		}
	}

	
	private  int downloadApk(Context ctx) {
		int result = 1;
		if (isSDCardExistAndReadable()) {
			boolean flag = downloadApk(DOWNLOAD_APK_URL, getApkPath());
			if (!flag) {
				result = 3;
			}
		} else {
			result = 2;
		}
		return result;
	}


	private  boolean isSDCardExistAndReadable() {
		String sdcardState = Environment.getExternalStorageState();
		if (sdcardState.equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	
	private  boolean downloadApk(String pageUrl, String apkName) {
		boolean result = false;
		File file = null;
		OutputStream output = null;
		try {
			URL url = new URL(pageUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);
			urlConn.connect();
			InputStream inputStream = urlConn.getInputStream();
			file = new File(apkName);
			if (!file.exists())
				file.createNewFile();
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			output.flush();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			try {
				if (null != output)
					output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private  void installApk(Context ctx, String apkPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkPath),"application/vnd.android.package-archive");
		ctx.startActivity(intent);
	}

	private  PackageInfo getPackageInfo(Context ctx)
	{
		PackageInfo packInfo=null;
		PackageManager packageManager = ctx.getPackageManager();
		try {
			 packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return packInfo;
	}

	private  int getVersionName(Context ctx) {
		int version = 0;
		PackageManager packageManager = ctx.getPackageManager();
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					ctx.getPackageName(), 0);
			version = packInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();

		}

		return version;
	}

	
}
