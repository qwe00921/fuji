package com.fsti.fjdicClient.util;

import android.content.Context;
import android.widget.ImageView;

import com.fsti.fjdicClient.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * 图片加载帮助�?DisplayImageOptions是用于设置图片显示的类： 1.此类的功能： //设置图片在下载期间显示的图片 showStubImage(R.drawable.ic_launcher) //设置图片Uri为空或是错误的时候显示的图片 showImageForEmptyUri(R.drawable.ic_empty) //设置图片加载/解码过程中错误时候显示的图片 showImageOnFail(R.drawable.ic_error) //设置图片在下载前是否重置，复�?resetViewBeforeLoading() //设置下载的图片是否缓存在内存�?cacheInMemory() //设置下载的图片是否缓存在SD卡中 cacheOnDisc() //设置图片的解码类�?bitmapConfig(Bitmap.Config.RGB_565)
 * //设置图片的解码配�?decodingOptions(android.graphics.BitmapFactory.Options decodingOptions) //设置图片下载前的延迟 delayBeforeLoading(int delayInMillis) //设置额外的内容给ImageDownloader extraForDownloader(Object extra) //设置图片加入缓存前，对bitmap进行设置 preProcessor(BitmapProcessor preProcessor) //设置显示前的图片，显示后这个图片�?��保留在缓存中 postProcessor(BitmapProcessor postProcessor) //设置图片以如何的编码方式显示 imageScaleType(ImageScaleType imageScaleType)
 * .此类的两种创建方�? DisplayImageOptions 创建的两种方式�? // 创建默认的DisplayImageOptions DisplayImageOptions option_0 = DisplayImageOptions.createSimple(); // 使用DisplayImageOptions.Builder()创建DisplayImageOptions DisplayImageOptions option_1 = new DisplayImageOptions.Builder() .showStubImage(R.drawable.ic_launcher) .showImageOnFail(R.drawable.ic_error) .showImageForEmptyUri(R.drawable.ic_empty).cacheInMemory()
 * .cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)) .build(); 3.类中的方法使用： 设置图片的显示方�?displayer(BitmapDisplayer displayer) displayer�?RoundedBitmapDisplayer（int roundPixels）设置圆角图�?FakeBitmapDisplayer（）这个类什么都没做 FadeInBitmapDisplayer（int durationMillis）设置图片渐显的时间 �??�??�??�?SimpleBitmapDisplayer()正常显示�?��图片 图片的缩放方�?imageScaleType(ImageScaleType imageScaleType) imageScaleType: EXACTLY
 * :图像将完全按比例缩小的目标大�? EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数�? IN_SAMPLE_POWER_OF_2:图片将降�?倍，直到下一减少步骤，使图像更小的目标大�?NONE:图片不会调整
 * 
 * @author syc
 */
public class ImageLoaderHelper {

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                // .enableLogging() // Not necessary in common
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

    }

    static DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.image_load_err)// 加载中的图片
                                               .showImageForEmptyUri(R.drawable.image_load_err)// URL为空的图片
                                               .showImageOnFail(R.drawable.image_load_err)// 加载失败的图片
                                               .cacheInMemory(true)// 是否缓存
                                               .cacheOnDisc(true).displayer(new SimpleBitmapDisplayer()).build(); // 图片圆角

    public static void displayImage(ImageView imageView, String uri) {
        ImageLoader.getInstance().displayImage(uri, imageView, options);
    }

    public static void displayImage(ImageView imageView, String uri, int tubImage, int emptyImg, int failImg) {
        DisplayImageOptions temp_options = new DisplayImageOptions.Builder().showStubImage(tubImage).showImageForEmptyUri(emptyImg).showImageOnFail(failImg).cacheInMemory(true).cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(5)).build();
        ImageLoader.getInstance().displayImage(uri, imageView, temp_options);
    }

    public static void displayImageAndSetFail(ImageView imageView, String uri, int failDrawable, int roundPx) {
        DisplayImageOptions temp_options = new DisplayImageOptions.Builder().showStubImage(failDrawable).showImageForEmptyUri(failDrawable).showImageOnFail(failDrawable).cacheInMemory(true)
                .cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(roundPx)).build();
        ImageLoader.getInstance().displayImage(uri, imageView, temp_options);
    }
}
