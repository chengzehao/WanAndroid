package com.sgitg.common.utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sgitg.common.LibApp;
import com.sgitg.common.R;
import com.sgitg.common.thread.MainThreadExcute;
import com.sdsmdg.tastytoast.ErrorToastView;
import com.sdsmdg.tastytoast.SuccessToastView;

/**
 * 描述：
 *
 * @author 周麟
 * @created 2018/1/15 8:42
 */

public class ToastUtils {
    private static volatile ToastUtils sInstance;

    public static ToastUtils getInstance() {
        if (sInstance == null) {
            synchronized (ToastUtils.class) {
                if (sInstance == null) {
                    sInstance = new ToastUtils();
                }
            }
        }
        return sInstance;
    }

    private Toast mToast;

    private ToastUtils() {
        mToast = new Toast(LibApp.getInstance());
    }

    public void showSuccessInfoToast(final String info) {
        MainThreadExcute.post(new Runnable() {
            @Override
            public void run() {
                View layout = LayoutInflater.from(LibApp.getInstance()).inflate(R.layout.success_toast_layout, null, false);
                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(info);
                SuccessToastView successToastView = layout.findViewById(R.id.successView);
                successToastView.startAnim();
                text.setBackgroundResource(R.drawable.success_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                mToast.setView(layout);
                mToast.setView(layout);
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    public void showErrorInfoToast(final String info) {
        MainThreadExcute.post(new Runnable() {
            @Override
            public void run() {
                View layout = LayoutInflater.from(LibApp.getInstance()).inflate(R.layout.error_toast_layout, null, false);
                TextView text = (TextView) layout.findViewById(R.id.toastMessage);
                text.setText(info);
                ErrorToastView errorToastView = layout.findViewById(R.id.errorView);
                errorToastView.startAnim();
                text.setBackgroundResource(R.drawable.error_toast);
                text.setTextColor(Color.parseColor("#FFFFFF"));
                mToast.setView(layout);
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
}
