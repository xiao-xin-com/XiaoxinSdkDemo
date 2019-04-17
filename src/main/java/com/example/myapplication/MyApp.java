package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.xiaoxin.sdk.Xiaoxin;
import com.xiaoxin.sdk.call.OnCallXiaoxin;
import com.xiaoxin.sdk.data.Person;
import com.xiaoxin.sdk.utils.Log;

import io.rong.imlib.RongIMClient;

/**
 * Created by ljman on 2018/4/11.
 */

public class MyApp extends MultiDexApplication {

    private static final String TAG = "Xiaoxin";
    public static Context context;

    // 应用的 appkey, 请向孝信通申请
    public static String appKey = "4f18da48538e7bb8";
    // 应用的的 privateKeyString, 请向孝信通申请
    public static String privateKeyString = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMcg2xnvtaHrgF2Y" +
            "PD3RKBA9lUrA6OpLlNDPkRAC8OE4aLa2Zpcal8jv2UvyxK7xgLCQuGDsz/QuG+9J" +
            "nFqgP+mAniRO7TBnSIRkLmmIUZbNOJSlyJMlyJA708VG4lnf0qoYVc31ALURH2cJ" +
            "kldDDTwAxLzC0ubR5Gr8p9DKIHZbAgMBAAECgYEAsLLkGejPaUacGvGbC1PNVFQB" +
            "ZVs3k6jL/lsiI24oqAIxUWkcMhvD8ud7EFz10TO2eXeCVWfuxIQh/713RCgNbSl2" +
            "6FcXUnYDd9KaKSFUxX8ktxtuAEPxDK7bEU+u96WkmdeVO0G1F+DQP+QXEOJ3Nv76" +
            "v3czokDrVfB7UCe4BfkCQQD3YiHhxexbRodvcZ29JT1+hTRafx+/EIkbZKX9Au+o" +
            "0T2ZARE+RhVS5tACEZt/VikkuiF6O68kW4tsAFJg0GNdAkEAzhBxhlkBnUnKUtS3" +
            "xoQtzFMqD3nf29McW6xbrTvtLmzHQxNhhYjnp66eU41xNZu/jN8/wJyQXzYO/SnG" +
            "AF4dFwJBAJXE7phemGrmN0asNYB21FUN1hVX02N8YlWYSLn2FhxRmYZRVBwsl+pZ" +
            "3OLzv/2Gr2AR5AS5r55GYhFf3H/Kie0CQQCrDMk/rSiC6NrSsRKv7c6aejpCyHcD" +
            "YZV7n3ImNcVnMRKi4jakgwVQksmIL7PWvSdfB35DI3NQJW6Bxag/a+AXAkEArylR" +
            "XWWrZaXQOryxB/7c1V2nwRP15R2cRfIXK0MslVe9HsL1K2GNVUmFgrik6z64nAMR" +
            "cOfYbtD1cnnAW2i6eg==";
    // 百度地图的 ak，此 ak 为 demo 专用，集成 sdk 时请更换为自己的 ak，并替换 AndroidManifest.xml 中的对应内容，申请地址：http://lbsyun.baidu.com
    public static String baiduAk = "yUI7urcBZPEwkuQ0e8b5S5zOhTyAENRg";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Xiaoxin.init(this, appKey, privateKeyString, new OnCallXiaoxin() {
            @Override
            public void init() {

            }

            @Override
            public void onSuccessXX(@NonNull Person data) {
                Log.d(TAG, "onSuccessXX() called with: data = [" + data + "]");
            }

            @Override
            public void onErrorXX(Throwable throwable) {
                Log.d(TAG, "onErrorXX() called with: throwable = [" + throwable + "]");
            }

            @Override
            public void onSuccessRG(String data) {
                Log.d(TAG, "onSuccessRG() called with: data = [" + data + "]");
            }

            @Override
            public void onErrorRG(RongIMClient.ErrorCode errorCode) {
                Log.d(TAG, "onErrorRG() called with: errorCode = [" + errorCode + "]");
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(new MyUn());
    }
}
