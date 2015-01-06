package com.fsti.fjdicClient;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.fsti.fjdicClient.dao.BusinessDao;
import com.fsti.fjdicClient.dao.CommonDao;
import com.fsti.fjdicClient.thread.ShortCutThread;
import com.fsti.fjdicClient.util.CrashHandler;
import com.fsti.fjdicClient.util.ImageLoaderHelper;
import com.fsti.fjdicClient.util.PersistenceVarUtil;

/**
 * 应用程序级全局类
 * 
 * @author 金涛
 */
public class ApplicationUtil extends Application {

    /**
     * 首页对象
     */
    public static Activity         MAINACTIVITY;
    public static Context          myContext;
    private static ApplicationUtil applicationInstance;
    private Handler                mHandler = new Handler();

    public static Handler getHandler() {
        return getInstance().mHandler;
    }

    public static CrashHandler crashHandler;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        System.out.println("----------------Application start----------------");

        myContext = getApplicationContext();
        ImageLoaderHelper.initImageLoader(getApplicationContext());
        addShortCut();
        String debuggerFlag = myContext.getString(R.string.debuggerFlag);
        if (debuggerFlag.equals("true")) {
            crashHandler = CrashHandler.getInstance();
            // 注册crashHandler
            crashHandler.init(myContext);
        }

        initDataBaseTable();
    }

    public ApplicationUtil() {
        applicationInstance = this;
    }

    public static ApplicationUtil getInstance() {
        if (applicationInstance == null) {
            applicationInstance = new ApplicationUtil();
        }
        return applicationInstance;
    }

    /**
     * 添加快捷方式
     */
    private void addShortCut() {
        String flag = PersistenceVarUtil.getApplicationStringValue(myContext, "shortcut", "isAddshortcut", "false");
        if (flag.equals("false")) {
            PersistenceVarUtil.setApplicationStringValue(myContext, "shortcut", "isAddshortcut", "true");
            // 生成桌面快捷方式
            ShortCutThread st = new ShortCutThread(myContext);
            st.start();
        }
    }

    /**
     * 初始化数据库表
     */
    private void initDataBaseTable() {
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_AdvInfo, BusinessDao.Table_Name_AdvInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_ShoppingMallSortInfo, BusinessDao.Table_Name_ShoppingMallSortInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_GoodsInfo, BusinessDao.Table_Name_GoodsInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_CollectionInfo, BusinessDao.Table_Name_CollectionInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_AccountInfo, BusinessDao.Table_Name_AccountInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_ShoppingcartInfo, BusinessDao.Table_Name_ShoppingcartInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_SearchHistoryInfo, BusinessDao.Table_Name_SearchHistoryInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_AddressInfo, BusinessDao.Table_Name_AddressInfo);
        CommonDao.creatTable(BusinessDao.SQL_Create_Table_HomeMainInfo, BusinessDao.Table_Name_HomeMainInfo);

    }

}
