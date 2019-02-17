package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.ProjectCategoryBean;
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

public class ProjectCategoryViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ArrayList<ProjectCategoryBean>>> mProjectCategoryData;

    public ProjectCategoryViewModel() {
        mProjectCategoryData = new MutableLiveData<>();
    }

    public void loadProjectCategory() {
        EntityListRequest<ProjectCategoryBean> request = new EntityListRequest<>(Urls.PROJECT_CATEGORY, RequestMethod.GET, ProjectCategoryBean.class);
        CallServer.getInstance().request(0, request, new HttpListener<ArrayList<ProjectCategoryBean>>() {
            @Override
            public void onResponse(int what, RestResult<ArrayList<ProjectCategoryBean>> t) {
                mProjectCategoryData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ArrayList<ProjectCategoryBean>>> getProjectCategory() {
        return mProjectCategoryData;
    }

}
