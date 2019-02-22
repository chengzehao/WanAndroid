package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.ProjectBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
import com.sgitg.common.http.StringRequest;
import com.sgitg.common.viewmodel.BaseViewModel;
import com.yanzhenjie.nohttp.RequestMethod;

import java.io.File;

/**
 * 描述：
 *
 * @author 周麟
 * @date 2018/5/24/024 11:44
 */

public class ProjectViewModel extends BaseViewModel {
    private MutableLiveData<RestResult<ProjectBean>> mProjectData;
    private MutableLiveData<RestResult<String>> mCollectResult;
    private MutableLiveData<RestResult<String>> mUnCollectResult;

    public ProjectViewModel() {
        mProjectData = new MutableLiveData<>();
        mCollectResult = new MutableLiveData<>();
        mUnCollectResult = new MutableLiveData<>();
    }

    public void loadProject(int pageIndex, String cid) {
        String url = Urls.PROJECT_LIST + File.separator + pageIndex + File.separator + "json";
        EntityRequest<ProjectBean> request = new EntityRequest<>(url, RequestMethod.GET, ProjectBean.class);
        request.add("cid", cid);
        CallServer.getInstance().request(0, request, new HttpListener<ProjectBean>() {
            @Override
            public void onResponse(int what, RestResult<ProjectBean> t) {
                mProjectData.setValue(t);
            }
        });
    }

    public LiveData<RestResult<ProjectBean>> getProject() {
        return mProjectData;
    }

    public void collect(String id) {
        startLoading("提交中");
        String url = Urls.COLLECT + File.separator + id + File.separator + "json";
        StringRequest request = new StringRequest(url, RequestMethod.POST);
        CallServer.getInstance().request(0, request, new HttpListener<String>() {
            @Override
            public void onResponse(int what, RestResult<String> t) {
                dismissLoading();
                mCollectResult.setValue(t);
            }
        });
    }

    public MutableLiveData<RestResult<String>> getCollectResult() {
        return mCollectResult;
    }

    public void unCollect(String id) {
        startLoading("提交中");
        String url = Urls.UNCOLLECT + File.separator + id + File.separator + "json";
        StringRequest request = new StringRequest(url, RequestMethod.POST);
        CallServer.getInstance().request(0, request, new HttpListener<String>() {
            @Override
            public void onResponse(int what, RestResult<String> t) {
                dismissLoading();
                mUnCollectResult.setValue(t);
            }
        });
    }

    public MutableLiveData<RestResult<String>> getUnCollectResult() {
        return mUnCollectResult;
    }

}
