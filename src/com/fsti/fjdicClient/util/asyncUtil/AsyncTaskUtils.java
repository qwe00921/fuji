package com.fsti.fjdicClient.util.asyncUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/** 
* �첽���������� 
* @ClassName: ActivityUtils 
*/  
public class AsyncTaskUtils {  
 
   /** 
    * ��װ��asynctask�������˷���û�н�ȿ�. 
    *  
    * @param callEarliest 
    *            ���������̣߳�����ִ�д˷���. 
    * @param mCallable 
    *            �������첽�߳�,�ڶ�ִ�д˷���. 
    * @param mCallback 
    *            ���������߳�,���ִ�д˷���. 
    */  
   public static <T> void doAsync(final CallEarliest<Object> callEarliest,  
           final Callable<Object> callable, final Callback<String> callback) {  
 
       new AsyncTask<Void, Void, T>() {  
 
           /** 
            * �������д˷���,���������߳� 
            */  
           @Override  
           protected void onPreExecute() {  
               super.onPreExecute();  
               try {  
                   callEarliest.onCallEarliest();  
               } catch (Exception e) {  
                   Log.e("error", e.toString());  
               }  
           }  
 
           /** 
            * �ڶ���ִ�������������������������첽�߳��� 
            */  
           @Override  
           protected T doInBackground(Void... params) {  
 
               try {  
                   return (T) callable.call();  
               } catch (Exception e) {  
                   Log.e("error", e.toString());  
               }  
               return null;  
           }  
 
           /** 
            * ����ִ��������������������߳� 
            */  
           protected void onPostExecute(T result) {  
//               callback.onCallback((String) result);
               callback.onCallback(String.valueOf(result));
        	   
           }  
       }.execute((Void[]) null);  
   }  
 
   /** 
    * ��װ��asynctask�������˷���ӵ�н�ȶԻ��򣬲�֧�ֶ�����ʽ. 
    *  
    * @param pContext 
    *            ������ 
    * @param styleID 
    *            �Ի�����ʽ 
    *            ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER 
    * @param pTitle 
    *            ���� 
    * @param pMessage 
    *            ���� 
    * @param pCallEarliest 
    *            ���������̣߳�����ִ�д˷���. 
    * @param progressCallable 
    *            �������첽�߳�,���ڴ��ݶԻ�����. 
    * @param pCallback 
    *            ���������߳�,���ִ�д˷���. 
    */  
   public static <T> void doProgressAsync(final Context pContext,  
           final int styleID, final String pTitle, final String pMessage,  
           final CallEarliest<T> pCallEarliest,  
           final ProgressCallable<T> progressCallable,  
           final Callback<T> pCallback) {  
 
       new AsyncTask<Void, Void, T>() {  
 
           private ProgressDialog mProgressDialog;  
 
           /** 
            * �������д˷���,���������߳� 
            */  
           @Override  
           protected void onPreExecute() {  
               super.onPreExecute();  
 
               mProgressDialog = new ProgressDialog(pContext);  
               mProgressDialog.setProgressStyle(styleID);  
               mProgressDialog.setTitle(pTitle);  
               mProgressDialog.setMessage(pMessage);  
                mProgressDialog.setIndeterminate(false);  
                mProgressDialog.show();  
                try {  
                    pCallEarliest.onCallEarliest();  
                } catch (Exception e) {  
                    Log.e("error", e.toString());  
                }  
            }  
  
            /** 
             * �ڶ���ִ�������������������������첽�߳��� 
             */  
            @Override  
            protected T doInBackground(Void... params) {  
                try {  
                    return progressCallable.call(new IProgressListener() {  
  
                        @Override  
                        public void onProgressChanged(int pProgress) {  
                            // TODO Auto-generated method stub  
                            onProgressUpdate(pProgress);  
                        }  
                    });  
                } catch (Exception e) {  
                    Log.e("error", e.toString());  
                }  
  
                return null;  
            }  
  
            /** 
             * ���½�ȿ� 
             */  
            protected void onProgressUpdate(final Integer... values) {  
                mProgressDialog.setProgress(values[0]);  
            };  
  
            /** 
             * ����ִ��������������������߳� 
             */  
            protected void onPostExecute(T result) {  
                if (mProgressDialog != null)  
                    mProgressDialog.dismiss();  
                pCallback.onCallback(result);  
  
            }  
  
        }.execute((Void[]) null);  
  
    }  
  
    /** 
     * ��װ��asynctask�������˷���ӵ�н�ȶԻ��򣬲�֧�ֶ�����ʽ. 
     *  
     * @param pContext 
     *            ������ 
     * @param styleID 
     *            �Ի�����ʽ 
     *            ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER 
     * @param pTitle 
     *            ����,��Դid 
     * @param pMessage 
     *            ����,��Դid 
     * @param pCallEarliest 
     *            ���������̣߳�����ִ�д˷���. 
     * @param progressCallable 
     *            �������첽�߳�,���ڴ��ݶԻ�����. 
     * @param pCallback 
     *            ���������߳�,���ִ�д˷���. 
     */  
    public static <T> void doProgressAsync(final Context pContext,  
            final int styleID, final int pTitleResId, final int pMessageResId,  
            final CallEarliest<T> pCallEarliest,  
            final ProgressCallable<T> progressCallable,  
            final Callback<T> pCallback) {  
        AsyncTaskUtils.doProgressAsync(pContext, styleID,  
                pContext.getString(pTitleResId),  
                pContext.getString(pMessageResId), pCallEarliest,  
                progressCallable, pCallback);  
    }  
  
}  