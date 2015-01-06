package com.fsti.fjdicClient.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 消息处理工具类
 * @author 金涛
 *
 */
public class HandlerUtil {
	/**
	 * 发送消息内容
	 * 
	 * @param h
	 *            消息处理器
	 * @param msgIndex
	 *            消息序号
	 * @param data 消息数据
	*/
	public static void sentMessage(Handler h, int msgIndex, Object data) {
		//System.out.println("*********************************"+percent);
		Message msg = h.obtainMessage();
		msg.what = msgIndex;
		msg.obj = data;
		//msg.setData(bundle);
		msg.sendToTarget();
	}
}
