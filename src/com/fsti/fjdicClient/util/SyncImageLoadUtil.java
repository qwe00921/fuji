package com.fsti.fjdicClient.util;


/**
 * 异步图片加载器
 * 
 * @author 金涛
 */
//public class SyncImageLoadUtil {
//
//    private static final String                      TAG             = "SyncImageLoadUtil";
//
//    private Object                                   lock            = new Object();
//
//    private boolean                                  mAllowLoad      = true;
//
//    private boolean                                  firstLoad       = true;
//
//    private int                                      mStartLoadLimit = 0;
//
//    private int                                      mStopLoadLimit  = 0;
//
//    final Handler                                    handler         = new Handler();
//
//    private HashMap<String, SoftReference<Drawable>> imageCache      = new HashMap<String, SoftReference<Drawable>>();
//
//    public static String                             mImageSavePath;
//
//    public SyncImageLoadUtil() {
//    }
//
//    public SyncImageLoadUtil(String path) {
//        this.mImageSavePath = path;
//    }
//
//    public interface OnImageLoadListener {
//        public void onImageLoad(Integer t, Drawable drawable);
//
//        public void onError(Integer t);
//    }
//
//    public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {
//        if (startLoadLimit > stopLoadLimit) {
//            return;
//        }
//        mStartLoadLimit = startLoadLimit;
//        mStopLoadLimit = stopLoadLimit;
//    }
//
//    public void restore() {
//        mAllowLoad = true;
//        firstLoad = true;
//    }
//
//    public void lock() {
//        mAllowLoad = false;
//        firstLoad = false;
//    }
//
//    public void unlock() {
//        mAllowLoad = true;
//        synchronized (lock) {
//            lock.notifyAll();
//        }
//    }
//
//    public void loadImage(Integer t, String imageUrl, OnImageLoadListener listener, Context context) {
//        final OnImageLoadListener mListener = listener;
//        final String mImageUrl = context.getString(R.string.base_url) + imageUrl;
//        final Integer mt = t;
//
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                if (!mAllowLoad) {
//                    // DebugUtil.debug("prepare to load");
//                    synchronized (lock) {
//                        try {
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                if (mAllowLoad && firstLoad) {
//                    loadImage(mImageUrl, mt, mListener);
//                }
//
//                if (mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit) {
//                    loadImage(mImageUrl, mt, mListener);
//                }
//            }
//
//        }).start();
//    }
//
//    private void loadImage(final String mImageUrl, final Integer mt, final OnImageLoadListener mListener) {
//
//        if (imageCache.containsKey(mImageUrl)) {
//            SoftReference<Drawable> softReference = imageCache.get(mImageUrl);
//            final Drawable d = softReference.get();
//            if (d != null) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mAllowLoad) {
//                            mListener.onImageLoad(mt, d);
//                        }
//                    }
//                });
//                return;
//            }
//        }
//        try {
//            final Drawable d = loadImageFromUrl(mImageUrl);
//            if (d != null) {
//                imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
//            }
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (mAllowLoad) {
//                        mListener.onImageLoad(mt, d);
//                    }
//                }
//            });
//        } catch (IOException e) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mListener.onError(mt);
//                }
//            });
//            e.printStackTrace();
//        }
//    }
//
//    public static Drawable loadImageFromUrl(String url) throws IOException {
//        // DebugUtil.debug(url);
//
//        Log.e(TAG, "图片路径" + url);
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//
//            String dirs = Environment.getExternalStorageDirectory() + "/" + mImageSavePath + "/";
//            String fileName = dirs + MD5.getMD5(url);
//            File pathFile = new File(dirs);
//            try {
//                if (!(pathFile.isDirectory()) && !(pathFile.exists())) {
//                    boolean create = pathFile.mkdirs();
//                    if (create) {
//                        Log.e("图片下载", "创建文件夹成功！");
//                    } else {
//                        Log.e("图片下载", "创建文件夹失败！");
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            File f = new File(fileName);
//            // if(!f.exists())
//            // {
//            // f.createNewFile();
//            // }
//            if (f.length() > 0 & f.exists()) {
//                FileInputStream fis = new FileInputStream(f);
//                Drawable d = null;
//                try {
//                    d = Drawable.createFromStream(fis, "src");
//                    fis.close();
//                    Log.e("读取图片", "------------");
//                } catch (OutOfMemoryError e) {
//                    // TODO: handle exception
//                    d = null;
//                    Log.e("图片内存溢出", "------------");
//                    // wang...
//                    // System.runFinalization();
//                    // Runtime.getRuntime().gc();
//                    // System.gc();
//                    System.gc();
//                    System.runFinalization();
//                }
//                if (d == null) {
//                    if (f.isFile() && f.exists()) {
//                        f.delete();
//                        return loadImageFromUrl(url);
//                    }
//                }
//                // d.setCallback(null);//wang...
//                return d;
//            }
//            // String strUrl = URLEncoder.encode(url, "utf-8");
//            // System.out.println("strUrl=" + strUrl);
//
//            // String strRight = url.substring(url.lastIndexOf("/")+1, url.length()-1);
//            // url = url.replaceAll(strRight, URLEncoder.encode(strRight, "utf-8"));
//            System.out.println("url before=" + url);
//            // url = url.trim();
//            // String[] replaceArray = url.split("/");
//            // String[] replacedBefore = new String[replaceArray.length - 3];
//            // String[] replacedAfter = new String[replaceArray.length - 3];
//            // String replacedBeforeStr = "";
//            // String replacedAfterStr = "";
//            // for(int i=3;i<replaceArray.length;i++){
//            // replacedBefore[i-3] = replaceArray[i];
//            // System.out.println("replaceArray[i]=" + replaceArray[i]);
//            // replacedBeforeStr += "/" + replacedBefore[i-3];
//            // System.out.println("replacedBeforeStr=" + replacedBeforeStr);
//            // replacedAfter[i-3] = URLEncoder.encode(replacedBefore[i-3], "utf-8");
//            // System.out.println("replacedAfter[i-3]=" + replacedAfter[i-3]);
//            // replacedAfterStr += "/" + replacedAfter[i-3];
//            // System.out.println("replacedAfterStr=" + replacedAfterStr);
//            // }
//            //
//            // if(url.indexOf("%") < 0){
//            // url = url.replaceAll(replacedBeforeStr, replacedAfterStr);
//            // }
//
//            url = HttpUtil.EncoderURL(url);
//
//            URL m = new URL(url);
//            InputStream i = (InputStream) m.getContent();
//            DataInputStream in = new DataInputStream(i);
//            FileOutputStream out = new FileOutputStream(f);
//            byte[] buffer = new byte[1024];
//            int byteread = 0;
//            while ((byteread = in.read(buffer)) != -1) {
//                out.write(buffer, 0, byteread);
//            }
//            in.close();
//            out.close();
//            // Drawable d = Drawable.createFromStream(i, "src");
//            return loadImageFromUrl(url);
//        } else {
//            URL m = new URL(url);
//            InputStream i = (InputStream) m.getContent();
//            Drawable d = Drawable.createFromStream(i, "src");
//            return d;
//        }
//
//    }
//
//    public void displayImageTest(String url, ImageView ivGoods, Context context) {
//        // TODO Auto-generated method stub
//        // 添加图片连接地址前缀
//        // final String mImageUrl = "http://yads.gnway.net:81/" + url;
//        // final String mImageUrl = context.getString(R.string.base_url) + url;
//
//        if (url != null && !url.equals("") && !url.substring(0, 5).equals("http:")) {
//            url = context.getString(R.string.base_url) + url;
//        }
//        final String mImageUrl = url;
//        final ImageView mt = ivGoods;
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                if (!mAllowLoad) {
//                    // DebugUtil.debug("prepare to load");
//                    synchronized (lock) {
//                        try {
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                if (mAllowLoad && firstLoad) {
//                    loadImage(mImageUrl, mt);
//                }
//            }
//
//        }).start();
//    }
//
//    public void displayImage(String url, ImageView ivGoods, Context context) {
//        // TODO Auto-generated method stub
//        // 添加图片连接地址前缀
//        // final String mImageUrl = context.getString(R.string.base_url) + url;
//        if (url != null && !url.equals("") && !url.substring(0, 5).equals("http:")) {
//            url = context.getString(R.string.base_url) + url;
//        }
//        final String mImageUrl = url;
//        final ImageView mt = ivGoods;
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                if (!mAllowLoad) {
//                    // DebugUtil.debug("prepare to load");
//                    synchronized (lock) {
//                        try {
//                            lock.wait();
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                if (mAllowLoad && firstLoad) {
//                    loadImage(mImageUrl, mt);
//                }
//            }
//
//        }).start();
//    }
//
//    protected void loadImage(String url, ImageView mt1) {
//        // TODO Auto-generated method stub
//        final String mImageUrl = url;
//        final ImageView mt = mt1;
//        if (imageCache.containsKey(mImageUrl)) {
//            SoftReference<Drawable> softReference = imageCache.get(mImageUrl);
//            final Drawable d = softReference.get();
//            if (d != null) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mAllowLoad) {
//                            mt.setImageDrawable(d);
//                            d.setCallback(null);
//                            // Log.e("loadImage SoftReference", "setCallback(null);-----");
//                        }
//                    }
//                });
//                return;
//            }
//        }
//        try {
//            final Drawable d = loadImageFromUrl(mImageUrl);
//            if (d != null) {
//                imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (mAllowLoad) {
//                            mt.setImageDrawable(d);
//                            d.setCallback(null);
//                            // Log.e("loadImage FromUrl d!=null", "setCallback(null);-----");
//                        }
//                    }
//                });
//                // d.setCallback(null);
//                // Log.e("loadImage FromUrl d!=null", "d.setCallback(null);-----");
//            } else {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mt.setScaleType(ScaleType.FIT_CENTER);
//                        mt.setImageResource(R.drawable.image_load_err);
//                        // Log.e("loadImage d==null", "setImageResource(R.drawable.image_load_err);-----");
//                    }
//                });
//            }
//
//        } catch (IOException e) {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mt.setScaleType(ScaleType.FIT_CENTER);
//                    mt.setImageResource(R.drawable.image_load_err);
//                    // Log.e("loadImage error", "setImageResource(R.drawable.image_load_err);-----");
//                }
//            });
//            e.printStackTrace();
//        }
//    }
// }
