package com.sgitg.common.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.sgitg.common.LibApp;

import java.util.List;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 15:56
 */

public class ServiceUtil {

    public static boolean isServiceWork(String serviceName) {
        ActivityManager myAM = (ActivityManager) LibApp.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = myAM.getRunningServices(40);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo runningService : runningServices) {
            if (runningService.service.getClassName().contains(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
