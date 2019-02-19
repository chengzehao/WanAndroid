package com.sgitg.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.securepreferences.SecurePreferences;
import com.sgitg.common.base.BaseActivity;
import com.sgitg.common.imageloader.MediaLoader;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/5/23/023 22:04
 */

public class LibApp extends MultiDexApplication {
    private static LibApp mInstance;
    private List<BaseActivity> mAllActivities = new ArrayList<>();
    private SecurePreferences mSecurePrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        InitializationConfig config = InitializationConfig.newBuilder(this)
                .connectionTimeout(20 * 1000)
                .readTimeout(20 * 1000)
                .cacheStore(new DBCacheStore(this).setEnable(true))
                .cookieStore(new DBCookieStore(this).setEnable(true))
                .retry(0)
                .build();
        NoHttp.initialize(config);
        Logger.setDebug(true);
        Logger.setTag("ZHOUL");
        ZXingLibrary.initDisplayOpinion(this);
        Album.initialize(
                AlbumConfig.newBuilder(mInstance)
                        .setAlbumLoader(new MediaLoader())
                        .setLocale(Locale.getDefault())
                        .build()
        );
        CaocConfig.Builder.create().apply();
        LitePal.initialize(this);
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
    }

    public static synchronized LibApp getInstance() {
        return mInstance;
    }

    public SharedPreferences getSharedPreferences() {
        if (mSecurePrefs == null) {
            mSecurePrefs = new SecurePreferences(this, NdkMethods.getPreferencesPd(), "mm_prefs.xml");
        }
        return mSecurePrefs;
    }

    public void addActivity(BaseActivity act) {

        mAllActivities.add(act);
    }

    public void removeActivity(BaseActivity act) {
        mAllActivities.remove(act);
    }

    public List<BaseActivity> getAllActivities() {
        return mAllActivities;
    }

    public void exitApp() {
        synchronized (LibApp.class) {
            for (Activity act : mAllActivities) {
                act.finish();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
