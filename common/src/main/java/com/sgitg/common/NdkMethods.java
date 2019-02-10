package com.sgitg.common;

import android.content.Context;

/**
 * 描述：NDK方法类
 *
 * @author 周麟
 * @created  2018/1/23/023 20:21
 */

public class NdkMethods {
    static {
        System.loadLibrary("native-lib");
    }

    public static native void signatureVerify(Context context);

    public static native String getPreferencesPd();

    public static native String getServerPath();

}
