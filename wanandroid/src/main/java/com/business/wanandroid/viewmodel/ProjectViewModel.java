package com.business.wanandroid.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.business.wanandroid.Urls;
import com.business.wanandroid.bean.ProjectBean;
import com.sgitg.common.http.CallServer;
import com.sgitg.common.http.EntityRequest;
import com.sgitg.common.http.HttpListener;
import com.sgitg.common.http.RestResult;
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

    public ProjectViewModel() {
        mProjectData = new MutableLiveData<>();
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

}
