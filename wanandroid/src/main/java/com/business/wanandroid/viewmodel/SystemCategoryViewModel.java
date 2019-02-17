package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.SystemCategoryBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityListRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

import java.util.ArrayList;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class SystemCategoryViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ArrayList<SystemCategoryBean>>> mCategory;

    public SystemCategoryViewModel() {
        mCategory = new MutableLiveData<>();
    }

    public void loadCategory() {
        String url = Urls.SYSTEM_CATEGORY;
        EntityListRequest<SystemCategoryBean> request = new EntityListRequest<>(url, RequestMethod.GET, SystemCategoryBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<SystemCategoryBean>>() {
            @Override
            public void onResponse(int what, RestResult<ArrayList<SystemCategoryBean>> t) {
                mCategory.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArrayList<SystemCategoryBean>>> getCategory() {
        return mCategory;
    }

}
