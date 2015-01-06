package com.fsti.fjdicClient.util;

import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

/**
 * 处理图片的工具类.
 * 
 * @author zhengdh
 */
public class ImageTools {

	/***/
	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		
		int width, height;
		
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/***/
	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);

		return toRoundCorner(bmpGrayscale, pixels);
	}

	/***/
	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
				.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// final int color = 0xff424242;
		final int color = 0x8080ff77;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN);
		canvas.drawBitmap(bitmap, rect, rectF, paint);

		return output;
	}

	/***/
	/**
	 * 使圆角功能支持BitampDrawable
	 * 
	 * @param bitmapDrawable
	 * @param pixels
	 * @return
	 */
	public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
			int pixels) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
		return bitmapDrawable;
	}

	/**
	 * 把原始图片变成圆角,并且添加背景色
	 * 
	 * @param bmpOriginal
	 * @param pixels
	 * @return圆角且有背景色的图片
	 */
	public static Bitmap addColorBackground(Bitmap bmpOriginal, int pixels) {

		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap colorBg = Bitmap.createBitmap(width + 10, height + 10,
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(colorBg);

		final int color = 0xffff00ff; // 背景色，可自定义
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, colorBg.getWidth(), colorBg
				.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		// c.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		c.drawRoundRect(rectF, roundPx, roundPx, paint);

		// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN);
		c.drawBitmap(colorBg, rect, rectF, paint);

		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(1);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.reset();
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 5, 5, paint);
		return colorBg;
	}

	/**
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	public static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
				.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

//	/**
//	 * 优化加载图片，防止内存溢出
//	 * @param path 图片路径
//	 * @param iv 图片控件
//	 */
//	public static void optimizeLoadImage(String path, ImageView iv) {
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		opts.inJustDecodeBounds = true;
//		BitmapFactory.decodeFile(path, opts);
//
//		opts.inSampleSize = computeSampleSize(opts, -1, 1280 * 1280);
//		opts.inJustDecodeBounds = false;
//		try {
//			Bitmap bmp = BitmapFactory.decodeFile(path, opts);
//			iv.setImageBitmap(bmp);
//		} catch (OutOfMemoryError err) {
//			err.printStackTrace();
//		}
//	}
	
	/**
	 * 优化加载图片，防止内存溢出
	 * @param path 图片路径
	 * @param iv 图片控件，height、width需为自适应
	 * @param fileSize 图片大小
	 */
	public static void optimizeLoadImage(String path, ImageView iv,long fileSize) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);

		int width = opts.outWidth;
		int height = opts.outHeight;
		//设置对比标准图片大小为500k
		long standard = 500 * 1024;
		int sampleSize = 1;
		//根据图片大小，计算opts.inSampleSize大小，数值越大缩放比率越大
		if(fileSize < standard){
			sampleSize = 1;
		}
		else if((fileSize > standard) && (fileSize < 1000 * 1024)){
			sampleSize = 2;
		}
		else if((fileSize > 1000 * 1024) && (fileSize < 2000 * 1024)){
			sampleSize = 3;
		}
		else{
			sampleSize = 4;
		}
System.out.println("sampleSize after=" + sampleSize);
		opts.inSampleSize = sampleSize;
		opts.inJustDecodeBounds = false;
		try {
			Bitmap bmp = BitmapFactory.decodeFile(path, opts);
			iv.setImageBitmap(bmp);
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
	}
	
	/**
	 * 优化加载图片，防止内存溢出
	 * @param path 图片路径
	 * @param fileSize 图片大小
	 */
	public static Bitmap optimizeLoadImage(String path,long fileSize) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);

		int width = opts.outWidth;
		int height = opts.outHeight;
		//设置对比标准图片大小为500k
		long standard = 500 * 1024;
		int sampleSize = 1;
		//根据图片大小，计算opts.inSampleSize大小，数值越大缩放比率越大
		if(fileSize < standard){
			sampleSize = 1;
		}
		else if((fileSize > standard) && (fileSize < 1000 * 1024)){
			sampleSize = 2;
		}
		else if((fileSize > 1000 * 1024) && (fileSize < 2000 * 1024)){
			sampleSize = 3;
		}
		else{
			sampleSize = 4;
		}
System.out.println("sampleSize after=" + sampleSize);
		opts.inSampleSize = sampleSize;
		opts.inJustDecodeBounds = false;
		Bitmap bmp = null;
		try {
			bmp = BitmapFactory.decodeFile(path, opts);
//			iv.setImageBitmap(bmp);
		} catch (OutOfMemoryError err) {
			err.printStackTrace();
		}
		
		return bmp;
	}
}