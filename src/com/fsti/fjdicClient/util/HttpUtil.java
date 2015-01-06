package com.fsti.fjdicClient.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class HttpUtil {

    private static int connectTimeOut = 600000; // 连接超时时间毫秒
    private static int readTimeOut    = 600000; // 数据读取超时时间毫秒

    // private static String encoding = "UTF-8"; //编码
    /**
     * 以post方式提交http请求
     * 
     * @param reqUrl
     *            请求地址
     * @param parameters
     *            参数
     * @return 返回结果
     * @throws Exception
     *             异常
     */
    public static String postData(String reqUrl, Map<String, Object> parameters, String encoding) throws Exception {
        HttpURLConnection url_con = null;
        String responseContent = null;
        StringBuffer params = new StringBuffer();
        String queryUrl = reqUrl;
        System.out.println("httputil config Url=" + reqUrl);
        try {
            if (null != parameters && parameters.size() > 0) {
                for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
                    Entry<?, ?> element = (Entry<?, ?>) iter.next();
                    params.append(element.getKey().toString());
                    params.append("=");
                    // params.append(URLEncoder.encode(element.getValue().toString(),
                    // encoding));
                    params.append(element.getValue().toString());
                    params.append("&");
                }
                if (params.charAt(params.length() - 1) == '&') {
                    params = params.deleteCharAt(params.length() - 1);
                }
            }
            System.out.println("httpUtil params---------->" + params);
            URL url = new URL(queryUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            // url_con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            url_con.setConnectTimeout(connectTimeOut);
            url_con.setReadTimeout(readTimeOut);
            url_con.setDoOutput(true);

            byte[] b = params.toString().getBytes(encoding);

            System.out.println("post if success");
            if (null != parameters && parameters.size() > 0) {
                url_con.getOutputStream().write(b, 0, b.length);
                url_con.getOutputStream().flush();
                url_con.getOutputStream().close();
            }

            System.out.println("post over");

            int totalBytes = url_con.getContentLength();
            int responseCode = url_con.getResponseCode();
            System.out.println("连接代码_+_+_+_+" + responseCode);
            InputStream in = url_con.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            // responseContent = temp.toString();
            // System.out.println("code temp.toString()=" + temp.toString());
            // String dd = "\u6d4b\u8bd5\u7528\u6237";
            // System.out.println("code aa=" + dd);
            // // responseContent=new String(temp.toString().getBytes("UTF-8"));
            // responseContent = new String(temp.toString().getBytes("UTF-8"), "gb2312");
            // //responseContent =URLDecoder.decode(temp.toString(),"UTF-8");

            responseContent = new String(temp.toString().getBytes(), encoding);
            if (responseContent != null && responseContent.startsWith("\ufeff")) {
                responseContent = responseContent.substring(1);
            }
            System.out.println("code responseContent=" + responseContent);
            // responseContent = temp.toString();
            rd.close();
            in.close();
            b = null;
            System.out.println("complete post");
        } catch (Exception ex) {
            System.out.println("httputil exception postData1111111111111111");
            ex.printStackTrace();
            // 抛出异常有调用者处理
            throw new Exception(ex);
        } catch (OutOfMemoryError error) {
            System.out.println("内存溢出啦");

        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    /**
     * 判断当前网络状态
     * 
     * @param myContext
     *            上下文
     * @param myActivity
     *            当前页面
     * @return false 无网络，true 有可用网络
     */
    public static boolean checkNetWorkStatus(final Context myContext) {
        boolean netSataus = false;
        ConnectivityManager conMan = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
            netSataus = true;
        }
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        // wifi连接
        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            netSataus = true;
        }
        return netSataus;
    }

    /**
     * 判断当前网络状态，未连接网络则跳至网络设置页面
     * 
     * @param myContext
     *            上下文
     * @param myActivity
     *            当前页面
     * @return false 无网络，true 有可用网络
     */
    public static boolean NetWorkStatus(final Context myContext, Activity myActivity) {
        boolean netSataus = false;
        ConnectivityManager conMan = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
            netSataus = true;
        }
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        // wifi连接
        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            netSataus = true;
        }
        if (!netSataus) {
            Builder b = new AlertDialog.Builder(myActivity).setTitle("没有可用的网络").setMessage("是否对网络进行设置？");
            b.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent mIntent = new Intent("/");
                    ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    mIntent.setComponent(comp);
                    mIntent.setAction("android.intent.action.VIEW");
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myContext.startActivity(mIntent);
                }
            }).setNeutralButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        }
        return netSataus;
    }

    /**
     * 编码Url地址(如:http://ip/service/中文/中文.jpg，编码以"/"分组的字符串，不编码"/"字符)
     * 
     * @param urlStr
     *            请求地址
     * @return 编码后的地址
     */
    public static String EncoderURL(String urlStr) {
        String url = "";
        try {
            url = urlStr.trim();
            String[] replaceArray = url.split("/");
            String[] replacedBefore = new String[replaceArray.length - 3];
            String[] replacedAfter = new String[replaceArray.length - 3];
            String replacedBeforeStr = "";
            String replacedAfterStr = "";
            for (int i = 3; i < replaceArray.length; i++) {
                replacedBefore[i - 3] = replaceArray[i];
                System.out.println("replaceArray[i]=" + replaceArray[i]);
                replacedBeforeStr += "/" + replacedBefore[i - 3];
                System.out.println("replacedBeforeStr=" + replacedBeforeStr);
                replacedAfter[i - 3] = URLEncoder.encode(replacedBefore[i - 3], "utf-8");
                System.out.println("replacedAfter[i-3]=" + replacedAfter[i - 3]);
                replacedAfterStr += "/" + replacedAfter[i - 3];
                System.out.println("replacedAfterStr=" + replacedAfterStr);
            }

            if (url.indexOf("%") < 0) {
                url = url.replaceAll(replacedBeforeStr, replacedAfterStr);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            // 异常返回原地址
            return urlStr;
        }

        return url;
    }

}