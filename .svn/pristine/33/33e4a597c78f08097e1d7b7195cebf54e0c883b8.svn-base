package com.fsti.fjdicClient.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Map;

import com.fsti.fjdicClient.bean.FormFile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 文件处理工具类
 * @author 金涛
 *
 */
public class FileUtil {
	private String SDPATH = Environment.getExternalStorageDirectory() + "/";

	private static int FILESIZE = 4 * 1024;

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {
		System.out.println("createSDFile=" + (SDPATH + fileName));
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public File createDir(String path) {
		System.out.println("http 5");
		System.out.println("path=" + path);
		File file = new File(SDPATH + path);
		// 寻找父目录是否存在
		File parent = new File(file.getAbsolutePath().substring(0,
				file.getAbsolutePath().lastIndexOf(File.separator)));
		// 如果父目录不存在，则递归寻找更上一层目录
		if (!parent.exists()) {
			createDir(parent.getPath());
			// 创建父目录
			parent.mkdirs();
			System.out.println("http 6");
		} else {
			// 判断自己是否存在
			File self = new File(SDPATH + path);
			if (!self.exists())
				self.mkdirs();
			System.out.println("http 7");
		}

		return file;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 下载文件到sd卡目录
	 * 
	 * @param urlStr
	 *            下载文件地址
	 * @param path
	 *            存放文件目录
	 * @param fileName
	 *            存放文件名
	 * @param myHandler
	 *            消息处理器
	 * @return
	 */
	public static int downloadFile(String urlStr, String path, String fileName,
			Handler myHandler) {

		HttpURLConnection httpConnection = null;
		InputStream inputStream = null;
		URL url = null;

		System.out.println("file down url=" + urlStr);
		try {
			url = new URL(urlStr);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent",
					"java-download-core");// 设置头,也可以不做设置
			httpConnection.setRequestProperty("Accept-Language",
					"en-us,en;q=0.7,zh-cn;q=0.3");
			httpConnection.setRequestProperty("Accept-Encoding", "aa");
			httpConnection.setRequestProperty("Accept-Charset",
					"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
			httpConnection.setRequestProperty("Keep-Alive", "300");
			httpConnection.setRequestProperty("Connection", "keep-alive");
			httpConnection.setRequestProperty("Cache-Control", "max-age=0");

			inputStream = httpConnection.getInputStream();

			File resultFile = null;
			resultFile = write2SDFromInput(path, fileName, inputStream,
					myHandler);

			if (resultFile == null) {
				myHandler
						.sendEmptyMessage(GlobalVarUtil.HANDLER_FLAG_FILE_DOWNLOAD_FAILED);
				return -1;

			}
		} catch (Exception e) {
			e.printStackTrace();
			myHandler
					.sendEmptyMessage(GlobalVarUtil.HANDLER_FLAG_FILE_DOWNLOAD_FAILED);
			return -1;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				myHandler
						.sendEmptyMessage(GlobalVarUtil.HANDLER_FLAG_FILE_DOWNLOAD_FAILED);
				return -1;
			}
		}
		System.out.println("http 10");
		return 0;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	private static File write2SDFromInput(String path, String fileName,
			InputStream input, Handler myHandler) {
		File file = null;
		OutputStream output = null;
		try {
			// createDir(path);
			// 目录
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 文件
			file = new File(path + File.separator + fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			// file = createSDFile(path + File.separator + fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];
			int readLength = 0;
			Long totalLength = 0L;
			while (readLength != -1) {
				if ((readLength = input.read(buffer)) > 0) {
					output.write(buffer, 0, readLength);
					totalLength += readLength;
					System.out.println("totalLength=" + totalLength);
					HandlerUtil.sentMessage(myHandler, GlobalVarUtil.HANDLER_FLAG_FILE_DOWNLOAD_ING,totalLength);
					
//					Message msg = new Message();
//					msg.obj = totalLength;
//					msg.what = ConstantUtil.HANDLER_FLAG_FILE_DOWNLOAD_ING;
//					myHandler.sendMessage(msg);
				}
			}

			// 以下语句会导致生成文件不完整，原因不明
			// while((input.read(buffer)) != -1){
			// output.write(buffer);
			// }
			output.flush();
//			Message msg = myHandler.obtainMessage();
//			msg.what = ConstantUtil.HANDLER_FLAG_FILE_DOWNLOAD_SUCCESSED;
//			Bundle bundle = new Bundle();
//			bundle.putString("filePath", path + File.separator + fileName);
//			msg.setData(bundle);
//			msg.sendToTarget();
			HandlerUtil.sentMessage(myHandler, GlobalVarUtil.HANDLER_FLAG_FILE_DOWNLOAD_SUCCESSED, path + File.separator + fileName);

			// myHandler.sendEmptyMessage(DownLoadFileBean.DOWLOAD_FLAG_SUCCESS);
		} catch (Exception e) {
			myHandler
					.sendEmptyMessage(GlobalVarUtil.HANDLER_FLAG_FILE_DOWNLOAD_FAILED);
			e.printStackTrace();

		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 上传文件不带进度条
	 * 
	 * @param path
	 *            上传路径
	 * @param params
	 *            请求参数 key为参数名,value为参数值
	 * @param file
	 *            上传文件
	 */
	public static boolean uploadFile(String path, Map<String, String> params,
			FormFile[] files) throws Exception {
		System.out.println("SocketHttpRequester config getHttpRequest=" + path);
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志
		// Log.e("lmh", "-------LMHFile0-------"+files[0].getFile().length());
		int fileDataLength = 0;
		for (FormFile uploadFile : files) {// 得到文件类型数据的总长度
			Log.e("lmh", "-------LMHFile88-------"
					+ uploadFile.getFile().length());
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.getInStream() != null) {
				fileDataLength += uploadFile.getFile().length();
			} else {
				fileDataLength += uploadFile.getData().length;
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构造文本类型参数的实体数据
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;
		Log.e("lmh", "-------LMHdataLength-------" + dataLength);
		URL url = new URL(path);
		int port = url.getPort() == -1 ? 80 : url.getPort();
		System.out.println("port=" + port);
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		// 超时时间
		socket.setSoTimeout(300000);
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/png, image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来
		for (FormFile uploadFile : files) {
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileEntity.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile.getInStream() != null) {
				long fileLen = uploadFile.getFile().length();
				System.out.println(uploadFile.getFilname() + "=" + fileLen);
				byte[] buffer = new byte[1024];
				long currentLen = 0;
				int len = 0;
				while ((len = uploadFile.getInStream().read(buffer, 0, 1024)) != -1) {
					currentLen += len;
					System.out.println("currentLen=" + currentLen);
					outStream.write(buffer, 0, len);
				}
				uploadFile.getInStream().close();
			} else {
				System.out.println("inStream is null");
				outStream.write(uploadFile.getData(), 0,
						uploadFile.getData().length);
			}
			outStream.write("\r\n".getBytes());
			System.out.println("outStream=" + outStream);
		}
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		String returnStr = reader.readLine();

		System.out.println("reader.readLine()" + returnStr);
		if (returnStr.indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			System.out.println("socket false");
			return false;
		}
		outStream.flush();
		outStream.close();
		reader.close();
		socket.close();
		System.out.println("socket true");
		return true;
	}

	/**
	 * 
	 * 上传文件带进度条
	 * 
	 * @param path
	 *            上传路径
	 * @param params
	 *            请求参数 key为参数名,value为参数值
	 * @param file
	 *            上传文件
	 * @param myHandler
	 *            消息接收器
	 * @param sendMessageFlag
	 *            消息接收标志
	 * @param showText
	 *            消息显示内容
	 */
	public static boolean uploadFileWithProgress(Context myContext,
			String path, Map<String, String> params, FormFile[] files,
			Handler myHandler, int sendMessageFlag, String showText) {
		try {
			final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
			final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志
			// Log.e("lmh",
			// "-------LMHFile0-------"+files[0].getFile().length());
			int fileDataLength = 0;
			for (FormFile uploadFile : files) {// 得到文件类型数据的总长度
				Log.e("lmh", "-------LMHFile88-------"
						+ uploadFile.getFile().length());
				StringBuilder fileExplain = new StringBuilder();
				fileExplain.append("--");
				fileExplain.append(BOUNDARY);
				fileExplain.append("\r\n");
				fileExplain.append("Content-Disposition: form-data;name=\""
						+ uploadFile.getParameterName() + "\";filename=\""
						+ uploadFile.getFilname() + "\"\r\n");
				fileExplain.append("Content-Type: "
						+ uploadFile.getContentType() + "\r\n\r\n");
				fileExplain.append("\r\n");
				fileDataLength += fileExplain.length();
				if (uploadFile.getInStream() != null) {
					fileDataLength += uploadFile.getFile().length();
				} else {
					fileDataLength += uploadFile.getData().length;
				}
			}
			StringBuilder textEntity = new StringBuilder();
			for (Map.Entry<String, String> entry : params.entrySet()) {// 构造文本类型参数的实体数据
				textEntity.append("--");
				textEntity.append(BOUNDARY);
				textEntity.append("\r\n");
				textEntity.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				textEntity.append(entry.getValue());
				textEntity.append("\r\n");
			}
			// 计算传输给服务器的实体数据总长度
			int dataLength = (int) (textEntity.toString().getBytes().length
					+ fileDataLength + endline.getBytes().length);
			Log.e("lmh", "-------LMHdataLength-------" + dataLength);
			URL url = new URL(path);
			int port = url.getPort() == -1 ? 80 : url.getPort();
			System.out.println("port=" + port);
			Socket socket = new Socket(InetAddress.getByName(url.getHost()),
					port);
			// 超时时间
			// socket.setSoTimeout(300000);
			OutputStream outStream = socket.getOutputStream();
			// 下面完成HTTP请求头的发送
			String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
			outStream.write(requestmethod.getBytes());
			String accept = "Accept: image/png, image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
			outStream.write(accept.getBytes());
			String language = "Accept-Language: zh-CN\r\n";
			outStream.write(language.getBytes());
			String contenttype = "Content-Type: multipart/form-data; boundary="
					+ BOUNDARY + "\r\n";
			outStream.write(contenttype.getBytes());
			String contentlength = "Content-Length: " + dataLength + "\r\n";
			outStream.write(contentlength.getBytes());
			String alive = "Connection: Keep-Alive\r\n";
			outStream.write(alive.getBytes());
			String host = "Host: " + url.getHost() + ":" + port + "\r\n";
			outStream.write(host.getBytes());
			// 写完HTTP请求头后根据HTTP协议再写一个回车换行
			outStream.write("\r\n".getBytes());
			// 把所有文本类型的实体数据发送出来
			outStream.write(textEntity.toString().getBytes());
			// 浮点型的上传文件长度
			double fileLen = Double.parseDouble(String.valueOf(fileDataLength));
			double currentLen = 0;

			// 把所有文件类型的实体数据发送出来
			for (FormFile uploadFile : files) {
				StringBuilder fileEntity = new StringBuilder();
				fileEntity.append("--");
				fileEntity.append(BOUNDARY);
				fileEntity.append("\r\n");
				fileEntity.append("Content-Disposition: form-data;name=\""
						+ uploadFile.getParameterName() + "\";filename=\""
						+ uploadFile.getFilname() + "\"\r\n");
				fileEntity.append("Content-Type: "
						+ uploadFile.getContentType() + "\r\n\r\n");
				outStream.write(fileEntity.toString().getBytes());
				if (uploadFile.getInStream() != null) {

					System.out.println(uploadFile.getFilname() + " fileLen="
							+ fileLen);
					byte[] buffer = new byte[10240];
					int len = 0;
					while ((len = uploadFile.getInStream().read(buffer, 0,
							10240)) != -1) {
						currentLen += len;
						System.out.println("currentLen=" + currentLen);
						if (HttpUtil.checkNetWorkStatus(myContext)) {
							System.out.println("网络正常");
							outStream.write(buffer, 0, len);
						} else {
							System.out.println("网络异常");
							outStream.flush();
							outStream.close();
							// reader.close();
							socket.close();
							return false;
						}
						// long totalLength = fileDataLength
						int percent = (int) Math
								.floor((currentLen / fileLen) * 100);
						System.out.println("percent=" + percent);
						String showText1 = showText + percent + "%    ";
						// 发送进度消息
						HandlerUtil.sentMessage(myHandler, sendMessageFlag,
								percent);
					}
					uploadFile.getInStream().close();
				} else {
					System.out.println("inStream is null");
					outStream.write(uploadFile.getData(), 0, uploadFile
							.getData().length);
				}
				outStream.write("\r\n".getBytes());
				System.out.println("outStream=" + outStream);
			}
			// 下面发送数据结束标志，表示数据已经结束
			outStream.write(endline.getBytes());

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String returnStr = reader.readLine();

			System.out.println("reader.readLine()" + returnStr);
			if (returnStr.indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
				System.out.println("socket false");
				// 发送进度消息
				HandlerUtil.sentMessage(myHandler, sendMessageFlag, 100);
				return false;
			}
			outStream.flush();
			outStream.close();
			reader.close();
			socket.close();
			System.out.println("socket true");
			// 发送进度消息
			HandlerUtil.sentMessage(myHandler, sendMessageFlag, 100);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

}